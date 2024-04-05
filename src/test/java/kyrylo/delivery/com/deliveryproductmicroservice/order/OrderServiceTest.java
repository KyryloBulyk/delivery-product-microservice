package kyrylo.delivery.com.deliveryproductmicroservice.order;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Order;
import kyrylo.delivery.com.deliveryproductmicroservice.exceptions.orderException.OrderNotFoundException;
import kyrylo.delivery.com.deliveryproductmicroservice.exceptions.orderException.UserNotFoundException;
import kyrylo.delivery.com.deliveryproductmicroservice.repositories.OrderRepository;
import kyrylo.delivery.com.deliveryproductmicroservice.services.OrderService;
import kyrylo.delivery.com.deliveryproductmicroservice.services.UserClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserClient userClient;

    @InjectMocks
    private OrderService orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order("1", 1L, Set.of("product1", "product2"), LocalDateTime.now(), 100.0);
        when(userClient.existsById(anyLong())).thenReturn(ResponseEntity.ok(true));
    }

    @Test
    void getAllOrdersTest() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));
        List<Order> orders = orderService.getAllOrders();
        assertFalse(orders.isEmpty());
        assertEquals(1, orders.size());
        verify(orderRepository).findAll();
    }

    @Test
    void getOrderByIdTest() {
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        Order foundOrder = orderService.getOrderById(order.getId());
        assertNotNull(foundOrder);
        assertEquals(order.getId(), foundOrder.getId());
        verify(orderRepository).findById(order.getId());
    }

    @Test
    void getOrderByIdNotFoundTest() {
        when(orderRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById("2"));
        verify(orderRepository).findById("2");
    }

    @Test
    void createOrderTest() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Order newOrder = orderService.createOrder(order);
        assertNotNull(newOrder);
        assertEquals(order.getTotalCost(), newOrder.getTotalCost());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void createOrderWithNonExistingUserTest() {
        when(userClient.existsById(anyLong())).thenReturn(ResponseEntity.ok(false));
        assertThrows(UserNotFoundException.class, () -> orderService.createOrder(order));
    }

    @Test
    void updateOrderTest() {
        Order updatedDetails = new Order(order.getId(), 1L, Set.of("product3"), LocalDateTime.now(), 200.0);
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedDetails);

        Order updatedOrder = orderService.updateOrder(order.getId(), updatedDetails);
        assertNotNull(updatedOrder);
        assertEquals(updatedDetails.getTotalCost(), updatedOrder.getTotalCost());
        verify(orderRepository).findById(order.getId());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void deleteOrderTest() {
        when(orderRepository.existsById(order.getId())).thenReturn(true);
        doNothing().when(orderRepository).deleteById(order.getId());

        orderService.deleteOrder(order.getId());
        verify(orderRepository).deleteById(order.getId());
    }

    @Test
    void deleteOrderNotFoundTest() {
        when(orderRepository.existsById(anyString())).thenReturn(false);
        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder("2"));
        verify(orderRepository).existsById("2");
    }
}

