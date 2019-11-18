package com.es.phoneshop.web;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductListService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CartPageServlet extends HttpServlet {
    private ProductListService productListService;
    private CartService cartService;

    private void showPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    public void init() {
        productListService = new ProductListService();
        cartService = HttpSessionCartService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showPage(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIdStrings = request.getParameterValues("productId");
        String[] quantityStrings = request.getParameterValues("quantity");
        Map<Product, String> errorMap = new HashMap<>();

        for (int i = 0; i < productIdStrings.length; i++) {
            long productId = Long.parseLong(productIdStrings[i]);
            Product product = productListService.getProduct(productId);

            try {
                Locale locale = request.getLocale();
                String quantityString = quantityStrings[i];
                int quantity = NumberFormat.getNumberInstance(locale).parse(quantityString).intValue();
                cartService.update(cartService.getCart(request.getSession()), product, quantity);
            } catch (ParseException e) {
                errorMap.put(product, "Not a number");
            } catch (OutOfStockException e) {
                errorMap.put(product, e.getMessage());
            }
        }

        if (!errorMap.isEmpty()) {
            request.setAttribute("errorMap", errorMap);
            showPage(request, response);
            return;
        }

        response.sendRedirect(request.getRequestURI() + "?message=update successfully");
    }
}
