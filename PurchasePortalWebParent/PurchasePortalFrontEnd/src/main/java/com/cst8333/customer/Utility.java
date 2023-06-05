package com.cst8333.customer;

import javax.servlet.http.HttpServletRequest;

public class Utility {

	public static String getEmailOfAuthenticatedCustomer(HttpServletRequest request) {
		
		String customerEmail = request.getUserPrincipal().getName();
	
		return customerEmail;
	}	
}
