package com.database;

import com.gp_scheduling.LogicFunctions;
import net.minidev.json.JSONObject;

import java.awt.print.Book;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

public class Appt implements Storeable {
    DB db;
    private int patient_id;
    private int gp_id;
    private Timestamp start_time;
    private Timestamp end_time;
    private String subject;
    private String appt_details;
    private boolean completed;
    // Can only change subject, appt_details. Any other changes handled as creating a new record and then deleting the old one
    boolean[] changed = {false, false};

    public Appt(int patient_id, int gp_id, Timestamp start_time, Timestamp end_time, String subject, String appt_details, boolean completed) {
        this.patient_id = patient_id;
        this.gp_id = gp_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.subject = subject;
        this.appt_details = appt_details;
        this.completed = completed;
    }

    public Appt(BookingRequest request, Timestamp start_time) {
        this.patient_id = request.getPatient_id();
        this.gp_id = request.getGp_id();
        this.start_time = start_time;
        this.end_time = LogicFunctions.getEndTime(start_time);
        this.subject = request.getSubject();
        this.appt_details = request.getAppt_details();
        this.completed = false;
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

    public void setAppt_details(String appt_details) {
        this.appt_details = appt_details;
    }

    public void setCancelled(boolean completed) {
        this.completed = completed;
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

    public String getAppt_details() {
        return appt_details;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean cancel(DB db) {
        db.makeConnection();
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
        } finally {
            db.closeConnection();
        }
        return true;
    }

    @Override
    public boolean save(DB db) {

        db.makeConnection();
        String changeString =
                "INSERT INTO appointments " +
                        "(patient_id, gp_id, start_time, end_time, subject, appt_details, completed)" +
                        " VALUES (?, ?, ?, ? , ?, ?, ?)";
        PreparedStatement change;
        try {
            change = db.getConnection().prepareStatement(changeString);
            change.setInt(1, this.patient_id);
            change.setInt(2, this.gp_id);
            change.setTimestamp(3, this.start_time);
            change.setTimestamp(4, new Timestamp(this.start_time.getTime() + 60 * 15 * 1_000));
            change.setString(5,this.subject);
            change.setString(6,this.appt_details);
            change.setBoolean(7,false);
            change.executeUpdate();

        } catch (SQLException throwables) {
            System.out.println(throwables.getStackTrace());
            return false;
        }
        db.closeConnection();
        return true;
    }

 //   @Override
    public JSONObject toJSON() {
        JSONObject toRet = new JSONObject();
        toRet.appendField("patient_id",this.patient_id);
        toRet.appendField("gp_id",this.gp_id);
        toRet.appendField("start_time",LogicFunctions.getStringTime(this.start_time));
        toRet.appendField("end_time",LogicFunctions.getStringTime(this.end_time));
        toRet.appendField("subject",this.subject);
        toRet.appendField("appt_details",this.appt_details);
        toRet.appendField("completed",this.completed);
        return toRet;
    }
}
