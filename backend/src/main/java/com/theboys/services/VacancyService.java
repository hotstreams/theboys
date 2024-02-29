package com.theboys.services;

import com.theboys.data.entities.Vacancy;
import com.theboys.data.enums.VacancyStatus;
import com.theboys.data.repos.VacancyRepo;
import com.theboys.exceptions.IncorrectRequest;
import com.theboys.to.VacancyRequestTO;
import com.theboys.to.VacancyResponseTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class VacancyService {

    private final VacancyRepo vacancyRepo;

    public VacancyService(VacancyRepo vacancyRepo) {
        this.vacancyRepo = vacancyRepo;
    }

    public List<VacancyResponseTO> getVacancies() {
        return vacancyRepo.findAll().stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    public VacancyResponseTO createVacancy(VacancyRequestTO request) {
        boolean nullAllFields = isNullAllFields(request);
        if (nullAllFields) {
            throw new IncorrectRequest("Все поля пустые");
        }
        Vacancy vacancyEntity = createVacancyEntity(request);
        Vacancy save = vacancyRepo.save(vacancyEntity);
        return buildResponse(save);
    }

    public void updateStatus(VacancyStatus status, Integer vacancyId) {
        vacancyRepo.updateCandidateRequestStatus(status, vacancyId);
    }

    private boolean isNullAllFields(VacancyRequestTO request) {
        String birthday = request.getBirthday();
        String description = request.getDescription();
        String sex = request.getSex();
        String weight = request.getWeight();
        String race = request.getRace();
        String height = request.getHeight();
        return Stream.of(birthday, description, sex, weight, race, height)
                .allMatch(Objects::isNull);
    }

    private Vacancy createVacancyEntity(VacancyRequestTO request) {
        Vacancy vacancy = new Vacancy();;
        vacancy.setStatus(VacancyStatus.OPEN);
        vacancy.setBirthday(request.getBirthday());
        vacancy.setRace(request.getRace());
        vacancy.setDescription(request.getDescription());
        vacancy.setSex(request.getSex());
        vacancy.setHeight(request.getHeight());
        vacancy.setWeight(request.getWeight());
        vacancy.setFirstName(request.getFirstName());
        vacancy.setLastName(request.getLastName());
        return vacancy;
    }

    private VacancyResponseTO buildResponse(Vacancy vacancy) {
        return VacancyResponseTO.builder()
                .id(vacancy.getVacancyId().toString())
                .firstName(vacancy.getFirstName())
                .lastName(vacancy.getLastName())
                .race(vacancy.getRace())
                .dateOfBirth(vacancy.getBirthday())
                .sex(vacancy.getSex())
                .vacancyStatus(vacancy.getStatus())
                .weight(vacancy.getWeight())
                .height(vacancy.getHeight())
                .description(vacancy.getDescription())
                .build();
    }

}
