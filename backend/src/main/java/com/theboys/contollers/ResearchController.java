package com.theboys.contollers;

import com.theboys.data.entities.Research;
import com.theboys.data.repos.UserRepo;
import com.theboys.services.ResearchService;
import com.theboys.to.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/researches")
public class ResearchController {

    private final ResearchService service;
    private final UserRepo userRepo;


    @Autowired
    public ResearchController(ResearchService service, UserRepo userRepo) {
        this.service = service;
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<Research> getResearches() {
        return service.getResearches();
    }

    @GetMapping(value = "/{id}")
    public ResearchResponseTO getResearch(@PathVariable(name = "id") Integer id) {
        return new ResearchResponseTO(service.getResearchById(id));
    }


    @PostMapping
    public ResearchResponseTO createResearch(Principal principal, @RequestBody ResearchRequestTO requestTO) {
        return new ResearchResponseTO(service.createResearch(userRepo.findByLogin(principal.getName()).getId(), requestTO));
    }

    @PatchMapping(value = "/{researchId}")
    public void updateResearchStatus(@PathVariable(name = "researchId") Integer id, @RequestBody ResearchStatusTO statusTO) {
        service.updateResearchStatus(id, statusTO);
    }

    @PostMapping(value = "/{researchId}/tests")
    public TestResponseTO addTestToResearch(@PathVariable(name = "researchId") Integer id, @RequestBody TestRequestTO testTO) {
        return new TestResponseTO(service.addTestsToResearch(id, testTO));
    }

    @PatchMapping(value = "/{researchId}/tests/{testId}")
    public TestResponseTO submitTestResults(@PathVariable(name = "researchId") Integer id, @PathVariable(name = "testId") Integer testId, @RequestBody TestResultTO testTO) {
        return new TestResponseTO(service.submitTestResults(testId, testTO));
    }

    @PostMapping(value = "/{researchId}/assessments")
    public AssessmentResponseTO addAssessmentsToResearch(@PathVariable(name = "researchId") Integer researchId, @RequestBody AssessmentRequestTO assessmentTO) {
        return new AssessmentResponseTO(service.addAssessmentsToResearch(researchId, assessmentTO));
    }

    @PatchMapping(value = "/{researchId}/assessments/{assessmentId}")
    public AssessmentResponseTO submitAssessmentResults(@PathVariable(name = "researchId") Integer id, @PathVariable(name = "assessmentId") Integer assessmentId, @RequestBody AssessmentResultTO assessmentTO) {
        return new AssessmentResponseTO(service.submitAssessmentResults(assessmentId, assessmentTO));
    }

    @PatchMapping(value = "/{researchId}/exam")
    public void submitExamStatus(@PathVariable(name = "researchId") Integer id, @RequestParam(name = "passed") boolean passed) {
        service.submitExamStatus(id, passed);
    }

}

