/**
 * Copyright Company, Inc. 2017
 */
package com.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.DAO.Books;
import com.DAO.Doc;
import com.DAO.SessionUtil;

/**
 *
 * @author William Nasaraen
 * @since 1.0
 */
@Path("/document")
public class Document {

	/**
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 * @author William Nasaraen
	 * @since 1.0
	 */
}