package com.store.sales.dao.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import com.store.sales.dao.CustomerDaoI;
import com.store.sales.domain.Customer;
import com.store.sales.domain.CustomerPassword;

@Repository("customerDaoJdbc")
//@Transactional
public class CustomerDaoJdbc implements CustomerDaoI{
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate dbTemplate;
	private SimpleJdbcInsert jdbcInsert;
	
	private boolean clientExists = false;
	private CustomerRowMapper customerRowMapper;
	private CustomerPasswordsRowMapper customerPasswordsRowMapper;

	
	@PostConstruct
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dbTemplate = new NamedParameterJdbcTemplate(dataSource);
		customerRowMapper = new CustomerRowMapper();
		customerPasswordsRowMapper = new CustomerPasswordsRowMapper();
		jdbcInsert = new SimpleJdbcInsert(dataSource)
		                 .withTableName("clients_info")
		                 .usingGeneratedKeyColumns("client_id")
		                 .usingColumns("client_firstname", "client_lastname", "client_email");
	}

	public Customer getCustomerByEmail(String email) {
		String sql = "SELECT * FROM CLIENTS_INFO WHERE client_email = :email";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", email);
		List<Customer> matchingCustomers = dbTemplate.query(sql, params, customerRowMapper);
		
		if (matchingCustomers.size() == 0) {
			System.out.println("getCustomerByEmail method: matchingCustomers.size() == 0");
			return null;
		}
		
		System.out.println("1 - getCustomerByEmail method: " + matchingCustomers.get(0));
		return matchingCustomers.get(0);
	}
	
	public Customer clientAuthentication(String email, String password) {
		String sql = "SELECT * FROM CLIENTS_PASSWORDS WHERE client_email = :email AND client_password = :password";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", email);
		params.addValue("password", password);
		List<CustomerPassword> matchingPasswords = dbTemplate.query(sql, params, customerPasswordsRowMapper);
		
		System.out.println("edu.npu.zu.dao.jdbc.CustomerDaoJdbc.clientAuthentication method");

		if (matchingPasswords.size() == 0) {
			System.out.println("clientAuthentication method: matchingPasswords.size() = 0");
			return null;
		}
		
		System.out.println("2 - clientAuthentication method: " + matchingPasswords.get(0));
		clientExists = true;
		return getCustomerByEmail(email);
	}
	
	public boolean isEmailExists(String email) throws SQLException {
		boolean exists = false;
		
		System.out.println(email);
		
		String sql = "SELECT * FROM CLIENTS_INFO WHERE CLIENT_EMAIL = :email";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("email", email);
		List<Customer> emails = dbTemplate.query(sql, params, customerRowMapper);
		
		if(emails.size() > 0){
			exists = true;
			System.out.println("4 - isEmailExists method: email exists and size is " + emails.size());
		}
		
		System.out.println("4 - isEmailExists method: ");
		return exists;
	}
	
	public Customer deleteClientInfoWithId(int id) throws SQLException{
		Customer cust = null;
		cust = getCustomerWithId(id);
		System.out.println("sss");
		System.out.println(cust);
		if(cust != null){
			String stmt1 = "delete from clients_info where client_id = :id";
			String stmt2 = "delete from clients_passwords where client_id = :id";
			
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("id", id);
			dbTemplate.update(stmt1, params);
			dbTemplate.update(stmt2, params);
			
			return cust;
		} else {
			return cust;
		}
	}
	
	public void insertClientInfo(String fname, String lname, String email, String password) throws SQLException {
		String insert_into_clients_info_tbl = "INSERT INTO CLIENTS_INFO (CLIENT_FIRSTNAME, CLIENT_LASTNAME, CLIENT_EMAIL) "
				                            + "VALUES (:client_firstName, :client_lastName, :client_email)";
		String get_client_id = "SELECT * FROM CLIENTS_INFO "
				             + "WHERE CLIENT_FIRSTNAME = :client_firstName AND "
				             + "CLIENT_LASTNAME = :client_lastName AND "
				             + "CLIENT_EMAIL = :client_email";
		String insert_into_clients_passwords_tbl = "INSERT INTO CLIENTS_PASSWORDS (CLIENT_ID, CLIENT_EMAIL, CLIENT_PASSWORD) "
				                                 + "VALUES (:client_id, :client_email, :client_password)";
		
		
		// Insert new customer info Customer_info table
		MapSqlParameterSource params_1 = new MapSqlParameterSource();
		params_1.addValue("client_firstName", fname);
		params_1.addValue("client_lastName", lname);
		params_1.addValue("client_email", email);
		dbTemplate.update(insert_into_clients_info_tbl, params_1);
		
		// Get new customer id from Clients_Info table
		List<Customer> matchingCustomers = dbTemplate.query(get_client_id, params_1, customerRowMapper);
		
		int clientId = 0;
		for(Customer cust : matchingCustomers) {
			clientId = cust.getCustomerId();
		}
		
		// INsert new customer password and his new id into Clients_Passwords table
		MapSqlParameterSource params_2 = new MapSqlParameterSource();
		params_2.addValue("client_id", clientId);
		params_2.addValue("client_email", email);
		params_2.addValue("client_password", password);
		dbTemplate.update(insert_into_clients_passwords_tbl, params_2);
		
	}
	
	public List<Customer> getClientsInfo() throws SQLException {
		String stmt = "SELECT * FROM CLIENTS_INFO";
		
		List<Customer> customerList = dbTemplate.query(stmt, customerRowMapper);
		
		return customerList;
	}
	
	public Customer getCustomerWithId(int id) throws SQLException{
		String stmt = "SELECT * FROM CLIENTS_INFO";
		
		List<Customer> customerList = dbTemplate.query(stmt, customerRowMapper);
		
		Customer cust = null;
		for(Customer customer : customerList){
			if(customer.getCustomerId() == id)
				cust = customer;
		}
		
		return cust;
	}
	
	public DriverManagerDataSource getDataSource() {
		  DriverManagerDataSource dataSource = new DriverManagerDataSource();
		  dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		  dataSource.setUrl("jdbc:mysql://localhost:3306/phones_db");
		  dataSource.setUsername("root");
		  dataSource.setPassword("moon");
		  return dataSource;
	}

	
}
