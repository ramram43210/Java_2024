package com.s3;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateBucketResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3CreateBucketExample
{

	public static void main(String[] args)
	{
		String bucketName = "rambucket1234"; // Replace with your desired bucket name

		/**
		 * The program first creates a connection to AWS using the S3Client. It
		 * specifies: The AWS region where your buckets are stored (Region.US_EAST_1 in
		 * this case).
		 * 
		 * It uses AWS credentials stored on your local machine (from the AWS CLI).
		 * These credentials allow the program to interact with your AWS account.
		 */
		S3Client s3 = S3Client.builder().region(Region.US_EAST_1) // Replace with your desired region
				.credentialsProvider(ProfileCredentialsProvider.create()).build();

		try
		{
			// Build the CreateBucketRequest
			CreateBucketRequest createBucketRequest = CreateBucketRequest.builder().bucket(bucketName).build();

			// Create the bucket
			CreateBucketResponse createBucketResponse = s3.createBucket(createBucketRequest);
			System.out.println("Bucket created successfully. Location: " + createBucketResponse.location());

		}
		/**
		 * If there's an issue (for example, if the credentials are invalid or there's a
		 * network issue), the program catches that error and prints an error message.
		 */
		catch (S3Exception e)
		{
			System.err.println("Error creating bucket: " + e.awsErrorDetails().errorMessage());
		}
		/**
		 * After the program finishes its task, it closes the connection to the AWS S3
		 * service. This is done to release any resources that were being used.
		 */
		finally
		{
			s3.close();
		}
	}
}
