import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

public class SnsExample
{
	
	public static void main(String[] args)
	{
		String phoneNumber = "+913333322222"; // Replace with the recipient's phone number
		String message = "Hello! This is a test message from AWS SNS.";

		// Create SNS Client
		SnsClient snsClient = SnsClient.builder().region(Region.US_EAST_1) // Change to your AWS region
				.credentialsProvider(DefaultCredentialsProvider.create()).build();

		// Send SMS
		PublishRequest request = PublishRequest.builder().message(message).phoneNumber(phoneNumber).build();
		PublishResponse response = snsClient.publish(request);
		System.out.println("Message sent! Message ID: " + response.messageId());

		// Close the SNS Client
		snsClient.close();
	}
}
