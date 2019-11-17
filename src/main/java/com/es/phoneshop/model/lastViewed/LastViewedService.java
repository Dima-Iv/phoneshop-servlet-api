package com.es.phoneshop.model.lastViewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;

public interface LastViewedService {
    LinkedList<Product> getLastViewed(HttpSession session);

    void add(LinkedList<Product> lastViewed, Product product);
}
