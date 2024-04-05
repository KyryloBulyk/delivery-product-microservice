package kyrylo.delivery.com.deliveryproductmicroservice.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Order items cannot be null")
    @Size(min = 1, message = "There must be at least one order item")
    private Set<OrderItem> orderItems = new HashSet<>();

    @NotNull(message = "Order date cannot be null")
    private LocalDateTime orderDate;

    public double getTotalCost() {
        return orderItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }
}
