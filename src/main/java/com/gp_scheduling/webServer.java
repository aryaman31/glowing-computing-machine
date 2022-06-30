package com.gp_scheduling;

import com.database.DB;
import com.database.DBWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class webServer {
	static DB db;
	public static void main(String[] args) {
		db = new DBWrapper();
		db.setup();
		db.populate();
		SpringApplication.run(webServer.class, args);
	}

	@RequestMapping(value = {"/", "/home", "/bookings", "/view", "/your_appointment", "/note", 
					"/patient_login", "/admin_login", "/admin_home", "/patient_booking_requests",
					"/book_appointment", "/appointment_details", "/appointment_slot"})
	public String index() {
		return "index";
	}
}
