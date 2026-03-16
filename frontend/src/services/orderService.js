import api from './api';

export const checkout = async (payload) => {
  const response = await api.post('/checkout', payload);
  return response.data.data;
};

export const getOrders = async () => {
  const response = await api.get('/orders');
  return response.data.data;
};

export const getOrderById = async (orderId) => {
  const response = await api.get(`/orders/${orderId}`);
  return response.data.data;
};
