package com.example.celixandroid;


public class User {

    private String fullName, email;
    private Boolean isDoctor;

    public User(){

    }

    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    public User(String fullName, String email, Boolean isDoctor) {
        this.fullName = fullName;
        this.email = email;
        this.isDoctor = isDoctor;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getDoctor() {
        return isDoctor;
    }

    public void setDoctor(Boolean doctor) {
        isDoctor = doctor;
    }
}
