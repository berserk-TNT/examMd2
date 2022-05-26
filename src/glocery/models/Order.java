package glocery.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private Long orderId;
    private String accName;
    private String accPhoneNumber;
    private String accAddress;
    private Double orderTotal;
    private Instant orderCreatedTime;

    public Order() {
    }

    public Order(Long orderId, String accName, String accPhoneNumber, String accAddress) {
        this.orderId = orderId;
        this.accName = accName;
        this.accPhoneNumber = accPhoneNumber;
        this.accAddress = accAddress;
    }

    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long accId) {
        this.orderId = accId;
    }
    public String getAccName() {
        return accName;
    }
    public void setAccName(String accName) {
        this.accName = accName;
    }
    public String getAccPhoneNumber() {
        return accPhoneNumber;
    }
    public void setAccPhoneNumber(String accPhoneNumber) {
        this.accPhoneNumber = accPhoneNumber;
    }
    public String getAccAddress() {
        return accAddress;
    }
    public void setAccAddress(String accAddress) {
        this.accAddress = accAddress;
    }
    public Double getOrderTotal() {
        return orderTotal;
    }
    public void setOrderTotal(Double orderTotal) {
        this.orderTotal = orderTotal;
    }
    public Instant getOrderCreatedTime() {
        return orderCreatedTime;
    }
    public void setOrderCreatedTime(Instant orderCreatedTime) {
        this.orderCreatedTime = orderCreatedTime;
    }

    public static Order parse(String record) {
        Order order = new Order();
        String[] field = record.split(", ");
        order.orderId = Long.parseLong(field[0]);
        order.accName = field[1];
        order.accPhoneNumber = field[2];
        order.accAddress = field[3];
        return order;
    }

    @Override
    public String toString() {
        return orderId + ", " + accName + ", " + accPhoneNumber + ", " + accAddress;
    }

    List<OrderItem> orderItems = new ArrayList<>();
}
