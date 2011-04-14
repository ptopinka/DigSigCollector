<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="java.util.*" %>
<%@ page import="org.topon.database.*"%>


<jsp:include page="webapp/head.jsp" />

<%
	String serverURL = request.getRequestURL().toString();
	serverURL = serverURL.substring(0,serverURL.lastIndexOf('/'));
%>

<Body>


<div id="head">

</div>



	<h1> Welcome to the application</h1> 
	<strong><%=request.getUserPrincipal().getName()%></strong>




	<table border="1" width="300px">
    <form name="mainForm" method="post" action="FileUploadServlet">
        Choose file to upload and sign:
        
        <br>
        Certification chain:
		<tr>

<%
		Vector documents = DatabaseHelper.getDocumentsToSign();
		Iterator itr = documents.iterator();
	    while(itr.hasNext()) {
		DocumentModel docMod = (DocumentModel) itr.next();
%>
		<tr>
			<td><input type="checkbox" name="getsign" onchange="singleSelectCheckbox(this.form,this)" value="<%= docMod.getDocid()  %>"></td>
			<td><input type="text disabled="true" value="fresh"></td>
			<td><input type="text" name="<%= docMod.getDocid()%>_fileToBeSigned" value="<%= docMod.getDocURL() %>"></td>
			<td><input type="text" name="<%= docMod.getDocid()%>_description" value="<%= docMod.getDescription() %>"></td>
			<td><input type="text" name="<%= docMod.getDocid()%>_validStart" value="<%= new Date(docMod.getValidStart()) %>"></td>
			<td><input type="text" name="<%= docMod.getDocid()%>_validEnd" value="<%= new Date(docMod.getValidEnd()) %>"></td>
			<td><input type="text" name="<%= docMod.getDocid()%>_doc_id" value="<%= docMod.getDocid()  %>"></td>
			
		</tr>
<%
		}

%>



        
        <br>
        Signature:
        


	</table>
    <input type="hidden" name="docctype" value="inner">
    <input type="hidden" name="serverURL" value="<%= serverURL %>">
    </form>





<script src="http://www.java.com/js/deployJava.js"></script> 
<!--
<script> 
		var attributes = { code:'org.topon.applet.SignTest.class', archive:'dsigcollector-applet-1.0.jar,dsigcollector-client-1.0.jar',  width:130, height:25} ; 
		var parameters = { fileNameField:'fileToBeSigned',signButtonCaption:'Sign selected file', 
							signatureField:'signature',signatureField:'signature',scriptable:'true',certificationChainField:'certificationChain' } ; 
		deployJava.runApplet(attributes, parameters, '1.6'); 
</script> 
-->
<script> 
		var attributes = { id:'dsigApplet', code:'org.topon.applet.DSigCollectorApplet.class', archive:'dsigcollector-applet-1.0.jar,dsigcollector-client-1.0.jar',  width:130, height:25} ; 
		var parameters = { fileNameField:'fileToBeSigned',doctypeField:'docctype',signButtonCaption:'Sign selected file', 
							signatureField:'signature',signatureField:'signature',scriptable:'true',certificationChainField:'certificationChain' } ; 
		deployJava.runApplet(attributes, parameters, '1.6'); 
</script> 



<form  >
	<TEXTAREA NAME="xmlsignature" COLS=40 ROWS=6>
	</TEXTAREA>
	<P><INPUT TYPE="button" NAME="send" VALUE="Send Signature To Server..." onClick="signDocument(this.form)"> </P>
</form>



<jsp:include page="webapp/foot.jsp" />

</body>

</html>