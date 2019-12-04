package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(HttpSession session);

    void addProduct(Cart cart, Product product, int quantity);

    void update(Cart cart, Product product, int quantity);

    void delete(Cart cart, Product product);

    void resetCart(Cart cart);
}
