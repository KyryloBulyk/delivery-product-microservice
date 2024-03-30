package kyrylo.delivery.com.deliveryproductmicroservice.exceptions;

public class CreationFailedException extends RuntimeException {
    public CreationFailedException(String message) {
        super(message);
    }
}
