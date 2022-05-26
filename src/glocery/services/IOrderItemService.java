package glocery.services;

import glocery.models.OrderItem;

import java.util.List;

public interface IOrderItemService {
    List<OrderItem> findAll();

    void add(OrderItem newOrderItem);

    void update(OrderItem newOrderItem);
}
