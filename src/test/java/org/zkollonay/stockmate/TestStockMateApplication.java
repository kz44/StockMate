package org.zkollonay.stockmate;

import org.springframework.boot.SpringApplication;

public class TestStockMateApplication {

  public static void main(String[] args) {
    SpringApplication.from(StockMateApplication::main).with(TestcontainersConfiguration.class).run(args);
  }

}
