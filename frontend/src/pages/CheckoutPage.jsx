import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import { checkout } from '../services/orderService';
import { formatCurrency } from '../utils/currency';
import { getErrorMessage } from '../utils/error';

const CheckoutPage = () => {
  const navigate = useNavigate();
  const { cart, refreshCart } = useCart();
  const [formData, setFormData] = useState({
    shippingAddress: '',
    paymentMethod: 'UPI'
  });
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState('');
  const hasItems = Boolean(cart.items?.length);

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (!hasItems) {
      setError('Add products to your cart before checking out.');
      return;
    }
    setError('');
    setSubmitting(true);
    try {
      const order = await checkout(formData);
      await refreshCart();
      navigate(`/orders/${order.id}`);
    } catch (requestError) {
      setError(getErrorMessage(requestError, 'Unable to place the order right now.'));
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="two-column-layout">
      <form className="content-card form-card" onSubmit={handleSubmit}>
        <h2>Checkout</h2>
        {error ? <div className="error-banner">{error}</div> : null}
        <label>
          Shipping Address
          <textarea
            rows="4"
            value={formData.shippingAddress}
            onChange={(event) =>
              setFormData((prev) => ({ ...prev, shippingAddress: event.target.value }))
            }
            required
          />
        </label>
        <label>
          Payment Method
          <select
            value={formData.paymentMethod}
            onChange={(event) =>
              setFormData((prev) => ({ ...prev, paymentMethod: event.target.value }))
            }
          >
            <option value="UPI">UPI</option>
            <option value="Credit Card">Credit Card</option>
            <option value="Net Banking">Net Banking</option>
            <option value="Cash on Delivery">Cash on Delivery</option>
          </select>
        </label>
        <button className="dark-button" type="submit" disabled={submitting || !hasItems}>
          {submitting ? 'Placing Order...' : 'Place Order'}
        </button>
      </form>
      <aside className="content-card summary-card">
        <h3>Order Summary</h3>
        {hasItems ? cart.items?.map((item) => (
          <div className="summary-row" key={item.productId}>
            <span>
              {item.productName} x {item.quantity}
            </span>
            <span>{formatCurrency(item.subtotal)}</span>
          </div>
        )) : <p className="empty-state">No items in cart yet.</p>}
        <div className="summary-row total-row">
          <span>Total</span>
          <strong>{formatCurrency(cart.totalPrice)}</strong>
        </div>
      </aside>
    </div>
  );
};

export default CheckoutPage;
