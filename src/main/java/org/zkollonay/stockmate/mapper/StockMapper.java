package org.zkollonay.stockmate.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.zkollonay.stockmate.DTO.FullStockDTO;
import org.zkollonay.stockmate.DTO.StockDTO;
import org.zkollonay.stockmate.domain.Stock;

@Component
@RequiredArgsConstructor
public class StockMapper {

  public FullStockDTO toNewDTO(Stock stock) {
    return FullStockDTO.builder()
        .name(stock.getName())
        .stockIdentifier(stock.getStockIdentifier())
        .amount(stock.getAmount())
        .sumDescription(stock.getSumDescription())
        .fullDescription(stock.getFullDescription())
        .tradingVenue(stock.getTradingVenue())
        .purchaseDate(stock.getPurchaseDate())
        .purchasePricePerPiece(stock.getPurchasePricePerPiece())
        .purchasePriceTotal(stock.getPurchasePriceTotal())
        .currency(stock.getCurrency())
        .stockType(stock.getStockType())
        .build();
  }


  public Stock toEntity(FullStockDTO fullStockDTO) {
    return Stock.builder()
        .name(fullStockDTO.getName())
        .stockIdentifier(fullStockDTO.getStockIdentifier())
        .amount(fullStockDTO.getAmount())
        .sumDescription(fullStockDTO.getSumDescription())
        .fullDescription(fullStockDTO.getFullDescription())
        .tradingVenue(fullStockDTO.getTradingVenue())
        .purchaseDate(fullStockDTO.getPurchaseDate())
        .purchasePricePerPiece(fullStockDTO.getPurchasePricePerPiece())
        .purchasePriceTotal(fullStockDTO.getPurchasePriceTotal())
        .currency(fullStockDTO.getCurrency())
        .stockType(fullStockDTO.getStockType())
        .build();
  }


  public StockDTO toDTO(Stock stock) {
    return StockDTO.builder()
        .name(stock.getName())
        .stockIdentifier(stock.getStockIdentifier())
        .amount(stock.getAmount())
        .description(stock.getSumDescription())
        .build();
  }


  public void updateStockFromFullStockDTO(FullStockDTO fullStockDTO, Stock oldStock){
    oldStock.setName(fullStockDTO.getName());
    oldStock.setStockIdentifier(fullStockDTO.getStockIdentifier());
    oldStock.setAmount(fullStockDTO.getAmount());
    oldStock.setSumDescription(fullStockDTO.getSumDescription());
    oldStock.setFullDescription(fullStockDTO.getFullDescription());
    oldStock.setTradingVenue(fullStockDTO.getTradingVenue());
    oldStock.setPurchaseDate(fullStockDTO.getPurchaseDate());
    oldStock.setPurchasePricePerPiece(fullStockDTO.getPurchasePricePerPiece());
    oldStock.setCurrency(fullStockDTO.getCurrency());
    oldStock.setStockType(fullStockDTO.getStockType());
  }

}
