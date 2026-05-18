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

    // =====================================
    // CREATE ORDER
    // =====================================
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

        ResponseDTO response = convertToDTO(saved);

        messagingTemplate.convertAndSend("/topic/orders", response);

        System.out.println("🔥 NEW ORDER SENT");

        return response;
    }

    // =====================================
    // GET ALL ORDERS
    // =====================================
    @GetMapping("/all")
    public List<ResponseDTO> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    // =====================================
    // ACCEPT ORDER
    // =====================================
    @PutMapping("/{id}/accept")
    public ResponseDTO acceptOrder(@PathVariable Long id) {

        System.out.println("👉 ACCEPT ORDER: " + id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));

        order.setStatus("ACCEPTED");

        Order saved = orderRepository.save(order);

        ResponseDTO response = convertToDTO(saved);

        messagingTemplate.convertAndSend("/topic/orders", response);

        System.out.println("✅ ORDER ACCEPTED");

        return response;
    }

    // =====================================
    // COMPLETE ORDER
    // =====================================
    @PutMapping("/{id}/complete")
    public ResponseDTO completeOrder(@PathVariable Long id) {

        System.out.println("🍳 COMPLETE ORDER: " + id);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order Not Found"));

        order.setStatus("COMPLETED");

        Order saved = orderRepository.save(order);

        ResponseDTO response = convertToDTO(saved);

        messagingTemplate.convertAndSend("/topic/orders", response);

        System.out.println("✅ ORDER COMPLETED");

        return response;
    }

    // =====================================
    // DELETE SINGLE ORDER
    // =====================================
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {

        System.out.println("🗑 DELETE ORDER: " + id);

        orderRepository.deleteById(id);

        return "Order Deleted";
    }

    // =====================================
    // CLEAR HISTORY
    // =====================================
    @DeleteMapping("/history/clear")
    public String clearHistory() {

        System.out.println("🧹 CLEARING HISTORY");

        List<Order> completedOrders =
                orderRepository.findByStatus("COMPLETED");

        orderRepository.deleteAll(completedOrders);

        return "History Cleared";
    }

    // =====================================
    // DTO CONVERTER
    // =====================================
    private ResponseDTO convertToDTO(Order order) {

        List<OrderItemDTO> itemDTOs =
                order.getItems().stream().map(i -> {

                    OrderItemDTO dto = new OrderItemDTO();

                    dto.setName(i.getName());
                    dto.setPrice(i.getPrice());
                    dto.setQuantity(i.getQuantity());

                    return dto;

                }).toList();

        ResponseDTO response = new ResponseDTO();

        response.setId(order.getId());
        response.setTableNo(order.getTableNo());
        response.setTotal(order.getTotal());
        response.setStatus(order.getStatus());
        response.setItems(itemDTOs);

        return response;
    }
}