package kyrylo.delivery.com.deliveryproductmicroservice.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    private String categoryId;

    @NotBlank(message = "Category name cannot be blank")
    @Size(min = 1, max = 50, message = "Category name must be between 1 and 50 characters")
    private String name;
}