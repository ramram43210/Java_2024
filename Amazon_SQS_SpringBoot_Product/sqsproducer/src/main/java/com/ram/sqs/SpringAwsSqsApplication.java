package com.ram.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ram.sqs.model.Product;
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
		
		// Create a product object
        Product product = new Product("1", "IPhone Pro Max 16", 125000);

        // Serialize the product to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(product);
		
		messageSender.sendMessage(productJson);
	}
}