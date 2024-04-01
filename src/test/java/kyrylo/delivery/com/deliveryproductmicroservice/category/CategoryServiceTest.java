package kyrylo.delivery.com.deliveryproductmicroservice.category;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Category;
import kyrylo.delivery.com.deliveryproductmicroservice.exceptions.categoryExceptions.CategoryNotFoundException;
import kyrylo.delivery.com.deliveryproductmicroservice.repositories.CategoryRepository;
import kyrylo.delivery.com.deliveryproductmicroservice.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category("1", "Electronics");
    }

    @Test
    void createCategoryTest() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        Category createdCategory = categoryService.createCategory(category);
        assertNotNull(createdCategory);
        assertEquals(category.getName(), createdCategory.getName());
    }

    @Test
    void getAllCategoriesTest() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        List<Category> categories = categoryService.getAllCategories();
        assertFalse(categories.isEmpty());
        assertEquals(1, categories.size());
    }

    @Test
    void getCategoryByIdTest() {
        when(categoryRepository.findById(category.getCategoryId())).thenReturn(Optional.of(category));
        Category foundCategory = categoryService.getCategoryById(category.getCategoryId());
        assertNotNull(foundCategory);
        assertEquals(category.getName(), foundCategory.getName());
    }

    @Test
    void getCategoryByIdNotFoundTest() {
        when(categoryRepository.findById("2")).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryService.getCategoryById("2"));
    }

    @Test
    void updateCategoryTest() {
        when(categoryRepository.findById(category.getCategoryId())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category updatedCategory = new Category(category.getCategoryId(), "Updated Electronics");
        Category result = categoryService.updateCategory(category.getCategoryId(), updatedCategory);

        assertNotNull(result);
        assertEquals(updatedCategory.getName(), result.getName());
    }

    @Test
    void deleteCategoryTest() {
        when(categoryRepository.existsById(category.getCategoryId())).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(category.getCategoryId());
        categoryService.deleteCategory(category.getCategoryId());
        verify(categoryRepository, times(1)).deleteById(category.getCategoryId());
    }

    @Test
    void deleteCategoryNotFoundTest() {
        when(categoryRepository.existsById("2")).thenReturn(false);
        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory("2"));
    }

    @Test
    void getCategoryByNameTest() {
        when(categoryRepository.findByName(category.getName())).thenReturn(category);
        Category foundCategory = categoryService.getCategoryByName(category.getName());
        assertNotNull(foundCategory);
        assertEquals(category.getName(), foundCategory.getName());
    }
}
