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

/**
 *
 * @author William Nasaraen
 * @since 1.0
 */
public class SendMail {

	/**
	 * @param mailid
	 * @param code
	 * @throws Exception
	 * @author William Nasaraen
	 * @since 1.0
	 */
	public void email(String mailid, int code) throws Exception {

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
			message.setSubject("Activation code for ReadIt");
			message.setText("Activation code:" + code);

			Transport.send(message);
			System.out.println("Done");

		} catch (MessagingException e) {
			//throw new RuntimeException(e);
		}

	}
}
