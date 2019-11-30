package com.es.phoneshop.model.cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Cart implements Serializable {
    private Set<CartItem> cartItemList;
    private int totalQuantity;
    private BigDecimal totalPrice;

    public Cart() {
        cartItemList = new HashSet<>();
        totalPrice = new BigDecimal(0);
    }

    public Cart(Set<CartItem> cartItemList, int totalQuantity, BigDecimal totalPrice) {
        this.cartItemList = cartItemList;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public Set<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(Set<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
