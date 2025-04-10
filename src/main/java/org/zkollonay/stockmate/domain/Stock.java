package org.zkollonay.stockmate.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.zkollonay.stockmate.ENUM.Currency;
import org.zkollonay.stockmate.ENUM.StockType;
import org.zkollonay.stockmate.ENUM.TradingVenue;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stocks")
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String name;

  @NotBlank
  private String stockIdentifier;

  @NonNull
  @Column(nullable = false)
  private Double amount;

  @NotBlank
  private String sumDescription;

  @NotBlank
  private String fullDescription;

  @Enumerated(EnumType.STRING)
  private TradingVenue tradingVenue;

  private LocalDateTime purchaseDate;

  @NonNull
  @Column(nullable = false)
  private Double purchasePricePerPiece;

  private Double purchasePriceTotal;

  @Enumerated(EnumType.STRING)
  private Currency currency;

  @Enumerated(EnumType.STRING)
  private StockType stockType;

  @PrePersist
  public void calculatePurchasePriceTotal() {
    if (purchasePriceTotal == null) {
      purchasePriceTotal = purchasePricePerPiece * amount;
    }
  }
}
