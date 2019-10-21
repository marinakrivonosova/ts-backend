package entity;

import java.util.Objects;

public class ProductAttribute {
    private String productAttributesId;
    private String productId;
    private String name;
    private String value;

    public ProductAttribute(final String productAttributesId, final String productId, final String name, final String value) {
        this.productAttributesId = productAttributesId;
        this.productId = productId;
        this.name = name;
        this.value = value;
    }

   public ProductAttribute(){}

    public String getProductAttributesId() {
        return productAttributesId;
    }

    public void setProductAttributesId(String productAttributesId) {
        this.productAttributesId = productAttributesId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductAttribute that = (ProductAttribute) o;
        return productAttributesId.equals(that.productAttributesId) &&
                productId.equals(that.productId) &&
                name.equals(that.name) &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productAttributesId, productId, name, value);
    }

    @Override
    public String toString() {
        return "ProductAttribute{" +
                "productAttributesId='" + productAttributesId + '\'' +
                ", productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
