package kyrylo.delivery.com.deliveryproductmicroservice.services;

import kyrylo.delivery.com.deliveryproductmicroservice.configuration.UserClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "delivery-users-microservice", configuration = UserClientConfiguration.class)
public interface UserClient {

    @GetMapping("/api/users/exists/{userId}")
    ResponseEntity<Boolean> existsById(@PathVariable Long userId);
}

