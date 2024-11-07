package com.ram.sqs.service;

import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ram.sqs.model.Product;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;

@Service
public class MessageReceiver
{

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

	private final String queueName = "MessageQueue";

	@SqsListener(queueName)
	public void listen(Message<?> message) throws JsonMappingException, JsonProcessingException
	{
		String productJson = (String)message.getPayload();
		
		// Deserialize and process each message
		ObjectMapper objectMapper = new ObjectMapper();
		Product product = objectMapper.readValue(productJson, Product.class);
		
		System.out.println("Received Product: " + product.getName());
		LOGGER.info(message.getPayload() + " = received on listen method at {}", OffsetDateTime.now());
		//Acknowledgement.acknowledge(message);
	}
}