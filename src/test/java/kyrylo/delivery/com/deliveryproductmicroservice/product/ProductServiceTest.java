package kyrylo.delivery.com.deliveryproductmicroservice.product;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import kyrylo.delivery.com.deliveryproductmicroservice.dto.RequestProduct;
import kyrylo.delivery.com.deliveryproductmicroservice.entities.Category;
import kyrylo.delivery.com.deliveryproductmicroservice.entities.Product;
import kyrylo.delivery.com.deliveryproductmicroservice.repositories.ProductRepository;
import kyrylo.delivery.com.deliveryproductmicroservice.services.CategoryService;
import kyrylo.delivery.com.deliveryproductmicroservice.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private RequestProduct requestProduct;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category("1", "Electronics");
        product = new Product("1", "Laptop", 999.99, category);
        requestProduct = new RequestProduct("Laptop", 999.99, "Electronics");
    }

    @Test
    void getAllProductsTest() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        List<Product> products = productService.getAllProducts();
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void createProductTest() {
        when(categoryService.getCategoryByName(requestProduct.categoryName())).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product createdProduct = productService.createProduct(requestProduct);

        assertNotNull(createdProduct);
        assertEquals(requestProduct.name(), createdProduct.getName());
        assertEquals(requestProduct.price(), createdProduct.getPrice());
    }

    @Test
    void createProductWithNonExistentCategoryTest() {
        when(categoryService.getCategoryByName(anyString())).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> productService.createProduct(requestProduct));
        assertEquals("Category not found with name: " + requestProduct.categoryName(), exception.getMessage());
    }

    @Test
    void getProductByIdTest() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        Product foundProduct = productService.getProductById(product.getId());
        assertNotNull(foundProduct);
        assertEquals(product.getId(), foundProduct.getId());
    }

    @Test
    void getProductByNonExistentIdTest() {
        when(productRepository.findById(anyString())).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> productService.getProductById("2"));
        assertEquals("Product not found with id: 2", exception.getMessage());
    }

    @Test
    void updateProductTest() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(categoryService.getCategoryByName(requestProduct.categoryName())).thenReturn(category);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product updatedProduct = productService.updateProduct(product.getId(), requestProduct);

        assertNotNull(updatedProduct);
        assertEquals(requestProduct.name(), updatedProduct.getName());
    }

    @Test
    void deleteProductTest() {
        when(productRepository.existsById(product.getId())).thenReturn(true);
        doNothing().when(productRepository).deleteById(product.getId());

        productService.deleteProduct(product.getId());
        verify(productRepository, times(1)).deleteById(product.getId());
    }

    @Test
    void deleteNonExistentProductTest() {
        when(productRepository.existsById(anyString())).thenReturn(false);
        Exception exception = assertThrows(RuntimeException.class, () -> productService.deleteProduct("2"));
        assertEquals("Product not found with id: 2", exception.getMessage());
    }
}
