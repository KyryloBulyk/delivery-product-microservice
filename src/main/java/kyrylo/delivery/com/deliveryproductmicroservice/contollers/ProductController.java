package kyrylo.delivery.com.deliveryproductmicroservice.contollers;

import kyrylo.delivery.com.deliveryproductmicroservice.dto.RequestProduct;
import kyrylo.delivery.com.deliveryproductmicroservice.entities.Product;
import kyrylo.delivery.com.deliveryproductmicroservice.services.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LogManager.getLogger(ProductController.class);
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody RequestProduct requestProduct) {
        logger.info("Creating product: {}", requestProduct);
        Product createdProduct = productService.createProduct(requestProduct);
        logger.info("Product created successfully: {}", createdProduct);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> products = productService.getAllProducts();
        logger.info("Total products fetched: {}", products.size());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        logger.info("Fetching product with ID: {}", id);
        Product product = productService.getProductById(id);
        logger.info("Product fetched successfully: {}", product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody RequestProduct productDetails) {
        logger.info("Updating product with ID: {}", id);
        Product updatedProduct = productService.updateProduct(id, productDetails);
        logger.info("Product updated successfully: {}", updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        logger.info("Deleting product with ID: {}", id);
        productService.deleteProduct(id);
        logger.info("Product deleted successfully");
        return ResponseEntity.ok().build();
    }
}
