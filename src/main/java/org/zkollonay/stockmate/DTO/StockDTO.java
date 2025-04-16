package org.zkollonay.stockmate.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {

  @NotBlank
  private String name;

  @NotBlank
  private String stockIdentifier;

  @NonNull
  private BigDecimal amount;

  @NotBlank
  private String description;
}
