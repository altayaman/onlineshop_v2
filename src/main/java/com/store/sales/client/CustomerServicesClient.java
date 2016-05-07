package com.store.sales.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.store.sales.domain.Customer;
import com.store.sales.domain.CustomerList;

public class CustomerServicesClient {
	static private Logger logger = Logger.getLogger(CustomerServicesClient.class);
	private static String CUSTOMER_SERVICES_URL = "http://localhost:8080/zuniversity/webservices/studrestapp/customer/";
	private static String CUSTOMER_SERVICES_AUTH_URL = "http://localhost:8080/zuniversity/webservices/studrestapp/authcustomer/";
	private static Client authclient=null;  /* Required for JAX-RS authorization processing -- client will add Authorization Header */
	private static Client client=null;  
	
	public static void main(String args[]) {
		testLookupCustomer(2);  // it takes ID of the customer as an argument
//		testCustomerListLookup();
//		testCustomerDelete(2);  // it takes ID of the customer as an argument
		
//		Customer newCustomer = createNewCustomer();
//		testPost_addNewCustomer(newCustomer);
		
//		testLookupCustomerWithAuth(1);
	}
	
	
	/* Client that will not add the authorization header */
	private static Client getClient() {
		if (client == null) {
			client = ClientBuilder.newClient();
		}
		
		return client;
	}
	
	public static Customer testLookupCustomer(int idToLookup) {
		int responseCode;
		Customer customer=null;
		
		Client client = getClient();
		
		//Targeting the RESTful Webservice we want to invoke by capturing it in WebTarget instance.
		WebTarget target = client.target(CUSTOMER_SERVICES_URL + idToLookup);
		
		
		//Building the request i.e a GET request to the RESTful Webservice defined
		//by the URI in the WebTarget instance.
		Invocation getAddrEntryInvocation = target.request(MediaType.APPLICATION_XML_TYPE).buildGet();
		Response response = getAddrEntryInvocation.invoke();
		
		responseCode = response.getStatus();
		logger.info("The response code is: " + responseCode);
		logger.info(CUSTOMER_SERVICES_URL + idToLookup);
		if (responseCode == 200) {
			customer = response.readEntity(Customer.class);
			logger.info(customer);
		}
		
		return customer;
	}
	
	public static void testCustomerListLookup() {
		int responseCode;
		Client client = getClient();
		
		//Targeting the RESTful Webservice we want to invoke by capturing it in WebTarget instance.
		WebTarget target = client.target(CUSTOMER_SERVICES_URL);
		
		//Building the request i.e a GET request to the RESTful Webservice defined
		//by the URI in the WebTarget instance.
		Builder request = target.request(MediaType.APPLICATION_XML_TYPE);
		Response response = request.get();
		
		responseCode = response.getStatus();
		logger.info("The response code is: " + responseCode);
		if (responseCode == 200) {
			CustomerList listOfCustomers = response.readEntity(CustomerList.class);
			logger.info("Retrieved student list from Http Get request: " + listOfCustomers);
		}
	}

	public static Customer createNewCustomer() {
	Customer cust = new Customer();
	
	cust.setCustomerFirstName("Alban");
	cust.setCustomerLastName("Morgan");
	cust.setCustomerEmail("alban.morgan@gmail.com");
	
	return cust;
	}
	
	/* Using a POST Http Command, we'll add a completely new student */
	public static void testPost_addNewCustomer(Customer newCustomer) {
		int responseCode;
		
		Client client = getClient();
		
//		Customer newCustomer = createNewCustomer();
		
		WebTarget target = client.target(CUSTOMER_SERVICES_URL);
		
		Builder request = target.request();
		request.accept(MediaType.APPLICATION_XML_TYPE);
		Response response = request.post(Entity.xml(newCustomer));
		
		responseCode = response.getStatus();
		logger.info("The response code from Post operation is: " + responseCode);
		
		if (responseCode == 201) {
			Customer createdCustomer = response.readEntity(Customer.class);
			logger.debug("Student object returned by the POST command: " + createdCustomer);
		}
	}
	
	/* Client that will add an Authorization header */
	private static Client getClientWithAuth() {
		if (authclient == null) {
			authclient = ClientBuilder.newClient();
			/* Dummy user/password that should be overridden in the actual invocations */
			HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic("user", "password");
			authclient.register(authFeature);
		}
		
		return authclient;
	}
	
	/* Using a Delete Http Command, we'll delete an existing student */
	public static void testCustomerDelete(int idOfStudentToDelete) {
		int responseCode;
		Client client = getClient();
		
		WebTarget target = client.target(CUSTOMER_SERVICES_URL + idOfStudentToDelete);
		
		Builder request = target.request();
		request.accept(MediaType.APPLICATION_XML_TYPE);
		Response response = request.delete();
		
		responseCode = response.getStatus();
		logger.info("The response code from delete operation is: " + responseCode);
		
		if (responseCode == Status.OK.getStatusCode()) {
			logger.debug("Customer removed");
		}
	}
	
	/* Use a GET Http Command with an Authorization Header */
	public static void testLookupCustomerWithAuth(int idToLookup) {
//		int idToLookup = 100;  /* Just some hardcoded test data */
		String acctName = "root";  /* Hard coded test data */
		String pswd = "moon";       /* Hard coded test data */
		int responseCode;
		
		/* When using authorization you need a client with the basic authentication registered */
		Client client = getClientWithAuth();
		
		//Targeting the RESTful Webservice we want to invoke by capturing it in WebTarget instance.
		WebTarget target = client.target(CUSTOMER_SERVICES_AUTH_URL + idToLookup);
		Builder requestBuilder = target.request(MediaType.APPLICATION_XML_TYPE);
		/* Use Basic Authorization.  Set the user name and password in the Authorization header */
		requestBuilder.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, acctName);
		requestBuilder.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, pswd);
		
		Response response = requestBuilder.get();
		
		responseCode = response.getStatus();
		logger.info("The response code from lookup with Basic Authorization is: " + responseCode);
		if (responseCode == 200) {
			Customer customer = response.readEntity(Customer.class);
			logger.info(customer);
		}
	}
	
}
