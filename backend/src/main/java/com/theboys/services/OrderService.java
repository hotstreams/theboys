package com.theboys.services;

import com.theboys.data.entities.Order;
import com.theboys.data.enums.OrderStatus;
import com.theboys.data.repos.OrderRepo;
import com.theboys.to.OrderResponseTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepo orderRepo;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public void createHeroOrder(Order order) {
        orderRepo.save(order);
    }

    public List<Order> getHeroOrders(Integer heroId) {
        return orderRepo.findOrdersByHeroHeroId(heroId);
    }

    public List<OrderResponseTO> getOrders() {
        List<Order> orders = (List<Order>) orderRepo.findAll();
        return orders.stream().map(this::createOrderResponse).collect(Collectors.toList());
    }

    public void updateOrderStatus(OrderStatus status, Integer orderId) {
        orderRepo.updateOrderStatus(status, orderId);
    }

    private OrderResponseTO createOrderResponse(Order order) {
        String heroName = order.getHero() == null ? "" : order.getHero().getName();

        return new OrderResponseTO(
                order.getOrderId(),
                order.getCustomer().getCustomerId(),
                heroName,
                order.getHeroDescription(),
                order.getRequestDescription(),
                order.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                order.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                order.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                order.getStatus()
        );
    }
}
