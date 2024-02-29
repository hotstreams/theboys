package com.theboys.contollers;

import com.theboys.data.enums.CandidateRequestStatus;
import com.theboys.security.User;
import com.theboys.security.UserRole;
import com.theboys.services.CandidateRequestService;
import com.theboys.services.CandidateService;
import com.theboys.services.UserService;
import com.theboys.to.CandidateResponseTO;
import com.theboys.to.RequestCandidateRequestTO;
import com.theboys.to.RequestCandidateResponseTO;
import com.theboys.to.UpdateRequestCandidateStatusTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/scientist")
public class ScientistController {

    private final CandidateRequestService candidateRequestService;
    public ScientistController(CandidateRequestService candidateRequestService) {
        this.candidateRequestService = candidateRequestService;
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
}
