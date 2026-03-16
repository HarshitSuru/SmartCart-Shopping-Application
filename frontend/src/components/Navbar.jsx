import React, { useState } from 'react';
import { Link, NavLink } from 'react-router-dom';
import { useCart } from '../context/CartContext';

const Navbar = () => {
  const [menuOpen, setMenuOpen] = useState(false);
  const { itemCount, setCartOpen } = useCart();

  const closeMenu = () => setMenuOpen(false);

  return (
    <header className="navbar-shell">
      <nav className="navbar">
        <Link to="/" className="brand-mark">
          Nova Cart
        </Link>
        <button
          className="menu-toggle"
          type="button"
          aria-label="Toggle navigation"
          aria-expanded={menuOpen}
          onClick={() => setMenuOpen((value) => !value)}
        >
          <span />
          <span />
          <span />
        </button>
        <div className={`nav-links ${menuOpen ? 'open' : ''}`}>
          <NavLink to="/" onClick={closeMenu}>Home</NavLink>
          <NavLink to="/products" onClick={closeMenu}>Shop</NavLink>
          <NavLink to="/orders" onClick={closeMenu}>Orders</NavLink>
          <NavLink to="/admin" onClick={closeMenu}>Admin</NavLink>
        </div>
        <div className={`nav-actions ${menuOpen ? 'open' : ''}`}>
          <span className="user-pill">Guest Checkout</span>
          <button className="dark-button" onClick={() => {
            setCartOpen(true);
            closeMenu();
          }}>
            Cart ({itemCount})
          </button>
        </div>
      </nav>
    </header>
  );
};

export default Navbar;
