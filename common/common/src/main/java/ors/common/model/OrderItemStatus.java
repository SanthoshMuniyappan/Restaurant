package ors.common.model;

public enum OrderItemStatus {

    ORDERED("01"),
    PREPARING("02"),
    READY("03"),
    DELIVERED("04"),
    PAID("05");

    private final String status;

    OrderItemStatus(String status){
        this.status=status;
    }

    public String getStatus(){
        return status;
    }
}
