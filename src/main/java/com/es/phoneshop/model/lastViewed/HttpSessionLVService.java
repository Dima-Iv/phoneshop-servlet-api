package com.es.phoneshop.model.lastViewed;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HttpSessionLVService implements LastViewedService {
    private static LastViewedService lastViewedService;
    private static Lock lock = new ReentrantLock();

    public static HttpSessionLVService getInstance() {
        lock.lock();
        try {
            if (lastViewedService == null) {
                lastViewedService = new HttpSessionLVService();
            }
            return (HttpSessionLVService) lastViewedService;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public LinkedList<Product> getLastViewed(HttpSession session) {
        lock.lock();
        try {
            LinkedList<Product> lastViewed = (LinkedList<Product>) session.getAttribute("lastViewed");
            if (lastViewed == null) {
                lastViewed = new LinkedList<>();
                session.setAttribute("lastViewed", lastViewed);
            }
            return lastViewed;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void add(LinkedList<Product> lastViewed, Product product) {
        lock.lock();
        try {
            if (lastViewed.contains(product)) {
                return;
            }

            if (lastViewed.size() == 3) {
                lastViewed.remove(0);
                lastViewed.add(product);
                return;
            }

            lastViewed.add(product);
        } finally {
            lock.unlock();
        }
    }
}
