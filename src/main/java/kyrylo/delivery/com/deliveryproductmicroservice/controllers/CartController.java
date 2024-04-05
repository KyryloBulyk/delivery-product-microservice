package kyrylo.delivery.com.deliveryproductmicroservice.controllers;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Cart;
import kyrylo.delivery.com.deliveryproductmicroservice.entities.CartItem;
import kyrylo.delivery.com.deliveryproductmicroservice.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Cart> createCartForUser(@PathVariable Long userId) {
        Cart existingCart = cartService.getCartByUserId(userId);
        if (existingCart.getId() != null) {
            return ResponseEntity.ok(existingCart);
        }

        Cart newCart = new Cart(null, userId, new HashMap<>());
        Cart savedCart = cartService.createCart(newCart);
        return ResponseEntity.ok(savedCart);
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long userId, @RequestBody CartItem item) {
        Cart cart = cartService.addItemToCart(userId, item);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long userId, @PathVariable String productId) {
        Cart cart = cartService.removeItemFromCart(userId, productId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<Cart> updateItemInCart(@PathVariable Long userId, @PathVariable String productId, @RequestParam int quantity) {
        Cart cart = cartService.updateItemInCart(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<Cart> clearCart(@PathVariable Long userId) {
        Cart cart = cartService.clearCart(userId);
        return ResponseEntity.ok(cart);
    }
}
