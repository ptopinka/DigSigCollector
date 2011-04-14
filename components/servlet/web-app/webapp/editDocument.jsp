<%@page import="org.topon.database.*"%>
<%@page import="org.topon.configuration.*"%>
<%@page import="org.topon.configuration.identity.*"%>
<%@page import="org.topon.signatures.servlet.ServletUtilities"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>

<%
String context = request.getContextPath();
String appURL = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+context;

	

	
%>

<jsp:include page="head.jsp" />

<%
	Connection con = 
	new DatabaseConection(ServerConfiguration.getInstance()).getMySqlConnection();
	int docid = Integer.parseInt(request.getParameter("docid"));
	DocumentModel docMod = DatabaseHelper.getDocumentById(con, docid);
%>

	<h1>Edit Document <%=docMod.getDocURL()%></h1>
	<div>
		
		<table>
<FORM ACTION="<%=appURL%>/DocumentsStore"
      METHOD="POST">
  <input type="hidden" name="<%=ServletUtilities.DOCUMENT_ACTION%>" value="<%=ServletUtilities.DOCUMENT_EDIT%>"/>
  <input type="hidden" name="docid" value="<%=docid%>"/>
  Document URL:
  <input type="text" size="100" value="<%=docMod.getDocURL()%>" name="docurl"><BR>
  Valid Start:

year <select name="<%=ServletUtilities.YEAR_START%>">-
<%
	int year = docMod.getCalendarField(DocumentModel.VALID_START_DATE, Calendar.YEAR);
	out.println("<option value='"+year+"'>"+year + " current setting" );
	
	for(int i = 2007; i < 2020; i++) {
		out.println("<option value='"+i+"'>"+i);
	}
%>


</select>
month
<select name="<%=ServletUtilities.MONTH_START%>">-
<%
	int month = docMod.getCalendarField(DocumentModel.VALID_START_DATE, Calendar.MONTH);
	out.println("<option value='"+month+"'>"+(month+1) + " current setting" );

	for(int i = 0; i <= 11; i++) {
	out.println("<option value='"+i+"'>"+(i+1));
	}
%>

</select>-
day
<select name="<%=ServletUtilities.DAY_START%>">-
<%
	int day = docMod.getCalendarField(DocumentModel.VALID_START_DATE, Calendar.DATE);
	out.println("<option value='"+day+"'>"+day + " current setting" );

	for(int i = 1; i <= 31; i++) {
	out.println("<option value='"+i+"'>"+i);
	}
%>
</select><br/>

  Valid End:
year
<select name="<%=ServletUtilities.YEAR_END%>">-
<%
	year = docMod.getCalendarField(DocumentModel.VALID_END_DATE, Calendar.YEAR);
	out.println("<option value='"+year+"'>"+year + " current setting" );

	for(int i = 2007; i < 2020; i++) {
	out.println("<option value='"+i+"'>"+i);
	}
%>
</select>
month
<select name="<%=ServletUtilities.MONTH_END%>">-
<%
	month = docMod.getCalendarField(DocumentModel.VALID_END_DATE, Calendar.MONTH);
	out.println("<option value='"+month+"'>"+(month+1) + " current setting" );

	for(int i = 0; i <= 11; i++) {
	out.println("<option value='"+i+"'>"+(i+1));
	}
%>
</select>-
day
<select name="<%=ServletUtilities.DAY_END%>">-
<%
	day = docMod.getCalendarField(DocumentModel.VALID_END_DATE, Calendar.DATE);
	out.println("<option value='"+day+"'>"+day + " current setting" );

	for(int i = 1; i <= 31; i++) {
	out.println("<option value='"+i+"'>"+i);
	}
%>

</select><br/>
  Description:
  <input type="text" name="description" size="150" value="<%=docMod.getDescription()%>"/>
  <CENTER>
    <INPUT TYPE="SUBMIT" VALUE="Submit">
  </CENTER>
</FORM>		</table>
	</div>




<jsp:include page="foot.jsp" />
	