package kyrylo.delivery.com.deliveryproductmicroservice.cart;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Cart;
import kyrylo.delivery.com.deliveryproductmicroservice.entities.CartItem;
import kyrylo.delivery.com.deliveryproductmicroservice.repositories.CartRepository;
import kyrylo.delivery.com.deliveryproductmicroservice.services.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    private Cart cart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cart = new Cart("1", 1L, new HashMap<>());
    }

    @Test
    void createCartTest() {
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        Cart newCart = cartService.createCart(new Cart());
        assertNotNull(newCart);
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void getCartByUserIdTest() {
        when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.of(cart));
        Cart foundCart = cartService.getCartByUserId(1L);
        assertNotNull(foundCart);
        assertEquals(1L, foundCart.getUserId());
        verify(cartRepository).findByUserId(anyLong());
    }

    @Test
    void addItemToCartTest() {
        CartItem item = new CartItem("productId", 2, 20.0);
        when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.addItemToCart(1L, item);
        assertNotNull(updatedCart);
        assertTrue(updatedCart.getItems().containsKey("productId"));
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void removeItemFromCartTest() {
        cart.addItem(new CartItem("productId", 1, 10.0));
        when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.removeItemFromCart(1L, "productId");
        assertFalse(updatedCart.getItems().containsKey("productId"));
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void updateItemInCartTest() {
        cart.addItem(new CartItem("productId", 1, 10.0));
        when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.updateItemInCart(1L, "productId", 3);
        assertEquals(3, updatedCart.getItems().get("productId").getQuantity());
        verify(cartRepository).save(any(Cart.class));
    }

    @Test
    void clearCartTest() {
        cart.addItem(new CartItem("productId", 1, 10.0));
        when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart updatedCart = cartService.clearCart(1L);
        assertTrue(updatedCart.getItems().isEmpty());
        verify(cartRepository).save(any(Cart.class));
    }
}
