package com.store.sales.services;

import javax.xml.bind.DatatypeConverter;

import com.store.sales.domain.CustomerAccount;

public class CustomerAccountService {
	static public CustomerAccount extractAcctFromAuthorization(String auth) {
		
		if (auth == null || auth.length() == 0) {
			return null;
		}

		/* Get the Basic authorization */
		auth = auth.replaceFirst("[B|b]asic ", "");

		// Decode the Base64 into byte[]
		byte[] decodedBytes = DatatypeConverter.parseBase64Binary(auth);

		// If the decode fails in any case
		if (decodedBytes == null || decodedBytes.length == 0) {
			return null;
		}

		// First elem is user name
		// Second elem is password
		String loginInfo[];
		loginInfo = new String(decodedBytes).split(":", 2);
		CustomerAccount acct = new CustomerAccount();
		acct.setAccountname(loginInfo[0]);
		acct.setPassword(loginInfo[1]);
		return acct;
	}
}
