package org.zkollonay.stockmate.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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

  private long id;

  @NotBlank
  private String name;

  @NotBlank
  private String stockIdentifier;

  @NonNull
  @Column(nullable = false)
  @Positive
  private BigDecimal amount;

  @NotBlank
  private String sumDescription;

  @NotBlank
  private String fullDescription;

  @Enumerated(EnumType.STRING)
  private TradingVenue tradingVenue;

  private LocalDateTime purchaseDate;

  @NonNull
  @Column(nullable = false)
  @Positive
  private BigDecimal purchasePricePerPiece;

  @Positive
  private BigDecimal purchasePriceTotal;

  @Enumerated(EnumType.STRING)
  private Currency currency;

  @Enumerated(EnumType.STRING)
  private StockType stockType;
}
