package com.example.apisearchpracticebase.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "RequestSubmitted")
public class RequestSubmitted {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_submitted_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "practice_base_id")
    private PracticeBase practiceBase;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "submission_datetime")
    private LocalDateTime submissionDateTime;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private RequestStatus status;

    @Column(name = "is_canceled")
    private boolean isCanceled;

    @Column(name = "comment")
    private String comment;

    public RequestSubmitted() {
    }

    public RequestSubmitted(Long id, PracticeBase practiceBase, Student student, LocalDateTime submissionDateTime, RequestStatus status, boolean isCanceled, String comment) {
        this.id = id;
        this.practiceBase = practiceBase;
        this.student = student;
        this.submissionDateTime = submissionDateTime;
        this.status = status;
        this.isCanceled = isCanceled;
        this.comment = comment;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public PracticeBase getPracticeBase() {
        return practiceBase;
    }

    public void setPracticeBase(PracticeBase practiceBase) {
        this.practiceBase = practiceBase;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDateTime getSubmissionDateTime() {
        return submissionDateTime;
    }

    public void setSubmissionDateTime(LocalDateTime submissionDateTime) {
        this.submissionDateTime = submissionDateTime;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
