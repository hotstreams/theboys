package com.theboys.contollers;

import com.theboys.security.PersistentUserManager;
import com.theboys.services.HeroService;
import com.theboys.services.OrderService;
import com.theboys.services.UserService;
import com.theboys.to.CustomHttpResponse;
import com.theboys.to.HeroTO;
import com.theboys.to.OrderRequestTO;
import com.theboys.to.OrderResponseTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/heroes")
public class HeroController {

    private final HeroService heroService;

    private final OrderService orderService;
    private final PersistentUserManager userService;

    @Autowired
    public HeroController(HeroService heroService, OrderService orderService, PersistentUserManager userService) {
        this.heroService = heroService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/rent")
    public CustomHttpResponse rentHero(@Valid @RequestBody OrderRequestTO request, Authentication authentication) {
        heroService.rentHero(request, authentication.getName());
        return new CustomHttpResponse();
    }

    @GetMapping
    public List<HeroTO> getHeroes() {
        return heroService.getHeroes();
    }

    @GetMapping("/rents")
    public List<OrderResponseTO> getOrdersOfHero(Principal principal) {
        int heroId = userService.loadUserIdByUsername(principal.getName());
        return orderService.getHeroOrders(heroId);
    }

    @PostMapping(path = "/{heroId}/rate")
    public void rateHero(Principal principal, @PathVariable("heroId") int heroId, @RequestParam("rate") int rate) {
        int userId = userService.loadUserIdByUsername(principal.getName());
        heroService.rateHero(heroId, userId, rate);
    }
}
