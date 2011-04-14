package org.topon.signatures.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;

import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.cert.CertificateEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.topon.applet.Base64Utils;
import org.topon.applet.SignTest;
import org.topon.client.URLClient;
import org.topon.configuration.Configuration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;
import org.topon.database.DocumentModel;
import org.topon.signatures.DSigCollectorSignatureHelper;
import org.topon.signatures.XMLSignatureHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;



public class DetachedXMLSigTest {
	
	public static String DIGITAL_SIGNATURE_ALGORITHM_NAME = "SHA1withRSA";
	
	public static void main(String [] args) {

		String urlDocument= "http://www.w3.org/TR/xml-stylesheet";
		Configuration config = Configuration.getInstance();
		IdentityInterface ident = IdentityFactory.createIdentity(config);

		
		
		Document signatureDoc = DSigCollectorSignatureHelper.createDSigColSignature(ident.getPrivateKey(),ident.getX509Cetificate(), urlDocument);
		boolean isOk = DSigCollectorSignatureHelper.verifyDSigColSignature(signatureDoc);
		System.out.println("JE to OK/KO?" +isOk);

        
        
		
		 
		 byte[] digest = null;
		
		byte[] documentToSign = null;
	        try {
	            //documentToSign = readFileInByteArray(aFileName);
	            documentToSign = readURLInByteArray(urlDocument);
	            //System.out.println(new String(documentToSign));
	        } catch (IOException ioex) {
	        	ioex.printStackTrace();
	        }
		
	        MessageDigest sha1 = null;
			try {
				sha1 = MessageDigest.getInstance("SHA1");
            digest = sha1.digest(documentToSign);
	        
	        
	 			
				
				
	    	Signature signature =
	            Signature.getInstance(DIGITAL_SIGNATURE_ALGORITHM_NAME);
	        signature.initSign(ident.getKeyPair().getPrivate());
	        signature.update(documentToSign);
	        byte[] signatureVal = signature.sign();
			System.out.println("DigitalSignature : " + Base64.encode(signatureVal));      

	        
	        
			
				//X509Certificate cert = X509Certificate.getInstance(Base64.decode(certificateStr));
			/*	
		   	Signature signatureV = Signature.getInstance(DIGITAL_SIGNATURE_ALGORITHM_NAME);
		    signatureV.initVerify(ident.getKeyPair().getPublic());
			signatureV.update(documentToSign);
		    if(signatureV.verify(signatureVal)) {
				System.out.println("Message is valid");	
			} else {
			    System.out.println("message is invalid");
			}
			 */  		
	        
		
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
	            ref.setAttribute("URI", urlDocument);
	            sigInf.appendChild(ref);
	            
	            Element digMeth = doc.createElement("DigestMethod");
	            digMeth.setAttribute("Algorithm", "http://www.w3.org/2000/09/xmldsig#sha1");
	            ref.appendChild(digMeth);
	            
	            Element digVal = doc.createElement("DigestValue");
	            Text text = doc.createTextNode(Base64.encode(digest));
	            digVal.appendChild(text);
	            ref.appendChild(digVal);
	            
	            
	            Element signValue = doc.createElement("SignatureValue");
	            Text signValueText = doc.createTextNode(Base64.encode(signatureVal));

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
	            		Text x509CertText = doc.createTextNode(Base64.encode(ident.getX509Cetificate().getEncoded()));


	            		x509Cert.appendChild(x509CertText);
	            		x509data.appendChild(x509Cert);



	            		
	            		System.out.println("je dokument platny (main method) " + verifySignature(doc));

	            	
	            		
			
					
	   
	            
	            
	            
	            
	            
	            
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
	            
	            
	            
	        	
	            

		
			
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (java.security.cert.CertificateEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SignatureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			
			} catch (Exception e) {
	            System.out.println(e);
	        }
	}
	
	public static boolean verifySignature(Document doc) {
		boolean isOK = false;
		String digestValueStr = null;
		String certificateStr = null;
		String signatureStr = null;
		
		System.out.println("Verify mockup\n\n");
		String urlDocument= null;
		
		
		Element root = null;
	    
	    // Get the XML root node by examining the children nodes
		System.out.println("Root element " + doc.getDocumentElement().getNodeName());
		root = doc.getDocumentElement();
	    
		NodeList docNodes = root.getElementsByTagName("SignedInfo"); // jsem signed info
		
		
		for(int i = 0; i < docNodes.getLength(); i++) {  //mame jenom jeden Element (SignedInfo)
			Node fstNode = docNodes.item(i);
			System.out.println(fstNode.getNodeName());
			
			if(fstNode.getNodeType() == Node.ELEMENT_NODE) {
			
				
				Element signInf = (Element) docNodes.item(i);
				NodeList referList = signInf.getElementsByTagName("Reference");
				System.out.println(referList.getLength());
				Element refer = (Element)referList.item(0);
				urlDocument = refer.getAttribute("URI");

				NodeList digValList = refer.getElementsByTagName("DigestValue");
				System.out.println(digValList.getLength());
				Element digVal = (Element)digValList.item(0);
				NodeList fstNm = digVal.getChildNodes();
			    
				digestValueStr = ((Node) fstNm.item(0)).getNodeValue();
			}
		
		}
		
		NodeList sigValList = root.getElementsByTagName("SignatureValue"); // jsem signed info 
		
		Element sigValEl = (Element)sigValList.item(0);
		NodeList sigValElVal = sigValEl.getChildNodes();
		signatureStr = ((Node) sigValElVal.item(0)).getNodeValue();

		
		NodeList keyInfoNodes = root.getElementsByTagName("KeyInfo"); // jsem signed info
		
		for(int i = 0; i < keyInfoNodes.getLength(); i++) {
			Node keyInfoNode = keyInfoNodes.item(i);
			if(keyInfoNode.getNodeType() == Node.ELEMENT_NODE) {
				Element keyInf = (Element) keyInfoNode;
				NodeList x509DataList = keyInf.getElementsByTagName("X509Data");
				Element x509DataEl = (Element)x509DataList.item(0);
				NodeList x509certList = x509DataEl.getElementsByTagName("X509Certificate");
				certificateStr = ((Element)x509certList.item(0)).getChildNodes().item(0).getNodeValue();
			}
		}
		
	
		
		
		//System.out.println("Digest Value : "  + digestValueStr);	
		//System.out.println("Signature Value: " + signatureStr);
		//System.out.println("Certificate Value: " + certificateStr);

		byte[] documentToSign = null;
        try {
            //documentToSign = readFileInByteArray(aFileName);
            documentToSign = readURLInByteArray(urlDocument);
            //System.out.println(new String(documentToSign));
        } catch (IOException ioex) {
        	ioex.printStackTrace();
        }


		try {

			X509Certificate cert = X509Certificate.getInstance(Base64.decode(certificateStr));
			
		   	Signature signature =
	            Signature.getInstance(DIGITAL_SIGNATURE_ALGORITHM_NAME);
	        signature.initVerify(cert.getPublicKey());
	        signature.update(documentToSign);
		   	if(signature.verify(Base64.decode(signatureStr))) {
		   		isOK = true;	
		   	} 
			
	        
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		return isOK;
	}
	
			private static String bytes2String(byte[] bytes) {
			        StringBuilder string = new StringBuilder();
			        for (byte b: bytes) {
			                String hexString = Integer.toHexString(0x00FF & b);
			                string.append(hexString.length() == 1 ? "0" + hexString : hexString);
			        }
			        return string.toString();
			}
			    
	
	
    private static byte[] readURLInByteArray(String afile) throws IOException {
    	StringBuffer strBuff;
    	   URL url = null;
    	   File f=new File("outFile.java"); 
    	   InputStream in =null;
    	   try{
    	      url = new URL( afile);
    	      //System.out.println(url.getPath() + "....... " + url);
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
	
}
