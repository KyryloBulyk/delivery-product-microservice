package kyrylo.delivery.com.deliveryproductmicroservice.repositories;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String categoryName);
}
