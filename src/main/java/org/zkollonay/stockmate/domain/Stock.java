package org.zkollonay.stockmate.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.zkollonay.stockmate.ENUM.Currency;
import org.zkollonay.stockmate.ENUM.StockType;
import org.zkollonay.stockmate.ENUM.TradingVenue;

import java.math.BigDecimal;
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


  /**
   * If user don't give purchasePriceTotal parameter, automatically calculate it
   */
  @PrePersist
  @PreUpdate
  public void calculatePurchasePriceTotal() {
    if (purchasePriceTotal == null) {
      purchasePriceTotal = purchasePricePerPiece.multiply(amount);
    }
  }
}
