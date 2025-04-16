package org.zkollonay.stockmate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zkollonay.stockmate.DTO.InvestedDTO;
import org.zkollonay.stockmate.ENUM.Currency;
import org.zkollonay.stockmate.domain.Stock;
import org.zkollonay.stockmate.repository.StockRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AmountServiceImp implements AmountService {

  private final StockRepository stockRepository;
  private final ExchangeRateService exchangeRateService;

  @Override
  public InvestedDTO getTotalInvestmentAmountByCurrency(Currency currency) {
    List<Stock> stocks = stockRepository.findAllByCurrency(currency);
    BigDecimal totalInvestmentAmount = BigDecimal.ZERO;

    for (Stock stock : stocks) {
      totalInvestmentAmount = totalInvestmentAmount.add(stock.getPurchasePriceTotal());
    }

    BigDecimal roundedAmount = totalInvestmentAmount.setScale(2, RoundingMode.HALF_UP);

    return InvestedDTO.builder()
        .currency(currency)
        .amount(totalInvestmentAmount)
        .amountInHUF((exchangeRateService.convertToCurrency(roundedAmount, currency.toString(), "HUF")).getConvertedAmount())
        .build();
  }

  @Override
  public InvestedDTO getTotalInvestmentAmount() {
    InvestedDTO totalInUSD = getTotalInvestmentAmountByCurrency(Currency.USD);
    InvestedDTO totalInEUR = getTotalInvestmentAmountByCurrency(Currency.EUR);
    InvestedDTO totalInHUF = getTotalInvestmentAmountByCurrency(Currency.HUF);

    BigDecimal totalInvestmentAmount = totalInUSD.getAmountInHUF()
        .add(totalInEUR.getAmountInHUF())
        .add(totalInHUF.getAmountInHUF());

    return InvestedDTO.builder()
        .amountInHUF(totalInvestmentAmount)
        .currency(Currency.HUF)
        .build();
  }
}
