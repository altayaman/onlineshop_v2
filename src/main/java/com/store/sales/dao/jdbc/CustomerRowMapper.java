package com.store.sales.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.store.sales.domain.Customer;

public class CustomerRowMapper implements RowMapper<Customer> {

	public Customer mapRow(ResultSet resultSet, int row) throws SQLException {
		int customer_id;
		String customer_firstName;
		String customer_lastName;
		String customer_email;
		Customer cust;
		
		customer_id = resultSet.getInt("client_id");
		customer_firstName = resultSet.getString("client_firstname");
		customer_lastName = resultSet.getString("client_lastname");
		customer_email = resultSet.getString("client_email");
		
		cust = new Customer();
		cust.setCustomerId(customer_id);
		cust.setCustomerFirstName(customer_firstName);
		cust.setCustomerLastName(customer_lastName);
		cust.setCustomerEmail(customer_email);
		
		return cust;
	}

}