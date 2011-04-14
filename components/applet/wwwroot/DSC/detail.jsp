<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="org.topon.database.*"%>
<%@ page import="java.text.DateFormat"%>



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
            <h2> detail dokumentu </h2>
 
<%
			DocumentModel docMod = DatabaseHelper.getDocumentById(1);
%>
<!--docid,docurl,validstart,validend,description,filetype,hash,type,filename-->
	<table>
		<tr>
			<td><b>popis:&nbsp;</b></td><td><%= docMod.getDescription() %></td>
		</tr><tr>
				<td><b>stav:&nbsp;</b></td><td>podepsaný</td>
			</tr><tr>

			<td><b>počátek platnosti:&nbsp;</b></td><td><%= DateFormat.getDateInstance().format(new Date(docMod.getValidStart())) %></td>
		</tr><tr>

			<td><b>konec platnosti:&nbsp;</b></td><td><%= DateFormat.getDateInstance().format(new Date(docMod.getValidEnd())) %></td>
		</tr><tr>
			<td><b>popis:&nbsp;</b></td><td><%= docMod.getDescription() %></td>
		</tr><tr>
			<td><b>zadavatel&nbsp;</b></td><td>Jan Novák</td>
		</tr><tr>
			<td><b>typ dokumentu:&nbsp;</b></td><td><%= docMod.getType() %></td>
		</tr><tr>
			<td><b>typ souboru:&nbsp;</b></td><td><%= docMod.getFiletype() %></td>
		</tr><tr>
 			<td><b>typ souboru:&nbsp;</b></td><td><%= docMod.getFiletype() %></td>
		</tr><tr>
			<td><b>hash (sha1):&nbsp;</b></td><td><%= docMod.getHash() %></td>
		</tr><tr>
			<td><b>jméno souboru:&nbsp;</b></td><td><%= docMod.getFilename() %></td>
		</tr>
		</table>
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
		</div>
		
		
        </div>
    </div>
<jsp:include page="webapp/foot.jsp" />
</div>
</div>
</body>
</html>