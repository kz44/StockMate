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

    const navigate = useNavigate();

    useEffect(() => {
        if (!searchMode) {
            fetchStocks(page);
        }
    }, [page, searchMode]);

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

    const handleClearSearch = () => {
        setSearchTerm('');
        setSearchMode(false);
        fetchStocks(0);
    };

    const handleSummaryClick = () => {
        navigate('/stocks/summary');
    };

    const handleDeleteStock = async (id) => {
        if (window.confirm(`Biztosan törölni szeretnéd a(z) ${stocks.find(stock => stock.stockIdentifier === stock.id)?.name} nevű részvényt?`)) {
            try {
                const response = await fetch(`http://localhost:8080/stocks/${id}`, {
                    method: 'DELETE',
                });
                if (!response.ok) {
                    throw new Error(`Error deleting stock: ${response.status}`);
                }
                // Sikeres törlés esetén frissítjük a listát
                if (!searchMode) {
                    fetchStocks(page);
                } else {
                    fetchFilteredStocks(); // Ha keresési módban voltunk, újra lefuttatjuk a szűrést
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
                        placeholder="Keresés név, azonosító vagy leírás alapján..."
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
                    Részvény Összefoglalóm
                </button>
            </div>

            {loading && <p className="loading-error">Betöltés...</p>}
            {error && <p className="loading-error">Hiba: {error}</p>}

            {!loading && !error && (
                <>
                    <ul className="stock-list">
                    {stocks.map(stock => {
    return (
    <li key={stock.id} className="stock-item">
        <div className="stock-details">
            {searchMode ? (
                <>
                    <p>Név: <span>{stock.name}</span></p>
                    <p>Azonosító: <span>{stock.stockIdentifier}</span></p>
                    <p>Mennyiség: <span>{stock.amount}</span></p>
                    <p>Leírás: <span>{stock.description}</span></p>
                </>
            ) : (
                <>
                    <p>Név: <span>{stock.name}</span></p>
                    <p>Azonosító: <span>{stock.stockIdentifier}</span></p>
                    <p>Mennyiség: <span>{stock.amount}</span></p>
                    <p>Rövid leírás: <span>{stock.sumDescription}</span></p>
                    <p>Teljes leírás: <span>{stock.fullDescription}</span></p>
                    <p>Tőzsde: <span>{stock.tradingVenue}</span></p>
                    <p>Vásárlás dátuma: <span>{stock.purchaseDate || 'Nincs adat'}</span></p>
                    <p>Vételár / darab: <span>{stock.purchasePricePerPiece} {stock.currency}</span></p>
                    <p>Összes vételár: <span>{stock.purchasePriceTotal ? `${stock.purchasePriceTotal} ${stock.currency}` : 'Nincs adat'}</span></p>
                    <p>Pénznem: <span>{stock.currency}</span></p>
                    <p>Típus: <span>{stock.stockType}</span></p>
                    {/* Törlés gomb megjelenítése csak ha nem vagyunk keresési módban */}
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
                                Előző
                            </button>
                            <span className="pagination-info"> {page + 1} / {totalPages} </span>
                            <button
                                onClick={() => setPage((prev) => Math.min(prev + 1, totalPages - 1))}
                                disabled={page === totalPages - 1}
                                className="pagination-button"
                            >
                                Következő
                            </button>
                        </div>
                    )}
                </>
            )}
        </div>
    );
};

export default GetAllStocks;