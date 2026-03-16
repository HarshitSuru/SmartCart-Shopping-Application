import React from 'react';
import { Link } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import { formatCurrency } from '../utils/currency';

const CartPage = () => {
  const { cart, updateItem, removeItem } = useCart();
  const hasItems = Boolean(cart.items?.length);

  return (
    <div className="two-column-layout">
      <section className="content-card">
        <h2>Your Shopping Cart</h2>
        {hasItems ? (
          cart.items.map((item) => (
            <div className="cart-page-line" key={item.productId}>
              <div>
                <h3>{item.productName}</h3>
                <p>{formatCurrency(item.unitPrice)} each</p>
              </div>
              <div className="cart-page-controls">
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
                <strong>{formatCurrency(item.subtotal)}</strong>
                <button className="text-button" onClick={() => removeItem(item.productId)}>
                  Remove
                </button>
              </div>
            </div>
          ))
        ) : (
          <div className="empty-panel">
            <p>Your cart is empty.</p>
            <Link className="ghost-button" to="/products">
              Continue Shopping
            </Link>
          </div>
        )}
      </section>
      <aside className="content-card summary-card">
        <h3>Order Summary</h3>
        <div className="summary-row">
          <span>Items</span>
          <span>{cart.items?.length || 0}</span>
        </div>
        <div className="summary-row">
          <span>Total</span>
          <strong>{formatCurrency(cart.totalPrice)}</strong>
        </div>
        <Link className={`dark-button full-width ${!hasItems ? 'disabled-link' : ''}`} to={hasItems ? '/checkout' : '/cart'}>
          Proceed to Checkout
        </Link>
      </aside>
    </div>
  );
};

export default CartPage;
