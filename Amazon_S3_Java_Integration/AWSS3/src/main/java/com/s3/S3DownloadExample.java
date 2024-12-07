package com.s3;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import java.nio.file.Paths;

//This Java program downloads a file from an Amazon S3 bucket to your local computer.
public class S3DownloadExample
{

	public static void main(String[] args)
	{
		String bucketName = "rambucket1234";
		String keyName = "lion.jpg"; // The file in the S3 bucket is called lion.jpg
		String downloadFilePath = "D:\\misc\\download\\lion.jpg";

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
			 * The program sets up a GetObjectRequest that specifies which bucket and file
			 * to download from Amazon S3.
			 * 
			 * The request includes: Bucket Name: rambucket1234 (the bucket from which the
			 * file is being downloaded). Key Name: lion.jpg (the file name in the S3
			 * bucket).
			 * 
			 */
			GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(keyName).build();

			/**
			 * The getObject() method is used to download the file from S3. The file will be
			 * saved to the specified local path: D:\\misc\\download\\lion.jpg. If the
			 * download is successful, it prints the message: "File downloaded
			 * successfully."
			 */
			s3.getObject(getObjectRequest, Paths.get(downloadFilePath));
			System.out.println("File downloaded successfully.");
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
