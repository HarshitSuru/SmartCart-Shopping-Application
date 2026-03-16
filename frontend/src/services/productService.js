import api from './api';

export const getProducts = async (params = {}) => {
  const response = await api.get('/products', { params });
  return response.data.data;
};

export const getProductById = async (id) => {
  const response = await api.get(`/products/${id}`);
  return response.data.data;
};

export const createProduct = async (payload) => {
  const response = await api.post('/admin/products', payload);
  return response.data.data;
};

export const updateProduct = async (id, payload) => {
  const response = await api.put(`/admin/products/${id}`, payload);
  return response.data.data;
};

export const deleteProduct = async (id) => {
  const response = await api.delete(`/admin/products/${id}`);
  return response.data.data;
};
