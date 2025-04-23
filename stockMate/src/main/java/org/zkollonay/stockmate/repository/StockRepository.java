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

  /**
   * Retrieves a summary of stocks, grouping by stock identifier.
   *
   * @return A list of StockDTO objects representing the stock summary.
   */
  @Query("SELECT NEW org.zkollonay.stockmate.DTO.StockDTO(" +
      "MIN(s.id), MIN(s.name), s.stockIdentifier, SUM(s.amount), MIN(s.sumDescription)) " +
      "FROM Stock s GROUP BY s.stockIdentifier")
  List<StockDTO> getStockSummary();


  /**
   * Finds all stocks with the given stock identifier.
   *
   * @param stockIdentifier The identifier of the stock.
   * @return A list of Stock objects with the specified identifier.
   */
  List<Stock> findByStockIdentifier(String stockIdentifier);


  /**
   * Retrieves a summary of a specific stock based on its identifier.
   *
   * @param stockIdentifier The identifier of the stock.
   * @return An Optional containing the StockDTO representing the stock summary, or an empty Optional if not found.
   */
  @Query("SELECT NEW org.zkollonay.stockmate.DTO.StockDTO(" +
      "MIN(s.id), MIN(s.name), s.stockIdentifier, SUM(s.amount), MIN(s.sumDescription)) " +
      "FROM Stock s WHERE s.stockIdentifier = :stockIdentifier GROUP BY s.stockIdentifier")
  Optional<StockDTO> getStockSummaryByStockIdentifier(@Param("stockIdentifier") String stockIdentifier);


  /**
   * Finds stocks that match the given filter in name, summary description, or stock identifier.
   *
   * @param filter The filter string to search for.
   * @return A list of Stock objects that match the filter.
   */
  @Query("SELECT s FROM Stock s " +
      "WHERE (:filter IS NULL OR " +
      "LOWER(s.sumDescription) LIKE LOWER(CONCAT('%', :filter, '%')) " +
      "OR LOWER(s.name) LIKE LOWER(CONCAT('%', :filter, '%')) " +
      "OR LOWER(s.stockIdentifier) LIKE LOWER(CONCAT('%', :filter, '%')))")
  List<Stock> findFilteredStocks(@Param("filter") String filter);


  /**
   * Finds stocks purchased within the specified date range.
   *
   * @param startDate The starting date of the range (inclusive).
   * @param endDate   The ending date of the range (inclusive).
   * @return A list of Stock objects purchased within the given period.
   */
  @Query("SELECT s FROM Stock s WHERE s.purchaseDate BETWEEN :startDate AND :endDate")
  List<Stock> findByPurchaseDateBetween(LocalDateTime startDate, LocalDateTime endDate);

  /**
   * Retrieves the distinct full description of a stock based on its identifier.
   *
   * @param stockIdentifier The identifier of the stock.
   * @return An Optional containing the full description, or an empty Optional if not found.
   */
  @Query("SELECT DISTINCT s.fullDescription FROM Stock s WHERE s.stockIdentifier = :stockIdentifier")
  Optional<String> getFullDescription(String stockIdentifier);


  /**
   * Finds all stocks with the given currency.
   *
   * @param currency The currency of the stock.
   * @return A list of Stock objects with the specified currency.
   */
  List<Stock> findAllByCurrency(Currency currency);


  /**
   * Finds all stocks purchased in the given year.
   *
   * @param year The year of purchase.
   * @return A list of Stock objects purchased in the specified year.
   */
  @Query("SELECT s FROM Stock s WHERE EXTRACT(YEAR FROM s.purchaseDate) = :year")
  List<Stock> findByPurchaseYear(@Param("year") Integer year);
}



