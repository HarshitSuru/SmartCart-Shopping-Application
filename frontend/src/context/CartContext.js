import React, { createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';
import { addToCart, getCart, removeCartItem, updateCartItem } from '../services/cartService';

const CartContext = createContext(null);

export const CartProvider = ({ children }) => {
  const [cart, setCart] = useState({ items: [], totalPrice: 0 });
  const [cartOpen, setCartOpen] = useState(false);
  const [loading, setLoading] = useState(false);

  const refreshCart = useCallback(async () => {
    setLoading(true);
    try {
      const data = await getCart();
      setCart(data || { items: [], totalPrice: 0 });
    } catch (error) {
      setCart({ items: [], totalPrice: 0 });
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    refreshCart();
  }, [refreshCart]);

  const addItem = useCallback(async (payload) => {
    try {
      const data = await addToCart(payload);
      setCart(data);
      setCartOpen(true);
    } catch (error) {
      setCartOpen(false);
      throw error;
    }
  }, []);

  const updateItem = useCallback(async (payload) => {
    const data = await updateCartItem(payload);
    setCart(data);
  }, []);

  const removeItem = useCallback(async (productId) => {
    const data = await removeCartItem(productId);
    setCart(data);
  }, []);

  const value = useMemo(
    () => ({
      cart,
      loading,
      cartOpen,
      setCartOpen,
      refreshCart,
      addItem,
      updateItem,
      removeItem,
      itemCount: cart.items?.reduce((sum, item) => sum + item.quantity, 0) || 0
    }),
    [cart, loading, cartOpen, refreshCart, addItem, updateItem, removeItem]
  );

  return <CartContext.Provider value={value}>{children}</CartContext.Provider>;
};

export const useCart = () => useContext(CartContext);
