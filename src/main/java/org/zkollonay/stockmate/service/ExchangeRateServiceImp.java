package org.zkollonay.stockmate.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImp {

  private final RestTemplate restTemplate;
  private final ObjectMapper objectMapper;

  private final String apiUrl = "https://v6.exchangerate-api.com/v6/832fbb78b6d27cf4385b78d0/latest/USD"; //USD adatokat szolgal ki

  public double getExchangeRate(String fromCurrency, String toCurrency) {
    try {
      String response = restTemplate.getForObject(apiUrl, String.class);  // -> lekerdezi az api valaszat
      JsonNode root = objectMapper.readTree(response); // json feldolgozasa amit az api visszaadott
      return root.path("conversion_rates").path(toCurrency).asDouble(); // -> kiolvassa az adott penznem arfolzamat
    } catch (Exception e) {
      throw new RuntimeException("Hiba történt az API elérésekor", e); // hiba eseten kivetelt dob
    }
  }

  public double convertToHUF(double amount, String currency) {
    if ("HUF".equalsIgnoreCase(currency)) {
      return amount; // Ha már HUF, nem kell váltani
    }
    double rate = getExchangeRate(currency, "HUF"); // --lekerei az arfolyamot a fentebbi metodussal
    return amount * rate; // atvaltja az osszeget forintra
  }
}
