package ru.marina.tshop.orders;

import java.util.Objects;

public class OrderComment {
    private String id;
    private String orderId;
    private String comment;

    public OrderComment(final String id, final String orderId, final String comment) {
        this.id = id;
        this.orderId = orderId;
        this.comment = comment;
    }

    public OrderComment() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(final String orderId) {
        this.orderId = orderId;
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
        OrderComment that = (OrderComment) o;
        return id.equals(that.id) &&
                orderId.equals(that.orderId) &&
                comment.equals(that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, comment);
    }

    @Override
    public String toString() {
        return "OrderComment{" +
                "orderCommentId='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}