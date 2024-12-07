package com.s3;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.nio.file.Paths;

public class S3DownloadMultipleFiles
{

	public static void main(String[] args)
	{
		String bucketName = "rambucket1234"; // Replace with your bucket name
		String downloadDirectory = "D:\\misc\\download\\"; // Replace with your desired local download directory

		// Create an S3 client
		S3Client s3 = S3Client.builder().region(Region.US_EAST_1) // Replace with your bucket's region
				.credentialsProvider(ProfileCredentialsProvider.create()).build();

		try
		{
			// List all objects in the S3 bucket
			ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder().bucket(bucketName).build();
			ListObjectsV2Response listObjectsResponse = s3.listObjectsV2(listObjectsRequest);

			// Check if the directory exists, if not, create it
			File directory = new File(downloadDirectory);
			if (!directory.exists())
			{
				directory.mkdirs();
			}

			// Loop through each object in the bucket
			listObjectsResponse.contents().forEach(object ->
			{
				String keyName = object.key();
				System.out.println("Downloading: " + keyName);

				// Build the request to download the object
				GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(keyName).build();

				// Download the object
				s3.getObject(getObjectRequest, Paths.get(downloadDirectory + keyName));
				System.out.println("File downloaded: " + keyName);
			});

		}
		catch (S3Exception e)
		{
			System.err.println("Error: " + e.awsErrorDetails().errorMessage());
		}
		finally
		{
			// Close the S3 client
			s3.close();
		}
	}
}
