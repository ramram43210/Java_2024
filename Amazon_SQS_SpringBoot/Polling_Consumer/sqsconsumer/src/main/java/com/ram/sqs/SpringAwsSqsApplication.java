package com.ram.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ram.sqs.service.MessageReceiver;

@SpringBootApplication
public class SpringAwsSqsApplication implements CommandLineRunner
{

	@Autowired
	private MessageReceiver messageReceiver;
	
	public static void main(String[] args)
	{
		SpringApplication.run(SpringAwsSqsApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception
	{
		messageReceiver.pollMessages();
	}

}