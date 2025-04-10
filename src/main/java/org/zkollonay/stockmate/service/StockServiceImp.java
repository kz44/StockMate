package org.zkollonay.stockmate.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zkollonay.stockmate.DTO.FullStockDTO;
import org.zkollonay.stockmate.DTO.StockDTO;
import org.zkollonay.stockmate.domain.Stock;
import org.zkollonay.stockmate.mapper.StockMapper;
import org.zkollonay.stockmate.repository.StockRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockServiceImp implements StockService {

  private final StockRepository stockRepository;
  private final StockMapper stockMapper;


  @Override
  public FullStockDTO addNewStock(FullStockDTO fullStockDTO) {
    Stock stock = stockMapper.toEntity(fullStockDTO);
    stockRepository.save(stock);
    return stockMapper.toNewDTO(stock);
  }

  @Override
  public List<FullStockDTO> getAllStocks() {
    return stockRepository.findAll().stream().map(stockMapper::toNewDTO).toList();
  }

  @Override
  public List<StockDTO> getSumStocks() {
    return stockRepository.getStockSummary();
  }

  @Override
  public List<FullStockDTO> getStockByStocksIdentifier(String stockIdentifier) {
    return stockRepository.findByStockIdentifier(stockIdentifier).stream().map(stockMapper::toNewDTO).toList();
  }


  @Override
  public StockDTO getSumStockByStocksIdentifier(String stockIdentifier) {
    Optional<StockDTO> stockDTO = stockRepository.getStockSummaryByStockIdentifier(stockIdentifier);

    return stockDTO.orElseThrow(() -> new EntityNotFoundException(stockIdentifier));
  }

  @Override
  public void deleteStockById(long stockID) {
    stockRepository.deleteById(stockID);
  }

  @Override
  public FullStockDTO updateStockById(FullStockDTO fullStockDTO, long stockID) {
    Stock oldStock = stockRepository.findById(stockID)
        .orElseThrow(() -> new EntityNotFoundException("Stock not found with ID: " + stockID));

    oldStock.setName(fullStockDTO.getName());
    oldStock.setStockIdentifier(fullStockDTO.getStockIdentifier());
    oldStock.setAmount(fullStockDTO.getAmount());
    oldStock.setSumDescription(fullStockDTO.getSumDescription());
    oldStock.setFullDescription(fullStockDTO.getFullDescription());
    oldStock.setTradingVenue(fullStockDTO.getTradingVenue());
    oldStock.setPurchaseDate(fullStockDTO.getPurchaseDate());
    oldStock.setPurchasePricePerPiece(fullStockDTO.getPurchasePricePerPiece());
    oldStock.setPurchasePriceTotal(fullStockDTO.getPurchasePriceTotal());
    oldStock.setCurrency(fullStockDTO.getCurrency());
    oldStock.setStockType(fullStockDTO.getStockType());
    stockRepository.save(oldStock);

    return stockMapper.toNewDTO(oldStock);
  }

  @Override
  public List<StockDTO> filterStocks(String filter) {
    return stockRepository.findFilteredStocks(filter).stream().map(stockMapper::toDTO).toList();
  }

  @Override
  public List<FullStockDTO> getStocksByYearFromTo(LocalDateTime from, LocalDateTime to) {
    return stockRepository.findByPurchaseDateBetween(from, to).stream().map(stockMapper::toNewDTO).toList();
  }

  @Override
  public String getFullDescriptionByStocksIdentifier(String stockIdentifier) throws Exception {
    Optional<String> description = stockRepository.getFullDescription(stockIdentifier);

    return description.orElseThrow(() -> new EntityNotFoundException("Full description not found for stock identifier: " + stockIdentifier));
  }

  @Override
  public List<FullStockDTO> getStocksByYear(Integer year) {
    return stockRepository.findByPurchaseYear(year).stream().map(stockMapper::toNewDTO).toList();
  }
}
