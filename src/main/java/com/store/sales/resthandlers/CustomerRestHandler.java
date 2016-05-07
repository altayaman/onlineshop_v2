package com.store.sales.resthandlers;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.store.sales.domain.Customer;
import com.store.sales.domain.CustomerAccount;
import com.store.sales.domain.CustomerList;
import com.store.sales.exceptions.InvalidAcctException;
import com.store.sales.exceptions.UnknownResourceException;
import com.store.sales.services.CustomerAccountService;
import com.store.sales.services.CustomerServiceI;


@Path("/customerrestapp")
public class CustomerRestHandler {
	@Autowired
	private CustomerServiceI customerService;
	private Logger logger = Logger.getLogger(CustomerRestHandler.class);
	
	/* Test Url -- Post the data from file student.xml to:
	 * http://localhost:8080/CustomerReg/webservices/customerrestapp/customer
	 * After doing the post, use a get command to retrieve the customer (and verify that the post was successful).
	 */
	@POST
	@Path("/customer")
	@Produces("application/json, application/xml")
	@Consumes("application/json, application/xml")
	public Response addCustomer(Customer newCustomer) throws SQLException{
		ResponseBuilder respBuilder;
		
		customerService.addCustomer(newCustomer, "defaultPassword");
		respBuilder = Response.status(Status.CREATED);
		respBuilder.entity(newCustomer);
		return respBuilder.build();
	}
	
	/* Test Url:
	 * http://localhost:8080/CustomerReg/webservices/customerrestapp/customer/1
	 * See web.xml file for Jersey configuration
	 */
	@GET
	@Path("/customer/{id}")
	@Produces("application/xml, application/json")
	public Customer getCustomer(@PathParam("id") int id) throws SQLException{
		Customer cust = null;
		
		cust = lookupCustomer(id);
		if (cust == null) {
			throw new UnknownResourceException("Customer id: " + id + " is invalid");
		}
		
		return cust;
	}
	
	/* Test Url:
	 * http://localhost:8080/CustomerReg/webservices/customerrestapp/customer
	 * See web.xml file for Jersey configuration
	 */
	@GET
	@Path("/customer")
	@Produces("application/xml")
	public CustomerList getCustomersList() throws SQLException{
		List<Customer> custList;
		CustomerList listOfCustomers = new CustomerList();
		
		custList = customerService.getCustomersList();
		listOfCustomers.setCustomerList(custList);
		return listOfCustomers;
	}
	
	/* Test Url:  Use HTTP Delete command
	 * http://localhost:8080/CustomerReg/webservices/customerrestapp/customer/1
	 */
	@DELETE
	@Path("/customer/{id}")
	public Response deleteCustomer(@PathParam("id") int id) throws SQLException{
		Customer removedCust;
		ResponseBuilder respBuilder;
		
		removedCust = customerService.deleteClientInfoWithId(id);
		logger.info("sss ---   " + removedCust);
		if (removedCust == null) {
			respBuilder = Response.status(Status.NOT_FOUND);
		} else {
			respBuilder = Response.ok();
		}
		
		return respBuilder.build();
	}
	
	private Customer lookupCustomer(int id) throws SQLException{
		Customer cust;
		
		cust = customerService.getCustomerWithId(id);
		if (cust == null) {
			throw new UnknownResourceException("Customer id: " + id + " is invalid");
		}
		
		return cust;
	}
	
	
	/* This  Url requires a basic Authorization header using an account name and password:
	 * http://localhost:8080/CustomerReg/webservices/customerrestapp/authcustomer/1
	 * See web.xml file for Jersey configuration
	 */
	@GET
	@Path("/authcustomer/{id}")
	@Produces("application/xml, application/json")
	public Customer getCustomerWithAuth(@PathParam("id") int id, @HeaderParam("Authorization") String auth) 
																				throws SQLException{
		Customer cust = null;
		
		logger.debug("Get Request for Customer with Authorization Header");
		cust = lookupCustomerWithAuth(id, auth);
		return cust;
	}
	
	
	/* Only return the customer if the authentication information is correct */
	private Customer lookupCustomerWithAuth(int id, String auth) throws SQLException {
		String actualAcctName, expectedAcctName = "root";   /* Hardcoded test data -- replace with database lookup */
		String actualPasswd, expectedPasswd = "moon";        /* Hardcoded test data -- replace with database lookup */

		CustomerAccount acct = CustomerAccountService.extractAcctFromAuthorization(auth);
		if (acct == null) {
			logger.debug("Authorization Header was null");
			throw new InvalidAcctException("Invalid Authorization Header");
		}
		
		/*
		 * Need to verify this user account by looking up the account info in
		 * the database. Here we just print it and compare against a "hardcoded"
		 * acct name and password.
		 */
		logger.debug("Authorized user found in lookupCustomerWithAuth():  " + acct);
		actualAcctName = acct.getAccountname();
		actualPasswd = acct.getPassword();
		if (!(actualAcctName.equals(expectedAcctName) && actualPasswd
				.equals(expectedPasswd))) {
			throw new InvalidAcctException("Authorization is invalid for account: " + actualAcctName);
		}
		
		return lookupCustomer(id);

	}
	
	
	/* Test Url -- Put (HTTP Put Command) the data from file student.xml to:
	 * http://localhost:8080/CustomerReg/webservices/customerrestapp/student/100
	 * After doing the post, use a get command to retrieve the student (and verify that the post was successful).
	 */
//	@PUT
//	@Path("/student/{id}")
//	@Produces("application/json, application/xml")
//	@Consumes("application/json, application/xml")
//	public Response updateStudent(@PathParam("id") int id, Student newStudent) {
//		ResponseBuilder respBuilder;
//		Student updatedStudent;
//		
//		updatedStudent = studentService.updateStudent(id, newStudent);
//		if (updatedStudent == null) {
//			respBuilder = Response.status(Status.NOT_FOUND);
//		} else {
//			respBuilder = Response.status(Status.OK);
//			respBuilder.entity(updatedStudent);
//		}
//		
//		return respBuilder.build();
//	}

}
