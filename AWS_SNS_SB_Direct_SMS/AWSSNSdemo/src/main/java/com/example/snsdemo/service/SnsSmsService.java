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

	private final SnsClient snsClient;

	/**
	 * Constructor with Dependency Injection. 
	 * This constructor uses Spring's @Value annotation to read AWS credentials and region from configuration.
	 */
	public SnsSmsService(@Value("${aws.accessKey}") String accessKey, @Value("${aws.secretKey}") String secretKey,
			@Value("${aws.region}") String region)
	{
		this.snsClient = SnsClient.builder().region(Region.of(region))
				.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
				.build();
	}

	/**
	 * Sends an SMS directly to a phone number via SNS
	 * Sends SMS without creating a topic — just use the number
	 */
	public String sendSms(String phoneNumber, String message)
	{
		try
		{
			PublishRequest request = PublishRequest.builder().message(message).phoneNumber(phoneNumber).build();
			PublishResponse response = snsClient.publish(request);
			return "SMS sent successfully! Message ID: " + response.messageId();
		}
		catch (Exception e)
		{
			return "Error sending SMS: " + e.getMessage();
		}
	}
}
