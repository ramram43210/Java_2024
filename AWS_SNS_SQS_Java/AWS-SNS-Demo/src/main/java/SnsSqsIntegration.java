import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueAttributesRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueAttributesResponse;
import software.amazon.awssdk.services.sqs.model.QueueAttributeName;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;
import software.amazon.awssdk.services.sqs.model.SqsException;

public class SnsSqsIntegration
{

	public static void main(String[] args)
	{
		// AWS Region
		Region region = Region.US_EAST_1; // Change as needed

		// Create SNS and SQS Clients
		try (SnsClient snsClient = SnsClient.builder().region(region)
				.credentialsProvider(ProfileCredentialsProvider.create()).build();

				SqsClient sqsClient = SqsClient.builder().region(region)
						.credentialsProvider(ProfileCredentialsProvider.create()).build())
		{

			// 1. Create SNS Topic
			String topicArn = createSnsTopic(snsClient, "MySNSTopic");

			// 2. Create SQS Queue
			String queueUrl = createSqsQueue(sqsClient, "MySQSQueue");

			// 3. Get SQS Queue ARN
			String queueArn = getQueueArn(sqsClient, queueUrl);

			// 4. Subscribe SQS to SNS Topic
			subscribeSqsToSns(snsClient, topicArn, queueArn);

			// 5. Publish Message to SNS Topic
			publishToSns(snsClient, topicArn, "Hello from AWS SNS to SQS!");

			// 6. Receive Message from SQS
			receiveMessagesFromSqs(sqsClient, queueUrl);

		}
		catch (SnsException | SqsException e)
		{
			System.err.println("Error: " + e.awsErrorDetails().errorMessage());
		}
	}

	// Method to Create SNS Topic
	public static String createSnsTopic(SnsClient snsClient, String topicName)
	{
		CreateTopicRequest request = CreateTopicRequest.builder().name(topicName).build();
		CreateTopicResponse response = snsClient.createTopic(request);
		System.out.println("SNS Topic Created: " + response.topicArn());
		return response.topicArn();
	}

	// Method to Create SQS Queue
	public static String createSqsQueue(SqsClient sqsClient, String queueName)
	{
		CreateQueueRequest request = CreateQueueRequest.builder().queueName(queueName).build();
		CreateQueueResponse response = sqsClient.createQueue(request);
		System.out.println("SQS Queue Created: " + response.queueUrl());
		return response.queueUrl();
	}

	// Method to Get SQS Queue ARN
	public static String getQueueArn(SqsClient sqsClient, String queueUrl)
	{
		GetQueueAttributesRequest request = GetQueueAttributesRequest.builder().queueUrl(queueUrl)
				.attributeNames(QueueAttributeName.QUEUE_ARN).build();
		GetQueueAttributesResponse response = sqsClient.getQueueAttributes(request);
		return response.attributes().get(QueueAttributeName.QUEUE_ARN);
	}

	// Method to Subscribe SQS to SNS Topic
	public static void subscribeSqsToSns(SnsClient snsClient, String topicArn, String queueArn)
	{
		SubscribeRequest request = SubscribeRequest.builder().topicArn(topicArn).protocol("sqs").endpoint(queueArn)
				.build();
		SubscribeResponse response = snsClient.subscribe(request);
		System.out.println("SQS Subscribed to SNS. Subscription ARN: " + response.subscriptionArn());
	}

	// Method to Publish Message to SNS Topic
	public static void publishToSns(SnsClient snsClient, String topicArn, String message)
	{
		PublishRequest request = PublishRequest.builder().topicArn(topicArn).message(message).build();
		PublishResponse response = snsClient.publish(request);
		System.out.println("Message Published. Message ID: " + response.messageId());
	}

	// Method to Receive Messages from SQS
	public static void receiveMessagesFromSqs(SqsClient sqsClient, String queueUrl)
	{
		ReceiveMessageRequest request = ReceiveMessageRequest.builder().queueUrl(queueUrl).maxNumberOfMessages(5)
				.waitTimeSeconds(19).build();
		ReceiveMessageResponse response = sqsClient.receiveMessage(request);

		if (response.messages().isEmpty())
		{
			System.out.println("No messages received.");
		}
		else
		{
			response.messages().forEach(msg ->
			{
				System.out.println("Received Message: " + msg.body());
				// Delete message after processing
				deleteMessageFromQueue(sqsClient, queueUrl, msg.receiptHandle());
			});
		}
	}

	// Method to Delete a Message from SQS
	public static void deleteMessageFromQueue(SqsClient sqsClient, String queueUrl, String receiptHandle)
	{
		DeleteMessageRequest request = DeleteMessageRequest.builder().queueUrl(queueUrl).receiptHandle(receiptHandle)
				.build();
		sqsClient.deleteMessage(request);
		System.out.println("Message deleted from SQS.");
	}
}
