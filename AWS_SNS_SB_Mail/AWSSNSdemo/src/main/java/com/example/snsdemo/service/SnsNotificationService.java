package com.example.snsdemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

/**
 * This Java class sends notifications using AWS SNS (Simple Notification Service).
 */
@Service
public class SnsNotificationService
{

	//Used to interact with AWS SNS
	private final SnsClient snsClient;

	//Fetches the SNS topic ARN (Amazon Resource Name) from application.properties.
	@Value("${sns.topic.arn}")
	private String topicArn;

	//Constructor (Setting Up the SNS Client)
	public SnsNotificationService(SnsClient snsClient)
	{
		this.snsClient = snsClient;
	}

	//Sending a Notification
	public String sendNotification(String message, String subject)
	{
		PublishRequest request = PublishRequest.builder().message(message).subject(subject).topicArn(topicArn).build();

		PublishResponse response = snsClient.publish(request);
		return response.messageId();
	}
}
