package com.s3;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CopyObjectRequest;
import software.amazon.awssdk.services.s3.model.CopyObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

/**
 * This program copies a file (referred to as an "object" in Amazon S3) from one
 * S3 bucket to another using Java and the AWS SDK
 */
public class S3CopyObjectExample
{

	public static void main(String[] args)
	{
		// Source and destination bucket details
		String sourceBucketName = "rambucket1234"; // Replace with your source bucket name
		String sourceObjectKey = "lion.jpg"; // File (object) to copy
		String destinationBucketName = "destrambucket"; // Replace with your destination bucket name
		String destinationObjectKey = "lion.jpg"; // New name in the destination bucket

		// Create an S3 client
		S3Client s3 = S3Client.builder().region(Region.US_EAST_1) // Replace with your bucket's region
				.credentialsProvider(ProfileCredentialsProvider.create()).build();

		try
		{
			// Define the source and destination in the format: "bucket/key"
			String source = sourceBucketName + "/" + sourceObjectKey;

			// Create a request to copy the object
			CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder().copySource(source) // Source bucket and
																									// key
					.destinationBucket(destinationBucketName) // Destination bucket
					.destinationKey(destinationObjectKey) // Destination key (file name)
					.build();

			// Perform the copy operation
			CopyObjectResponse response = s3.copyObject(copyObjectRequest);
			System.out.println("Object copied successfully. ETag: " + response.copyObjectResult().eTag());

		}
		catch (S3Exception e)
		{
			System.err.println("Failed to copy object: " + e.awsErrorDetails().errorMessage());
		}
		finally
		{
			// Close the S3 client
			s3.close();
		}
	}
}
