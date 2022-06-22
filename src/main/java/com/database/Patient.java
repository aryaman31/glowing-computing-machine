package com.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Patient implements Storeable {

    int patient_id;
    String first_name;
    String surname;
    int clinic_id;
    int hospital_id;
    String salted;
    String salt;
    private boolean changed[] = {false, false, false, false, false, false};

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

    public boolean save(DB db) {

        db.makeConnection();
        String exists = "SELECT * FROM patients WHERE patient_id = ?";
        PreparedStatement existsS;
        String addNewRecord = "INSERT INTO patients " +
                "(patient_id, first_name, surname, clinic_id, hospital_id, salted, salt) " +
                "VALUES (?, ?,  ?, ?, ?, ?, ?)";
        PreparedStatement addNewRecordS;
        String updateRecord = "UPDATE patients\n" +
                "SET first_name = ?, surname = ?, clinic_id = ?, hospital_id = ?, salted = ?, salt = ? " +
                "WHERE patient_id = ?";
        PreparedStatement updateRecordS;
        try {
            existsS = db.getConnection().prepareStatement(exists);
            existsS.setInt(1, this.patient_id);
            boolean result = existsS.executeQuery().next();
            if (result) {
                updateRecordS = db.getConnection().prepareStatement(updateRecord);
                updateRecordS.setString(1, this.first_name);
                updateRecordS.setString(2, this.surname);
                updateRecordS.setInt(3, this.clinic_id);
                updateRecordS.setInt(4, this.hospital_id);
                updateRecordS.setString(5, this.salted);
                updateRecordS.setString(6, this.salt);
                updateRecordS.setInt(7, this.patient_id);
                updateRecordS.executeUpdate();
            } else {
                addNewRecordS = db.getConnection().prepareStatement(addNewRecord);
                addNewRecordS.setInt(1, this.patient_id);
                addNewRecordS.setString(2, this.first_name);
                addNewRecordS.setString(3, this.surname);
                addNewRecordS.setInt(4, this.clinic_id);
                addNewRecordS.setInt(5, this.hospital_id);
                addNewRecordS.setString(6, this.salted);
                addNewRecordS.setString(7, this.salt);
                addNewRecordS.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

        return true;

    }
}
