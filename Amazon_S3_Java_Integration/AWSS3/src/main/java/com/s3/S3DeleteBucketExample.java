package com.s3;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3DeleteBucketExample
{

	public static void main(String[] args)
	{
		String bucketName = "rambucket1234"; // Replace with the name of the bucket to delete

		// Create an S3 client
		S3Client s3 = S3Client.builder().region(Region.US_EAST_1) // Replace with your bucket's region
				.credentialsProvider(ProfileCredentialsProvider.create()).build();

		try
		{
			// Create a delete bucket request
			DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucketName).build();

			// Perform the delete bucket operation
			s3.deleteBucket(deleteBucketRequest);
			System.out.println("Bucket '" + bucketName + "' deleted successfully.");

		}
		catch (S3Exception e)
		{
			System.err.println("Error deleting bucket: " + e.awsErrorDetails().errorMessage());
		}
		finally
		{
			// Close the S3 client
			s3.close();
		}
	}
}
