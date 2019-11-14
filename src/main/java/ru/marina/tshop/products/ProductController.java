package ru.marina.tshop.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public FilterProductResponse filterProducts(@RequestParam("title") final String title,
                                                @RequestParam("offset") final int offset,
                                                @RequestParam("limit") final int limit) {
        final FilterProductResponse response = new FilterProductResponse();
        response.products = productService.filterProducts(title, offset, limit);
        response.overallSuitableProducts = productService.getProductCount(title);
        return response;
    }

    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable("productId") final String productId) {
        return productService.getProduct(productId);
    }
}
