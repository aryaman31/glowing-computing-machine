package com.gp_scheduling;

import com.database.Appt;
import com.database.DB;
import com.database.GP;
import com.database.Patient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RestApi {
    DB db = webServer.db;
    LogicFunctions logic = new LogicFunctions(db);

    @GetMapping("/api/test")
    public List<Test> test() {
        List<Test> a = new ArrayList<>();
        a.add(new Test("msg1 lool", 11));
        a.add(new Test("msg2 loooool", 123132));
        return a;
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

    @GetMapping("/api/bookingConfirmation")
    public boolean bookingConfirmation(@RequestParam(name = "patient_id") int patient_id,
                                   @RequestParam(name = "gp_id") int gp_id,
                                   @RequestParam(name = "start_time") String start_time,
                                   @RequestParam(name = "end_time") String end_time,
                                   @RequestParam(name = "subject") String subject) {
        System.out.println("START TIME IS " + start_time);
        return logic.bookAppt(new Appt(patient_id,gp_id,logic.getTimeStamp(start_time),
                        logic.getTimeStamp(end_time),subject,-1,false));
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


}

class Test {
    String msg;
    int i;

    public Test(String mString, int i) {
        this.msg = mString;
        this.i = i;
    }
}
