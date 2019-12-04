package com.es.phoneshop.model.order;

public interface OrderDao {
    Order getOrder(String secureId);

    void saveOrder(Order order);
}
