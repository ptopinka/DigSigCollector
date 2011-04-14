package org.topon.timestamp;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.topon.configuration.Configuration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;
import org.topon.signatures.interfaces.XMLSignatureInt;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TimeStampHelper {
	
	/**
	 * Add timestamp into existing XML Signature
	 * @param xmlSignature
	 * @param ts
	 * @return
	 */
	public static Document addTimestampIntoXMLSignature(Document xmlSignature, TimeStamp ts) {
		return xmlSignature;
	}

	/**
	 * Get timestamp XML representation like defined in XAdES http://www.w3.org/TR/XAdES/  
	 * @param urlDocument
	 * @return
	 */
	public static Document getTimestamp(String urlDocument) {
		Document doc = null;
		TimeStamp ts = new TimeStamp();
		ts.setUrlDocument(urlDocument);
		ts.init();
		try {
			doc = ts.generateTimeStamp();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;
	}
	
	
	/**
	 * Add into XML Signature timestamp as defined in http://www.w3.org/TR/XAdES/
	 * @param xmlSignature
	 * @param timestamp
	 * @return
	 */
	public static Document addTimestampIntoXMLSignature(Document xmlSignature,Document timestamp  ) {

        
        NodeList signature = xmlSignature.getElementsByTagName("Signature");
		Element object = xmlSignature.createElement("Object");
		Element qualProps = (Element) object.appendChild(timestamp);
		
		
		xmlSignature.appendChild(object);
 		
		return xmlSignature;
	}



	/**
	 * Add into XML Signature timestamp as defined in http://www.w3.org/TR/XAdES/
	 * @param xmlSignature
	 * @param timestamp
	 * @return
	 */
	public static boolean verifyTimestampInXMLSignature(Document xmlSignature  ) {

        
        Element tsp = (Element) xmlSignature.getElementsByTagName("QualifyingProperties");
		Configuration config = Configuration.getInstance();
		IdentityInterface ident = IdentityFactory.createIdentity(config);
        
		TimeStamp ts = new TimeStamp();
		boolean isOK = true;
		ts.init();
		isOK = ts.verifyTimestampInXMLSignature(xmlSignature);
		
		
		
 		
		return isOK;
	}


}
