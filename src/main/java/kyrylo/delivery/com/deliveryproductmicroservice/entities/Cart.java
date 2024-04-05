package kyrylo.delivery.com.deliveryproductmicroservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "carts")
public class Cart {

    @Id
    private String id;

    private Long userId;

    private Map<String, CartItem> items = new HashMap<>();

    public double getTotalCost() {
        return items.values().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    public void addItem(CartItem item) {
        if (items.containsKey(item.getProductId())) {
            CartItem existingItem = items.get(item.getProductId());
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            items.put(item.getProductId(), existingItem);
        } else {
            items.put(item.getProductId(), item);
        }
    }

    public void removeItem(String productId) {
        items.remove(productId);
    }

    public void updateItemQuantity(String productId, int quantity) {
        if (items.containsKey(productId)) {
            CartItem existingItem = items.get(productId);
            existingItem.setQuantity(quantity);
            items.put(productId, existingItem);
        }
    }

}
