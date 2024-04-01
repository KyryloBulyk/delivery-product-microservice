package kyrylo.delivery.com.deliveryproductmicroservice.repositories;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByName(String categoryName);
}
