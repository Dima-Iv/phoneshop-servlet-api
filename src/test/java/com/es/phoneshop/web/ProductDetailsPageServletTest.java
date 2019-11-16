package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.lastViewed.LastViewedService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductListService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Locale;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProductListService productListService;
    @Mock
    private LastViewedService lastViewedService;
    @Mock
    private CartService cartService;
    @Mock
    private LinkedList lastViewed;
    @Mock
    private Cart cart;
    @Mock
    private Product product;
    @InjectMocks
    private ProductDetailsPageServlet servlet;

    @Before
    public void setup() {
        when(productListService.getProduct(1L)).thenReturn(product);
        when(cartService.getCart(session)).thenReturn(cart);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp")).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(lastViewedService.getLastViewed(session)).thenReturn(lastViewed);
        when(request.getPathInfo()).thenReturn("/1");

        servlet.doGet(request, response);

        verify(lastViewedService).add(lastViewed, product);
        verify(request).setAttribute("product", productListService.getProduct(1L));
        verify(request).setAttribute("lastViewed", lastViewed);
        verify(request).setAttribute("cart", cart);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/1");
        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(request.getParameter("quantity")).thenReturn("1");

        servlet.doPost(request, response);

        verify(cartService).addProduct(cart, product, 1);
        verify(response).sendRedirect(request.getRequestURI() + "?message=Added to cart successfully");
    }
}
