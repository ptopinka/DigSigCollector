package org.topon.signatures.interfaces;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.w3c.dom.Document;

public interface XMLSignatureInt {


	/**
	 * Create a detached XML signature
	 * @param url of the document to be signed
	 * @param privateKey private key used for signing (RSA expected)
	 * @param cert X509 certificate used to atache signed information to XML DSig
	 * @return Detached XML signature
	 * @throws XMLSecurityException unable to create signature
	 * @throws ParserConfigurationException unable to create DOM document
	 */
	public abstract Document createDetachedSignature(
			String url, PrivateKey privateKey, X509Certificate cert)
	throws Exception; 

	/**
	 * Verifies detached XML signature
	 * @param cert X509 certificate used to verify XML DSig (can be null to obtain cert from signature)
	 * @param detachedDSig document that contains detached XML signature
	 * @return <code>true</code> if verified
	 * @throws XMLSecurityException unable to verify signature
	 * @throws ParserConfigurationException unable to create DOM document
	 */
	public boolean verifyDetachedSignature(
			X509Certificate cert, Document detachedDSig) 
	throws Exception;
	/**
	 * Get X509Certificate from Given XMLSignature DOM Document
	 * @param detachedDSig detachedDSig document that contains detached XML signature
	 * @return X509Certificate if it's included
	 * @throws Exception
	 */
	public X509Certificate getX509Certificate(Document detachedDSig) 
	throws Exception;
}