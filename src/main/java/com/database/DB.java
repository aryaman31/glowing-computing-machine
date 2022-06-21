package com.database;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public interface DB {

    public boolean setup();

    boolean makeConnection();

    boolean closeConnection();

    public boolean saveChanges(List<Storeable> changedRecords);

    public Connection getConnection();


    public List<Appt> getAllAppts(int patientId);

    public List<Appt> getWaitList(Timestamp time, int gpId);

    // Gets a list of partially complete appt objects (not on db) upon calling, representing potential appointments.
    public Patient getPatient(int patientId);

    public GP getGP(int gpId);

    public Appt getAppt(Timestamp startTime, int gpId);

    public int getNumGPAppointments(int gpId, Timestamp currentTime, Timestamp start_time);

    // public boolean sendEmail(String email, String subject, String msg);

    public void populate();
}
