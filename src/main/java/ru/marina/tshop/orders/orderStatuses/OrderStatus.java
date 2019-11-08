package ru.marina.tshop.orders.orderStatuses;

import java.util.Objects;

public class OrderStatus {
    private String id;
    private String orderStatus;

    public OrderStatus(final String id, final String orderStatus) {
        this.id = id;
        this.orderStatus = orderStatus;
    }

    public OrderStatus() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(final String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatus that = (OrderStatus) o;
        return id.equals(that.id) &&
                orderStatus.equals(that.orderStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderStatus);
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "orderStatusId='" + id + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
