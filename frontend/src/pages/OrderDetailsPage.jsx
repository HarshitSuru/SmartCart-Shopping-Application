import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { getOrderById } from '../services/orderService';
import { formatCurrency } from '../utils/currency';
import { getErrorMessage } from '../utils/error';

const OrderDetailsPage = () => {
  const { orderId } = useParams();
  const [order, setOrder] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const loadOrder = async () => {
      try {
        setError('');
        const data = await getOrderById(orderId);
        setOrder(data);
      } catch (requestError) {
        setOrder(null);
        setError(getErrorMessage(requestError, 'Order details could not be loaded right now.'));
      }
    };

    loadOrder();
  }, [orderId]);

  if (error) {
    return (
      <div className="content-card empty-panel">
        <p>{error}</p>
        <p className="empty-state">If the backend was changed recently, restart it once and reload this page.</p>
        <Link className="ghost-button" to="/orders">
          Back to Orders
        </Link>
      </div>
    );
  }

  if (!order) {
    return <div className="content-card">Loading order details...</div>;
  }

  return (
    <div className="content-card">
      <div className="summary-row">
        <div>
          <span className="eyebrow">Order Details</span>
          <h2>Order #{order.id.slice(-6)}</h2>
        </div>
        <span className="category-tag">{order.status}</span>
      </div>
      <div className="stack-list">
        {order.items.map((item) => (
          <div className="summary-row" key={item.productId}>
            <span>
              {item.productName} x {item.quantity}
            </span>
            <strong>{formatCurrency(item.subtotal)}</strong>
          </div>
        ))}
      </div>
      <div className="summary-row total-row">
        <span>Total</span>
        <strong>{formatCurrency(order.totalPrice)}</strong>
      </div>
      <p>Shipping Address: {order.shippingAddress}</p>
      <p>Payment Method: {order.paymentMethod}</p>
    </div>
  );
};

export default OrderDetailsPage;
