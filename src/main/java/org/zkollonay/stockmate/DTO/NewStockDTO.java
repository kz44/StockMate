package org.zkollonay.stockmate.DTO;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class NewStockDTO {

  @NotBlank
  private String name;

  @NotBlank
  private String stockIdentifier;

  @NonNull
  private Double amount;

  @NotBlank
  private String description;

  @Enumerated(EnumType.STRING)
  private TradingVenue tradingVenue;

  private LocalDateTime purchaseDate;

  @NonNull
  private Double purchasePricePerPiece;

  @NonNull
  private Double purchasePriceTotal;

  @Enumerated(EnumType.STRING)
  private Currency currency;

  @Enumerated(EnumType.STRING)
  private StockType stockType;
}
