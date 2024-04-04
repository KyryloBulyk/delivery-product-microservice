package kyrylo.delivery.com.deliveryproductmicroservice.services;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Order;
import kyrylo.delivery.com.deliveryproductmicroservice.exceptions.orderException.OrderNotFoundException;
import kyrylo.delivery.com.deliveryproductmicroservice.repositories.OrderRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LogManager.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final UserClient userClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, UserClient userClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        logger.info("Fetching all orders. Total orders found: {}", orders.size());
        return orders;
    }

    public Order getOrderById(String id) {
        logger.info("Fetching order by id: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    public Order createOrder(Order order) {
        logger.info("Creating new order for user ID: {}", order.getUserId());

        ResponseEntity<Boolean> userExistsResponse = userClient.existsById(order.getUserId());
        if (Boolean.FALSE.equals(userExistsResponse.getBody())) {
            logger.error("User with ID: {} does not exist. Order creation failed.", order.getUserId());
            throw new IllegalStateException("Cannot create order. User does not exist.");
        }

        order.setOrderDate(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        logger.info("Order created successfully with id: {}", savedOrder.getId());
        return savedOrder;
    }

    public Order updateOrder(String id, Order orderDetails) {
        logger.info("Updating order with id: {}", id);
        return orderRepository.findById(id)
                .map(order -> {
                    order.setProductIds(orderDetails.getProductIds());
                    order.setTotalCost(orderDetails.getTotalCost());
                    Order updatedOrder = orderRepository.save(order);
                    logger.info("Order updated successfully with id: {}", updatedOrder.getId());
                    return updatedOrder;
                }).orElseThrow(() -> {
                    logger.error("Order update failed. Order not found with id: {}", id);
                    return new OrderNotFoundException("Order not found with id: " + id);
                });
    }

    public void deleteOrder(String id) {
        logger.info("Deleting order with id: {}", id);
        if (!orderRepository.existsById(id)) {
            logger.error("Order deletion failed. Order not found with id: {}", id);
            throw new OrderNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
        logger.info("Order deleted successfully with id: {}", id);
    }
}
