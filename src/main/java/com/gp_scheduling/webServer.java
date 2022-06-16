package com.gp_scheduling;

import com.database.DB;
import com.database.DBWrapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class webServer {
	static DB db;
	public static void main(String[] args) {
		db = new DBWrapper();
		db.setup();
		SpringApplication.run(webServer.class, args);
	}

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
}
