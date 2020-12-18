/**
 * @author William Nasaraen
 * Dated 12-07-2017
 * @Copyrighted PSAW R&D
 */
package com.controller;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendCartEmail {

	/**
	 * @param mailid
	 * @param id
	 * @param name
	 * @param author
	 * @param available
	 * @param category
	 * @param price
	 * @param publication
	 * @throws Exception
	 * @author authour
	 * @since 1.0
	 */
	public void sendemail(String mailid, int id, String name, String author, int available, String category,
			float price, String publication) throws Exception {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				// put the original username and password
				return new PasswordAuthentication("", "");
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(""));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailid));
			message.setSubject("Greeting from Read It");
			message.setText("The Book in your cart is updated recently\nBook id:" + id + "\nBook name:" + name
					+ "\nBook author:" + author + "\nBook price:" + price + "\nBook available:" + available
					+ "\nBook category:" + category + "\nBook publication:" + publication);

			Transport.send(message);
			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
