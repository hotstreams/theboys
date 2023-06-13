package com.theboys.data.entities;

import com.theboys.data.enums.CandidateStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "candidates")
public class Candidate {

    @Id
    @Column(name = "candidate_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer candidateId;

    //todo Add fetch type lazy
    @OneToMany(mappedBy = "candidate")
    private List<HeroCreationEvent> events;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "sex")
    private String sex;

    @Column(name = "race")
    private String race;

    @Column(name = "weight", nullable = false)
    private int weight;

    @Column(name = "height", nullable = false)
    private int height;

    @Column(name = "address")
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "medicalDoc")
    private byte[] medicalDocument;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private CandidateStatus candidateStatus;

    public Candidate() {
    }

    public Candidate(String firstName, String lastName, String phone, LocalDate birthday, String sex, String race, int weight, int height, String address, String description, byte[] photo, byte[] medicalDocument) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.birthday = birthday;
        this.sex = sex;
        this.race = race;
        this.weight = weight;
        this.height = height;
        this.address = address;
        this.description = description;
        this.photo = photo;
        this.medicalDocument = medicalDocument;
        this.candidateStatus = CandidateStatus.WAITING;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public List<HeroCreationEvent> getEvents() {
        return events;
    }

    public void setEvents(List<HeroCreationEvent> events) {
        this.events = events;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getMedicalDocument() {
        return medicalDocument;
    }

    public void setMedicalDocument(byte[] medicalDocument) {
        this.medicalDocument = medicalDocument;
    }

    public CandidateStatus getCandidateStatus() {
        return candidateStatus;
    }

    public void setCandidateStatus(CandidateStatus candidateStatus) {
        this.candidateStatus = candidateStatus;
    }
}
