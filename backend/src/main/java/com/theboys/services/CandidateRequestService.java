package com.theboys.services;

import com.theboys.data.entities.CandidateRequest;
import com.theboys.data.entities.Scientist;
import com.theboys.data.enums.CandidateRequestStatus;
import com.theboys.data.repos.CandidateRequestRepo;
import com.theboys.data.repos.ScientistRepo;
import com.theboys.exceptions.EntityNotFoundException;
import com.theboys.exceptions.IncorrectRequest;
import com.theboys.security.User;
import com.theboys.security.UserRole;
import com.theboys.to.RequestCandidateRequestTO;
import com.theboys.to.RequestCandidateResponseTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CandidateRequestService {

    private final CandidateRequestRepo candidateRequestRepo;

    private final ScientistRepo scientistRepo;

    private final UserService userService;

    public CandidateRequestService(CandidateRequestRepo candidateRequestRepo,
                                   ScientistRepo scientistRepo,
                                   UserService userService) {
        this.candidateRequestRepo = candidateRequestRepo;
        this.scientistRepo = scientistRepo;
        this.userService = userService;
    }

    public void updateStatus(CandidateRequestStatus status, Integer requestId) {
        candidateRequestRepo.updateCandidateRequestStatus(status, requestId);
    }

    public RequestCandidateResponseTO createCandidateRequest(String username, RequestCandidateRequestTO request) {
        boolean nullAllFields = isNullAllFields(request);
        if (nullAllFields) {
            throw new IncorrectRequest("Все поля пустые");
        }
        User userByLogin = userService.getUserByLogin(username);
        Scientist scientist = scientistRepo.findById(userByLogin.getId()).orElse(null);
        if (scientist == null) {
            throw new EntityNotFoundException("Не удалось найти такого учёного");
        }
        CandidateRequest candidateRequest = createCandidateRequest(scientist, request);
        CandidateRequest save = candidateRequestRepo.save(candidateRequest);
        return buildResponse(save);
    }

    public List<RequestCandidateResponseTO> getCandidateRequests(String username) {
        User userByLogin = userService.getUserByLogin(username);
        UserRole role = userByLogin.getRole();
        List<CandidateRequest> candidateRequests;
        if (role == UserRole.SCIENTIST) {
            candidateRequests = candidateRequestRepo.findByScientistScientistId(userByLogin.getId());
        } else {
            candidateRequests = candidateRequestRepo.findAll();
        }
        return candidateRequests.stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    private CandidateRequest createCandidateRequest(Scientist scientist, RequestCandidateRequestTO candidateRequestTO) {
        CandidateRequest candidateRequest = new CandidateRequest();
        candidateRequest.setScientist(scientist);
        candidateRequest.setStatus(CandidateRequestStatus.PENDING);
        candidateRequest.setBirthday(candidateRequestTO.getBirthday());
        candidateRequest.setRace(candidateRequestTO.getRace());
        candidateRequest.setDescription(candidateRequestTO.getDescription());
        candidateRequest.setSex(candidateRequestTO.getSex());
        candidateRequest.setHeight(candidateRequestTO.getHeight());
        candidateRequest.setWeight(candidateRequestTO.getWeight());
        return candidateRequest;
    }

    private RequestCandidateResponseTO buildResponse(CandidateRequest candidateRequest) {
        return RequestCandidateResponseTO.builder()
                .id(candidateRequest.getCandidateId())
                .race(candidateRequest.getRace())
                .dateOfBirth(candidateRequest.getBirthday().toString())
                .sex(candidateRequest.getSex())
                .status(candidateRequest.getStatus())
                .weight(candidateRequest.getWeight())
                .height(candidateRequest.getHeight())
                .description(candidateRequest.getDescription())
                .scientistId(candidateRequest.getScientist().getScientistId())
                .build();
    }

    private boolean isNullAllFields(RequestCandidateRequestTO request) {
        LocalDate birthday = request.getBirthday();
        String description = request.getDescription();
        String sex = request.getSex();
        Integer weight = request.getWeight();
        String race = request.getRace();
        Integer height = request.getHeight();
        return Stream.of(birthday, description, sex, weight, race, height)
                .allMatch(Objects::isNull);
    }

}
