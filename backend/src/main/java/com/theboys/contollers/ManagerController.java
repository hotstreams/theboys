package com.theboys.contollers;

import com.theboys.data.enums.VacancyStatus;
import com.theboys.services.VacancyService;
import com.theboys.to.UpdateVacancyStatusTO;
import com.theboys.to.VacancyRequestTO;
import com.theboys.to.VacancyResponseTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    private final VacancyService vacancyService;

    public ManagerController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
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
}
