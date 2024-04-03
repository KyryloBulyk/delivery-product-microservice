package kyrylo.delivery.com.deliveryproductmicroservice.services;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Category;
import kyrylo.delivery.com.deliveryproductmicroservice.exceptions.categoryExceptions.CategoryAlreadyExistException;
import kyrylo.delivery.com.deliveryproductmicroservice.exceptions.categoryExceptions.CategoryNotFoundException;
import kyrylo.delivery.com.deliveryproductmicroservice.repositories.CategoryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private static final Logger logger = LogManager.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        logger.info("Creating category with name: {}", category.getName());
        if (categoryRepository.existsByName(category.getName())) {
            logger.warn("Category creation failed. Category already exists with name: {}", category.getName());
            throw new CategoryAlreadyExistException("Category already exists with name: " + category.getName());
        }
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        logger.info("Fetching all categories");
        return categoryRepository.findAll();
    }

    public Category getCategoryById(String id) {
        logger.info("Fetching category with ID: {}", id);
        return categoryRepository.findById(id).orElseThrow(() -> {
            logger.error("Category not found with id: {}", id);
            return new CategoryNotFoundException("Category not found with id: " + id);
        });
    }

    public Category updateCategory(String id, Category categoryDetails) {
        logger.info("Updating category with ID: {}", id);
        Category existingCategory = getCategoryById(id);
        Category categoryByName = categoryRepository.findByName(categoryDetails.getName());
        if (categoryByName != null && !categoryByName.getCategoryId().equals(id)) {
            logger.warn("Category update failed. Category already exists with name: {}", categoryDetails.getName());
            throw new CategoryAlreadyExistException("Category already exists with name: " + categoryDetails.getName());
        }
        existingCategory.setName(categoryDetails.getName());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(String id) {
        logger.info("Deleting category with ID: {}", id);
        if (!categoryRepository.existsById(id)) {
            logger.error("Category deletion failed. Category not found with id: {}", id);
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    public Category getCategoryByName(String categoryName) {
        logger.info("Fetching category by name: {}", categoryName);
        return categoryRepository.findByName(categoryName);
    }
}
