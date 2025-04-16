package org.zkollonay.stockmate.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.zkollonay.stockmate.DTO.ExchangeRateDTO;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImp implements ExchangeRateService {

  private final WebClient restClient;

  private final ObjectMapper jsonMapper;

  @Value("${exchange.api.key}")
  private String apiKey;

  @Value("${exchange.api.base-url}")
  private String baseUrl;

  private final BigDecimal DEFAULT_AMOUNT = BigDecimal.ONE;

  @Override
  public BigDecimal fetchExchangeRate(String fromCurrency, String toCurrency) {
    try {
      String requestApiUrl = baseUrl + apiKey + "/latest/" + fromCurrency;
      Mono<String> apiResponseMono = restClient.get()
          .uri(requestApiUrl)
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(String.class);

      String apiResponse = apiResponseMono.block();

      JsonNode responseJson = jsonMapper.readTree(apiResponse);
      BigDecimal rate = responseJson.path("conversion_rates").path(toCurrency).decimalValue();
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
