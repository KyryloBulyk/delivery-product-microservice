package kyrylo.delivery.com.deliveryproductmicroservice.services;

import kyrylo.delivery.com.deliveryproductmicroservice.dto.RequestProduct;
import kyrylo.delivery.com.deliveryproductmicroservice.entities.Category;
import kyrylo.delivery.com.deliveryproductmicroservice.entities.Product;
import kyrylo.delivery.com.deliveryproductmicroservice.exceptions.categoryExceptions.CategoryNotFoundException;
import kyrylo.delivery.com.deliveryproductmicroservice.exceptions.productExceptions.ProductNotFoundException;
import kyrylo.delivery.com.deliveryproductmicroservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(RequestProduct requestProduct) {
        Product product = new Product();
        product.setName(requestProduct.name());
        product.setPrice(requestProduct.price());

        Category category = categoryService.getCategoryByName(requestProduct.categoryName());
        if (category == null) {
            throw new CategoryNotFoundException("Category not found with name: " + requestProduct.categoryName());
        }
        product.setCategory(category);

        return productRepository.save(product);
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public Product updateProduct(String id, RequestProduct productDetails) {
        Product product = getProductById(id);
        product.setName(productDetails.name());
        product.setPrice(productDetails.price());

        Category category = categoryService.getCategoryByName(productDetails.categoryName());
        if (category == null) {
            throw new CategoryNotFoundException("Category not found with name: " + productDetails.categoryName());
        }
        product.setCategory(category);

        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
