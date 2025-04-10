package org.zkollonay.stockmate.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.zkollonay.stockmate.DTO.ExchangeRateDTO;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImp implements ExchangeRateService {

  private final RestTemplate restClient;
  private final ObjectMapper jsonMapper;

  private final double DEFAULT_AMOUNT = 1;
  private final String EXCHANGE_API_BASE_URL = "https://v6.exchangerate-api.com/v6/832fbb78b6d27cf4385b78d0/latest/";

  @Override
  public double fetchExchangeRate(String fromCurrency, String toCurrency) {
    try {
      String requestApiUrl = EXCHANGE_API_BASE_URL + fromCurrency;
      String apiResponse = restClient.getForObject(requestApiUrl, String.class);  // Get API answers
      JsonNode responseJson = jsonMapper.readTree(apiResponse); // Convert to JSON
      return responseJson.path("conversion_rates").path(toCurrency).asDouble(); // -> Read the current exchange rate
    } catch (Exception e) {
      throw new RuntimeException("Failed to fetch exchange rate from external service", e);
    }
  }

  public ExchangeRateDTO getExchangeRateDetails(String fromCurrency, String toCurrency) {
    if (fromCurrency.equals(toCurrency)) {
      throw new IllegalArgumentException("fromCurrency and toCurrency are both equal");
    }

    double rate = fetchExchangeRate(fromCurrency, toCurrency);

    return ExchangeRateDTO.builder()
        .fromCurrency(fromCurrency)
        .toCurrency(toCurrency)
        .exchangeRate(rate)
        .originalAmount(DEFAULT_AMOUNT)
        .convertedAmount(DEFAULT_AMOUNT * rate)
        .build();
  }

  public ExchangeRateDTO convertToCurrency(double amount, String fromCurrency, String toCurrency) {
    double rate = 1;
    double convertedAmount;

    if (fromCurrency.equals(toCurrency)) {
      convertedAmount = amount;
    } else {
      rate = fetchExchangeRate(fromCurrency, toCurrency);
      convertedAmount = amount * rate;
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
