import com.theboys.contollers.CandidateController;
import com.theboys.contollers.HeroController;
import com.theboys.data.enums.CandidateStatus;
import com.theboys.exceptions.EntityNotFoundException;
import com.theboys.exceptions.RestResponseEntityExceptionHandler;
import com.theboys.security.WebSecurityConfig;
import com.theboys.services.CandidateService;
import com.theboys.to.CandidateResponseTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import testhelpers.SecurityTestConfigurationHelper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {CandidateController.class, WebSecurityConfig.class, RestResponseEntityExceptionHandler.class})
@TestPropertySource(locations = {"/application-test.properties"})
@AutoConfigureMockMvc
@Import(SecurityTestConfigurationHelper.class)
public class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidateService candidateService;

    @WithMockUser(roles = {"MANAGER"})
    @Test
    public void testGetCandidates() throws Exception {
        List<CandidateResponseTO> candidates = new ArrayList<>();
        when(candidateService.getCandidates()).thenReturn(candidates);
        mockMvc.perform(get("/candidates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
        candidates.add(new CandidateResponseTO(1, "John", "Doe", "+74954567890", "1980-01-01", "male", "asian", 180, 80, "Somewhere", "", "Noname idiot", CandidateStatus.WAITING));
        mockMvc.perform(get("/candidates"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [{
                            "id": 1,
                            "firstName": "John",
                            "lastName": "Doe",
                            "phone":"+74954567890",
                            "dateOfBirth":"1980-01-01",
                            "sex":"male",
                            "race":"asian",
                            "height":180,
                            "weight":80,
                            "address":"Somewhere",
                            "photo":"",
                            "description":"Noname idiot",
                            "status":"WAITING"
                        }]
                        """));

    }

    @WithMockUser(roles = {"MANAGER"})
    @Test
    public void testGetCandidate() throws Exception {
        List<CandidateResponseTO> candidates = new ArrayList<>();
        candidates.add(new CandidateResponseTO(1, "John", "Doe", "+74954567890", "1980-01-01", "male", "asian", 180, 80, "Somewhere", "", "Noname idiot", CandidateStatus.WAITING));
        when(candidateService.getCandidates()).thenReturn(candidates);
        when(candidateService.getCandidateById(1)).thenReturn(candidates.get(0));
        mockMvc.perform(get("/candidates/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                            "id": 1,
                            "firstName": "John",
                            "lastName": "Doe",
                            "phone":"+74954567890",
                            "dateOfBirth":"1980-01-01",
                            "sex":"male",
                            "race":"asian",
                            "height":180,
                            "weight":80,
                            "address":"Somewhere",
                            "photo":"",
                            "description":"Noname idiot",
                            "status":"WAITING"
                        }
                        """));
    }

    @WithMockUser(roles = {"MANAGER"})
    @Test
    public void testGetCandidateNotFound() throws Exception {
        when(candidateService.getCandidates()).thenThrow(new EntityNotFoundException("Candidate with id=1 not found"));
        mockMvc.perform(get("/candidate/1"))
                .andExpect(status().isNotFound());
    }

    @WithAnonymousUser
    @Test
    public void testGetCandidatesUnauthorized() throws Exception {
        List<CandidateResponseTO> candidates = new ArrayList<>();
        when(candidateService.getCandidates()).thenReturn(candidates);
        mockMvc.perform(get("/candidates"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));

    }

    @WithMockUser(roles = {"MANAGER"})
    @Test
    public void testUpdateCandidateStatus() throws Exception {
        mockMvc.perform(patch("/candidates/1").content(
                                """
                                { "status":"IN_PROGRESS" }
                                """).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
//
//    @WithMockUser
//    @Test
//    public void testUpdateCandidateStatusForbidden() throws Exception {
//        mockMvc.perform(patch("/candidates/1").content(
//                        """
//                        { "status":"IN_PROGRESS" }
//                        """).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isForbidden());
//
//    }

    @WithMockUser(roles = {"MANAGER"})
    @Test
    public void testUpdateCandidateStatusInvalidStatus() throws Exception {
        mockMvc.perform(patch("/candidates/1").content(
                        """
                        { "status":"INVALID_STATUS" }
                        """).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @WithMockUser(roles = {"MANAGER"})
    @Test
    public void testCreateCandidate() throws Exception {
        mockMvc.perform(post("/candidates").content(
                        """
                        {
                            "firstName": "John",
                            "lastName": "Doe",
                            "phone":"+74954567890",
                            "dateOfBirth":"1980-01-01",
                            "sex":"male",
                            "race":"asian",
                            "height":180,
                            "weight":80,
                            "address":"Somewhere",
                            "photo":"",
                            "description":"",
                            "medicalDoc":""
                        }
                        """).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                        {
                            "status":"OK"
                        }
                        """));

    }

    @WithMockUser(roles = {"MANAGER"})
    @Test
    public void testCreateCandidateInvalidRequestBody() throws Exception {
        mockMvc.perform(post("/candidates").content(
                        """
                        {
                            "firstName": "",
                            "lastName": "",
                            "phone":"",
                            "dateOfBirth":"1980-01-01",
                            "sex":"male",
                            "race":"asian",
                            "height":180,
                            "weight":80,
                            "address":"",
                            "photo":"",
                            "description":"",
                            "medicalDoc":""
                        }
                        """).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
}