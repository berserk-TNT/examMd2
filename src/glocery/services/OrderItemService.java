package glocery.services;

import glocery.models.OrderItem;
import glocery.tools.FileFormat;

import java.util.ArrayList;
import java.util.List;

public class OrderItemService implements IOrderItemService {
    private final static String path = "data/order-items.csv";
    private static OrderItemService instance;

    private OrderItemService() {
    }

    public static OrderItemService getInstance() {
        if (instance == null) {
            instance = new OrderItemService();
        }
        return instance;
    }

    @Override
    public List<OrderItem> findAll() {
        List<OrderItem> orderItemList = new ArrayList<>();
        List<String> records = FileFormat.read(path);
        for (String record : records) {
            orderItemList.add(new OrderItem(record));
        }
        return orderItemList;
    }

    @Override
    public void add(OrderItem newOrderItem) {
        List<OrderItem> orderItemList = findAll();
        orderItemList.add(newOrderItem);
        FileFormat.write(path, orderItemList);
    }

    @Override
    public void update(OrderItem newOrderItem) {
        List<OrderItem> orderItemList = findAll();
        FileFormat.write(path, orderItemList);
    }
}
