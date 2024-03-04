package com.theboys.services;

import com.theboys.data.entities.Customer;
import com.theboys.data.entities.Hero;
import com.theboys.data.repos.CustomerRepo;
import com.theboys.data.repos.HeroRepo;
import com.theboys.data.repos.OrderRepo;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.User;
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
import java.util.Optional;

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
        Hero.class,
        User.class
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
        OrderRequestTO orderRequestTO = new OrderRequestTO();
        orderRequestTO.setHeroDescription("test");
        orderRequestTO.setHeroId(8);
        orderRequestTO.setDateStart(LocalDate.now());
        orderRequestTO.setDateEnd(LocalDate.now().plusDays(1));

        Assertions.assertDoesNotThrow(() -> heroService.rentHero(orderRequestTO, "customer"));
    }
}
