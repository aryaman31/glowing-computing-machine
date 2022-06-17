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
        String changeString = "UPDATE gps SET ? = ? WHERE gp_id = ?";
        PreparedStatement change;
        try {
            change = db.getConnection().prepareStatement(changeString);
            change.setInt(3, this.gp_id);
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
                change.setString(1, "salted");
                change.setString(2, this.salted);
                change.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (changed[3]) {
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
