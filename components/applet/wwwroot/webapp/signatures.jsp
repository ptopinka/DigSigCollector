<%@page import="org.topon.database.*"%>
<%@page import="org.topon.configuration.*"%>
<%@page import="org.topon.configuration.identity.*"%>
<%@page import="org.topon.signatures.servlet.ServletUtilities"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Date"%>
<%@page import="org.topon.ldap.LdapStore"%>

<%
String context = request.getContextPath();
String appURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+context;
%>

<jsp:include page="head.jsp" />


	<h1>Recieved Signatures Administration</h1>
	
<%
		Connection connection = 
			new DatabaseConection(ServerConfiguration.getInstance()).getMySqlConnection();
		Vector vector = new Vector();
		vector = DatabaseHelper.getDocumentsToSign(connection);

		LdapStore ldap = new LdapStore(ServerConfiguration.getInstance());

		HashMap ldapusersHM = ldap.getUsersHashMap();		
		
		for(int i = 0; i < vector.size(); i++) {
			DocumentModel dm = (DocumentModel)vector.get(i);
			String docURL = dm.getDocURL();
			int docid = dm.getDocid();
			out.println("<h3>Document:" +dm.getDocURL() + "</h3><p> signed these users:</p>");
%>
			<table border='1'>
			<tr><td>user name</td><td>user login </td><td>action</td></tr>
<%
			Vector users = DatabaseHelper.getUsersWhoSignDocument(connection, docid); 
			for(int j = 0; j < users.size();j++){
				UserModel user = (UserModel)users.get(j);
				
				String username = new String();
				if(!user.getUid().equals("")) {
				 UserModel ummm = (UserModel)ldapusersHM.get(user.getUid()); 
				 if(ummm != null) {
				 	username = ummm.getCommonName();
				 }
				}
				
				out.println("<tr>");
				out.println("\t<td>"+username+"</td>");
				out.println("\t<td>"+user.getUid()+"</td>");
				out.println("<td><form action='"+appURL+"/CA' method='post'>"+
                			"<input type='hidden' name='"+ServletUtilities.USER_ACTION+"' value='"+ServletUtilities.USER_VALIDATE+"'>"+ 
                			"<input type='hidden' name='uid' value='"+user.getUid()+"'/>"+ 
                			"<input type='hidden' name='docid' value='"+docid+"'/>"+ 
                			"<input type='hidden' name='idpodpis' value='"+user.getIdpodpis()+"'/>"+ 
							"<input type='submit' value='Verify'></form></td>");
				out.println("</tr>");
			}





%>
			</table>
<%
		}


%>



<jsp:include page="foot.jsp" />
	