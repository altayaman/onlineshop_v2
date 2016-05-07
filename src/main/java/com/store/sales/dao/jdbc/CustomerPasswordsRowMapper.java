package com.store.sales.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.store.sales.domain.Customer;
import com.store.sales.domain.CustomerPassword;

public class CustomerPasswordsRowMapper implements RowMapper<CustomerPassword> {

	public CustomerPassword mapRow(ResultSet resultSet, int row) throws SQLException {
		int customer_id;
		String customer_email;
		String customer_password;
		
		customer_id = resultSet.getInt("client_id");
		customer_email = resultSet.getString("client_email");
		customer_password = resultSet.getString("client_password");
		
		CustomerPassword custPassword = new CustomerPassword();
		custPassword.setCustomer_id(customer_id);
		custPassword.setCustomer_email(customer_email);
		custPassword.setCustomer_password(customer_password);
		
		return custPassword;
	}
}