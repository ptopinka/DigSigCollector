package org.topon.configuration;

public class ServerConfigurationProperties {

	public static final String DATABASE_TYPE = "mysql";
	public static final String DATABASE_HOST = "127.0.0.1";
	public static final String DATABASE_PORT = "3306";
	public static final String DATABASE_NAME = "xmlsignatures";
	public static final String DATABASE_USER = "xmlsignatures";
	public static final String DATABASE_PASSWORD ="changeit";
	
	public static final String LDAP_ADMIN_USER ="uid=topon,ou=topon,dc=in,dc=weegee,dc=com";
	public static final String LDAP_ADMIN_PASSWORD="changeit";
	public static final String LDAP_SEARCH_BASE_GROUP="ou=CVUT,dc=in,dc=systinet,dc=com";
	public static final String LDAP_SEARCH_FILTER_USER="objectClass=Person";
	public static final String LDAP_PROVIDER_URL="ldap://localhost:10339";
	public static final String TSA_AUTHORITY_URL="http://timestamping.edelweb.fr/service/tsp";
	
	

}
