/**
 * @author William Nasaraen
 * Dated 12-07-2017
 * @Copyrighted PSAW R&D
 */

package com.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeSet;

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

import com.DAO.SessionUtil;
import com.DAO.SubPane;
import com.DAO.Users;
import com.DAO.AddtoCart;
import com.DAO.Books;
import com.DAO.BuyBook;
import com.DAO.Doc;
import com.DAO.DocStatus;
import com.DAO.Pane;

/**
 * This Class provides the functionalities for orgnizing books
 * 
 * @author wmichael
 *
 */

@Path("/books")
public class Book {

	public void run() {

	}

	/**
	 * 
	 * @param incomingData
	 *            This parameter gets the books details and put them into
	 *            database
	 * @return Returns success is book is inserted
	 * @throws Exception
	 */
	@Path("/addbooks")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBook(String incomingData) throws Exception {

		JSONObject jobj = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
		BookDetails book = mapper.readValue(incomingData, BookDetails.class);
		System.out.println(book.name);

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Books bookObj = new Books(UserAuthentication.userObj.getUsername(), book.name, book.author, book.publication,
				book.price, book.available, book.category);
		session.save(bookObj);
		tx.commit();
		session.close();
		jobj.put("status", "success");
		return Response.ok(jobj.toString()).build();
	}

	/**
	 * 
	 * @param incomingData
	 *            This function gets the new updated data for book and update
	 *            the particular book with the given id
	 * @return Returns success if book was updated
	 * @throws Exception
	 */

	@Path("/updatebooks")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBook(String incomingData) throws Exception {

		JSONObject jobj = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
		UpdateBookDetails book = mapper.readValue(incomingData, UpdateBookDetails.class);
		System.out.println(book.name);

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		String hql = "update Books set name=:name,author=:author,publication=:publication,price=:price,available=:available,category=:category where retailer=:username AND id=:id ";
		Query query = session.createQuery(hql);
		query.setString("name", book.name);
		query.setString("author", book.author);
		query.setString("publication", book.publication);
		query.setString("category", book.category);
		query.setFloat("price", book.price);
		query.setInteger("available", book.available);
		query.setString("username", UserAuthentication.userObj.getUsername());
		query.setInteger("id", book.id);
		query.executeUpdate();
		tx.commit();

		Transaction tx2 = session.beginTransaction();
		hql = "FROM AddtoCart where bookid=:id";
		query = session.createQuery(hql);
		query.setInteger("id", book.id);
		tx2.commit();

		List<AddtoCart> result = query.list();
		for (AddtoCart a : result) {
			System.out.println(a.getUsername());

			Transaction tx3 = session.beginTransaction();
			hql = "FROM Users where username=:username";
			query = session.createQuery(hql);
			query.setString("username", a.getUsername());
			tx3.commit();
			List<Users> user = query.list();
			System.out.println(user.size());
			SendCartEmail email = new SendCartEmail();
			for (Users u : user) {
				System.out.println(u.getEmail());
				email.sendemail(u.getEmail(), book.id, book.name, book.author, book.available, book.category,
						book.price, book.publication);
			}
		}
		session.close();
		jobj.put("status", "success");
		return Response.ok(jobj.toString()).build();
	}

	/**
	 * 
	 * @param incomingData
	 *            Receives the search category and search for books
	 * @return Returns the list of books
	 * @throws Exception
	 */
	@Path("/searchbooks")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchBook(String incomingData) throws Exception {

		JSONArray jarray = new JSONArray();
		ObjectMapper mapper = new ObjectMapper();
		SearchQuery s = mapper.readValue(incomingData, SearchQuery.class);
		System.out.println(s.search);

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		String hql = "from Books where category=:category";
		Query query = session.createQuery(hql);
		query.setString("category", s.search);
		List<Books> results = query.list();
		System.out.println(results.size());
		for (Books bobj : results) {
			System.out.println(bobj.getName());
			JSONObject jobj = new JSONObject();
			jobj.put("name", bobj.getName());
			jobj.put("author", bobj.getAuthor());
			jobj.put("publication", bobj.getPublication());
			jobj.put("price", bobj.getPrice());
			jobj.put("available", bobj.getAvailable());
			jobj.put("id", bobj.getId());
			jobj.put("category", bobj.getCategory());
			jarray.put(jobj);

		}
		tx.commit();

		session.close();
		return Response.ok(jarray.toString()).build();
	}

