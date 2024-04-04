package kyrylo.delivery.com.deliveryproductmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DeliveryProductMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryProductMicroserviceApplication.class, args);
	}

}
