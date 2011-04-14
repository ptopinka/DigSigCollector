package org.topon.applet;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.apache.xml.security.exceptions.XMLSecurityException;
import org.topon.client.URLClient;
import org.topon.client.URLClientException;
import org.topon.signatures.DSigCollectorSignatureHelper;
import org.topon.signatures.apache.DetachedXMLSignatureApache;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import netscape.javascript.JSException;
import netscape.javascript.JSObject;


public class SignTest extends Applet {

    private static final String FILE_NAME_FIELD_PARAM = "fileNameField";
    private static final String CERT_CHAIN_FIELD_PARAM = "certificationChainField";
    private static final String SIGNATURE_FIELD_PARAM = "signatureField";
    private static final String SIGN_BUTTON_CAPTION_PARAM = "signButtonCaption";

    private static final String PKCS12_KEYSTORE_TYPE = "PKCS12";
    private static final String X509_CERTIFICATE_TYPE = "X.509";
    private static final String CERTIFICATION_CHAIN_ENCODING = "PkiPath";
    private static final String DIGITAL_SIGNATURE_ALGORITHM_NAME = "SHA1withRSA";

    private Button mSignButton;
    private PrivateKeyAndCertChain privateKeyAndCertChain = null;

    /**
     * Initializes the applet - creates and initializes its graphical user interface.
     * Actually the applet consists of a single button, that fills its surface. The
     * button's caption comes from the applet parameter SIGN_BUTTON_CAPTION_PARAM.
     */
    public void init() {
        String signButtonCaption = this.getParameter(SIGN_BUTTON_CAPTION_PARAM);
        mSignButton = new Button(signButtonCaption);
        mSignButton.setLocation(0, 0);
        Dimension appletSize = this.getSize();
        mSignButton.setSize(appletSize);
        mSignButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                signSelectedFile();
            }
        });
        this.setLayout(null);
        this.add(mSignButton);
    }

    /**
     * Signs the selected file. The file name comes from a field in the HTML document.
     * The result consists of the calculated digital signature and certification chain,
     * both placed in fields in the HTML document, encoded in Base64 format. The HTML
     * document should contain only one HTML form. The name of the field, that contains
     * the name of the file to be signed is obtained from FILE_NAME_FIELD_PARAM
     * applet parameter. The names of the output fields for the signature and the
     * certification chain are obtained from the parameters CERT_CHAIN_FIELD_PARAM
     * and SIGNATURE_FIELD_PARAM. The applet extracts the certificate, its chain
     * and its private key from a PFX file. The user is asked to select this PFX file
     * and the password for accessing it.
     */
    private void signSelectedFile() {
        try {
            // Get the file name to be signed from the form in the HTML document
            JSObject browserWindow = JSObject.getWindow(this);
            JSObject mainForm = (JSObject) browserWindow.eval("document.forms[0]");
            String fileNameFieldName = this.getParameter(FILE_NAME_FIELD_PARAM);
            JSObject fileNameField = (JSObject) mainForm.getMember(fileNameFieldName);
            String fileName = (String) fileNameField.getMember("value");

            // Perform file signing
            CertificationChainAndSignatureInBase64 signingResult = signFile(fileName);

            if (signingResult != null) {
                // Document signed. Fill the certificate and signature fields
                String certChainFieldName = this.getParameter(CERT_CHAIN_FIELD_PARAM);
                JSObject certChainField = (JSObject) mainForm.getMember(certChainFieldName);
                certChainField.setMember("value", signingResult.mCertChain);
                String signatureFieldName = this.getParameter(SIGNATURE_FIELD_PARAM);
                JSObject signatureField = (JSObject) mainForm.getMember(signatureFieldName);
                signatureField.setMember("value", signingResult.mSignature);
            } else {
                // User canceled signing
            }
        }
        catch (DocumentSignException dse) {
            // Document signing failed. Display error message
            String errorMessage = dse.getMessage();
            JOptionPane.showMessageDialog(this, errorMessage);
        }
        catch (SecurityException se) {
            se.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Unable to access the local file system.\n" +
                "This applet should be started with full security permissions.\n" +
                "Please accept to trust this applet when the Java Plug-In ask you.");
        }
        catch (JSException jse) {
            jse.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Unable to access some of the fields in the\n" +
                "HTML form. Please check applet parameters.");
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Signs given local file. The certification chain and private key to be used for
     * signing are specified by the local user who choose a PFX file and password for
     * accessing it.
     * @param aFileName the name of the file to be signed.
     * @return the digital signature of the given file and the certification chain of
     * the certificate used for signing the file, both Base64-encoded or null if the
     * signing process is canceled by the user.
     * @throws DocumentSignException when a problem arise during the singing process
     * (e.g. invalid file format, invalid certificate, invalid password, etc.)
     */
    private CertificationChainAndSignatureInBase64 signFile(String aFileName)
    throws DocumentSignException {

        // Load the file for signing
        byte[] documentToSign = null;
        try {
            //documentToSign = readFileInByteArray(aFileName);
            documentToSign = readURLInByteArray(aFileName);
            System.out.println(documentToSign);
        } catch (IOException ioex) {
            String errorMsg = "Can not read the file for signing " + aFileName + ".";
            throw new DocumentSignException(errorMsg, ioex);
        }

        

        
        
        
        
        // Show a dialog for selecting PFX file and password
        CertificateFileAndPasswordDialog certFileAndPasswdDlg =
            new CertificateFileAndPasswordDialog();
        if (certFileAndPasswdDlg.run()) {

            // Load the keystore from specified file using the specified password
            String keyStoreFileName = certFileAndPasswdDlg.getCertificateFileName();
            if (keyStoreFileName.length() == 0) {
                String errorMessage = "It is mandatory to select a certificate " +
                	"keystore (.PFX or .P12 file)!";
                throw new DocumentSignException(errorMessage);
            }
            String password = certFileAndPasswdDlg.getCertificatePassword();
            KeyStore userKeyStore = null;
            try {
                userKeyStore = loadKeyStoreFromPFXFile(keyStoreFileName, password);
            } catch (Exception ex) {
                String errorMessage = "Can not read certificate keystore file (" +
                    keyStoreFileName + ").\nThe file is either not in PKCS#12 format" +
                    " (.P12 or .PFX) or is corrupted or the password is invalid.";
                throw new DocumentSignException(errorMessage, ex);
            }

            // Get the private key and its certification chain from the keystore
            
            try {
                privateKeyAndCertChain =
                    getPrivateKeyAndCertChain(userKeyStore, password);
            } catch (GeneralSecurityException gsex) {
                String errorMessage = "Can not extract certification chain and " +
                    "corresponding private key from the specified keystore file " +
                    "with given password. Probably the password is incorrect.";
                throw new DocumentSignException(errorMessage, gsex);
            }

            // Check if a private key is available in the keystore
            PrivateKey privateKey = privateKeyAndCertChain.mPrivateKey;
            if (privateKey == null) {
                String errorMessage = "Can not find the private key in the " +
                    "specified file " + keyStoreFileName + ".";
                throw new DocumentSignException(errorMessage);
            }

            // Check if X.509 certification chain is available
            Certificate[] certChain =
                privateKeyAndCertChain.mCertificationChain;
            if (certChain == null) {
                String errorMessage = "Can not find neither certificate nor " +
                    "certification chain in the file " + keyStoreFileName + ".";
                throw new DocumentSignException(errorMessage);
            }

            // Create the result object
            CertificationChainAndSignatureInBase64 signingResult =
                new CertificationChainAndSignatureInBase64();

            // Save X.509 certification chain in the result encoded in Base64
            try {
                signingResult.mCertChain = encodeX509CertChainToBase64(certChain);
            }
            catch (CertificateException cee) {
                String errorMessage = "Invalid certification chain found in the " +
                    "file " + keyStoreFileName + ".";
                throw new DocumentSignException(errorMessage);
            }

    		Document signatureDoc = 
    			DSigCollectorSignatureHelper.createDSigColSignature(
    					privateKeyAndCertChain.mPrivateKey,privateKeyAndCertChain.mCertificate, aFileName);
            


    		URLClient urlClient = new URLClient();
    		urlClient.setDocumentStore("http://localhost:8080/signatures/DocumentsStore");
    		urlClient.setSignatureStore("http://localhost:8080/signatures/SignatureStore");
    		int respCode = 0;
			try {
				respCode = urlClient.sendXMLSignatureToServer(signatureDoc);
			} catch (URLClientException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		System.out.println("AAAAAAAAAAAAAAAAA: " +respCode);

            
            
    		String urlDoc = "http://localhost/~topon/budejovicepraha/index.php"; 
    		//String urlDoc = "http://localhost:8080/signatures/SignatureStore";

    		/*
    		Document doc = xmlSigSUN.createDetachedSignature(
    				urlDoc,
    				ident.getKeyPair().getPrivate(),
    				ident.getX509Cetificate());  
*/
//    		doc = addUserInfoToSignature(doc);

    		System.out.println("Apache TEST start");
    		DetachedXMLSignatureApache xmlSigApacheTest = new DetachedXMLSignatureApache();
    		Document docA;
    		boolean validA = false;
    		try {
				docA = xmlSigApacheTest.createDetachedSignature(urlDoc,privateKeyAndCertChain.mPrivateKey,
						privateKeyAndCertChain.mCertificate);
	    		validA = xmlSigApacheTest.verifyDetachedSignature(null,docA);

			} catch (XMLSecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
    		System.out.println("Create by Apache,  Validate by Apache (cert is null) is: " + validA);
    		System.out.println("Apache TEST finish");		
    		
            
            
            //TEST
            MessageDigest sha1 = null;
			try {
				sha1 = MessageDigest.getInstance("SHA1");
			
            byte[] digest = sha1.digest(documentToSign);

            // Encrypt digest
            Cipher cipher = null;
			
				cipher = Cipher.getInstance("RSA");
			
            
				cipher.init(Cipher.ENCRYPT_MODE, privateKeyAndCertChain.mPublicKey);
			
            
				byte[] cipherText = cipher.doFinal(digest);
				
				
				
				
				
				
				System.out.println("Private Key: " +privateKeyAndCertChain.mPrivateKey.getEncoded());
				System.out.println("Private Key: " +Base64Utils.base64Encode(privateKeyAndCertChain.mPrivateKey.getEncoded()));
				System.out.println("Digest: " + Base64Utils.base64Encode(digest));
				System.out.println("Cipher text: " + Base64Utils.base64Encode(cipherText));
				System.out.println("Digest: " + bytes2String(digest));
				System.out.println("Cipher text: " + bytes2String(cipherText));
				
				
				
				Cipher dcipher = null;
				dcipher = Cipher.getInstance("RSA");
				dcipher.init(Cipher.DECRYPT_MODE, privateKeyAndCertChain.mPrivateKey);
		        byte[] decrypted = dcipher.doFinal(cipherText);
      			System.out.println("Decripted text: " + Base64Utils.base64Encode(decrypted));
				
				
				
				
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			}
           // createXMLSignature(); //create fake xml signature
            
            
            /* Apache XML Signature nefunguje. je to na smazani
    		try {
				System.out.println("Apache TEST start");
				DetachedXMLSignatureApache xmlSigApacheTest = new DetachedXMLSignatureApache();
				Document docA = xmlSigApacheTest.createDetachedSignature(aFileName,privateKeyAndCertChain.mPrivateKey,
						privateKeyAndCertChain.mCertificate); 
				boolean validA = xmlSigApacheTest.verifyDetachedSignature(null,docA);
				System.out.println("Create by Apache,  Validate by Apache (cert is null) is: " + validA);
				System.out.println("Apache TEST finish");
			} catch (XMLSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
    		*/
            
            
            //TEST
            
            
            
            
            
            // Calculate the digital signature of the file,
            // encode it in Base64 and save it in the result
            try {
                byte[] digitalSignature = signDocument(documentToSign, privateKey);
                signingResult.mSignature = Base64Utils.base64Encode(digitalSignature);
                System.out.println("tisk certifikatu: " + Base64Utils.base64Encode(privateKeyAndCertChain.mCertificate.getEncoded()));
            } catch (GeneralSecurityException gsex) {
                String errorMessage = "Error signing file " + aFileName + ".";
                throw new DocumentSignException(errorMessage, gsex);
            }

            // Document signing completed succesfully
            return signingResult;
        }
        else {
            // Document signing canceled by the user
            return null;
        }
    }

    /**
     * Loads a keystore from .PFX or .P12 file (file format should be PKCS#12)
     * using given keystore password.
     */
    private KeyStore loadKeyStoreFromPFXFile(String aFileName, String aKeyStorePasswd)
    throws GeneralSecurityException, IOException {
        KeyStore keyStore = KeyStore.getInstance(PKCS12_KEYSTORE_TYPE);
        FileInputStream keyStoreStream = new FileInputStream(aFileName);
        char[] password = aKeyStorePasswd.toCharArray();
        keyStore.load(keyStoreStream, password);
        return keyStore;
    }
    
    
    
    private void createXMLSignature() {
        try {
            /////////////////////////////
            //Creating an empty XML Document

            //We need a Document
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            dbfac.setNamespaceAware(true);
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            
            ////////////////////////
            //Creating the XML tree

            //create the root element and add it to the document
            
            String ns = "http://www.w3.org/2000/09/xmldsig#";
            Element root = doc.createElementNS(ns,"Signature");
            
            doc.appendChild(root);
            

            //create child element, add an attribute, and add to root
            Element sigInf = doc.createElement("SignedInfo");
            root.appendChild(sigInf);

            Element cm = doc.createElement("CanonicalizationMethod");
            cm.setAttribute("Algorithm","http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
            sigInf.appendChild(cm);
            
            
            Element sm = doc.createElement("SignatureMethod");
            sm.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#rsa-sha1");
            sigInf.appendChild(sm);
            
            
            Element ref = doc.createElement("Reference");
            ref.setAttribute("URI", "http://www.w3.org/TR/xml-stylesheet");
            sigInf.appendChild(ref);
            
            Element digMeth = doc.createElement("DigestMethod");
            digMeth.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#sha1");
            ref.appendChild(digMeth);
            
            Element digVal = doc.createElement("DigestValue");
            Text text = doc.createTextNode("pABdjWJGeIB7U4NdYaZwMfhGCfQ=");
            digVal.appendChild(text);
            ref.appendChild(digVal);
            
            
            Element signValue = doc.createElement("SignatureValue");
            Text signValueText = doc.createTextNode("NUvvI0gq5KWk/8pcPVR10VAexelGKcrAYcsOq3D07Uo8X8IIKhsw8WqSVl0hF/IzBqbl0mvBA4SH\n"+
            		"sxhRHUvr2ybCmDrwHDpkRyXPLMFAPUJ14ZbtOosDhEj6Qt+FoA3Bo+LhqQMeMOX4pcBItY6l0SgK\n"+
            		"P1UP0ehlBnv6A+cRjg0=");
            signValue.appendChild(signValueText);
            root.appendChild(signValue);
            
            Element keyInfo = doc.createElement("KeyInfo");
            root.appendChild(keyInfo);
            	Element x509data = doc.createElement("X509Data");
            	keyInfo.appendChild(x509data);
            		Element x509SubjName = doc.createElement("X509SubjectName");
            		Text x509SubjNameText = doc.createTextNode("CN=topon,OU=Security,O=WeeGee,ST=California,C=US");
            		x509SubjName.appendChild(x509SubjNameText);
            		x509data.appendChild(x509SubjName);
            		
            		Element x509Cert = doc.createElement("X509Certificate");
            		Text x509CertText = doc.createTextNode("MIICVTCCAb4CAxAAATANBgkqhkiG9w0BAQQFADCBjTEPMA0GA1UEChMGV2VlR2VlMREwDwYDVQQL\n"+
"EwhTZWN1cml0eTEhMB8GCSqGSIb3DQEJARYScHRvcGlua2FAZ21haWwuY29tMRIwEAYDVQQHEwlQ\n"+
"YWxvIEFsdG8xEzARBgNVBAgTCkNhbGlmb3JuaWExCzAJBgNVBAYTAlVTMQ4wDAYDVQQDEwVUb3Bv\n"+
"bjAeFw0xMDExMTQyMjMwMjNaFw0xMTExMTQyMjMwMjNaMFYxCzAJBgNVBAYTAlVTMRMwEQYDVQQI\n"+
"EwpDYWxpZm9ybmlhMQ8wDQYDVQQKEwZXZWVHZWUxETAPBgNVBAsTCFNlY3VyaXR5MQ4wDAYDVQQD\n"+
"EwV0b3BvbjCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAwfbXC94QZpRKoT1fexMuHO62W7ZM\n"+
"9gHIYQB3gkKW92uk3vo5R94rQIrvj9FD7SJ/43Xmmt9mZicFKN+Bj4K6KDm9KfFo5yumdVRA9RQQ\n"+
"JBs3a3ORJDiwr0AdXA/tziXcftCQiZvmnz5ywMa7AZVEwl84UImUYv/0yqW1NkPP/C8CAwEAATAN\n"+
"BgkqhkiG9w0BAQQFAAOBgQB5C2PJAu/xIz90SFmr7WU0ZPj0YSKBS2RhIiCqYe2UFF183msXxz/D\n"+
"fXRIX6Y0HS5uSJxafV0WDiYm6GgUf38zthOu70AxjAGj8PF2WH41RB/CKstziXHZDEzWW+6xy5R8\n"+
"dfXnL2LTTsAQjtkdHHESbXxYcfoFD8SSTMYj+VE49g==");
            		x509Cert.appendChild(x509CertText);
            		x509data.appendChild(x509Cert);

            		URLClient urlClient = new URLClient();
            		urlClient.setDocumentStore("http://localhost:8080/signatures/DocumentsStore");
            		urlClient.setSignatureStore("http://localhost:8080/signatures/SignatureStore");
            		int respCode = urlClient.sendXMLSignatureToServer(doc);
            		System.out.println("AAAAAAAAAAAAAAAAA: " +respCode);

  

            		
            		
		
				
   
            
            
            
            
            
            
            //add a text element to the child
            
            /////////////////
            //Output the XML

            //set up a transformer

            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            String xmlString = sw.toString();

            //print xml
            //System.out.println("Here's the xml:\n\n" + xmlString);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
    
    
    private static String bytes2String(byte[] bytes) {
        StringBuilder string = new StringBuilder();
        for (byte b: bytes) {
                String hexString = Integer.toHexString(0x00FF & b);
                string.append(hexString.length() == 1 ? "0" + hexString : hexString);
        }
        return string.toString();
}
    
    

    /**
     * @return private key and certification chain corresponding to it, extracted from
     * given keystore using given password to access the keystore and the same password
     * to access the private key in it. The keystore is considered to have only one
     * entry that contains both certification chain and the corresponding private key.
     * If the certificate has no entries, an exception is trown. It the keystore has
     * several entries, the first is used.
     */
    private PrivateKeyAndCertChain getPrivateKeyAndCertChain(
        KeyStore aKeyStore, String aKeyPassword)
    throws GeneralSecurityException {
        char[] password = aKeyPassword.toCharArray();
        Enumeration aliasesEnum = aKeyStore.aliases();
        if (aliasesEnum.hasMoreElements()) {
            String alias = (String)aliasesEnum.nextElement();
            Certificate[] certificationChain = aKeyStore.getCertificateChain(alias);
            PrivateKey privateKey = (PrivateKey) aKeyStore.getKey(alias, password);
            PrivateKeyAndCertChain result = new PrivateKeyAndCertChain();
            PublicKey publicKey = (PublicKey) aKeyStore.getCertificate(alias).getPublicKey();
            result.mPrivateKey = privateKey;
            result.mCertificationChain = certificationChain;
            result.mPublicKey = publicKey;
            result.mCertificate =(X509Certificate) aKeyStore.getCertificate(alias); 
            
            
            
            result.mPrivateKey = privateKey;
            result.mCertificationChain = certificationChain;
            return result;
        } else {
            throw new KeyStoreException("The keystore is empty!");
        }
    }

    /**
     * @return Base64-encoded ASN.1 DER representation of given X.509 certification
     * chain.
     */
    private String encodeX509CertChainToBase64(Certificate[] aCertificationChain)
    throws CertificateException {
        List certList = Arrays.asList(aCertificationChain);
        CertificateFactory certFactory =
            CertificateFactory.getInstance(X509_CERTIFICATE_TYPE);
        CertPath certPath = certFactory.generateCertPath(certList);
        byte[] certPathEncoded = certPath.getEncoded(CERTIFICATION_CHAIN_ENCODING);
        String base64encodedCertChain = Base64Utils.base64Encode(certPathEncoded);
        return base64encodedCertChain;
    }

    /**
     * Reads the specified file into a byte array.
     */
    private byte[] readFileInByteArray(String aFileName)
    throws IOException {
        File file = new File(aFileName);
        FileInputStream fileStream = new FileInputStream(file);
        try {
            int fileSize = (int) file.length();
            byte[] data = new byte[fileSize];
            int bytesRead = 0;
            while (bytesRead < fileSize) {
                bytesRead += fileStream.read(data, bytesRead, fileSize-bytesRead);
            }
            return data;
        }
        finally {
            fileStream.close();
        }
    }
    
    private byte[] readURLInByteArray(String afile) throws IOException {
    	StringBuffer strBuff;
    	   URL url = null;
    	   File f=new File("outFile.java"); 
    	   InputStream in =null;
    	   try{
    	      url = new URL(getCodeBase(), afile);
    	      System.out.println(url.getPath() + "" + url);
    	    }
    	    catch(MalformedURLException e){}
    	    
    	    try{
    	      in = url.openStream();
    	      OutputStream out=new FileOutputStream(f);
    	      byte buf[]=new byte[1024];
    	      int len;
    	      while((len=in.read(buf))>0)
    	      out.write(buf,0,len);
    	      out.close();
    	      in.close();
    	    }
    	    catch(IOException e){
    	      e.printStackTrace();
    	    } finally {
					in.close();
    	    }
    	
            //transform output file into byte array
    	    FileInputStream fileStream = new FileInputStream(f);
            try {
                int fileSize = (int) f.length();
                byte[] data = new byte[fileSize];
                int bytesRead = 0;
                while (bytesRead < fileSize) {
                    bytesRead += fileStream.read(data, bytesRead, fileSize-bytesRead);
                }
                return data;
            }
            finally {
                fileStream.close();
            }	
    	
    	
    }

    
    
    
    private byte[] signDocumentRSA(byte[] aDocument, PrivateKey aPrivateKey) {
   
    	byte[] cipherText = null;
    	
    	// Compute signature
    	//Signature instance = Signature.getInstance("SHA1withRSA");
    	//instance.initSign(privateKey);
    	//instance.update((plaintext).getBytes());
    	//byte[] signature = instance.sign();

    	try {
			// Compute digest
			MessageDigest sha1 = MessageDigest.getInstance("SHA1");
			byte[] digest = sha1.digest(aDocument);

			// Encrypt digest
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, aPrivateKey);
			cipherText = cipher.doFinal(digest);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	
    	
    	
    	return cipherText;
    	
    }
    
    
    /**
     * Signs given document with a given private key.
     */
    private byte[] signDocument(byte[] aDocument, PrivateKey aPrivateKey)
    throws GeneralSecurityException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte[] digest = md.digest(aDocument);
        System.out.println(new String(digest));
    	System.out.println(Base64Utils.base64Encode(digest));
    	
    	Signature signatureAlgorithm =
            Signature.getInstance(DIGITAL_SIGNATURE_ALGORITHM_NAME);
        signatureAlgorithm.initSign(aPrivateKey);
        signatureAlgorithm.update(aDocument);
        
        byte[] digitalSignature = signatureAlgorithm.sign();
        return digitalSignature;
    }

    /**
     * Data structure that holds a pair of private key and
     * certification chain corresponding to this private key.
     */
 

    /**
     * Data structure that holds a pair of Base64-encoded
     * certification chain and digital signature.
     */
    static class CertificationChainAndSignatureInBase64 {
        public String mCertChain = null;
        public String mSignature = null;
    }

 
    static class PrivateKeyAndCertChain {
        public PrivateKey mPrivateKey;
        public Certificate[] mCertificationChain;
        public PublicKey mPublicKey;
        public X509Certificate mCertificate;
    } 
    
    
    /**
     * Exception class used for document signing errors.
     */
    static class DocumentSignException extends Exception {
        public DocumentSignException(String aMessage) {
            super(aMessage);
        }

        public DocumentSignException(String aMessage, Throwable aCause) {
            super(aMessage, aCause);
        }
    }

}
