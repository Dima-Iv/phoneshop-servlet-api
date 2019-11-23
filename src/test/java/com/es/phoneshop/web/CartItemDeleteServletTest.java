package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductListService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartItemDeleteServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ProductListService productListService;
    @Mock
    private CartService cartService;
    @Mock
    private Cart cart;
    @Mock
    private Product product;
    @InjectMocks
    private CartItemDeleteServlet servlet;

    @Before
    public void setup() {
        when(productListService.getProduct(anyLong())).thenReturn(product);
        when(cartService.getCart(session)).thenReturn(cart);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(request.getParameter("productId")).thenReturn("1");
        when(request.getSession()).thenReturn(session);

        servlet.doPost(request, response);

        verify(cartService).delete(cart, product);
        verify(response).sendRedirect(request.getContextPath() + "/cart?message=delete successfully");
    }
}
