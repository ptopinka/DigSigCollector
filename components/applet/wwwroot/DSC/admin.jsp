<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="org.topon.database.*"%>
<%@ page import="java.text.DateFormat"%>


<%
	String serverURL = request.getRequestURL().toString();
	serverURL = serverURL.substring(0,serverURL.lastIndexOf('/'));
%>


<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>
<html>
<jsp:include page="webapp/head.jsp" />




<body>
<div id="all">
<div id="wrapper">


<jsp:include page="webapp/menu.jsp" />
    
    

    

    <div id="container">
        <div id="side-a">
        
            <ul id="navbar">
                <li><a href="admindocuments.jsp">Dokumenty</a>
                <li><a href="adminsignatures.jsp">Podpisy</a>
            
        </div>
        
        <div id="side-b">
           
           <ul>
				<li><b>Uživatel:</b>&nbsp;Antonín Novák </li>
				<li><b>role:</b>administrátor</li>
			</ul>
		
			<h3>Dokument: BOZP</h3>
			<ul>
				<li>jméno souboru: BOZP.pdf</li>
				<li>platnost:  1.3.2010 - 1.3.2011</li>
				<li><a href="detail.jsp">detail..</a></li>
			</ul>

			<hr/>


				<table border="1" width="600px">
			   		<tr>
						<td>uživatel</td>
						<td>login</td>
						<td>stav</td>
						<td>detail podpisu</td>
						
					</tr>
			 		<form name="mainForm" method="post" action="FileUploadServlet">

						<tr>
							<td>Borůvka Milan</td>
							<td>boruvkam</td>
							<td>nepodepsaný</td>
							<td>&nbsp;</td>
						</tr>

						<tr>
							<td>Fabián Jiří</td>
							<td>fabianj</td>
							<td>nepodepsaný</td>
							<td>&nbsp;</td>
						</tr>

						<tr>
							<td>Petr Novák</td>
							<td>novakp</td>
							<td>podepsaný</td>
							<td><a href="detail.jsp">...</a></td>
						</tr>
						<tr>
							<td>Pavel Topinka</td>
							<td>topinpav</td>
							<td>podepsaný</td>
							<td><a href="detail.jsp">...</a></td>
						</tr>
						<tr>
							<td>Pavel Matyasko</td>
							<td>matyapav</td>
							<td>podepsaný</td>
							<td><a href="detail.jsp">...</a></td>
						</tr>
						<tr>
							<td>Radim Čebiš</td>
							<td>cebisrad</td>
							<td>podepsaný</td>
							<td><a href="detail.jsp">...</a></td>
						</tr>
						<tr>
							<td>Lindsey Hudak</td>
							<td>hudaklin</td>
							<td>podepsaný</td>
							<td><a href="detail.jsp">...</a></td>
						</tr>
					
						
			



				</table>
			    <input type="hidden" name="docctype" value="inner">
			    <input type="hidden" name="serverURL" value="<%= serverURL %>">

			    </form>

				<br/>



        </div>
    </div>
<jsp:include page="webapp/foot.jsp" />
</div>
</div>
</body>
</html>