package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


public class DefaultOrderService implements OrderService {
    private static OrderService orderService;
    private static Lock lock = new ReentrantLock();

    public static DefaultOrderService getInstance() {
        lock.lock();
        try {
            if (orderService == null) {
                orderService = new DefaultOrderService();
            }
            return (DefaultOrderService) orderService;
        } finally {
            lock.unlock();
        }
    }

    private BigDecimal getDeliveryCost() {
        return new BigDecimal(10);
    }

    @Override
    public Order getOrder(Cart cart) {
        Order result = new Order(cart.getCartItemList().stream()
                .map(CartItem::new)
                .collect(Collectors.toSet())
                , cart.getTotalQuantity()
                , cart.getTotalPrice().add(getDeliveryCost()));
        result.setDeliveryCost(getDeliveryCost());
        result.setSubtotal(cart.getTotalPrice());
        return result;
    }

    @Override
    public Order getOrder(String secureId) {
        return ArrayListOrderDao.getInstance().getOrder(secureId);
    }

    @Override
    public String placeOrder(Order order) {
        String secureId = UUID.randomUUID().toString();
        order.setSecureId(secureId);
        ArrayListOrderDao.getInstance().saveOrder(order);
        return secureId;
    }
}
