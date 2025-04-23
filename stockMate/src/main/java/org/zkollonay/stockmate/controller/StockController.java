package org.zkollonay.stockmate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zkollonay.stockmate.DTO.FullStockDTO;
import org.zkollonay.stockmate.DTO.StockDTO;
import org.zkollonay.stockmate.service.StockService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
@CrossOrigin(origins = "http://localhost:3000")
public class StockController {

  private final StockService stockService;


  /**
   * Adds a new stock.
   *
   * @param fullStockDTO The DTO containing the stock details.
   * @return ResponseEntity containing the FullStockDTO of the newly added stock.
   */
  @PostMapping()
  public ResponseEntity<FullStockDTO> addNewStock(@Valid @RequestBody FullStockDTO fullStockDTO) {
    return ResponseEntity.ok(stockService.addNewStock(fullStockDTO));
  }


  /**
   * Retrieves all stocks with pagination.
   *
   * @param pageable Pagination parameters.
   * @return ResponseEntity containing a Page of FullStockDTO objects.
   */
  @GetMapping()
  public ResponseEntity<Page<FullStockDTO>> getAllStocks(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
    return ResponseEntity.ok(stockService.getAllStocks(pageable));
  }


  /**
   * Retrieves a summary of all stocks.
   *
   * @return ResponseEntity containing a list of StockDTO objects.
   */
  @GetMapping("/summary")
  public ResponseEntity<List<StockDTO>> getSumStocks() {
    return ResponseEntity.ok(stockService.getSumStocks());
  }


  /**
   * Retrieves all stocks with a specific stock identifier.
   *
   * @param stockIdentifier The identifier of the stock.
   * @return ResponseEntity containing a list of FullStockDTO objects.
   */
  @GetMapping("/{stockIdentifier}")
  public ResponseEntity<List<FullStockDTO>> getStocksByStockIdentifier(@PathVariable String stockIdentifier) {
    return ResponseEntity.ok(stockService.getStockByStocksIdentifier(stockIdentifier));
  }


  /**
   * Retrieves a summary of a specific stock by its identifier.
   *
   * @param stockIdentifier The identifier of the stock.
   * @return ResponseEntity containing a StockDTO object.
   */
  @GetMapping("/summary/{stockIdentifier}")
  public ResponseEntity<StockDTO> getSumStockByStocksIdentifier(@PathVariable String stockIdentifier) {
    return ResponseEntity.ok(stockService.getSumStockByStocksIdentifier(stockIdentifier));
  }


  /**
   * Deletes a stock by its ID.
   *
   * @param stockID The ID of the stock to delete.
   * @return ResponseEntity with no content.
   */
  @DeleteMapping("/{stockID}")
  public ResponseEntity<Void> deleteStock(@PathVariable long stockID) {
    System.out.println("Törlés hívása: ID = " + stockID);
    stockService.deleteStockById(stockID);
    return ResponseEntity.noContent().build();
  }


  /**
   * Updates an existing stock by its ID.
   *
   * @param fullStockDTO The DTO containing the updated stock details.
   * @param stockID      The ID of the stock to update.
   * @return ResponseEntity containing the FullStockDTO of the updated stock.
   */
  @PutMapping("/{stockID}")
  public ResponseEntity<FullStockDTO> updateStock(@Valid @RequestBody FullStockDTO fullStockDTO, @PathVariable long stockID) {
    return ResponseEntity.ok(stockService.updateStockById(fullStockDTO, stockID));
  }


  /**
   * Searches for stocks based on a filter.
   *
   * @param filter The filter string to search for in name, summary description, or stock identifier.
   * @return ResponseEntity containing a list of StockDTO objects that match the filter.
   */
  @GetMapping("/filter")
  public ResponseEntity<List<StockDTO>> searchStock(@RequestParam String filter) {
    return ResponseEntity.ok(stockService.filterStocks(filter));
  }


  /**
   * Retrieves stocks purchased within a specific date range.
   *
   * @param fromDate The starting date of the range.
   * @param toDate   The ending date of the range.
   * @return ResponseEntity containing a list of FullStockDTO objects.
   */
  @GetMapping("/by-purchase-date")
  public ResponseEntity<List<FullStockDTO>> getStocksByYearFromTo(@RequestParam LocalDateTime fromDate,
                                                                  @RequestParam LocalDateTime toDate) {
    return ResponseEntity.ok(stockService.getStocksByYearFromTo(fromDate, toDate));
  }


  /**
   * Retrieves the full description of a stock by its identifier.
   *
   * @param stockIdentifier The identifier of the stock.
   * @return ResponseEntity containing the full description as a String.
   */
  @GetMapping("/full-description")
  public ResponseEntity<String> getFullDescriptionByStockIdentifier(@RequestParam("stockIdentifier") String stockIdentifier) {
    return ResponseEntity.ok(stockService.getFullDescriptionByStocksIdentifier(stockIdentifier));
  }


  /**
   * Retrieves stocks purchased in a specific year.
   *
   * @param year The year of purchase.
   * @return ResponseEntity containing a list of FullStockDTO objects.
   */
  @GetMapping("/by-purchase-year")
  public ResponseEntity<List<FullStockDTO>> getStocksByYear(@RequestParam int year) {
    return ResponseEntity.ok(stockService.getStocksByYear(year));
  }
}
