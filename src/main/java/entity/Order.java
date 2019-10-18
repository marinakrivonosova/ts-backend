package entity;

import enums.DeliveryMethod;
import enums.OrderStatus;
import enums.PaymentMethod;
import enums.PaymentStatus;

import java.util.Objects;

public class Order {
    private String orderId;
    private String userId;
    private String address;
    private OrderStatus orderStatus;
    private DeliveryMethod deliveryMethod;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String comment;

    public Order(final String orderId, final String userId, final String address, final OrderStatus orderStatus, final DeliveryMethod deliveryMethod,
                 final PaymentMethod paymentMethod, final PaymentStatus paymentStatus, final String comment) {
        this.orderId = orderId;
        this.userId = userId;
        this.address = address;
        this.orderStatus = orderStatus;
        this.deliveryMethod = deliveryMethod;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.comment = comment;
    }

    public Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(final String orderId) {
        this.orderId = orderId;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(final OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public DeliveryMethod getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(final DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(final PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(final PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId.equals(order.orderId) &&
                userId.equals(order.userId) &&
                address.equals(order.address) &&
                orderStatus == order.orderStatus &&
                deliveryMethod == order.deliveryMethod &&
                paymentMethod == order.paymentMethod &&
                paymentStatus == order.paymentStatus &&
                comment.equals(order.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, address, orderStatus, deliveryMethod, paymentMethod, paymentStatus, comment);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                ", orderStatus=" + orderStatus +
                ", deliveryMethod=" + deliveryMethod +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                ", comment='" + comment + '\'' +
                '}';
    }
}
