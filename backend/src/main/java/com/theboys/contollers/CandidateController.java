package com.theboys.contollers;

import com.theboys.exceptions.IncorrectRequest;
import com.theboys.services.CandidateService;
import com.theboys.to.CandidateRequestTO;
import com.theboys.to.CandidateResponseTO;
import com.theboys.to.CustomHttpResponse;
import com.theboys.to.UpdateCandidateStatusTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public List<CandidateResponseTO> getCandidates() {
        return candidateService.getCandidates();
    }

    @PostMapping
    public CustomHttpResponse createCandidate(@Valid @RequestBody CandidateRequestTO candidateRequestTO, Authentication authentication) {
        candidateService.saveCandidate(candidateRequestTO, authentication.getName());
        return new CustomHttpResponse();
    }

    @GetMapping(path = "/{id}")
    public CandidateResponseTO getCandidate(@PathVariable("id") Integer userId) {
        return candidateService.getCandidateById(userId);
    }

    //todo Make wrapper to avoid writing new CustomHttpResponse call for every endpoint
    @PatchMapping(path = "/{id}")
    public CustomHttpResponse updateCandidateStatus(@PathVariable("id") Integer userId, @Valid @RequestBody UpdateCandidateStatusTO statusTO) {
        if (statusTO.getStatus() == null) {
            throw new IncorrectRequest("Missing required parameter 'status'");
        }
        candidateService.updateCandidateStatus(userId, statusTO.getStatus());
        return new CustomHttpResponse();
    }
}
