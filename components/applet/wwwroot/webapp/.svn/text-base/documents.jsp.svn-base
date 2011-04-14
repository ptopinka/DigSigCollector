<%@page import="org.topon.database.*"%>
<%@page import="org.topon.configuration.*"%>
<%@page import="org.topon.configuration.identity.*"%>
<%@page import="org.topon.signatures.servlet.ServletUtilities"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Date"%>
<%
String context = request.getContextPath();
String appURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+context;
%>

<jsp:include page="head.jsp" />


	<div id="start">
		<h1>Documents to sign Administration</h1>
	</div>
	<div id="data">
<%
		Connection connection = 
			new DatabaseConection(ServerConfiguration.getInstance()).getMySqlConnection();
		Vector vector = new Vector();
		vector = DatabaseHelper.getDocumentsToSign(connection);
%>
	<table border="1">	
<%
		for(int i = 0; i < vector.size(); i++) {
			DocumentModel dm = (DocumentModel)vector.get(i);
			out.println("<tr><td>"+dm.getDocURL()+
				"</td><td>"+new Date(dm.getValidStart()).toString()+
				"</td><td>"+new Date(dm.getValidEnd()).toString()+
				"</td><td>"+dm.getDescription()+"</td>"+
				
				"<td><form action='"+appURL+"/webapp/editDocument.jsp' method='post'>"+
                "<input type='hidden' name='"+ServletUtilities.DOCUMENT_ACTION+"' value='"+ServletUtilities.DOCUMENT_EDIT+"'>"+ 
                "<input type='hidden' name='docid' value='"+dm.getDocid()+"'/>"+ 
				"<input type='submit' value='Edit'></form></td>"+

				"<td><form action='"+appURL+"/DocumentsStore' method='post'>"+
                "<input type='hidden' name='"+ServletUtilities.DOCUMENT_ACTION+"' value='"+ServletUtilities.DOCUMENT_DEL+"'>"+ 
                "<input type='hidden' name='docid' value='"+dm.getDocid()+"'/>"+ 
				"<input type='submit' value='Delete'></form></td>"+
				"</tr>");
		}

%>
	</table>
	</div>
	

	<div>
		<h3>New Document</h3>
		<table>
<FORM ACTION="<%=appURL%>/DocumentsStore"
      METHOD="POST">
  <input type="hidden" name="<%=ServletUtilities.DOCUMENT_ACTION%>" value="<%=ServletUtilities.DOCUMENT_ADD%>"/>
  Document URL:
  <input type="text" size="100" value="http://" name="docurl"><BR>
  Valid Start:

year <select name="<%=ServletUtilities.YEAR_START%>">-
<%
	for(int i = 2007; i < 2020; i++) {
	out.println("<option value='"+i+"'>"+i);
	}
%>
</select>
month
<select name="<%=ServletUtilities.MONTH_START%>">-
<%
	for(int i = 0; i <= 11; i++) {
	out.println("<option value='"+i+"'>"+(i+1));
	}
%>
</select>-
day
<select name="<%=ServletUtilities.DAY_START%>">-
<%
	for(int i = 1; i <= 31; i++) {
	out.println("<option value='"+i+"'>"+i);
	}
%>

</select><br/>
  Valid End:
year
<select name="<%=ServletUtilities.YEAR_END%>">-
<%
	for(int i = 2007; i < 2020; i++) {
	out.println("<option value='"+i+"'>"+i);
	}
%>
</select>
month
<select name="<%=ServletUtilities.MONTH_END%>">-
<%
	for(int i = 0; i <= 11; i++) {
	out.println("<option value='"+i+"'>"+(i+1));
	}
%>

</select>-
day
<select name="<%=ServletUtilities.DAY_END%>">-
<%
	for(int i = 1; i <= 31; i++) {
	out.println("<option value='"+i+"'>"+i);
	}
%>
</select><br/>
  Description:
  <textarea name="description" rows="4" cols="40" ></textarea>
  <CENTER>
    <INPUT TYPE="SUBMIT" VALUE="Submit">
  </CENTER>
</FORM>		</table>
	</div>


<%-- Create the bean only when the form is posted --%>
<% 
if (request.getMethod().equals("POST")) { 
	out.println("method post");
%>

	
<% 
} 
%>




<jsp:include page="foot.jsp" />
