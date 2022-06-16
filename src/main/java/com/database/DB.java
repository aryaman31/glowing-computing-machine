package com.database;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public interface DB {

    public boolean setup();
    public Connection makeConnection();
    public boolean closeConnection();




    public List<Appt> getWaitList(Timestamp time, int gpId);
    // Gets a list of partially complete appt objects (not on db) upon calling, representing potential appointments.
    public Patient getPatient(int patientId);
    public GP getGP(int gpId);
    public Appt getAppt(Timestamp startTime, int gpId);
    public int getNumGPAppointments(int gpId, Timestamp currentTime, Timestamp start_time);


}
