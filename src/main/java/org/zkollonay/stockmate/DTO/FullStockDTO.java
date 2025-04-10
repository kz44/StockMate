package org.zkollonay.stockmate.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.zkollonay.stockmate.ENUM.Currency;
import org.zkollonay.stockmate.ENUM.StockType;
import org.zkollonay.stockmate.ENUM.TradingVenue;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullStockDTO {

  @NotBlank
  private String name;

  @NotBlank
  private String stockIdentifier;

  @NonNull
  private Double amount;

  @NotBlank
  private String sumDescription;

  @NotBlank
  private String fullDescription;

  private TradingVenue tradingVenue;

  private LocalDateTime purchaseDate;

  @NonNull
  private Double purchasePricePerPiece;

  @NonNull
  private Double purchasePriceTotal;

  private Currency currency;

  private StockType stockType;
}
