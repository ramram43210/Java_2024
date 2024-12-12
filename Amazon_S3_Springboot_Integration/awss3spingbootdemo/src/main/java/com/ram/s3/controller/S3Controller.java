package com.ram.s3.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ram.s3.service.S3Service;

@RestController
@RequestMapping("/s3")
public class S3Controller
{

	@Autowired
	private S3Service s3Service;

	//Uploads a file to AWS S3.
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException
	{
		//The file is saved temporarily on the server before uploading to S3.
		String keyName = file.getOriginalFilename();
		File tempFile = File.createTempFile("temp", null);
		file.transferTo(tempFile);
		
		s3Service.uploadFile(keyName, tempFile.getAbsolutePath());
		return "File uploaded successfully.";
	}

	//Downloads a file from AWS S3 to a specific location on your computer.
	@GetMapping("/download")
	public String downloadFile(@RequestParam("keyName") String keyName,
			@RequestParam("downloadPath") String downloadPath)
	{
		s3Service.downloadFile(keyName, downloadPath);
		return "File downloaded successfully.";
	}
}
