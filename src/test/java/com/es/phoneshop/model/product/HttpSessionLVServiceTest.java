package com.es.phoneshop.model.product;

import com.es.phoneshop.model.lastViewed.HttpSessionLVService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HttpSessionLVServiceTest {
    private HttpSessionLVService httpSessionLVService = HttpSessionLVService.getInstance();
    private LinkedList<Product> cartItemList = new LinkedList<>();

    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product product4;
    @Mock
    private Product product5;
    @Mock
    private HttpSession session;

    @Before
    public void setUp() {
        httpSessionLVService.add(cartItemList, product1);

        when(session.getAttribute("lastViewed")).thenReturn(cartItemList);
    }

    @Test
    public void testGetLastViewedNotNull() {
        assertNotNull(httpSessionLVService.getLastViewed(session));
    }

    @Test
    public void testGetLastViewed() {
        assertEquals(httpSessionLVService.getLastViewed(session), cartItemList);
    }

    @Test
    public void testAddExistingProduct() {
        product2 = product1;
        httpSessionLVService.add(cartItemList, product2);

        assertEquals(httpSessionLVService.getLastViewed(session).size(), 1);
    }

    @Test
    public void testAddControlSize() {
        httpSessionLVService.add(cartItemList, product3);
        httpSessionLVService.add(cartItemList, product4);
        httpSessionLVService.add(cartItemList, product5);

        assertEquals(3, httpSessionLVService.getLastViewed(session).size());
    }

    @Test
    public void testAdd() {
        int size = httpSessionLVService.getLastViewed(session).size();

        httpSessionLVService.add(cartItemList, product3);

        assertEquals(httpSessionLVService.getLastViewed(session).size(), size + 1);
    }
}
