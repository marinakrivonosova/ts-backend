package entity;

import java.util.Objects;

public class OrderComment {
    private String orderCommentId;
    private String orderId;
    private String comment;

    public OrderComment(String orderCommentId, String orderId, String comment) {
        this.orderCommentId = orderCommentId;
        this.orderId = orderId;
        this.comment = comment;
    }

    public OrderComment() {
    }

    public String getOrderCommentId() {
        return orderCommentId;
    }

    public void setOrderCommentId(String orderCommentId) {
        this.orderCommentId = orderCommentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderComment that = (OrderComment) o;
        return orderCommentId.equals(that.orderCommentId) &&
                orderId.equals(that.orderId) &&
                comment.equals(that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderCommentId, orderId, comment);
    }

    @Override
    public String toString() {
        return "OrderComment{" +
                "orderCommentId='" + orderCommentId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
