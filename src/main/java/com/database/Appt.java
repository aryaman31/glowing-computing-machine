package com.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.Date;
import java.util.List;

public class Appt implements Storeable {

    final String id;
    final Date dateTime;
    String patientId; // Form patient object as needed
    String gpId; // Form gp object as needed
    List<String> viewerIds; // List of people with view permissions on this record (by ID)
    int duration = 0; // EST when booking, after completion updated to be accurate
    String issue = "";
    String subject = "";
    String description = "";
    String notes = "";
    boolean completed = false;
    Practice practice;

    public Appt(Date dateTime, String patientId, String gpId, Practice practice) {
        // Could refactor a factory out of here
        this.dateTime = dateTime;
        this.patientId = patientId;
        this.gpId = gpId;
        // At its base, an appointment is defined as a meeting between a GP and a Patient at a time
        // If any of these are changed, it ceases to be the same appointment
        this.completed = false;
        this.id = this.dateTime.toString() +"-"+ this.gpId;
        this.practice = practice;
    }

    public static String formId(Date dateTime, String gpId) {
        return dateTime.toString() + "-" + gpId;
    }

    public BasicDBObject toDocument() {
        BasicDBObject document = new BasicDBObject("_id",id);
        document.append("appointmentTime",dateTime);
        document.append("duration",duration);
        document.append("gp",gpId);
        document.append("patient",patientId);
        document.append("viewers",viewerIds);
        document.append("subject",subject);
        document.append("issue",issue);
        document.append("notes",notes);
        document.append("completed",completed);
        return document;
    }

    public static Appt getApptById(String appId, Practice practice) {
        //TODO: Retrieve Appt data from database using query and then populate an object to process

        DBCursor retrieved = practice.appts.find(new BasicDBObject("_id",appId));
        DBObject document = retrieved.one();
        Appt toRet = new Appt((Date) document.get("appointmentTime"),(String)document.get("patient"),(String) document.get("gp"),practice);
        toRet.duration = (int) document.get("duration");
        toRet.subject = (String) document.get("subject");
        toRet.issue = (String) document.get("issue");
        toRet.notes = (String) document.get("notes");
        toRet.viewerIds = (List<String>) document.get("viewers");
        toRet.completed = (boolean) document.get("completed");

        return toRet;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Appt)) {
            return false;
        }

        Appt other = (Appt) o;
        if (other.id.equals(this.id)) {
            return true;
        } else {
            return false;
        }
    }



    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    public String getId() {
        return this.id;
    }
}

// Make mongoDB document adaptor