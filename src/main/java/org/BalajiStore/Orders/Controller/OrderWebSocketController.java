package org.BalajiStore.Orders.Controller;

import org.BalajiStore.Orders.Dto.OrderDTO;
import org.BalajiStore.Orders.Dto.ResponseDTO;
import org.BalajiStore.Orders.Dto.OrderItemDTO;
import org.BalajiStore.Orders.Entity.Order;
import org.BalajiStore.Orders.Entity.OrderItem;
import org.BalajiStore.Orders.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class OrderWebSocketController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/order")
    public void placeOrder(OrderDTO dto) {

        System.out.println("🔥 ORDER RECEIVED FROM CUSTOMER");
        System.out.println("Table No: " + dto.getTableNo());
        System.out.println("Total: " + dto.getTotal());
        System.out.println("Items: " + dto.getItems().size());

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

        ResponseDTO response = new ResponseDTO();
        response.setId(saved.getId());
        response.setTableNo(saved.getTableNo());
        response.setTotal(saved.getTotal());
        response.setStatus(saved.getStatus());

        List<OrderItemDTO> itemDTOs = saved.getItems().stream().map(i -> {
            OrderItemDTO d = new OrderItemDTO();
            d.setName(i.getName());
            d.setPrice(i.getPrice());
            d.setQuantity(i.getQuantity());
            return d;
        }).toList();

        response.setItems(itemDTOs);

        System.out.println("📢 Sending order to admin panel");

        messagingTemplate.convertAndSend("/topic/orders", response);
    }
}