package ru.marina.tshop.orders;

import java.util.Objects;

public class Order {
    private String id;
    private String userId;
    private String address;
    private String deliveryMethodId;
    private String paymentMethodId;
    private String orderStatusId;
    private String paymentStatusId;

    public Order(final String id, final String userId, final String address, final String orderStatusId,
                 final String deliveryMethodId, final String paymentMethodId, final String paymentStatusId) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.orderStatusId = orderStatusId;
        this.deliveryMethodId = deliveryMethodId;
        this.paymentMethodId = paymentMethodId;
        this.paymentStatusId = paymentStatusId;
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(final String orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(final String deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(final String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getPaymentStatusId() {
        return paymentStatusId;
    }

    public void setPaymentStatusId(final String paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Order order = (Order) o;
        return id.equals(order.id) &&
                userId.equals(order.userId) &&
                address.equals(order.address) &&
                deliveryMethodId.equals(order.deliveryMethodId) &&
                paymentMethodId.equals(order.paymentMethodId) &&
                orderStatusId.equals(order.orderStatusId) &&
                paymentStatusId.equals(order.paymentStatusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, address, deliveryMethodId, paymentMethodId, orderStatusId, paymentStatusId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                ", deliveryMethodId='" + deliveryMethodId + '\'' +
                ", paymentMethodId='" + paymentMethodId + '\'' +
                ", orderStatusId='" + orderStatusId + '\'' +
                ", paymentStatusId='" + paymentStatusId + '\'' +
                '}';
    }
}