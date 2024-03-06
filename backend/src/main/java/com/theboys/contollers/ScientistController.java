package com.theboys.contollers;

import com.theboys.data.enums.CandidateRequestStatus;
import com.theboys.data.enums.CreationStatus;
import com.theboys.services.CandidateRequestService;
import com.theboys.services.HeroCreationOrderService;
import com.theboys.to.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/scientists")
public class ScientistController {

    private final CandidateRequestService candidateRequestService;

    private final HeroCreationOrderService heroCreationOrderService;

    @Autowired
    public ScientistController(CandidateRequestService candidateRequestService, HeroCreationOrderService heroCreationOrderService) {
        this.candidateRequestService = candidateRequestService;
        this.heroCreationOrderService = heroCreationOrderService;
    }

    @GetMapping("/candidates")
    public List<RequestCandidateResponseTO> getCandidateRequests(Principal principal) {
        String name = principal.getName();
        return candidateRequestService.getCandidateRequests(name);
    }

    @PostMapping("/candidates")
    public RequestCandidateResponseTO createCandidateRequest(@RequestBody RequestCandidateRequestTO request,
                                                             Principal principal) {
        String name = principal.getName();
        return candidateRequestService.createCandidateRequest(name, request);
    }

    @PatchMapping("/candidate/{id}")
    public ResponseEntity<Void> updateCandidateRequestStatus(@RequestBody @Valid UpdateRequestCandidateStatusTO requestCandidateStatusTO,
                                                       @PathVariable("id") Integer requestId) {
        CandidateRequestStatus candidateRequestStatus = requestCandidateStatusTO.getCandidateRequestStatus();
        candidateRequestService.updateStatus(candidateRequestStatus, requestId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/hero_creation_orders")
    public List<HeroCreationOrderResponseTO> getHeroCreationOrder() {
        return heroCreationOrderService.getHeroCreationOrders();
    }

    @PatchMapping("/hero_creation_orders/{id}")
    public void updateOrderWithCreatedHero(@PathVariable("id") Integer heroCreationOrderId, @RequestBody @Valid HeroCreationStatusTO status) {
        heroCreationOrderService.fulfillHeroCreationOrder(heroCreationOrderId, status.getStatus());
    }
}
