package com.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GP implements Storeable {

    private int gp_id;
    private String first_name;
    private String surname;
    private String salted;
    private String salt;
    private boolean[] changed = {false, false, false, false};

    public GP(int gp_id, String first_name, String surname, String salted, String salt) {
        this.gp_id = gp_id;
        this.first_name = first_name;
        this.surname = surname;
        this.salted = salted;
        this.salt = salt;
    }


    public void setGp_id(int gp_id) {
        this.gp_id = gp_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
        changed[0] = true;
    }

    public void setSurname(String surname) {
        this.surname = surname;
        changed[1] = true;
    }

    public void setSalted(String salted) {
        this.salted = salted;
        changed[2] = true;
    }

    public void setSalt(String salt) {
        this.salt = salt;
        changed[3] = true;
    }

    public int getGp_id() {
        return gp_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSalted() {
        return salted;
    }

    public String getSalt() {
        return salt;
    }




    @Override
    public boolean save(DB db) {
        db.makeConnection();

        String addNewRecord =  "INSERT INTO gps (gp_id, first_name, surname, salted, salt) " +
                "VALUES (?, ?,  ?, ?, ?)";

        String exists = "SELECT * FROM gps WHERE gp_id = ?";
        PreparedStatement existsS;

        PreparedStatement addNewRecordS;
        String updateRecord = "UPDATE gps " +
                "SET first_name = ?, surname = ?, salted = ?, salt = ? " +
                "WHERE gp_id = ?";
        PreparedStatement updateRecordS;
        try {
            existsS = db.getConnection().prepareStatement(exists);
            existsS.setInt(1, this.gp_id);
            boolean result = existsS.executeQuery().next();
            if (result) {
                updateRecordS = db.getConnection().prepareStatement(updateRecord);
                updateRecordS.setString(1, this.first_name);
                updateRecordS.setString(2, this.surname);
                updateRecordS.setString(3, this.salted);
                updateRecordS.setString(4, this.salt);
                updateRecordS.setInt(5, this.gp_id);
                updateRecordS.executeUpdate();
            } else {


                addNewRecordS = db.getConnection().prepareStatement(addNewRecord);
                addNewRecordS.setInt(1, this.gp_id);
                addNewRecordS.setString(2, this.first_name);
                addNewRecordS.setString(3, this.surname);
                addNewRecordS.setString(4, this.salted);
                addNewRecordS.setString(5, this.salt);
                addNewRecordS.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

        return true;

    }

}
