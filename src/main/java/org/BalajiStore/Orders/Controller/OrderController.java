package org.BalajiStore.Orders.Controller;

import org.BalajiStore.Orders.Dto.OrderDTO;
import org.BalajiStore.Orders.Dto.OrderItemDTO;
import org.BalajiStore.Orders.Dto.ResponseDTO;
import org.BalajiStore.Orders.Entity.Order;
import org.BalajiStore.Orders.Entity.OrderItem;
import org.BalajiStore.Orders.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // ==============================
    // CREATE ORDER (YOU ALREADY HAVE)
    // ==============================
    @PostMapping
    public ResponseDTO createOrder(@RequestBody OrderDTO dto) {

        Order order = new Order();
        order.setTotal(dto.getTotal());
        order.setTableNo(dto.getTableNo());
        order.setStatus("NEW");

        List<OrderItem> items = dto.getItems().stream().map(i -> {
            OrderItem item = new OrderItem();
            item.setName(i.getName());
            item.setPrice(i.getPrice());
            item.setQuantity(i.getQuantity());
            return item;
        }).toList();

        order.setItems(items);

        Order saved = orderRepository.save(order);

        List<OrderItemDTO> itemDTOs = saved.getItems().stream().map(i -> {
            OrderItemDTO d = new OrderItemDTO();
            d.setName(i.getName());
            d.setPrice(i.getPrice());
            d.setQuantity(i.getQuantity());
            return d;
        }).toList();

        ResponseDTO response = new ResponseDTO();
        response.setId(saved.getId());
        response.setTableNo(saved.getTableNo());
        response.setTotal(saved.getTotal());
        response.setStatus(saved.getStatus());
        response.setItems(itemDTOs);

        messagingTemplate.convertAndSend("/topic/orders", response);

        return response;
    }

    // ==============================
    // ✅ NEW: ACCEPT ORDER API
    // ==============================
    @PutMapping("/{id}/accept")
    public ResponseDTO acceptOrder(@PathVariable Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus("ACCEPTED");

        Order saved = orderRepository.save(order);

        List<OrderItemDTO> items = saved.getItems().stream().map(i -> {
            OrderItemDTO d = new OrderItemDTO();
            d.setName(i.getName());
            d.setPrice(i.getPrice());
            d.setQuantity(i.getQuantity());
            return d;
        }).toList();

        ResponseDTO response = new ResponseDTO();
        response.setId(saved.getId());
        response.setTableNo(saved.getTableNo());
        response.setTotal(saved.getTotal());
        response.setStatus(saved.getStatus());
        response.setItems(items);

        // optional live update to admin
        messagingTemplate.convertAndSend("/topic/orders", response);

        return response;
    }

    // ==============================
    // 🆕 NEW ORDERS
    // ==============================
    @GetMapping("/new")
    public List<Order> getNewOrders() {
        return orderRepository.findByStatus("NEW");
    }

    // ==============================
    // 🍳 ACTIVE ORDERS
    // ==============================
    @GetMapping("/active")
    public List<Order> getActiveOrders() {
        return orderRepository.findByStatusIn(
                List.of("ACCEPTED")
        );
    }

    // ==============================
    // 📜 HISTORY (ALL EXCEPT NEW)
    // ==============================
    @GetMapping("/history")
    public List<Order> getHistory() {
        return orderRepository.findByStatusIn(
                List.of("ACCEPTED")
        );
    }
    @PutMapping("/{id}/complete")
    public Order completeOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElseThrow();

        order.setStatus("COMPLETED");
        Order saved = orderRepository.save(order);

        messagingTemplate.convertAndSend("/topic/orders", saved);
        return saved;
    }
    @GetMapping("/status/{status}")
    public List<Order> getByStatus(@PathVariable String status) {
        return orderRepository.findByStatus(status);
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}