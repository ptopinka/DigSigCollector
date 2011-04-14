package org.topon.signatures.apache;

//Copyright 2001-2006 Systinet Corp. All rights reserved.
//Use is subject to license terms.

import org.topon.signatures.interfaces.XMLSignatureInt;
import org.w3c.dom.Document;
import org.apache.xml.security.Init;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.signature.XMLSignature;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

/**
 *
 */
public class DetachedXMLSignatureApache implements XMLSignatureInt {
	// initialize apache XML security runtime
	static{
		if (!Init.isInitialized()){
			Init.init();
		}
	}


	public Document createDetachedSignature(String url, PrivateKey privateKey, X509Certificate cert) 
	throws XMLSecurityException, ParserConfigurationException {
		// only RSA private keys supported
		if (!"RSA".equalsIgnoreCase(privateKey.getAlgorithm())){
			throw new IllegalArgumentException("Only RSA private keys supported!");
		}
		// create a document for XML signature
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();

		XMLSignature xmlSignature = new XMLSignature(document, "", XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1, Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
		xmlSignature.addDocument(url);
		xmlSignature.addKeyInfo(cert);
		xmlSignature.sign(privateKey);
		document.appendChild(xmlSignature.getElement());
		return document;
	}

	public boolean verifyDetachedSignature(
			X509Certificate cert, 
			Document detachedDSig) throws XMLSecurityException, ParserConfigurationException {
		// parse the signature
		XMLSignature dSig = new XMLSignature(detachedDSig.getDocumentElement(),"");
		if (cert == null){
			// trying to extract certificate from key info
			KeyInfo keyInfo = dSig.getKeyInfo();
			if (keyInfo.containsX509Data()){
				cert = keyInfo.getX509Certificate();
			}
		}
		if (cert == null){
			throw new IllegalArgumentException("The supplied signature does not contain certificate!");
		}
		boolean retVal = dSig.checkSignatureValue(cert);
		return retVal;
	}


	public X509Certificate getX509Certificate(Document detachedDSig) 
	throws XMLSecurityException, ParserConfigurationException {
		X509Certificate cert = null;
		XMLSignature dSig = new XMLSignature(detachedDSig.getDocumentElement(),"");
			// trying to extract certificate from key info
			KeyInfo keyInfo = dSig.getKeyInfo();
			if (keyInfo.containsX509Data()){
				cert = keyInfo.getX509Certificate();
			}
		if (cert == null){
			throw new IllegalArgumentException(
					"The supplied signature does not contain certificate!");
		}
		return cert;
	}



}
