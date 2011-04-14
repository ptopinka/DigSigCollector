package org.topon.signatures.servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.topon.configuration.ServerConfiguration;
import org.topon.database.DatabaseConection;
import org.topon.database.DatabaseHelper;
import org.topon.database.DocumentModel;
import org.topon.signatures.XMLSignatureHelper;
import org.w3c.dom.Document;

public class DocumentsStore extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		// TODO Na zaklade get parametru vyplivnout spravy obsah type=xml|html
		//zde to vrati xml ktery bude obsahovat seznam dokumentu ktery je mozno
		//podepsat
		/*
		 * <documents>
		 * 	<doc id="1" url="http://localhost...." validstart="" validend="">
		 *  	<metadata>
		 *  		<description text="skoleni bezpecnosti prace v laboratorich">
		 *  	<metadata>
		 *  </doc>
		 * </documents>
		 */
		Connection connection = 
			new DatabaseConection(ServerConfiguration.getInstance()).getMySqlConnection();
		Vector vector = new Vector();
		try {
			vector = DatabaseHelper.getDocumentsToSign(connection);
			Document doc = XMLSignatureHelper.createXMLWithDocuments(vector);
			ByteArrayOutputStream baos = XMLSignatureHelper.printDOMDocumentToOutputStream(doc);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());

			OutputStream out = response.getOutputStream();
			int input;
			while((input = bais.read()) != -1) {
				out.write(input);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String action = request.getParameter(ServletUtilities.DOCUMENT_ACTION);
 		int iaction = new Integer(action).intValue();
		switch (iaction) {
		case ServletUtilities.DOCUMENT_ADD:
			addDocumentIntoStore(request,response);
			break;
		case ServletUtilities.DOCUMENT_DEL:
			deleteDocumentFromStore(request,response);
			break;
		case ServletUtilities.DOCUMENT_EDIT:
			editDocumentInStore(request,response);
			break;
		default:
			errorMessage(response, "Wrong action");
			break;
		}
	}

	/**
	 * Sestaveni url adresy bezici aplikace. Pouziva se pro navigaci mezi strankama
	 * ktere se do sebe includuji
	 * @param request
	 * @return
	 */
	private String getAppUrl(HttpServletRequest request) {
		String context = request.getContextPath();
		String appURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+context;
		return appURL;
	}
	
	private void deleteDocumentFromStore(HttpServletRequest request, HttpServletResponse response) 
	throws IOException{

		String docids = request.getParameter("docid");
		int docid = Integer.parseInt(docids);
		PrintWriter out = response.getWriter();
		out.println("<h1>Document Delete</h1>");
		//docMod.setDocURL(docURL);
		Connection connection = 
			new DatabaseConection(ServerConfiguration.getInstance()).getMySqlConnection();

		try {
			DatabaseHelper.deleteDocumentFromDatabase(connection, docid);
			out.println("<h3>Document Deleted</h3>");

		} catch (SQLException e) {
			handleException(response, e);
		} finally {
			out.println("go back <a href=\""+getAppUrl(request)+"/webapp/documents.jsp\">go back</a> <br/>");
			try {
				connection.close();
			} catch (SQLException e) {
				handleException(response, e);
			}
		}
		
		out.close();
		
	}
	private void editDocumentInStore(HttpServletRequest request, HttpServletResponse response) 
	throws IOException{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		DocumentModel docMod = createDocumentModelFromParams(request);
		String docid = request.getParameter("docid");
		docMod.setDocid(Integer.parseInt(docid));
		out.println("<h1>Document Edit</h1>");
		
		Connection connection = 
			new DatabaseConection(ServerConfiguration.getInstance()).getMySqlConnection();		
		out.println("<h1>Editing  document in Database</h1>");
		
		try {
			DatabaseHelper.updateDocumentInDatabase(connection, docMod);
			out.println("<h3>Document Document Edited</h3>");

		} catch (SQLException e) {
			handleException(response, e);
		} finally {
			out.println("go back to Documents <a href=\""+getAppUrl(request)+"/webapp/documents.jsp\">go back</a> <br/>");
			out.close();

			try {
				connection.close();
			} catch (SQLException e) {
				handleException(response, e);
			}
		}
		
		
		
		//docMod.setDocURL(docURL);
	}

	
	
	private void errorMessage(HttpServletResponse response, String message) 
	throws IOException {
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String title = "Documents Store Error";

	    out.println(ServletUtilities.headWithTitle(title) +
	                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
	                "<H1 ALIGN=CENTER>" + title + "</H1>\n");
	    out.println("<p>"+ message +"</p>");	
	    out.println("</TABLE>\n</BODY></HTML>");
		
	}
	
	private void handleException(HttpServletResponse response, Throwable e) 
	throws IOException {
	    PrintWriter out = response.getWriter();
	    String title = "Documents Store Error";

	    
	    out.println("<BODY BGCOLOR=\"#FDF5E6\">\n" +
	                "<H1>" + title + "</H1>\n");
	    out.println("<p>"+ e.getMessage() +"</p>");	
	    out.println("<p>");
	    e.printStackTrace(out);
	    out.println("</p>");

		
	}

	/**
	 * Create instance of DocumentModel object from request.params from. This method
	 * is used from addDocumentIntoStore and editDocumentInTheStore
	 * @param request
	 * @return
	 */
	private DocumentModel createDocumentModelFromParams(HttpServletRequest request) {
		String monthst = request.getParameter(ServletUtilities.MONTH_START);
		String yearst = request.getParameter(ServletUtilities.YEAR_START);
		String dayst = request.getParameter(ServletUtilities.DAY_START);

		String monthe = request.getParameter(ServletUtilities.MONTH_END);
		String yeare = request.getParameter(ServletUtilities.YEAR_END);
		String daye = request.getParameter(ServletUtilities.DAY_END);
		
		String description = request.getParameter("description");
		String docurl = request.getParameter("docurl");
		
		Calendar dateS = new GregorianCalendar();
		dateS.set(Integer.parseInt(yearst), Integer.parseInt(monthst), Integer.parseInt(dayst));
		Calendar dateE = new GregorianCalendar();
		dateE.set(Integer.parseInt(yeare), Integer.parseInt(monthe), Integer.parseInt(daye));
		
		
		DocumentModel docMod = new DocumentModel();
		docMod.setDocURL(docurl);
		docMod.setDescription(description);
		docMod.setValidStart(dateS.getTimeInMillis());
		docMod.setValidEnd(dateE.getTimeInMillis());
		

		return docMod;
	}
	
	
	private void addDocumentIntoStore(HttpServletRequest request, HttpServletResponse response) 
	throws IOException{

		DocumentModel docMod = createDocumentModelFromParams(request);
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	
		Connection connection = 
			new DatabaseConection(ServerConfiguration.getInstance()).getMySqlConnection();		
		out.println("<h1>Going to add document into Database</h1>");
		
		try {
			DatabaseHelper.insertDocumentIntoDatabase(connection, docMod);
			out.println("<h3>Document Added</h3>");

		} catch (SQLException e) {
			handleException(response, e);
		} finally {
			out.println("go back to Documents<a href=\""+getAppUrl(request)+"/webapp/documents.jsp\">go back</a> <br/>");
			try {
				connection.close();
			} catch (SQLException e) {
				handleException(response, e);
			}
		}
		

	}
	
	private void writeResponse(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String title = "Reading All Request Parameters";

	    out.println(ServletUtilities.headWithTitle(title) +
	                "<BODY BGCOLOR=\"#FDF5E6\">\n" +
	                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
	                "<TABLE BORDER=1 ALIGN=CENTER>\n" +
	                "<TR BGCOLOR=\"#FFAD00\">\n" +
	                "<TH>Parameter Name<TH>Parameter Value(s)");
	    Enumeration paramNames = request.getParameterNames();
	    while(paramNames.hasMoreElements()) {
	      String paramName = (String)paramNames.nextElement();
	      out.println("<TR><TD>" + paramName + "\n<TD>");
	      String[] paramValues = request.getParameterValues(paramName);
	      if (paramValues.length == 1) {
	        String paramValue = paramValues[0];
	        if (paramValue.length() == 0)
	          out.print("<I>No Value</I>");
	        else
	          out.print(paramValue);
	      } else {
	        out.println("<UL>");
	        for(int i=0; i<paramValues.length; i++) {
	          out.println("<LI>" + paramValues[i]);
	        }
	        out.println("</UL>");
	      }
	    }
	    out.println("</TABLE>\n</BODY></HTML>");
		
	}
	
	
}
