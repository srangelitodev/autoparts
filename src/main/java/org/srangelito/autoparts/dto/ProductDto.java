package org.srangelito.autoparts.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductDto {

    private Short quantity;
    private String partNumber;
    private String application;
    private Float privatePrice;
    private Float publicPrice;

    public ProductDto(Short quantity, String partNumber, String application, Float privatePrice, Float publicPrice) {
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
