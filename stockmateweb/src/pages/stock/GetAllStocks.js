import React, { useEffect, useState } from 'react';
import "./GetAllStocks.css";
import { useNavigate } from 'react-router-dom';

const GetAllStocks = () => {
    const [stocks, setStocks] = useState([]); // State to store stocks
    const [page, setPage] = useState(0); // State to store the current page number
    const [totalPages, setTotalPages] = useState(1); // State to store total number of pages
    const [loading, setLoading] = useState(true); // State to handle loading state
    const [error, setError] = useState(null); // State to handle error message
    const [searchTerm, setSearchTerm] = useState(''); // State to handle search input
    const [searchMode, setSearchMode] = useState(false); // State to toggle search mode

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

    return (
        <div className="stock-list-container">
            <div className="stock-list-header">
                <div className="header-side">
                    <input
                        type="text"
                        placeholder="Search by name, identifier, or description..."
                        value={searchTerm}
                        onChange={(e) => setSearchTerm(e.target.value)}
                        className="stock-search-input"
                    />
                    <button onClick={fetchFilteredStocks} className="stock-search-button">Search</button>
                    {searchMode && (
                        <button onClick={handleClearSearch} className="stock-search-clear">X</button>
                    )}
                </div>

                <h2 className="stock-list-title">My Stocks</h2>
                <button className="stock-summary-button" onClick={handleSummaryClick}>
                    Stock Summary
                </button>
            </div>

            {loading && <p className="loading-error">Loading...</p>}
            {error && <p className="loading-error">Error: {error}</p>}

            {!loading && !error && (
                <>
                    <ul className="stock-list">
                        {stocks.map(stock => {
                            return (
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
                                                {/* Show delete button only if not in search mode */}
                                                <button
                                                    className="delete-stock-button"
                                                    onClick={() => handleDeleteStock(stock.id)}
                                                >
                                                    X
                                                </button>
                                            </>
                                        )}
                                    </div>
                                </li>
                            );
                        })}
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
        </div>
    );
};

export default GetAllStocks;
