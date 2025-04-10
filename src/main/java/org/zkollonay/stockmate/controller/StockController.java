package org.zkollonay.stockmate.controller;

import lombok.RequiredArgsConstructor;
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
public class StockController {

  private final StockService stockService;


  /**
   * Add new Stock with all details. FullStockDTO
   */
  @PostMapping()
  public ResponseEntity<FullStockDTO> addNewStock(@RequestBody FullStockDTO fullStockDTO) {
    return ResponseEntity.ok(stockService.addNewStock(fullStockDTO));
  }

  /**
   * Get all stocks with all details. List -> FullStockDTO
   */
  @GetMapping()
  public ResponseEntity<List<FullStockDTO>> getAllStocks() {
    return ResponseEntity.ok(stockService.getAllStocks());
  }

  /**
   * Get a summary of all stocks with less details. List -> StockDTO
   */
  @GetMapping("/summary")
  public ResponseEntity<List<StockDTO>> getSumStocks() {
    return ResponseEntity.ok(stockService.getSumStocks());
  }

  /**
   * Get all stocks by stockIdentifier. List -> FullStockDTO
   */
  @GetMapping("/{stockIdentifier}")
  public ResponseEntity<List<FullStockDTO>> getStocksByStockIdentifier(@PathVariable String stockIdentifier) {
    return ResponseEntity.ok(stockService.getStockByStocksIdentifier(stockIdentifier));
  }

  /**
   * Get a summary of a 1 specified stock by stockIdentifier. StockDTO
   */
  @GetMapping("/summary/{stockIdentifier}")
  public ResponseEntity<StockDTO> getSumStockByStocksIdentifier(@PathVariable String stockIdentifier) {
    return ResponseEntity.ok(stockService.getSumStockByStocksIdentifier(stockIdentifier));
  }

  /**
   * Delete stock by id.
   */
  @DeleteMapping("/{stockID}")
  public ResponseEntity<String> deleteStock(@PathVariable long stockID) {
    stockService.deleteStockById(stockID);
    return ResponseEntity.noContent().build();
  }

  /**
   * Modify stock by id. FullStockDTO
   */
  @PutMapping("/{stockID}")
  public ResponseEntity<FullStockDTO> updateStock(@RequestBody FullStockDTO fullStockDTO, @PathVariable long stockID) {
    return ResponseEntity.ok(stockService.updateStockById(fullStockDTO, stockID));
  }

  /**
   * Search in stocks LIKE (based on: name, description, stockIdentifier). List -> StockDTO
   */
  @GetMapping("/filter")
  public ResponseEntity<List<StockDTO>> searchStock(@RequestParam String filter) {
    return ResponseEntity.ok(stockService.filterStocks(filter));
  }

  /**
   * Get purchased list of stocks between the given date. List -> FullStockDTO
   */
  @GetMapping("/by-purchase-date")
  public ResponseEntity<List<FullStockDTO>> getStocksByYearFromTo(@RequestParam LocalDateTime fromDate,
                                                                  @RequestParam LocalDateTime toDate) {
    return ResponseEntity.ok(stockService.getStocksByYearFromTo(fromDate, toDate));
  }

  /**
   * Get full description by stock identifier limit 1 -> String
   */
  @GetMapping("/full-description")
  public ResponseEntity<String> getFullDescriptionByStockIdentifier(@RequestParam("stockIdentifier") String stockIdentifier) throws Exception {
    return ResponseEntity.ok(stockService.getFullDescriptionByStocksIdentifier(stockIdentifier));
  }

  /**
   * Get purchased list of stocks by year. List -> FullStockDTO
   */
  @GetMapping("/by-purchase-year")
  public ResponseEntity<List<FullStockDTO>> getStocksByYear(@RequestParam int year) {
    return ResponseEntity.ok(stockService.getStocksByYear(year));
  }
}
