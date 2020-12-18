package com.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author William Nasaraen
 * @since 1.0
 */

@Table
@Entity
public class AccountStatus {

	@Id
	private String username;

	@Column
	private String status;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public AccountStatus(String username, String status) {
		super();
		this.username = username;
		this.status = status;
	}

	public AccountStatus() {
		super();
	}

}
