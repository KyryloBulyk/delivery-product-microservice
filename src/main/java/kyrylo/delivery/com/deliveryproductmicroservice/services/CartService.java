package kyrylo.delivery.com.deliveryproductmicroservice.services;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Cart;
import kyrylo.delivery.com.deliveryproductmicroservice.entities.CartItem;
import kyrylo.delivery.com.deliveryproductmicroservice.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CartService {

    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> new Cart(null, userId, new HashMap<>()));
    }

    public Cart addItemToCart(Long userId, CartItem item) {
        Cart cart = getCartByUserId(userId);
        cart.addItem(item);
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long userId, String productId) {
        Cart cart = getCartByUserId(userId);
        cart.removeItem(productId);
        return cartRepository.save(cart);
    }

    public Cart updateItemInCart(Long userId, String productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        cart.updateItemQuantity(productId, quantity);
        return cartRepository.save(cart);
    }

    public Cart clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
}
