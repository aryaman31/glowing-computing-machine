package com.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import javax.swing.event.DocumentEvent;

public class User implements Storeable {
    final String firstNames;
    final String surname;
    final String id;
    String salt;
    String password;
    Practice practice;
    private String token;

    public User(String firstNames, String surname, String id,Practice practice) {
        this.firstNames = firstNames;
        this.surname = surname;
        this.id = id;
        this.practice = practice;
    }

    public void setPassword(String hashedPass, String salt) {
        this.salt = salt;
        this.password = hashedPass;
    }

    public BasicDBObject toDocument() {
        BasicDBObject docToReturn = new BasicDBObject("_id",id);
        docToReturn.append("firstNames",firstNames);
        docToReturn.append("surname",surname);
        docToReturn.append("salt",salt);
        docToReturn.append("password",password);
        return docToReturn;
    }

    public static void populateUserFromDocument(DBObject doc, User user) {

        user.salt = (String) doc.get("salt");
        user.password = (String) doc.get("password");

    }

    public String getId() {
        return this.id;
    }

    public void generateToken() {
        //TODO : Generate token for sessions
        this.token = "000";
    }
}

// Make adaptor for all subclasses s.t one can make Mongo documents from the classes and vice versa
