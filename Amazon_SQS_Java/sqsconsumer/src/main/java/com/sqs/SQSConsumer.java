package com.sqs;

import java.util.List;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

public class SQSConsumer
{
	public static void main(String[] args)
	{

		// Initialize SQS Client
		try (SqsClient sqsClient = SqsClient.builder().build())
		{

			// Specify the queue name
			String queueName = "OrderQueue";
			// Get the queue URL
			GetQueueUrlRequest getQueueUrlRequest = GetQueueUrlRequest.builder().queueName(queueName).build();
			GetQueueUrlResponse getQueueUrlResponse = sqsClient.getQueueUrl(getQueueUrlRequest);
			String queueUrl = getQueueUrlResponse.queueUrl();

			// Print the queue URL
			System.out.println("Queue URL: " + queueUrl);

			// Receive Messages
			ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder().queueUrl(queueUrl)
					.maxNumberOfMessages(5).build();
			List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
			for (Message message : messages)
			{
				System.out.println("Message: " + message.body());

				// Delete the Message
				DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder().queueUrl(queueUrl)
						.receiptHandle(message.receiptHandle()).build();
				sqsClient.deleteMessage(deleteMessageRequest);
				System.out.println("Message deleted: " + message.messageId());
			}
		}
		catch (Exception exe)
		{
			exe.printStackTrace();
		}
	}
}
