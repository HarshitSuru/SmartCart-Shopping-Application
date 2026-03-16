import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getOrders } from '../services/orderService';
import { formatCurrency } from '../utils/currency';
import { getErrorMessage } from '../utils/error';

const OrderHistoryPage = () => {
  const [orders, setOrders] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const loadOrders = async () => {
      try {
        setError('');
        const data = await getOrders();
        setOrders(data);
      } catch (requestError) {
        setOrders([]);
        setError(getErrorMessage(requestError, 'Orders could not be loaded right now.'));
      }
    };

    loadOrders();
  }, []);

  return (
    <div className="section-block">
      <div className="section-heading">
        <div>
          <span className="eyebrow">History</span>
          <h2>Your previous orders</h2>
        </div>
      </div>
      <div className="stack-list">
        {error ? (
          <div className="content-card empty-panel">
            <p>{error}</p>
            <p className="empty-state">If you just changed the backend security, restart the Spring Boot server once and try again.</p>
            <Link className="ghost-button" to="/products">
              Go to Products
            </Link>
          </div>
        ) : orders.length ? orders.map((order) => (
          <article className="content-card" key={order.id}>
            <div className="summary-row">
              <div>
                <h3>Order #{order.id.slice(-6)}</h3>
                <p>{new Date(order.createdAt).toLocaleString()}</p>
              </div>
              <span className="category-tag">{order.status}</span>
            </div>
            <div className="summary-row">
              <span>{order.items.length} products</span>
              <strong>{formatCurrency(order.totalPrice)}</strong>
            </div>
            <Link className="text-button" to={`/orders/${order.id}`}>
              View details
            </Link>
          </article>
        )) : (
          <div className="content-card empty-panel">
            <p>No orders yet. Add products to the cart and place your first order.</p>
            <Link className="ghost-button" to="/products">
              Browse Products
            </Link>
          </div>
        )}
      </div>
    </div>
  );
};

export default OrderHistoryPage;
