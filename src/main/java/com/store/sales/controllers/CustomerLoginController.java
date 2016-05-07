package com.store.sales.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.store.sales.domain.Customer;
import com.store.sales.services.CustomerServiceI;

@Controller
public class CustomerLoginController {

private static final Logger logger = LoggerFactory.getLogger(CustomerLoginController.class);
	
	@Autowired
	@Qualifier("customerService")
	private CustomerServiceI custService;

	HttpSession session;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String indexPage() {
		logger.info("Index page: GET method");
		
		return "index";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String login(@RequestParam String email, 
			            @RequestParam String password, 
			            Model model, 
			            HttpServletRequest request) {
		
		logger.info("Index page: POST method");
		
		Customer customer = custService.clientAuthentication(email, password);
		
		if(customer == null){
			return "index";
		}
		
		session = request.getSession();
		session.setAttribute("clientName" , customer.getCustomerFirstName());
		session.setAttribute("email" , customer.getCustomerEmail());
		
		model.addAttribute("clientName", customer.getCustomerFirstName());
		return "loginStatus";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		logger.info("Logout method invoked");
		
		return "logout";
	}
	
	@RequestMapping(value = "/clientRegistration", method = RequestMethod.GET)
	public String clientRegistrationPage() {
		logger.info("clientRegistration page: GET");
		
		return "clientRegistration";
	}
	
	@RequestMapping(value = "/clientRegistration", method = RequestMethod.POST)
	public String clientRegistration(@RequestParam String firstName, 
			                         @RequestParam String lastName, 
			                         @RequestParam String email, 
			                         @RequestParam String password, 
			                         Model model) throws SQLException{
		logger.info("clientRegistration page: POST");
		String registrationStatusMessage;
		
		if(custService.isEmailExists(email) == true){
			registrationStatusMessage = "Sorry, email you entered already exists in our database";
		} else if(firstName.equals("") || lastName.equals("") || email.equals("") || password.equals("")){
			registrationStatusMessage = "You did not fill all the fields, try again please";
		} else {
			registrationStatusMessage = "Welcome " + firstName + ", you are registered successfully";
			custService.insertClientInfo(firstName, lastName, email, password);
		}
		
		model.addAttribute("registrationStatusMessage", registrationStatusMessage);
		
		return "clientRegistrationStatus";
	}
	
	@RequestMapping(value = "/customersList", method = RequestMethod.GET)
	public ModelAndView getClientsList(ModelAndView model) throws SQLException {
		List<Customer> customersList = custService.getCustomersList();
		
		model.addObject("names", customersList);
		
		for(Customer cust : customersList){
			System.out.println(cust.getCustomerEmail());
		}
		
		
		return model;
	} 
}
