package org.zkollonay.stockmate.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zkollonay.stockmate.ENUM.Currency;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestedDTO {

  private double amountInHUF;

  private Currency currency;
}
