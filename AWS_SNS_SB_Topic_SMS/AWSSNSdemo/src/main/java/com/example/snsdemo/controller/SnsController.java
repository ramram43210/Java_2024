package com.example.snsdemo.controller;

import com.example.snsdemo.service.SnsSmsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sns") // Every method in this controller will start with /sns in the URL.
public class SnsController
{

	private final SnsSmsService snsSmsService;

	/**
	 * When the app starts, Spring gives this controller an instance of
	 * SnsSmsService so it can use it to send messages.
	 * @param snsSmsService
	 */
	public SnsController(SnsSmsService snsSmsService)
	{
		this.snsSmsService = snsSmsService;
	}

	@PostMapping("/sendSms")
	//POST /sns/sendSms
	public String sendSms(@RequestParam String message)
	{
		System.out.println("Send SMS");
		return snsSmsService.sendSms(message);
	}
}
