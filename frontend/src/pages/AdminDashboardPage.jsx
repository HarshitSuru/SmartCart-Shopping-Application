import React, { useEffect, useState } from 'react';
import { createProduct, deleteProduct, getProducts, updateProduct } from '../services/productService';
import { categories, formatCategory } from '../utils/categories';
import { formatCurrency } from '../utils/currency';

const initialForm = {
  name: '',
  description: '',
  price: '',
  category: 'ELECTRONICS',
  inventory: '',
  imageUrl: ''
};

const AdminDashboardPage = () => {
  const [products, setProducts] = useState([]);
  const [formData, setFormData] = useState(initialForm);
  const [editingId, setEditingId] = useState(null);

  const loadProducts = async () => {
    const data = await getProducts();
    setProducts(data);
  };

  useEffect(() => {
    loadProducts();
  }, []);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const payload = {
      ...formData,
      price: Number(formData.price),
      inventory: Number(formData.inventory)
    };

    if (editingId) {
      await updateProduct(editingId, payload);
    } else {
      await createProduct(payload);
    }

    setFormData(initialForm);
    setEditingId(null);
    await loadProducts();
  };

  const handleEdit = (product) => {
    setEditingId(product.id);
    setFormData({
      name: product.name,
      description: product.description,
      price: product.price,
      category: product.category,
      inventory: product.inventory,
      imageUrl: product.imageUrl || ''
    });
  };

  const handleDelete = async (productId) => {
    await deleteProduct(productId);
    await loadProducts();
  };

  const totalInventory = products.reduce((sum, product) => sum + product.inventory, 0);
  const lowStockProducts = products.filter((product) => product.inventory < 20).length;

  return (
    <div className="admin-page">
      <section className="admin-hero">
        <div className="admin-hero-copy">
          <span className="eyebrow">Admin Dashboard</span>
          <h1>Manage products</h1>
          <p>
            Add products, update stock, and keep the catalog current.
          </p>
        </div>
        <div className="admin-stats">
          <article className="admin-stat-card">
            <span>Total products</span>
            <strong>{products.length}</strong>
          </article>
          <article className="admin-stat-card">
            <span>Total inventory</span>
            <strong>{totalInventory}</strong>
          </article>
          <article className="admin-stat-card">
            <span>Low stock items</span>
            <strong>{lowStockProducts}</strong>
          </article>
        </div>
      </section>

      <div className="admin-layout-grid">
        <form className="content-card form-card admin-form-card" onSubmit={handleSubmit}>
          <div className="admin-form-header">
            <div>
              <span className="eyebrow">{editingId ? 'Editing Product' : 'New Product'}</span>
              <h2>{editingId ? 'Update product' : 'Add product'}</h2>
            </div>
            {editingId ? (
              <button
                className="ghost-button"
                type="button"
                onClick={() => {
                  setEditingId(null);
                  setFormData(initialForm);
                }}
              >
                Clear
              </button>
            ) : null}
          </div>

          <div className="admin-form-grid">
            <label className="form-field">
              <span>Product Name</span>
              <input
                value={formData.name}
                onChange={(event) => setFormData((prev) => ({ ...prev, name: event.target.value }))}
                required
              />
            </label>

            <label className="form-field admin-form-wide">
              <span>Description</span>
              <textarea
                rows="4"
                value={formData.description}
                onChange={(event) =>
                  setFormData((prev) => ({ ...prev, description: event.target.value }))
                }
                required
              />
            </label>

            <label className="form-field">
              <span>Price</span>
              <input
                type="number"
                step="0.01"
                value={formData.price}
                onChange={(event) => setFormData((prev) => ({ ...prev, price: event.target.value }))}
                required
              />
            </label>

            <label className="form-field">
              <span>Category</span>
              <select
                value={formData.category}
                onChange={(event) => setFormData((prev) => ({ ...prev, category: event.target.value }))}
              >
                {categories.map((category) => (
                  <option key={category} value={category}>
                    {formatCategory(category)}
                  </option>
                ))}
              </select>
            </label>

            <label className="form-field">
              <span>Inventory</span>
              <input
                type="number"
                value={formData.inventory}
                onChange={(event) => setFormData((prev) => ({ ...prev, inventory: event.target.value }))}
                required
              />
            </label>

            <label className="form-field admin-form-wide">
              <span>Image URL</span>
              <input
                value={formData.imageUrl}
                onChange={(event) => setFormData((prev) => ({ ...prev, imageUrl: event.target.value }))}
              />
            </label>
          </div>

          <button className="dark-button" type="submit">
            {editingId ? 'Save Product' : 'Create Product'}
          </button>
        </form>

        <section className="admin-list">
          <div className="admin-list-header">
            <div>
              <span className="eyebrow">Catalog Items</span>
              <h2>Product list</h2>
            </div>
            <span className="admin-list-count">{products.length} items</span>
          </div>

          <div className="admin-product-grid">
            {products.map((product) => (
              <article className="content-card admin-product-card" key={product.id}>
                <div className="admin-product-body">
                  <div className="summary-row">
                    <span className="category-tag">{formatCategory(product.category)}</span>
                    <strong>{formatCurrency(product.price)}</strong>
                  </div>
                  <h3>{product.name}</h3>
                  <p>{product.description}</p>
                  <div className="admin-product-footer">
                    <span className={`inventory-pill ${product.inventory < 20 ? 'low' : ''}`}>
                      {product.inventory} in stock
                    </span>
                    <div className="inline-actions">
                      <button className="ghost-button" onClick={() => handleEdit(product)}>
                        Edit
                      </button>
                      <button className="text-button danger-button" onClick={() => handleDelete(product.id)}>
                        Delete
                      </button>
                    </div>
                  </div>
                </div>
              </article>
            ))}
          </div>
        </section>
      </div>
    </div>
  );
};

export default AdminDashboardPage;
