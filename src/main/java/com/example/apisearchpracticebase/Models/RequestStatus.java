package com.example.apisearchpracticebase.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "RequestStatus")
public class RequestStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long id;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "status_color")
    private String statusColor;

    public RequestStatus() {
    }

    public RequestStatus(Long id, String statusName, String statusColor, boolean isCanceled) {
        this.id = id;
        this.statusName = statusName;
        this.statusColor = statusColor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusColor() {
        return statusColor;
    }

    public void setStatusColor(String statusColor) {
        this.statusColor = statusColor;
    }
}