package com.example.apisearchpracticebase.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "PracticeManager")
public class PracticeManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Manager_ID")
    private Long id;

    @Column(name = "PostManager", nullable = false)
    private String postManager;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "secondName", nullable = false)
    private String secondName;

    @Column(name = "middleName", nullable = true)
    private String middleName;

    @Column(name = "Manager_Login", nullable = false, unique = true)
    private String managerLogin;

    @Column(name = "WorkExperience", nullable = false)
    private String workExperience;

    @Column(name = "WorkDirection", nullable = false)
    private String workDirection;

    @Column(name = "role", nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "Contact_ID", nullable = false)
    private Contact contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostManager() {
        return postManager;
    }

    public void setPostManager(String postManager) {
        this.postManager = postManager;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(String workExperience) {
        this.workExperience = workExperience;
    }

    public String getWorkDirection() {
        return workDirection;
    }

    public void setWorkDirection(String workDirection) {
        this.workDirection = workDirection;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public PracticeManager(Long id, String postManager, String workExperience, String workDirection, Contact contact, String login, String firstName, String secondName, String middleName, String role) {
        this.id = id;
        this.postManager = postManager;
        this.workExperience = workExperience;
        this.workDirection = workDirection;
        this.contact = contact;
        this.managerLogin = login;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.role = role;
    }

    public PracticeManager() {
    }

    public String getManagerLogin() {
        return managerLogin;
    }

    public void setManagerLogin(String managerLogin) {
        this.managerLogin = managerLogin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
