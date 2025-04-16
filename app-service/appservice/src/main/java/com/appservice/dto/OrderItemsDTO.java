package com.appservice.dto;

import ors.common.model.OrderItemStatus;

public class OrderItemsDTO {

    private int quantity;
    private String orderId;
    private String productsId;
    private String specialInstruction;
    private OrderItemStatus status;
    private String sharedWithTable;
    private String createdBy;
    private String updatedBy;


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductsId() {
        return productsId;
    }

    public void setProductsId(String productsId) {
        this.productsId = productsId;
    }

    public String getSpecialInstruction() {
        return specialInstruction;
    }

    public void setSpecialInstruction(String specialInstruction) {
        this.specialInstruction = specialInstruction;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    public String getSharedWithTable() {
        return sharedWithTable;
    }

    public void setSharedWithTable(String sharedWithTable) {
        this.sharedWithTable = sharedWithTable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
