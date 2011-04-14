package org.topon.signatures.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.topon.configuration.Configuration;
import org.topon.configuration.ServerConfiguration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;
import org.topon.database.DatabaseConection;
import org.topon.database.DatabaseHelper;
import org.topon.database.SignatureModel;
import org.topon.signatures.DSigCollectorSignatureHelper;
import org.topon.signatures.XMLSignatureHelper;
import org.topon.signatures.interfaces.XMLSignatureInt;
import org.topon.signatures.jdk16.DetachedXMLSignatureJDK;
import org.topon.timestamp.TimeStampHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;



public class SignatureStore extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		//get web.xml for display by a servlet
		String file = "/WEB-INF/web.xml";




		URL url = null;
		URLConnection urlConn = null;
		PrintWriter out = null;
		BufferedInputStream buf = null;
		try {
			out = response.getWriter();
			url = getServletContext().getResource(file);
			//set response header
			//response.setContentType("text/xml");
			response.setContentType("text/html");

			urlConn = url.openConnection();
			//establish connection with URL presenting web.xml
			urlConn.connect();
			buf = new BufferedInputStream(urlConn.getInputStream());
			int readBytes = 0;
			out.write("<h1>Signature Store</h1>");
			out.write("<p>This Servlet Serve to Store Digital Signatures</p>");
			/*
			while ((readBytes = buf.read()) != -1)
				out.write(readBytes);
			*/
			
		} catch (MalformedURLException mue) {
			throw new ServletException(mue.getMessage());
		} catch (IOException ioe) {
			throw new ServletException(ioe.getMessage());
		} finally {
			if (out != null)
				out.close();
			if (buf != null)
				buf.close();
		}
	}




	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {


		InputStream in = request.getInputStream();
		Document xmlSignature = XMLSignatureHelper.getXMLDocumentFromInputStream(in);
		
		
		boolean isOK = DSigCollectorSignatureHelper.verifyDSigColSignature(xmlSignature);
		System.out.println("DEBUG: [DSIGCOLLECTOR] Verifikace prijate XML Signatury OK/KO?: " + isOK);
				
		boolean isTSPOK = TimeStampHelper.verifyTimestampInXMLSignature(xmlSignature);
		if(isTSPOK) {
			String xmlSignatureString = XMLSignatureHelper.getSignatureStringFromInputStream(in);
			
			SignatureModel sigMod = new SignatureModel(xmlSignature,xmlSignatureString); 
			
			DatabaseConection dbCon = new DatabaseConection(ServerConfiguration.getInstance());
			Connection connection = dbCon.getMySqlConnection();
			
	   		
			try {
				if(xmlSignature != null) {
					DetachedXMLSignatureJDK dxsj = new DetachedXMLSignatureJDK();
					boolean isok = dxsj.verifyDetachedSignature(null, xmlSignature);
				//	System.out.println("Signature is OK? "+ isok);
				} else {
				//	System.out.println("Doc is null");
				}
			} catch (MarshalException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (XMLSignatureException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			try {
				
				DatabaseHelper.insertSignatureIntoDatabase(connection, sigMod);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				in.close();
			}
			dbCon.closeConnection();			
		}
		

		


	}
}