package org.topon.database;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.topon.configuration.Configuration;
import org.topon.configuration.ServerConfiguration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;
import org.topon.signatures.XMLSignatureHelper;
import org.topon.signatures.apache.DetachedXMLSignatureApache;
import org.topon.signatures.jdk16.DetachedXMLSignatureJDK;
import org.w3c.dom.Document;

import com.mysql.jdbc.Blob;


public class DatabaseHelper {


	/**
	 * Insert New Signature into database
	 * @param connection
	 * @param sigMod
	 * @return true when insert is successful.
	 * @throws SQLException 
	 * @throws SQLException
	 */
	public static boolean insertSignatureIntoDatabase(Connection connection, SignatureModel sigMod) throws SQLException 
	//throws SQLException
	{
		
		UserModel user = getUserWhoSignedDocument(connection, sigMod.getDocid(),sigMod.getLogin());
		if(user != null) {
			throw new SQLException(" user login='"+sigMod.getLogin()+"' already signed this document url='"+sigMod.getDocumentURL()+"'");
		}

		boolean isInserted = false;
		PreparedStatement pstm;
		connection.setAutoCommit(false);
		pstm = connection.prepareStatement("insert into t_signatures "
				+ "(iddoc,CN,login,blobsignature)"
				+ " values (?, ? , ?, ?)");
		pstm.setInt(1, sigMod.getDocid());
		pstm.setString(2, sigMod.getCN());
		pstm.setString(3, sigMod.getLogin());


		/* ulozeni blob do databaze */
		byte[] array = sigMod.getSignatureByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(array);
		pstm.setBinaryStream(4, bais, array.length);

		pstm.executeUpdate();
		connection.commit();
		isInserted = true;
		return isInserted;
	}
	/**
	 * Insert new document into Database
	 * @param connection
	 * @param docMod
	 * @return
	 * @throws SQLException
	 */
	public static boolean insertDocumentIntoDatabase(Connection connection, DocumentModel docMod)
	throws SQLException{
		boolean isInserted = false;
		String docUrl = docMod.getDocURL();
		int docID = getDocumentIdfromDatabase(connection, docUrl);
		if(docID > 0) {
			isInserted = false;
			throw new DatabaseException("Document with url="+docUrl+ " already in database");
			
		} else {
			PreparedStatement pstm;
			connection.setAutoCommit(false);
			pstm = connection.prepareStatement("insert into t_documents " +
					"(docurl,validstart,validend,description)" +
			" values (?, ? , ?, ?)");
			pstm.setString(1,docMod.getDocURL());
			java.sql.Date start =  new java.sql.Date(docMod.getValidStart());//(java.sql.Date)docMod.getValidStart();
			java.sql.Date end =  new java.sql.Date(docMod.getValidEnd());

			pstm.setDate(2,start);
			pstm.setDate(3, end);
			pstm.setString(4, docMod.getDescription());
			pstm.executeUpdate();
			connection.commit();
			isInserted = true;		
			pstm.close();
		}
		return isInserted;
	}

	
	/**
	 * Update document info in the database 
	 * @param connection
	 * @param docMod
	 * @return
	 * @throws SQLException
	 */
	public static boolean updateDocumentInDatabase(Connection connection, DocumentModel docMod)
	throws SQLException{
		boolean isUpdated = false;
		String docUrl = docMod.getDocURL();
		int docID = getDocumentIdfromDatabase(connection, docUrl);
		if(docID != docMod.getDocid()) {
			isUpdated = false;
			throw new DatabaseException("Document with url="+docUrl+ " and id="+docMod.getDocid()+" is not  in the database");
			
		} else {
			PreparedStatement pstm;
			connection.setAutoCommit(false);
			pstm = connection.prepareStatement(
			  "UPDATE t_documents SET docurl=?,validstart=?,validend=?,description=?" +
			  " WHERE docid=?"); 
			pstm.setString(1,docMod.getDocURL());        // Assign value to first parameter     2 
			
			java.sql.Date start =  new java.sql.Date(docMod.getValidStart());//(java.sql.Date)docMod.getValidStart();
			java.sql.Date end =  new java.sql.Date(docMod.getValidEnd());

			
			pstm.setDate(2,start);      // Assign value to second parameter 
			pstm.setDate(3,end);
			pstm.setString(4, docMod.getDescription());
			pstm.setInt(5, docMod.getDocid());
			int numUpd = pstm.executeUpdate(); 
			connection.commit();
			pstm.close();
			if(numUpd == 1) {
				isUpdated = true;
			}
		}
		return isUpdated;
	}	
	
	
	/**
	 * Delete document from t_documents by given URL
	 * @param connection
	 * @param docURL
	 * @return
	 * @throws SQLException
	 */
	public static boolean deleteDocumentFromDatabase(Connection connection, String docURL)
		throws SQLException
	{
		boolean isDeleted = false;
		int docid = getDocumentIdfromDatabase(connection, docURL);
		isDeleted = deleteDocumentFromDatabase(connection ,docid);
		return isDeleted;
	}

