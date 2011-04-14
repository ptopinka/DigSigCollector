package org.topon.signatures.servlet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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

import org.topon.certificationautority.CAHelper;
import org.topon.configuration.ServerConfiguration;
import org.topon.database.DatabaseConection;
import org.topon.database.DatabaseHelper;
import org.topon.database.SignatureModel;
import org.topon.database.UserModel;
import org.topon.ldap.LdapStore;
import org.topon.ldap.LdapStoreException;
import org.topon.signatures.XMLSignatureHelper;
import org.w3c.dom.Document;

public class CA extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get web.xml for display by a servlet
		String file = "/WEB-INF/web.xml";

		URL url = null;
		URLConnection urlConn = null;
		PrintWriter out = null;
		BufferedInputStream buf = null;
		try {
			out = response.getWriter();
			url = getServletContext().getResource(file);
			// set response header
			response.setContentType("text/xml");

			urlConn = url.openConnection();
			// establish connection with URL presenting web.xml
			urlConn.connect();
			buf = new BufferedInputStream(urlConn.getInputStream());
			int readBytes = 0;
			while ((readBytes = buf.read()) != -1)
				out.write(readBytes);
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

		String action = request.getParameter(ServletUtilities.USER_ACTION);
		int iaction = new Integer(action).intValue();
		switch (iaction) {
		case ServletUtilities.USER_ADD:
			addUser(request, response);
			break;
		case ServletUtilities.USER_DEL:
			deleteUser(request, response);
			break;
		case ServletUtilities.USER_EDIT:
			editUser(request, response);
			break;
		case ServletUtilities.USER_REVOKE:
			revokeUser(request, response);
			break;
		case ServletUtilities.USER_VALIDATE:
			validateUser(request, response);
			break;

		default:
			errorMessage(response, "Wrong action");
			break;
		}

	}

	private void addUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<h1>Adding user</h1>");
		out.println("go back <a href=\"" + getAppUrl(request)
				+ "/webapp/documents.jsp\">go back</a> <br/>");
		out.close();
	}

	private void deleteUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<h1>Deleting user user</h1>");
		out.println("go back <a href=\"" + getAppUrl(request)
				+ "/webapp/documents.jsp\">go back</a> <br/>");
		out.close();

	}

	private void editUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<h1>Edit user</h1>");
		out.println("go back <a href=\"" + getAppUrl(request)
				+ "/webapp/documents.jsp\">go back</a> <br/>");
		out.close();

	}

	private void revokeUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write("<h1>Revoke user</h1>");
		out.println("go back <a href=\"" + getAppUrl(request)
				+ "/webapp/documents.jsp\">go back</a> <br/>");
		out.close();

	}

	private void validateUser(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String uid = request.getParameter("uid");
		String idpodpis = request.getParameter("idpodpis");
		String docidPar = request.getParameter("docid");
		int docid = Integer.parseInt(docidPar);
		// get user from LDAP by uid
		// get user from DATABASE who signed document
		out.write("<h1>Validating  user certifice from LDAP with from XML Signautre stored in database</h1>");
		Connection connection = new DatabaseConection(ServerConfiguration
				.getInstance()).getMySqlConnection();

		out.write("idpodpis="+idpodpis+", docid"+docid+", uid="+uid);
		
		try {

			LdapStore ldap = new LdapStore(ServerConfiguration.getInstance());

			UserModel databaseUser = 
				DatabaseHelper.getUserWhoSignedDocument(connection, docid, uid);
			UserModel ldapUser = ldap.getUser(uid);
			
			out.write("<br/><br/><b>db </b><br/>"+ databaseUser.getCertificate());
			out.write("<br/><br/><b>ldap</b> <br/>"+ ldapUser.getCertificate());
			
			boolean equal = CAHelper.isCertEqual(
					databaseUser.getCertificate(), ldapUser.getCertificate());
			if(equal) {
			out.write("<h3>Certificates from stored signature and from LDAP are equal for user '"+uid+"', Signature is valid</h3>");
			} else {
				out.write("<h3>Certificates from stored xml signature and from LDAP are equal for user '"+uid+"', Signature isn't  valid</h3>");
			}
		} catch (SQLException e) {
			handleException(response, e);
		} catch (LdapStoreException e) {
			handleException(response, e);
		} finally {
			out.println("go back to Documents <a href=\"" + getAppUrl(request)
					+ "/webapp/documents.jsp\">go back</a> <br/>");
			out.close();

			try {
				connection.close();
			} catch (SQLException e) {
				handleException(response, e);
			}
		}
		out.close();

	}

	private void handleException(HttpServletResponse response, Throwable e)
			throws IOException {
		PrintWriter out = response.getWriter();
		String title = "Documents Store Error";

		out
				.println("<BODY BGCOLOR=\"#FDF5E6\">\n" + "<H1>" + title
						+ "</H1>\n");
		out.println("<p>" + e.getMessage() + "</p>");
		out.println("<p>");
		e.printStackTrace(out);
		out.println("</p>");

	}

	private void errorMessage(HttpServletResponse response, String message)
			throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String title = "Documents Store Error";

		out.println(ServletUtilities.headWithTitle(title)
				+ "<BODY BGCOLOR=\"#FDF5E6\">\n" + "<H1 ALIGN=CENTER>" + title
				+ "</H1>\n");
		out.println("<p>" + message + "</p>");
		out.println("</TABLE>\n</BODY></HTML>");

	}

	/**
	 * Sestaveni url adresy bezici aplikace. Pouziva se pro navigaci mezi
	 * strankama ktere se do sebe includuji
	 * 
	 * @param request
	 * @return
	 */
	private String getAppUrl(HttpServletRequest request) {
		String context = request.getContextPath();
		String appURL = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + context;
		return appURL;
	}

}
