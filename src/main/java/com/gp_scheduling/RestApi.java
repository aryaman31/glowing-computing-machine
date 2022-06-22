package com.gp_scheduling;

import com.database.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestApi {
    DB db = webServer.db;
    LogicFunctions logic = new LogicFunctions(db);

    @GetMapping("/api/test")
    public String test() {
        return "TEST";
    }

    @GetMapping("/api/waitlist")
    public List<Appt> gWList(@RequestParam(name = "time") Timestamp time, @RequestParam(name = "id") int id) {
        return db.getWaitList(time, id);
    }

    @GetMapping("/api/patient")
    public Patient gPatient(@RequestParam(name = "id") int id) {
        return db.getPatient(id);
    }

    @GetMapping("/api/gp")
    public GP ggp(@RequestParam(name = "id") int id) {
        return db.getGP(id);
    }

    @GetMapping("/api/appt")
    public Appt getappt(@RequestParam(name = "startTime") String start, @RequestParam(name = "id") int id) {
        return db.getAppt(logic.getTimeStamp(start), id);
    }

    @GetMapping("/api/getAppts")
    public List<Appt> getAppts(@RequestParam int id) {
        return db.getAllAppts(id);
    }

    @GetMapping("/api/numGpAppt")
    public int getNumGPAppointments(@RequestParam(name = "id") int id, @RequestParam(name = "currTime") String currTimestamp,
                                    @RequestParam(name = "startTime") String sTimestamp) {
        return db.getNumGPAppointments(id, logic.getTimeStamp(currTimestamp), logic.getTimeStamp(sTimestamp));
    }

    @GetMapping("/api/requestBooking") // Rename to request booking
    public boolean requestBooking(@RequestParam(name = "patient_id") int patient_id,
                                   @RequestParam(name = "gp_id") int gp_id,
                                   @RequestParam(name = "start_times") String[] start_times,
                                   @RequestParam(name = "subject") String subject,
                                   @RequestParam(name = "appt_details") String appt_details) {
         return logic.requestAppt(new BookingRequest(patient_id,gp_id,
                 Arrays.stream(start_times).map(x -> logic.getTimeStamp(x)).collect(Collectors.toList()),
                 new Timestamp(System.currentTimeMillis()),subject,appt_details));
    }

    @GetMapping("/api/reschedule")
    public boolean reschedule(@RequestParam(name = "original_start") String original_start,
                                       @RequestParam(name = "gp_id") int gp_id,
                                       @RequestParam(name = "new_start") String new_start,
                                       @RequestParam(name = "new_end") String new_end)
    {

        Appt initial = db.getAppt(logic.getTimeStamp(original_start),gp_id);

        return logic.rescheduleAppt(initial,logic.getTimeStamp(new_start),logic.getTimeStamp(new_end));

    }

    @GetMapping("/api/cancelAppt")
    public boolean cancelAppt(@RequestParam(name = "original_start") String original_start,
                              @RequestParam(name = "gp_id") int gp_id,
                              @RequestParam(name = "new_start") String new_start,
                              @RequestParam(name = "new_end") String new_end)
    {
        Appt initial = db.getAppt(logic.getTimeStamp(original_start),gp_id);
        return logic.cancelAppt(initial);

    }

    @GetMapping("/api/bookingRequests")
    public List<Appt> bookingRequests(@RequestParam(name = "start_time") String start_time,
                                   @RequestParam(name = "gp_id") int gp_id)
    {
        return db.getBookingRequests(logic.getTimeStamp(start_time),gp_id);
    }

    @GetMapping("/api/adminSetBooking")
    public boolean bookingConfirmation(@RequestParam(name = "patient_id") int patient_id,
                                       @RequestParam(name = "gp_id") int gp_id,
                                       @RequestParam(name = "start_time") String start_time,
                                       @RequestParam(name = "end_time") String end_time,
                                       @RequestParam(name = "subject") String subject,
                                       @RequestParam(name = "appt_details") String appt_details) {

        return logic.bookAppt(new Appt(patient_id,gp_id,logic.getTimeStamp(start_time),
                logic.getTimeStamp(end_time),subject,appt_details,false));
    }

    @GetMapping("/api/markAppointmentComplete")
    public boolean markAppointment(@RequestParam(name = "gp_id") int gp_id,
                                   @RequestParam(name = "start_time") String start_time)
    {
        return db.markAppointment(gp_id,logic.getTimeStamp(start_time));
    }


}

class Test {
    String msg;
    int i;

    public Test(String mString, int i) {
        this.msg = mString;
        this.i = i;
    }
}
