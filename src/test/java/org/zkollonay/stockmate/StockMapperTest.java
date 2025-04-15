package org.zkollonay.stockmate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.zkollonay.stockmate.DTO.FullStockDTO;
import org.zkollonay.stockmate.DTO.StockDTO;
import org.zkollonay.stockmate.ENUM.Currency;
import org.zkollonay.stockmate.ENUM.StockType;
import org.zkollonay.stockmate.ENUM.TradingVenue;
import org.zkollonay.stockmate.domain.Stock;
import org.zkollonay.stockmate.mapper.StockMapper;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("StockMapper Unit Tests")
public class StockMapperTest {

  private StockMapper stockMapper;

  @BeforeEach
  void setUp() {
    stockMapper = new StockMapper();
  }

  @Test
  @DisplayName("Should correctly map Stock entity to FullStockDTO")
  void shouldMapStockToFullStockDTO() {

    // Arrange
    Stock stockEntity = Stock.builder()
        .id(1L)
        .name("Apple Inc.")
        .stockIdentifier("AAPL")
        .amount(1.0) // Double típus
        .sumDescription("Tech giant shares")
        .fullDescription("Detailed description about Apple shares purchase.")
        .tradingVenue(TradingVenue.NASDAQ)
        .purchaseDate(LocalDateTime.of(2024, 5, 15, 10, 30, 0)) // LocalDateTime
        .purchasePricePerPiece(175.50)
        .purchasePriceTotal(175.50)
        .currency(Currency.USD)
        .stockType(StockType.STOCK)
        .build();

    // Act
    FullStockDTO resultDTO = stockMapper.toNewDTO(stockEntity);

    // Assert
    assertThat(resultDTO).isNotNull();
    assertThat(resultDTO.getName()).isEqualTo(stockEntity.getName());
    assertThat(resultDTO.getStockIdentifier()).isEqualTo(stockEntity.getStockIdentifier());
    assertThat(resultDTO.getAmount()).isEqualTo(stockEntity.getAmount());
    assertThat(resultDTO.getSumDescription()).isEqualTo(stockEntity.getSumDescription());
    assertThat(resultDTO.getFullDescription()).isEqualTo(stockEntity.getFullDescription());
    assertThat(resultDTO.getTradingVenue()).isEqualTo(stockEntity.getTradingVenue());
    assertThat(resultDTO.getPurchaseDate()).isEqualTo(stockEntity.getPurchaseDate());
    assertThat(resultDTO.getPurchasePricePerPiece()).isEqualTo(stockEntity.getPurchasePricePerPiece());
    assertThat(resultDTO.getPurchasePriceTotal()).isEqualTo(stockEntity.getPurchasePriceTotal());
    assertThat(resultDTO.getCurrency()).isEqualTo(stockEntity.getCurrency());
    assertThat(resultDTO.getStockType()).isEqualTo(stockEntity.getStockType());
  }



  @Test
  @DisplayName("Should correctly map Stock entity to FullStockDTO")
  void shouldMapFullStockDTOToStock() {

    // Arrange
    FullStockDTO fullStockDTO = FullStockDTO.builder()
        .name("Apple Inc.")
        .stockIdentifier("AAPL")
        .amount(2.0)
        .sumDescription("Tech giant shares")
        .fullDescription("Detailed description about Apple shares purchase.")
        .tradingVenue(TradingVenue.NASDAQ)
        .purchaseDate(LocalDateTime.of(2024, 5, 15, 10, 30, 0))
        .purchasePricePerPiece(20.10)
        .purchasePriceTotal(40.20)
        .currency(Currency.USD)
        .stockType(StockType.STOCK)
        .build();

    // Act
    Stock stockEntity = stockMapper.toEntity(fullStockDTO);

    // Assert
    assertThat(stockEntity).isNotNull();
    assertThat(stockEntity.getName()).isEqualTo(fullStockDTO.getName());
    assertThat(stockEntity.getStockIdentifier()).isEqualTo(fullStockDTO.getStockIdentifier());
    assertThat(stockEntity.getAmount()).isEqualTo(fullStockDTO.getAmount());
    assertThat(stockEntity.getSumDescription()).isEqualTo(fullStockDTO.getSumDescription());
    assertThat(stockEntity.getFullDescription()).isEqualTo(fullStockDTO.getFullDescription());
    assertThat(stockEntity.getTradingVenue()).isEqualTo(fullStockDTO.getTradingVenue());
    assertThat(stockEntity.getPurchaseDate()).isEqualTo(fullStockDTO.getPurchaseDate());
    assertThat(stockEntity.getPurchasePricePerPiece()).isEqualTo(fullStockDTO.getPurchasePricePerPiece());
    assertThat(stockEntity.getPurchasePriceTotal()).isEqualTo(fullStockDTO.getPurchasePriceTotal());
    assertThat(stockEntity.getCurrency()).isEqualTo(fullStockDTO.getCurrency());
    assertThat(stockEntity.getStockType()).isEqualTo(fullStockDTO.getStockType());
  }


  @Test
  @DisplayName("Should correctly map Stock entity to StockDTO (summary DTO)")
  void shouldMapStockToStockDTO() {

    // Arrange
    Stock stockEntity = Stock.builder()
        .name("Tesla Inc.")
        .stockIdentifier("TSLA")
        .amount(20.0) // Double
        .sumDescription("Electric vehicle maker")
        .fullDescription("Detailed description...")
        .purchaseDate(LocalDateTime.now())
        .currency(Currency.USD)
        .stockType(StockType.STOCK)
        .tradingVenue(TradingVenue.NASDAQ)
        .purchasePricePerPiece(180.0)
        .build();

    // Act
    StockDTO resultDTO = stockMapper.toDTO(stockEntity);

    // Assert
    assertThat(resultDTO).isNotNull();
    assertThat(resultDTO.getName()).isEqualTo(stockEntity.getName());
    assertThat(resultDTO.getStockIdentifier()).isEqualTo(stockEntity.getStockIdentifier());
    assertThat(resultDTO.getAmount()).isEqualTo(stockEntity.getAmount());
    assertThat(resultDTO.getDescription()).isEqualTo(stockEntity.getSumDescription());
  }

  @Test
  @DisplayName("Should handle null input by throwing NullPointerException")
  void shouldHandleNullInput() {
    assertThrows(NullPointerException.class, () -> stockMapper.toNewDTO(null));
    assertThrows(NullPointerException.class, () -> stockMapper.toEntity(null));
    assertThrows(NullPointerException.class, () -> stockMapper.toDTO(null));
  }
}