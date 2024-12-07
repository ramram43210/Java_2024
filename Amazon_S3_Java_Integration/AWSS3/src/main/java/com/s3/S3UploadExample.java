package com.s3;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.nio.file.Paths;

//This program uploads a file from your computer to Amazon's cloud storage service called Amazon S3
public class S3UploadExample
{

	public static void main(String[] args)
	{
		String bucketName = "rambucket1234";
		String keyName = "lion.jpg"; // File Name in S3
		String filePath = "D:\\misc\\images\\lion.jpg";

		/**
		 * The program first creates a connection to AWS using the S3Client. It
		 * specifies: The AWS region where your buckets are stored (Region.US_EAST_1 in
		 * this case).
		 * 
		 * It uses AWS credentials stored on your local machine (from the AWS CLI).
		 * These credentials allow the program to interact with your AWS account.
		 */
		S3Client s3 = S3Client.builder().region(Region.US_EAST_1) // Replace with your bucket's region
				.credentialsProvider(ProfileCredentialsProvider.create()).build();

		try
		{
			/**
			 * Once the connection is set up, the program does the following:
			 * 
			 * It prepares the file for upload by specifying the bucket name (rambucket1234)
			 * and the file name (lion.jpg) in the cloud. It then uploads the file from your
			 * computer to Amazon S3 using the file path (D:\\misc\\images\\lion.jpg).
			 */
			PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(keyName).build();
			PutObjectResponse response = s3.putObject(putObjectRequest, Paths.get(filePath));

			/**
			 * If the upload is successful, the program will print a message with the ETag
			 * of the file. The ETag is a unique identifier for the uploaded file,
			 * confirming that the upload worked.
			 */
			System.out.println("File uploaded successfully. ETag: " + response.eTag());
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
