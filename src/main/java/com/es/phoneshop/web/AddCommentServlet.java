package com.es.phoneshop.web;

import com.es.phoneshop.model.lastViewed.HttpSessionLVService;
import com.es.phoneshop.model.lastViewed.LastViewedService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductListService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddCommentServlet extends HttpServlet {
    private LastViewedService lastViewedService;
    private ProductListService productListService;


    private String readRequestParameter(HttpServletRequest request, String name, Map<String, String> errorMap) {
        String curName = request.getParameter(name);
        if (curName == null || curName.isEmpty()) {
            errorMap.put(name, "Name is required");
        }
        return curName;
    }

    private long getProductId(HttpServletRequest request) {
        return Long.parseLong(request.getPathInfo().substring(1));
    }

    @Override
    public void init() {
        lastViewedService = HttpSessionLVService.getInstance();
        productListService = new ProductListService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productListService.getProduct(getProductId(request));
        Map<String, String> errorMap = new HashMap<>();

        String name = readRequestParameter(request, "name", errorMap);
        String stringRating = readRequestParameter(request, "rating", errorMap);
        try {
            Integer rating = Integer.valueOf(stringRating);
            if (rating < 1 || rating > 5) {
                errorMap.put(stringRating, "border exceeded");
            }
        } catch (NumberFormatException mes) {
            errorMap.put(stringRating, mes.getMessage());
        }

        String comment = request.getParameter("comment");

        if (!errorMap.isEmpty()) {
            request.setAttribute("errorMap", errorMap);
            request.setAttribute("product", product);
            request.setAttribute("lastViewed", lastViewedService.getLastViewed(request.getSession()));
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
            return;
        }

        productListService.addComment(product, name, stringRating, comment);

        response.sendRedirect(request.getContextPath() + "/products/" + getProductId(request) + "?add comment successfully");
    }
}

