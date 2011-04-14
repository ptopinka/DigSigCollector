package org.topon.timestamp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.security.cert.CertificateEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ess.ESSCertID;

import org.bouncycastle.asn1.tsp.TimeStampResp;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.jce.PrincipalUtil;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.tsp.TSPAlgorithms;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TSPUtil;
import org.bouncycastle.tsp.TSPValidationException;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampRequestGenerator;
import org.bouncycastle.tsp.TimeStampResponse;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.tsp.TimeStampTokenInfo;
import org.topon.configuration.Configuration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;
import org.w3c.dom.Document;




public class TimeStamp {
	
	/** String of the document going to sign*/
	private String urlDocument;

	/** Returned timeStamp String version*/
	private String timeStampString;
	
	private byte[] timeStamp; 
	
	public String getUrlDocument() {
		return urlDocument;
	}


	public void setUrlDocument(String urlDocument) {
		this.urlDocument = urlDocument;
	}

	/**
	 * Do basic initialization
	 */
	public void init() {
		
	}
	
	
	public  Document generateTimeStamp() throws NoSuchAlgorithmException, IOException {
		
		Document doc = null;
		
		Security.addProvider (new org.bouncycastle.jce.provider.BouncyCastleProvider());
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        dbfac.setNamespaceAware(true);
        DocumentBuilder docBuilder = null;
		try {
			docBuilder = dbfac.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        doc = docBuilder.newDocument();
		
		//String ocspUrl = "http://timestamping.edelweb.fr/service/tsp";
	    String ocspUrl = "http://timestamping.edelweb.fr/service/tsp";
	    //String ocspUrl = "http://info.szikszi.hu:8080/tsa";
	    
	    
	    //byte[] digest = "hello".getBytes();
	    byte[] digest = calculateMessageDigest();
	    OutputStream out = null;

	    try {
	        
	    	TimeStampRequestGenerator reqgen = new TimeStampRequestGenerator();
//	        TimeStampRequest req = reqgen.generate(TSPAlgorithms.SHA1, digest);
	    	System.out.println(new String(digest));
	    	byte[] mess = new byte[20];
	    	
	        TimeStampRequest req = reqgen.generate(TSPAlgorithms.SHA1, mess);

	        
	        
	        byte request[] = req.getEncoded();

	        URL url = new URL(ocspUrl);
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();

	        con.setDoOutput(true);
	        con.setDoInput(true);
	        con.setRequestMethod("POST");
	        con.setRequestProperty("Content-type", "application/timestamp-query");

	        con.setRequestProperty("Content-length", String.valueOf(request.length));
	        out = con.getOutputStream();
	        out.write(request);
	        out.flush();

	        if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
	            throw new IOException("Received HTTP error: " + con.getResponseCode() + " - " + con.getResponseMessage());
	        }
	        InputStream in = con.getInputStream();
	        
	        TimeStampResp resp = TimeStampResp.getInstance(new ASN1InputStream(in).readObject());
	        TimeStampResponse response = new TimeStampResponse(resp);	
	        if(response != null) {
	        	TimeStampToken tok = response.getTimeStampToken();
	        	TimeStampTokenInfo toki = tok.getTimeStampInfo();
	        	
	        	System.out.println(response.getTimeStampToken().getTimeStampInfo().getGenTime());
	        } else
	        	System.out.println("leda hovno machale");
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return doc;
	}
	
	
	
	
	
	
	
	
	
	
	public boolean verifyTimestampInXMLSignature(Document doc) {
		boolean ok = true;
		Configuration config = Configuration.getInstance();
		IdentityInterface ident = IdentityFactory.createIdentity(config);
		try {
			ok = validate(ident.getX509Cetificate(),config.getDocumentsStoreURL());
		} catch (CertificateExpiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateNotYetValidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TSPValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TSPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ok;
	}
	
	
    private boolean validate(
    		            X509Certificate cert,
    		             String provider)
    		             throws TSPException, TSPValidationException,
    		            CertificateExpiredException, CertificateNotYetValidException, NoSuchProviderException
    		        {
    	boolean ok = true;
    	CMSSignedData tsToken;
    	          SignerInformation tsaSignerInfo;
    	    
    	         Date genTime;
    	      
    	         TimeStampTokenInfo tstInfo;
    	          
    	         ESSCertID   certID;	   
    	
    	TSPUtil.validateCertificate(cert);
		 
		 //cert.checkValidity(tstInfo.getGenTime());
 
    		        return ok;
    		        }
    		     
 
	
	
	
	
	private byte[] calculateMessageDigest()
    throws NoSuchAlgorithmException, IOException {
		SHA1Digest md = new SHA1Digest();

		byte[] dataBytes = urlDocument.getBytes();
		int nread = dataBytes.length;
		md.update(dataBytes, 0, nread);
		byte[] result = new byte[32];
		md.doFinal(result, 0);
		return result;
	}
	
	
	public static void main(String[] args) throws Exception {
		Security.addProvider (new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		//String ocspUrl = "http://timestamping.edelweb.fr/service/tsp";
	    String ocspUrl = "http://timestamping.edelweb.fr/service/tsp";
	    //String ocspUrl = "http://info.szikszi.hu:8080/tsa";
	    TimeStamp ts = new TimeStamp();
	    
	    //byte[] digest = "hello".getBytes();
	    byte[] digest = ts.calculateMessageDigest();
	    OutputStream out = null;

	    try {
	    	TimeStampRequestGenerator reqgen = new TimeStampRequestGenerator();
//	        TimeStampRequest req = reqgen.generate(TSPAlgorithms.SHA1, digest);
	    	System.out.println(new String(digest));
	    	byte[] mess = new byte[20];
	    	
	        TimeStampRequest req = reqgen.generate(TSPAlgorithms.SHA1, mess);

	        
	        
	        byte request[] = req.getEncoded();

	        URL url = new URL(ocspUrl);
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();

	        con.setDoOutput(true);
	        con.setDoInput(true);
	        con.setRequestMethod("POST");
	        con.setRequestProperty("Content-type", "application/timestamp-query");

	        con.setRequestProperty("Content-length", String.valueOf(request.length));
	        out = con.getOutputStream();
	        out.write(request);
	        out.flush();

	        if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
	            throw new IOException("Received HTTP error: " + con.getResponseCode() + " - " + con.getResponseMessage());
	        }
	        InputStream in = con.getInputStream();
	        
	        TimeStampResp resp = TimeStampResp.getInstance(new ASN1InputStream(in).readObject());
	        TimeStampResponse response = new TimeStampResponse(resp);	
	        if(response != null) {
	        	TimeStampToken tok = response.getTimeStampToken();
	        	TimeStampTokenInfo toki = tok.getTimeStampInfo();
	        	
	        	System.out.println(response.getTimeStampToken().getTimeStampInfo().getGenTime());
	        } else
	        	System.out.println("leda hovno machale");
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}