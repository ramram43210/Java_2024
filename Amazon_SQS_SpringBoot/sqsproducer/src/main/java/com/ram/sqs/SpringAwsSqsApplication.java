package com.ram.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ram.sqs.service.MessageSender;

@SpringBootApplication
public class SpringAwsSqsApplication implements CommandLineRunner
{

	@Autowired
	private MessageSender messageSender;

	public static void main(String[] args)
	{
		SpringApplication.run(SpringAwsSqsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		int i=1;
		while(true)
		{
			messageSender.sendMessage("Hello World "+ i);
			//sleep for 5 seconds
			Thread.sleep(5000);
			++i;
		}
	}
}