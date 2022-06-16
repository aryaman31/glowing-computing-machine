package com.database;

public class Patient {

    int patient_id;
    String first_name;
    String surname;
    int clinic_id;
    int hospital_id;
    String salted;
    String salt;

    public Patient(int patient_id, String first_name, String surname, int clinic_id, int hospital_id, String salted, String salt) {


        this.patient_id = patient_id;
        this.first_name = first_name;
        this.surname = surname;
        this.clinic_id = clinic_id;
        this.hospital_id = hospital_id;
        this.salted = salted;
        this.salt = salt;

    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(int clinic_id) {
        this.clinic_id = clinic_id;
    }

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }

    public String getSalted() {
        return salted;
    }

    public void setSalted(String salted) {
        this.salted = salted;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
