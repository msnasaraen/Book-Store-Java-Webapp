/**
 * @author William Nasaraen
 * Dated 12-07-2017
 * @Copyrighted PSAW R&D
 */
package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.DAO.AccountStatus;
import com.DAO.OTP;
import com.DAO.SessionUtil;
import com.DAO.Users;

/**
 * This class provides methods for organizing users
 * 
 * @author wmichael
 *
 */
@Path("/user")
public class UserAuthentication {

	public static Users userObj;

	/**
	 * 
	 * @param incomingData
	 * Gets the userDetails and Generate otp
	 * @return
	 * @throws Exception
	 */
	@Path("/register")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(String incomingData) throws Exception {

		JSONObject jobj = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
		UserDetails user = mapper.readValue(incomingData, UserDetails.class);
		System.out.println(user.firstname);

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		userObj = new Users(user.firstname + " " + user.lastname, user.username, user.password, user.mobile, user.email,
				user.desig);
		session.save(userObj);
		tx.commit();

		Transaction tx1 = session.beginTransaction();
		Random rand = new Random();
		int num = rand.nextInt(9000000) + 1000000;
		System.out.println(num);
		OTP o1 = new OTP(user.username, num);
		session.save(o1);
		tx1.commit();

		System.out.println(UserAuthentication.userObj.getDesignation());
		if (UserAuthentication.userObj.getDesignation().equals("retailclient")) {
			System.out.println("hii");
			Transaction tx2 = session.beginTransaction();
			AccountStatus a1 = new AccountStatus(UserAuthentication.userObj.getUsername(), "notapproved");
			session.save(a1);
			tx2.commit();
		}
		SendMail s1 = new SendMail();
		s1.email(user.email, num);
		session.close();

		jobj.put("status", "success");
		return Response.ok(jobj.toString()).build();
	}

	/**
	 * 
	 * @param incomingData
	 *            Receives otp and give account for user
	 * @return
	 * @throws Exception
	 */

	@Path("/otp")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response otpVerification(String incomingData) throws Exception {

		JSONObject jobj = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
		Otp otp = mapper.readValue(incomingData, Otp.class);
		System.out.println(otp.otp);

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		String otpQuery = "FROM OTP WHERE username=:username";
		Query query = session.createQuery(otpQuery);
		System.out.println(UserAuthentication.userObj.getUsername());
		query.setString("username", UserAuthentication.userObj.getUsername());
		List<OTP> otpResult = query.list();
		if (otpResult.size() == 1) {
			for (OTP obj : otpResult) {
				System.out.println(obj.getUsername());
				System.out.println(obj.getOtp());

				if (UserAuthentication.userObj.getDesignation().equals("student")) {
					if (otp.otp == obj.getOtp()) {
						System.out.println("Success");
						jobj.put("status", "success");
						jobj.put("desig", UserAuthentication.userObj.getDesignation());
						jobj.put("login", "approved");

					}
				}

				else if (UserAuthentication.userObj.getDesignation().equals("retailclient")) {
					if (otp.otp == obj.getOtp()) {
						System.out.println("Success");
						jobj.put("status", "success");
						jobj.put("desig", UserAuthentication.userObj.getDesignation());

						String hql1 = "FROM AccountStatus where username=:username";
						Query query1 = session.createQuery(hql1);
						query1.setString("username", UserAuthentication.userObj.getUsername());
						List<AccountStatus> resultapprove = query1.list();
						System.out.println(resultapprove.size());
						if (resultapprove.size() == 1) {
							for (AccountStatus a1 : resultapprove) {
								System.out.println(a1.getStatus());
								if (a1.getStatus().equals("approved")) {
									jobj.put("login", "approved");
								} else {
									jobj.put("login", "notapproved");
								}
							}
						}

					}
				}

			}
		}

		tx.commit();
		return Response.ok(jobj.toString()).build();
	}

