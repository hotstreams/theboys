package com.theboys.services;

import com.theboys.data.entities.Candidate;
import com.theboys.data.entities.Customer;
import com.theboys.data.entities.Hero;
import com.theboys.data.entities.Order;
import com.theboys.data.enums.OrderStatus;
import com.theboys.data.repos.CandidateRepo;
import com.theboys.data.repos.CustomerRepo;
import com.theboys.data.repos.HeroRepo;
import com.theboys.data.repos.OrderRepo;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.User;
import com.theboys.security.UserRole;
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
        Order.class
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
        Order order = createOrder(1);
        Assertions.assertDoesNotThrow(() -> orderService.createHeroOrder(order));
    }

    @Test
    public void testGetOrders() {
        Order order = createOrder(4);
        orderService.createHeroOrder(order);
        List<OrderResponseTO> orders = orderService.getOrders(2);
        Assertions.assertEquals(1, orders.size());
    }

    private Order createOrder(int postfix) {
        Hero hero = new Hero();
        hero.setName("X" + (postfix + 1));
        hero.setLogin("X" + (postfix + 1));
        hero.setPassword("X" + (postfix + 1));
        hero.setRole(UserRole.HERO);
        heroRepo.save(hero);

        Customer customer = new Customer();
        customer.setLogin("X" + postfix);
        customer.setPassword("X" + postfix);
        customer.setRole(UserRole.CUSTOMER);
        customerRepo.save(customer);

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
