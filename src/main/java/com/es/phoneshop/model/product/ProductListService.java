package com.es.phoneshop.model.product;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ProductListService {
    private static ProductDao productDao = ArrayListProductDao.getInstance();
    private static Lock lock = new ReentrantLock();

    public Product getProduct(Long id) {
        lock.lock();
        try {
            return productDao.getProduct(id);
        } finally {
            lock.unlock();
        }
    }

    public List<Product> search(String query) {
        lock.lock();
        try {
            if (query == null || query.equals("")) {
                return productDao.findProducts();
            } else {
                String[] allWordsFromQuery = query.toLowerCase().split("\\s+");
                return productDao.findProducts().stream()
                        .collect(Collectors.toMap(product -> product, product -> Arrays.stream(allWordsFromQuery)
                                .filter(word -> product.getDescription().toLowerCase().contains(word)).count()))
                        .entrySet().stream()
                        .filter(map -> map.getValue() > 0)
                        .sorted(Comparator.comparing(Map.Entry<Product, Long>::getValue).reversed())
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
            }
        } finally {
            lock.unlock();
        }
    }

    public List<Product> findProducts(String query, String sortField, String sortOrder) {
        lock.lock();
        try {
            String sortOptions = sortField + "_" + sortOrder;

            if (sortOptions.equals("null_null")) {
                return search(query);
            } else {
                Comparator<Product> comparator = SortOptions.valueOf(sortOptions.toUpperCase());
                return search(query).stream().sorted(comparator).collect(Collectors.toList());
            }
        } finally {
            lock.unlock();
        }
    }

    public void save(Product product) {
        lock.lock();
        try {
            productDao.save(product);
        } finally {
            lock.unlock();
        }
    }

    public void delete(Long id) {
        lock.lock();
        try {
            productDao.delete(id);
        } finally {
            lock.unlock();
        }
    }

    public ProductDao getProductDao() {
        lock.lock();
        try {
            return productDao;
        } finally {
            lock.unlock();
        }
    }
}
