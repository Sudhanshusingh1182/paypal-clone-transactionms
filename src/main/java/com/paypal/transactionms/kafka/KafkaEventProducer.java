package com.paypal.transactionms.kafka;

import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KafkaEventProducer {
	
	private static final String TOPIC = "txn-initiated";
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	private final ObjectMapper objectMapper;
	
	@Autowired
	public KafkaEventProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
		//Register module to handle Java 8 date/time serialization
		this.objectMapper.registerModule(new JavaTimeModule());
	}
	
	//Send raw String message
	public void sendTransactionEvent(String key, String message) {
		log.debug("sendTransactionEvent:: Sending to kafka topic: {}, key: {}, message: {}",TOPIC, key, message);
		
		CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC, key, message);
		
		future.thenAccept(result -> {
			RecordMetadata metaData = result.getRecordMetadata();
			log.debug("sendTransactionEvent:: Kafka message sent successfully. Topic: {}, partition: {}", metaData.topic(), metaData.partition());
		}).exceptionally(e -> {
			log.error("sendTransactionEvent:: failed to send kafka message: {}",e.getMessage());
			return null;
		});
	}
}
