package edu.npu.zu.test;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Before;
import org.junit.Test;

import com.store.sales.domain.Customer;

public class CustomerRestTest {
	private static String CUSTOMER_SERVICES_URL = "http://localhost:8080/zuniversity/webservices/studrestapp/customer/";
	private static String CUSTOMER_SERVICES_AUTH_URL = "http://localhost:8080/zuniversity/webservices/studrestapp/authcustomer/";
	private Customer testCustomer;
	
	@Before
	public void init() {
		testCustomer.setCustomerFirstName("Alan");
		testCustomer.setCustomerLastName("Mann");
		testCustomer.setCustomerFirstName("alan.mann@gmail.com");
	}
	
	/* Client is Required for JAX-RS authorization processing (user name and password must be provided) */
	private  Client getClientWithAuth() {
		Client client;
		
		client = ClientBuilder.newClient();
		/* Dummy user/password that should be overridden in the actual invocations */
		HttpAuthenticationFeature authFeature = HttpAuthenticationFeature.basic("user", "password");
		client.register(authFeature);
		
		return client;
	}

	@Test
	public  void testLookupCustomer() {
		int idToLookup = 1;  /* Just some hardcoded test data */
		int responseCode;
		Customer customer = null;
		
		Client client = ClientBuilder.newClient();
		
		// Targeting the RESTful Webservice URI we want to invoke by capturing it in WebTarget instance.
		WebTarget target = client.target(CUSTOMER_SERVICES_URL + idToLookup);
		
		
		//Building the request i.e a GET request to the RESTful Webservice defined
		//by the URI in the WebTarget instance.
		Invocation getCustomerInvocation = target.request(MediaType.APPLICATION_XML_TYPE).buildGet();
		Response response = getCustomerInvocation.invoke();
		
		responseCode = response.getStatus();
		assertEquals(responseCode, 200);
		
		customer = response.readEntity(Customer.class);
		assertEquals(customer,testCustomer);
	}
	
	@Test  
	public  void testLookupCustomerWithAuth() {
		int idToLookup = 1;        /* Just some hardcoded test data */
		String acctName = "root";  /* Hard coded test data */
		String pswd = "moon";      /* Hard coded test data */
		int responseCode;
		
		/* When using authorization you need a client with the basic authentication registered */
		Client client = getClientWithAuth();
		
		// WebTarget instance holds the web service URI.
		WebTarget target = client.target(CUSTOMER_SERVICES_AUTH_URL + idToLookup);
		Builder requestBuilder = target.request(MediaType.APPLICATION_XML_TYPE);  /* we'd like to get back XML from the web service */
		/* Use Basic Authorization.  Set the user name and password in the Authorization header */
		requestBuilder.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, acctName);
		requestBuilder.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, pswd);
		
		
		//Building the request i.e a GET request to the RESTful Webservice defined
		//by the URI in the WebTarget instance.
		Invocation getCustomerRequestInvocation = requestBuilder.buildGet();
		Response response = getCustomerRequestInvocation.invoke();
		
		responseCode = response.getStatus();
		assertEquals(responseCode, 200);
		
		Customer customer = response.readEntity(Customer.class);
		assertEquals(customer,testCustomer);
	}
	
	@Test
	public void testPost() {
		int responseCode;
		Customer newCustomer = new Customer();
		newCustomer.setCustomerFirstName("Thai");
		newCustomer.setCustomerLastName("Hsu");
		newCustomer.setCustomerEmail("thai.hsu@gmail.com");
		
		Client client = ClientBuilder.newClient();
		
		WebTarget target = client.target(CUSTOMER_SERVICES_URL);
		
		Builder request = target.request();
		request.accept(MediaType.APPLICATION_XML_TYPE);
		Response response = request.post(Entity.xml(newCustomer));
		
		responseCode = response.getStatus();
		assertEquals(responseCode, 201);
		
		Customer createdCustomer = response.readEntity(Customer.class);
		int createdId = createdCustomer.getCustomerId();
		newCustomer.setCustomerId(createdId);
		assertEquals(newCustomer,createdCustomer);
	}
}
