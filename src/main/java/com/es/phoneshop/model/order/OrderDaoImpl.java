package com.es.phoneshop.model.order;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class OrderDaoImpl implements OrderDao {
    private static OrderDaoImpl instance;
    private List<Order> orders = new CopyOnWriteArrayList<>();
    private AtomicLong Id = new AtomicLong(0);

    protected OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance() {
        if (instance == null) {
            synchronized (OrderDaoImpl.class) {
                if (instance == null) {
                    instance = new OrderDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void save(Order order) {
        order.setId(Id.incrementAndGet());
        orders.add(order);
    }

    @Override
    public Order getBySecureId(String secureId) {
        Order order = orders
                .stream()
                .filter(products -> products.getSecureId().equals(secureId))
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException("Product with  id: " + secureId + " isn't"));
        return order;
    }
}