	/**
	 * Deleted document from database by given docid (primary key)
	 * @param connection
	 * @param docid - primary key 
	 * @return true when some lines were deleted 
	 * @throws SQLException
	 */
	public static boolean deleteDocumentFromDatabase(Connection connection, int docid)
		throws SQLException
	{
		boolean isdeleted = false;
		Statement stmt = connection.createStatement();
		//TODO jmena tabulek do konfigurace
		
		String sql = "delete from t_documents where docid = '"+docid+"'"; 
		int iisdel = stmt.executeUpdate(sql);
		if(iisdel >0)
			isdeleted = true;
		return isdeleted;
	}
	
	
	
	/** Get document ID from database by given URL ( which is unique) */
	public static int getDocumentIdfromDatabase(Connection connection, String docURL) {
		int docId = 0;

		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery("select docid from t_documents where docurl = '"+docURL+"'");
			while(rset.next()) {
				docId = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(stmt != null) {
				try {
					stmt.close();
					
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}
		}

		return docId;
	}


	
	
	
	
	/** Return all documents that are available in Database to sign*/
	public static Vector getDocumentsToSign(Connection connection) 
	throws SQLException {
		Vector vector = new Vector();
		Statement stmt  = connection.createStatement();
		stmt = connection.createStatement();
		ResultSet rset = stmt.executeQuery("select docid,docurl,validstart,validend,description from t_documents");
		while(rset.next()) {
			DocumentModel model = new DocumentModel();
			model.setDocid(rset.getInt(1));
			model.setDocURL(rset.getString(2));
			model.setValidStart(rset.getTimestamp(3).getTime());
			model.setValidEnd(rset.getTimestamp(4).getTime());
			model.setDescription(rset.getString(5));
			vector.add(model);
		}
		return vector;

	}

	public static UserModel getUserWhoSignedDocument (
			Connection connection,int docid, String uid)
	throws SQLException{
		UserModel user = null;
		Statement stmt  = connection.createStatement();
		stmt = connection.createStatement();
		ResultSet rset = stmt.executeQuery("select idpodpis,login,blobsignature from t_signatures where iddoc="+docid+" and login='"+uid+"'");
		ResultSetMetaData rsmd = rset.getMetaData();
    	//rsmd.
		if(rset.next()){
			
			user = new UserModel();
			user.setIdpodpis(rset.getInt(1));
			user.setUid(rset.getString(2));
			
			byte[] signBytes = rset.getBytes(3);
			ByteArrayInputStream in = new ByteArrayInputStream(signBytes);
			Document domSignature = XMLSignatureHelper.getXMLDocumentFromInputStream(in);
		try {
				X509Certificate cert =  
					new DetachedXMLSignatureApache().getX509Certificate(domSignature);
				user.setCertificate(cert);
			} catch (XMLSecurityException e) {
				throw new DatabaseException("Cannot parse X509Certificate from store XMLSignature in database",e);
			} catch (ParserConfigurationException e) {
				throw new DatabaseException("Cannot parse X509Certificate from store XMLSignature in database",e);
			}
			
		}
		
		return user;
	}
	
	
	public static Vector getUsersWhoSignDocument(Connection connection,int docid)
	throws SQLException {
		Vector vector = new Vector();
		Statement stmt  = connection.createStatement();
		stmt = connection.createStatement();
		ResultSet rset = stmt.executeQuery("select idpodpis,login,blobsignature from t_signatures where iddoc="+docid);
		while(rset.next()) {
			UserModel user = new UserModel();
			user.setIdpodpis(rset.getInt(1));
			user.setUid(rset.getString(2));
			
			byte[] signBytes = rset.getBytes(3);
			ByteArrayInputStream in = new ByteArrayInputStream(signBytes);
			Document domSignature = XMLSignatureHelper.getXMLDocumentFromInputStream(in);
		try {
				X509Certificate cert =  
					new DetachedXMLSignatureApache().getX509Certificate(domSignature);
				user.setCertificate(cert);
			} catch (XMLSecurityException e) {
				throw new DatabaseException("Cannot parse X509Certificate from store XMLSignature in database",e);
			} catch (ParserConfigurationException e) {
				throw new DatabaseException("Cannot parse X509Certificate from store XMLSignature in database",e);
			}
			
			
			vector.add(user);
		}
		return vector;
	}
	
	
	
	/** Return all documents that are available in Database to sign*/
	public static DocumentModel getDocumentById( int docid) 
	 {
		Connection connection = 
			new DatabaseConection(ServerConfiguration.getInstance()).getMySqlConnection();
		DocumentModel model = new DocumentModel();
		try {
			Statement stmt  = connection.createStatement();
			stmt = connection.createStatement();
			ResultSet rset = 
				stmt.executeQuery("select docid,docurl,validstart,validend,description,filetype,hash,type,filename from t_documents where docid = "+docid);
			if(rset.first()) {
				
				model.setDocid(rset.getInt(1));
				model.setDocURL(rset.getString(2));
				model.setValidStart(rset.getTimestamp(3).getTime());
				model.setValidEnd(rset.getTimestamp(4).getTime());
				model.setDescription(rset.getString(5));
				model.setFiletype(rset.getString(6));
				model.setHash(rset.getString(7));
				model.setType(rset.getString(8));
				model.setFilename(rset.getString(9));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return model;

	}


	public static Vector getDocumentsToSign() {
		Connection connection = 
			new DatabaseConection(ServerConfiguration.getInstance()).getMySqlConnection();
		Vector vector = new Vector();
		
			try {
				vector = DatabaseHelper.getDocumentsToSign(connection);
				Document doc = XMLSignatureHelper.createXMLWithDocuments(vector);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return vector;
		
		
		
	}
	
	
	

	public static void  toString(DocumentModel docMod) {
		System.out.println(docMod.getDocURL());
		System.out.println(docMod.getDescription());
		System.out.println(docMod.getValidStart());
		System.out.println(docMod.getValidEnd());
		
	}
	

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, MarshalException, XMLSignatureException, ParserConfigurationException, SQLException {

		Vector documents = DatabaseHelper.getDocumentsToSign();
		Iterator itr = documents.iterator();
		 
	    //use hasNext() and next() methods of Iterator to iterate through the elements
	    System.out.println("Iterating through Vector elements...");
	    while(itr.hasNext())
	      System.out.println(itr.next());
		
		
		
		IdentityInterface identity = IdentityFactory.createIdentity(Configuration.getInstance());
		DetachedXMLSignatureJDK sign = new DetachedXMLSignatureJDK();
		Document signature = sign.createDetachedSignature("http://amateri.cz",
				identity.getPrivateKey(),
				identity.getX509Cetificate());
		SignatureModel sigMod = new SignatureModel(signature);
		System.out.println("signatureModel...."+ sigMod.getDocumentURL() + "..."+ sigMod.getDocid());
		Connection con = (new  DatabaseConection(ServerConfiguration.getInstance())).getMySqlConnection();
		boolean isinserted =false;
//		try {
			isinserted = DatabaseHelper.insertSignatureIntoDatabase(con, sigMod);
//		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("is inserted " + isinserted);

		
		/*
*/		

		/*		
		Connection con = (new  DatabaseConection(ServerConfiguration.getInstance())).getMySqlConnection();
		DocumentModel docMod = DatabaseHelper.getDocumentById(con, 9);
		DatabaseHelper.toString(docMod);
		docMod.setDescription("signature store Servlet for manipulating Documents" );
		boolean isChanged = DatabaseHelper.updateDocumentInDatabase(con, docMod);
		DatabaseHelper.toString(docMod);
*/		

		/*
		con = (new  DatabaseConection(ServerConfiguration.getInstance())).getMySqlConnection();
		Vector users = DatabaseHelper.getUsersWhoSignDocument(con, 3);
		for(int i = 0; i < users.size();i++){
		UserModel user = (UserModel)users.get(i);
		System.out.println(user.getUid());
		System.out.println(user.getCertificate());
		}
		
		
		UserModel user = getUserWhoSignedDocument(con,3,"anicka@fel.cvut.cz");
		System.out.println(user);
	*/
		
		/*		Connection con = (new  DatabaseConection(ServerConfiguration.getInstance())).getMySqlConnection();
		Vector vector = DatabaseHelper.getDocumentsToSign(con);
		Iterator it = vector.iterator();
		while(it.hasNext()) {
			DocumentModel mod = (DocumentModel)it.next();
			System.out.println(mod.getDocid()+ "  " + mod.getDocURL() + " "+ mod.getValidStart() +  " " +mod.getValidEnd() );
		}
		Document doc = XMLSignatureHelper.createXMLWithDocuments(vector);
		XMLSignatureHelper.printDOMDocument(doc);

		

		DocumentModel docMod = (DocumentModel)vector.get(0);
		docMod.setDocURL("http://localhost:8080/ejbca/");
		docMod.setValidStart(new Date().getTime());
		docMod.setDescription("hlavni stranka EJC Certification Authority");
		GregorianCalendar calendar= new GregorianCalendar();
		calendar.set(Calendar.YEAR, 2007);
		calendar.set(Calendar.MONTH,11);
		calendar.set(Calendar.DAY_OF_MONTH,31);
		Date validEnd = calendar.getTime();

		docMod.setValidEnd(validEnd.getTime());
		Connection conn = (new  DatabaseConection(ServerConfiguration.getInstance())).getMySqlConnection();

		boolean inserted = DatabaseHelper.insertDocumentIntoDatabase(conn, docMod);
		System.out.println("new document with url "+docMod.getDocURL() + " is inserted="+inserted);
*/
/*
		boolean isdeleted = DatabaseHelper.deleteDocumentFromDatabase(conn, docMod.getDocURL());


		System.out.println("document with url "  + docMod.getDocURL() + "is deleted="+isdeleted );
*/
	}

}
