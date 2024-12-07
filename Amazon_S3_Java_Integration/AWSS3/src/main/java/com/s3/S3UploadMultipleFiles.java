package com.s3;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class S3UploadMultipleFiles
{

	public static void main(String[] args)
	{
		String bucketName = "rambucket1234"; // Replace with your S3 bucket name
		List<String> filePaths = List.of("D:\\misc\\images\\Bird.jpg", 
				"D:\\misc\\images\\lion.jpg",
				"D:\\misc\\images\\Nature.jpg"); // Add file paths to upload

		// Create an S3 client
		S3Client s3 = S3Client.builder().region(Region.US_EAST_1) // Replace with your bucket's region
				.credentialsProvider(ProfileCredentialsProvider.create()).build();

		try
		{
			// Loop through each file path and upload to S3
			for (String filePath : filePaths)
			{
				File file = new File(filePath);
				if (!file.exists())
				{
					System.err.println("File not found: " + filePath);
					continue;
				}

				String keyName = file.getName(); // Use file name as S3 object key

				PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(keyName).build();

				s3.putObject(putObjectRequest, Paths.get(filePath));
				System.out.println("File uploaded successfully: " + keyName);
			}
		}
		catch (S3Exception e)
		{
			System.err.println("Error uploading files: " + e.awsErrorDetails().errorMessage());
		}
		finally
		{
			s3.close(); // Close the S3 client
		}
	}
}
