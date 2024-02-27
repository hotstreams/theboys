package com.theboys.services;

import com.theboys.data.entities.*;
import com.theboys.data.enums.OrderStatus;
import com.theboys.data.repos.HeroRepo;
import com.theboys.exceptions.EntityNotFoundException;
import com.theboys.security.User;
import com.theboys.to.HeroTO;
import com.theboys.to.OrderRequestTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
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
        List<HeroToRating> ratings = heroRepo.getHeroRates();
        HashMap<Integer, Double> ratingsMap = new HashMap<>(ratings.size());
        ratings.forEach(heroToRating -> ratingsMap.put(heroToRating.getHeroId(), heroToRating.getRating()));
        return heroes.stream().map(this::createHeroTO).peek(heroTO -> heroTO.setRating(ratingsMap.getOrDefault(heroTO.getId(), 0d))).collect(Collectors.toList());
    }

    @Transactional
    public void rentHero(OrderRequestTO request, String username) {
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

    @Transactional
    public void rateHero(int heroId, int userId, int rate) {
        heroRepo.rateHero(heroId, userId, rate);
    }

    private HeroTO createHeroTO(Hero hero) {
        return new HeroTO(
                hero.getId(),
                hero.getName(),
                0d,
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