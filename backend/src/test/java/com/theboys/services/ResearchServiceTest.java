package com.theboys.services;

import com.theboys.data.entities.*;
import com.theboys.data.repos.*;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.User;
import com.theboys.security.UserRole;
import com.theboys.security.WebSecurityConfig;
import com.theboys.services.ResearchService;
import com.theboys.services.UserService;
import com.theboys.to.ResearchRequestTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
        createMedicine(1,"Test");
        int candidateId = createCandidate("TestCandidate");
        Scientist scientist = new Scientist();
        scientist.setLogin("X1");
        scientist.setPassword("X1");
        scientist.setRole(UserRole.SCIENTIST);
        ResearchRequestTO requestTO = createResearchRequest(1, candidateId);
        Scientist savedScientist = scientistRepo.save(scientist);
        Assertions.assertDoesNotThrow(
                () -> researchService.createResearch(savedScientist.getId(), requestTO));
    }

    @Test
    public void testGetResearchById() {
        createMedicine(2, "Test2");
        int candidateId = createCandidate("TestCandidate2");
        Scientist scientist =  new Scientist();
        scientist.setLogin("X12");
        scientist.setPassword("X1");
        scientist.setRole(UserRole.SCIENTIST);
        Scientist savedScientist = scientistRepo.save(scientist);
        ResearchRequestTO researchRequest = createResearchRequest(2, candidateId);
        Research research = researchService.createResearch(savedScientist.getId(), researchRequest);

        Research researchById = researchService.getResearchById(research.getResearchId());
        Assertions.assertEquals(research.getDozeCount(), researchById.getDozeCount());
    }

    @Test
    public void testGetMedicine() {
        createMedicine(1, "Test");
        List<Medicine> medicines = researchService.getMedicine();
        List<Medicine> collect = medicines.stream().filter(Objects::nonNull).toList();
        Assertions.assertFalse(collect.isEmpty());
    }

    private void createMedicine(Integer id, String name) {
        Medicine medicine = new Medicine();
        medicine.setName(name);
        medicine.setId(id);
        medicineRepo.save(medicine);
    }

    private int createCandidate(String login) {
        Candidate candidate = new Candidate();
        candidate.setLogin(login);
        candidate.setPassword("X");
        candidate.setRole(UserRole.CANDIDATE);
        candidate.setFirstName("A");
        candidate.setLastName("A");
        candidate.setBirthday(LocalDate.now());
        candidate.setPhone("");
        candidate.setMedicalDocument(new byte[]{});
        Candidate saved = candidateRepo.save(candidate);
        return saved.getId();
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
