package com.paypal.transactionms.pojo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionAPI {
	private Long transactionId;
	private Long senderId;
	private Long receiverId;
	private Double amount;
	private String status;
	private LocalDateTime createdDate;
}
