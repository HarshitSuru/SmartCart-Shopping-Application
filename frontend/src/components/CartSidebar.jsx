import React from 'react';
import { Link } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import { formatCurrency } from '../utils/currency';

const CartSidebar = () => {
  const { cart, cartOpen, setCartOpen, updateItem, removeItem } = useCart();

  return (
    <aside className={`cart-sidebar ${cartOpen ? 'open' : ''}`}>
      <div className="cart-sidebar-header">
        <h3>Your Cart</h3>
        <button className="icon-button" onClick={() => setCartOpen(false)}>
          x
        </button>
      </div>
      <div className="cart-sidebar-content">
        {cart.items?.length ? (
          cart.items.map((item) => (
            <div className="cart-line" key={item.productId}>
              <div>
                <h4>{item.productName}</h4>
                <p>{formatCurrency(item.subtotal)}</p>
              </div>
              <div className="cart-line-actions">
                <input
                  type="number"
                  min="1"
                  value={item.quantity}
                  onChange={(event) =>
                    updateItem({
                      productId: item.productId,
                      quantity: Number(event.target.value)
                    })
                  }
                />
                <button className="text-button" onClick={() => removeItem(item.productId)}>
                  Remove
                </button>
              </div>
            </div>
          ))
        ) : (
          <p className="empty-state">Your persistent cart is waiting for its first item.</p>
        )}
      </div>
      <div className="cart-sidebar-footer">
        <div className="summary-row">
          <span>Total</span>
          <strong>{formatCurrency(cart.totalPrice)}</strong>
        </div>
        <Link className="dark-button full-width" to="/cart" onClick={() => setCartOpen(false)}>
          View Full Cart
        </Link>
      </div>
    </aside>
  );
};

export default CartSidebar;
