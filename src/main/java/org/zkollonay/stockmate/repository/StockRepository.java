package org.zkollonay.stockmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zkollonay.stockmate.ENUM.Currency;
import org.zkollonay.stockmate.domain.Stock;

import java.time.LocalDateTime;
import java.util.List;


public interface StockRepository extends JpaRepository<Stock, Long> {

  // vagy interface, dto, tarolt eljaras?, kuldott ricsi
  @Query("SELECT s.stockIdentifier, MIN(s.name), SUM(s.amount), MIN(s.sumDescription) " +
      "FROM Stock s GROUP BY s.stockIdentifier")
  List<Object[]> getStockSummary();

  List<Stock> getStocksByStockIdentifier(String stockIdentifier);


  @Query("SELECT s.stockIdentifier, MIN(s.name), SUM(s.amount), MIN(s.sumDescription) " +
      "FROM Stock s WHERE s.stockIdentifier = :stockIdentifier GROUP BY s.stockIdentifier")
  Object getStockSummaryByStockIdentifier(@Param("stockIdentifier") String stockIdentifier);


  @Query("SELECT s FROM Stock s " +
      "WHERE (:filter IS NULL OR " +
      "LOWER(s.sumDescription) LIKE LOWER(CONCAT('%', :filter, '%')) " +
      "OR LOWER(s.name) LIKE LOWER(CONCAT('%', :filter, '%')) " +
      "OR LOWER(s.stockIdentifier) LIKE LOWER(CONCAT('%', :filter, '%')))")
  List<Stock> findFilteredStocks(@Param("filter") String filter);


  @Query("SELECT s FROM Stock s WHERE s.purchaseDate BETWEEN :startDate AND :endDate")
  List<Stock> findYears(LocalDateTime startDate, LocalDateTime endDate);


  @Query("SELECT DISTINCT s.fullDescription FROM Stock s WHERE s.stockIdentifier = :stockIdentifier")
  String getFullDescription(String stockIdentifier);


  List<Stock> findAllByCurrency(Currency currency);


  @Query("SELECT s FROM Stock s WHERE EXTRACT(YEAR FROM s.purchaseDate) = :year")
  List<Stock> findByPurchaseYear(@Param("year") Integer year);
}



