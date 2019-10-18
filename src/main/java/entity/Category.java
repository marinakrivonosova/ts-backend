package entity;

import java.util.Objects;

public class Category {
    private int categoryId;
    private String name;

    public Category(final int categoryId, final String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    public Category() {
    }

    public void setCategoryId(final int categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryId == category.categoryId &&
                name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}
