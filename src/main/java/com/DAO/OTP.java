package com.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class OTP {

	
	@Id
	private String username;
	
	@Column
	private int otp;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getOtp() {
		return otp;
	}

	public void setOtp(int otp) {
		this.otp = otp;
	}

	public OTP(String username, int otp) {
		super();
		this.username = username;
		this.otp = otp;
	}

	public OTP() {
		super();
	}




	

}
