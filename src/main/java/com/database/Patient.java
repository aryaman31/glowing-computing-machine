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
        String changeString = "UPDATE patients SET ? = ? WHERE patient_id = ?";
        PreparedStatement change;
        try {
            change = db.getConnection().prepareStatement(changeString);
            change.setInt(3, this.patient_id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        if (changed[0]) {
            try {
                change.setString(1, "first_name");
                change.setString(2, this.first_name);
                change.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (changed[1]) {
            try {
                change.setString(1, "surname");
                change.setString(2, this.surname);
                change.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (changed[2]) {
            try {
                change.setString(1, "clinic_id");
                change.setInt(2, this.clinic_id);
                change.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (changed[3]) {
            try {
                change.setString(1, "hospital_id");
                change.setInt(2, this.hospital_id);
                change.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (changed[4]) {
            try {
                change.setString(1, "salted");
                change.setString(2, this.salted);
                change.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (changed[5]) {
            try {
                change.setString(1, "salt");
                change.setString(2, this.salt);
                change.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        db.closeConnection();
        return true;
    }
}
