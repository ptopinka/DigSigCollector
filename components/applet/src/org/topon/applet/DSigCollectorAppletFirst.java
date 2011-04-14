package org.topon.applet;

import java.applet.Applet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import javax.swing.*;

import org.w3c.dom.Document;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.GeneralSecurityException;
import java.security.KeyStoreException;
import org.topon.configuration.Configuration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;
import org.topon.configuration.identity.IdentityJKS;
import org.topon.signatures.jdk16.DetachedXMLSignatureJDK;

import org.topon.signatures.XMLSignatureHelper;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;

import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.ParserConfigurationException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


public class DSigCollectorAppletFirst extends Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SIGN_BUTTON_CAPTION_PARAM = "signButtonCaption";
	private static final String SIGNATURE_FIELD_PARAM = "signatureField";
    private static final String FILE_NAME_FIELD_PARAM = "fileNameField";

    private static final String PKCS12_KEYSTORE_TYPE = "PKCS12";
    private static final String X509_CERTIFICATE_TYPE = "X.509";
    private static final String CERTIFICATION_CHAIN_ENCODING = "PkiPath";
    private static final String DIGITAL_SIGNATURE_ALGORITHM_NAME = "SHA1withRSA";

    private Button mSignButton;

    private Document xmlSignature;
    
    
    public String setFileURL(String message) {
    	return "bla bla: " + message;
    }
    
    
    
    /**
     * Initializes the applet - creates and initializes its graphical user interface.
     * Actually the applet consists of a single button, that fills its surface. The
     * button's caption comes from the applet parameter SIGN_BUTTON_CAPTION_PARAM.
     */
    
    
    
    public void init() {
        String signButtonCaption = this.getParameter(SIGN_BUTTON_CAPTION_PARAM);
        mSignButton = new Button(signButtonCaption);
        mSignButton.setLocation(0, 0);
        Dimension appletSize = this.getSize();
        mSignButton.setSize(appletSize);
        mSignButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                signFile();
            }
        });
        this.setLayout(null);
        this.add(mSignButton);
    }
    
    private void signFile() {
    	

    	
    	JOptionPane.showMessageDialog(this, "doctype");
    	//try {
    	  
    	 JSObject browserWindow = JSObject.getWindow(this);
         JSObject mainForm = (JSObject) browserWindow.eval("document.forms[0]");
         
         //String fileNameFieldName = this.getParameter(FILE_NAME_FIELD_PARAM);
         //JSObject fileNameField = (JSObject) mainForm.getMember(fileNameFieldName);
         //String fileName = (String) fileNameField.getMember("value");
    	
         String doctypefieldName = this.getParameter("doctypeField");
         JSObject doctypefield = (JSObject) mainForm.getMember(doctypefieldName);
         
         String doctype = (String) doctypefield.getMember("value");
         
         JOptionPane.showMessageDialog(this, doctype);

       	 //JOptionPane.showMessageDialog(this, "fileName," + "......." + fileName);
       	 
       	
         
    		
    	//	String fileName = "http://www.sportzbraslav.org/budejovicepraha/img/vysledky_cbp_2010.pdf";
       	 
       	 
       	// sign(fileName);
    	/*
    	} catch (DocumentSignException dse) {
            // Document signing failed. Display error message
            String errorMessage = dse.getMessage();
            JOptionPane.showMessageDialog(this, errorMessage);
        }
    	
         */
 
         
    }
    
    
    
    private void sign(String docURL) throws DocumentSignException {
    
    	  // Show a dialog for selecting PFX file and password
        CertificateFileAndPasswordDialog certFileAndPasswdDlg =
            new CertificateFileAndPasswordDialog();
        if (certFileAndPasswdDlg.run()) {
        	String keyStoreFileName = certFileAndPasswdDlg.getCertificateFileName();
        	
        	String password = certFileAndPasswdDlg.getCertificatePassword();
        	
        	JOptionPane.showMessageDialog(this, "certificate file..."+ keyStoreFileName + "password:"+password);
        	
        	KeyStore userKeyStore = null;
            try {
                userKeyStore = loadKeyStoreFromPFXFile(keyStoreFileName, password);
            } catch (Exception ex) {
                String errorMessage = "Can not read certificate keystore file (" +
                    keyStoreFileName + ").\nThe file is either not in PKCS#12 format" +
                    " (.P12 or .PFX) or is corrupted or the password is invalid.";
                throw new DocumentSignException(errorMessage, ex);
            }
            
            // Get the private key and its certification chain from the keystore
            PrivateKeyAndCertChain privateKeyAndCertChain = null;
            try {
                privateKeyAndCertChain =
                    getPrivateKeyAndCertChain(userKeyStore, password);
            } catch (GeneralSecurityException gsex) {
                String errorMessage = "Can not extract certification chain and " +
                    "corresponding private key from the specified keystore file " +
                    "with given password. Probably the password is incorrect.";
                throw new DocumentSignException(errorMessage, gsex);
            }

            //JOptionPane.showMessageDialog(this, "keyStore(obsah): " + privateKeyAndCertChain.mPrivateKey + "\n" +
            //		privateKeyAndCertChain.mPublicKey );

            JOptionPane.showMessageDialog(this, "budu podepisovat 0");
			DetachedXMLSignatureJDK detXMLSig;
			
			
//			identity.getKeyPair().getPrivate(),
	//		identity.getX509Cetificate());

            JOptionPane.showMessageDialog(this, "budu podepisovat 1");

			try {
				detXMLSig = new DetachedXMLSignatureJDK();
	            JOptionPane.showMessageDialog(this, "budu podepisovat");
				
				xmlSignature = detXMLSig.createDetachedSignature(
						docURL,
						privateKeyAndCertChain.mPrivateKey,
						privateKeyAndCertChain.mCertificate);
	            JOptionPane.showMessageDialog(this, "podepsal jsem");

			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, e.getMessage());
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, e.getMessage());
			} catch (MarshalException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, e.getMessage());
			} catch (XMLSignatureException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, e.getMessage());
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, e.getMessage());
			
				
			}
	       	JOptionPane.showMessageDialog(this, "Signature: " + xmlSignature); 
        	
        	
        }
    	
    }
    
    private PrivateKeyAndCertChain getPrivateKeyAndCertChain(
            KeyStore aKeyStore, String aKeyPassword)
        throws GeneralSecurityException {
            char[] password = aKeyPassword.toCharArray();
            Enumeration aliasesEnum = aKeyStore.aliases();
            if (aliasesEnum.hasMoreElements()) {
                String alias = (String)aliasesEnum.nextElement();
                Certificate[] certificationChain = aKeyStore.getCertificateChain(alias);
                PrivateKey privateKey = (PrivateKey) aKeyStore.getKey(alias, password);
                PublicKey publicKey = (PublicKey) aKeyStore.getCertificate(alias).getPublicKey();
                PrivateKeyAndCertChain result = new PrivateKeyAndCertChain();
                result.mPrivateKey = privateKey;
                result.mCertificationChain = certificationChain;
                result.mPublicKey = publicKey;
                result.mCertificate =(X509Certificate) aKeyStore.getCertificate(alias); 

                return result;
            } else {
                throw new KeyStoreException("The keystore is empty!");
            }
        }
    
  
    /**
     * Loads a keystore from .PFX or .P12 file (file format should be PKCS#12)
     * using given keystore password.
     */
    private KeyStore loadKeyStoreFromPFXFile(String aFileName, String aKeyStorePasswd)
    throws GeneralSecurityException, IOException {
        KeyStore keyStore = KeyStore.getInstance(PKCS12_KEYSTORE_TYPE);
        FileInputStream keyStoreStream = new FileInputStream(aFileName);
        JOptionPane.showMessageDialog(this, "keyStoreStream" + keyStoreStream);
        char[] password = aKeyStorePasswd.toCharArray();
        keyStore.load(keyStoreStream, password);

        return keyStore;
    }

    

    /**
     * Data structure that holds a pair of private key and
     * certification chain corresponding to this private key.
     */
    static class PrivateKeyAndCertChain {
        public PrivateKey mPrivateKey;
        public Certificate[] mCertificationChain;
        public PublicKey mPublicKey;
        public X509Certificate mCertificate;
    } 
    
    /**
     * Exception class used for document signing errors.
     */
    static class DocumentSignException extends Exception {
        public DocumentSignException(String aMessage) {
            super(aMessage);
        }

        public DocumentSignException(String aMessage, Throwable aCause) {
            super(aMessage, aCause);
        }
    }

    

}
