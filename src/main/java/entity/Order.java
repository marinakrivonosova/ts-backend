package entity;

import java.util.Objects;

public class Order {
    private String orderId;
    private String userId;
    private String address;
    private String orderStatusId;
    private String deliveryMethodId;
    private String paymentMethodId;
    private String paymentStatusId;

    public Order(String orderId, String userId, String address, String orderStatusId, String deliveryMethodId, String paymentMethodId, String paymentStatusId) {
        this.orderId = orderId;
        this.userId = userId;
        this.address = address;
        this.orderStatusId = orderStatusId;
        this.deliveryMethodId = deliveryMethodId;
        this.paymentMethodId = paymentMethodId;
        this.paymentStatusId = paymentStatusId;
    }

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(String orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(String deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentStatusId() {
        return paymentStatusId;
    }

    public void setPaymentStatusId(String paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId.equals(order.orderId) &&
                userId.equals(order.userId) &&
                address.equals(order.address) &&
                orderStatusId.equals(order.orderStatusId) &&
                deliveryMethodId.equals(order.deliveryMethodId) &&
                paymentMethodId.equals(order.paymentMethodId) &&
                paymentStatusId.equals(order.paymentStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, address, orderStatusId, deliveryMethodId, paymentMethodId, paymentStatusId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                ", orderStatusId='" + orderStatusId + '\'' +
                ", deliveryMethodId='" + deliveryMethodId + '\'' +
                ", paymentMethodId='" + paymentMethodId + '\'' +
                ", paymentStatusId='" + paymentStatusId + '\'' +
                '}';
    }
}
