package org.zkollonay.stockmate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zkollonay.stockmate.DTO.NewStockDTO;
import org.zkollonay.stockmate.DTO.StockDTO;
import org.zkollonay.stockmate.domain.Stock;
import org.zkollonay.stockmate.mapper.StockMapper;
import org.zkollonay.stockmate.repository.StockRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImp implements StockService {

  private final StockRepository stockRepository;
  private final StockMapper stockMapper;
  private final ExchangeRateServiceImp exchangeRateServiceImp;


  @Override
  public NewStockDTO addNewStock(NewStockDTO newStockDTO) {
    Stock stock = stockMapper.toEntity(newStockDTO);
    stockRepository.save(stock);
    return stockMapper.toNewDTO(stock);
  }

  @Override
  public List<NewStockDTO> getAllStocks() {
    return stockRepository.findAll()
        .stream()
        .map(stockMapper::toNewDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<StockDTO> getSumStocks() {
    List<Object[]> results = stockRepository.getStockSummary();

    return results.stream()
        .map(row -> new StockDTO(
            (String) row[1],  // name
            (String) row[0],  // stockIdentifier
            ((Number) row[2]).doubleValue(), // amount (összegzett érték)
            (String) row[3]   // description
        ))
        .toList();
  }

  @Override
  public List<NewStockDTO> getStockByStocksIdentifier(String stockIdentifier) {
    return stockRepository.getStocksByStockIdentifier(stockIdentifier).stream().map(stockMapper::toNewDTO).toList();
  }


  @Override
  public StockDTO getSumStockByStocksIdentifier(String stockIdentifier) {
    Object result = stockRepository.getStockSummaryByStockIdentifier(stockIdentifier);

    if (result == null) {
      throw new RuntimeException("No stock found with identifier: " + stockIdentifier);
    }

    Object[] row = (Object[]) result;
    return new StockDTO(
        (String) row[1],  // name
        (String) row[0],  // stockIdentifier
        ((Number) row[2]).doubleValue(), // amount (összegzett érték)
        (String) row[3]   // description
    );
  }

  @Override
  public void deleteStockById(long stockID) {
    stockRepository.deleteById(stockID);
  }

  @Override
  public NewStockDTO updateStockById(NewStockDTO newStockDTO, long stockID) {
    Stock oldStock = stockRepository.findById(stockID).orElseThrow(() -> new RuntimeException("Stock not found with ID: " + stockID));

    oldStock.setName(newStockDTO.getName());
    oldStock.setStockIdentifier(newStockDTO.getStockIdentifier());
    oldStock.setAmount(newStockDTO.getAmount());
    oldStock.setDescription(newStockDTO.getDescription());
    oldStock.setTradingVenue(newStockDTO.getTradingVenue());
    oldStock.setPurchaseDate(newStockDTO.getPurchaseDate());
    oldStock.setPurchasePricePerPiece(newStockDTO.getPurchasePricePerPiece());
    oldStock.setPurchasePriceTotal(newStockDTO.getPurchasePriceTotal());
    oldStock.setCurrency(newStockDTO.getCurrency());
    oldStock.setStockType(newStockDTO.getStockType());
    stockRepository.save(oldStock);

    return stockMapper.toNewDTO(oldStock);
  }

  @Override
  public List<StockDTO> filterStocks(StockDTO filter) {
    return stockRepository.findFilteredStocks(filter.getName(), filter.getStockIdentifier(), filter.getDescription()).stream().map(stockMapper::toDTO).toList();
  }

  @Override
  public StockDTO getMostValuableStock() {
    return null;
  }
}