	/**
	 * 
	 * @param incomingData
	 *            Gets the book id and add it to the cart of the user
	 * @return
	 * @throws Exception
	 */
	@Path("/addtocart")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response addtocart(String incomingData) throws Exception {

		JSONArray jarray = new JSONArray();
		ObjectMapper mapper = new ObjectMapper();
		AddCart cart = mapper.readValue(incomingData, AddCart.class);
		System.out.println(cart.id);
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		AddtoCart a1 = new AddtoCart(UserAuthentication.userObj.getUsername(), cart.id);
		session.save(a1);

		tx.commit();
		session.close();
		return Response.ok(jarray.toString()).build();
	}

	/**
	 * 
	 * @param incomingData
	 *            Receives book id
	 * @return Returns success if book is available
	 * @throws Exception
	 */
	@Path("/buybook")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response buybook(String incomingData) throws Exception {

		JSONObject jarray = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
		Buybook cart = mapper.readValue(incomingData, Buybook.class);
		System.out.println(cart.id);
		Session session = SessionUtil.getSession();

		Transaction tx0 = session.beginTransaction();
		String hql = "From Books where id=:id";
		Query query = session.createQuery(hql);
		query.setInteger("id", cart.id);
		List<Books> results = query.list();
		int current_available = 0;
		for (Books bobj : results) {
			current_available = bobj.getAvailable();

		}
		tx0.commit();

		if (current_available - cart.count <= 0) {
			jarray.put("status", "failure");
			return Response.ok(jarray.toString()).build();

		}
		Transaction tx1 = session.beginTransaction();

		hql = "update Books set available=:available where id=:id ";
		query = session.createQuery(hql);

		query.setInteger("available", current_available - cart.count);
		query.setInteger("id", cart.id);
		int rowCount = query.executeUpdate();

		System.out.println(rowCount);
		tx1.commit();

		Transaction tx = session.beginTransaction();

		BuyBook a1 = new BuyBook(UserAuthentication.userObj.getUsername(), cart.id);
		session.save(a1);
		jarray.put("status", "success");
		tx.commit();

		session.close();
		return Response.ok(jarray.toString()).build();
	}

	/**
	 * 
	 * @param incomingData
	 *            Gets the username and returns the cart items
	 * @return
	 * @throws Exception
	 */
	@Path("/cartitems")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response cartitems(String incomingData) throws Exception {

		JSONArray jarray = new JSONArray();

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		String hql = "From AddtoCart where username=:username";
		Query query = session.createQuery(hql);
		query.setString("username", UserAuthentication.userObj.getUsername());
		List<AddtoCart> results = query.list();
		tx.commit();
		for (AddtoCart a : results) {
			hql = "From Books where id=:id";
			query = session.createQuery(hql);
			query.setInteger("id", a.getBookid());
			List<Books> book = query.list();
			for (Books b : book) {
				JSONObject jobj = new JSONObject();
				jobj.put("name", b.getName());
				jobj.put("author", b.getAuthor());
				jobj.put("price", b.getPrice());
				jobj.put("available", b.getAvailable());
				jobj.put("id", b.getId());
				jobj.put("category", b.getCategory());
				jarray.put(jobj);
			}

		}

		session.close();
		return Response.ok(jarray.toString()).build();
	}

