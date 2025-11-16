package com.paypal.transactionms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.transactionms.pojo.CreateTransactionRequest;
import com.paypal.transactionms.pojo.GenericResponse;
import com.paypal.transactionms.pojo.TransactionListResponse;
import com.paypal.transactionms.service.TransactionServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TransactionController {

	@Autowired
	private TransactionServiceImpl transactionServiceImpl;

	@PostMapping("/api/transactionms/v1/transaction")
	public GenericResponse createTransaction(@RequestBody CreateTransactionRequest createTransactionRequest) {
		long currentTime = System.currentTimeMillis();
		try {
			return transactionServiceImpl.createTransaction(createTransactionRequest);
		} finally {
			log.debug("Time taken to createTransaction: {}", (System.currentTimeMillis() - currentTime));
		}
	}

	@GetMapping("/api/transactionms/v1/getalltransactions")
	public TransactionListResponse getAllTransactions() {
		long currentTime = System.currentTimeMillis();
		try {
			return transactionServiceImpl.getAlltransactions();
		} finally {
			log.debug("Time taken to getAllTransactions: {}", (System.currentTimeMillis() - currentTime));
		}
	}

}
