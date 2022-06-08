package com.database;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Practice {
    /** This class is the business logic core, coordinating between patients and GPs, and effectively actually
     *  performing the rescheduling. Might make static? Or singleton? Will also be entry point to the DB
     *  Practice DB has 3 collections:
     *      - GPs
     *      - Patients
     *      - Appointments
     *  so we can use objectIDs instead of DBRrefs
     *
     *  Notes :
     *    - Enforce Appointments at 15 minute intervals - duration field deprecated
     *    - Appointments can only be at multiples of 15 minutes
     *    - Do this in server side validation - don't want people to fuck things up and double book
     */


    // All the following functions are going to need to use some sort of session ID to verify that
    // the correct user is logged on

    List<GP> practitioners = new LinkedList<>();
    String practiceName;

    // Boilerplate
    MongoClient mongoClient;
    DB database; // Practice database
    DBCollection gps; // GPs collection
    DBCollection patients;
    DBCollection appts;
    {
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public Practice(String name) {
        this.practiceName = name;
        DB database = mongoClient.getDB(name); // Get or create database associated with this practice on initialisation
        this.gps = database.getCollection("GPs");
        this.patients = database.getCollection("Patients");
        this.appts = database.getCollection("Appointments"); // Collections start with Capitals


    }

    public boolean addGP(GP gpToAdd) {
        if (notPresent(gpToAdd.id, gps)) {
            gps.insert(gpToAdd.toDocument());
            return true;
        } else {
            return false;
        }
    }
    public boolean addPatient(Patient patientToAdd) {
        if (notPresent(patientToAdd.id, patients)) {
            patients.insert(patientToAdd.toDocument());
            return true;
        }
        return false;
    }

    public String getPracticeName() {
        return this.practiceName;
    }

    public boolean bookAppt(Appt appointment, Patient patient, GP gp,String verify) {
        // Appt should have already been formed, this verifies based on that information that the appointment
        // is free, and then saves and returns true if succesful
        if (UserClient.verifySession(patient,verify) &&
                notPresent(Appt.formId(appointment.dateTime,gp.id),appts)) {
            appts.insert(appointment.toDocument());
            patient.addApt(appointment);
            patients.update(new BasicDBObject("_id",patient.id),new BasicDBObject("$set",patient.toDocument()));
            return true;
        }

        return false;
    }

    public Patient startPatientSession(String id,String password) {
        // Returns null on login failure
        Patient userAttempt = Patient.getPatientById(id,this);
        if (UserClient.hasher(password,userAttempt.salt).equals(userAttempt.password)) {
            userAttempt.generateToken();
            return userAttempt;
        }
        return null;
    }

    public boolean cancelAppt(String apptId, Patient patient) {
        if (!notPresent(apptId,appts)) {
            appts.remove(new BasicDBObject("_id",apptId));
            patient.rmvApt(apptId);
            return true;
        }
        return false;
    }

    public boolean rescheduleAppt(String apptId,Date apptTime,GP gp) {
        /* TODO
           Will extract the relevant information out of the old appointment and add it into the new one at the new time,
           if the new appointment is free
        */
        return true;
    }

    public Appt viewAppt(String apptId) {
        //TODO
        // Will presumably return a json with the appointment information in it for rendering by the client
        return null;
    }

    public static boolean notPresent(String id, DBCollection collection) {
        return collection.find(new BasicDBObject("_id",id)).size()==0;
    }

}
