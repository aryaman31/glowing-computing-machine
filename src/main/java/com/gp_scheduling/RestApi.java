package com.gp_scheduling;

import com.database.Appt;
import com.database.DB;
import com.database.GP;
import com.database.Patient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

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
    public Appt getappt(@RequestParam(name = "startTime") String start, @RequestParam(name = "id") Integer id) {
        return db.getAppt(logic.getTimeStamp(start), id);
    }

    @GetMapping("/numGpAppt")
    public int getNumGPAppointments(@RequestParam(name = "id") int id, @RequestParam(name = "currTime") String currTimestamp,
                                    @RequestParam(name = "startTime") String sTimestamp) {
        return db.getNumGPAppointments(id, logic.getTimeStamp(currTimestamp), logic.getTimeStamp(sTimestamp));
    }

    @GetMapping("/bookingConfirmation")
    public boolean bookingConfirmation(@RequestParam(name = "patient_id") int patient_id,
                                   @RequestParam(name = "gp_id") int gp_id,
                                   @RequestParam(name = "start_time") String start_time,
                                   @RequestParam(name = "end_time") String end_time,
                                   @RequestParam(name = "subject") String subject) {

        return logic.bookAppt(new Appt(patient_id,gp_id,logic.getTimeStamp(start_time),
                        logic.getTimeStamp(end_time),subject,-1,false));
    }

    @GetMapping("/reschedule")
    public boolean reschedule(@RequestParam(name = "original_start") String original_start,
                                       @RequestParam(name = "gp_id") int gp_id,
                                       @RequestParam(name = "new_start") String new_start,
                                       @RequestParam(name = "new_end") String new_end)
    {

        Appt initial = db.getAppt(logic.getTimeStamp(original_start),gp_id);

        return logic.rescheduleAppt(initial,logic.getTimeStamp(new_start),logic.getTimeStamp(new_end));

    }

    @GetMapping("/cancelAppt")
    public boolean cancelAppt(@RequestParam(name = "original_start") String original_start,
                              @RequestParam(name = "gp_id") int gp_id,
                              @RequestParam(name = "new_start") String new_start,
                              @RequestParam(name = "new_end") String new_end)
    {
        Appt initial = db.getAppt(logic.getTimeStamp(original_start),gp_id);
        return logic.cancelAppt(initial);

    }


}
