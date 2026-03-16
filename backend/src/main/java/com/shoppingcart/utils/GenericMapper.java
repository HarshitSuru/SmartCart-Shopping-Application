package com.shoppingcart.utils;

public interface GenericMapper<E, D> {

    D toDto(E entity);
}
