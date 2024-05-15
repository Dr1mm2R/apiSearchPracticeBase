package com.example.apisearchpracticebase.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "PracticeBase")
public class PracticeBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Base_ID")
    private Long id;

    @Column(name = "Name_Base", nullable = false, unique = true)
    private String nameBase;

    @Column(name = "Description_About_Base", nullable = false)
    private String descriptionAboutBase;

    @Column(name = "Status_Dialing", nullable = false)
    private String statusDialing;

    @Lob
    @Column(name = "Photo_Place")
    private String photoPlace;

    @ManyToOne
    @JoinColumn(name = "Manager_ID", nullable = false)
    private PracticeManager manager;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameBase() {
        return nameBase;
    }

    public void setNameBase(String nameBase) {
        this.nameBase = nameBase;
    }

    public String getDescriptionAboutBase() {
        return descriptionAboutBase;
    }

    public void setDescriptionAboutBase(String descriptionAboutBase) {
        this.descriptionAboutBase = descriptionAboutBase;
    }

    public String getStatusDialing() {
        return statusDialing;
    }

    public void setStatusDialing(String statusDialing) {
        this.statusDialing = statusDialing;
    }

    public String getPhotoPlace() {
        return photoPlace;
    }

    public void setPhotoPlace(String photoPlace) {
        this.photoPlace = photoPlace;
    }

    public PracticeManager getManager() {
        return manager;
    }

    public void setManager(PracticeManager manager) {
        this.manager = manager;
    }

    public PracticeBase(Long id, String nameBase, String descriptionAboutBase, String statusDialing, String photoPlace, PracticeManager manager) {
        this.id = id;
        this.nameBase = nameBase;
        this.descriptionAboutBase = descriptionAboutBase;
        this.statusDialing = statusDialing;
        this.photoPlace = photoPlace;
        this.manager = manager;
    }

    public PracticeBase() {
    }
}
