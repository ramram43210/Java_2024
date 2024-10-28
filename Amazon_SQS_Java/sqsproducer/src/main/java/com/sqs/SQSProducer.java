package com.sqs;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class SQSProducer
{
	public static void main(String[] args)
	{
		// Initialize SQS Client
		try (SqsClient sqsClient = SqsClient.builder().build())
		{
			// Create a Queue
			CreateQueueRequest createQueueRequest = CreateQueueRequest.builder().queueName("OrderQueue").build();
			CreateQueueResponse createQueueResponse = sqsClient.createQueue(createQueueRequest);
			String queueUrl = createQueueResponse.queueUrl();
			System.out.println("Queue URL: " + queueUrl);

			// Send a Message
			SendMessageRequest sendMessageRequest = SendMessageRequest.builder().queueUrl(queueUrl)
					.messageBody("IPhone Pro Max").build();
			sqsClient.sendMessage(sendMessageRequest);
			System.out.println("Message sent successfully");
		}
		catch (Exception exe)
		{
			exe.printStackTrace();
		}

	}
}
