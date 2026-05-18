package org.BalajiStore.Orders.Service;

import org.BalajiStore.Orders.Entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderNotificationService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendNewOrder(Order order) {
        messagingTemplate.convertAndSend("/topic/orders", order);
    }
}
