package org.zkollonay.stockmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zkollonay.stockmate.domain.Stock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public interface StockRepository extends JpaRepository<Stock, Long> {

  // vagy interface, dto, tarolt eljaras?, kuldott ricsi
  @Query("SELECT s.stockIdentifier, MIN(s.name), SUM(s.amount), MIN(s.description) " +
      "FROM Stock s GROUP BY s.stockIdentifier")
  List<Object[]> getStockSummary();

  List<Stock> getStocksByStockIdentifier(String stockIdentifier);


  @Query("SELECT s.stockIdentifier, MIN(s.name), SUM(s.amount), MIN(s.description) " +
      "FROM Stock s WHERE s.stockIdentifier = :stockIdentifier GROUP BY s.stockIdentifier")
  Object getStockSummaryByStockIdentifier(@Param("stockIdentifier") String stockIdentifier);


  @Query("SELECT s FROM Stock s " +
      "WHERE (:description IS NULL OR s.description LIKE %:description%) " +
      "AND (:name IS NULL OR s.name LIKE %:name%) " +
      "AND (:stockIdentifier IS NULL OR s.stockIdentifier LIKE %:stockIdentifier%)")
  List<Stock> findFilteredStocks(
      @Param("name") String name,
      @Param("stockIdentifier") String stockIdentifier,
      @Param("description") String description
  );


  /**
   * Unfinished
   */
  @Query("SELECT s.stockIdentifier, MIN(s.name), SUM(s.purchasePriceTotal), MIN(s.description) " +
      "FROM Stock s WHERE s.stockIdentifier = :stockIdentifier GROUP BY s.stockIdentifier")
  Object getMostValuableStock();
}



