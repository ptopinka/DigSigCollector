package org.topon.client;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Vector;

import org.topon.configuration.Configuration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;
import org.topon.configuration.identity.IdentityJKS;
import org.topon.database.DocumentModel;
import org.topon.signatures.XMLSignatureHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class URLClient {

	//az prepises JKS a P12 tak tady to taky zmenit
	private IdentityInterface identity;
	private Configuration configuration; 
	private String signaturesStoreURL;
	private String documentStoreURL;

	public URLClient (IdentityInterface ident) {
		this.identity = ident;
		configuration = identity.getConfiguration();
		init();
	}


	public URLClient() {
		
	}
	
	public void setSignatureStore(String sigStoreURL) {
		signaturesStoreURL = sigStoreURL;
	}
	public void setDocumentStore(String docStoreURL) {
		documentStoreURL = docStoreURL;
	}
	
	
	private void init() {
		signaturesStoreURL = configuration.getSignatureStoreURL();
		documentStoreURL = configuration.getDocumentsStoreURL();
	}	

	private HttpURLConnection getURLConnectionToServer(String resourceURL) throws IOException {
		HttpURLConnection connection = null;
		URL _url = new URL(resourceURL);
		connection = (HttpURLConnection)_url.openConnection();
		return connection;
	}


	
	public Vector getDocumentsToSignFromServer() {
		Vector vector = new Vector();
		HttpURLConnection connection = null;
		try {
			connection = getURLConnectionToServer(documentStoreURL);
			InputStream input = connection.getInputStream();
			Document documentsToSign = XMLSignatureHelper.getXMLDocumentFromInputStream(input);
			vector = XMLSignatureHelper.getVectorDocumentsFromXmlDOMDoc(documentsToSign,vector);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		return vector;
	}
	
	
	
	/**
	 * Send XMLSignature DOM Document to the server...
	 * @param doc
	 * @throws URLClientException
	 */
	public int sendXMLSignatureToServer(Document doc) throws URLClientException {
            int respCode = 0;
            HttpURLConnection connection = null;
		try {
			connection = getURLConnectionToServer(signaturesStoreURL);
			connection.setRequestProperty("Cache-Control","no-cache, no-store");
			connection.setRequestProperty("Pragma","no-cache");
			connection.setRequestProperty("Content-Type","text/xml");
			connection.setRequestMethod("POST");
			connection.setDefaultUseCaches(false);
			connection.setInstanceFollowRedirects(false);
			connection.setDoOutput(true);

			/* get outputs stream from URL Connection*/
			OutputStream outputStream = connection.getOutputStream();
			/* Get ByteArrayOuptutStram with readed xmlSignature DOM */
			ByteArrayOutputStream baos = XMLSignatureHelper.printDOMDocumentToOutputStream(doc);
			/* change OutputStream to InputStream*/
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			
			/* Write data from InputStream to OutputStream to URL connection*/
			int input = 0;
			while( (input =  bais.read()) != -1) {
				outputStream.write(input);
			}
			outputStream.flush();
			outputStream.close();
			
			respCode = connection.getResponseCode();

			if(respCode != 200) {
				throw new URLClientException("http response code: " + respCode);
			} 
			//TODO Udelat nejakou rozumnou reakci na odpoved od Serveru


		} catch (ProtocolException e) {
			throw new URLClientException(e);
		} catch (IOException e) {
			throw new URLClientException(e);
		} finally {
			connection.disconnect();
		}

                return respCode;
        }

    /**
     * Send XML Signature to the Server stored in local store in xml file
     * @param filename
     * @throws URLClientException
     */
	public int sendXMLSignatureToServer(String filename) throws URLClientException {
		HttpURLConnection connection = null;
		int respCode;
                try {
			File file = XMLSignatureHelper.getSignatureXMLFile(filename);
			 connection = getURLConnectionToServer(signaturesStoreURL);
			connection.setRequestProperty("Cache-Control","no-cache, no-store");
			connection.setRequestProperty("Pragma","no-cache");
			connection.setRequestProperty("Content-Type","text/xml");
			connection.setRequestMethod("POST");
			connection.setDefaultUseCaches(false);
			connection.setInstanceFollowRedirects(false);
			connection.setDoOutput(true);


			OutputStream outputStream = connection.getOutputStream();
			OutputStreamWriter out = new OutputStreamWriter(outputStream);            

			BufferedReader in = new BufferedReader(new FileReader(file));
			String str;
			while((str = in.readLine()) != null) {
				out.write(str);
			}
			out.close();



			respCode = connection.getResponseCode();

			if(respCode != 200) {
				throw new URLClientException("http response code: " + respCode);
			} else {
				System.out.println("Signature was succesfully sent");
				System.out.println("response Code = " + respCode);

			}


			/*            
            InputStream is = null;

            try {
                is = connection.getInputStream();

                int x;
        	    while ( (x = is.read()) != -1)
        	    {
        		System.out.write(x);
        	    }
        	    is.close();

            } catch (IOException e) {
            }
			 */            

			//  fis.read(b);

		} catch (IOException e) {
			throw new URLClientException(e);
		} finally {
			connection.disconnect();
		}
                return respCode;
        }
	public static void main(String[] args) {
		Configuration config = Configuration.getInstance();
		IdentityInterface ident = IdentityFactory.createIdentity(config);
		URLClient client = new URLClient(ident);

		Vector vector = client.getDocumentsToSignFromServer();
	    System.out.println(((DocumentModel)vector.get(2)).getDescription());
		
	}
}
