package com.gp_scheduling;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import com.database.Appt;
import com.database.DB;
import com.database.GP;
import com.database.Patient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApi {
    DB db = webServer.db;
    LogicFunctions logic = new LogicFunctions(db);

    @GetMapping("/waitlist")
    public List<Appt> gWList(@RequestParam(name = "time") Timestamp time, @RequestParam(name = "id") int id) {
        return db.getWaitList(time, id);
    }

    @GetMapping("/patient")
    public Patient gPatient(@RequestParam(name = "id") Integer id) {
        return db.getPatient(id);
    }

    @GetMapping("/gp")
    public GP ggp(@RequestParam(name = "id") Integer id) {
        return db.getGP(id);
    }

    @GetMapping("/appt")
    public Appt getappt(@RequestParam(name = "startTime") Timestamp start, @RequestParam(name = "id") Integer id) {
        return db.getAppt(start, id);
    }

    @GetMapping("/numGpAppt")
    public int getNumGPAppointments(@RequestParam(name = "id") int id, @RequestParam(name = "currTime") Timestamp currTimestamp,
                                    @RequestParam(name = "startTime") Timestamp sTimestamp) {
        return db.getNumGPAppointments(id, currTimestamp, sTimestamp);                               
    }

    @GetMapping("/bookingConfirmation")
    public boolean bookingConfirmation(@RequestParam(name = "patient_id") int patient_id,
                                   @RequestParam(name = "gp_id") int gp_id,
                                   @RequestParam(name = "start_time") Timestamp start_time,
                                   @RequestParam(name = "end_time") Timestamp end_time,
                                   @RequestParam(name = "subject") String subject) {

        return logic.bookAppt(new Appt(patient_id,gp_id,start_time,end_time,subject,-1,false));
    }

    @GetMapping("/reschedule")
    public boolean reschedule(@RequestParam(name = "original_start") Timestamp original_start,
                                       @RequestParam(name = "gp_id") int gp_id,
                                       @RequestParam(name = "new_start") Timestamp new_start,
                                       @RequestParam(name = "new_end") Timestamp new_end)
    {

        Appt initial = db.getAppt(original_start,gp_id);

        return logic.rescheduleAppt(initial,new_start,new_end);

    }

    @GetMapping("/cancelAppt")
    public boolean cancelAppt(@RequestParam(name = "original_start") Timestamp original_start,
                              @RequestParam(name = "gp_id") int gp_id,
                              @RequestParam(name = "new_start") Timestamp new_start,
                              @RequestParam(name = "new_end") Timestamp new_end)
    {
        Appt initial = db.getAppt(original_start,gp_id);
        return logic.cancelAppt(initial);

    }


}
