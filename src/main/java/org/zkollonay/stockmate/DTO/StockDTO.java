package org.zkollonay.stockmate.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


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
  private Double amount;

  @NotBlank
  private String description;
}
