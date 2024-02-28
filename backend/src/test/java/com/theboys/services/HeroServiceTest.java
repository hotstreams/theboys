package com.theboys.services;

import com.theboys.data.entities.Customer;
import com.theboys.data.entities.Hero;
import com.theboys.data.entities.Order;
import com.theboys.data.repos.CustomerRepo;
import com.theboys.data.repos.HeroRepo;
import com.theboys.data.repos.OrderRepo;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.UserRole;
import com.theboys.security.WebSecurityConfig;
import com.theboys.to.OrderRequestTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;

@SpringBootTest(
        classes = {
                PersistentUserManager.class,
                OrderService.class,
                CustomerService.class,
                UserService.class,
                HeroService.class
        })
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {
        OrderRepo.class
})
@EntityScan(basePackageClasses = {
        Hero.class
})
@Import(WebSecurityConfig.class)
public class HeroServiceTest {

    @Autowired
    private HeroService heroService;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private HeroRepo heroRepo;

    @Test
    public void testRentHero() {
        Customer customer = createCustomer(1);
        customerRepo.save(customer);

        Hero hero = createHero(2);
        Hero saved = heroRepo.save(hero);

        OrderRequestTO orderRequestTO = new OrderRequestTO();
        orderRequestTO.setHeroDescription("test");
//        orderRequestTO.setHeroId(saved.getId());
        orderRequestTO.setDateStart(LocalDate.now());
        orderRequestTO.setDateEnd(LocalDate.now().plusDays(1));

        Assertions.assertDoesNotThrow(() -> heroService.rentHero(orderRequestTO, "X1"));
    }

    private Customer createCustomer(int postfix) {
        Customer customer = new Customer();
//        customer.setLogin("X" + postfix);
//        customer.setPassword("X" + postfix);
//        customer.setRole(UserRole.CUSTOMER);
        return customer;
    }

    private Hero createHero(int postfix) {
        Hero hero = new Hero();
        hero.setName("X" + (postfix + 1));
//        hero.setLogin("X" + (postfix + 1));
//        hero.setPassword("X" + (postfix + 1));
//        hero.setRole(UserRole.HERO);
        return hero;
    }
}
