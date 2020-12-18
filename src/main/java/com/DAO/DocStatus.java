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
public class DocStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String label;
	
	@Column
	private int docid;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getDocid() {
		return docid;
	}

	public void setDocid(int docid) {
		this.docid = docid;
	}

	public DocStatus(String label, int docid) {
		super();
		this.label = label;
		this.docid = docid;
	}

	public DocStatus() {
		super();
	}
	
	

}
