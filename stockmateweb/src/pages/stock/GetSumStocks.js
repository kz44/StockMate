import React, { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import "./GetSumStocks.css";

const GetSumStocks = () => {
  const [stockSummary, setStockSummary] = useState([]);
  const [stocksByYear, setStocksByYear] = useState({});
  const [showByYear, setShowByYear] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [fullDescriptions, setFullDescriptions] = useState({});

  const navigate = useNavigate();

  useEffect(() => {
    const fetchStockSummary = async () => {
      try {
        const response = await fetch("http://localhost:8080/stocks/summary");
        if (!response.ok) {
          throw new Error(`An error occurred during the request: ${response.status}`);
        }
        const data = await response.json();
        setStockSummary(data);
      } catch (err) {
        setError("An error occurred while retrieving the data.");
        console.error("An error occurred while retrieving the data:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchStockSummary();
  }, []);

  const fetchStocksByYear = async () => {
    if (showByYear) {
      setShowByYear(false);
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/stocks/by-purchase-year");
      if (!response.ok) {
        throw new Error(`Failed to fetch stocks by year: ${response.status}`);
      }
      const data = await response.json();
      setStocksByYear(data);
      setShowByYear(true);
    } catch (err) {
      setError("Nem sikerült lekérni az évekre bontott adatokat.");
      console.error("Error fetching stocks by year:", err);
    }
  };

  const fetchFullDescription = async (stockIdentifier) => {
    try {
      if (fullDescriptions[stockIdentifier]) {
        setFullDescriptions((prevDescriptions) => {
          const updatedDescriptions = { ...prevDescriptions };
          delete updatedDescriptions[stockIdentifier];
          return updatedDescriptions;
        });
      } else {
        const url = `http://localhost:8080/stocks/full-description?stockIdentifier=${stockIdentifier}`;
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error(`Failed to fetch full description: ${response.status}`);
        }
        const fullDescription = await response.text();
        setFullDescriptions((prevDescriptions) => ({
          ...prevDescriptions,
          [stockIdentifier]: fullDescription,
        }));
      }
    } catch (err) {
      console.error("Error fetching full description:", err);
    }
  };

  if (loading) return <p>Loading stock summary...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  return (
    <div className="stock-summary-container">
      <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
        <button onClick={() => navigate('/stocks')} className="back-button">
          ← Vissza a részvényeimhez
        </button>
        <button onClick={fetchStocksByYear} className="year-button">
          📅 Éves bontás
        </button>
      </div>

      <h2 className="stock-summary-title">Részvényeim összesítve</h2>

      <table className="stock-summary-table">
        <thead>
          <tr>
            <th>Név</th>
            <th>Azonosító</th>
            <th>Mennyiség</th>
            <th>Rövid leírás</th>
          </tr>
        </thead>
        <tbody>
          {stockSummary.map((stock, index) => (
            <tr key={index}>
              <td>{stock.name}</td>
              <td>{stock.stockIdentifier}</td>
              <td>{stock.amount}</td>
              <td>
                {stock.description}
                <button
                  onClick={() => fetchFullDescription(stock.stockIdentifier)}
                  className="show-full-description-btn"
                >
                  {fullDescriptions[stock.stockIdentifier] ? "Mutass kevesebbet" : "Mutass többet"}
                </button>
                {fullDescriptions[stock.stockIdentifier] && (
                  <div className="full-description">
                    <p>{fullDescriptions[stock.stockIdentifier]}</p>
                  </div>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {showByYear && (
        <div className="year-section">
          <h3>Részvények vásárlási év szerint</h3>
          {Object.entries(stocksByYear).map(([year, stocks]) => (
            <div key={year} className="year-block">
              <h4>{year}</h4>
              <table className="stock-summary-table">
                <thead>
                  <tr>
                    <th>Név</th>
                    <th>Azonosító</th>
                    <th>Mennyiség</th>
                    <th>Leírás</th>
                  </tr>
                </thead>
                <tbody>
                  {stocks.map((stock, index) => (
                    <tr key={index}>
                      <td>{stock.name}</td>
                      <td>{stock.stockIdentifier}</td>
                      <td>{stock.amount}</td>
                      <td>{stock.description}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default GetSumStocks;
