package products;

import java.util.Objects;

public class Category {
    private String categoryId;
    private String name;
    private String description;

    public Category(final String categoryId, final String name, final String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
    }

    public Category() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryId.equals(category.categoryId) &&
                name.equals(category.name) &&
                description.equals(category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name, description);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId='" + categoryId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
