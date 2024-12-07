package com.s3;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

//Java program to delete objects (files) from an Amazon S3 bucket using the AWS SDK
public class S3DeleteObjectExample
{

	public static void main(String[] args)
	{

		String bucketName = "rambucket1234"; // Your S3 bucket name
		String objectKey = "lion.jpg"; // The file (object) you want to delete

		// Creates an S3Client instance to communicate with AWS S3
		S3Client s3 = S3Client.builder().region(Region.US_EAST_1) // Replace with your bucket's region
				.credentialsProvider(ProfileCredentialsProvider.create()).build();

		try
		{
			// Create a request to delete the object
			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(objectKey)
					.build();

			// Delete the object from the bucket
			s3.deleteObject(deleteObjectRequest);
			System.out.println("Object " + objectKey + " was deleted successfully from bucket " + bucketName);

		}
		catch (S3Exception e)
		{
			System.err.println("Failed to delete object: " + e.awsErrorDetails().errorMessage());
		}
		finally
		{
			// Close the S3 client
			s3.close();
		}
	}
}
