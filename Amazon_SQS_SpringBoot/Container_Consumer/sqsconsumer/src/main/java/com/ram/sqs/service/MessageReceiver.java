package com.ram.sqs.service;

import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.acknowledgement.Acknowledgement;

@Service
public class MessageReceiver
{

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiver.class);

	private final String queueName = "MessageQueue";

	@SqsListener(queueName)
	public void listen(Message<?> message)
	{
		LOGGER.info(message.getPayload()+ " = received on listen method at {}", OffsetDateTime.now());
		//Acknowledgement.acknowledge(message);
	}	
}