	/**
	 * 
	 * @param incomingData
	 * @return
	 * @throws Exception
	 */
	@Path("/mypurchase")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response MyPurchase(String incomingData) throws Exception {

		JSONArray jarray = new JSONArray();

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		String hql = "From BuyBook where username=:username";
		Query query = session.createQuery(hql);
		query.setString("username", UserAuthentication.userObj.getUsername());
		List<BuyBook> results = query.list();
		tx.commit();
		for (BuyBook a : results) {
			hql = "From Books where id=:id";
			query = session.createQuery(hql);
			query.setInteger("id", a.getBookid());
			List<Books> book = query.list();
			for (Books b : book) {
				JSONObject jobj = new JSONObject();
				jobj.put("name", b.getName());
				jobj.put("author", b.getAuthor());
				jobj.put("price", b.getPrice());
				jobj.put("available", b.getAvailable());
				jobj.put("id", b.getId());
				jobj.put("category", b.getCategory());
				jarray.put(jobj);
			}

		}

		session.close();
		return Response.ok(jarray.toString()).build();
	}

	/**
	 * 
	 * @param incomingData
	 * @return Returns the list of books added by the user
	 * @throws Exception
	 */
	@Path("/showmybooks")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response showMyBooks(String incomingData) throws Exception {

		JSONArray jarray = new JSONArray();
		ObjectMapper mapper = new ObjectMapper();
		SearchQuery s = mapper.readValue(incomingData, SearchQuery.class);
		System.out.println(s.search);

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		String hql = "from Books where retailer=:username";
		Query query = session.createQuery(hql);
		query.setString("username", UserAuthentication.userObj.getUsername());
		List<Books> results = query.list();
		System.out.println(results.size());
		for (Books bobj : results) {
			System.out.println(bobj.getName());
			JSONObject jobj = new JSONObject();
			jobj.put("name", bobj.getName());
			jobj.put("author", bobj.getAuthor());
			jobj.put("publication", bobj.getPublication());
			jobj.put("price", bobj.getPrice());
			jobj.put("available", bobj.getAvailable());
			jobj.put("id", bobj.getId());
			jobj.put("category", bobj.getCategory());
			jarray.put(jobj);

		}
		tx.commit();

		session.close();
		return Response.ok(jarray.toString()).build();
	}

	public void run(int num) {

	}

	/**
	 * 
	 * @param incomingData
	 * @return Returns all books in the database
	 * @throws Exception
	 */
	@Path("/allbooks")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response allBooks(String incomingData) throws Exception {

		JSONArray jarray = new JSONArray();

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		String hql = "from Books";
		Query query = session.createQuery(hql);

		List<Books> results = query.list();
		System.out.println(results.size());
		for (Books bobj : results) {
			System.out.println(bobj.getName());
			JSONObject jobj = new JSONObject();
			jobj.put("name", bobj.getName());
			jobj.put("author", bobj.getAuthor());
			jobj.put("publication", bobj.getPublication());
			jobj.put("price", bobj.getPrice());
			jobj.put("available", bobj.getAvailable());
			jobj.put("id", bobj.getId());
			jobj.put("category", bobj.getCategory());
			jarray.put(jobj);

		}
		tx.commit();

		session.close();
		return Response.ok(jarray.toString()).build();
	}

	/**
	 * 
	 * @param incomingData
	 *            Gets the query String
	 * @return Returns list of books
	 * @throws Exception
	 */
	@Path("/querybooks")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response queryBooks(String incomingData) throws Exception {

		JSONArray jarray = new JSONArray();
		ObjectMapper mapper = new ObjectMapper();
		SearchQuery s = mapper.readValue(incomingData, SearchQuery.class);
		System.out.println(s.search);
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		String hql = "from Books";
		Query query = session.createQuery(hql);

		List<Books> results = query.list();
		System.out.println(results.size());
		for (Books bobj : results) {
			System.out.println(bobj.getName());
			if (bobj.getName().toLowerCase().contains((s.search.toLowerCase()))) {
				JSONObject jobj = new JSONObject();
				jobj.put("name", bobj.getName());
				jobj.put("author", bobj.getAuthor());
				jobj.put("publication", bobj.getPublication());
				jobj.put("price", bobj.getPrice());
				jobj.put("available", bobj.getAvailable());
				jobj.put("id", bobj.getId());
				jobj.put("category", bobj.getCategory());
				jarray.put(jobj);
			}

		}
		tx.commit();

		session.close();
		return Response.ok(jarray.toString()).build();
	}

