import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import { getProducts } from '../services/productService';
import { formatCurrency } from '../utils/currency';

const HomePage = () => {
  const { addItem } = useCart();
  const [featuredProducts, setFeaturedProducts] = useState([]);
  const [activeIndex, setActiveIndex] = useState(0);

  useEffect(() => {
    getProducts().then((items) =>
      setFeaturedProducts(items.filter((item) => item.name !== 'Everyday Moisture Cream').slice(0, 6))
    );
  }, []);

  const handleAddToCart = async (productId) => {
    await addItem({ productId, quantity: 1 });
  };

  useEffect(() => {
    if (!featuredProducts.length) {
      return undefined;
    }

    const interval = window.setInterval(() => {
      setActiveIndex((current) => (current + 1) % featuredProducts.length);
    }, 3500);

    return () => window.clearInterval(interval);
  }, [featuredProducts]);

  const activeProduct = featuredProducts[activeIndex];

  return (
    <div className="simple-home">
      <section className="home-carousel">
        {activeProduct ? (
          <>
            <div className="carousel-copy">
              <span className="eyebrow">Top Picks</span>
              <h1>{activeProduct.name}</h1>
              <p>{activeProduct.description}</p>
              <div className="carousel-price-row">
                <strong>{formatCurrency(activeProduct.price)}</strong>
                <span>{activeProduct.inventory} items in stock</span>
              </div>
              <div className="hero-actions">
                <button className="dark-button" onClick={() => handleAddToCart(activeProduct.id)}>
                  Add to Cart
                </button>
                <Link className="ghost-button" to={`/products/${activeProduct.id}`}>
                  View Product
                </Link>
              </div>
              <div className="carousel-dots">
                {featuredProducts.map((product, index) => (
                  <button
                    key={product.id}
                    type="button"
                    className={`carousel-dot ${index === activeIndex ? 'active' : ''}`}
                    onClick={() => setActiveIndex(index)}
                    aria-label={`Show ${product.name}`}
                  />
                ))}
              </div>
            </div>
            <div
              className="carousel-image"
              style={{
                backgroundImage: `linear-gradient(180deg, rgba(12,18,31,0.08), rgba(12,18,31,0.26)), url(${activeProduct.imageUrl})`
              }}
            />
          </>
        ) : null}
      </section>

      <section className="simple-home-section">
        <div className="section-heading">
          <div>
            <span className="eyebrow">Popular Right Now</span>
            <h2>Shop by what people usually pick first</h2>
          </div>
          <Link to="/products" className="text-button">
            View all products
          </Link>
        </div>
        <div className="simple-product-row">
          {featuredProducts.slice(0, 4).map((product) => (
            <article className="simple-product-tile" key={product.id}>
              <div
                className="simple-product-image"
                style={{
                  backgroundImage: `url(${product.imageUrl})`
                }}
              />
              <h3>{product.name}</h3>
              <p>{formatCurrency(product.price)}</p>
              <button className="ghost-button full-width" onClick={() => handleAddToCart(product.id)}>
                Add to Cart
              </button>
            </article>
          ))}
        </div>
      </section>
    </div>
  );
};

export default HomePage;
