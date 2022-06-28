package com.database;

import net.minidev.json.JSONObject;

public interface Storeable {

    public boolean save(DB db);

    //public JSONObject toJSON();

}
