import api from './api';

export const getCart = async () => {
  const response = await api.get('/cart');
  return response.data.data;
};

export const addToCart = async (payload) => {
  const response = await api.post('/cart/add', payload);
  return response.data.data;
};

export const updateCartItem = async (payload) => {
  const response = await api.put('/cart/update', payload);
  return response.data.data;
};

export const removeCartItem = async (productId) => {
  const response = await api.delete('/cart/remove', { params: { productId } });
  return response.data.data;
};
