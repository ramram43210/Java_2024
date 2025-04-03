package com.example.snsdemo.controller;

import com.example.snsdemo.service.SnsNotificationService;
import com.example.snsdemo.service.SnsSubscriptionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sns") //All URLs in this controller start with /sns.
public class SnsController
{

	private final SnsSubscriptionService snsSubscriptionService;
	private final SnsNotificationService snsNotificationService;

	//Constructor for Dependency Injection
	public SnsController(SnsSubscriptionService snsSubscriptionService, SnsNotificationService snsNotificationService)
	{
		this.snsSubscriptionService = snsSubscriptionService;
		this.snsNotificationService = snsNotificationService;
	}

	//Subscribe an Email to SNS
	//POST /sns/subscribe?email=example@gmail.com
	@PostMapping("/subscribe")
	public String subscribeEmail(@RequestParam String email)
	{
		return snsSubscriptionService.subscribeEmail(email);
	}

	//Send a Notification to SNS Subscribers
	//POST /sns/notify?message=Hello&subject=Test
	@PostMapping("/notify")
	public String sendNotification(@RequestParam String message, @RequestParam String subject)
	{
		return snsNotificationService.sendNotification(message, subject);
	}
}
