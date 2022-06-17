package com.gp_scheduling;

import com.database.Appt;
import com.database.DB;
import com.database.DBWrapper;
import com.database.Patient;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class LogicFunctions {

    DB db;

    public LogicFunctions(DB db) {
        this.db = db;
    }

    public boolean bookAppt(Appt appt) {
        if (noClashingAppts(appt)) {
            return appt.save(db);
        } else {
            return false;
        }
    }

    public boolean rescheduleAppt(Appt initAppt, Timestamp newTime,Timestamp newEnd) {

        boolean success = this.bookAppt(new Appt(initAppt.getPatient_id(),initAppt.getGp_id(),newTime,newEnd,
                initAppt.getSubject(),initAppt.getAppt_file(),false));
        if (success) {
            this.cancelAppt(initAppt);
        } else {
            return false;
        }
        return true;
    }

    public boolean cancelAppt(Appt appt) {
        if (appt.cancel(db)) {
            alertWaiters(appt);
            return true;
        } else {
            return false;
        }
    }

    public boolean noClashingAppts(Appt appt) {
        // Returns true if there is no appointment in the com.database with a matching gp and start time;
        return db.getAppt(appt.getStart_time(),appt.getGp_id()).getGp_id() == -1;
    }

    public boolean alertWaiters(Appt appt) {
        List<Appt> templates = db.getWaitList(appt.getStart_time(),appt.getGp_id());
        if (templates == null) {
            return true;
        }
        for (Appt template : templates) {
            notify(db.getPatient(template.getPatient_id()),template);
        }
        return true;
    }

    private void notify(Patient patient, Appt template) {
        // TODO
        int i = -1;
    }

    public Timestamp getTimeStamp(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("\"yyyy/MM/dd HH:mm\"");
        try {
            return new Timestamp(sdf.parse(dateTime).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // For testing
        DB db = new DBWrapper();
        db.setup();
        db.populate();
        LogicFunctions lf = new LogicFunctions(db);
        Appt soreThroat = new Appt(1, 111,
                lf.getTimeStamp("2022/06/16 21:00"), lf.getTimeStamp("2022/06/16 21:15"),
                "Sore Throat", -1, false);
        Appt rash = new Appt(2, 111,
                lf.getTimeStamp("2022/06/16 21:15"), lf.getTimeStamp("2022/06/16 21:30"),
                "Rash", -1, false);
        lf.bookAppt(soreThroat);
        lf.bookAppt(rash);

        lf.rescheduleAppt(soreThroat,lf.getTimeStamp("2023/06/16 21:30"),lf.getTimeStamp("2023/06/16 21:45"));
        lf.cancelAppt(rash);
        rash.setStart_time(lf.getTimeStamp("2023/06/16 21:30"));
        rash.setEnd_time(lf.getTimeStamp("2023/06/16 21:45"));
        lf.bookAppt(rash);

    }

}
