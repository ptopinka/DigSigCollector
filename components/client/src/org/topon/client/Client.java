	package org.topon.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.ParserConfigurationException;
import org.topon.client.gui.ClientGUI;

import org.topon.configuration.Configuration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;
import org.topon.configuration.identity.IdentityJKS;
import org.topon.signatures.jdk16.DetachedXMLSignatureJDK;
import org.topon.signatures.XMLSignatureHelper;
import org.w3c.dom.Document;

public class Client {

	private IdentityInterface identity;
	private String docURL;
	private Document xmlSignature;
	
	
	public Client() {
		init();
	}

	public void run() {
		menu();
	}


	public void init() {
		Configuration config = Configuration.getInstance();
		identity = IdentityFactory.createIdentity(config);
	}
	
	
	/**
	 * Nacte vstup jako cislo
	 * @return  cislo jinak spadne
	 */
	public int readLineNumber() {
		InputStreamReader stdin =
			new InputStreamReader(System.in);
		BufferedReader console =
			new BufferedReader(stdin);
		int	i1 = 0;
		String	s1;
		try
		{
			System.out.print("Zadej vstup ");
			s1 = console.readLine();
			i1 = Integer.parseInt(s1);

		}
		catch(IOException ioex)
		{
			System.out.println("Input error");
			System.exit(1);
		}
		catch(NumberFormatException nfex)
		{
			System.out.println("\"" +
					nfex.getMessage() +
			"\" is not numeric");
			System.exit(1);
		}
		return i1;
	}

	/**
	 * Zadej vstup jako String
	 * @return  nacteny String
	 */
	public String readLineString() {
		InputStreamReader stdin =
			new InputStreamReader(System.in);
		BufferedReader console =
			new BufferedReader(stdin);
		String	s1 = new String();
		try
		{
			s1 = console.readLine();
		}
		catch(IOException ioex)
		{
			System.out.println("Input error");
			System.exit(1);
		}
		return s1.trim();
	}
	

	public void setURLDocument() {
		System.out.println("Insert URL of document you want to sign :");
		System.out.print(".... ");
		docURL = readLineString();
		System.out.println("nacteny dokument: " + docURL);
	}
	public void singDocument(){
		if(docURL == null) {
			System.out.println("document URL wasn't set");
		} else {
			DetachedXMLSignatureJDK detXMLSig = new DetachedXMLSignatureJDK();
			try {
				xmlSignature = detXMLSig.createDetachedSignature(
						docURL,
						identity.getKeyPair().getPrivate(),
						identity.getX509Cetificate());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			} catch (MarshalException e) {
				e.printStackTrace();
			} catch (XMLSignatureException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}
		}
		System.out.println("xxxxxxxxxxxxxx" + xmlSignature);
	}
	
	public void validateXMLSignature() {
		System.out.println("Insert file name  xml file signature :");
		System.out.print(".... ");
		String fileName = readLineString();
		boolean valid = XMLSignatureHelper.validateSignatureInXMLFile(fileName);
		if(valid) {
			System.out.println("Xml signature "+ fileName +" is valid");
		} else {
			System.out.println("Xml signature " + fileName + " is not valid ");
		}
	}
	
	public void exportToXML() {
		System.out.println("Insert file name of output xml document :");
		System.out.print(".... ");
				
		String fileName = readLineString();
		if(xmlSignature != null) {
			XMLSignatureHelper.storeSignatureToXMLFile(xmlSignature, fileName);
		} else {
			System.out.println("No xml signature created so nothing is stored");
		}
		
	}
	public void sendItToServer() {
		//TODO set SERVER url to configuration
		System.out.println("Sending xml file to server");
	}


	public void help() {
		String help = "Zadej tvoji volbu\n" +
		"1:   Set Document URL\n" +
		"2:   Sign Document\n" +
		"3:   Export to XML File\n" +
		"4:   Set it to server\n" +
		"5:   Validate xml signature\n" +
		"9:   Help\n"+
        "0:   exit program";
		System.out.println(help);

	}

	public void menu() {
		int in;
		while((in = readLineNumber()) != 0)  {

			switch(in) {
			case 1:
				setURLDocument();
				break;
			case 2:
				singDocument();
				break;
			case 3:
				exportToXML();
				break;
			case 4:
				sendItToServer();
				break;
			case 5:
				validateXMLSignature();
				break;

			case 9:
				help();
				break;
			case 0:
				System.exit(0);
			default:
				System.err.println("Neplatny vstup");

			}
		}

	} 	


	public static void main (String []args) {
      //   Client cli = new Client();
	//	 cli.run();

			Configuration config = Configuration.getInstance();
            ClientGUI cliGui = new ClientGUI(config);
            cliGui.run();
           
 
 }
}





