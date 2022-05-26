package glocery.models;

public class OrderItem {
    private Long orderId;
    private String proName;
    private Double proPrice;
    private Integer orderQuantity;
//    private Instant orderItemTime;
//    private Double orderTotal;

    public OrderItem() {
    }

    public OrderItem(Long orderId, String proName, Double proPrice, Integer orderQuantity) {
        this.orderId = orderId;
        this.proName = proName;
        this.proPrice = proPrice;
        this.orderQuantity = orderQuantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Double getProPrice() {
        return proPrice;
    }

    public void setProPrice(Double proPrice) {
        this.proPrice = proPrice;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

//    public Instant getOrderItemTime() {
//        return orderItemTime;
//    }
//    public void setOrderItemTime(Instant orderItemTime) {
//        this.orderItemTime = orderItemTime;
//    }
//    public Double getOrderTotal() {
//        return orderTotal;
//    }
//    public void setOrderTotal(Double orderTotal) {
//        this.orderTotal = orderTotal;
//    }

    public OrderItem(String record) {
        String[] fields = record.split(", ");
        orderId = Long.parseLong(fields[0]);
        proName = fields[1];
        proPrice = Double.parseDouble(fields[2]);
        orderQuantity = Integer.parseInt(fields[3]);
    }

    @Override
    public String toString() {
        return orderId + ", " + proName + ", " + proPrice + ", " + orderQuantity;
    }
}
