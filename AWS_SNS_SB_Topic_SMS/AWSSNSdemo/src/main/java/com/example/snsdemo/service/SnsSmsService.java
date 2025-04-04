package com.example.snsdemo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
public class SnsSmsService
{

	//snsClient connects to AWS SNS.
	private final SnsClient snsClient;
	//This is the ID of the topic where the SMS will be sent
	private final String snsTopicArn;

	//It gets the access key, secret key, region, and topic ARN from your application settings.
	public SnsSmsService(@Value("${aws.accessKey}") String accessKey, @Value("${aws.secretKey}") String secretKey,
			@Value("${aws.region}") String region, @Value("${aws.sns.topic.arn}") String snsTopicArn)
	{
		this.snsClient = SnsClient.builder().region(Region.of(region))
				.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
				.build();

		this.snsTopicArn = snsTopicArn;
	}

	//This method is called when you want to send an SMS.
	public String sendSms(String message)
	{
		try
		{
			PublishRequest request = PublishRequest.builder().message(message).topicArn(snsTopicArn).build();
			PublishResponse response = snsClient.publish(request);
			return "SMS sent successfully! Message ID: " + response.messageId();
		}
		catch (Exception e)
		{
			return "Error sending SMS: " + e.getMessage();
		}
	}
}
