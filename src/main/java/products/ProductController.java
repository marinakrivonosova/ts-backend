package products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> filterProducts(@RequestParam("title") final String title,
                                        @RequestParam("offset") final int offset,
                                        @RequestParam("limit") final int limit) {
        return productService.filterProducts(title, offset, limit);
    }

    @GetMapping("/products/{productId}")
    public Product getProduct(@PathVariable("productId") final String productId) {
        return productService.getProduct(productId);
    }
}
