package com.database;

import com.gp_scheduling.LogicFunctions;

import java.sql.*;
import java.util.*;

public class DBWrapper implements DB {

    Connection con = null;
    private boolean localDebug = true;
    String dburl = localDebug ? "jdbc:postgresql://localhost/testdb?user=postgres&password=123" : System.getenv("JDBC_DATABASE_URL");
    // db is contained in a url - identified by : jdbc:postgresql://host:post/com.database locally,
    // else user System.getenv


    @Override
    public boolean setup() {
        /** Opens a connection to the com.database and populates it with tables if they do not already exist.
         */

        try {
            if (!this.makeConnection()) {
                return false;
            }

            String createPatients =
                    "CREATE TABLE IF NOT EXISTS patients (" +
                            "patient_id bigint CONSTRAINT patient_key PRIMARY KEY," +
                            "first_name varchar(20)," +
                            "surname varchar(20)," +
                            "clinic_id int," +
                            "hospital_id int," +
                            "salted varchar(66)," +
                            "salt varchar(66), " +
                            "email varchar(40) " +
                            ")";
            String createGPs =
                    "CREATE TABLE IF NOT EXISTS gps (" +
                            "gp_id bigint CONSTRAINT gp_key PRIMARY KEY," + // Employee number append clinic number append hospital number
                            "salted varchar(66)," +
                            "salt varchar(66)," +
                            "first_name varchar(20)," +
                            "surname varchar(20), " +
                            "email varchar(40) " +
                            ")";

            String createRecords =
                    "CREATE TABLE IF NOT EXISTS appointments (" +
                            "patient_id bigint," +
                            "gp_id bigint," +
                            "start_time timestamp," +
                            "end_time timestamp," +
                            "subject varchar(50)," +
                            "appt_details text," + // Files numerically produced representing appt, path should be known before
                            "completed boolean," +
                            "PRIMARY KEY(start_time,gp_id)," +
                            "CONSTRAINT patient_key FOREIGN KEY(patient_id) REFERENCES patients(patient_id)," +
                            "CONSTRAINT gp_key FOREIGN KEY(gp_id) REFERENCES gps(gp_id)" +
                            ")";

            String createWaitList =
                    "CREATE TABLE IF NOT EXISTS wait_list (" +
                            "patient_id bigint," +
                            "gp_id bigint," +
                            "start_times timestamp[]," +
                            "subject varchar(50)," +
                            "requested timestamp," +
                            "CONSTRAINT wait_key PRIMARY KEY(patient_id,subject)," +
                            "CONSTRAINT gp_key FOREIGN KEY(gp_id) REFERENCES gps(gp_id)," +
                            "CONSTRAINT patient_key FOREIGN KEY(patient_id) REFERENCES patients(patient_id)" +
                            ")";

            String createBookingRequests =
                    "CREATE TABLE IF NOT EXISTS booking_requests (" +
                        "patient_id bigint," +
                        "gp_id bigint," +
                        "start_times timestamp[]," +
                        "subject varchar(50)," +
                        "appt_details text," + // Txt representing details of appt
                        "booking_requests_key SERIAL PRIMARY KEY," +
                        "request_time timestamp, " +
                        "CONSTRAINT patient_key FOREIGN KEY(patient_id) REFERENCES patients(patient_id)," +
                        "CONSTRAINT gp_key FOREIGN KEY(gp_id) REFERENCES gps(gp_id)" +
                        ")";

            PreparedStatement createPatientsS = con.prepareStatement(createPatients);
            PreparedStatement createGPsS = con.prepareStatement(createGPs);
            PreparedStatement createRecordsS = con.prepareStatement(createRecords);
            PreparedStatement createWaitListS = con.prepareStatement(createWaitList);
            PreparedStatement createBookingRequestsS = con.prepareStatement(createBookingRequests);
            createPatientsS.execute();
            createPatientsS.close();

            createGPsS.execute();
            createGPsS.close();

            createRecordsS.execute();
            createRecordsS.close();

            createWaitListS.execute();
            createWaitListS.close();

            createBookingRequestsS.execute();
            createBookingRequestsS.close();

            this.closeConnection();

        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
            return false;
        }

        return true;

    }

