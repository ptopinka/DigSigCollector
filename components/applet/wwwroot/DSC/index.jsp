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
        <!--
            <ul id="navbar">
                <li><a href="hippo.html">Otext t njooio I</a>
                <li><a href="jak.html">Odrazka II</a>
                <li><a href="second.html">Odrazka III</a>
                <li><a href="links.html">Odrazka IV</a>
                <li><a href="vyzia.html">Odrazka V</a>
                <li><a href="kkiji.html">Odrazka VI</a>
            </ul>
            -->
        </div>
        
        <div id="side-b">
            <ul>
				<li><b>Uživatel:</b>&nbsp;Pavel Topinka </li>
				<li><b>role:</b>uživatel</li>
			</ul>

Máte 5 nepodepsaných dokumentů:</br>
<hr/>

        
	<table border="1" width="300px">
   		<tr>
			<td>vybrat</td>
			<td>stav</td>
			<td>popis</td>
			<td>od</td>
			<td>do</td>
			<td>url</td>
			<td>detail</td>
		</tr>
 		<form name="mainForm" method="post" action="FileUploadServlet">


<%
		Vector documents = DatabaseHelper.getDocumentsToSign();
		Iterator itr = documents.iterator();
	    while(itr.hasNext()) {
		DocumentModel docMod = (DocumentModel) itr.next();
%>
		<tr>
			<td><input type="checkbox" name="getsign" onchange="singleSelectCheckbox(this.form,this)" value="<%= docMod.getDocid()  %>"></td>
			<td><input type="text" size="13" disabled="true" value="nepodepsany"></td>
			<td><input type="text" size="45" name="<%= docMod.getDocid()%>_description" value="<%= docMod.getDescription() %>"></td>
			<td><input type="text" size="15" name="<%= docMod.getDocid()%>_validStart" value="<%=DateFormat.getDateInstance().format(new Date(docMod.getValidStart()))%>"></td>
			<td><input type="text" size="15" name="<%= docMod.getDocid()%>_validEnd" value="<%= DateFormat.getDateInstance().format(new Date(docMod.getValidEnd())) %>"></td>
			<td><a href="<%= docMod.getDocURL() %>">stáhnout</td>
			<td><a href="detail.jsp">detail</td>
		</tr>
		<input type="hidden" size="60px" name="<%= docMod.getDocid()%>_fileToBeSigned" value="<%= docMod.getDocURL() %>">
		<input type="hidden" size="1" name="<%= docMod.getDocid()%>_doc_id" value="<%= docMod.getDocid()  %>">
<%
		}

%>



	</table>
    <input type="hidden" name="docctype" value="inner">
    <input type="hidden" name="serverURL" value="<%= serverURL %>">

    </form>

<br/>
<div align="center">
<table >
<tr>
<td>
<script> 
		var attributes = { id:'dsigApplet', code:'org.topon.applet.DSigCollectorApplet.class', archive:'dsigcollector-applet-1.0.jar,dsigcollector-client-1.0.jar',  width:150, height:25} ; 
		var parameters = { fileNameField:'fileToBeSigned',doctypeField:'docctype',signButtonCaption:'Podepsat Dokument', 
							signatureField:'signature',signatureField:'signature',scriptable:'true',certificationChainField:'certificationChain' } ; 
		deployJava.runApplet(attributes, parameters, '1.6'); 
</script>
</td>
<td>
<script> 
		var attributes = { id:'dsigAppletSend', code:'org.topon.applet.DSigCollectorApplet.class', archive:'dsigcollector-applet-1.0.jar,dsigcollector-client-1.0.jar',  width:150, height:25} ; 
		var parameters = { fileNameField:'fileToBeSigned',doctypeField:'docctype',signButtonCaption:'Odeslat', 
							signatureField:'signature',signatureField:'signature',scriptable:'true',certificationChainField:'certificationChain' } ; 
		deployJava.runApplet(attributes, parameters, '1.6'); 
</script> 
</td>
<td>
<script> 
		var attributes = { id:'dsigAppletStore', code:'org.topon.applet.DSigCollectorApplet.class', archive:'dsigcollector-applet-1.0.jar,dsigcollector-client-1.0.jar',  width:150, height:25} ; 
		var parameters = { fileNameField:'fileToBeSigned',doctypeField:'docctype',signButtonCaption:'Uložit', 
							signatureField:'signature',signatureField:'signature',scriptable:'true',certificationChainField:'certificationChain' } ; 
		deployJava.runApplet(attributes, parameters, '1.6'); 
</script> 
</td>
<td>
<script> 
		var attributes = { id:'dsigAppletRefuse', code:'org.topon.applet.DSigCollectorApplet.class', archive:'dsigcollector-applet-1.0.jar,dsigcollector-client-1.0.jar',  width:150, height:25} ; 
		var parameters = { fileNameField:'fileToBeSigned',doctypeField:'docctype',signButtonCaption:'Odmítnout', 
							signatureField:'signature',signatureField:'signature',scriptable:'true',certificationChainField:'certificationChain' } ; 
		deployJava.runApplet(attributes, parameters, '1.6'); 
</script> 
</td>
</tr>
</table>
</div> <!-- div tabulky-->
        </div>
    </div>

<jsp:include page="webapp/foot.jsp" />


</div>
</div>
</body>
</html>