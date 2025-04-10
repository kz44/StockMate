package org.zkollonay.stockmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zkollonay.stockmate.DTO.StockDTO;
import org.zkollonay.stockmate.ENUM.Currency;
import org.zkollonay.stockmate.domain.Stock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface StockRepository extends JpaRepository<Stock, Long> {

  @Query("SELECT NEW org.zkollonay.stockmate.DTO.StockDTO(" +
      "s.stockIdentifier, MIN(s.name), SUM(s.amount), MIN(s.sumDescription)) " +
      "FROM Stock s GROUP BY s.stockIdentifier")
  List<StockDTO> getStockSummary();

  List<Stock> findByStockIdentifier(String stockIdentifier);

  @Query("SELECT NEW org.zkollonay.stockmate.DTO.StockDTO(" +
      "s.stockIdentifier, MIN(s.name), SUM(s.amount), MIN(s.sumDescription)) " +
      "FROM Stock s WHERE s.stockIdentifier = :stockIdentifier GROUP BY s.stockIdentifier")
  Optional<StockDTO> getStockSummaryByStockIdentifier(@Param("stockIdentifier") String stockIdentifier);

  @Query("SELECT s FROM Stock s " +
      "WHERE (:filter IS NULL OR " +
      "LOWER(s.sumDescription) LIKE LOWER(CONCAT('%', :filter, '%')) " +
      "OR LOWER(s.name) LIKE LOWER(CONCAT('%', :filter, '%')) " +
      "OR LOWER(s.stockIdentifier) LIKE LOWER(CONCAT('%', :filter, '%')))")
  List<Stock> findFilteredStocks(@Param("filter") String filter);

  @Query("SELECT s FROM Stock s WHERE s.purchaseDate BETWEEN :startDate AND :endDate")
  List<Stock> findByPurchaseDateBetween(LocalDateTime startDate, LocalDateTime endDate);

  @Query("SELECT DISTINCT s.fullDescription FROM Stock s WHERE s.stockIdentifier = :stockIdentifier")
  Optional<String> getFullDescription(String stockIdentifier);

  List<Stock> findAllByCurrency(Currency currency);

  @Query("SELECT s FROM Stock s WHERE EXTRACT(YEAR FROM s.purchaseDate) = :year")
  List<Stock> findByPurchaseYear(@Param("year") Integer year);
}



