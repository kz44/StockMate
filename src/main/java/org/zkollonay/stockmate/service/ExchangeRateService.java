package org.zkollonay.stockmate.service;

import org.zkollonay.stockmate.DTO.ExchangeRateDTO;

public interface ExchangeRateService {

  double fetchExchangeRate(String fromCurrency, String toCurrency);

  ExchangeRateDTO getExchangeRateDetails(String fromCurrency, String toCurrency);

  ExchangeRateDTO convertToCurrency(double amount, String fromCurrency, String toCurrency);
}
