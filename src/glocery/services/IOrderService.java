package glocery.services;

import glocery.models.Order;

import java.util.List;

public interface IOrderService {
    List<Order> findAll();

    void add(Order newOrder);

    void update();

    Order findById(Long id);

    //List<Order> findByAccountId(Long id);

    //boolean existById(Long id);
}
