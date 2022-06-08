package com.database;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class UserClient {

    List<User> users = new LinkedList<>();

    public static String hasher(String password, String salt) {
        //TODO;
        //return sha256(password + salt ) - Need to import libraries
        return null;
    }

    public static boolean verifySession(User user, String client) {
        //TODO
        return true;
    }

    // Need to find out how to do session verification in order to gate off actions based off privileges

    public static User getCurrentUser() {
        // TODO: Session Verification
        // Assume this works and returns the current user
        return null;
    }

    public static void main(String[] args) {

        Practice practice = new Practice("MadeUpPractice");
        GP thomas = new GP("Thomas","Smith","GP1",practice);
        practice.addGP(thomas);
        Patient ali = new Patient("Ali","Xavier","01",practice,thomas);
        practice.addPatient(ali);
        thomas = GP.getGpById("GP1",practice);
        ali = Patient.getPatientById("01",practice);

        ali.generateToken();

        Appt fever = new Appt(
                new Date(2022, 8, 8, 9, 15, 0),
                ali.id,thomas.id,practice
        );
        fever.duration = 15;
        fever.subject = "fever";
        fever.issue = "mild fever";
        fever.notes = "";
        fever.completed = false;
        fever.viewerIds = new LinkedList<String>();
        fever.viewerIds.add(ali.id);
        fever.viewerIds.add(thomas.id);

        Patient alison = new Patient("Alison","Singh","02",practice,thomas);
        alison.generateToken();
        practice.addPatient(alison);
        alison = Patient.getPatientById("02",practice);
        Appt earache = new Appt(
                new Date(2023, 8, 8, 9, 15, 0),
                ali.id,thomas.id,practice
        );
        earache.subject = "earache";
        earache.issue = "earache after swimming";
        earache.notes = "";
        earache.completed = false;
        earache.viewerIds = new LinkedList<String>();
        earache.viewerIds.add(alison.id);
        earache.viewerIds.add(thomas.id);
        practice.bookAppt(fever,ali,thomas,"000");
        boolean result = practice.bookAppt(earache, alison,thomas,"000");
        System.out.println(result);
        System.out.println(ali.appointments);
        System.out.println(alison.appointments);
        practice.cancelAppt(fever.id,ali);
        System.out.println(ali.appointments);

    }



}
