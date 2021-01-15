package health.monitoring.system;

public class PatienProfile {
    private String name,phone,email,address,symptoms,prescription,suggestion,bloodpressure,temprature,description;

    public PatienProfile(String name, String phone, String email, String address, String symptoms, String bloodpressure, String temprature, String description) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.symptoms = symptoms;
        this.bloodpressure = bloodpressure;
        this.temprature = temprature;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PatienProfile(String name, String phone, String email, String address, String symptoms, String prescription, String suggestion, String bloodpressure, String temprature, String description) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.symptoms = symptoms;
        this.prescription = prescription;
        this.suggestion = suggestion;
        this.bloodpressure = bloodpressure;
        this.temprature = temprature;
        this.description = description;
    }

    public PatienProfile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getBloodpressure() {
        return bloodpressure;
    }

    public void setBloodpressure(String bloodpressure) {
        this.bloodpressure = bloodpressure;
    }

    public String getTemprature() {
        return temprature;
    }

    public void setTemprature(String temprature) {
        this.temprature = temprature;
    }
}
