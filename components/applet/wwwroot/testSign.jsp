<html>

<head>
    <title>Test Document Signer Applet</title>
</head>

<body>
    <form name="mainForm" method="post" action="FileUploadServlet">
        Choose file to upload and sign:
        <input type="text" name="fileToBeSigned" value="http://localhost:8080/dsigcollector/SignatureStore">
        <br>
        Certification chain:
        <input type="text" name="certificationChain">
        <br>
        Signature:
        <input type="text" name="signature">
    </form>
<script src="http://www.java.com/js/deployJava.js"></script> 
<script> 
		var attributes = { code:'org.topon.applet.SignTest.class', archive:'dsigcollector-applet-1.0.jar,dsigcollector-client-1.0.jar',  width:130, height:25} ; 
		var parameters = { fileNameField:'fileToBeSigned',signButtonCaption:'Sign selected file', 
							signatureField:'signature',signatureField:'signature',scriptable:'true',certificationChainField:'certificationChain' } ; 
		deployJava.runApplet(attributes, parameters, '1.6'); 
</script> 

</body>

</html>