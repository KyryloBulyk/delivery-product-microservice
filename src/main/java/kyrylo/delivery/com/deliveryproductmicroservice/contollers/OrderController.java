package kyrylo.delivery.com.deliveryproductmicroservice.contollers;

import kyrylo.delivery.com.deliveryproductmicroservice.entities.Order;
import kyrylo.delivery.com.deliveryproductmicroservice.services.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        logger.info("Getting all orders");
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        logger.info("Getting order with id: {}", id);
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        logger.info("Creating new order");
        Order createdOrder = orderService.createOrder(order);
        logger.info("Order created with id: {}", createdOrder.getId());
        return ResponseEntity.ok(createdOrder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable String id, @RequestBody Order order) {
        logger.info("Updating order with id: {}", id);
        Order updatedOrder = orderService.updateOrder(id, order);
        logger.info("Order updated with id: {}", updatedOrder.getId());
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        logger.info("Deleting order with id: {}", id);
        orderService.deleteOrder(id);
        logger.info("Order deleted with id: {}", id);
        return ResponseEntity.ok().build();
    }
}
