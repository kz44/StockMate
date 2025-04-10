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
public class StockController {

  private final StockService stockService;

  @PostMapping()
  public ResponseEntity<FullStockDTO> addNewStock(@Valid @RequestBody FullStockDTO fullStockDTO) {
    return ResponseEntity.ok(stockService.addNewStock(fullStockDTO));
  }

  @GetMapping()
  public ResponseEntity<Page<FullStockDTO>> getAllStocks(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
    return ResponseEntity.ok(stockService.getAllStocks(pageable));
  }

  @GetMapping("/summary")
  public ResponseEntity<List<StockDTO>> getSumStocks() {
    return ResponseEntity.ok(stockService.getSumStocks());
  }

  @GetMapping("/{stockIdentifier}")
  public ResponseEntity<List<FullStockDTO>> getStocksByStockIdentifier(@PathVariable String stockIdentifier) {
    return ResponseEntity.ok(stockService.getStockByStocksIdentifier(stockIdentifier));
  }

  @GetMapping("/summary/{stockIdentifier}")
  public ResponseEntity<StockDTO> getSumStockByStocksIdentifier(@PathVariable String stockIdentifier) {
    return ResponseEntity.ok(stockService.getSumStockByStocksIdentifier(stockIdentifier));
  }

  @DeleteMapping("/{stockID}")
  public ResponseEntity<String> deleteStock(@PathVariable long stockID) {
    stockService.deleteStockById(stockID);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{stockID}")
  public ResponseEntity<FullStockDTO> updateStock(@Valid @RequestBody FullStockDTO fullStockDTO, @PathVariable long stockID) {
    return ResponseEntity.ok(stockService.updateStockById(fullStockDTO, stockID));
  }

  @GetMapping("/filter")
  public ResponseEntity<List<StockDTO>> searchStock(@RequestParam String filter) {
    return ResponseEntity.ok(stockService.filterStocks(filter));
  }

  @GetMapping("/by-purchase-date")
  public ResponseEntity<List<FullStockDTO>> getStocksByYearFromTo(@RequestParam LocalDateTime fromDate,
                                                                  @RequestParam LocalDateTime toDate) {
    return ResponseEntity.ok(stockService.getStocksByYearFromTo(fromDate, toDate));
  }

  @GetMapping("/full-description")
  public ResponseEntity<String> getFullDescriptionByStockIdentifier(@RequestParam("stockIdentifier") String stockIdentifier) {
    return ResponseEntity.ok(stockService.getFullDescriptionByStocksIdentifier(stockIdentifier));
  }

  @GetMapping("/by-purchase-year")
  public ResponseEntity<List<FullStockDTO>> getStocksByYear(@RequestParam int year) {
    return ResponseEntity.ok(stockService.getStocksByYear(year));
  }
}
