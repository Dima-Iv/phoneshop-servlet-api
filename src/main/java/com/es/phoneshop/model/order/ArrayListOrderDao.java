package com.es.phoneshop.model.order;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ArrayListOrderDao implements OrderDao {
    private static OrderDao orderDao;
    private List<Order> orders;
    private static Lock lock = new ReentrantLock();

    public static ArrayListOrderDao getInstance() {
        lock.lock();
        try {
            if (orderDao == null) {
                orderDao = new ArrayListOrderDao();
            }
            return (ArrayListOrderDao) orderDao;
        } finally {
            lock.unlock();
        }
    }

    private ArrayListOrderDao() {
        orders = new ArrayList<>();
    }

    @Override
    public void saveOrder(Order order) {
        if(orders.contains(order)) {
            throw new IllegalArgumentException();
        }
        if(order != null) {
            orders.add(order);
        }
    }
}
