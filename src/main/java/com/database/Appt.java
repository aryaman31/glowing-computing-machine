package com.database;

import java.sql.Timestamp;

public class Appt {
    private int patient_id;
    private int gp_id;
    private Timestamp start_time;
    private Timestamp end_time;
    private String subject;
    private int appt_file;
    private boolean cancelled;

    public Appt(int patient_id, int gp_id, Timestamp start_time, Timestamp end_time, String subject, int appt_file, boolean cancelled) {
        this.patient_id = patient_id;
        this.gp_id = gp_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.subject = subject;
        this.appt_file = appt_file;
        this.cancelled = cancelled;
    }

    public static Timestamp getEndTimeOnAppt(Timestamp start_time) {
        // TODO
        return null;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public void setGp_id(int gp_id) {
        this.gp_id = gp_id;
    }

    public void setStart_time(Timestamp start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(Timestamp end_time) {
        this.end_time = end_time;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAppt_file(int appt_file) {
        this.appt_file = appt_file;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public int getGp_id() {
        return gp_id;
    }

    public Timestamp getStart_time() {
        return start_time;
    }

    public Timestamp getEnd_time() {
        return end_time;
    }

    public String getSubject() {
        return subject;
    }

    public int getAppt_file() {
        return appt_file;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean cancel() {
        // TODO
        return false;
    }
}
