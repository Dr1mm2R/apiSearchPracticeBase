package com.example.apisearchpracticebase.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "Contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Contact_ID")
    private Long id;

    @Column(name = "Number_Phone", nullable = false)
    private String phoneNumber;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Location_Address", nullable = false)
    private String address;

    @Column(name = "vk_page_data", nullable = false)
    private String vkPageData;

    @Column(name = "whats_app_data", nullable = false)
    private String whatsAppData;

    @Column(name = "telegram_data", nullable = false)
    private String telegramData;

    public Contact() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Contact(Long id, String phoneNumber, String email, String address) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public String getVkPageData() {
        return vkPageData;
    }

    public void setVkPageData(String vkPageData) {
        this.vkPageData = vkPageData;
    }

    public String getWhatsAppData() {
        return whatsAppData;
    }

    public void setWhatsAppData(String whatsUpData) {
        this.whatsAppData = whatsUpData;
    }

    public String getTelegramData() {
        return telegramData;
    }

    public void setTelegramData(String telegramData) {
        this.telegramData = telegramData;
    }
}
