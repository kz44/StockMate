package org.zkollonay.stockmate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zkollonay.stockmate.DTO.InvestedDTO;
import org.zkollonay.stockmate.ENUM.Currency;
import org.zkollonay.stockmate.service.AmountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/amount")
public class AmountController {

  private final AmountService amountService;

  @GetMapping("/total-investment-currency")
  public ResponseEntity<InvestedDTO> getTotalInvestmentAmountByCurrency(@RequestParam Currency currency) {
    return ResponseEntity.ok(amountService.getTotalInvestmentAmountByCurrency(currency));
  }

  @GetMapping("/total-investment")
  public ResponseEntity<InvestedDTO> getTotalInvestmentAmount() {
    return ResponseEntity.ok(amountService.getTotalInvestmentAmount());
  }


}
