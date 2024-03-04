package com.theboys.services;

import com.theboys.data.entities.Customer;
import com.theboys.data.entities.Hero;
import com.theboys.data.entities.Order;
import com.theboys.data.enums.OrderStatus;
import com.theboys.data.repos.CustomerRepo;
import com.theboys.data.repos.HeroRepo;
import com.theboys.data.repos.OrderRepo;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.User;
import com.theboys.security.WebSecurityConfig;
import com.theboys.to.OrderResponseTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest(
        classes = {
                PersistentUserManager.class,
                OrderService.class
        })
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {
        OrderRepo.class
})
@EntityScan(basePackageClasses = {
        Order.class,
        User.class
})
@Import(WebSecurityConfig.class)
public class OrderServiceTest {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private HeroRepo heroRepo;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerRepo customerRepo;

    @Test
    public void testCreateHeroOrder() {
        Order order = createOrder();
        Assertions.assertDoesNotThrow(() -> orderService.createHeroOrder(order));
    }

    @Test
    public void testGetOrders() {
        List<OrderResponseTO> orders = orderService.getOrdersByCustomerId(18);
        Assertions.assertFalse(orders.isEmpty());
    }

    private Order createOrder() {
        Optional<Hero> optionalHero = heroRepo.findById(7);
        Assertions.assertFalse(optionalHero.isEmpty());
        Hero hero = optionalHero.get();

        Optional<Customer> optionalCustomer = customerRepo.findById(18);
        Assertions.assertFalse(optionalCustomer.isEmpty());
        Customer customer = optionalCustomer.get();

        Order order = new Order();
        order.setHero(hero);
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setCustomer(customer);
        order.setStartDate(LocalDate.now());
        order.setEndDate(LocalDate.now().plusDays(10));
        order.setDate(LocalDate.now().minusDays(1));
        return order;
    }
}
