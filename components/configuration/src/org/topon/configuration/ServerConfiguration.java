package org.topon.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ServerConfiguration {

	private String dbType;
	private String dbName;
	private String dbHost;
	private String dbPort;
	private String dbPassword;
	private String dbUser;


	private String ldapAdminUser;
	private String ldaAdminPassword;
	private String ldapSearchBaseGroup;
	private String ldapSearchfilterUser;
	private String ldapProviderUrl;

	private String tspServer;
	
	private static final String SERVER_PROPERTIES_FILE = "server.properties";


	private String serverURL;

	private Properties props;
	private static ServerConfiguration config;

	private ServerConfiguration() {

	}

	public static ServerConfiguration getInstance() {
		if(config == null) {
			config = new ServerConfiguration();
			config.loadConfiguration();
		}

		return config;
	}


	private void  loadConfiguration() {
		props = new Properties();

		File config = new File("conf"+File.separator+ SERVER_PROPERTIES_FILE);
		/*
		 * TODO - remove HACK with server.properties. Problem is that in war+jar
		 * I cannot find conf/server.properties file
		 */
		if(config.exists()) {
			try {
				props.load(new FileInputStream("conf"+File.separator+ SERVER_PROPERTIES_FILE));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			props.setProperty("database.type", ServerConfigurationProperties.DATABASE_TYPE);
			props.setProperty("database.host", ServerConfigurationProperties.DATABASE_HOST);
			props.setProperty("database.port", ServerConfigurationProperties.DATABASE_PORT);
			props.setProperty("database.name", ServerConfigurationProperties.DATABASE_NAME);
			props.setProperty("database.user", ServerConfigurationProperties.DATABASE_USER);
			props.setProperty("database.password", ServerConfigurationProperties.DATABASE_PASSWORD);

			props.setProperty("ldap.admin.user",ServerConfigurationProperties.LDAP_ADMIN_USER);
			props.setProperty("ldap.admin.password",ServerConfigurationProperties.LDAP_ADMIN_PASSWORD);
			props.setProperty("ldap.search.base.group",ServerConfigurationProperties.LDAP_SEARCH_BASE_GROUP);
			props.setProperty("ldap.searchfilter.user",ServerConfigurationProperties.LDAP_SEARCH_FILTER_USER);
			props.setProperty("ldap.provider.url",ServerConfigurationProperties.LDAP_PROVIDER_URL);
		}
		getProperties(props);
	}

	/**
	 * Get properties from initialized Properties
	 * @param props
	 */
	private void getProperties(Properties props) {
		dbType = props.getProperty("database.type");
		dbHost = props.getProperty("database.host");
		dbPort = props.getProperty("database.port");
		dbName = props.getProperty("database.name");
		dbUser = props.getProperty("database.user");
		dbPassword = props.getProperty("database.password");
		ldapAdminUser = props.getProperty("ldap.admin.user",ServerConfigurationProperties.LDAP_ADMIN_USER);
		ldaAdminPassword = props.getProperty("ldap.admin.password",ServerConfigurationProperties.LDAP_ADMIN_PASSWORD);
		ldapSearchBaseGroup = props.getProperty("ldap.search.base.group",ServerConfigurationProperties.LDAP_SEARCH_BASE_GROUP);
		ldapSearchfilterUser = props.getProperty("ldap.searchfilter.user",ServerConfigurationProperties.LDAP_SEARCH_FILTER_USER);
		ldapProviderUrl = props.getProperty("ldap.provider.url",ServerConfigurationProperties.LDAP_PROVIDER_URL);
		tspServer = props.getProperty("server.url.tsa",ServerConfigurationProperties.TSA_AUTHORITY_URL);
	}

	public static ServerConfiguration getConfig() {
		return config;
	}

	public static void setConfig(ServerConfiguration config) {
		ServerConfiguration.config = config;
	}

	public String getDbHost() {
		return dbHost;
	}

	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public Properties getProps() {
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	public String getLdapAdminUser() {
		return ldapAdminUser;
	}

	public void setLdapAdminUser(String ldapAdminUser) {
		this.ldapAdminUser = ldapAdminUser;
	}

	public String getLdaAdminPassword() {
		return ldaAdminPassword;
	}

	public void setLdaAdminPassword(String ldaAdminPassword) {
		this.ldaAdminPassword = ldaAdminPassword;
	}

	public String getLdapSearchBaseGroup() {
		return ldapSearchBaseGroup;
	}

	public void setLdapSearchBaseGroup(String ldapSearchBaseGroup) {
		this.ldapSearchBaseGroup = ldapSearchBaseGroup;
	}

	public String getLdapSearchfilterUser() {
		return ldapSearchfilterUser;
	}

	public void setLdapSearchfilterUser(String ldapSearchfilterUser) {
		this.ldapSearchfilterUser = ldapSearchfilterUser;
	}

	public String getLdapProviderUrl() {
		return ldapProviderUrl;
	}

	public void setLdapProviderUrl(String ldapProviderUrl) {
		this.ldapProviderUrl = ldapProviderUrl;
	}



}
