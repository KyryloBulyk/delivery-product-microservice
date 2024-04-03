package kyrylo.delivery.com.deliveryproductmicroservice.exceptions.categoryExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class CategoryAlreadyExistException extends RuntimeException {
    public CategoryAlreadyExistException(String name) {
        super("Category with " + name +  "is already");
    }
}
