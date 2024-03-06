package com.theboys.services;

import com.theboys.data.entities.*;
import com.theboys.data.enums.CreationStatus;
import com.theboys.data.repos.*;
import com.theboys.exceptions.EntityNotFoundException;
import com.theboys.security.PersistentUserManager;
import com.theboys.to.HeroCreationOrderResponseTO;
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
    private final HeroCultivationRepo heroCultivationRepo;

    private final HeroRepo heroRepo;

    private final PersistentUserManager userManager;

    private final ManagerRepo managerRepo;

    @Autowired
    public HeroCreationOrderService(HeroCreationOrderRepo heroOrderRepo, OrderRepo orderRepo, SkillRepo skillRepo, HeroRepo heroRepo, PersistentUserManager userManager, ManagerRepo managerRepo, HeroCultivationRepo heroCultivationRepo) {
        this.heroOrderRepo = heroOrderRepo;
        this.orderRepo = orderRepo;
        this.heroCultivationRepo = heroCultivationRepo;
        this.heroRepo = heroRepo;
        this.userManager = userManager;
        this.managerRepo = managerRepo;
    }

    public void createHeroCreationOrder(HeroCreationOrderTO to, String managerName) {
        int managerId = userManager.loadUserIdByUsername(managerName);

        CultivationOrder order = new CultivationOrder();
        order.setManager(managerRepo.getReferenceById(managerId));
        order.setDescription(to.getDescription());
        order.setStatus(CreationStatus.PENDING);
        heroCultivationRepo.save(order);
    }

    public void fulfillHeroCreationOrder(Integer heroCreationOrderId, CreationStatus status) {
        CultivationOrder order = heroCultivationRepo.findById(heroCreationOrderId).orElseThrow(() -> new EntityNotFoundException("No heroCreationOrder with id " + heroCreationOrderId));
        order.setStatus(status);
        heroCultivationRepo.save(order);
    }

    public List<HeroCreationOrderResponseTO> getHeroCreationOrders() {
        return heroCultivationRepo.findAll().stream()
                .map(o -> new HeroCreationOrderResponseTO(o.getHeroCreationOrderId(), o.getDescription(), o.getStatus()))
                .collect(Collectors.toList());
    }
}
