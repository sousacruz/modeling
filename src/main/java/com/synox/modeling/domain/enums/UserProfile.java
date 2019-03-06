package com.synox.modeling.domain.enums;

public enum UserProfile {

	ADMIN(1, "ROLE_ADMIN"),
	USER(2, "ROLE_USER");
	
	private Integer code;
	private String description;
	
	private UserProfile(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static UserProfile toEnum(Integer code) {
		if (code == null) {
			return null;
		}
		
		for (UserProfile t : UserProfile.values()) {
			if (code.equals(t.getCode())) {
				return t;
			}
		}
		
		throw new IllegalArgumentException("Invalid code " + code);
	}
}
