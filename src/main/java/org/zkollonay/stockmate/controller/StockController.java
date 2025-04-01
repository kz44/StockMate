package org.zkollonay.stockmate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zkollonay.stockmate.DTO.NewStockDTO;
import org.zkollonay.stockmate.DTO.StockDTO;
import org.zkollonay.stockmate.service.StockService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class StockController {

  private final StockService stockService;


  /**
   * Add new Stock with all details. NewStockDTO
   */
  @PostMapping()
  public ResponseEntity<NewStockDTO> addNewStock(@RequestBody @Valid NewStockDTO newStockDTO) {
    return ResponseEntity.ok(stockService.addNewStock(newStockDTO));
  }

  /**
   * Get all stocks with all details. List -> NewStockDTO
   */
  @GetMapping()
  public ResponseEntity<List<NewStockDTO>> getAllStocks() {
    return ResponseEntity.ok(stockService.getAllStocks());
  }

  /**
   * Get a summary of all stocks with less details. List -> StockDTO
   */
  @GetMapping("/all")
  public ResponseEntity<List<StockDTO>> getSumStocks() {
    return ResponseEntity.ok(stockService.getSumStocks());
  }

  /**
   * Get all stocks by stockIdentifier. List -> NewStockDTO
   */
  @GetMapping("/get/{stockIdentifier}")
  public ResponseEntity<List<NewStockDTO>> getStocksByStockIdentifier(@PathVariable String stockIdentifier) {
    return ResponseEntity.ok(stockService.getStockByStocksIdentifier(stockIdentifier));
  }

  /**
   * Get a summary of a 1 specified stock by stockIdentifier. StockDTO
   */
  @GetMapping("/all/{stockIdentifier}")
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
   * Modify stock by id. NewStockDTO
   */
  @PutMapping("/{stockID}")
  public ResponseEntity<NewStockDTO> updateStock(@RequestBody NewStockDTO newStockDTO, @PathVariable long stockID) {
    return ResponseEntity.ok(stockService.updateStockById(newStockDTO, stockID));
  }

  /**
   * Search in stocks LIKE (based on: name, description, stockIdentifier). List -> StockDTO
   */
  @GetMapping("/filter")
  public ResponseEntity<List<StockDTO>> searchStock(@RequestBody StockDTO filter) {
    return ResponseEntity.ok(stockService.filterStocks(filter));
  }
}
