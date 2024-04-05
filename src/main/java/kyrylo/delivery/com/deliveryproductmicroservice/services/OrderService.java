package kyrylo.delivery.com.deliveryproductmicroservice.services;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Order;
import kyrylo.delivery.com.deliveryproductmicroservice.entities.OrderItem;
import kyrylo.delivery.com.deliveryproductmicroservice.exceptions.orderException.OrderNotFoundException;
import kyrylo.delivery.com.deliveryproductmicroservice.repositories.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    private void verifyOrderItems(Set<OrderItem> orderItems) {
        orderItems.forEach(item -> {
            productService.getProductById(item.getProductId());
        });
    }

    public Order createOrder(Order order) {
        verifyOrderItems(order.getOrderItems());
        order.setOrderDate(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order updateOrder(String id, Order orderDetails) {
        Order existingOrder = getOrderById(id);
        verifyOrderItems(orderDetails.getOrderItems());
        existingOrder.setOrderItems(orderDetails.getOrderItems());
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(String id) {
        Order existingOrder = getOrderById(id);
        orderRepository.delete(existingOrder);
    }
}
