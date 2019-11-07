package orders.lineitems;

import java.math.BigDecimal;
import java.util.Objects;

public class LineItem {
    private String id;
    private String orderId;
    private String productId;
    private int count;
    private BigDecimal price;

    public LineItem() {
    }

    public LineItem(final String id, final String orderId, final String productId, final int count, final BigDecimal price) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.count = count;
        this.price = price;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(final String productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final LineItem lineItem = (LineItem) o;
        return count == lineItem.count &&
                id.equals(lineItem.id) &&
                orderId.equals(lineItem.orderId) &&
                productId.equals(lineItem.productId) &&
                price.equals(lineItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, productId, count, price);
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", productId='" + productId + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}