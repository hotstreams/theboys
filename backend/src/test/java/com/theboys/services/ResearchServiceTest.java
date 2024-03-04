package com.theboys.services;

import com.theboys.data.entities.*;
import com.theboys.data.repos.*;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.User;
import com.theboys.security.WebSecurityConfig;
import com.theboys.to.AssessmentRequestTO;
import com.theboys.to.ResearchRequestTO;
import com.theboys.to.TestRequestTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Objects;

@SpringBootTest(
        classes = {
                PersistentUserManager.class,
                ResearchService.class
        })
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = UserRepo.class)
@EntityScan(basePackageClasses = {
        Medicine.class,
        User.class
})
@Import(WebSecurityConfig.class)
public class ResearchServiceTest {

    @Autowired
    private MedicineRepo medicineRepo;

    @Autowired
    private ResearchService researchService;

    @Autowired
    private ScientistRepo scientistRepo;

    @Autowired
    private CandidateRepo candidateRepo;

    @Autowired
    private ResearchRepo researchRepo;

    @Test
    public void testCreateResearch() {
        ResearchRequestTO requestTO = createResearchRequest(1, 17);
        Assertions.assertDoesNotThrow(
                () -> researchService.createResearch(2, requestTO));
    }

    @Test
    public void testGetResearchById() {
        List<Research> all = researchRepo.findAll();
        Research research = all.get(0);
        
        Research researchById = researchService.getResearchById(research.getResearchId());
        Assertions.assertNotNull(researchById);
    }

    @Test
    public void testGetMedicine() {
        createMedicine(1, "Test");
        List<Medicine> medicines = researchService.getMedicine();
        List<Medicine> collect = medicines.stream().filter(Objects::nonNull).toList();
        Assertions.assertFalse(collect.isEmpty());
    }

    @Test
    @Transactional
    public void testAddTestsToResearch() {
        List<Research> all = researchRepo.findAll();
        Research research = all.get(0);
        TestRequestTO testRequestTO = new TestRequestTO();
        testRequestTO.setTestName("Test");
        com.theboys.data.entities.Test test = researchService.addTestsToResearch(research.getResearchId(), testRequestTO);
        Assertions.assertEquals("Test", test.getName());
    }

    @Test
    @Transactional
    public void testAddAssessmentsToResearch() {
        List<Research> all = researchRepo.findAll();
        Research research = all.get(0);
        AssessmentRequestTO request = new AssessmentRequestTO();
        request.setAssessmentName("Test");
        Assessment assessment = researchService.addAssessmentsToResearch(research.getResearchId(), request);
        Assertions.assertEquals("Test", assessment.getName());
        Assertions.assertEquals(research, assessment.getResearch());

    }

    private void createMedicine(Integer id, String name) {
        Medicine medicine = new Medicine();
        medicine.setName(name);
        medicine.setId(id);
        medicineRepo.save(medicine);
    }

    private ResearchRequestTO createResearchRequest(Integer medicineId, Integer candidateId) {
        ResearchRequestTO requestTO = new ResearchRequestTO();
        requestTO.setDozeMl(1);
        requestTO.setMedicineId(medicineId);
        requestTO.setDozeCount(1);
        requestTO.setCandidateId(candidateId);
        return requestTO;
    }
}
