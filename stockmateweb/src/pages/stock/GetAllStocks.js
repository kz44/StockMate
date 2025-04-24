import React, { useEffect, useState } from 'react';
import "./GetAllStocks.css";
import { useNavigate } from 'react-router-dom';

const GetAllStocks = () => {
    const [stocks, setStocks] = useState([]); 
    const [page, setPage] = useState(0); 
    const [totalPages, setTotalPages] = useState(1); 
    const [loading, setLoading] = useState(true); 
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState(''); 
    const [searchMode, setSearchMode] = useState(false); 
    const [stockToEdit, setStockToEdit] = useState(null); 
    const [isEditing, setIsEditing] = useState(false); 

    const navigate = useNavigate();

    // Fetch stocks when page or searchMode changes
    useEffect(() => {
        if (!searchMode) {
            fetchStocks(page);
        }
    }, [page, searchMode]);

    // Function to fetch stocks with pagination
    const fetchStocks = async (pageNumber) => {
        setLoading(true);
        setError(null);
        try {
            const response = await fetch(`http://localhost:8080/stocks?page=${pageNumber}&size=10&sort=name`);
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
            const data = await response.json();
            setStocks(data.content);
            setTotalPages(data.totalPages);
        } catch (err) {
            setError(err.message);
            setStocks([]);
            setTotalPages(1);
        } finally {
            setLoading(false);
        }
    };

    // Function to fetch filtered stocks based on search term
    const fetchFilteredStocks = async () => {
        setLoading(true);
        setError(null);
        setSearchMode(true);
        try {
            const response = await fetch(`http://localhost:8080/stocks/filter?filter=${searchTerm}`);
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
            const data = await response.json();
            setStocks(data);
            setTotalPages(1);
            setPage(0);
        } catch (err) {
            setError(err.message);
            setStocks([]);
        } finally {
            setLoading(false);
        }
    };

    // Function to clear search and reset the state to default
    const handleClearSearch = () => {
        setSearchTerm('');
        setSearchMode(false);
        fetchStocks(0);
    };

    // Navigate to stock summary page
    const handleSummaryClick = () => {
        navigate('/stocks/summary');
    };

    // Handle stock deletion after confirmation
    const handleDeleteStock = async (id) => {
        if (window.confirm(`Are you sure you want to delete the stock named ${stocks.find(stock => stock.stockIdentifier === stock.id)?.name}?`)) {
            try {
                const response = await fetch(`http://localhost:8080/stocks/${id}`, {
                    method: 'DELETE',
                });
                if (!response.ok) {
                    throw new Error(`Error deleting stock: ${response.status}`);
                }
                // Refresh the list after successful deletion
                if (!searchMode) {
                    fetchStocks(page);
                } else {
                    fetchFilteredStocks(); // Re-run the search if we were in search mode
                }
            } catch (error) {
                setError(error.message);
            }
        }
    };

    // Fetch stock by ID for editing
    const handleUpdateStock = async (stock) => {
        try {
            const response = await fetch(`http://localhost:8080/stocks/id/${stock.id}`);
            if (!response.ok) {
                throw new Error(`Error fetching stock details: ${response.status}`);
            }
            const stockData = await response.json();
            setStockToEdit(stockData); // Store the stock data to edit
            setIsEditing(true); // Show the form
        } catch (error) {
            setError(error.message);
        }
    };

    // Handle stock update submission
    const handleSubmitEdit = async (e) => {
        e.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/stocks/${stockToEdit.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(stockToEdit), // Send updated stock data
            });

            if (!response.ok) {
                throw new Error(`Error updating stock: ${response.status}`);
            }

            // Refresh the list and close the edit form
            setIsEditing(false);
            if (!searchMode) {
                fetchStocks(page);
            } else {
                fetchFilteredStocks();
            }
        } catch (error) {
            setError(error.message);
        }
    };

    // Cancel editing and close the form
    const handleCancelEdit = () => {
        setIsEditing(false);
        setStockToEdit(null); // Reset the stock data
    };

    return (
        <div className="stock-list-container">
            <div className="stock-list-header">
                <div className="header-side">
                    <input
                        type="text"
                        placeholder="Keresés név, azonosító, vagy leírás alapján..."
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        className="stock-search-input"
                    />
                    <button onClick={fetchFilteredStocks} className="stock-search-button">Keresés</button>
                    {searchMode && (
                        <button onClick={handleClearSearch} className="stock-search-clear">X</button>
                    )}
                </div>

                <h2 className="stock-list-title">Részvényeim</h2>
                <button className="stock-summary-button" onClick={handleSummaryClick}>
                    Részvényeim összesítve
                </button>
            </div>

            {loading && <p className="loading-error">Loading...</p>}
            {error && <p className="loading-error">Error: {error}</p>}

            {!loading && !error && !isEditing && (
                <>
                    <ul className="stock-list">
                        {stocks.map(stock => (
                            <li key={stock.id} className="stock-item">
                                <div className="stock-details">
                                    {searchMode ? (
                                        <>
                                            <p>Name: <span>{stock.name}</span></p>
                                            <p>Identifier: <span>{stock.stockIdentifier}</span></p>
                                            <p>Amount: <span>{stock.amount}</span></p>
                                            <p>Description: <span>{stock.description}</span></p>
                                        </>
                                    ) : (
                                        <>
                                            <p>Name: <span>{stock.name}</span></p>
                                            <p>Identifier: <span>{stock.stockIdentifier}</span></p>
                                            <p>Amount: <span>{stock.amount}</span></p>
                                            <p>Short Description: <span>{stock.sumDescription}</span></p>
                                            <p>Full Description: <span>{stock.fullDescription}</span></p>
                                            <p>Trading Venue: <span>{stock.tradingVenue}</span></p>
                                            <p>Purchase Date: <span>{stock.purchaseDate || 'No data'}</span></p>
                                            <p>Purchase Price per Piece: <span>{stock.purchasePricePerPiece} {stock.currency}</span></p>
                                            <p>Total Purchase Price: <span>{stock.purchasePriceTotal ? `${stock.purchasePriceTotal} ${stock.currency}` : 'No data'}</span></p>
                                            <p>Currency: <span>{stock.currency}</span></p>
                                            <p>Type: <span>{stock.stockType}</span></p>
                                            <button
                                                className="delete-stock-button"
                                                onClick={() => handleDeleteStock(stock.id)}
                                            >
                                                X
                                            </button>
                                            <button
                                                className="modify-stock-button"
                                                onClick={() => handleUpdateStock(stock)}
                                            >
                                                Módosítás
                                            </button>
                                        </>
                                    )}
                                </div>
                            </li>
                        ))}
                    </ul>

                    {!searchMode && (
                        <div className="pagination-container">
                            <button
                                onClick={() => setPage((prev) => Math.max(prev - 1, 0))}
                                disabled={page === 0}
                                className="pagination-button"
                            >
                                Previous
                            </button>
                            <span className="pagination-info"> {page + 1} / {totalPages} </span>
                            <button
                                onClick={() => setPage((prev) => Math.min(prev + 1, totalPages - 1))}
                                disabled={page === totalPages - 1}
                                className="pagination-button"
                            >
                                Next
                            </button>
                        </div>
                    )}
                </>
            )}

    {isEditing && stockToEdit && ( // Only show the form if editing and stockToEdit exists
    <div className="stock-edit-form">
        <h2>Részvény módosítása</h2>
        <form onSubmit={handleSubmitEdit} className="stock-form">
            
            {/* Stock name input */}
            <label>Név:</label>
            <input
                type="text"
                value={stockToEdit.name || ''}
                onChange={(e) => setStockToEdit({ ...stockToEdit, name: e.target.value })}
            />
            
            {/* Stock identifier input */}
            <label>Tőzsdei azonosító:</label>
            <input
                type="text"
                value={stockToEdit.stockIdentifier || ''}
                onChange={(e) => setStockToEdit({ ...stockToEdit, stockIdentifier: e.target.value })}
            />
              
            {/* Amount of stock */}
            <label>Mennyiség:</label>
            <input
                type="number"
                value={stockToEdit.amount || ''}
                onChange={(e) => setStockToEdit({ ...stockToEdit, amount: e.target.value })}
            />

            {/* Short description */}
            <label>Rövid leírás:</label>
            <input
                type="text"
                value={stockToEdit.sumDescription || ''}
                onChange={(e) => setStockToEdit({ ...stockToEdit, sumDescription: e.target.value })}
            />

            {/* Full description */}
            <label>Teljes leírás:</label>
            <textarea
                value={stockToEdit.fullDescription || ''}
                onChange={(e) => setStockToEdit({ ...stockToEdit, fullDescription: e.target.value })}
            />

            {/* Trading venue */}
            <label>Kereskedési helyszín:</label>
            <input
                type="text"
                value={stockToEdit.tradingVenue || ''}
                onChange={(e) => setStockToEdit({ ...stockToEdit, tradingVenue: e.target.value })}
            />

            {/* Purchase date */}
            <label>Vásárlás dátuma:</label>
            <input
                type="date"
                value={stockToEdit.purchaseDate || ''}
                onChange={(e) => setStockToEdit({ ...stockToEdit, purchaseDate: e.target.value })}
            />

            {/* Price per piece */}
            <label>Részvény darabonkénti ára:</label>
            <input
                type="number"
                step="0.01"
                value={stockToEdit.purchasePricePerPiece || ''}
                onChange={(e) => setStockToEdit({ ...stockToEdit, purchasePricePerPiece: e.target.value })}
            />

            {/* Total purchase price */}
            <label>Teljes vásárlási ár:</label>
            <input
                type="number"
                step="0.01"
                value={stockToEdit.purchasePriceTotal || ''}
                onChange={(e) => setStockToEdit({ ...stockToEdit, purchasePriceTotal: e.target.value })}
            />

            {/* Currency used */}
            <label>Pénznem:</label>
            <input
                type="text"
                value={stockToEdit.currency || ''}
                onChange={(e) => setStockToEdit({ ...stockToEdit, currency: e.target.value })}
            />

            {/* Stock type */}
            <label>Részvény típusa:</label>
            <input
                type="text"
                value={stockToEdit.stockType || ''}
                onChange={(e) => setStockToEdit({ ...stockToEdit, stockType: e.target.value })}
            />

            {/* Save and Cancel buttons */}
            <div className="form-buttons">
                <button type="submit">Mentés</button>
                <button type="button" onClick={handleCancelEdit}>Mégse</button>
            </div>
        </form>
    </div>)}
    </div>
    );
};

export default GetAllStocks;
