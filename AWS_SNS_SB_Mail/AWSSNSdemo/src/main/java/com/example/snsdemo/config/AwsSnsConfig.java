package com.example.snsdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
public class AwsSnsConfig
{
	/**
	 * It fetches AWS credentials (Access Key & Secret Key) from the application's
	 * configuration (like application.properties)
	 */

	@Value("${aws.accessKeyId}")
	private String accessKey;

	@Value("${aws.secretAccessKey}")
	private String secretkey;

	/**
	 * @Bean - This tells Spring Boot to create an SNS Client object that can be used
	 * anywhere in the application.
	 */
	@Bean
	public SnsClient snsClient()
	{
		return SnsClient.builder().region(Region.US_EAST_1)
				.credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretkey)))
				.build();
	}
}
