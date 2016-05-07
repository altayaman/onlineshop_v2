package com.store.sales.domain;

public class CustomerPassword {

	private int customer_id;
	private String customer_email;
	private String customer_password;
	
	public CustomerPassword(){
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	public String getCustomer_password() {
		return customer_password;
	}

	public void setCustomer_password(String customer_password) {
		this.customer_password = customer_password;
	}

	@Override
	public String toString() {
		return "CustomerPassword [customer_id=" + customer_id + ", customer_email=" + customer_email
				+ ", customer_password=" + customer_password + "]";
	}
	
}
