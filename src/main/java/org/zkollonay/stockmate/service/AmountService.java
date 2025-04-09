package org.zkollonay.stockmate.service;

import org.zkollonay.stockmate.DTO.InvestedDTO;
import org.zkollonay.stockmate.ENUM.Currency;

public interface AmountService {
  InvestedDTO getTotalInvestmentAmountByCurrency(Currency currency);

  InvestedDTO getTotalInvestmentAmount();
}
