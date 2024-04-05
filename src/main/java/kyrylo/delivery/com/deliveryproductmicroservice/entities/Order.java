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

    @NotNull(message = "Product names cannot be null")
    @Size(min = 1, message = "There must be at least one product name")
    private Set<String> productNames;

    @NotNull(message = "Order date cannot be null")
    private LocalDateTime orderDate;

    @Min(value = 1, message = "Total cost must be greater than 0")
    private double totalCost;
}
