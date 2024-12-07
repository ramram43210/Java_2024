package com.s3;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Delete;
import software.amazon.awssdk.services.s3.model.DeleteObjectsRequest;
import software.amazon.awssdk.services.s3.model.ObjectIdentifier;
import software.amazon.awssdk.services.s3.model.DeleteObjectsResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.ArrayList;
import java.util.List;

public class S3DeleteMultipleFilesExample
{

	public static void main(String[] args)
	{
		String bucketName = "rambucket1234"; // Replace with your bucket name
		List<String> keysToDelete = List.of("Bird.jpg", "lion.jpg", "Nature.jpg"); // Replace with the keys of files to
																					// delete

		// Create an S3 client
		S3Client s3 = S3Client.builder().region(Region.US_EAST_1) // Replace with your bucket's region
				.credentialsProvider(ProfileCredentialsProvider.create()).build();

		try
		{
			// Create a list of ObjectIdentifiers for the files to delete
			List<ObjectIdentifier> objects = new ArrayList<>();
			for (String key : keysToDelete)
			{
				objects.add(ObjectIdentifier.builder().key(key).build());
			}

			// Create a Delete object containing the list of objects to delete
			Delete delete = Delete.builder().objects(objects).build();

			// Build the DeleteObjectsRequest
			DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder().bucket(bucketName).delete(delete)
					.build();

			// Execute the delete request
			DeleteObjectsResponse response = s3.deleteObjects(deleteObjectsRequest);
			response.deleted().forEach(deleted -> System.out.println("Deleted: " + deleted.key()));

		}
		catch (S3Exception e)
		{
			System.err.println("Error deleting objects: " + e.awsErrorDetails().errorMessage());
		}
		finally
		{
			// Close the S3 client
			s3.close();
		}
	}
}
