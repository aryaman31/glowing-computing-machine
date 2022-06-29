package com.gp_scheduling;

import com.database.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
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
        /**
         * Gets the waitlist from the database given a time slot and a gp ID
         */
        return db.getWaitList(time, id);
    }

    @GetMapping("/api/patient")
    public Patient gPatient(@RequestParam(name = "id") int id) {
        /**
         * gets patient data given their ID
         */
        return db.getPatient(id);
    }

    @GetMapping("/api/patientId")
    public Integer gpatientId(@RequestParam(name = "name") String name) {
        return db.getPatientId(name);
    } 

    @GetMapping("/api/gp")
    public GP ggp(@RequestParam(name = "id") int id) {
        /**
         * Returns GP data given their ID
         */
        return db.getGP(id);
    }

    @GetMapping("/api/allgps")
    public List<GP> allgps() {
        return db.getAllGps();
    }

    @GetMapping("/api/appt")
    public Appt getappt(@RequestParam(name = "startTime") String start, @RequestParam(name = "id") int id) {
        /**
         * Returns an appointment given a time slot and a gp id
         */
        return db.getAppt(logic.getTimeStamp(start), id);
    }

    @GetMapping("/api/getAppts")
    public List<Appt> getAppts(@RequestParam(name = "id") int id) {
        /**
         * Gets a list of all appointments for a particular GP, given a gps id
         */
        return db.getAllAppts(id);
    }

    @GetMapping("/api/numGpAppt")
    public int getNumGpAppt(@RequestParam(name = "id") int id, @RequestParam(name = "currTime") String currTimestamp,
                                    @RequestParam(name = "startTime") String sTimestamp) {
        /**
         * Gets the number of uncompleted appointments for a given gp (provided by id in "id") until a
         * particular start time "start time"
         */
        return db.getNumGPAppointments(id, logic.getTimeStamp(currTimestamp), logic.getTimeStamp(sTimestamp));
    }

    @GetMapping("/api/requestBooking") // Rename to request booking
    public boolean requestBooking(@RequestParam(name = "patient_id") int patient_id,
                                   @RequestParam(name = "gp_id") int gp_id,
                                   @RequestParam(name = "start_times") String[] start_times,
                                   @RequestParam(name = "subject") String subject,
                                   @RequestParam(name = "appt_details") String appt_details) {
        /**
         * patient_id: ID of the patient requesting the booking
         * gp_id: ID of the gp that the patient is booking for
         * start_times: A list of Strings representing the time slots the patient is willing to accept
         * subject: a short string representing the subject of the appointment
         * appt_details: Field for long form text details of the appointment (type etc.)
         * Adds a booking to the request table, that can be provided to an administrator page
         */
         return logic.requestAppt(new BookingRequest(patient_id,gp_id,
                 Arrays.stream(start_times).map(x -> logic.getTimeStamp(x)).collect(Collectors.toList()),
                 new Timestamp(System.currentTimeMillis()),subject,appt_details));
    }

    @GetMapping("/api/reschedule")
    public boolean reschedule(@RequestParam(name = "original_start") String original_start,
                                       @RequestParam(name = "gp_id") int gp_id,
                                       @RequestParam(name = "new_start") String new_start)
    {
        /**
         * Should only be available to administrators
         * Takes in the original start time, the gp it's with, and a new start time and cancels the original booking while making a new one
         */
        Appt initial = db.getAppt(logic.getTimeStamp(original_start),gp_id);

        return logic.rescheduleAppt(initial,logic.getTimeStamp(new_start),LogicFunctions.getEndTime(logic.getTimeStamp(new_start)));

    }

    @GetMapping("/api/cancelAppt")
    public boolean cancelAppt(@RequestParam(name = "original_start") String original_start,
                              @RequestParam(name = "gp_id") int gp_id,
                              @RequestParam(name = "new_start") String new_start)
    {
        /**
         * cancels appointment, identifying the appointment using the original start time and gp id
         */
        Appt initial = db.getAppt(logic.getTimeStamp(original_start),gp_id);
        return logic.cancelAppt(initial);

    }

    @GetMapping("/api/bookingRequests")
    public List<Appt> bookingRequests(@RequestParam(name = "start_time") String start_time,
                                   @RequestParam(name = "gp_id") int gp_id)
    {
        /**
         * Given a slot time (start_time) and a gp_id returns all requested appointments
         * For that slot for that gp
         */
        return db.getBookingRequests(logic.getTimeStamp(start_time),gp_id);
    }

    @GetMapping("/api/adminSetBooking")
    public boolean bookingConfirmation(@RequestParam(name = "patient_id") int patient_id,
                                       @RequestParam(name = "gp_id") int gp_id,
                                       @RequestParam(name = "start_time") String start_time,
                                       @RequestParam(name = "end_time") String end_time,
                                       @RequestParam(name = "subject") String subject,
                                       @RequestParam(name = "appt_details") String appt_details) {
        /**
         * Function to call when an admin decides which booking to actually put through
         */
        return logic.bookAppt(new Appt(patient_id,gp_id,logic.getTimeStamp(start_time),
                LogicFunctions.getEndTime(logic.getTimeStamp(start_time)),subject,appt_details,false));
    }

    @GetMapping("/api/markAppointmentComplete")
    public boolean markAppointment(@RequestParam(name = "gp_id") int gp_id,
                                   @RequestParam(name = "start_time") String start_time,
                                   @RequestParam(name = "notes") String notes)
    {
        /**
         * Function to call when a booking has been marked complete. Adds notes to the database appointment record
         */
        return db.markAppointment(gp_id,logic.getTimeStamp(start_time),notes);
    }

    @GetMapping("/api/email")
    public void sendEmail(@RequestParam(name = "to") String to, 
                          @RequestParam(name = "subject") String subject,
                          @RequestParam(name = "msg") String msg) {
        LogicFunctions.sendEmail(to, subject, msg);
    }

    @GetMapping("/api/getAppointmentsForPatient")
    public List<Appt> getAppointmentsForPatient(@RequestParam(name = "patient_id") int patient) {
        List<Appt> appts =  db.getAllAppts(patient);
        return appts.stream().filter(appt -> !appt.isCompleted()).toList();
    }

    @GetMapping("/api/getRequestedAppointment")
    public BookingRequest getRequestedAppointment(@RequestParam(name = "patient_id") int patient) {
        BookingRequest appt = db.getPatientBookingRequest(patient);
        return appt;
    }

    @GetMapping("/api/getPastAppointmentsForPatient")
    public List<Appt> getPastApptsForPatient(@RequestParam(name = "patient_id") int patient) {
        List<Appt> appts = db.getAllAppts(patient);
        return appts.stream().filter(appt->appt.isCompleted()).toList();
    }
}
