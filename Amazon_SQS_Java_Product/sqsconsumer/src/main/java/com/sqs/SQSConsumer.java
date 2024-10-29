package com.sqs;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

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

			// Receive messages from SQS
			ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder().queueUrl(queueUrl)
					.maxNumberOfMessages(1) // Adjust as needed
					.waitTimeSeconds(10) // Long polling
					.build();

			ReceiveMessageResponse receiveResponse = sqsClient.receiveMessage(receiveRequest);
			List<Message> messages = receiveResponse.messages();

			// Deserialize and process each message
			ObjectMapper objectMapper = new ObjectMapper();
			for (Message message : messages)
			{
				String productJson = message.body();
				Product product = objectMapper.readValue(productJson, Product.class);
				System.out.println("Received Product: " + product.getName());

				// Optionally delete the message after processing
				sqsClient.deleteMessage(DeleteMessageRequest.builder().queueUrl(queueUrl)
						.receiptHandle(message.receiptHandle()).build());
				System.out.println("Message deleted: " + message.messageId());
			}
		}
		catch (Exception exe)
		{
			exe.printStackTrace();
		}
	}
}
