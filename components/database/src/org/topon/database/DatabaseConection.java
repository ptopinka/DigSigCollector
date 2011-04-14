package org.topon.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.topon.configuration.ServerConfiguration;
import org.topon.signatures.XMLSignatureHelper;
import org.w3c.dom.Document;


public class DatabaseConection {

	public static final String TYPE_MYSQL = "mysql";
	public static final String TYPE_ORACLE = "oracle";

	private ServerConfiguration config;

	private Connection dbConnection;

	public DatabaseConection(ServerConfiguration config) {
		this.config = config;
		init();
	}


	private void  init() {
		if(config.getDbType().equals(TYPE_MYSQL)) {
			this.dbConnection = getMySqlConnection();
		}
	}

	public void insertSignatureIntoDatabase() {

	}

	public Connection getMySqlConnection()  {
		Connection conn = null;
		try {
			DriverManager.registerDriver (new org.gjt.mm.mysql.Driver());

			 conn = DriverManager.getConnection
			("jdbc:mysql://"+config.getDbHost()+":"+config.getDbPort()+"/"+config.getDbName()+"?jdbcCompliantTruncation=false",
					config.getDbUser(),
					config.getDbPassword());
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return conn;
	}


	public void closeConnection() {
		try {
			if(!dbConnection.isClosed()) {
				dbConnection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String [] args) throws SQLException {
		ServerConfiguration config =  ServerConfiguration.getInstance();
		System.out.println(config.getDbHost());

		DatabaseConection dbcon = new DatabaseConection(config);
		Connection connection = dbcon.getMySqlConnection();

		Document doc = XMLSignatureHelper.getXMLSignatureFromXMLFile("C:\\work\\fel\\Bakalarka\\sources\\dist\\signatures\\anicka.xml");
		//XMLSignatureHelper.printDOMDocument(doc);
		SignatureModel sigModel = new SignatureModel(doc);
		System.out.println("CN: " +sigModel.getCN());
		System.out.println("login: " +sigModel.getLogin());
		System.out.println("docurl: " +sigModel.getDocumentURL());
		System.out.println("sigid: " +sigModel.getDocid());
		System.out.println(sigModel.getCertificate().getIssuerDN().getName());
		
		boolean inserted = DatabaseHelper.insertSignatureIntoDatabase(connection, sigModel);
		System.out.println("is inserted: " + inserted);
/*
		Statement stmt = connection.createStatement();
        ResultSet rset = stmt.executeQuery("show databases");
        while(rset.next()) {
        	System.out.println(rset.getString(1));
        }
        stmt.close();
*/
        connection.close();
        	
		
		
		/*
		DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());

	        Connection conn = DriverManager.getConnection
	             ("jdbc:oracle:thin:@b207:1521:uddi", "topon", "topon");
	                             // @machineName:port:SID,   userid,  password

	        Statement stmt = conn.createStatement();
	        ResultSet rset = stmt.executeQuery("select BANNER from SYS.V_$VERSION");
	        while (rset.next())
	              System.out.println (rset.getString(1));   // Print col 1
	        stmt.close();

		 */
	}
}
