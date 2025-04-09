package org.zkollonay.stockmate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zkollonay.stockmate.DTO.InvestedDTO;
import org.zkollonay.stockmate.ENUM.Currency;
import org.zkollonay.stockmate.domain.Stock;
import org.zkollonay.stockmate.repository.StockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmountServiceImp implements AmountService {

  private final StockRepository stockRepository;
  private final ExchangeRateService exchangeRateService;

  @Override
  public InvestedDTO getTotalInvestmentAmountByCurrency(Currency currency) {
    List<Stock> stocks = stockRepository.findAllByCurrency(currency);
    double totalInvestmentAmount = 0;

    for (Stock stock : stocks) {
      totalInvestmentAmount += stock.getPurchasePriceTotal();
    }

    return InvestedDTO.builder()
        .currency(currency)
        .amountInHUF((exchangeRateService.convertToCurrency(totalInvestmentAmount, currency.toString(), "HUF")).getConvertedAmount())
        .build();
  }

  @Override
  public InvestedDTO getTotalInvestmentAmount() {
    InvestedDTO totalInUSD = getTotalInvestmentAmountByCurrency(Currency.USD);
    InvestedDTO totalInEUR = getTotalInvestmentAmountByCurrency(Currency.EUR);
    InvestedDTO totalInHUF = getTotalInvestmentAmountByCurrency(Currency.HUF);

    double totalInvestmentAmount = totalInUSD.getAmountInHUF() + totalInEUR.getAmountInHUF() + totalInHUF.getAmountInHUF();

    return InvestedDTO.builder()
        .amountInHUF(totalInvestmentAmount)
        .currency(Currency.HUF)
        .build();
  }
}
