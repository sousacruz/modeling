package com.synox.modeling.domain.enums;

public enum CustomerType {

	INDIVIDUAL(0, "Individual"),
	COMPANY(1, "Company");
	
	private Integer code;
	private String description;
	
	private CustomerType(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static CustomerType toEnum(Integer code) {
		if (code == null) {
			return null;
		}
		
		for (CustomerType t : CustomerType.values()) {
			if (code.equals(t.getCode())) {
				return t;
			}
		}
		
		throw new IllegalArgumentException("Invalid code " + code);
	}
}
