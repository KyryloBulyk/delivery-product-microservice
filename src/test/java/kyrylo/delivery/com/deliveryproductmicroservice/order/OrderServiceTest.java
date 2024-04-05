package kyrylo.delivery.com.deliveryproductmicroservice.order;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Order;
import kyrylo.delivery.com.deliveryproductmicroservice.entities.OrderItem;
import kyrylo.delivery.com.deliveryproductmicroservice.entities.Product;
import kyrylo.delivery.com.deliveryproductmicroservice.exceptions.orderException.OrderNotFoundException;
import kyrylo.delivery.com.deliveryproductmicroservice.repositories.OrderRepository;
import kyrylo.delivery.com.deliveryproductmicroservice.services.OrderService;
import kyrylo.delivery.com.deliveryproductmicroservice.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private Set<OrderItem> orderItems;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderItems = new HashSet<>();
        orderItems.add(new OrderItem("1", "product1", 2, 50.0));
        orderItems.add(new OrderItem("2", "product2", 1, 50.0));
        order = new Order("1", 1L, orderItems, LocalDateTime.now());

        when(productService.getProductById(anyString())).thenReturn(mock(Product.class));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    void getAllOrdersTest() {
        when(orderRepository.findAll()).thenReturn(List.of(order));
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
        Order createdOrder = orderService.createOrder(order);
        assertNotNull(createdOrder);
        assertEquals(order.getTotalCost(), createdOrder.getTotalCost());
        verify(orderRepository).save(order);
        verify(productService, times(order.getOrderItems().size())).getProductById(anyString());
    }

    @Test
    void updateOrderTest() {
        Order updatedDetails = new Order(order.getId(), 1L, orderItems, LocalDateTime.now());
        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Order updatedOrder = orderService.updateOrder(order.getId(), updatedDetails);
        assertNotNull(updatedOrder);
        assertEquals(updatedDetails.getTotalCost(), updatedOrder.getTotalCost());
        verify(orderRepository).findById(order.getId());
        verify(orderRepository).save(any(Order.class));
        verify(productService, times(updatedDetails.getOrderItems().size())).getProductById(anyString());
    }

//    @Test
//    void deleteOrderTest() {
//        when(orderRepository.findById(any(String.class))).thenReturn(Optional.ofNullable(order));
//        doNothing().when(orderRepository).deleteById(order.getId());
//
//        orderService.deleteOrder(order.getId());
//        verify(orderRepository).deleteById(order.getId());
//    }
//
//    @Test
//    void deleteOrderNotFoundTest() {
//        when(orderRepository.existsById(anyString())).thenReturn(false);
//        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrder("2"));
//        verify(orderRepository).existsById("2");
//    }
}
