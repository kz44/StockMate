import React, { useState } from 'react';
import './ExchangeRateConverter.css'; // Optional styling

const ExchangeRateConverter = () => {
  // State variables for form inputs and API results
  const [fromCurrency, setFromCurrency] = useState('HUF');
  const [toCurrency, setToCurrency] = useState('EUR');
  const [amount, setAmount] = useState(1);
  const [result, setResult] = useState(null);
  const [error, setError] = useState(null);

  // Fetch exchange rate without conversion
  const fetchRate = async () => {
    setError(null);
    setResult(null);

    try {
      const response = await fetch(
        `http://localhost:8080/exchanges/rate?fromCurrency=${fromCurrency}&toCurrency=${toCurrency}`
      );

      if (!response.ok) throw new Error('Nem sikerült lekérni az árfolyamot.');

      const data = await response.json();
      setResult(data);
    } catch (err) {
      setError(err.message);
    }
  };

  // Convert amount using backend service
  const convertCurrency = async () => {
    setError(null);
    setResult(null);

    try {
      const response = await fetch(
        `http://localhost:8080/exchanges/convertTo?amount=${amount}&fromCurrency=${fromCurrency}&toCurrency=${toCurrency}`
      );

      if (!response.ok) throw new Error('Nem sikerült átváltani az összeget.');

      const data = await response.json();
      setResult(data);
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="exchange-container">
      <h2>Valuta átváltó</h2>

      <div className="exchange-form">
        {/* Input for source currency */}
        <label>Forrás valuta:</label>
        <input
          type="text"
          value={fromCurrency}
          onChange={(e) => setFromCurrency(e.target.value.toUpperCase())}
        />

        {/* Input for target currency */}
        <label>Cél valuta:</label>
        <input
          type="text"
          value={toCurrency}
          onChange={(e) => setToCurrency(e.target.value.toUpperCase())}
        />

        {/* Input for amount to convert */}
        <label>Összeg (csak átváltásnál):</label>
        <input
          type="number"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
        />

        {/* Action buttons */}
        <div className="exchange-buttons">
          <button onClick={fetchRate}>Árfolyam lekérése</button>
          <button onClick={convertCurrency}>Átváltás</button>
        </div>
      </div>

      {/* Error message */}
      {error && <p className="error-message">❌ {error}</p>}

      {/* Result output */}
      {result && (
        <div className="exchange-result">
          <p>
            <strong>{result.originalAmount}</strong> {result.fromCurrency} ={' '}
            <strong>{result.convertedAmount}</strong> {result.toCurrency}
          </p>
          <p>
            Árfolyam: <strong>{result.exchangeRate}</strong>
          </p>
        </div>
      )}
    </div>
  );
};

export default ExchangeRateConverter;
