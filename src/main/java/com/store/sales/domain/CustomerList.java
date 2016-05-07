package com.store.sales.domain;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerList implements Serializable {
	private static final long serialVersionUID = 1L;
	// XmlElement sets the name of the entities
	@XmlElement(name = "customer")
	private List<Customer> custList;

	public CustomerList() {
	}
	
	public List<Customer> getCustomerList() {
		return custList;
	}

	public void setCustomerList(List<Customer> newCustomerList) {
		this.custList = newCustomerList;
	}
	
	public int numEntries() {
		if (custList == null) return 0;
		return custList.size();
	}
	
	public Customer getCustomer(int idx) {
		return custList.get(idx);
	}
	
	public String toString() {
		String listStr;
		
		listStr = "CustomerList{";
		for (Customer entry: custList) {
			listStr = listStr + "\n\t" + entry;
		}
		
		listStr = listStr + "\n}";
		return listStr;
	}
}