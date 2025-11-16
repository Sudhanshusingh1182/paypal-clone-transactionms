package com.paypal.transactionms.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.transactionms.dao.TransactionRepo;
import com.paypal.transactionms.entity.Transaction;
import com.paypal.transactionms.error.ErrorDetail;
import com.paypal.transactionms.kafka.KafkaEventProducer;
import com.paypal.transactionms.pojo.CreateTransactionRequest;
import com.paypal.transactionms.pojo.GenericResponse;
import com.paypal.transactionms.pojo.TransactionAPI;
import com.paypal.transactionms.pojo.TransactionListResponse;
import com.paypal.transactionms.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl {

	@Autowired
	private TransactionRepo transactionRepo;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private KafkaEventProducer kafkaEventProducer;

	public GenericResponse createTransaction(CreateTransactionRequest createTransactionRequest) {
		try {
			log.debug("createTransaction:: createTransactionRequest: {}", createTransactionRequest);

			Transaction transaction = Transaction.builder().receiverId(createTransactionRequest.getReceiverId())
					.senderId(createTransactionRequest.getSenderId()).amount(createTransactionRequest.getAmount())
					.createdDate(LocalDateTime.now()).status("SUCCESS").build();

			transactionRepo.save(transaction);
			
			String eventPayload = objectMapper.writeValueAsString(transaction);
			String key = String.valueOf(transaction.getId());
			kafkaEventProducer.sendTransactionEvent(key, eventPayload);
			log.debug("createTransaction:: Kafka message sent successfully");
			
			return GenericResponse.builder().success(true).build();

		} catch (Exception e) {
			log.error(StringUtils.ERROR_STR, e.getClass(), e.getLocalizedMessage(), e);
			return GenericResponse.builder().success(false)
					.errorDetailList(Arrays.asList(ErrorDetail.INTERNAL_SERVER_ERROR)).build();
		}
	}

	public TransactionListResponse getAlltransactions() {
		try {
			log.debug("getAlltransactions:: Inside the service class");

			List<Transaction> transactionList = transactionRepo.findAll();

			List<TransactionAPI> transactionAPIList = transactionList.stream()
					.map(transaction -> TransactionAPI.builder().transactionId(transaction.getId())
							.amount(transaction.getAmount()).senderId(transaction.getSenderId())
							.receiverId(transaction.getReceiverId()).status(transaction.getStatus())
							.createdDate(transaction.getCreatedDate()).build())
					.toList();

			return TransactionListResponse.builder().transactionAPIList(transactionAPIList).build();

		} catch (Exception e) {
			log.error(StringUtils.ERROR_STR, e.getClass(), e.getLocalizedMessage(), e);
			return TransactionListResponse.builder().errorDetailList(Arrays.asList(ErrorDetail.INTERNAL_SERVER_ERROR))
					.build();
		}
	}

}
