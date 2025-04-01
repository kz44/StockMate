package org.zkollonay.stockmate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zkollonay.stockmate.DTO.ExchangeRateDTO;
import org.zkollonay.stockmate.service.ExchangeRateService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges")
public class ExchangeController {
  private final ExchangeRateService exchangeRateService;

  /**
   * Get getExchangeRateDetails. ExchangeRateDTO -> (fromCurrency, toCurrency, exchangeRate, 1 originalAmount, convertedAmount)
   */
  @GetMapping("/rate")
  public ResponseEntity<ExchangeRateDTO> getExchangeRateDetails(
      @RequestParam String fromCurrency,
      @RequestParam String toCurrency) {
    return ResponseEntity.ok(exchangeRateService.getExchangeRateDetails(fromCurrency, toCurrency));
  }

  /**
   * Covert xy amount money from the given currency to a new one -> ExchangeRateDTO
   */
  @GetMapping("/convertTo")
  public ResponseEntity<ExchangeRateDTO> convertToCurrency(
      @RequestParam double amount,
      @RequestParam String fromCurrency,
      @RequestParam String toCurrency) {
    return ResponseEntity.ok(exchangeRateService.convertToCurrency(amount, fromCurrency, toCurrency));
  }
}
