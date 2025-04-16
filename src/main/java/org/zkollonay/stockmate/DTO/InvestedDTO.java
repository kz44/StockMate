package org.zkollonay.stockmate.DTO;

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

  private Currency currency;

  private BigDecimal amount;

  private BigDecimal amountInHUF;
}
