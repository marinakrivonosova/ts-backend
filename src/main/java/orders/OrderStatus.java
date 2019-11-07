package orders;

import java.util.Objects;

public class OrderStatus {
    private String orderStatusId;
    private String orderStatus;

    public OrderStatus(final String orderStatusId, final String orderStatus) {
        this.orderStatusId = orderStatusId;
        this.orderStatus = orderStatus;
    }

    public OrderStatus() {
    }

    public String getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(final String orderStatusId) {
        this.orderStatusId = orderStatusId;
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
        return orderStatusId.equals(that.orderStatusId) &&
                orderStatus.equals(that.orderStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderStatusId, orderStatus);
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "orderStatusId='" + orderStatusId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
