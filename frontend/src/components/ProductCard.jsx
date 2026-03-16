import React from 'react';
import { Link } from 'react-router-dom';
import { formatCurrency } from '../utils/currency';
import { formatCategory } from '../utils/categories';

const ProductCard = ({ product, onAddToCart }) => {
  const maxLength = 92;
  const needsReadMore = product.description.length > maxLength;
  const shortDescription = needsReadMore
    ? product.description.slice(0, maxLength).trim()
    : product.description;

  return (
    <article className="product-card">
      <div
        className="product-card-image"
        style={{
          backgroundImage: `linear-gradient(180deg, rgba(17,24,39,0.1), rgba(17,24,39,0.45)), url(${product.imageUrl || 'https://images.pexels.com/photos/90946/pexels-photo-90946.jpeg?auto=compress&cs=tinysrgb&w=1200'})`
        }}
      >
        <div className="product-card-badge-row">
          <span className="category-tag">{formatCategory(product.category)}</span>
          <span className="stock-badge">{product.inventory} left</span>
        </div>
      </div>
      <div className="product-card-body">
        <h3>{product.name}</h3>
        <p className="product-card-description">
          {shortDescription}
          {needsReadMore ? '... ' : ' '}
          {needsReadMore ? (
            <Link className="read-more-link" to={`/products/${product.id}`}>
              Read more
            </Link>
          ) : null}
        </p>
        <div className="product-card-footer">
          <strong>{formatCurrency(product.price)}</strong>
          <span>Usually ships in 2 days</span>
        </div>
        <div className="product-card-actions">
          <Link className="ghost-button" to={`/products/${product.id}`}>
            See Details
          </Link>
          <button className="dark-button" onClick={() => onAddToCart(product.id)}>
            Add to Bag
          </button>
        </div>
      </div>
    </article>
  );
};

export default ProductCard;
