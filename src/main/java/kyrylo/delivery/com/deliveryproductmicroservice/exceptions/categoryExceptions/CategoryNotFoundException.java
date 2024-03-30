package kyrylo.delivery.com.deliveryproductmicroservice.exceptions.categoryExceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
