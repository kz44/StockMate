package org.zkollonay.stockmate.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRateDTO {

  private String fromCurrency;

  private String toCurrency;

  private BigDecimal exchangeRate;

  private BigDecimal originalAmount;

  private BigDecimal convertedAmount;
}
