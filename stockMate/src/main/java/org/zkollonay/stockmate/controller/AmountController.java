package org.zkollonay.stockmate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zkollonay.stockmate.DTO.InvestedDTO;
import org.zkollonay.stockmate.ENUM.Currency;
import org.zkollonay.stockmate.service.AmountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/amount")
@CrossOrigin(origins = "http://localhost:3000")
public class AmountController {

  private final AmountService amountService;


  /**
   * Retrieves the total investment amount for a specific currency.
   *
   * @param currency The currency to calculate the total investment for.
   * @return ResponseEntity containing the InvestedDTO with the total investment amount.
   */
  @GetMapping("/total-investment-currency")
  public ResponseEntity<InvestedDTO> getTotalInvestmentAmountByCurrency(@RequestParam Currency currency) {
    return ResponseEntity.ok(amountService.getTotalInvestmentAmountByCurrency(currency));
  }


  /**
   * Retrieves the total investment amount across all currencies, converted to HUF.
   *
   * @return ResponseEntity containing the InvestedDTO with the total investment amount in HUF.
   */
  @GetMapping("/total-investment")
  public ResponseEntity<InvestedDTO> getTotalInvestmentAmount() {
    return ResponseEntity.ok(amountService.getTotalInvestmentAmount());
  }

}
