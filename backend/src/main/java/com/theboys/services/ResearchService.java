package com.theboys.services;

import com.theboys.data.entities.*;
import com.theboys.data.enums.CandidateStatus;
import com.theboys.data.enums.ResearchStatus;
import com.theboys.data.repos.*;
import com.theboys.exceptions.EntityNotFoundException;
import com.theboys.exceptions.IncorrectRequest;
import com.theboys.to.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ResearchService {
    private final ResearchRepo researchRepo;
    private final ScientistRepo scientistRepo;
    private final CandidateRepo candidateRepo;

    private final AssessmentRepo assessmentRepo;

    private final MedicineRepo medicineRepo;

    private final TestRepo testRepo;

    @Autowired
    public ResearchService(ResearchRepo researchRepo, ScientistRepo scientistRepo, CandidateRepo candidateRepo, AssessmentRepo assessmentRepo, MedicineRepo medicineRepo, TestRepo testRepo) {
        this.researchRepo = researchRepo;
        this.scientistRepo = scientistRepo;
        this.candidateRepo = candidateRepo;
        this.assessmentRepo = assessmentRepo;
        this.medicineRepo = medicineRepo;
        this.testRepo = testRepo;
    }

    public List<Medicine> getMedicine() { return medicineRepo.findAll(); }
    public List<Research> getResearches() {
        return researchRepo.findAll();
    }

    public Research getResearchById(Integer id) {
        return researchRepo.findById(id).orElseThrow(()->new EntityNotFoundException("Research with id="+id+" not found"));
    }

    public Research createResearch(Integer scientistId, ResearchRequestTO requestTO) {
        var research = new Research();
        research.setScientist(scientistRepo.findById(scientistId).orElseThrow(()->new EntityNotFoundException("Scientist with id="+scientistId+" not found")));
        Candidate candidate = candidateRepo.findById(requestTO.getCandidateId()).orElseThrow(()->new EntityNotFoundException("Candidate with id="+requestTO.getCandidateId()+" not found"));
        if (candidate.getCandidateStatus() != CandidateStatus.WAITING) {
            throw new IncorrectRequest("Candidate should have status "+ CandidateStatus.WAITING.name()+ ". Got "+candidate.getCandidateStatus());
        }
        candidate.setCandidateStatus(CandidateStatus.IN_PROGRESS);
        research.setCandidate(candidate);
        research.setDozeMl(requestTO.getDozeMl());
        research.setDozeCount(requestTO.getDozeCount());
        Medicine medicine = medicineRepo.findById(requestTO.getMedicineId()).orElseThrow(()->new EntityNotFoundException("Medicine with id="+requestTO.getMedicineId()+" not found"));
        research.setMedicine(medicine);
        research.setStatus(ResearchStatus.WAITING_FOR_INJECTION);

        researchRepo.save(research);
        return research;
    }

    public void updateResearchStatus(Integer researchId, ResearchStatusTO statusTO) {
        var research = researchRepo.findById(researchId).orElseThrow(()->new EntityNotFoundException("Research with id="+researchId+" not found"));
        research.setStatus(statusTO.getStatus());
        researchRepo.save(research);
    }

    public Test addTestsToResearch(Integer researchId, TestRequestTO requestTO) {
        var research = researchRepo.findById(researchId).orElseThrow(()->new EntityNotFoundException("Research with id="+researchId+" not found"));
        Test test = new Test();
        test.setResearch(research);
        test.setName(requestTO.getTestName());
        test.setRequestDate(LocalDate.now());
        if (research.getTests() == null) {
            research.setTests(List.of(test));
        } else {
            research.getTests().add(test);
        }
        testRepo.save(test);
        return test;
    }

    public Test submitTestResults(Integer testId, TestResultTO to) {
        var test = testRepo.findById(testId).orElseThrow(()->new EntityNotFoundException("Test with id="+testId+" not found"));
        test.setResult(to.getTestResult());
        test.setEventDate(LocalDate.now());
        testRepo.save(test);
        return test;
    }

    public Assessment addAssessmentsToResearch(Integer researchId, AssessmentRequestTO assessmentTO) {
        var research = researchRepo.findById(researchId).orElseThrow(()->new EntityNotFoundException("Research with id="+researchId+" not found"));
        var assessment = new Assessment();
        assessment.setResearch(research);
        assessment.setName(assessmentTO.getAssessmentName());
        assessment.setRequestDate(LocalDate.now());
        if (research.getAssessments() == null) {
            research.setAssessments(List.of(assessment));
        } else {
            research.getAssessments().add(assessment);
        }
        assessmentRepo.save(assessment);
        return assessment;
    }

    public Assessment submitAssessmentResults(Integer assessmentId, AssessmentResultTO resultTO) {
        var assessment = assessmentRepo.findById(assessmentId).orElseThrow(()->new EntityNotFoundException("Assessment with id="+assessmentId+" not found"));
        assessment.setComment(resultTO.getComment());
        assessment.setPassed(resultTO.isPassed());
        assessment.setEventDate(LocalDate.parse(resultTO.getEventDate(), DateTimeFormatter.ISO_LOCAL_DATE));
        assessmentRepo.save(assessment);
        return assessment;
    }

    public void submitExamStatus(Integer researchId, boolean passed) {
        var research = researchRepo.findById(researchId).orElseThrow(()->new EntityNotFoundException("Research with id="+researchId+" not found"));
        research.setExamPassed(passed);
        research.setStatus(ResearchStatus.CANDIDATE_BECAME_HERO);
        researchRepo.save(research);
    }
}
