package com.theboys.contollers;

import com.theboys.security.PersistentUserManager;
import com.theboys.services.HeroService;
import com.theboys.services.OrderService;
import com.theboys.services.UserService;
import com.theboys.to.CustomHttpResponse;
import com.theboys.to.HeroTO;
import com.theboys.to.OrderRequestTO;
import com.theboys.to.OrderResponseTO;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/heroes")
@Validated
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
    public void rateHero(Principal principal, @PathVariable("heroId") int heroId, @RequestParam("rate") @Min(1) @Max(5) int rate) {
        int userId = userService.loadUserIdByUsername(principal.getName());
        heroService.rateHero(heroId, userId, rate);
    }
}
