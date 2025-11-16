package com.paypal.transactionms.pojo;

import java.util.List;

import com.paypal.transactionms.error.ErrorDetail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse {
	private Boolean success;
	private List<ErrorDetail> errorDetailList;
}
