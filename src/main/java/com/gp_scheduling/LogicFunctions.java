package com.gp_scheduling;

import com.database.Appt;
import com.database.DB;
import com.database.Patient;

import java.sql.Timestamp;
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
        for (Appt template : templates) {
            notify(db.getPatient(template.getPatient_id()),template);
        }
        return true;
    }

    private void notify(Patient patient, Appt template) {
        // TODO
        int i = -1;
    }


}
