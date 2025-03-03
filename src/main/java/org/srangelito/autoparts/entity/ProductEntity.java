package org.srangelito.autoparts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "product")
public class ProductEntity {

    private Short quantity;
    @Id
    @Column (name = "part_number")
    private String partNumber;
    private String application;
    @Column (name = "private_price")
    private Float privatePrice;
    @Column (name = "public_price")
    private Float publicPrice;

    public ProductEntity() {
    }

    public ProductEntity(Short quantity, String partNumber, String application, Float privatePrice, Float publicPrice) {
        this.quantity = quantity;
        this.partNumber = partNumber;
        this.application = application;
        this.privatePrice = privatePrice;
        this.publicPrice = publicPrice;
    }

    public Short getQuantity() {
        return this.quantity;
    }

    public String getPartNumber() {
        return this.partNumber;
    }

    public String getApplication() {
        return this.application;
    }

    public Float getPrivatePrice() {
        return this.privatePrice;
    }

    public Float getPublicPrice() {
        return this.publicPrice;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void setPrivatePrice(Float privatePrice) {
        this.privatePrice = privatePrice;
    }

    public void setPublicPrice(Float publicPrice) {
        this.publicPrice = publicPrice;
    }

}
