package com.store.sales.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.store.sales.dao.CustomerDaoI;
import com.store.sales.domain.Customer;

@Service("customerService")
//@Transactional
public class CustomerService implements CustomerServiceI{

	@Autowired
	@Qualifier("customerDaoJdbc")  // Use one qualifier or the other, but not both.  Uncomment Transactional annotation when using database
	CustomerDaoI customerDao;// = new CustomerDaoJdbc();
	
	public Customer clientAuthentication(String email, String password){
		System.out.println("edu.npu.zu.services.CustomerSevice.clientAuthentication method");
		
		Customer cust = customerDao.clientAuthentication(email, password); 
		
		return cust;
	}
	
	public boolean isEmailExists(String email) throws SQLException {
		System.out.println("edu.npu.zu.services.CustomerSevice.isEmailExists method");
		
		return customerDao.isEmailExists(email);
	}
	
	public void insertClientInfo(String fname, String lname, String email, String password) throws SQLException {
		System.out.println("edu.npu.zu.services.CustomerSevice.insertClientInfo method");
		
		customerDao.insertClientInfo(fname, lname, email, password);
	}
	
	public Customer deleteClientInfoWithId(int id) throws SQLException {
		System.out.println("edu.npu.zu.services.CustomerSevice.deleteClientInfoWIthId method");
		
		return customerDao.deleteClientInfoWithId(id);
	}
	
	public List<Customer> getCustomersList() throws SQLException {
		System.out.println("edu.npu.zu.services.CustomerSevice.getClientsInfo method");
		
		return customerDao.getClientsInfo();
	}
	
	public Customer getCustomerWithId(int id) throws SQLException {
		System.out.println("edu.npu.zu.services.CustomerSevice.getClientWithId method");
		
		return customerDao.getCustomerWithId(id);
	}

	public Customer addCustomer(Customer customer, String password) throws SQLException{
		String fname = customer.getCustomerFirstName();
		String lname = customer.getCustomerLastName();
		String email = customer.getCustomerEmail();
		customerDao.insertClientInfo(fname, lname, email, password);
		return customer;
	}
}
