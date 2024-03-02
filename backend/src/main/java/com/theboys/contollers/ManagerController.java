package com.theboys.contollers;

import com.theboys.data.enums.VacancyStatus;
import com.theboys.services.HeroCreationOrderService;
import com.theboys.services.VacancyService;
import com.theboys.to.HeroCreationOrderTO;
import com.theboys.to.UpdateVacancyStatusTO;
import com.theboys.to.VacancyRequestTO;
import com.theboys.to.VacancyResponseTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    private final VacancyService vacancyService;

    private final HeroCreationOrderService heroCreationOrderService;

    @Autowired
    public ManagerController(VacancyService vacancyService, HeroCreationOrderService heroCreationOrderService) {
        this.vacancyService = vacancyService;
        this.heroCreationOrderService = heroCreationOrderService;
    }

    @GetMapping("/vacancies")
    public List<VacancyResponseTO> getVacancies() {
        return vacancyService.getVacancies();
    }

    @PostMapping("/vacancies")
    public VacancyResponseTO createCandidateRequest(@RequestBody VacancyRequestTO request) {
        return vacancyService.createVacancy(request);
    }

    @PatchMapping("/vacancy/{id}")
    public ResponseEntity<Void> updateCandidateRequestStatus(@RequestBody @Valid UpdateVacancyStatusTO vacancyStatusTO,
                                                             @PathVariable("id") Integer requestId) {
        VacancyStatus vacancyStatus = vacancyStatusTO.getVacancyStatus();
        vacancyService.updateStatus(vacancyStatus, requestId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rents/update")
    public ResponseEntity<Void> createHeroCreationOrder(Principal principal, @RequestBody @Valid HeroCreationOrderTO heroCreationOrderTO) {

        heroCreationOrderService.createHeroCreationOrder(heroCreationOrderTO, principal.getName());
        return ResponseEntity.ok().build();
    }
}
