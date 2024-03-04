package com.theboys.services;

import com.theboys.data.entities.Candidate;
import com.theboys.data.entities.Scientist;
import com.theboys.data.enums.CandidateStatus;
import com.theboys.data.repos.CandidateRepo;
import com.theboys.data.repos.ScientistRepo;
import com.theboys.data.repos.UserRepo;
import com.theboys.exceptions.EntityNotFoundException;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.User;
import com.theboys.security.WebSecurityConfig;
import com.theboys.to.CandidateRequestTO;
import com.theboys.to.CandidateResponseTO;
import com.theboys.to.RegistrationTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@SpringBootTest(
        classes = {
                CandidateService.class,
                PersistentUserManager.class,
                UserService.class
        })
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = CandidateRepo.class)
@EntityScan(basePackageClasses = {
        Candidate.class,
        User.class
})
@Import(WebSecurityConfig.class)
public class CandidateServiceTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CandidateRepo candidateRepo;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private UserService userService;

    @Autowired
    private ScientistRepo scientistRepo;
    @Test
    public void testCreateCandidateRequest() {
        CandidateRequestTO candidateRequestTO = CandidateRequestTO.builder()
                .firstName("C")
                .lastName("C")
                .dateOfBirth("2023-11-01")
                .height("123")
                .weight("123")
                .race("race")
                .medicalDoc("")
                .photo("")
                .build();
        Assertions.assertDoesNotThrow(() -> candidateService.saveCandidate(candidateRequestTO, "login"));
    }

    @Test
    public void testSavingCandidate() {
        createUser("User");
        CandidateRequestTO candidateRequestTO = createTestCandidate();
        Assertions.assertDoesNotThrow(() -> candidateService.saveCandidate(candidateRequestTO, "User"));

    }

    @Test
    public void testGetCandidates() {
        List<CandidateResponseTO> candidates = candidateService.getCandidates();
        Assertions.assertFalse(candidates.isEmpty());
    }

    @Test
    public void testGetCandidate() {
        CandidateResponseTO candidateById = candidateService.getCandidateById(17);
        Assertions.assertNotNull(candidateById);
    }

    @Test
    public void testFailedGettingCandidate() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> candidateService.getCandidateById(-1));
    }

    @Test
    public void testUpdateCandidateStatus() {
        CandidateStatus newStatus = CandidateStatus.IN_PROGRESS;
        candidateService.updateCandidateStatus(17, newStatus);
        CandidateResponseTO candidateById = candidateService.getCandidateById(17);
        Assertions.assertEquals(newStatus, candidateById.getStatus());
    }

    private void createUser(String username) {
        RegistrationTO registrationTO = new RegistrationTO(username, "test");
        userService.register(registrationTO);
    }

    private CandidateRequestTO createTestCandidate() {
        return CandidateRequestTO.builder()
                .firstName("Piter")
                .lastName("Parker")
                .phone("777")
                .dateOfBirth("2022-01-01")
                .sex("unknown")
                .race("pendos")
                .weight("100")
                .height("180")
                .photo("")
                .medicalDoc("")
                .build();
    }
}
