package com.theboys.services;

import com.theboys.data.entities.Candidate;
import com.theboys.data.entities.CandidateRequest;
import com.theboys.data.entities.Scientist;
import com.theboys.data.enums.CandidateStatus;
import com.theboys.data.repos.CandidateRepo;
import com.theboys.data.repos.CandidateRequestRepo;
import com.theboys.exceptions.EntityNotFoundException;
import com.theboys.security.User;
import com.theboys.security.UserRole;
import com.theboys.to.CandidateRequestTO;
import com.theboys.to.CandidateResponseTO;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    private final CandidateRepo candidateRepo;

    private final CandidateRequestRepo candidateRequestRepo;
    private final UserService userService;

    @Autowired
    public CandidateService(CandidateRepo candidateRepo,
                            UserService userService,
                            CandidateRequestRepo candidateRequestRepo) {
        this.candidateRepo = candidateRepo;
        this.userService = userService;
        this.candidateRequestRepo = candidateRequestRepo;
    }

    @Transactional
    public void createCandidateRequest(Scientist scientist, CandidateRequestTO candidateRequestTO) {
        CandidateRequest candidateRequest = convertCandidateRequest(scientist, candidateRequestTO);
        candidateRequestRepo.save(candidateRequest);
    }


    public List<CandidateResponseTO> getCandidates() {
        List<Candidate> candidates = (List<Candidate>) candidateRepo.findAll();
        return candidates.stream()
                .map(this::createCandidateResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveCandidate(CandidateRequestTO candidateRequestTO, String username) {
        User user = userService.getUserByLogin(username);
        userService.updateUserRole(UserRole.CANDIDATE, user.getId());
        candidateRepo.save(createCandidate(user.getId(), candidateRequestTO));
    }

    public CandidateResponseTO getCandidateById(@NonNull Integer userId) {
        Optional<Candidate> candidate = candidateRepo.findById(userId);
        return candidate.map(this::createCandidateResponse)
                .orElseThrow(() -> new EntityNotFoundException("Candidate with id=" + userId + " not found"));
    }

    public void updateCandidateStatus(@NonNull Integer userId, @NonNull CandidateStatus status) {
        candidateRepo.updateCandidateStatus(status, userId);
    }

    private CandidateResponseTO createCandidateResponse(Candidate candidate) {
        return new CandidateResponseTO(
                candidate.getCandidateId(),
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getPhone(),
                candidate.getBirthday().format(DateTimeFormatter.ISO_LOCAL_DATE),
                candidate.getSex(),
                candidate.getRace(),
                candidate.getHeight(),
                candidate.getWeight(),
                candidate.getAddress(),
                Base64.encodeBase64String(candidate.getPhoto()),
                candidate.getDescription(),
                candidate.getCandidateStatus()
        );
    }

    private Candidate createCandidate(Integer userId, CandidateRequestTO candidateRequestTO) {
        return new Candidate(
                userId,
                candidateRequestTO.getFirstName(),
                candidateRequestTO.getLastName(),
                candidateRequestTO.getPhone(),
                LocalDate.parse(candidateRequestTO.getDateOfBirth(), DateTimeFormatter.ISO_LOCAL_DATE),
                candidateRequestTO.getSex(),
                candidateRequestTO.getRace(),
                Integer.parseInt(candidateRequestTO.getWeight()),
                Integer.parseInt(candidateRequestTO.getHeight()),
                candidateRequestTO.getAddress(),
                candidateRequestTO.getDescription(),
                Base64.decodeBase64(candidateRequestTO.getPhoto()),
                Base64.decodeBase64(candidateRequestTO.getMedicalDoc())
        );
    }

    private CandidateRequest convertCandidateRequest(Scientist scientist, CandidateRequestTO candidateRequestTO) {
        CandidateRequest candidateRequest = new CandidateRequest();
        candidateRequest.setRace(candidateRequestTO.getRace());
        String dateOfBirth = candidateRequestTO.getDateOfBirth();
        candidateRequest.setBirthday(LocalDate.parse(dateOfBirth));
        candidateRequest.setScientist(scientist);
        return candidateRequest;
    }
}
