package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductListService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {
    private ProductListService productListService;
    private CartService cartService;

    @Override
    public void init() {
        productListService = new ProductListService();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdString = request.getParameter("productId");
        Product product = productListService.getProduct(Long.valueOf(productIdString));
        Cart cart = cartService.getCart(request.getSession());

        cartService.delete(cart, product);

        response.sendRedirect(request.getContextPath() + "/cart?message=delete successfully");
    }
}
