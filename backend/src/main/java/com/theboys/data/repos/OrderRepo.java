package com.theboys.data.repos;

import com.theboys.data.entities.Order;
import com.theboys.data.enums.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends CrudRepository<Order, Integer> {
    List<Order> findOrdersByHeroId(Integer heroId);

    List<Order> findOrdersByCustomerId(Integer heroId);

    @Transactional
    @Modifying
    @Query("update Order o set o.status = ?1 where o.orderId = ?2")
    void updateOrderStatus(OrderStatus status, Integer orderId);
}
