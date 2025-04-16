package org.zkollonay.stockmate.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRateDTO {

  @Enumerated(EnumType.STRING)
  private String fromCurrency;

  @Enumerated(EnumType.STRING)
  private String toCurrency;

  @Positive
  private BigDecimal exchangeRate;

  @NonNull
  private BigDecimal originalAmount;

  @NonNull
  private BigDecimal convertedAmount;
}
