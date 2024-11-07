package com.ram.sqs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.awspring.cloud.sqs.operations.SqsTemplate;

@Service
public class MessageSender
{

	@Autowired
	private SqsTemplate sqsTemplate;
	
	private final String queueName = "MessageQueue";

	public void sendMessage(String productJson)
	{
		sqsTemplate.send(sqsSendOptions -> sqsSendOptions.queue("MessageQueue").payload(productJson));
		System.out.println(productJson + " = sent successfully to the "+ queueName);
	}
}