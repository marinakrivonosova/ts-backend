package entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private String productId;
    private String name;
    private String description;
    private BigDecimal price;
    private int weight;
    private int volume;
    private int count;
    private String categoryId;

    public Product(final String productId, final String name, final String description, final BigDecimal price,
                   final int weight, final int volume, final int count, final String categoryId) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.weight = weight;
        this.volume = volume;
        this.count = count;
        this.categoryId = categoryId;
    }

    public Product() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return weight == product.weight &&
                volume == product.volume &&
                count == product.count &&
                productId.equals(product.productId) &&
                name.equals(product.name) &&
                description.equals(product.description) &&
                price.equals(product.price) &&
                categoryId.equals(product.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, description, price, weight, volume, count, categoryId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", volume=" + volume +
                ", count=" + count +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
