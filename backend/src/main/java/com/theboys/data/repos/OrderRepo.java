package com.theboys.data.repos;

import com.theboys.data.entities.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends CrudRepository<Order, Integer> {
    List<Order> findOrdersByHeroHeroId(Integer heroId);
}
