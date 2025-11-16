package com.paypal.transactionms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long senderId;

	@Column(nullable = false)
	private Long receiverId;

	@Column(nullable = false)
	@Positive(message = "Amount must be positive")
	private Double amount;

	@Column(nullable = false)
	private LocalDateTime createdDate;

	@Column(nullable = false)
	private String status;

	@PrePersist
	public void prePersist() {
		if (createdDate == null) {
			createdDate = LocalDateTime.now();
		}

		if (status == null) {
			status = "PENDING";
		}
	}

	@Override
	public String toString() {
		return "Transaction{" + "id=" + id + ", senderId=" + senderId + ", receiverId=" + receiverId + ", amount="
				+ amount + ", timestamp=" + createdDate + ", status='" + status + '\'' + '}';
	}
}
