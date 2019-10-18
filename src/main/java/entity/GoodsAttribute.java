package entity;

import java.util.Objects;

public class GoodsAttribute {
    private String goodsAttributesId;
    private String goodsId;
    private String name;
    private String value;

    public GoodsAttribute(final String goodsAttributesId, final String goodsId, final String name, final String value) {
        this.goodsAttributesId = goodsAttributesId;
        this.goodsId = goodsId;
        this.name = name;
        this.value = value;
    }

    public String getGoodsAttributesId() {
        return goodsAttributesId;
    }

    public void setGoodsAttributesId(final String goodsAttributesId) {
        this.goodsAttributesId = goodsAttributesId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(final String goodsId) {
        this.goodsId = goodsId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public GoodsAttribute() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsAttribute that = (GoodsAttribute) o;
        return goodsAttributesId.equals(that.goodsAttributesId) &&
                goodsId.equals(that.goodsId) &&
                name.equals(that.name) &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goodsAttributesId, goodsId, name, value);
    }

    @Override
    public String toString() {
        return "GoodsAttribute{" +
                "goodsAttributesId='" + goodsAttributesId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
