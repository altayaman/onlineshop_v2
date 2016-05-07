package com.store.sales.dao;

import java.sql.SQLException;
import java.util.List;

import com.store.sales.domain.Customer;

public interface CustomerDaoI {
	public Customer clientAuthentication(String email, String password);
	public boolean isEmailExists(String email) throws SQLException;
	public void insertClientInfo(String fname, String lname, String email, String password) throws SQLException;
	public Customer deleteClientInfoWithId(int id) throws SQLException;
	public List<Customer> getClientsInfo() throws SQLException;
	public Customer getCustomerWithId(int id) throws SQLException;
}