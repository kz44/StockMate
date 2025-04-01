package org.zkollonay.stockmate.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.zkollonay.stockmate.DTO.NewStockDTO;
import org.zkollonay.stockmate.DTO.StockDTO;

import java.util.List;

public interface StockService {

  NewStockDTO addNewStock(final NewStockDTO newStockDTO);

  List<NewStockDTO> getAllStocks();

  List<StockDTO> getSumStocks();

  List<NewStockDTO> getStockByStocksIdentifier(final String stockIdentifier);

  StockDTO getSumStockByStocksIdentifier(String stockIdentifier);

  void deleteStockById(@PathVariable long stockID);

  NewStockDTO updateStockById(final NewStockDTO newStockDTO, long stockID);

  List<StockDTO> filterStocks(StockDTO filter);
}
