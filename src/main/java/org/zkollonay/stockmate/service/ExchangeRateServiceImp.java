package org.zkollonay.stockmate.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.zkollonay.stockmate.DTO.ExchangeRateDTO;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImp implements ExchangeRateService {

  private final RestTemplate restClient;
  private final ObjectMapper jsonMapper;

  private final BigDecimal DEFAULT_AMOUNT = BigDecimal.ONE;
  private final String EXCHANGE_API_BASE_URL = "https://v6.exchangerate-api.com/v6/832fbb78b6d27cf4385b78d0/latest/";

  @Override
  public BigDecimal fetchExchangeRate(String fromCurrency, String toCurrency) {
    try {
      String requestApiUrl = EXCHANGE_API_BASE_URL + fromCurrency;
      String apiResponse = restClient.getForObject(requestApiUrl, String.class);  // Get API answers
      JsonNode responseJson = jsonMapper.readTree(apiResponse); // Convert to JSON
      BigDecimal rate = responseJson.path("conversion_rates").path(toCurrency).decimalValue(); // -> Read the current exchange rate
      if (rate == null) {
        throw new RuntimeException("Exchange rate not found for " + fromCurrency + " to " + toCurrency);
      }
      return rate;
    } catch (Exception e) {
      throw new RuntimeException("Failed to fetch exchange rate from external service", e);
    }
  }

  public ExchangeRateDTO getExchangeRateDetails(String fromCurrency, String toCurrency) {
    if (fromCurrency.equals(toCurrency)) {
      throw new IllegalArgumentException("fromCurrency and toCurrency are both equal");
    }

    BigDecimal rate = fetchExchangeRate(fromCurrency, toCurrency);

    return ExchangeRateDTO.builder()
        .fromCurrency(fromCurrency)
        .toCurrency(toCurrency)
        .exchangeRate(rate)
        .originalAmount(DEFAULT_AMOUNT)
        .convertedAmount(DEFAULT_AMOUNT.multiply(rate))
        .build();
  }

  public ExchangeRateDTO convertToCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
    BigDecimal rate = BigDecimal.ONE;
    BigDecimal convertedAmount;

    if (fromCurrency.equals(toCurrency)) {
      convertedAmount = amount;
    } else {
      rate = fetchExchangeRate(fromCurrency, toCurrency);
      convertedAmount = amount.multiply(rate);
    }

    return ExchangeRateDTO.builder()
        .fromCurrency(fromCurrency)
        .toCurrency(toCurrency)
        .exchangeRate(rate)
        .originalAmount(amount)
        .convertedAmount(convertedAmount)
        .build();
  }
}
