package com.theboys.contollers;

import com.theboys.services.HeroService;
import com.theboys.to.CustomHttpResponse;
import com.theboys.to.HeroTO;
import com.theboys.to.RentHeroRequestTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/heroes")
public class HeroController {

    private final HeroService heroService;

    @Autowired
    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @PostMapping("/rent")
    public CustomHttpResponse rentHero(@RequestBody RentHeroRequestTO request, Authentication authentication) {
        heroService.rentHero(request, authentication.getName());
        return new CustomHttpResponse();
    }

    @GetMapping
    public List<HeroTO> getHeroes() {
        return heroService.getHeroes();
    }
}
