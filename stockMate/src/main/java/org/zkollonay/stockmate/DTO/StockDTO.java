package org.zkollonay.stockmate.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {

  private long id;

  @NotBlank
  private String name;

  @NotBlank
  private String stockIdentifier;

  @NonNull
  @Positive
  private BigDecimal amount;

  @NotBlank
  private String description;
}
