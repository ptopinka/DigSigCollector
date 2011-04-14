package org.topon.database.tests;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.topon.configuration.Configuration;
import org.topon.configuration.ServerConfiguration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;
import org.topon.database.DatabaseConection;
import org.topon.database.DatabaseHelper;
import org.topon.database.DocumentModel;
import org.topon.database.SignatureModel;
import org.topon.signatures.XMLSignatureHelper;
import org.topon.signatures.jdk16.DetachedXMLSignatureJDK;
import org.w3c.dom.Document;

public class DatabaseTests extends TestCase{
	
	public void testDatabase() throws SQLException {

		
		
		Vector documents = DatabaseHelper.getDocumentsToSign();
		Iterator itr = documents.iterator();
		 
	    //use hasNext() and next() methods of Iterator to iterate through the elements
	    System.out.println("Iterating through Vector elements...");
	    while(itr.hasNext())
	      System.out.println(itr.next());
		
		
		
		IdentityInterface identity = IdentityFactory.createIdentity(Configuration.getInstance());
		DetachedXMLSignatureJDK sign = new DetachedXMLSignatureJDK();
		Document signature = null;
		try {
			signature = sign.createDetachedSignature("http://amateri.cz",
					identity.getPrivateKey(),
					identity.getX509Cetificate());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MarshalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLSignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertNotNull(signature);
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
		assertTrue(isinserted);
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
		
		 con = (new  DatabaseConection(ServerConfiguration.getInstance())).getMySqlConnection();
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
		assertTrue(inserted);


		boolean isdeleted = DatabaseHelper.deleteDocumentFromDatabase(conn, docMod.getDocURL());
		assertTrue(isdeleted);


		
		
		
		
		Configuration config = Configuration.getInstance();
		String jmenoKey = config.getKeystore();
		assertEquals("jmeno keystoru neni stejne", jmenoKey, "PavelTopinka.p12");
		assertEquals("Spatny typkeystoru",config.getKeystoreType(),"p12");
		assertEquals("Spatne nactene heslo" ,config.getPassword(),"changeit");
		assertEquals("Nespravne URL Sig Store", config.getSignatureStoreURL(),"https://localhost:8443/dsigcollector");

		
		
	}
}