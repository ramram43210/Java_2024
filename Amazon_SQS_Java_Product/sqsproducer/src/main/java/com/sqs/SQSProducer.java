package com.sqs;

import com.fasterxml.jackson.databind.ObjectMapper;

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

			// Create a product object
            Product product = new Product("1", "IPhone Pro Max 16", 125000);

            // Serialize the product to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String productJson = objectMapper.writeValueAsString(product);

            // Send the product JSON to SQS
            SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(productJson)
                    .build();

            sqsClient.sendMessage(sendMsgRequest);
            System.out.println("Product sent to SQS: " + productJson);
		}
		catch (Exception exe)
		{
			exe.printStackTrace();
		}

	}
}
