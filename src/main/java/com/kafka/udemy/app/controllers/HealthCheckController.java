package com.kafka.udemy.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class HealthCheckController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckController.class);


	@GetMapping("/healthCheck")
	public ResponseEntity<?> healthCheck() {
		LOGGER.info(">> healthCheck()");

		return ResponseEntity.ok("Server Running...");
	}

}
