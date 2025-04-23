package org.zkollonay.stockmate.service;

import org.zkollonay.stockmate.DTO.ExchangeRateDTO;

import java.math.BigDecimal;

public interface ExchangeRateService {

  BigDecimal fetchExchangeRate(String fromCurrency, String toCurrency);

  ExchangeRateDTO getExchangeRateDetails(String fromCurrency, String toCurrency);

  ExchangeRateDTO convertToCurrency(BigDecimal amount, String fromCurrency, String toCurrency);
}
