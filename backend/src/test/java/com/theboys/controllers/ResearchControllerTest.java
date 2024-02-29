package com.theboys.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theboys.SecurityTestConfigurationHelper;
import com.theboys.contollers.ResearchController;
import com.theboys.data.entities.Assessment;
import com.theboys.data.entities.Candidate;
import com.theboys.data.entities.Medicine;
import com.theboys.data.entities.Research;
import com.theboys.data.enums.ResearchStatus;
import com.theboys.data.repos.UserRepo;
import com.theboys.exceptions.RestResponseEntityExceptionHandler;
import com.theboys.security.WebSecurityConfig;
import com.theboys.services.ResearchService;
import com.theboys.to.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {ResearchController.class, WebSecurityConfig.class, RestResponseEntityExceptionHandler.class})
@Import(SecurityTestConfigurationHelper.class)
public class ResearchControllerTest {


    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResearchService researchService;

    @MockBean
    private UserRepo userRepo;

    @WithMockUser(roles = {"SCIENTIST"})
    @Test
    public void testGetResearches() throws Exception {
        Research research = createResearch();

        Mockito.when(researchService.getResearches())
                        .thenReturn(List.of(research));
        mockMvc.perform(MockMvcRequestBuilders.get("/researches"))
                        .andExpect(result -> {
                            MockHttpServletResponse response = result.getResponse();
                            int status = response.getStatus();
                            Assertions.assertEquals(200, status);
                            String contentAsString = response.getContentAsString();
                            List<ResearchResponseTO> list = objectMapper.readValue(contentAsString, new TypeReference<>() {
                            });
                            ResearchResponseTO responseTO = list.get(0);
                            Assertions.assertEquals(research.getResearchId(), responseTO.getResearchId());
                            Assertions.assertEquals(research.getDozeCount(), responseTO.getDozeCount());
                            Assertions.assertEquals(research.getTests().stream().map(TestResponseTO::new).collect(Collectors.toList()), responseTO.getTests());
                            Assertions.assertEquals(research.getMedicine().getName(), responseTO.getMedicine());
                            Candidate candidate = research.getCandidate();
                            Candidate responseTOCandidate = responseTO.getCandidate();
                            Assertions.assertEquals(candidate.getFirstName(), responseTOCandidate.getFirstName());
                            Assertions.assertEquals(candidate.getLastName(), responseTOCandidate.getLastName());
                            Assertions.assertEquals(research.getAssessments().stream().map(AssessmentResponseTO::new).collect(Collectors.toList()), responseTO.getAssessments());
                            Assertions.assertEquals(research.getDozeMl(), responseTO.getDozeMl());
                            Assertions.assertEquals(research.getResult(), responseTO.getResult());
                            Assertions.assertEquals(research.getStatus(), responseTO.getStatus());
                        });
    }

    @WithMockUser(roles = {"SCIENTIST"})
    @Test
    public void testGettingTest() throws Exception {
        com.theboys.data.entities.Test test = new com.theboys.data.entities.Test();
        test.setName("Test");
        test.setResearch(null);
        test.setResult("Good");

        Mockito.when(researchService.addTestsToResearch(Mockito.anyInt(), Mockito.any()))
                .thenReturn(test);
        TestRequestTO testRequestTO = new TestRequestTO();
        testRequestTO.setTestName("Test");
        String value = objectMapper.writeValueAsString(testRequestTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/researches/1/tests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(value))
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    int status = response.getStatus();
                    Assertions.assertEquals(200, status);

                    String contentAsString = response.getContentAsString();
                    TestResponseTO responseTO = objectMapper.readValue(contentAsString, TestResponseTO.class);
                    Assertions.assertEquals(testRequestTO.getTestName(), responseTO.getName());
                    Assertions.assertEquals(test.getResult(), responseTO.getResult());
                });
    }

    @WithMockUser(roles = {"SCIENTIST"})
    @Test
    public void testAddAssessmentsToResearch() throws Exception {
        AssessmentRequestTO assessmentRequestTO = new AssessmentRequestTO();
        assessmentRequestTO.setAssessmentName("Test");
        String content = objectMapper.writeValueAsString(assessmentRequestTO);
        Assessment assessment = new Assessment();
        assessment.setName("Test");
        assessment.setPassed(false);
        assessment.setComment("Bad");

        Mockito.when(researchService.addAssessmentsToResearch(Mockito.anyInt(), Mockito.any()))
                        .thenReturn(assessment);
        mockMvc.perform(MockMvcRequestBuilders.post("/researches/1/assessments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(result -> {
                    MockHttpServletResponse response = result.getResponse();
                    Assertions.assertEquals(200, response.getStatus());
                    String contentAsString = response.getContentAsString();
                    AssessmentResponseTO responseTO = objectMapper.readValue(contentAsString, AssessmentResponseTO.class);

                    Assertions.assertFalse(responseTO.isPassed());
                    Assertions.assertEquals("Test", responseTO.getName());
                    Assertions.assertEquals("Bad", responseTO.getComment());
                });
    }


    private Candidate createCandidate() {
        Candidate candidate = new Candidate();
        candidate.setFirstName("Test");
        candidate.setLastName("test");
        return candidate;
    }

    private Medicine createMedicine() {
        Medicine medicine = new Medicine();
        medicine.setName("Drug");
        medicine.setId(1);
        return medicine;
    }

    private Research createResearch() {
        Candidate candidate = createCandidate();
        Medicine medicine = createMedicine();

        Research research = new Research();
        research.setResearchId(1);
        research.setTests(Collections.emptyList());
        research.setAssessments(Collections.emptyList());
        research.setCandidate(candidate);
        research.setMedicine(medicine);
        research.setDozeCount(1);
        research.setDozeMl(1);
        research.setExamPassed(false);
        research.setScientist(null);
        research.setStatus(ResearchStatus.INITIALIZED);
        research.setResult("Died");
        return  research;
    }
}
