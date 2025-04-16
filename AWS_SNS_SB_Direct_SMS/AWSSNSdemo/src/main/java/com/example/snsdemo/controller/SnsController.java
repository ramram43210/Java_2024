package com.example.snsdemo.controller;

import com.example.snsdemo.service.SnsSmsService;
import org.springframework.web.bind.annotation.*;

/**
 * This SnsController class is a Spring Boot REST controller that exposes an
 * HTTP endpoint to send SMS messages using AWS SNS via the service you created
 * (SnsSmsService).
 */
@RestController
@RequestMapping("/sns") // All endpoints in this controller will be prefixed with /sns.
public class SnsController
{

	private final SnsSmsService snsSmsService;

	/**
	 * Spring injects the SnsSmsService into the controller via constructor-based
	 * dependency injection.
	 */
	public SnsController(SnsSmsService snsSmsService)
	{
		this.snsSmsService = snsSmsService;
	}

	/**
	 * Handles POST requests to /sns/sendSms?phoneNumber=+14155552671&message=Hello.
	 */
	@PostMapping("/sendSms")
	public String sendSms(@RequestParam String phoneNumber, @RequestParam String message)
	{
		return snsSmsService.sendSms(phoneNumber, message);
	}
}
