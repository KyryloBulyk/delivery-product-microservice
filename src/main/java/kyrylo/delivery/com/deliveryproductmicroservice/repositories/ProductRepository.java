package kyrylo.delivery.com.deliveryproductmicroservice.repositories;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
