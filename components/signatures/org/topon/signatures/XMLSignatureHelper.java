package org.topon.signatures;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import javax.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Vector;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.cert.CertificateException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.topon.applet.SignTest;
import org.topon.client.URLClient;
import org.topon.configuration.Configuration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;
import org.topon.configuration.identity.IdentityJKS;
import org.topon.database.DocumentModel;
import org.topon.signatures.apache.DetachedXMLSignatureApache;
import org.topon.signatures.interfaces.XMLSignatureInt;
import org.topon.signatures.jdk16.DetachedXMLSignatureJDK;
import org.topon.signatures.test.Base64;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;


/**
 * Represent data structure of xml signature
 * @author topon
 *
 */


public class XMLSignatureHelper {
	
	public static String DIGITAL_SIGNATURE_ALGORITHM_NAME = "SHA1withRSA";

	//TODO -export  dist dir to configuration
	static String baseDir = "dist"+File.separator+"signatures";



	private static final void handleError(Throwable ex) {
		ex.printStackTrace();
		// ... handle error here...
	}

	/**
	 * Append application specific tags into xml signature document
	 * @param document
	 * @return
	 */
	public static Document addUserInfoToSignature(Document document) {

		Element signature = document.getDocumentElement();

		// initially it has no root-element, ... so we create it:
		Element root = document.createElement("digital-signature");

		Element subjInfo = document.createElement("subject-information");
		subjInfo.appendChild(document.createElement("subject"));
		subjInfo.appendChild(document.createElement("date"));
		subjInfo.appendChild(document.createElement("time"));
		subjInfo.appendChild(document.createElement("timestamp"));


		root.appendChild(subjInfo);
		root.appendChild(signature);
		// we can add an element to a document only once,
		// the following calls will raise exceptions:
		document.appendChild(root);
		document.setXmlStandalone(true);

		return document;
	}

	/**
	 * Store xml Signature represented by dom.Document into xml file
	 * @param doc - XML Signature
	 * @param fileName - name of output file
	 */
	public static boolean storeSignatureToXMLFile(Document doc, String fileName) {
		boolean stored = false;
                OutputStream os;
                File absolute = new File(fileName);
                
                String outputPath = new String();
                if(absolute.getParentFile().isAbsolute()) {
                    outputPath = fileName;  //path is absolute
                } else {
                    outputPath = baseDir + File.separator + fileName; //relative, store it into %project_dir%/dist/signatures
                }
                
		File file = new File(outputPath);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		try {
			os = new FileOutputStream(outputPath);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			//trans.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			//trans.setOutputProperty(OutputKeys.INDENT, "no");
			//trans.setOutputProperty(OutputKeys.METHOD, "xml");

			trans.transform(new DOMSource(doc), new StreamResult(os));
                        stored = true;
		} catch (FileNotFoundException e) {
			handleError(e);
		} catch (TransformerConfigurationException e) {
			handleError(e);
		} catch (TransformerException e) {
			handleError(e);
		}
                return stored;
        }

	/**
	 * Read xml Signature by given name from local Signature store
	 * @param filename
	 * @return File object with xml signature
	 * @throws Exception when file doesn't exists
	 */
	public static File getSignatureXMLFile(String filename) throws IOException{
		File file = new File(baseDir + File.separator + filename);
		if(!file.exists())
			throw new  IOException("given xml signature file doesn't exist :" + filename);
		return file;
	}


