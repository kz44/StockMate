package org.zkollonay.stockmate.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zkollonay.stockmate.ENUM.Currency;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestedDTO {

  @Enumerated(EnumType.STRING)
  private Currency currency;

  private BigDecimal amount;

  @Positive
  private BigDecimal amountInHUF;
}
