package com.store.sales.domain;

public class CustomerAccount {
	private int id;
	private String accountname;
	private String password;
	
	public CustomerAccount() {
	}
	
	public String getAccountname() {
		return accountname;
	}
	
	public void setAccountname(String name) {
		this.accountname = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean hasPassword(String testPswd) {
		return testPswd.equals(password);
	}
	
	public String toString() {
		return "Account[ User: " + accountname + "  password: " + password + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
