package com.gp_scheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class webServer {

	public static void main(String[] args) {
		SpringApplication.run(webServer.class, args);
	}

	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
}