	@Path("/adddocument")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response addDocument(String incomingData) throws Exception {

		JSONObject jobj = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();

		DocumentDetails doc = mapper.readValue(incomingData, DocumentDetails.class);
		System.out.println(doc.name);

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Doc d = new Doc(doc.name, doc.parentid);
		session.save(d);
		tx.commit();

		Transaction tx1 = session.beginTransaction();
		DocStatus d1 = new DocStatus("", d.getId());
		session.save(d1);
		tx1.commit();

		session.close();
		jobj.put("status", "success");
		return Response.ok(jobj.toString()).build();
	}

	@Path("/getDocuments")
	@GET
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDocuments(String incomingData) throws Exception {

		JSONArray jarray = new JSONArray();
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		String hql = "FROM Doc";
		Query query = session.createQuery(hql);
		List<Doc> docObj = query.list();
		tx.commit();

		for (Doc d : docObj) {
			JSONObject jobj = new JSONObject();
			jobj.put("id", d.getId());
			jobj.put("parentid", d.getParentid());
			jobj.put("name", d.getName());

			Transaction tx1 = session.beginTransaction();
			hql = "FROM DocStatus where docid=:id";
			query = session.createQuery(hql);
			query.setInteger("id", d.getId());
			List<DocStatus> statusObj = query.list();
			tx1.commit();
			String s1 = "";
			 TreeSet<String> al=new TreeSet<String>();  
			for (DocStatus ds : statusObj) {
				if (ds.getLabel().length() > 0) {
						al.add(ds.getLabel());
				}

			}
			Iterator<String> itr=al.iterator();  
			  while(itr.hasNext()){  
				  	s1+=itr.next();
			  }
			jobj.put("label", s1);
			jarray.put(jobj);

		}

		session.close();

		return Response.ok(jarray.toString()).build();
	}

	@Path("/getPane")
	@GET
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPane(String incomingData) throws Exception {

		JSONArray jarray = new JSONArray();
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();

		String hql = "FROM Pane";
		Query query = session.createQuery(hql);
		List<Pane> paneObj = query.list();
		tx.commit();

		for (Pane d : paneObj) {
			JSONObject jobj = new JSONObject();
			jobj.put("space"," ");
			jobj.put("name", d.getName());
			jobj.put("type", d.getType());
			jarray.put(jobj);

			Transaction tx1=session.beginTransaction();
			hql="FROM SubPane where parent=:parent";
			query=session.createQuery(hql);
			query.setString("parent",d.getName());
			List<SubPane> sb=query.list();
			tx1.commit();
			for(SubPane s1:sb)
			{
				JSONObject jobj1 = new JSONObject();
				jobj1.put("space","       ");
				jobj1.put("name",s1.getName());
				jobj1.put("type",s1.getType());
				jarray.put(jobj1);

			}

		}

		session.close();

		return Response.ok(jarray.toString()).build();
	}

	@Path("/getCount")
	@GET
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCount(String incomingData) throws Exception {

		JSONArray jarray = new JSONArray();
		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		
		String hql = "FROM Pane";
		Query query = session.createQuery(hql);
		List<Pane> paneObj = query.list();
		tx.commit();

		for (Pane d : paneObj) {
			JSONObject jobj = new JSONObject();
			jobj.put("name", d.getName());
			Transaction tx1 = session.beginTransaction();
			hql = "FROM DocStatus where label=:label";
			query = session.createQuery(hql);
			query.setString("label", d.getName());
			List<DocStatus> result = query.list();
			int count = 0;
			jobj.put("count", result.size());
			tx1.commit();
			jarray.put(jobj);

		}

		session.close();

		return Response.ok(jarray.toString()).build();
	}

