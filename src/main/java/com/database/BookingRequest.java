package com.database;

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
    private int appt_file;

    public BookingRequest(int patient_id, int gp_id, List<Timestamp> start_times, Timestamp request_time, String subject, int appt_file) {
        this.patient_id = patient_id;
        this.gp_id = gp_id;
        this.start_times = start_times;
        this.request_time = request_time;
        this.subject = subject;
        this.appt_file = appt_file;
    }


    @Override
    public boolean save(DB db) {

        db.makeConnection();
        String changeString =
                "INSERT INTO appointments " +
                        "(patient_id, gp_id, start_times, request_time, subject, appt_file)" +
                        " VALUES (?, ?, ?, ? , ?, ?, ?)";
        PreparedStatement change;
        try {
            change = db.getConnection().prepareStatement(changeString);
            change.setInt(1, this.patient_id);
            change.setInt(2, this.gp_id);
            change.setArray(3, (Array) this.start_times);
            change.setTimestamp(4, this.request_time);
            change.setString(5,this.subject);
            change.setInt(6,-1);
            change.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        db.closeConnection();
        return true;
    }
}