    @Override
    public void populate() {

        try {
            this.makeConnection();

            Patient alice = new Patient(1,"Alice","Smith",1,1,"","", "alice.smith@gmail.com");
            alice.save(this);

            Patient bob = new Patient(2,"Bob","Jones",1,1,"","","bob.jones@gmail.com");
            bob.save(this);

            Patient charlie = new Patient(3,"Charlie","Skinner",1,1,"","", "charlie.skinner@gmail.com");
            charlie.save(this);

            GP thomas = new GP(111,"Thomas","Jackson","","",
                    "thomas.jackson@gmail.com");
            thomas.save(this);

            GP smith = new GP(1, "Dr", "Smith", "", "", "dr.smith@gmail.com");
            smith.save(this);

            GP garcia = new GP(2, "Dr", "Garcia", "", "", "dr.garcia@gmail.com");
            garcia.save(this);

            GP jones = new GP(3, "Dr", "Jones", "", "", "dr.jones@gmail.com");
            jones.save(this);

        } finally {
            this.closeConnection();
        }
    }

    @Override
    public Patient getPatient(int patientId) {
        if (!this.makeConnection()) {
            return null;
        }

        try {
            PreparedStatement patientQuery = con.prepareStatement("SELECT * FROM patients WHERE patient_id= ?");
            patientQuery.setInt(1, patientId);
            ResultSet patientResult = patientQuery.executeQuery();
            if (!patientResult.next()) {
                return null;
            }
            Patient patient =
                    new Patient(
                            patientResult.getInt("patient_id"),
                            patientResult.getString("first_name"),
                            patientResult.getString("surname"),
                            patientResult.getInt("clinic_id"),
                            patientResult.getInt("hospital_id"),
                            patientResult.getString("salted"),
                            patientResult.getString("salt"),
                            patientResult.getString("email")
                    );
            return patient;
        } catch (SQLException E) {
            System.out.println(E.getStackTrace());
            return null;
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public Integer getPatientId(String name) {
        System.out.println("IN CORRECT FUNCTION");
        if (!this.makeConnection()) {
            return null;
        }

        try {
            PreparedStatement patientQuery = con.prepareStatement("SELECT * FROM patients WHERE first_name= ?");
            patientQuery.setString(1, name);
            ResultSet patientResult = patientQuery.executeQuery();
            if (!patientResult.next()) {
                return null;
            }
            return patientResult.getInt("patient_id");
        } catch (SQLException E) {
            System.out.println(E.getStackTrace());
            return null;
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public GP getGP(int gpId) {
        if (!this.makeConnection()) {
            return null;
        }

        try {
            PreparedStatement gpQuery = con.prepareStatement("SELECT * FROM gps WHERE gp_id = ?");
            gpQuery.setInt(1, gpId);
            ResultSet gpResult = gpQuery.executeQuery();
            if (!gpResult.next()) {
                return null;
            }
            GP gp =
                    new GP(
                            gpResult.getInt("gp_id"),
                            gpResult.getString("first_name"),
                            gpResult.getString("surname"),
                            gpResult.getString("salted"),
                            gpResult.getString("salt"),
                            gpResult.getString("email")
                    );
            return gp;
        } catch (SQLException E) {
            System.out.println(E.getStackTrace());
            return null;
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public Appt getAppt(Timestamp time, int gpId) {
        if (!this.makeConnection()) {
            return null;
        }

        try {
            PreparedStatement apptQuery = con.prepareStatement("SELECT * FROM appointments WHERE start_time = ? AND gp_id = ?");
            apptQuery.setTimestamp(1, time);
            apptQuery.setInt(2, gpId);
            ResultSet apptResult = apptQuery.executeQuery();
            if (!apptResult.next()) {
                return new Appt(-1, -1, new Timestamp(0), new Timestamp(0), "", "", false);
            }
            Appt appt =
                    new Appt(
                            apptResult.getInt("patient_id"),
                            apptResult.getInt("gp_id"),
                            apptResult.getTimestamp("start_time"),
                            apptResult.getTimestamp("end_time"),
                            apptResult.getString("subject"),
                            apptResult.getString("appt_details"),
                            apptResult.getBoolean("completed")

                    );
            appt.db = this;
            return appt;
        } catch (SQLException E) {
            System.out.println(E.getStackTrace());
            return null;
        } finally {
            this.makeConnection();
        }
    }

    @Override
    public List<Appt> getBookingRequests(Timestamp timeslot, int gpId) {

        if (!this.makeConnection()) {
            return null;
        }

        try {
            PreparedStatement apptQuery = con.prepareStatement(
                    "SELECT * FROM booking_requests WHERE ? in start_times AND gp_id = ? ORDER BY request_time ASC");
            apptQuery.setTimestamp(1, timeslot);
            apptQuery.setInt(2, gpId);
            ResultSet apptResult = apptQuery.executeQuery();
            List<Appt> toReturn = new LinkedList<>();
            while (apptResult.next()) {
                Appt appt =
                        new Appt(
                                apptResult.getInt("patient_id"),
                                apptResult.getInt("gp_id"),
                                timeslot,
                                new Timestamp(timeslot.getTime() + LogicFunctions.slot_length),
                                apptResult.getString("subject"),
                                apptResult.getString("appt_details"),
                                false
                        );
                appt.db = this;
                toReturn.add(appt);
            }
            return toReturn;
        } catch (SQLException E) {
            System.out.println(E.getStackTrace());
            return null;
        } finally {
            this.makeConnection();
        }

    }

    @Override
    public int getNumGPAppointments(int gpId, Timestamp current_time, Timestamp start_time) {
        // Returns number of appointments for Database.GP between current time and start_time

        if (!this.makeConnection()) {
            return -1;
        }

        try {
            PreparedStatement apptQuery =
                    con.prepareStatement("SELECT * FROM appointments WHERE gp_id = ? AND completed = false");
            apptQuery.setInt(1, gpId);
            ResultSet gpAppts = apptQuery.executeQuery();
            if (!gpAppts.next()) {
                return 0;
            }
            gpAppts.last();    // moves cursor to the last row
            int size = gpAppts.getRow(); // get row id
            return size;
        } catch (SQLException E) {
            System.out.println(E.getStackTrace());
            return -1;
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public boolean markAppointment(int gp_id, Timestamp timeStamp, String notes) {


        if (!this.makeConnection()) {
            return false;
        }

        try {
            PreparedStatement apptQuery =
                    con.prepareStatement("UPDATE appointments SET completed = true WHERE gp_id = ? AND ? = start_time");
            PreparedStatement updateNotes =
                    con.prepareStatement("UPDATE appointments " +
                            "SET appt_details = appt_details + '\nAppointment Notes:\n' + ? WHERE gp_id = ? and ? = start_time");
            apptQuery.setInt(1, gp_id);
            apptQuery.setTimestamp(2, timeStamp);
            apptQuery.executeUpdate();
            updateNotes.setString(1,notes);
            updateNotes.setInt(2,gp_id);
            updateNotes.setTimestamp(3, timeStamp);
            updateNotes.executeUpdate();
            return true;
        } catch (SQLException E) {
            System.out.println(E.getStackTrace());
            return false;
        } finally {
            this.closeConnection();
        }


    }

    @Override
    public List<Appt> getWaitList(Timestamp time, int gpId) {
        // On calling this, should make a new table of appointments, then upon any access to this table,
        // Should add that appointment into the main appointment list, create an appt file and delete the table

        if (!this.makeConnection()) {
            return null;
        }

        try {
            PreparedStatement wlq = con.prepareStatement(
                    "SELECT * FROM wait_list WHERE ? = ANY(start_times) AND gp_id = ?");
            wlq.setTimestamp(1, time);
            wlq.setInt(2, gpId);
            ResultSet wlResult = wlq.executeQuery();
            List<Appt> wl = new ArrayList<Appt>();
            while (wlResult.next()) {
                Appt potentialAppt =
                        new Appt(
                                wlResult.getInt("patient_id"),
                                wlResult.getInt("gp_id"),
                                time,
                                LogicFunctions.getEndTime(time),
                                wlResult.getString("subject"),
                                wlResult.getString("appt_details"),
                                false

                        );
            }
            return wl;
        } catch (SQLException E) {
            E.printStackTrace();
            return null;
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public boolean addToWaitList(Timestamp time, int gpId, Appt appt) {

        //TODO
        return false;
    }

    @Override
    public boolean adjustRequestsTable(Appt appt) {

        /** Role :
         *  - Removes appropriate booking request row               x
         *  - Removes booking request timeslots from other rows     x
         *  - Deletes any now empty booking requests                x
         *      - notifies those users                              x
         */

        if (!this.makeConnection()) {
            return false;
        }

        try {
            PreparedStatement removeRequest = con.prepareStatement(
                    "DELETE FROM booking_requests WHERE patient_id = ? AND gp_id = ?");
            removeRequest.setInt(1, appt.getPatient_id());
            removeRequest.setInt(2, appt.getGp_id());
            removeRequest.executeUpdate();
            PreparedStatement getAffected = con.prepareStatement(
                    "SELECT * FROM booking_requests WHERE ? =ANY(start_times) AND gp_id = ?");

            getAffected.setTimestamp(1,appt.getStart_time());
            getAffected.setInt(2,appt.getGp_id());


            PreparedStatement adjustStartTimes = con.prepareStatement(
                    "UPDATE booking_requests SET start_times = ? WHERE patient_id = ? AND gp_id = ?"
            );


            ResultSet affected = getAffected.executeQuery();
            while (affected.next()) {
                Timestamp[] remaining_times = (Timestamp[]) affected.getArray("start_times").getArray();
                if (remaining_times.length == 1) {
                    Patient patient = getPatient(affected.getInt("patient_id"));
                    String message = String.format("Dear %s %s, we failed to find a slot for your appointment regarding %s. Please try again later",
                            patient.getFirst_name(),patient.getSurname(),appt.getSubject());
                    notify(patient.getEmail(),"Appointment: "+appt.getSubject(),message);
                    removeRequest.setInt(1,affected.getInt("patient_id"));
                    removeRequest.setInt(2,affected.getInt("gp_id"));
                    removeRequest.executeUpdate();
                } else {
                    List<Timestamp> newStartTimes = Arrays.asList((Timestamp[]) affected.getArray("start_times").getArray());
                    newStartTimes.remove(appt.getStart_time());
                    adjustStartTimes.setArray(1,con.createArrayOf("timestamp", newStartTimes.toArray()));
                    adjustStartTimes.setInt(2,affected.getInt("patient_id"));
                    adjustStartTimes.setInt(3,affected.getInt("gp_id"));
                    adjustStartTimes.executeUpdate();
                }
            }
            return true;
        } catch (SQLException E) {
            E.printStackTrace();
            return false;
        } finally {
            this.closeConnection();
        }

    }

    @Override
    public boolean notify(String email,String subject, String message) {
        return LogicFunctions.sendEmail(email,subject,message);
    }

    @Override
    public BookingRequest getPatientBookingRequest(int patient_id) {
        this.makeConnection();
        try {
            PreparedStatement stat = con.prepareStatement("SELECT * FROM booking_requests WHERE patient_id = ?");
            stat.setInt(1, patient_id);
            ResultSet res = stat.executeQuery();
            if (res.next()) {
                return new BookingRequest(
                        res.getInt("patient_id"),
                        res.getInt("gp_id"),
                        Arrays.asList((Timestamp[]) res.getArray("start_times").getArray()),
                        res.getTimestamp("request_time"),
                        res.getString("subject"),
                        res.getString("appt_details")

                );
            } else {
                return null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            this.closeConnection();
        }
        return null;
    }

    @Override
    public boolean makeConnection() {

        /**
         * Opens a connection to the com.database
         */

        // Older java requires Class.forName() to load the driver
        String url = dburl; // current
        Properties prop = new Properties();
        if (this.localDebug) {
            prop.setProperty("user", "lucas");
            prop.setProperty("password", "password");
            prop.setProperty("ssl", "false");
        }
        try {
            Class.forName("org.postgresql.Driver");
            this.con = this.localDebug ? DriverManager.getConnection(url, prop) : DriverManager.getConnection(url);

        } catch (Exception E) {
            System.out.println(E.getStackTrace());
            return false;
        }
        return true;
    }

    @Override
    public boolean closeConnection() {
        try {
            this.con.close();
            return this.con.isClosed();
        } catch (SQLException E) {
            System.out.println(E.getStackTrace());
            return false;
        }
    }

    @Override
    public boolean saveChanges(List<Storeable> changedRecords) {
        boolean status = true;
        for (Storeable record : changedRecords) {

            status = status && record.save(this);
        }
        return status;
    }

    @Override
    public Connection getConnection() {
        return this.con;
    }

    @Override
    public List<Appt> getAllAppts(int patientId) {
        if (!this.makeConnection()) {
            return null;
        }

        try {
            PreparedStatement apptQuery =
                    con.prepareStatement("SELECT * FROM appointments WHERE patient_id = ?");
            apptQuery.setInt(1, patientId);
            ResultSet appts = apptQuery.executeQuery();
            List ret = new LinkedList();
            while (appts.next()) {
                ret.add(this.getAppt(appts.getTimestamp("start_time"),appts.getInt("gp_id")));
            }
            return ret;
        } catch (SQLException E) {
            System.out.println(E.getStackTrace());
            return null;
        } finally {
            this.closeConnection();
        }
    }

    public static void main(String[] args) {
        DB db = new DBWrapper();

        db.setup();

        LogicFunctions lf = new LogicFunctions(db);
        LinkedList<Timestamp> dumlist = new LinkedList<Timestamp>(List.of(lf.getTimeStamp("\"2021/06/16 21:30\"")));
        /*
        BookingRequest dummy = new BookingRequest(3,111,dumlist,new Timestamp(0),"Different","Different");
        BookingRequest dummy2 = new BookingRequest(2,111,dumlist,new Timestamp(0),"Different","Different");
        dummy.save(db);
        dummy2.save(db);
        */
        db.makeConnection();/*


        // Objective statement:
        // SELECT * FROM booking_requests WHERE ? in start_times
        // AKA get all of the records where a specified time is in the start times for that record
        // AKA for each record, get the start times for that record, and when patient_id and gp_id are the same, use in
        */
        db.closeConnection();
    }

}

