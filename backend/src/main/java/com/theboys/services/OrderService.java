package com.theboys.services;

import com.theboys.data.entities.Order;
import com.theboys.data.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
