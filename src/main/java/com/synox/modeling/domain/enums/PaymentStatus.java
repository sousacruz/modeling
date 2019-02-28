package com.synox.modeling.domain.enums;

public enum PaymentStatus {

	OPEN(0, "Open/Pending/Outstanding"),
	PAID(1, "Paid/Settled/Liquidated"),
	CANCELED(2, "Canceled/Stopped/Withdraw");
	
	private Integer code;
	private String description;
	
	private PaymentStatus(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static PaymentStatus toEnum(Integer code) {
		if (code == null) {
			return null;
		}
		
		for (PaymentStatus t : PaymentStatus.values()) {
			if (code.equals(t.getCode())) {
				return t;
			}
		}
		
		throw new IllegalArgumentException("Invalid code " + code);
	}	
}
