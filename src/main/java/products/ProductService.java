package products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.IdGenerator;

import java.util.List;

@Service
public class ProductService {
    private final ProductDao productDao;
    private final IdGenerator idGenerator;

    @Autowired
    public ProductService(final ProductDao productDao, final IdGenerator idGenerator) {
        this.productDao = productDao;
        this.idGenerator = idGenerator;
    }

    public List<Product> filterProducts(final String title, final long offset, final int pageSize) {
        return productDao.filterProducts(title,offset, pageSize);
    }
    public Product getProduct(final String productId){
        return productDao.getProduct(productId);
    }
}
