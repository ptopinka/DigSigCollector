<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="org.topon.database.*"%>




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
                <li><a href="second.html"></a>
            
        </div>
        
        <div id="side-b">
            <h2> Administrace Dokumentů </h2>
           <ul>
				<li><b>Uživatel:</b>&nbsp;Antonín Novák </li>
				<li><b>role:</b>administrátor</li>
			</ul>


        </div>
    </div>
<jsp:include page="webapp/foot.jsp" />
</div>
</div>
</body>
</html>