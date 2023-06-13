package com.theboys.services;

import com.theboys.data.entities.Customer;
import com.theboys.data.entities.Hero;
import com.theboys.data.entities.Order;
import com.theboys.data.entities.Skill;
import com.theboys.data.enums.OrderStatus;
import com.theboys.data.repos.HeroRepo;
import com.theboys.exceptions.EntityNotFoundException;
import com.theboys.security.User;
import com.theboys.to.HeroTO;
import com.theboys.to.OrderRequestTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HeroService {

    private final CustomerService customerService;
    private final OrderService orderService;
    private final UserService userService;
    private final HeroRepo heroRepo;

    @Autowired
    public HeroService(HeroRepo heroRepo,
                       OrderService orderService,
                       UserService userService,
                       CustomerService customerService) {
        this.heroRepo = heroRepo;
        this.orderService = orderService;
        this.userService = userService;
        this.customerService = customerService;
    }

    public List<HeroTO> getHeroes() {
        List<Hero> heroes = heroRepo.findAll();
        return heroes.stream().map(this::createHeroTO).collect(Collectors.toList());
    }

    public void rentHero(OrderRequestTO request, String username) {
        // todo Today user id equals id from role table
        User user = userService.getUserByLogin(username);
        customerService.getCustomer(user.getId())
                .ifPresentOrElse(customer -> {
                            Optional<Hero> heroOptional = request.getHeroId() == null ? Optional.empty() : heroRepo.findById(request.getHeroId());
                            Order order = createHeroOrder(request, customer, heroOptional);
                            orderService.createHeroOrder(order);
                        },
                        () -> {
                            throw new EntityNotFoundException("Customer with id=" + user.getId() + " not found");
                        }
                );
    }

    private HeroTO createHeroTO(Hero hero) {
        return new HeroTO(
                hero.getHeroId(),
                hero.getName(),
                hero.getRating(),
                hero.getSkills().stream().map(Skill::getName).collect(Collectors.joining(", ")),
                hero.getDescription()
        );
    }

    private Order createHeroOrder(OrderRequestTO request, Customer customer, Optional<Hero> heroOptional) {
        LocalDate orderDate = LocalDate.now();
        return heroOptional
                .map(hero -> new Order(hero, customer, orderDate, request.getDateStart(), request.getDateEnd(), request.getRequestDescription(), OrderStatus.PENDING, request.getHeroDescription()))
                .orElse(new Order(customer, orderDate, request.getDateStart(), request.getDateEnd(), request.getRequestDescription(), OrderStatus.PENDING, request.getHeroDescription()));
    }
}
