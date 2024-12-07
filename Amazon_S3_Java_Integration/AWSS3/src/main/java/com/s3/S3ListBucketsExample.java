package com.s3;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

//This program connects to Amazon's S3 service and lists all the S3 buckets in your AWS account
public class S3ListBucketsExample
{

	public static void main(String[] args)
	{

		/**
		 * The program first creates a connection to AWS using the S3Client. It
		 * specifies: The AWS region where your buckets are stored (Region.US_EAST_1 in
		 * this case).
		 * 
		 * It uses AWS credentials stored on your local machine (from the AWS CLI).
		 * These credentials allow the program to interact with your AWS account.
		 */
		S3Client s3 = S3Client.builder().region(Region.US_EAST_1) // Replace with the desired region
				.credentialsProvider(ProfileCredentialsProvider.create()) // Uses AWS CLI credentials
				.build();

		try
		{
			/**
			 * The program creates a request to list all the S3 buckets associated with your
			 * AWS account. It uses ListBucketsRequest.builder().build() to set up this
			 * request.
			 */
			ListBucketsRequest listBucketsRequest = ListBucketsRequest.builder().build();

			/**
			 * After sending the request, it receives a response containing all the buckets
			 * in your AWS account. The program then prints the names of the buckets one by
			 * one using a forEach loop.
			 */
			ListBucketsResponse listBucketsResponse = s3.listBuckets(listBucketsRequest);

			// Print out the bucket names
			System.out.println("List of S3 Buckets:");
			listBucketsResponse.buckets().forEach(bucket -> System.out.println(bucket.name()));

		}
		/**
		 * If there's an issue (for example, if the credentials are invalid or there's a
		 * network issue), the program catches that error and prints an error message.
		 */
		catch (S3Exception e)
		{
			System.err.println("Error occurred: " + e.awsErrorDetails().errorMessage());
		}
		/**
		 * After the program finishes its task, it closes the connection to the AWS S3
		 * service. This is done to release any resources that were being used.
		 */
		finally
		{
			s3.close(); // Close the S3 client
		}
	}
}
