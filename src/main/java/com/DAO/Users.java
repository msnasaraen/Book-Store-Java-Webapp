package com.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author authour
 * @since 1.0
 */
@Entity
@Table
public class Users {
		
	@Column
	private String name;
	
	@Id
	@Column(nullable = false)
	private String username;
	
	@Column
	private String password;
	
	@Column
	private String mobile;
	
	@Column
	private String email;
	 
	@Column
	private String designation;

	public Users(String name, String username, String password, String mobile, String email, String designation) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.mobile = mobile;
		this.email = email;
		this.designation = designation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Users() {
		super();
	}
	
	


}
