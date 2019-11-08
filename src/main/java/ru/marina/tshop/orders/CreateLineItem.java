package ru.marina.tshop.orders;

import java.util.Objects;

public class CreateLineItem {
    private String productId;
    private int count;

    public CreateLineItem() {
    }

    public CreateLineItem(final String productId, final int count) {
        this.productId = productId;
        this.count = count;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final CreateLineItem that = (CreateLineItem) o;
        return count == that.count &&
                productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, count);
    }

    @Override
    public String toString() {
        return "CreateLineItem{" +
                "productId='" + productId + '\'' +
                ", count=" + count +
                '}';
    }
}
