package glocery.models;

import java.time.Instant;

public class Product {
    private Long proId;
    private String proName;
    private Double proPrice;
    private Integer proQuantity;
    private Instant proCreatedTime;
    private Instant proUdatedTime;

    public Product() {
    }

    public Product(Long proId, String proName, Double proPrice, Integer proQuantity) {
        this.proId = proId;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proQuantity = proQuantity;
    }

    public Product(Long proId, String proName, Double proPrice, Integer proQuantity, Instant proCreatedTime, Instant proUdatedTime) {
        this.proId = proId;
        this.proName = proName;
        this.proPrice = proPrice;
        this.proQuantity = proQuantity;
        this.proCreatedTime = proCreatedTime;
        this.proUdatedTime = proUdatedTime;
    }
    public Long getProId() {
        return proId;
    }
    public void setProId(Long proId) {
        this.proId = proId;
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
    public Integer getProQuantity() {
        return proQuantity;
    }
    public void setProQuantity(Integer proQuantity) {
        this.proQuantity = proQuantity;
    }
    public Instant getProCreatedTime() {
        return proCreatedTime;
    }
    public void setProCreatedTime(Instant proCreatedTime) {
        this.proCreatedTime = proCreatedTime;
    }
    public Instant getProUpdatedTime() {
        return proUdatedTime;
    }
    public void setProUpdatedTime(Instant proUdatedTime) {
        this.proUdatedTime = proUdatedTime;
    }

    public static Product parse(String record) {
        String[] fields = record.split(", ");
        long proId = Long.parseLong(fields[0]);
        String proName = fields[1];
        double proPrice = Double.parseDouble(fields[2]);
        int proQuantity = Integer.parseInt(fields[3]);
        Instant proCreatedTime = Instant.parse(fields[4]);
        String temp = fields[5];
        Instant proUdatedTime = null;
        if (temp != null && !temp.equals("null")) {
            proUdatedTime = Instant.parse(temp);
        }
        return new Product(proId, proName, proPrice, proQuantity, proCreatedTime, proUdatedTime);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s",
                proId, proName, proPrice, proQuantity, proCreatedTime, proUdatedTime);
    }
}