	/**
	 * Read xml siganture from xml file and parse it into Document
	 * @param filename
	 * @return parsed xml signature, when doesn't exists it returns empty Document
	 */
	public static Document getXMLSignatureFromXMLFile(String filename) {
		File file = new File(filename);
		if(!file.exists()) {
			throw new IllegalArgumentException("given file doesn't exist");
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc = null; 

		if(file.exists()) {
			try {
				doc = dbf.newDocumentBuilder().parse(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
			
                        //TODO udelat test zda nactene xml je opravdu xml signatura
                        return doc;

		} 
		//return empty document 
		try {
			doc = dbf.newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return doc;
	}

        /**
         * Validate that XML DOM document contains XML Signature
         */ 
	public static boolean verifyXMLDocumentContainsXMLSignature(Document doc) {
            //TODO implement method verifiing that XML DOM Doc contains XML Signature
            boolean isOK = false;
            if(doc != null) {
                isOK = true;
            }
            return isOK;
        }
        
        
        
        /**
	 * client read xml from server and create Vector with DocumentModel
	 * @param doc
	 * @param vector
	 * @return
	 */
	public static Vector getVectorDocumentsFromXmlDOMDoc(Document doc, Vector vector) {
			
	    Element root = null;
	    
	    // Get the XML root node by examining the children nodes
	    NodeList list = doc.getChildNodes();
	    for (int i = 0; i < list.getLength(); i++) {
	        if (list.item(i) instanceof Element) {
	            root = (Element)list.item(i);
	            break;
	        }
	    }
		NodeList docNodes = root.getElementsByTagName(DocumentModel.DOC_EL);
		for(int i = 0; i < docNodes.getLength(); i++) {
			
			Element docelm = (Element)docNodes.item(i);
			DocumentModel docModel = new DocumentModel();

			String docid = docelm.getAttribute(DocumentModel.DOCIID_ATT);
			docModel.setDocid(new Integer(docid).intValue());
			docModel.setDocURL(docelm.getAttribute(DocumentModel.DOCURL_ATT));

			long valstart = new Long(docelm.getAttribute(DocumentModel.VALIDSTART_ATT)).longValue();
			docModel.setValidStart(new Long(valstart).longValue());
			
			long valend = new Long(docelm.getAttribute(DocumentModel.VALIDEND_ATT)).longValue();
			docModel.setValidEnd(valend);
		
			//Nacteni metadat 
			NodeList metadataNode = docelm.getElementsByTagName(DocumentModel.METADATA_EL);
			Element metadataElm = (Element)metadataNode.item(0); //only one description
			//vytazeni textu description
			NodeList descrNode = metadataElm.getElementsByTagName(DocumentModel.DESCRIPTION_EL);
			docModel.setDescription(((Element)descrNode.item(0)).getAttribute(DocumentModel.TEXT_ATT));
			
		
			vector.add(docModel);
		}
		return vector;
	}
	
	
	
	
	
	public static Document createXMLWithDocuments(Vector docVect) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc = null; 
		try {
			doc = dbf.newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element root = doc.createElement(DocumentModel.DOCUMENTS_EL);

		Iterator it = docVect.iterator();
		while(it.hasNext()) {
			DocumentModel docMod = (DocumentModel)it.next();
			
			Element docElem = doc.createElement(DocumentModel.DOC_EL);
			Attr docid = doc.createAttribute(DocumentModel.DOCIID_ATT);
			docid.setNodeValue(docMod.getDocid()+"");
			docElem.setAttributeNode(docid);
			
			docElem.setAttribute(DocumentModel.DOCURL_ATT, docMod.getDocURL());
			docElem.setAttribute(DocumentModel.VALIDSTART_ATT, docMod.getValidStart()+"");		
			docElem.setAttribute(DocumentModel.VALIDEND_ATT, docMod.getValidEnd()+"");
			
			Element metadata = doc.createElement(DocumentModel.METADATA_EL);
			Element description = doc.createElement(DocumentModel.DESCRIPTION_EL);
			description.setAttribute(DocumentModel.TEXT_ATT, docMod.getDescription()); 
			metadata.appendChild(description);
			
			docElem.appendChild(metadata);
			
			root.appendChild(docElem);

		}
		
		
		doc.appendChild(root);
		return doc;
	}


	
	
	
	
	

	/** 
	 * Validate xml signature which is stored in xml file
	 * @param filename
	 * @return true if xml signature is valid
	 */
	public static boolean validateSignatureInXMLFile(String filename) {

		Document doc = getXMLSignatureFromXMLFile(filename);
		Configuration config = Configuration.getInstance();
		IdentityInterface ident = IdentityFactory.createIdentity(config);
		XMLSignatureInt genDet = new DetachedXMLSignatureJDK();
		boolean valid = false;
		try {
			valid = genDet.verifyDetachedSignature(ident.getX509Cetificate(),doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valid;

	}

	public static void printDOMDocument(Document doc) {
		OutputStream os;
		os = System.out;

		try {
			XMLSignatureHelper.storeSignatureToXMLFile(doc, "signature.xml");

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			trans.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty(OutputKeys.METHOD, "xml");

			trans.transform(new DOMSource(doc), new StreamResult(os));
		} catch (TransformerConfigurationException e) {
			
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static ByteArrayOutputStream printDOMDocumentToOutputStream(Document doc) {
		ByteArrayOutputStream os;
		os = new ByteArrayOutputStream();

		try {
			// TODO remove, nonsence here XMLSignatureHelper.storeSignatureToXMLFile(doc, "signature.xml");

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer trans = tf.newTransformer();
			//trans.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			//trans.setOutputProperty(OutputKeys.INDENT, "yes");
			//trans.setOutputProperty(OutputKeys.METHOD, "xml");

			trans.transform(new DOMSource(doc), new StreamResult(os));
			
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return os;
		
	}

	/**
	 * Create instance of DOM Documeut from String which contains xml string
	 * @param xmlSignature
	 * @return
	 */
	public static Document getXMLDocumentfromXMLString(String xmlSignature) {
		Document doc = null;
		byte[] sigBytes = xmlSignature.getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(sigBytes);
		
		doc = getXMLDocumentFromInputStream(bais);
		return doc;
	}

	
	
	/**
	 * Create DOM Document from inputStream. Used in Servlet to get xml 
	 * from request.inputStream
	 * @param in
	 * @return
	 */
	public static Document getXMLDocumentFromInputStream(InputStream in) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc = null; 

		try {
			doc = dbf.newDocumentBuilder().parse(in);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return doc;

		//return empty document 

	}

	/**
	 * Transfer DOM Document to String. Used to store XML into database
	 * @param is
	 * @return
	 */
	public static String getSignatureStringFromInputStream(InputStream is) {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String theLine  = new String();
        StringBuffer strBuff = new StringBuffer();
        try {
			while ((theLine = br.readLine()) != null) {
				strBuff.append(theLine);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}       
        return strBuff.toString();
    }
	
	
	

	
	
	
	
	

	public static void main(String[] args) throws Exception{
		System.out.println("........HURA funguje to");
		Configuration config = Configuration.getInstance();
		IdentityInterface ident = IdentityFactory.createIdentity(config);
		XMLSignatureInt xmlSigSUN = new DetachedXMLSignatureJDK();
		String urlDoc = "http://localhost/~topon/budejovicepraha/index.php"; 
		//String urlDoc = "http://localhost:8080/signatures/SignatureStore";

		
		Document doc = xmlSigSUN.createDetachedSignature(
				urlDoc,
				ident.getKeyPair().getPrivate(),
				ident.getX509Cetificate());  

//		doc = addUserInfoToSignature(doc);

		System.out.println("Apache TEST start");
		DetachedXMLSignatureApache xmlSigApacheTest = new DetachedXMLSignatureApache();
		Document docA = xmlSigApacheTest.createDetachedSignature(urlDoc,ident.getKeyPair().getPrivate(),
				ident.getX509Cetificate()); 
		boolean validA = xmlSigApacheTest.verifyDetachedSignature(null,docA);
		System.out.println("Create by Apache,  Validate by Apache (cert is null) is: " + validA);
		System.out.println("Apache TEST finish");		
		
		
		
		OutputStream os;
		if (args.length > 0) {
			os = new FileOutputStream(args[0]);
		} else {
			os = System.out;
		}

		XMLSignatureHelper.storeSignatureToXMLFile(doc, "/Users/topon/work/Diplomka/signature.xml");

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer trans = tf.newTransformer();
		trans.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");
		trans.setOutputProperty(OutputKeys.METHOD, "xml");
		trans.transform(new DOMSource(doc), new StreamResult(os));

		boolean valid = xmlSigSUN.verifyDetachedSignature(null,doc);
		System.out.println("\nCreate by Sun , Verified SUN, Signature is valid: "  +valid);
		valid = xmlSigSUN.verifyDetachedSignature(ident.getX509Cetificate(),doc);
		System.out.println("\nCreate by Sun , Verified SUN, Signature is valid: "  +valid);

		
		
		//generated by JDK


		
/*		
   		URLClient urlClient = new URLClient();
		urlClient.setDocumentStore("http://localhost:8080/dsigcollector/DocumentsStore");
		urlClient.setSignatureStore("http://localhost:8080/dsigcollector/SignatureStore");
		int respCode = urlClient.sendXMLSignatureToServer(doc);
		System.out.println("AAAAAAAAAAAAAAAAA: " +respCode);
*/
		Document sigfromfile = XMLSignatureHelper.getXMLSignatureFromXMLFile("/Users/topon/work/Diplomka/signature.xml");
		valid = xmlSigSUN.verifyDetachedSignature(ident.getX509Cetificate(),sigfromfile);
		System.out.println("\nCreate by Sun from File , Verified SUN, Signature is valid: "  +valid);
		valid = xmlSigSUN.verifyDetachedSignature(null,sigfromfile);
		System.out.println("\nCreate by Sun from File , Verified SUN, Signature is valid: "  +valid);
		

		doc = xmlSigSUN.createDetachedSignature(
				urlDoc,
				ident.getKeyPair().getPrivate(),
				ident.getX509Cetificate());		//validated by APACHE
		//XMLSignatureHelper.printDOMDocument(doc);		
		
		
		
		DetachedXMLSignatureApache xmlSigApache = new DetachedXMLSignatureApache();
		boolean valid2 = xmlSigApache.verifyDetachedSignature(null,doc);
		System.out.println("Create by JDK Validate by Apache (cert is null) is: " + valid2);
		

		
		
		
		
		//Genarated by APACHE
		//TODO when xml signature is created by Apache we cannot validate it
		// with Sun when using Certificate from the signature
		
	//	doc =  xmlSigApache.createDetachedSignature(urlDoc,
	//			ident.getKeyPair().getPrivate(), (X509Certificate)ident.getX509Cetificate());
		
		//XMLSignatureHelper.printDOMDocument(doc);
		
		boolean boo = xmlSigSUN.verifyDetachedSignature(null,doc);
		System.out.println("\nCreate by Apache Validate by JDK (cert is null) is: " + boo);
		 boo = xmlSigSUN.verifyDetachedSignature(ident.getX509Cetificate(),doc);
		System.out.println("Create by Apache Validate by JDK is: " + boo);
		
	}
	
	
	


}



