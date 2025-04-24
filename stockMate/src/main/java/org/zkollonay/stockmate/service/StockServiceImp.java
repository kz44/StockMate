package org.zkollonay.stockmate.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zkollonay.stockmate.DTO.FullStockDTO;
import org.zkollonay.stockmate.DTO.StockDTO;
import org.zkollonay.stockmate.domain.Stock;
import org.zkollonay.stockmate.mapper.StockMapper;
import org.zkollonay.stockmate.repository.StockRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockServiceImp implements StockService {

  private final StockRepository stockRepository;
  private final StockMapper stockMapper;


  /**
   * Adds a new stock to the system.
   *
   * @param fullStockDTO The DTO containing the details of the new stock.
   * @return The DTO representing the newly added stock.
   */
  @Override
  public FullStockDTO addNewStock(FullStockDTO fullStockDTO) {
    Stock stock = stockMapper.toEntity(fullStockDTO);
    stockRepository.save(stock);
    return stockMapper.toNewDTO(stock);
  }


  /**
   * Retrieves all stocks with pagination.
   *
   * @param pageable The pagination information.
   * @return A page of FullStockDTO objects representing the stocks.
   */
  @Override
  public Page<FullStockDTO> getAllStocks(Pageable pageable) {
    Page<Stock> stockPage = stockRepository.findAll(pageable);
    return stockPage.map(stockMapper::toNewDTO);
  }


  /**
   * Retrieves a summary of all stocks.
   *
   * @return A list of StockDTO objects representing the summary of stocks.
   */
  @Override
  public List<StockDTO> getSumStocks() {
    return stockRepository.getStockSummary();
  }


  /**
   * Retrieves all stocks with the given stock identifier.
   *
   * @param stockIdentifier The identifier of the stock.
   * @return A list of FullStockDTO objects with the specified identifier.
   */
  @Override
  public List<FullStockDTO> getStockByStocksIdentifier(String stockIdentifier) {
    return stockRepository.findByStockIdentifier(stockIdentifier).stream().map(stockMapper::toNewDTO).toList();
  }


  /**
   * Retrieves a summary of a specific stock based on its identifier.
   *
   * @param stockIdentifier The identifier of the stock.
   * @return A StockDTO representing the summary of the stock.
   * @throws EntityNotFoundException If no stock is found with the given identifier.
   */
  @Override
  public StockDTO getSumStockByStocksIdentifier(String stockIdentifier) {
    Optional<StockDTO> stockDTO = stockRepository.getStockSummaryByStockIdentifier(stockIdentifier);

    return stockDTO.orElseThrow(() -> new EntityNotFoundException(stockIdentifier));
  }


  /**
   * Deletes a stock by its ID.
   *
   * @param stockID The ID of the stock to delete.
   */
  public void deleteStockById(long stockID) {
    if (!stockRepository.existsById(stockID)) {
      throw new EntityNotFoundException("No stock found with ID: " + stockID);
    }
    stockRepository.deleteById(stockID);
  }


  /**
   * Updates an existing stock by its ID.
   *
   * @param fullStockDTO The DTO containing the updated stock details.
   * @param stockID      The ID of the stock to update.
   * @return The DTO representing the updated stock.
   * @throws EntityNotFoundException If no stock is found with the given ID.
   */
  @Override
  public FullStockDTO updateStockById(FullStockDTO fullStockDTO, long stockID) {
    Stock oldStock = stockRepository.findById(stockID)
        .orElseThrow(() -> new EntityNotFoundException("Stock not found with ID: " + stockID));

    stockMapper.updateStockFromFullStockDTO(fullStockDTO, oldStock);
    stockRepository.save(oldStock);
    return stockMapper.toNewDTO(oldStock);
  }

  /**
   * Retrieve stock with the given id.
   *
   * @param stockID The identifier of the stock.
   * @return a FullStockDTO objects with the given id.
   */
  @Override
  public FullStockDTO getStockById(final long stockID) {
    return stockRepository.findById(stockID)
        .map(stockMapper::toNewDTO)
        .orElseThrow(() -> new EntityNotFoundException("Stock not found with ID: " + stockID));
  }


  /**
   * Filters stocks based on a given filter string in name, summary description, or stock identifier.
   *
   * @param filter The filter string.
   * @return A list of StockDTO objects that match the filter.
   */
  @Override
  public List<StockDTO> filterStocks(String filter) {
    return stockRepository.findFilteredStocks(filter).stream().map(stockMapper::toDTO).toList();
  }


  /**
   * Retrieves stocks purchased within a specific date range.
   *
   * @param from The starting date of the range (inclusive).
   * @param to   The ending date of the range (inclusive).
   * @return A list of FullStockDTO objects purchased within the given period.
   */
  @Override
  public List<FullStockDTO> getStocksByYearFromTo(LocalDateTime from, LocalDateTime to) {
    return stockRepository.findByPurchaseDateBetween(from, to).stream().map(stockMapper::toNewDTO).toList();
  }


  /**
   * Retrieves the full description of a stock based on its identifier.
   *
   * @param stockIdentifier The identifier of the stock.
   * @return The full description of the stock.
   * @throws EntityNotFoundException If no full description is found for the given stock identifier.
   */
  @Override
  public String getFullDescriptionByStocksIdentifier(String stockIdentifier) {
    Optional<String> description = stockRepository.getFullDescription(stockIdentifier);

    return description.orElseThrow(() -> new EntityNotFoundException("Full description not found for stock identifier: " + stockIdentifier));
  }


  /**
   * Retrieves stocks purchased grouped by purchase year.
   *
   * @return A list of FullStockDTO objects purchased in the specified year.
   */
  @Override
  public Map<Integer, List<FullStockDTO>> getStocksByYear() {
    List<Stock> stocks = stockRepository.findAllWithPurchaseDate();

    return stocks.stream().filter(stock -> stock.getPurchaseDate() != null)
        .map(stockMapper::toNewDTO)
        .collect(Collectors.groupingBy(stock -> stock.getPurchaseDate().getYear()));
  }
}
