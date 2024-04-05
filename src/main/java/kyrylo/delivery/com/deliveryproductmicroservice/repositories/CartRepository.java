package kyrylo.delivery.com.deliveryproductmicroservice.repositories;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByUserId(Long userId);
}
