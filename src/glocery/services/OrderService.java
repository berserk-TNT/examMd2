package glocery.services;

import glocery.models.Order;
import glocery.tools.FileFormat;

import java.util.ArrayList;
import java.util.List;

public class OrderService implements IOrderService {
    public static final String path = "data/orders.csv";

    private static OrderService instance;
    private OrderService() {
    }
    public static OrderService getInstance() {
        if (instance == null)
            instance = new OrderService();
        return instance;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orderList = new ArrayList<>();
        List<String> records = FileFormat.read(path);
        for (String record : records) {
            orderList.add(Order.parse(record));
        }
        return orderList;
    }

    @Override
    public void add(Order newOrder) {
        List<Order> orderList = findAll();
        orderList.add(newOrder);
        FileFormat.write(path, orderList);
    }

    @Override
    public void update() {
        List<Order> orderList = findAll();
        FileFormat.write(path, orderList);
    }


    @Override
    public Order findById(Long id) {
        List<Order> orderList = findAll();
        for (Order order : orderList) {
            if (id.equals(order.getOrderId())) {
                return order;
            }
        }
        return null;
    }

//    @Override
//    public List<Order> findByAccountId(Long id) {
//        List<Order> newOrders = new ArrayList<>();
//        for (Order order : findAll()) {
//            if (id.equals(order.getOrderId())) {
//                newOrders.add(order);
//            }
//        }
//        return newOrders;
//    }
//
//    @Override
//    public boolean existById(Long id) {
//        return findById(id) != null;
//    }
}
