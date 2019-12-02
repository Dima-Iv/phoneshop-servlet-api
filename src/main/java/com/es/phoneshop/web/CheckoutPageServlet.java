package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import com.es.phoneshop.model.order.DefaultOrderService;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;

    private String readRequestParameter(HttpServletRequest request, String name, Map<String, String> errorMap) {
        String curName = request.getParameter(name);
        if (curName == null || curName.isEmpty()) {
            errorMap.put(name, "Name is required");
        }
        return curName;
    }

    @Override
    public void init() {
        cartService = HttpSessionCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());
        Order order = orderService.getOrder(cart);

        request.setAttribute("order", order);

        request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());
        Order order = orderService.getOrder(cart);

        Map<String, String> errorMap = new HashMap<>();
        String firstName = readRequestParameter(request, "firstname", errorMap);
        String lastName = readRequestParameter(request, "lastname", errorMap);
        String phone = readRequestParameter(request, "phone", errorMap);
        String date = readRequestParameter(request, "date", errorMap);
        String address = readRequestParameter(request, "address", errorMap);
        String payment = readRequestParameter(request, "payment", errorMap);

        if (!errorMap.isEmpty()) {
            request.setAttribute("order", order);
            request.setAttribute("errorMap", errorMap);
            request.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(request, response);
            return;
        }

        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setPhone(phone);
        order.setDate(date);
        order.setAddress(address);
        order.setPayment(payment);

        orderService.placeOrder(order);
    }
}
