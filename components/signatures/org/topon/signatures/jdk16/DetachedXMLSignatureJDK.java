package org.topon.signatures.jdk16;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.topon.signatures.DSigCollectorException;
import org.topon.signatures.X509KeySelector;
import org.topon.signatures.interfaces.XMLSignatureInt;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import javax.swing.JOptionPane;
public class DetachedXMLSignatureJDK implements XMLSignatureInt {


	public DetachedXMLSignatureJDK() {
			
	}
	/**
	 * 
	 */

	
	public DetachedXMLSignatureJDK(String name) throws DSigCollectorException {
		throw new DSigCollectorException();
	}





	/* (non-Javadoc)
	 * @throws XMLSignatureException 
	 * @throws MarshalException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchAlgorithmException 
	 * @throws ParserConfigurationException 
	 * @see org.topon.signatures.interfaces.XMLSignatureInt#createDetachedSignature(String documentURL,PrivateKey privKey,X509Certificate cert)
	 */
	public Document createDetachedSignature(
			String documentURL, 
			PrivateKey privKey,
			X509Certificate x509cert) throws MarshalException, XMLSignatureException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, ParserConfigurationException {


		// First, create a DOM XMLSignatureFactory that will be used to
		// generate the XMLSignature and marshal it to DOM.
		XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

		// Create a Reference to an external URI that will be digested
		// using the SHA1 digest algorithm
		Reference ref = null;

		/*		try {
			Collections.singletonList
			   (fac.newTransform
			    (Transform.BASE64, (TransformParameterSpec) null));
		} catch (NoSuchAlgorithmException e4) {
			e4.printStackTrace();
		} catch (InvalidAlgorithmParameterException e4) {
			e4.printStackTrace();
		}
		 */		

		ref = fac.newReference(documentURL,
				fac.newDigestMethod(DigestMethod.SHA1,
						null));





		// Create the SignedInfo
		SignedInfo si = null;
		si = fac.newSignedInfo(
				fac.newCanonicalizationMethod
				(CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS,
						(C14NMethodParameterSpec) null),
						fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
						Collections.singletonList(ref));  // <-- pouziti ref


		// Create a KeyValue containing the RSA PublicKey from Identity
		KeyInfoFactory kif = fac.getKeyInfoFactory();
		List x509Content = new ArrayList();
		x509Content.add(x509cert.getSubjectX500Principal().getName());
		x509Content.add(x509cert);
		X509Data xd = kif.newX509Data(x509Content);
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(xd));
		/*		
TODO old to be deleted
		KeyValue kv = null;
		try {
			kv = kif.newKeyValue(kp.getPublic());
		} catch (KeyException e1) {
			e1.printStackTrace();
		}
		// Create a KeyInfo and add the KeyValue to it
		KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));
		 */





		// Create the XMLSignature (but don't sign it yet)
		XMLSignature signature = fac.newXMLSignature(si, ki);

		// Create the Document that will hold the resulting XMLSignature
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true); // must be set
		Document doc = null; 

		doc = dbf.newDocumentBuilder().newDocument();

		// Create a DOMSignContext and set the signing Key to the RSA
		// PrivateKey and specify where the XMLSignature should be inserted
		// in the target document (in this case, the document root)



		DOMSignContext signContext = new DOMSignContext(privKey, doc);


		// Marshal, generate (and sign) the detached XMLSignature. The DOM
		// Document will contain the XML Signature if this method returns
		// successfully.
		signature.sign(signContext);
		return doc;
	}


	/* (non-Javadoc)
	 * @see org.topon.signatures.interfaces.XMLSignatureInt#validateXMLSignature(java.security.cert.X509Certificate cert, org.w3c.dom.Document)
	 */
	public boolean verifyDetachedSignature(
			X509Certificate cert, Document detachedDSig) 
	throws MarshalException, XMLSignatureException { 

		boolean valid = false;

		// Find Signature element.
		NodeList nl =
			detachedDSig.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
		if (nl.getLength() == 0) {
			throw new IllegalArgumentException(
			"The supplied Document does not contain XML Signature !");
		}

		DOMValidateContext valContext = null;		
		if(cert == null) {
			valContext= new DOMValidateContext
			(new X509KeySelector(), nl.item(0)); 
		} else {
			valContext = new DOMValidateContext
			(cert.getPublicKey(), nl.item(0)); 

		}
		if(valContext == null) {
			throw new IllegalArgumentException("The supplied signature does not contain certificate!");
		}
		

		XMLSignatureFactory factory = 
			XMLSignatureFactory.getInstance("DOM"); 

		XMLSignature signature = 
			factory.unmarshalXMLSignature(valContext); 



		valid = signature.validate(valContext); 
		return valid;
	}
	public X509Certificate getX509Certificate(Document detachedDSig)
			throws Exception {
		/* 
		 * TODO Implement getting X509Certificate from XML Document in JDK
		 * signature
		*/
		return null;
	}



	







}