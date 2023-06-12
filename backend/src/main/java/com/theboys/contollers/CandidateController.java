package com.theboys.contollers;

import com.theboys.exceptions.IncorrectRequest;
import com.theboys.services.CandidateService;
import com.theboys.to.CandidateRequestTO;
import com.theboys.to.CandidateResponseTO;
import com.theboys.to.UpdateCandidateStatusTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CandidateController {

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping(path = "/candidates")
    public List<CandidateResponseTO> getCandidates() {
        return candidateService.getCandidates();
    }

    @PostMapping(path = "/candidates")
    public void createCandidate(@RequestBody CandidateRequestTO candidateRequestTO) {
        candidateService.saveCandidate(candidateRequestTO);
    }

    @GetMapping(path = "/candidates/{id}")
    public CandidateResponseTO getCandidate(@PathVariable("id") Integer userId) {
        return candidateService.getCandidateById(userId);
    }

    @PatchMapping(path = "/candidates/{id}")
    public void updateCandidateStatus(@PathVariable("id") Integer userId, @RequestBody UpdateCandidateStatusTO statusTO) {
        if (statusTO.getStatus() == null) {
            throw new IncorrectRequest("Missing required parameter 'status'");
        }
        candidateService.updateCandidateStatus(userId, statusTO.getStatus());
    }
}
