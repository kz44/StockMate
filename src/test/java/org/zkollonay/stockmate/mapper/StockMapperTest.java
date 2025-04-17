package org.zkollonay.stockmate.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.zkollonay.stockmate.DTO.FullStockDTO;
import org.zkollonay.stockmate.DTO.StockDTO;
import org.zkollonay.stockmate.ENUM.Currency;
import org.zkollonay.stockmate.ENUM.StockType;
import org.zkollonay.stockmate.ENUM.TradingVenue;
import org.zkollonay.stockmate.domain.Stock;

import java.math.BigDecimal;
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


   // Creates a sample entity with predefined values for testing purposes. -> Apple
  private Stock createSampleStock() {
    return Stock.builder()
        .name("Apple Inc.")
        .stockIdentifier("AAPL")
        .amount(BigDecimal.valueOf(2.0))
        .sumDescription("Tech giant shares")
        .fullDescription("Detailed description about Apple shares purchase.")
        .tradingVenue(TradingVenue.NASDAQ)
        .purchaseDate(LocalDateTime.of(2024, 5, 15, 10, 30, 0))
        .purchasePricePerPiece(BigDecimal.valueOf(175.50))
        .purchasePriceTotal(BigDecimal.valueOf(351.00))
        .currency(Currency.USD)
        .stockType(StockType.STOCK)
        .build();
  }

  // Creates a sample entity with predefined values for testing purposes. -> Google
  private Stock createSampleStock2() {
    return Stock.builder()
        .name("Google LLC")
        .stockIdentifier("GOOGL")
        .amount(BigDecimal.valueOf(5.0))
        .sumDescription("Search engine stock")
        .fullDescription("Information about Google stock purchase.")
        .tradingVenue(TradingVenue.NASDAQ)
        .purchaseDate(LocalDateTime.of(2024, 4, 10, 9, 0, 0))
        .purchasePricePerPiece(BigDecimal.valueOf(150.00))
        .purchasePriceTotal(BigDecimal.valueOf(750.00)) // 5.0 * 150.00
        .currency(Currency.USD)
        .stockType(StockType.STOCK)
        .build();
  }

  // Creates a sample FullStockDTO with predefined values for testing purposes. -> Google
  private FullStockDTO createSampleFullStockDTO2() {
    return FullStockDTO.builder()
        .name("Google LLC")
        .stockIdentifier("GOOGL")
        .amount(BigDecimal.valueOf(5.0))
        .sumDescription("Search engine stock")
        .fullDescription("Information about Google stock purchase.")
        .tradingVenue(TradingVenue.NASDAQ)
        .purchaseDate(LocalDateTime.of(2024, 4, 10, 9, 0, 0))
        .purchasePricePerPiece(BigDecimal.valueOf(150.00))
        .purchasePriceTotal(BigDecimal.valueOf(750.00))
        .currency(Currency.USD)
        .stockType(StockType.STOCK)
        .build();
  }

  // Creates a sample StockDTO with predefined values for testing purposes. -> Google
  private StockDTO createSampleStockDTO2() {
    return StockDTO.builder()
        .name("Google LLC")
        .stockIdentifier("GOOGL")
        .amount(BigDecimal.valueOf(5.0))
        .description("Search engine stock")
        .build();
  }

  // stockMapper.toNewDTO
  @Test
  @DisplayName("Should correctly map Stock entity to FullStockDTO")
  void shouldMapStockToFullStockDTO() {
    Stock stockEntity = createSampleStock2();
    FullStockDTO fullStockDTO = createSampleFullStockDTO2();
    FullStockDTO resultDTO = stockMapper.toNewDTO(stockEntity);
    assertThat(resultDTO).usingRecursiveComparison().isEqualTo(fullStockDTO);
  }

  // stockMapper.toEntity
  @Test
  @DisplayName("Should correctly map FullStockDTO to Stock entity")
  void shouldMapFullStockDTOToStock() {
    FullStockDTO fullStockDTO = createSampleFullStockDTO2();
    Stock stockEntity = createSampleStock2();
    Stock resultEntity = stockMapper.toEntity(fullStockDTO);
    assertThat(resultEntity).usingRecursiveComparison().isEqualTo(stockEntity);
  }

  // stockMapper.toDTO
  @Test
  @DisplayName("Should correctly map Stock entity to StockDTO")
  void shouldMapStockToStockDTO() {
    Stock stockEntity = createSampleStock2();
    StockDTO stockDTO = createSampleStockDTO2();
    StockDTO resultDTO = stockMapper.toDTO(stockEntity);
    assertThat(resultDTO).usingRecursiveComparison().isEqualTo(stockDTO);
  }

  // stockMapper.updateStockFromFullStockDTO
  @Test
  @DisplayName("Should correctly update Stock entity from FullStockDTO")
  void shouldUpdateStockFromFullStockDTO() {
    FullStockDTO fullStockDTO = createSampleFullStockDTO2();
    Stock oldStock = createSampleStock();
    stockMapper.updateStockFromFullStockDTO(fullStockDTO, oldStock);
    assertThat(oldStock).usingRecursiveComparison()
        .ignoringFields("id")
        .isEqualTo(fullStockDTO);
  }

  // stockMapper methods with null values
  @Test
  @DisplayName("Should handle null input by throwing NullPointerException")
  void shouldHandleNullInput() {
    assertThrows(NullPointerException.class, () -> stockMapper.toNewDTO(null));
    assertThrows(NullPointerException.class, () -> stockMapper.toEntity(null));
    assertThrows(NullPointerException.class, () -> stockMapper.toDTO(null));
    assertThrows(NullPointerException.class, () -> stockMapper.updateStockFromFullStockDTO(null, null));
  }
}