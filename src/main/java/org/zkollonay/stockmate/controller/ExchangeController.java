package org.zkollonay.stockmate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zkollonay.stockmate.DTO.ExchangeRateDTO;
import org.zkollonay.stockmate.service.ExchangeRateService;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchanges")
public class ExchangeController {
  private final ExchangeRateService exchangeRateService;


  /**
   * Retrieves the exchange rate details between two currencies for a default amount.
   *
   * @param fromCurrency The source currency code.
   * @param toCurrency   The target currency code.
   * @return ResponseEntity containing the ExchangeRateDTO with the exchange rate details.
   */
  @GetMapping("/rate")
  public ResponseEntity<ExchangeRateDTO> getExchangeRateDetails(
      @RequestParam String fromCurrency,
      @RequestParam String toCurrency) {
    return ResponseEntity.ok(exchangeRateService.getExchangeRateDetails(fromCurrency, toCurrency));
  }


  /**
   * Converts a specified amount from one currency to another.
   *
   * @param amount       The amount to convert.
   * @param fromCurrency The source currency code.
   * @param toCurrency   The target currency code.
   * @return ResponseEntity containing the ExchangeRateDTO with the converted amount.
   */
  @GetMapping("/convertTo")
  public ResponseEntity<ExchangeRateDTO> convertToCurrency(
      @RequestParam BigDecimal amount,
      @RequestParam String fromCurrency,
      @RequestParam String toCurrency) {
    return ResponseEntity.ok(exchangeRateService.convertToCurrency(amount, fromCurrency, toCurrency));
  }
}
