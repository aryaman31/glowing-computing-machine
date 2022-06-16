package com.gp_scheduling;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.database.Appt;
import com.database.DB;
import com.database.GP;
import com.database.Patient;

@RestController
public class RestApi {
    DB db;

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
}
