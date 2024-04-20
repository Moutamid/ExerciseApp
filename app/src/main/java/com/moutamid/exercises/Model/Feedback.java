package com.moutamid.exercises.Model;

import com.google.firebase.Timestamp;

public class Feedback {
    String message;


    String email, model, device, brand, display, version, version_name, country_code;
    Timestamp timestamp;


    public Feedback(String email, String message, String model, String device, String brand, String display, String version, String version_name, String country_code, Timestamp timestamp) {
        this.email = email;
        this.message = message;
        this.model = model;
        this.device = device;
        this.brand = brand;
        this.display = display;
        this.version = version;
        this.version_name = version_name;
        this.country_code = country_code;
        this.timestamp = timestamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}