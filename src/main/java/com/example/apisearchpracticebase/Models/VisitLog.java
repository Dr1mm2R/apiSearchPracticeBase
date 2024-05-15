package com.example.apisearchpracticebase.Models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "VisitLog")
public class VisitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Visit_ID")
    private Long id;

    @Column(name = "DateVisit", nullable = false)
    private Date dateVisit;

    @Column(name = "Type_of_Work", nullable = false)
    private String typeOfWork;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "practiceBase_id")
    private PracticeBase practiceBase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit(Date dateVisit) {
        this.dateVisit = dateVisit;
    }

    public String getTypeOfWork() {
        return typeOfWork;
    }

    public void setTypeOfWork(String typeOfWork) {
        this.typeOfWork = typeOfWork;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public VisitLog(Long id, Date dateVisit, String typeOfWork, Student student, PracticeBase practiceBase) {
        this.id = id;
        this.dateVisit = dateVisit;
        this.typeOfWork = typeOfWork;
        this.student = student;
        this.practiceBase = practiceBase;
    }

    public VisitLog() {
    }

    public PracticeBase getPracticeBase() {
        return practiceBase;
    }

    public void setPracticeBase(PracticeBase practiceBase) {
        this.practiceBase = practiceBase;
    }
}
