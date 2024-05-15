package com.example.apisearchpracticebase.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "ApiLogs")
public class WorkApiLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkApiLogs_ID")
    private Long id;

    @Column(name = "allCountRequests")
    private int allCountRequests;

    @Column(name = "errorCountRequests", nullable = false)
    private int errorCountRequests;

    @Column(name = "successfulCountRequests", nullable = false)
    private int successfulCountRequests;

    public WorkApiLogs() {
    }

    public WorkApiLogs(Long id, int allCountRequests, int errorCountRequests, int successfulCountRequests) {
        this.id = id;
        this.allCountRequests = allCountRequests;
        this.errorCountRequests = errorCountRequests;
        this.successfulCountRequests = successfulCountRequests;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAllCountRequests() {
        return allCountRequests;
    }

    public void setAllCountRequests(int allCountRequests) {
        this.allCountRequests = allCountRequests;
    }

    public int getErrorCountRequests() {
        return errorCountRequests;
    }

    public void setErrorCountRequests(int errorCountRequests) {
        this.errorCountRequests = errorCountRequests;
    }

    public int getSuccessfulCountRequests() {
        return successfulCountRequests;
    }

    public void setSuccessfulCountRequests(int successfulCountRequests) {
        this.successfulCountRequests = successfulCountRequests;
    }
}
