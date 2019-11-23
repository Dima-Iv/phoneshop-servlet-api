package com.es.phoneshop.model.product;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.HttpSessionCartService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionCartServiceTest {
    private HttpSessionCartService httpSessionCartService = HttpSessionCartService.getInstance();
    private Cart cart = new Cart();

    @Mock
    Product product1;
    @Mock
    HttpSession session;

    @Before
    public void setUp() {
        when(product1.getStock()).thenReturn(10);
        when(product1.getPrice()).thenReturn(new BigDecimal(5));
        when(session.getAttribute("cart")).thenReturn(cart);
    }

    @Test
    public void testGetCartNotNull() {
        assertNotNull(httpSessionCartService.getCart(session));
    }

    @Test(expected = OutOfStockException.class)
    public void testAddProductOutOfStockExc() {
        httpSessionCartService.addProduct(cart, product1, 20);
    }

    @Test
    public void testAddProduct() {
        httpSessionCartService.addProduct(cart, product1, 1);

        assertEquals(cart.getTotalQuantity(), 1);
    }

    @Test
    public void testAddExistingProductWithGoodQuantity() {
        httpSessionCartService.addProduct(cart, product1, 5);
        httpSessionCartService.addProduct(cart, product1, 1);

        assertEquals(cart.getTotalQuantity(), 1);
        assertEquals(cart.getTotalQuantity(), 6);
        assertEquals(cart.getTotalPrice(), new BigDecimal(5*6));
    }

    @Test(expected = OutOfStockException.class)
    public void testAddExistingProductWithBigQuantity() {
        httpSessionCartService.addProduct(cart, product1, 5);
        httpSessionCartService.addProduct(cart, product1, 20);
    }

    @Test
    public void testUpdateWithZeroQuantity() {
        httpSessionCartService.addProduct(cart, product1, 5);
        httpSessionCartService.update(cart, product1, 0);

        assertEquals(cart.getTotalQuantity(), 0);
    }

    @Test(expected = OutOfStockException.class)
    public void testUpdateWithBigQuantity() {
        httpSessionCartService.addProduct(cart, product1, 5);
        httpSessionCartService.update(cart, product1, 30);
    }

    @Test
    public void testUpdate() {
        httpSessionCartService.addProduct(cart, product1, 6);
        httpSessionCartService.update(cart, product1, 5);

        assertEquals(cart.getTotalQuantity(), 5);
    }

    @Test
    public void testDelete() {
        httpSessionCartService.addProduct(cart, product1, 5);
        httpSessionCartService.delete(cart, product1);

        assertEquals(cart.getTotalQuantity(), 0);
    }
}
