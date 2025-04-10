package org.zkollonay.stockmate.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.zkollonay.stockmate.DTO.FullStockDTO;
import org.zkollonay.stockmate.DTO.StockDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface StockService {

  FullStockDTO addNewStock(final FullStockDTO fullStockDTO);

  Page<FullStockDTO> getAllStocks(Pageable pageable);

  List<StockDTO> getSumStocks();

  List<FullStockDTO> getStockByStocksIdentifier(final String stockIdentifier);

  StockDTO getSumStockByStocksIdentifier(String stockIdentifier);

  void deleteStockById(@PathVariable long stockID);

  FullStockDTO updateStockById(final FullStockDTO fullStockDTO, long stockID);

  List<StockDTO> filterStocks(String filter);

  List<FullStockDTO> getStocksByYearFromTo(LocalDateTime from, LocalDateTime to);

  String getFullDescriptionByStocksIdentifier(String stockIdentifier);

  List<FullStockDTO> getStocksByYear(Integer year);
}
