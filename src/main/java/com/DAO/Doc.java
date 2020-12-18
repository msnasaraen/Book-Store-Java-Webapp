/**
 * Copyright Company, Inc. 2017
 */
package com.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author William Nasaraen
 * @since 1.0
 */

@Entity
@Table
public class Doc {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String name;

	@Column
	private int parentid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public Doc(String name, int parentid) {
		super();
		this.name = name;
		this.parentid = parentid;
	}

	public Doc() {
		super();
	}
	
	
	
}
