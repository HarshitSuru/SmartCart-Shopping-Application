import React, { useEffect, useState } from 'react';
import ProductCard from '../components/ProductCard';
import { useCart } from '../context/CartContext';
import { getProducts } from '../services/productService';
import { categories, formatCategory } from '../utils/categories';

const ProductListingPage = () => {
  const { addItem } = useCart();
  const [products, setProducts] = useState([]);
  const [search, setSearch] = useState('');
  const [category, setCategory] = useState('');

  useEffect(() => {
    const loadProducts = async () => {
      const params = {};
      if (search) {
        params.search = search;
      }
      if (category) {
        params.category = category;
      }
      const data = await getProducts(params);
      setProducts(data);
    };
    loadProducts();
  }, [search, category]);

  const handleAddToCart = async (productId) => {
    await addItem({ productId, quantity: 1 });
  };

  return (
    <div className="section-block">
      <div className="section-heading">
        <div>
          <span className="eyebrow">Catalog</span>
          <h2>Shop the collection</h2>
        </div>
      </div>
      <div className="filter-bar">
        <input
          type="search"
          placeholder="Search by name or description"
          value={search}
          onChange={(event) => setSearch(event.target.value)}
        />
        <select value={category} onChange={(event) => setCategory(event.target.value)}>
          <option value="">All categories</option>
          {categories.map((item) => (
            <option key={item} value={item}>
              {formatCategory(item)}
            </option>
          ))}
        </select>
      </div>
      <div className="product-grid">
        {products.map((product) => (
          <ProductCard key={product.id} product={product} onAddToCart={handleAddToCart} />
        ))}
      </div>
    </div>
  );
};

export default ProductListingPage;
