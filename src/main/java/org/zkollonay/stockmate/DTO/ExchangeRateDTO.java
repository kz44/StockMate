package org.zkollonay.stockmate.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRateDTO {

  private String fromCurrency;

  private String toCurrency;

  private double exchangeRate;

  private double originalAmount;

  private double convertedAmount;
}
