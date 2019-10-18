package entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Goods {
    private String goodsId;
    private String title;
    private BigDecimal price;
    private int weight;
    private int volume;
    private int count;
    private String categoryId;

    public Goods(final String goodsId, final String title, final BigDecimal price, final int weight, final int volume, final int count, final String categoryId) {
        this.goodsId = goodsId;
        this.title = title;
        this.price = price;
        this.weight = weight;
        this.volume = volume;
        this.count = count;
        this.categoryId = categoryId;
    }

    public Goods() {
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(final String goodsId) {
        this.goodsId = goodsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(final int weight) {
        this.weight = weight;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(final int volume) {
        this.volume = volume;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Goods goods = (Goods) o;
        return weight == goods.weight &&
                volume == goods.volume &&
                count == goods.count &&
                goodsId.equals(goods.goodsId) &&
                title.equals(goods.title) &&
                price.equals(goods.price) &&
                categoryId.equals(goods.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goodsId, title, price, weight, volume, count, categoryId);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId='" + goodsId + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                ", volume=" + volume +
                ", count=" + count +
                ", categoryId='" + categoryId + '\'' +
                '}';
    }
}
