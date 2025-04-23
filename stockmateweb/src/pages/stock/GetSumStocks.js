import React, { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom'; // Import useNavigate for navigation
import "./GetSumStocks.css"; // Import the CSS file

const GetSumStocks = () => {
  const [stockSummary, setStockSummary] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const navigate = useNavigate(); // Initialize the navigate function

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

  if (loading) return <p>Loading stock summary...</p>;
  if (error) return <p style={{ color: "red" }}>{error}</p>;

  return (
    <div className="stock-summary-container">

      {/* Back button to navigate to '/stocks' */}
      <button onClick={() => navigate('/stocks')} className="back-button">
        ← Vissza a részvényeimhez
      </button>

      <h2 className="stock-summary-title">Stock Summary</h2>

      <table className="stock-summary-table">
        <thead>
          <tr>
            <th>Name</th>
            <th>Identifier</th>
            <th>Amount</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          {stockSummary.map((stock, index) => (
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
  );
};

export default GetSumStocks;