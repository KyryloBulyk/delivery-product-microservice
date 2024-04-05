package kyrylo.delivery.com.deliveryproductmicroservice.exceptions.orderException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long userId) {
        super("User with id " + userId + " is not found");
    }
}
