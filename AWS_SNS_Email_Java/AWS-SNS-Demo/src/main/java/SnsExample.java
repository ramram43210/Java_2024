import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

public class SnsExample
{
	public static void main(String[] args)
	{
		// Set AWS Region
		Region region = Region.US_EAST_1; // Change as needed

		// Create SNS Client
		try (SnsClient snsClient = SnsClient.builder().region(region)
				.credentialsProvider(ProfileCredentialsProvider.create()).build())
		{

			// 1. Create SNS Topic
			String topicArn = createSNSTopic(snsClient, "MyTestTopic");

			// 2. Subscribe Email to the Topic
			subscribeToTopic(snsClient, topicArn, "email", "test@gmail.com"); // Change to your email

			// 3. Publish Message to SNS Topic
			publishToTopic(snsClient, topicArn, "Hello! This is a test message from AWS SNS using Java.");

		}
		catch (SnsException e)
		{
			System.err.println("Error: " + e.awsErrorDetails().errorMessage());
		}
	}

	// Method to Create SNS Topic
	public static String createSNSTopic(SnsClient snsClient, String topicName)
	{
		CreateTopicRequest request = CreateTopicRequest.builder().name(topicName).build();
		CreateTopicResponse response = snsClient.createTopic(request);
		System.out.println("SNS Topic Created: " + response.topicArn());
		return response.topicArn();
	}

	// Method to Subscribe Email or SMS to the Topic
	public static void subscribeToTopic(SnsClient snsClient, String topicArn, String protocol, String endpoint)
	{
		SubscribeRequest request = SubscribeRequest.builder().topicArn(topicArn).protocol(protocol).endpoint(endpoint)
				.build();
		SubscribeResponse response = snsClient.subscribe(request);
		System.out.println("Subscription Request Sent. Subscription ARN: " + response.subscriptionArn());
	}

	// Method to Publish Message to SNS Topic
	public static void publishToTopic(SnsClient snsClient, String topicArn, String message)
	{
		PublishRequest request = PublishRequest.builder().topicArn(topicArn).message(message).build();
		PublishResponse response = snsClient.publish(request);
		System.out.println("Message Published. Message ID: " + response.messageId());
	}
}
