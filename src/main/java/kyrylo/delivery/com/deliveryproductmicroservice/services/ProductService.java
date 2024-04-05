package kyrylo.delivery.com.deliveryproductmicroservice.services;

import kyrylo.delivery.com.deliveryproductmicroservice.dto.RequestProduct;
import kyrylo.delivery.com.deliveryproductmicroservice.entities.Category;
import kyrylo.delivery.com.deliveryproductmicroservice.entities.Product;
import kyrylo.delivery.com.deliveryproductmicroservice.exceptions.categoryExceptions.CategoryNotFoundException;
import kyrylo.delivery.com.deliveryproductmicroservice.exceptions.productExceptions.ProductAlreadyExistsException;
import kyrylo.delivery.com.deliveryproductmicroservice.exceptions.productExceptions.ProductNotFoundException;
import kyrylo.delivery.com.deliveryproductmicroservice.repositories.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        logger.info("Fetching all products. Total products found: {}", products.size());
        return products;
    }

    public Product getProductById(String id) {
        logger.info("Fetching product by id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public Product createProduct(RequestProduct requestProduct) {
        logger.info("Creating product with name: {}", requestProduct.name());
        if (productRepository.existsByName(requestProduct.name())) {
            logger.error("Product creation failed. Product already exists with name: {}", requestProduct.name());
            throw new ProductAlreadyExistsException("Product already exists with name: " + requestProduct.name());
        }

        Category category = categoryService.getCategoryByName(requestProduct.categoryName());
        if (category == null) {
            logger.error("Product creation failed. Category not found with name: {}", requestProduct.categoryName());
            throw new CategoryNotFoundException("Category not found with name: " + requestProduct.categoryName());
        }

        Product product = new Product();
        product.setName(requestProduct.name());
        product.setPrice(requestProduct.price());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        logger.info("Product created successfully with id: {}", savedProduct.getId());
        return savedProduct;
    }

    public Product updateProduct(String id, RequestProduct productDetails) {
        logger.info("Updating product with id: {}", id);
        Product existingProduct = getProductById(id);
        if (!existingProduct.getName().equals(productDetails.name()) && productRepository.existsByName(productDetails.name())) {
            logger.error("Product update failed. Product already exists with name: {}", productDetails.name());
            throw new ProductAlreadyExistsException("Product already exists with name: " + productDetails.name());
        }

        Category category = categoryService.getCategoryByName(productDetails.categoryName());
        if (category == null) {
            logger.error("Product update failed. Category not found with name: {}", productDetails.categoryName());
            throw new CategoryNotFoundException("Category not found with name: " + productDetails.categoryName());
        }

        existingProduct.setName(productDetails.name());
        existingProduct.setPrice(productDetails.price());
        existingProduct.setCategory(category);

        Product updatedProduct = productRepository.save(existingProduct);
        logger.info("Product updated successfully with id: {}", updatedProduct.getId());
        return updatedProduct;
    }

    public void deleteProduct(String id) {
        logger.info("Deleting product with id: {}", id);
        if (!productRepository.existsById(id)) {
            logger.error("Product deletion failed. Product not found with id: {}", id);
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        logger.info("Product deleted successfully with id: {}", id);
    }

    public void existByName(String productName) {
        productRepository.findByName(productName)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with name " + productName));
    }
}
