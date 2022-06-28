package com.database;

import net.minidev.json.JSONObject;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class BookingRequest implements Storeable {

    DB db;
    private int patient_id;
    private int gp_id;

    private List<Timestamp> start_times;

    private Timestamp request_time;
    private String subject;
    private String appt_details;
    public BookingRequest(int patient_id, int gp_id, List<Timestamp> start_times, Timestamp request_time, String subject, String appt_details) {
        this.patient_id = patient_id;
        this.gp_id = gp_id;
        this.start_times = start_times;
        this.request_time = request_time;
        this.subject = subject;
        this.appt_details = appt_details;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getGp_id() {
        return gp_id;
    }

    public void setGp_id(int gp_id) {
        this.gp_id = gp_id;
    }

    public List<Timestamp> getStart_times() {
        return start_times;
    }

    public void setStart_times(List<Timestamp> start_times) {
        this.start_times = start_times;
    }

    public Timestamp getRequest_time() {
        return request_time;
    }

    public void setRequest_time(Timestamp request_time) {
        this.request_time = request_time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAppt_details() {
        return appt_details;
    }

    public void setAppt_details(String appt_details) {
        this.appt_details = appt_details;
    }


    @Override
    public boolean save(DB db) {

        db.makeConnection();
        String changeString =
                "INSERT INTO booking_requests " +
                        "(patient_id, gp_id, start_times, request_time, subject, appt_details)" +
                        " VALUES (?, ?, ?, ? , ?, ?)";
        PreparedStatement change;
        try {
            change = db.getConnection().prepareStatement(changeString);
            change.setInt(1, this.patient_id);
            change.setInt(2, this.gp_id);
            change.setArray(3,db.getConnection().createArrayOf("timestamp",start_times.toArray()));
            change.setTimestamp(4, this.request_time);
            change.setString(5,this.subject);
            change.setString(6,this.appt_details);
            change.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        db.closeConnection();
        return true;
    }


}
