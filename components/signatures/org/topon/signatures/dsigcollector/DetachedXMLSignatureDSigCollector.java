package org.topon.signatures.dsigcollector;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.topon.signatures.interfaces.XMLSignatureInt;
import org.topon.signatures.test.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class DetachedXMLSignatureDSigCollector implements XMLSignatureInt {

	public static String DIGITAL_SIGNATURE_ALGORITHM_NAME = "SHA1withRSA";
	


	public  Document createDetachedSignature( String url ,PrivateKey privateKey, X509Certificate cert) {
		Document doc = null;
	
		
		
		
		byte[] digest = null;
			
			byte[] documentToSign = null;
		        try {
		            //documentToSign = readFileInByteArray(aFileName);
		            documentToSign = readURLInByteArray(url);
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
		        signature.initSign(privateKey);
		        signature.update(documentToSign);
		        byte[] signatureVal = signature.sign();
				//System.out.println("DigitalSignature : " + Base64.encode(signatureVal));      

		        
		        
				
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
		            doc = docBuilder.newDocument();
		            
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
		            ref.setAttribute("URI", url);
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
		            		Text x509SubjNameText = doc.createTextNode(cert.getSubjectDN().getName());//get such string CN=topon, OU=Security, O=WeeGee, ST=California, C=US
		            		x509SubjName.appendChild(x509SubjNameText);
		            		x509data.appendChild(x509SubjName);
		            		
		            		Element x509Cert = doc.createElement("X509Certificate");
		            		Text x509CertText = doc.createTextNode(Base64.encode(cert.getEncoded()));


		            		x509Cert.appendChild(x509CertText);
		            		x509data.appendChild(x509Cert);


				
				} catch (InvalidKeyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  catch (SignatureException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();			
				} catch (Exception e) {
		            e.printStackTrace();
		        }
		
		
		return doc;
	}
	
	
	  private static byte[] readURLInByteArray(String afile) throws IOException {
	    	StringBuffer strBuff;
	    	   URL url = null;
	    	   File f=new File("outFile.java"); 
	    	   InputStream in =null;
	    	   try{
	    	      url = new URL( afile);
	    	     // System.out.println(url.getPath() + "....... " + url);
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
	
	
	
	
	
	
	
	@Override
	public boolean verifyDetachedSignature(X509Certificate cert,
			Document detachedDSig) throws Exception {
		boolean isOK = verifyDSigColSignature( detachedDSig);
		return isOK;
	}

	public  boolean verifyDSigColSignature(Document doc) {
		boolean isOK = false;
		String digestValueStr = null;
		String certificateStr = null;
		String signatureStr = null;
		
		String urlDocument= null;
		
		
		Element root = null;
	    
	    // Get the XML root node by examining the children nodes
		//System.out.println("Root element " + doc.getDocumentElement().getNodeName());
		root = doc.getDocumentElement();
	    
		NodeList docNodes = root.getElementsByTagName("SignedInfo"); // jsem signed info
		
		
		for(int i = 0; i < docNodes.getLength(); i++) {  //mame jenom jeden Element (SignedInfo)
			Node fstNode = docNodes.item(i);
			//System.out.println(fstNode.getNodeName());
			
			if(fstNode.getNodeType() == Node.ELEMENT_NODE) {
			
				
				Element signInf = (Element) docNodes.item(i);
				NodeList referList = signInf.getElementsByTagName("Reference");
				//System.out.println(referList.getLength());
				Element refer = (Element)referList.item(0);
				urlDocument = refer.getAttribute("URI");

				NodeList digValList = refer.getElementsByTagName("DigestValue");
				//System.out.println(digValList.getLength());
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

			//X509Certificate cert = X509Certificate.getInstance(Base64.decode(certificateStr));
			InputStream inStream = new ByteArrayInputStream(Base64.decode(certificateStr));
			 CertificateFactory cf = CertificateFactory.getInstance("X.509");
			 X509Certificate cert = (X509Certificate)cf.generateCertificate(inStream);
			 inStream.close();
			
		   	Signature signature =
	            Signature.getInstance(DIGITAL_SIGNATURE_ALGORITHM_NAME);
	        signature.initVerify(cert.getPublicKey());
	        signature.update(documentToSign);
		   	if(signature.verify(Base64.decode(signatureStr))) {
		   		isOK = true;	
		   	} 
			
	        
		}  catch (RuntimeException e) {
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
		} catch (java.security.cert.CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		return isOK;
	}
	
	

	
	
	
	
	
	
	
	@Override
	public X509Certificate getX509Certificate(Document detachedDSig)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
