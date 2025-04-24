import Navbar from "react-bootstrap/Navbar";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import { Link } from "react-router-dom";
import './Header.css'; 

// Main header component with navigation bar
const Header = () => {
  return (
    <>
      {/* Bootstrap Navbar with primary background and dark text variant */}
      <Navbar className="main-navbar">
        <Container>
          {/* Brand title linking to the homepage */}
          <Navbar.Brand as={Link} to="/">
            <strong>StockMate</strong>
          </Navbar.Brand>

          {/* Navigation links aligned to the right */}
          <Nav className="ms-auto">
            {/* Link to add a new stock */}
            <Nav.Link as={Link} to="/stocks/new">Részvény hozzáadása</Nav.Link>

            {/* Link to view all stocks */}
            <Nav.Link as={Link} to="/stocks">Részvényeim</Nav.Link>

            {/* Link to investment amount summary */}
            <Nav.Link as={Link} to="/amount">Befektetett vagyon</Nav.Link>

            {/* Link to currency exchange page */}
            <Nav.Link as={Link} to="/exchanges">Valuta</Nav.Link>
          </Nav>
        </Container>
      </Navbar>
    </>
  );
};

export default Header;
