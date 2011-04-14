package org.topon.ldap;

import javax.naming.NamingException;

public class LdapStoreException extends NamingException{

	
	public LdapStoreException(String message, Throwable e) {
		super(message);
		e.printStackTrace();
	}
}
