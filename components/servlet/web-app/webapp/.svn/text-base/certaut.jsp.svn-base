<%@page import="org.topon.database.*"%>
<%@page import="org.topon.configuration.*"%>
<%@page import="org.topon.configuration.identity.*"%>
<%@page import="org.topon.signatures.servlet.ServletUtilities"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Date"%>
<%@page import="org.topon.ldap.LdapStore"%>
<%
String context = request.getContextPath();
String appURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+context;
%>




<jsp:include page="head.jsp" />
	<p>
		<h1>Certification Authority</h1>
		Link to certification authority
		<a href="http://localhost:8080/ejbca/">EJBCA</a>
	</p>


<%

		
		LdapStore ldap = new LdapStore(ServerConfiguration.getInstance());

		Vector users = ldap.getUsers();

/*
			System.out.println(userMod.getUid());
			System.out.println(userMod.getCertificate());

*/

%>
	<h3>User Registered by Certification authority</h3>
	<div>
	<table border="1">	
<%
		for(int i = 0; i < users.size(); i++) {
			UserModel um = (UserModel)users.get(i);
			out.println("<tr><td>"+um.getUid()+"</td>"+
				
				"<td><form action='"+appURL+"/CA' method='post'>"+
                "<input type='hidden' name='"+ServletUtilities.USER_ACTION+"' value='"+ServletUtilities.USER_EDIT+"'>"+ 
                "<input type='hidden' name='uid' value='"+um.getUid()+"'/>"+ 
				"<input type='submit' value='Edit'></form></td>"+

				"<td><form action='"+appURL+"/CA' method='post'>"+
                "<input type='hidden' name='"+ServletUtilities.USER_ACTION+"' value='"+ServletUtilities.USER_DEL+"'>"+ 
                "<input type='hidden' name='uid' value='"+um.getUid()+"'/>"+ 
				"<input type='submit' value='Delete'></form></td>"+
				"</tr>");
		}

%>
	</table>
	</div>

	
	<div>
	<h3>Register New User into Registration Authority</h3>
	
	<p>
		<FORM ACTION="<%=appURL%>/CA" METHOD="POST">
  			<input type="hidden" name="<%=ServletUtilities.USER_ACTION%>" 
  				   value="<%=ServletUtilities.USER_ADD%>"/>
			name: <input type="text" size="50" name="name"><br/>
			surname:<input type="text" size="50" name="surname"><br/>
			login: <input type="text" size="50" name="login"><input type="text" size="16" value="@fel.cvut.cz" name="loginsuffix readonly="true" disabled="true"><br/>
			password:<input type="password" size="50" name="passwd1"><br/>
			retype password: <input type="password" size="50" name="passwd2"><br/>	
			<INPUT TYPE="SUBMIT" VALUE="Submit">
		</form>	
	</p>
	</div>

<jsp:include page="foot.jsp" />


