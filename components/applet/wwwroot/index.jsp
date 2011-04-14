<%@ page contentType="text/html; charset=ISO-8859-1" %>
<%@ page import="java.util.*" %>
<%@ page import="org.topon.database.*"%>

<html>
<jsp:include page="webapp/head.jsp" />

<%
	String serverURL = request.getRequestURL().toString();
	serverURL = serverURL.substring(0,serverURL.lastIndexOf('/'));
%>

<body>
<div id="custom-doc" class="yui-t1">
   <div id="hd" role="banner">
  		 <!--
  		 <div id="header">
	    	<a href="index.jsp" alt="asdf" title="asdfasdfsdf"><img src="img/header_3.jpg"></a>
		  </div>
		  -->
		  aaaa
   </div>
   <div id="bd" role="main">
	<div id="yui-main">
	<div class="yui-b"><div class="yui-ge">
    <div class="yui-u first">



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
<!--
			<td><input type="text" name="<%= docMod.getDocid()%>_validStart" value="<%= new Date(docMod.getValidStart()) %>"></td>
			<td><input type="text" name="<%= docMod.getDocid()%>_validEnd" value="<%= new Date(docMod.getValidEnd()) %>"></td>
			<td><input type="text" name="<%= docMod.getDocid()%>_doc_id" value="<%= docMod.getDocid()  %>"></td>
-->			
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




			
	</div>
 <!--
    <div class="yui-u">
<p> 



  		<div  class="banner">
	    	<a href="http://www.navaclavce.cz/"><img src="img/logonavaclavce.PNG"></a>

		</div>

   		<div  class="banner">
	    	<a href="http://www.skinzone.cz"><img src="img/skinzone_CZ.jpg"></a>

		</div>
	 		<div  class="banner_energy">
		    	<a href="http://www.trojkaenergy.cz"><img src="img/logo_trojka.PNG"></a>

			</div>
		</p>
    </div>
    -->
</div>
</div>
	</div>
	<div class="yui-b">
    	<div id="leftmenu" class="yuimenuu">
    		
			<ul>
				<li>
			       <a  id="propozice" href="index.php">
			          <img class="swapImage {src: 'img/buttons/aktuality2.PNG'}" src="img/buttons/aktuality1.PNG" alt="propozice">
			       </a>
			    </li>
			    <li >
			       <a  href="historie.php">
			          <img class="swapImage {src: 'img/buttons/historie2.PNG'}" src="img/buttons/historie1.PNG" alt="propozice">
			       </a>
			    </li>
                <li>
	                <a  id="propozice" href="propozice.php">
		               <img class="swapImage {src: 'img/buttons/propozice2.PNG'}" src="img/buttons/propozice1.PNG" alt="propozice">
		            </a>
		        </li>
			</ul>
    	</div>
    	
	</div>
	
	</div>
   <div id="ft" role="contentinfo">
   	<jsp:include page="webapp/foot.jsp" />
   	
   </div>
</div>
</body>
</html>



