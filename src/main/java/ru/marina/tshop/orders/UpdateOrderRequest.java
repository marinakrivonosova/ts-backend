package ru.marina.tshop.orders;

public class UpdateOrderRequest {
    private String orderStatusId;

    public UpdateOrderRequest() {
    }

    public UpdateOrderRequest(final String orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getOrderStatusId() {
        return orderStatusId;
    }
}