package com.database;

import com.mongodb.*;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Patient extends User{
    List<Appt> appointments = new LinkedList<>();
    GP currentGP;

    public Patient(String FirstNames, String Surname,String id, Practice practice,GP currentGP) {
        super(FirstNames,Surname,id,practice);
        this.currentGP = currentGP;
    }

    // Setters will need to update db, depends on whether we use documents or ood
    public void setGP(GP gp) {
        this.currentGP = gp;
    }
    public GP getCurrentGP() {
        return this.currentGP;
    }

    @Override
    public BasicDBObject toDocument() {
        BasicDBObject patientDoc = super.toDocument(); // Cast safe because we have control over User superclass
        patientDoc.append("currentGP",currentGP.id); // Object reference by id
        patientDoc.append("appointments",appointments.stream().map(Appt::getId).toArray());
        return patientDoc;
    }


    public static Patient getPatientById(String id,Practice practice) {
        DBCursor retrieved = practice.patients.find(new BasicDBObject("_id",id));
        // Should really only have 1 - IDs are unique and if they aren't we've fucked up
        DBObject document = retrieved.one(); // Add error checking here
        Patient objToReturn = new Patient(
                (String) document.get("firstNames"),
                (String) document.get("surname"),
                (String) document.get("_id"),
                practice,
                GP.getGpById((String) document.get("currentGP"),practice)
        ); // We can guarantee these casts are safe because we have been adding to the database
        List<Appt> ApptIdsdocument =
                ((List<String>) document.get("appointments")).stream().map(x -> Appt.getApptById(x,practice)).toList();
        User.populateUserFromDocument(document,objToReturn);
        return objToReturn;
    }




    // Actual functionality
    public void addApt(Appt appointment) {
        // will need to update db but for now done on object
        // booking not made here, enforced through "practice" bookAppt - Attempt to reduce concurrency issues
        this.appointments.add(appointment); // May want to make this so that it inserts ordered but not important for now
    }

    public void rmvApt(Appt appointment) {
        this.appointments.remove(appointment);
    }

    public boolean rmvApt(String apptId) {
        for (Appt i : appointments) {
            if (i.id == apptId) {
                appointments.remove(i);
                return true;
            }
        }
        return false;
    }

    // Reschedule consists of an add and a rmv

    // view appointment dependent on retrieving from database

}