	/**
	 *
	 * @param incomingData
	 *            Receives username and password and authenticate user
	 * @return
	 * @throws Exception
	 */
	@Path("/login")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response userLogin(String incomingData) throws Exception {

		JSONObject jobj = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
		userLogin user = mapper.readValue(incomingData, userLogin.class);
		System.out.println(user.username);
		System.out.println(user.password);

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		String otpQuery = "FROM Users WHERE username=:username AND password=:password";
		Query query = session.createQuery(otpQuery);
		query.setString("username", user.username);
		query.setString("password", user.password);
		List<Users> userResult = query.list();
		System.out.println(userResult.size());

		if (userResult.size() == 1) {
			for (Users obj : userResult) {
				UserAuthentication.userObj = new Users(obj.getName(), obj.getUsername(), obj.getPassword(),
						obj.getMobile(), obj.getEmail(), obj.getDesignation());
				if (UserAuthentication.userObj.getDesignation().equals("student")) {
					System.out.println("Success");
					jobj.put("status", "success");

					jobj.put("desig", UserAuthentication.userObj.getDesignation());
					jobj.put("login", "approved");

				}

				else if (UserAuthentication.userObj.getDesignation().equals("employee")) {
					System.out.println("Success");
					jobj.put("status", "success");

					jobj.put("desig", UserAuthentication.userObj.getDesignation());
					jobj.put("login", "approved");

				}

				else if (UserAuthentication.userObj.getDesignation().equals("admin")) {
					System.out.println("Success");
					jobj.put("status", "success");

					jobj.put("desig", UserAuthentication.userObj.getDesignation());
					jobj.put("login", "approved");

				} else if (UserAuthentication.userObj.getDesignation().equals("retailclient")) {

					System.out.println("Success");
					jobj.put("status", "success");

					jobj.put("desig", UserAuthentication.userObj.getDesignation());

					String hql1 = "FROM AccountStatus where username=:username";
					Query query1 = session.createQuery(hql1);
					query1.setString("username", UserAuthentication.userObj.getUsername());
					List<AccountStatus> resultapprove = query1.list();
					System.out.println(resultapprove.size());
					if (resultapprove.size() == 1) {
						for (AccountStatus a1 : resultapprove) {
							System.out.println(a1.getStatus());
							if (a1.getStatus().equals("approved")) {
								jobj.put("login", "approved");
							} else {
								jobj.put("login", "notapproved");
							}
						}

					}
				}

				UserAuthentication.userObj = new Users(obj.getName(), obj.getUsername(), obj.getPassword(),
						obj.getMobile(), obj.getEmail(), obj.getDesignation());

			}
		}

		tx.commit();
		session.close();
		return Response.ok(jobj.toString()).build();
	}

	/**
	 * 
	 * @param incomingData
	 * @return Returns Current User Details
	 * @throws Exception
	 */
	@Path("/getUser")
	@GET
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(String incomingData) throws Exception {

		JSONObject jobj = new JSONObject();
		if (UserAuthentication.userObj != null) {
			jobj.put("username", UserAuthentication.userObj.getUsername());
			jobj.put("designation", UserAuthentication.userObj.getDesignation());
		}
		else
		{
			jobj.put("designation","waste");
		}
		return Response.ok(jobj.toString()).build();
	}

	/**
	 * 
	 * @param incomingData
	 *            Receives Retail clients and initially gives not approved
	 *            status
	 * @return
	 * @throws Exception
	 */
	@Path("/approveAccount")
	@GET
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRetailClients(String incomingData) throws Exception {

		JSONArray jarray = new JSONArray();
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		String hql = "FROM AccountStatus where status=:status";
		Query query = session.createQuery(hql);
		query.setString("status", "notapproved");
		tx.commit();
		List<AccountStatus> a = query.list();
		System.out.println(a.size());
		for (AccountStatus a1 : a) {
			Transaction tx2 = session.beginTransaction();
			hql = "FROM Users where username=:username";
			query = session.createQuery(hql);
			query.setString("username", a1.getUsername());
			tx2.commit();
			List<Users> user = query.list();
			System.out.println(user.size());

			for (Users u : user) {
				JSONObject jobj = new JSONObject();

				jobj.put("name", u.getName());
				jobj.put("username", u.getUsername());
				jobj.put("email", u.getEmail());
				jobj.put("mobile", u.getMobile());
				jarray.put(jobj);

			}
		}
		return Response.ok(jarray.toString()).build();
	}

	/**
	 * 
	 * @param incomingData
	 *            Receives Retail Clients username and and give approval for
	 *            them
	 * @return
	 * @throws Exception
	 */
	@Path("/approve")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response setApprove(String incomingData) throws Exception {

		JSONObject json = new JSONObject();

		System.out.println(incomingData);

		String s = incomingData;

		if (s.length() > 0) {
			s = s.substring(1, s.length() - 1);
			System.out.println(s);
			s = s.replaceAll("\"", "");
			System.out.println(s);

			StringTokenizer st = new StringTokenizer(s, ",");
			ArrayList<String> l = new ArrayList<>();
			while (st.hasMoreTokens()) {
				l.add(st.nextToken());

			}
			for (int i = 0; i < l.size(); i++) {
				System.out.println(l.get(i));
				Session session = SessionUtil.getSession();
				Transaction tx1 = session.beginTransaction();
				String hql = "update AccountStatus SET status= :status where username=:username";
				Query query = session.createQuery(hql);
				query.setString("status", "approved");
				query.setString("username", l.get(i));
				int rowCount = query.executeUpdate();
				System.out.println(rowCount);
				tx1.commit();
				session.close();

			}
		}

		return Response.ok(json.toString()).build();
	}

	/**
	 * 
	 * @param incomingData
	 *            Finish the session of the user
	 * @return
	 * @throws Exception
	 */
	@Path("/logout")
	@GET
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(String incomingData) throws Exception {

		JSONObject jobj = new JSONObject();
		UserAuthentication.userObj = null;
		jobj.put("status", "success");
		return Response.ok(jobj.toString()).build();
	}

}

class UserDetails {
	public String firstname;
	public String lastname;
	public String email;
	public String mobile;
	public String username;
	public String desig;
	public String password;
}

class userLogin {
	public String username;
	public String password;
}

class Otp {
	public String username;
	public int otp;
}
