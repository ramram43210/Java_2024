package com.ram.s3.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.nio.file.Paths;

@Service
public class S3Service
{

	@Autowired
	private S3Client s3Client;

	private final String bucketName = "rambucket1234";

	/**
	 * Uploads a file to the S3 bucket.
	 */
	public void uploadFile(String keyName, String filePath)
	{
		PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(keyName).build();
		s3Client.putObject(putObjectRequest, RequestBody.fromFile(Paths.get(filePath)));
	}

	/**
	 * Downloads a file from the S3 bucket
	 */
	public void downloadFile(String keyName, String downloadPath)
	{
		GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(keyName).build();
		s3Client.getObject(getObjectRequest, Paths.get(downloadPath));
	}
}
