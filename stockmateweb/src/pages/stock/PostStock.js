import { useState } from "react";
import "./PostStock.css"; 
import Form from "react-bootstrap/Form"; 
import Button from "react-bootstrap/Button"; 
import Alert from 'react-bootstrap/Alert'; 

// Define Enum values
const TRADING_VENUES = ["XETRA", "NASDAQ", "AEX", "LSE"]; 
const CURRENCIES = ["USD", "EUR", "HUF"]; 
const STOCK_TYPES = ["ETF", "STOCK"]; 

const PostStock = () => {

  const initialFormData = {
    name: "",
    stockIdentifier: "",
    amount: "",
    sumDescription: "",
    fullDescription: "",
    tradingVenue: "",
    purchaseDate: "",
    purchasePricePerPiece: "",
    currency: "",
    stockType: ""
  };

  const [formData, setFormData] = useState(initialFormData);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [submitStatus, setSubmitStatus] = useState({ type: '', message: '' });

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (event) => {
    event.preventDefault(); 
    setIsSubmitting(true);
    setSubmitStatus({ type: '', message: '' });

    const payload = {
        ...formData,
    };

    console.log("Sending data:", payload); 

    try {
      // Post request to backend
      const response = await fetch('http://localhost:8080/stocks', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload),
      });

      if (response.ok) {
        const result = await response.json();
        console.log("Stock added:", result);
      
        // Generate response text basend on the given stock details
        const stockDetails = `
          Stock added successfully!
          • Name: ${result.name}
          • Identifier: ${result.stockIdentifier}
          • Amount: ${result.amount}
          • Summary: ${result.sumDescription}
          • Full description: ${result.fullDescription}
          • Venue: ${result.tradingVenue}
          • Date: ${new Date(result.purchaseDate).toLocaleString()}
          • Price per piece: ${result.purchasePricePerPiece} ${result.currency}
          • Type: ${result.stockType}
        `;
      
        setSubmitStatus({ type: 'success', message: stockDetails });
        setFormData(initialFormData);
      } else {
        const errorText = await response.text(); 
        console.error("Error response:", response.status, errorText);
        setSubmitStatus({ type: 'danger', message: `Error adding stock: ${response.status} ${errorText || response.statusText}` });
      }
    } catch (error) {
      console.error("Network or other error:", error);
      setSubmitStatus({ type: 'danger', message: `Network error: ${error.message}` });
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <>
      <div className="center-form">
        <h1>Részvény hozzáadása</h1>

        {submitStatus.message && (
            <Alert variant={submitStatus.type}>
                {submitStatus.message}
            </Alert>
        )}

        <Form onSubmit={handleSubmit}> 
          <Form.Group controlId="formBasicName" className="mb-3">
             <Form.Label>Részvény neve</Form.Label>
            <Form.Control
              type="text"
              name="name"
              placeholder="Apple Inc."
              value={formData.name}
              onChange={handleInputChange}
              required
            />
          </Form.Group>

          <Form.Group controlId="formBasicStockIdentifier" className="mb-3">
            <Form.Label>Részvény azonosító (Ticker/ISIN)</Form.Label>
            <Form.Control
              type="text"
              name="stockIdentifier"
              placeholder="AAPL"
              value={formData.stockIdentifier}
              onChange={handleInputChange}
              required
            />
          </Form.Group>

          <Form.Group controlId="formBasicAmount" className="mb-3">
            <Form.Label>Megvásárolt mennyiség</Form.Label>
            <Form.Control
              type="number"
              name="amount"
              placeholder="10"
              value={formData.amount}
              onChange={handleInputChange}
              required
              min="0"  // validation, must be positive
              step="any" 
            />
          </Form.Group>
      
          <Form.Group controlId="formBasicSumDescription" className="mb-3">
            <Form.Label>Rövid leírás</Form.Label>
            <Form.Control
              type="text"
              name="sumDescription"
              placeholder="Amerikai techvállalat részvénye"
              value={formData.sumDescription}
              onChange={handleInputChange}
              required
            />
          </Form.Group>

          <Form.Group controlId="formBasicFullDescription" className="mb-3">
             <Form.Label>Teljes leírás</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              name="fullDescription"
              placeholder="Apple Inc. az egyik legnagyobb tech cég, fő termékei: iPhone, Mac, stb."
              value={formData.fullDescription}
              onChange={handleInputChange}
              required
            />
          </Form.Group>

          <Form.Group controlId="formBasicTradingVenue" className="mb-3">
             <Form.Label>Kereskedési helyszín</Form.Label>
            <Form.Select
              name="tradingVenue"
              value={formData.tradingVenue}
              onChange={handleInputChange}
              required
            >
              <option value="">Kereskedési helyszín választása...</option>
              {TRADING_VENUES.map(venue => (
                <option key={venue} value={venue}>{venue}</option>
              ))}
            </Form.Select>
          </Form.Group>

           <Form.Group controlId="formBasicPurchaseDate" className="mb-3">
             <Form.Label>Vásárlás dátuma</Form.Label>
             <Form.Control
               type="datetime-local"
               name="purchaseDate"
               value={formData.purchaseDate}
               onChange={handleInputChange}
               required
             />
           </Form.Group>

           <Form.Group controlId="formBasicPurchasePricePerPiece" className="mb-3">
            <Form.Label>Vásárlás ár darabonként</Form.Label>
            <Form.Control
              type="number"
              name="purchasePricePerPiece"
              placeholder="Darabonkénti vásárlási ár"
              value={formData.purchasePricePerPiece}
              onChange={handleInputChange}
              required
              min="0"
              step="any"
            />
           </Form.Group>

          <Form.Group controlId="formBasicCurrency" className="mb-3">
            <Form.Label>Deviza</Form.Label>
            <Form.Select
              name="currency"
              value={formData.currency}
              onChange={handleInputChange}
              required
            >
              <option value="">Deviza választása...</option>
              {CURRENCIES.map(currency => (
                <option key={currency} value={currency}>{currency}</option>
              ))}
            </Form.Select>
          </Form.Group>

          <Form.Group controlId="formBasicStockType" className="mb-3">
            <Form.Label>Részvény típusa</Form.Label>
            <Form.Select
              name="stockType"
              value={formData.stockType}
              onChange={handleInputChange}
              required
            >
              <option value="">Részvény típus választása...</option>
              {STOCK_TYPES.map(type => (
                <option key={type} value={type}>{type}</option>
              ))}
            </Form.Select>
          </Form.Group>

          <Button
             variant="primary"
             type="submit"
             className="w-100"
             disabled={isSubmitting} 
          >
            {isSubmitting ? 'Submitting...' : 'Részvény hozzáadása'}
          </Button>
        </Form>
      </div>
    </>
  );
};

export default PostStock;