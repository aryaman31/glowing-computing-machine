package com.database;

import com.mongodb.DBObject;

public interface Storeable {

    DBObject toDocument();

    String getId();

}
