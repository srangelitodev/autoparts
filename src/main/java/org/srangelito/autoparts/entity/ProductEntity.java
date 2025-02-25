package org.srangelito.autoparts.entity;

public class ProductEntity {

    private Short quantity;
    private String partNumber;
    private String application;
    private Float privatePrice;
    private Float publicPrice;

    public ProductEntity(Short quantity, String partNumber, String application, Float privatePrice, Float publicPrice) {
        this.quantity = quantity;
        this.partNumber = partNumber;
        this.application = application;
        this.privatePrice = privatePrice;
        this.publicPrice = publicPrice;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "quantity=" + quantity +
                ", partNumber='" + partNumber + '\'' +
                ", application='" + application + '\'' +
                ", privatePrice=" + privatePrice +
                ", publicPrice=" + publicPrice +
                '}';
    }
}
