package kyrylo.delivery.com.deliveryproductmicroservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cartItems")
public class CartItem {

    private String productId;
    private int quantity;
    private double price;
}
