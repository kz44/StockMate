import { Route, Routes } from 'react-router-dom';
import './App.css';
import Header from "./pages/header/Header"
import NoMatch from './pages/noMatch/NoMatch';
import Dashboard from './pages/dashboard/Dashboard';
import PostStock from './pages/stock/PostStock';
import GetAllStocks from './pages/stock/GetAllStocks';
import GetSumStocks from './pages/stock/GetSumStocks';
import ExchangeRateConverter from './pages/exchange/ExchangeRateConverter';
import InvestAmount from './pages/amount/InvestAmount';

function App() {
  return (
   <>
    <Header />
    <Routes>
      <Route path='/' element={<Dashboard/>} />
      <Route path='/stocks/new' element={<PostStock/>} />
      <Route path='/stocks' element={<GetAllStocks/>} />
      <Route path='/stocks/summary' element={<GetSumStocks/>} />
      <Route path='*' element={<NoMatch/>} />
      <Route path='/exchanges' element={<ExchangeRateConverter/>} />
      <Route path='/amount' element={<InvestAmount/>} />
    </Routes>
   </>
  );
}

export default App;
