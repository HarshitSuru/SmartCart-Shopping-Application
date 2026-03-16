import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import { getProductById } from '../services/productService';
import { formatCategory } from '../utils/categories';
import { formatCurrency } from '../utils/currency';

const ProductDetailsPage = () => {
  const { id } = useParams();
  const { addItem } = useCart();
  const [product, setProduct] = useState(null);
  const [quantity, setQuantity] = useState(1);

  useEffect(() => {
    getProductById(id).then(setProduct);
  }, [id]);

  const handleAddToCart = async () => {
    await addItem({ productId: product.id, quantity });
  };

  if (!product) {
    return <div className="content-card">Loading product details...</div>;
  }

  return (
    <div className="product-details-grid">
      <div
        className="detail-image"
        style={{
          backgroundImage: `linear-gradient(180deg, rgba(17,24,39,0.12), rgba(17,24,39,0.35)), url(${product.imageUrl || 'https://images.pexels.com/photos/90946/pexels-photo-90946.jpeg?auto=compress&cs=tinysrgb&w=1200'})`
        }}
      />
      <div className="content-card">
        <span className="category-tag">{formatCategory(product.category)}</span>
        <h1>{product.name}</h1>
        <p>{product.description}</p>
        <div className="detail-meta">
          <strong>{formatCurrency(product.price)}</strong>
          <span>{product.inventory} units available</span>
        </div>
        <div className="detail-actions">
          <input
            type="number"
            min="1"
            max={product.inventory}
            value={quantity}
            onChange={(event) => setQuantity(Number(event.target.value))}
          />
          <button className="dark-button" onClick={handleAddToCart}>
            Add to Cart
          </button>
        </div>
      </div>
    </div>
  );
};

export default ProductDetailsPage;
