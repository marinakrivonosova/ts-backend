package ru.marina.tshop.products;

import java.util.List;

class FilterProductResponse {
    private List<Product> products;
    private int overallSuitableProducts;

    public FilterProductResponse() {
    }

    public FilterProductResponse(List<Product> products, int overallSuitableProducts) {
        this.products = products;
        this.overallSuitableProducts = overallSuitableProducts;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getOverallSuitableProducts() {
        return overallSuitableProducts;
    }

    public void setOverallSuitableProducts(int overallSuitableProducts) {
        this.overallSuitableProducts = overallSuitableProducts;
    }
}