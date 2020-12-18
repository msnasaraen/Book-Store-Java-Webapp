/**
 * Copyright Company, Inc. 2017
 */
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
public class Pane {
	
	@Id
	private String name;
	@Column
	private String type;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Pane(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
	public Pane() {
		super();
	}
	

}