	@Path("/addpane")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCodingPane(String incomingData) throws Exception {

		JSONObject jobj = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();

		CodingPane code = mapper.readValue(incomingData, CodingPane.class);
		System.out.println(code.name);

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		Pane p = new Pane(code.name, code.type);
		session.save(p);
		tx.commit();

		session.close();
		jobj.put("status", "success");
		return Response.ok(jobj.toString()).build();
	}

	@Path("/addsubpane")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSubPane(String incomingData) throws Exception {

		JSONObject jobj = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(incomingData);
		CodingSubPane csb = mapper.readValue(incomingData, CodingSubPane.class);

		Session session = SessionUtil.getSession();
		Transaction tx = session.beginTransaction();
		SubPane s1 = new SubPane(csb.name, csb.type, csb.parent);
		session.save(s1);
		tx.commit();

		session.close();
		jobj.put("status", "success");
		return Response.ok(jobj.toString()).build();
	}

	@Path("/setlabel")
	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response setApprove(String incomingData) throws Exception {

		JSONObject json = new JSONObject();

		System.out.println(incomingData);

		String s = incomingData;

		System.out.println(s);
		if (s.length() > 0) {
			s = s.substring(1, s.length() - 1);

			StringTokenizer st = new StringTokenizer(s, "|");
			ArrayList<String> l = new ArrayList<>();
			while (st.hasMoreTokens()) {
				l.add(st.nextToken());

			}

			String s1 = l.get(0);
			String s2 = l.get(1);
			System.out.println(s1 + " " + s2);
            
			StringTokenizer st1 = new StringTokenizer(s1, ",");
			ArrayList<String> l1 = new ArrayList<>();
			while (st1.hasMoreTokens()) {
				
				
				l1.add(st1.nextToken());
				
				
			}
			int val=1;

			String s3 = s2;
			s3= s3.replaceAll("\"", "");
			s3 = s3.replace("\\", "");
			s3 = s3.replace("}", "");
			s3 = s3.replace("{", "");
			s3 = s3.replace(":", ",");

			StringTokenizer st2 = new StringTokenizer(s3, ",");
			ArrayList<String> l2 = new ArrayList<>();
			while (st2.hasMoreTokens()) {
				System.out.println("val="+val);
				if(val%2==0)
				{
				System.out.println(val%2);
				l2.add(st2.nextToken());
				val++;
				}
				else
				{
				st2.nextToken();
				val++;
				}
			}
			for (int j = 0; j < l2.size(); j++) {
				System.out.println(l2.get(j));
			}
			for (int i = 0; i < l1.size(); i++) {
				System.out.println(l1.get(i));
				int id = Integer.parseInt(l1.get(i));
				Session session = SessionUtil.getSession();
				for (int j = 0; j < l2.size(); j++) {
					System.out.println(l2.get(j));
					
					Transaction tx1=session.beginTransaction();
					String hql="DELETE FROM DocStatus where label=:label AND docid=:id";
					Query query=session.createQuery(hql);
					query.setString("label",l2.get(j));
					query.setInteger("id",id);
					int rowCount=query.executeUpdate();
					tx1.commit();
					System.out.println(rowCount);
					Transaction tx = session.beginTransaction();
					DocStatus d = new DocStatus(l2.get(j), id);
					session.save(d);
					tx.commit();

				}
				session.close();

			}
		}

		return Response.ok(json.toString()).build();
	}

}

class DocumentDetails {
	public String name;
	public int parentid;
}

class CodingPane {
	public String name;
	public String type;
}

class CodingSubPane {
	public String parent;
	public String name;
	public String type;
}

class BookDetails {
	public String name;
	public String author;
	public String publication;
	public float price;
	public int available;
	public String category;

}

class UpdateBookDetails {
	public int id;
	public String name;
	public String author;
	public String publication;
	public float price;
	public int available;
	public String category;

}

class SearchQuery {
	public String search;
}

class AddCart {

	public int id;
}

class Buybook {

	public int id;
	public int count;
}
