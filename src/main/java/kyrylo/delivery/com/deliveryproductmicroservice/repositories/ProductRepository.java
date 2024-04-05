package kyrylo.delivery.com.deliveryproductmicroservice.repositories;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsByName(String name);

    Optional<Product> findByName(String productName);
}
