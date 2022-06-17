package com.database;

import java.sql.*;
import java.util.*;

public class DBWrapper implements DB {

    Connection con = null;
    String dburl = System.getenv("DATABASE_URL");

    @Override
    public boolean setup() {
        /** Opens a connection to the com.database and populates it with tables if they do not already exist.
         */

        // Older java requires Class.forName() to load the driver
        String url = dburl; // current
        // db is contained in a url - identified by : jdbc::postgresql//host:post/com.database
        Properties prop = new Properties();
        prop.setProperty("user", "postgres");
        prop.setProperty("password", "123");
        prop.setProperty("ssl", "false");
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, prop);

            String createPatients =
                    "CREATE TABLE IF NOT EXISTS patients (" +
                            "patient_id bigint CONSTRAINT patient_key PRIMARY KEY," +
                            "first_name varchar(20)," +
                            "surname varchar(20)," +
                            "clinic_id int," +
                            "hospital_id int," +
                            "salted varchar(66)," +
                            "salt varchar(66)" +
                            ")";
            String createGPs =
                    "CREATE TABLE IF NOT EXISTS gps (" +
                            "gp_id bigint CONSTRAINT gp_key PRIMARY KEY," + // Employee number append clinic number append hospital number
                            "salted varchar(66)," +
                            "salt varchar(66)," +
                            "first_name varchar(20)," +
                            "surname varchar(20)" +
                            ")";

            String createRecords =
                    "CREATE TABLE IF NOT EXISTS appointments (" +
                            "patient_id bigint," +
                            "gp_id bigint," +
                            "start_time timestamp," +
                            "end_time timestamp," +
                            "subject varchar(50)," +
                            "appt_file bigint," + // Files numerically produced representing appt, path should be known before
                            "cancelled boolean," +
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

            PreparedStatement createPatientsS = con.prepareStatement(createPatients);
            PreparedStatement createGPsS = con.prepareStatement(createGPs);
            PreparedStatement createRecordsS = con.prepareStatement(createRecords);
            PreparedStatement createWaitListS = con.prepareStatement(createWaitList);
            createPatientsS.execute();
            createPatientsS.close();

            createGPsS.execute();
            createGPsS.close();

            createRecordsS.execute();
            createRecordsS.close();

            createWaitListS.execute();
            createWaitListS.close();

            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("DATABSE NOT CONNECTED");
            e.printStackTrace();
            return false;
        }

        return true;

    }

    @Override
    public void populate() {
        try {
            String insertString =  "INSERT INTO patients (patient_id, first_name, surname, clinic_id, hospital_id, salted, salt) " +
                    "VALUES (?, ?,  ?, ?, ?, ?, ?)";
            PreparedStatement insertPatient = con.prepareStatement(insertString);
            insertPatient.setInt(1,1);
            insertPatient.setString(2,"Alice");
            insertPatient.setString(3,"Smith");
            insertPatient.setInt(4,1);
            insertPatient.setInt(5,1);
            insertPatient.setString(6,"");
            insertPatient.setString(7,"");
            insertPatient.executeUpdate();
            insertPatient.setInt(1,2);
            insertPatient.setString(2,"Bob");
            insertPatient.setString(3,"Jones");
            insertPatient.executeUpdate();
            insertPatient.setInt(1,3);
            insertPatient.setString(2,"Charlie");
            insertPatient.setString(3,"Skinner");
            insertPatient.executeUpdate();
            insertString =  "INSERT INTO gps (gp_id, first_name, surname, salted, salt) " +
                    "VALUES (?, ?,  ?, ?, ?)";
            PreparedStatement insertGP = con.prepareStatement(insertString);
            insertGP.setInt(1,111);
            insertGP.setString(2,"Thomas");
            insertGP.setString(3,"Jackson");
            insertGP.setString(4,"");
            insertGP.setString(5,"");
            insertGP.executeUpdate();
            this.closeConnection();

        } catch (SQLException e) {
            System.out.println("DATABSE COULDNT POPULATE ANYTHING");
            System.out.println(Arrays.toString(e.getStackTrace()));
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
                            patientResult.getString("salt")
                    );
            return patient;
        } catch (SQLException E) {
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
                            gpResult.getString("salt")
                    );
            return gp;
        } catch (SQLException E) {
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
                return new Appt(-1, -1, new Timestamp(0), new Timestamp(0), "", -1, false);
            }
            Appt appt =
                    new Appt(
                            apptResult.getInt("patient_id"),
                            apptResult.getInt("gp_id"),
                            apptResult.getTimestamp("start_time"),
                            apptResult.getTimestamp("end_time"),
                            apptResult.getString("subject"),
                            apptResult.getInt("appt_file"),
                            apptResult.getBoolean("cancelled")

                    );
            appt.db = this;
            return appt;
        } catch (SQLException E) {
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
                    con.prepareStatement("SELECT * FROM appointments WHERE gp_id = ? AND ? < start_time AND ? >= end_time");
            apptQuery.setInt(1, gpId);
            apptQuery.setTimestamp(2, current_time);
            apptQuery.setTimestamp(3, start_time);
            ResultSet gpAppts = apptQuery.executeQuery();
            if (!gpAppts.next()) {
                return 0;
            }
            gpAppts.last();    // moves cursor to the last row
            int size = gpAppts.getRow(); // get row id
            return size;
        } catch (SQLException E) {
            return -1;
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
                    "SELECT * FROM appointments WHERE ? = ANY(start_times) AND gp_id = ?");
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
                                Appt.getEndTimeOnAppt(time),
                                wlResult.getString("subject"),
                                -1,
                                false

                        );
            }
            return wl;
        } catch (SQLException E) {
            return null;
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public boolean makeConnection() {
        /**
         * Opens a connection to the com.database and populates it with tables if they do not already exist.
         */

        // Older java requires Class.forName() to load the driver
        String url = dburl; // current
        // db is contained in a url - identified by : jdbc::postgresql//host:post/com.database
        Properties prop = new Properties();
        prop.setProperty("user", "postgres");
        prop.setProperty("password", "123");
        prop.setProperty("ssl", "false");
        try {
            Class.forName("org.postgresql.Driver");
            this.con = DriverManager.getConnection(url, prop);

        } catch (Exception E) {
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
            return null;
        } finally {
            this.closeConnection();
        }
    }

    public static void main(String[] args) {
        DB db = new DBWrapper();

        db.setup();
    }

}

