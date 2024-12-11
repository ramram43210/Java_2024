package com.ram.s3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig
{

	@Value("${aws.accessKeyId}")
	private String accessKeyId;

	@Value("${aws.secretKey}")
	private String secretKey;

	@Value("${aws.region}")
	private String region;

	/**
	 * Creates a reusable S3Client object, so other parts of the application can use
	 * it for AWS S3 operations like uploading or downloading files
	 */
	@Bean
	public S3Client s3Client()
	{
		AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretKey);
		return S3Client.builder().region(Region.of(region))
				.credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();
	}
}
