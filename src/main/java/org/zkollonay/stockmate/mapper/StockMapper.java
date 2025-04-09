package org.zkollonay.stockmate.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.zkollonay.stockmate.DTO.NewStockDTO;
import org.zkollonay.stockmate.DTO.StockDTO;
import org.zkollonay.stockmate.domain.Stock;

@Component
@RequiredArgsConstructor
public class StockMapper {

  public NewStockDTO toNewDTO(Stock stock) {
    return NewStockDTO.builder()
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


  public Stock toEntity(NewStockDTO newStockDTO) {
    return Stock.builder()
        .name(newStockDTO.getName())
        .stockIdentifier(newStockDTO.getStockIdentifier())
        .amount(newStockDTO.getAmount())
        .sumDescription(newStockDTO.getSumDescription())
        .fullDescription(newStockDTO.getFullDescription())
        .tradingVenue(newStockDTO.getTradingVenue())
        .purchaseDate(newStockDTO.getPurchaseDate())
        .purchasePricePerPiece(newStockDTO.getPurchasePricePerPiece())
        .purchasePriceTotal(newStockDTO.getPurchasePriceTotal())
        .currency(newStockDTO.getCurrency())
        .stockType(newStockDTO.getStockType())
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

}
