package org.zkollonay.stockmate.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.zkollonay.stockmate.ENUM.Currency;
import org.zkollonay.stockmate.ENUM.StockType;
import org.zkollonay.stockmate.ENUM.TradingVenue;

import java.math.BigDecimal;
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
  private BigDecimal amount;

  @NotBlank
  private String sumDescription;

  @NotBlank
  private String fullDescription;

  private TradingVenue tradingVenue;

  private LocalDateTime purchaseDate;

  @NonNull
  private BigDecimal purchasePricePerPiece;

  @NonNull
  private BigDecimal purchasePriceTotal;

  private Currency currency;

  private StockType stockType;
}
