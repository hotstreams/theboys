package services;

import com.theboys.data.entities.Candidate;
import com.theboys.data.enums.CandidateStatus;
import com.theboys.data.repos.CandidateRepo;
import com.theboys.data.repos.UserRepo;
import com.theboys.security.PersistentUserManager;
import com.theboys.security.User;
import com.theboys.security.WebSecurityConfig;
import com.theboys.services.CandidateService;
import com.theboys.services.UserService;
import com.theboys.to.CandidateRequestTO;
import com.theboys.to.CandidateResponseTO;
import com.theboys.to.RegistrationTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

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
@TestPropertySource(locations = "classpath:application-test.properties")
public class CandidateServiceTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CandidateRepo candidateRepo;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private UserService userService;

    @AfterEach
    public void afterEach() {
        candidateRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    public void testSavingCandidate() {
        createUser();
        CandidateRequestTO candidateRequestTO = createTestCandidate();
        Assertions.assertDoesNotThrow(() -> candidateService.saveCandidate(candidateRequestTO, "test"));

    }

    @Test
    public void testGetCandidates() {
        createUser();
        CandidateRequestTO testCandidate = createTestCandidate();
        candidateService.saveCandidate(testCandidate, "test");
        List<CandidateResponseTO> candidates = candidateService.getCandidates();
        Assertions.assertEquals(1, candidates.size());
    }

    @Test
    public void testGetCandidate() {
        createUser();
        CandidateRequestTO testCandidate = createTestCandidate();
        candidateService.saveCandidate(testCandidate, "test");
        List<CandidateResponseTO> candidates = candidateService.getCandidates();
        Assertions.assertEquals(1, candidates.size());
        CandidateResponseTO candidateResponseTO = candidates.get(0);
        CandidateResponseTO candidateById = candidateService.getCandidateById(candidateResponseTO.getId());
        Assertions.assertEquals(candidateById, candidateResponseTO);
    }

    @Test
    public void testUpdateCandidateStatus() {
        createUser();
        CandidateRequestTO testCandidate = createTestCandidate();
        candidateService.saveCandidate(testCandidate, "test");
        List<CandidateResponseTO> candidates = candidateService.getCandidates();
        Assertions.assertEquals(1, candidates.size());
        CandidateResponseTO candidateResponseTO = candidates.get(0);
        Integer id = candidateResponseTO.getId();
        CandidateStatus newStatus = CandidateStatus.IN_PROGRESS;
        candidateService.updateCandidateStatus(id, newStatus);
        CandidateResponseTO candidateById = candidateService.getCandidateById(id);
        Assertions.assertEquals(newStatus, candidateById.getStatus());
    }

    private void createUser() {
        RegistrationTO registrationTO = new RegistrationTO("test", "test");
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
