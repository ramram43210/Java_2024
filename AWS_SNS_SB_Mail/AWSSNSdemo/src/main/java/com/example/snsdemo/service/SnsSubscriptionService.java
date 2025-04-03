package com.example.snsdemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;

/**
 * This Java class is a Spring Boot service that allows users to subscribe their
 * email to an AWS SNS (Simple Notification Service) topic.
 */
@Service
public class SnsSubscriptionService
{

	//Used to interact with AWS SNS
	private final SnsClient snsClient;

	//Fetches the SNS topic ARN (Amazon Resource Name) from application.properties.
	@Value("${sns.topic.arn}")
	private String topicArn;

	//Constructor-Based Dependency Injection
	public SnsSubscriptionService(SnsClient snsClient)
	{
		this.snsClient = snsClient;
	}

	//Subscribe an Email to SNS Topic
	public String subscribeEmail(String email)
	{
		SubscribeRequest request = SubscribeRequest.builder().protocol("email").endpoint(email)
				.returnSubscriptionArn(true).topicArn(topicArn).build();

		SubscribeResponse response = snsClient.subscribe(request);
		return response.subscriptionArn();
	}
}
