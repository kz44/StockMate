import { useEffect, useState } from "react";
import { Button, Alert, Form } from "react-bootstrap";
import "./InvestAmount.css";

const InvestAmount = () => {
  const [totalInvestmentWithCurrency, setTotalInvestmentWithCurrency] = useState(null);
  const [totalInvestmentHUFData, setTotalInvestmentHUFData] = useState(null);
  const [currency, setCurrency] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleCurrencyChange = (e) => {
    setCurrency(e.target.value);
  };

  // Fetch investment total in HUF
  const fetchTotalInvestmentInHUF = async () => {
    setIsLoading(true);
    setError(null);

    try {
      const response = await fetch("http://localhost:8080/amount/total-investment");

      if (!response.ok) {
        throw new Error("Failed to fetch total investment in HUF");
      }

      const data = await response.json();
      setTotalInvestmentHUFData(data);
      setTotalInvestmentWithCurrency(null); // Clear the other currency data
    } catch (err) {
      setError(err.message);
    } finally {
      setIsLoading(false);
    }
  };

  // Fetch investment total in selected foreign currency
  const fetchTotalInvestmentByCurrency = async (selectedCurrency) => {
    setIsLoading(true);
    setError(null);

    try {
      const url = `http://localhost:8080/amount/total-investment-currency?currency=${selectedCurrency}`;
      const response = await fetch(url);

      if (!response.ok) {
        throw new Error(`Failed to fetch investment data in ${selectedCurrency}`);
      }

      const data = await response.json();
      setTotalInvestmentWithCurrency(data);
      setTotalInvestmentHUFData(null); // Clear the HUF data
    } catch (err) {
      setError(err.message);
    } finally {
      setIsLoading(false);
    }
  };

  // No need to fetch anything on mount
  useEffect(() => {}, []);

  return (
    <div className="investment-container">
      <h1>Összes Befektetés</h1>

      <Button
        onClick={fetchTotalInvestmentInHUF}
        disabled={isLoading}
        className="mb-3"
      >
        {isLoading ? "Betöltés..." : "Minden vagyon forintban"}
      </Button>

      <Form>
        <Form.Group>
          <Form.Label>Válasszon devizát a lekérdezéshez</Form.Label>
          <Form.Control as="select" value={currency} onChange={handleCurrencyChange}>
            <option value="">Válasszon devizát</option>
            <option value="USD">USD</option>
            <option value="EUR">EUR</option>
          </Form.Control>
        </Form.Group>

        <Button
          onClick={() => fetchTotalInvestmentByCurrency(currency)}
          disabled={isLoading || !currency}
        >
          {isLoading ? "Betöltés..." : "Befektetés lekérése devizában"}
        </Button>
      </Form>

      {error && <Alert variant="danger">{error}</Alert>}

      {totalInvestmentHUFData && (
        <div className="investment-info mt-3">
          <h3>Összes Befektetés (HUF): {totalInvestmentHUFData.amountInHUF} {totalInvestmentHUFData.currency}</h3>
        </div>
      )}

      {totalInvestmentWithCurrency && (
        <div className="investment-info mt-3">
          <h3>Összes Befektetés ({totalInvestmentWithCurrency.currency}): {totalInvestmentWithCurrency.amount} {totalInvestmentWithCurrency.currency}</h3>
        </div>
      )}
    </div>
  );
};

export default InvestAmount;
