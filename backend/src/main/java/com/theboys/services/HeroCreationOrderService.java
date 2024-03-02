package com.theboys.services;

import com.theboys.data.entities.HeroCreationOrder;
import com.theboys.data.entities.Order;
import com.theboys.data.entities.Skill;
import com.theboys.data.repos.*;
import com.theboys.exceptions.EntityNotFoundException;
import com.theboys.security.PersistentUserManager;
import com.theboys.to.HeroCreationOrderTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HeroCreationOrderService {

    private final HeroCreationOrderRepo heroOrderRepo;

    private final OrderRepo orderRepo;

    private final SkillRepo skillRepo;

    private final HeroRepo heroRepo;

    private final PersistentUserManager userManager;

    private final ManagerRepo managerRepo;

    @Autowired
    public HeroCreationOrderService(HeroCreationOrderRepo heroOrderRepo, OrderRepo orderRepo, SkillRepo skillRepo, HeroRepo heroRepo, PersistentUserManager userManager, ManagerRepo managerRepo) {
        this.heroOrderRepo = heroOrderRepo;
        this.orderRepo = orderRepo;
        this.skillRepo = skillRepo;
        this.heroRepo = heroRepo;
        this.userManager = userManager;
        this.managerRepo = managerRepo;
    }

    public void createHeroCreationOrder(HeroCreationOrderTO to, String managerName) {
        int managerId = userManager.loadUserIdByUsername(managerName);
        HeroCreationOrder order = new HeroCreationOrder();
        Optional<Order> opt = orderRepo.findById(to.getOrderId());
        order.setOrder(opt.orElseThrow(() -> new EntityNotFoundException("No order with id " + to.getOrderId())));
        order.setSkills(skillRepo.findByNameIn(to.getSkillNames()));
        order.setManager(managerRepo.getReferenceById(managerId));
        order.setHeroCreationOrderId(1);
        heroOrderRepo.saveAndFlush(order);
        heroOrderRepo.flush();
    }

    public void fulfillHeroCreationOrder(Integer heroCreationOrderId, Integer heroId) {
        HeroCreationOrder order = heroOrderRepo.findById(heroCreationOrderId).orElseThrow(() -> new EntityNotFoundException("No heroCreationOrder with id " + heroId));
        order.getOrder().setHero(heroRepo.findById(heroId).orElseThrow(() -> new EntityNotFoundException("No hero with id " + heroId)));
        orderRepo.save(order.getOrder());
    }

    public List<HeroCreationOrderTO> getHeroCreationOrders() {
        return heroOrderRepo.findAll().stream().map(o -> new HeroCreationOrderTO(o.getHeroCreationOrderId(), o.getSkills().stream().map(Skill::getName).collect(Collectors.toList()))).collect(Collectors.toList());
    }
}
