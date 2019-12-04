package com.es.phoneshop.model.cart;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HttpSessionCartService implements CartService {
    private static CartService cartService;
    private static Lock lock = new ReentrantLock();

    public static HttpSessionCartService getInstance() {
        lock.lock();
        try {
            if (cartService == null) {
                cartService = new HttpSessionCartService();
            }
            return (HttpSessionCartService) cartService;
        } finally {
            lock.unlock();
        }
    }

    private void recalculate(Cart cart) {
        lock.lock();
        try {
            int tempTotalQuantity = cart.getCartItemList()
                    .stream().mapToInt(CartItem::getQuantity).sum();

            BigDecimal tempTotalPrice = cart.getCartItemList().stream()
                    .map(cartItem -> new BigDecimal(cartItem.getQuantity())
                            .multiply(cartItem.getProduct().getPrice()))
                    .reduce(BigDecimal::add).orElse(null);

            cart.setTotalQuantity(tempTotalQuantity);
            cart.setTotalPrice(tempTotalPrice);
        } finally {
            lock.unlock();
        }
    }

    private Optional<CartItem> findProduct(Cart cart, Product product) {
        lock.lock();
        try {
            return cart.getCartItemList().stream()
                    .filter(cartItem -> cartItem.getProduct()
                            .equals(product)).findAny();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Cart getCart(HttpSession session) {
        lock.lock();
        try {
            Cart cart = (Cart) session.getAttribute("cart_session");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart_session", cart);
            }
            return cart;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void addProduct(Cart cart, Product product, int quantity) {
        lock.lock();
        try {
            Optional<CartItem> cartItem = findProduct(cart, product);

            int tempQuantity = cartItem.map(CartItem::getQuantity).orElse(0);

            if (tempQuantity + quantity > product.getStock() || quantity < 0) {
                throw new OutOfStockException("Not enough product");
            }

            if (cartItem.isPresent()) {
                cartItem.get().setQuantity(tempQuantity + quantity);
            } else {
                cart.getCartItemList().add(new CartItem(product, quantity));
            }

            recalculate(cart);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void update(Cart cart, Product product, int quantity) {
        lock.lock();
        try {
            if (quantity == 0) {
                delete(cart, product);
                return;
            }

            Optional<CartItem> cartItem = findProduct(cart, product);

            if (quantity > product.getStock() || quantity < 0) {
                throw new OutOfStockException("Not enough product");
            }

            cartItem.ifPresent(item -> item.setQuantity(quantity));

            recalculate(cart);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void delete(Cart cart, Product product) {
        lock.lock();
        try {
            findProduct(cart, product).ifPresent(cartItem -> cart.getCartItemList()
                    .remove(cartItem));
            recalculate(cart);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void resetCart(Cart cart) {
        lock.lock();
        try {
            for (CartItem cartItem : cart.getCartItemList()) {
                cartItem.getProduct().setStock(cartItem.getProduct().getStock() - cartItem.getQuantity());
            }
            cart.getCartItemList().removeIf(cartItem -> true);
            recalculate(cart);
        } finally {
            lock.unlock();
        }
    }
}
