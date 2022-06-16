package com.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Appt implements Storeable {
    DB db;
    private int patient_id;
    private int gp_id;
    private Timestamp start_time;
    private Timestamp end_time;
    private String subject;
    private int appt_file;
    private boolean cancelled;
    // Can only change subject, appt_file. Any other changes handled as creating a new record and then deleting the old one
    boolean[] changed = {false, false};

    public Appt(int patient_id, int gp_id, Timestamp start_time, Timestamp end_time, String subject, int appt_file, boolean cancelled) {
        this.patient_id = patient_id;
        this.gp_id = gp_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.subject = subject;
        this.appt_file = appt_file;
        this.cancelled = cancelled;
    }

    public static Timestamp getEndTimeOnAppt(Timestamp start_time) {
        // TODO
        return null;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public void setGp_id(int gp_id) {
        this.gp_id = gp_id;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAppt_file(int appt_file) {
        this.appt_file = appt_file;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public int getGp_id() {
        return gp_id;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public String getSubject() {
        return subject;
    }

    public int getAppt_file() {
        return appt_file;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean cancel(DB db) {
        String changeString = "DELETE FROM appointments WHERE start_time = ? AND gp_id = ?";
        PreparedStatement change;
        try {
            change = db.getConnection().prepareStatement(changeString);
            change.setTimestamp(1, start_time);
            change.setInt(2, this.gp_id);
            change.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return true;
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
                change.setString(1, "subject");
                change.setString(2, this.subject);
                change.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (changed[1]) {
            try {
                change.setString(1, "appt_file");
                change.setInt(2, this.appt_file);
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
