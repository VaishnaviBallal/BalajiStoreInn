package org.BalajiStore.Orders.Repository;

import org.BalajiStore.Orders.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(String status);

    List<Order> findByStatusIn(List<String> status);
}