package com.store.sales.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customer")
public class Customer {
		
		private int customer_id;
		private String customer_firstname;
		private String customer_lastname;
		private String customer_email;
		
		public static void main(String[] args){}
		
		public Customer() {
		}
		
		public Customer(int customer_id, String customer_firstname, String customer_lastname, String customer_email) {
			this.customer_id = customer_id;
			this.customer_firstname = customer_firstname;
			this.customer_lastname = customer_lastname;
			this.customer_email = customer_email;
		}
		
		public boolean hasId(int id) {
			return this.customer_id == id;
		}
		
		public int getCustomerId() {
			return customer_id;
		}
		
		public String getCustomerFirstName() {
			return customer_firstname;
		}
		
		public String getCustomerLastName() {
			return customer_lastname;
		}
		
		public String getCustomerEmail() {
			return customer_email;
		}
		
		public void setCustomerId(int customer_id) {
			this.customer_id = customer_id;
		}
		
		public void setCustomerFirstName(String customer_firstname) {
			this.customer_firstname = customer_firstname;
		}
		
		public void setCustomerLastName(String customer_lastname) {
			this.customer_lastname = customer_lastname;
		}
		
		public void setCustomerEmail(String customer_email) {
			this.customer_email = customer_email;
		}
		
		public String toString() {
			return "" + customer_id + ", " + customer_firstname + ", " + customer_lastname + ", " + customer_email; 
		}

	}
