package com.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.List;

public class GP extends User{
    public GP(String firstNames,String surname,String id,Practice practice) {
        super(firstNames, surname,id,practice);
    }

    public static GP getGpById(String id, Practice practice) {
        DBCursor retrieved = practice.gps.find(new BasicDBObject("_id",id));
        // Should really only have 1 - IDs are unique and if they aren't we've fucked up
        DBObject document = retrieved.one(); // Add error checking here
        GP objToReturn = new GP(
                (String) document.get("firstNames"),
                (String) document.get("surname"),
                (String) document.get("_id"),
                practice); // We can guarantee these casts are safe because we have been adding to the database
        User.populateUserFromDocument(document,objToReturn);
        return objToReturn;
    }

    @Override
    public BasicDBObject toDocument() {
        // No extra fields required as of yet
        return super.toDocument();
    }


}

/**
 *   final String id;
 *     final LocalDateTime dateTime;
 *     final Patient patient; // This is the owner and should have control over the data
 *     final GP gp;
 *     List<User> viewers; // List of people with view permissions on this record
 *     int duration = 0; // EST when booking, after completion updated to be accurate
 *     String issue = "";
 *     String subject = "";
 *     String description = "";
 *     String notes = "";
 *     boolean completed;
 */