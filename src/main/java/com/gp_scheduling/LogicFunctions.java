package com.gp_scheduling;

import com.database.*;

import java.awt.print.Book;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class LogicFunctions {

    DB db;

    public LogicFunctions(DB db) {
        this.db = db;
    }

    public static int slot_length = 15 * 60 * 1000;
    public static Timestamp getEndTime(Timestamp start_time) {
        return new Timestamp(start_time.getTime()+slot_length);
    }

    public boolean bookAppt(Appt appt) {

        /** Role :
         *  - Checks no clashing bookings                           x
         *  - Removes a booking request row                         x
         *  - Removes booking request timeslots from other rows     x
         *  - Deletes any now empty booking requests                x
         *      - notifies those users                              x
        */

        if (noClashingAppts(appt)) {
            db.adjustRequestsTable(appt); // Implements lower 3
            GP gp = db.getGP(appt.getGp_id());
            db.notify(db.getPatient(appt.getPatient_id()).getEmail(),
                    "You have been booked in for an appointment with Dr. "+
                            gp.getFirst_name() + " "+gp.getSurname()+ " at "+appt.getStart_time().toString()+".");
            return appt.save(db);
        } else {
            return false;
        }
    }

    public boolean requestAppt(BookingRequest request) {
        return request.save(this.db);
    }

    public boolean rescheduleAppt(Appt initAppt, Timestamp newTime,Timestamp newEnd) {

        boolean success = this.bookAppt(new Appt(initAppt.getPatient_id(),initAppt.getGp_id(),newTime,newEnd,
                initAppt.getSubject(),initAppt.getAppt_details(),false));
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
        System.out.println("Notified Patient "+Integer.toString(patient.getPatient_id()));
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
        BookingRequest soreThroat = new BookingRequest(1, 111,
                List.of(lf.getTimeStamp("\"2022/06/16 21:00\"")), lf.getTimeStamp("\"2022/05/16 21:15\""),
                "Sore Throat", "Persistent sore throat");
        BookingRequest rash = new BookingRequest(2, 111,
                List.of(lf.getTimeStamp("\"2022/06/16 21:00\"\"2022/06/16 21:00\"")), lf.getTimeStamp("\"2022/05/16 21:30\""),
                "Rash", "Rash covering entire body");

        lf.requestAppt(soreThroat);
        lf.requestAppt(rash);

        lf.bookAppt(new Appt(soreThroat,lf.getTimeStamp("\"2022/06/16 21:00\"\"2022/06/16 21:00\"")));

    }

}
