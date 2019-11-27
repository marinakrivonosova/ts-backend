package ru.marina.tshop.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.marina.tshop.utils.IdGenerator;

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

    @Transactional(readOnly = true)
    public List<Product> filterProducts(final String title, final long offset, final int pageSize) {
        return productDao.filterProducts(title, offset, pageSize);
    }

    @Transactional(readOnly = true)
    public Product getProduct(final String productId) {
        return productDao.getProduct(productId);
    }

    @Transactional(readOnly = true)
    public int getProductCount(final String title) {
        return productDao.getProductCount(title);
    }
}
