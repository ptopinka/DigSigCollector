package org.topon.configuration.identity;

//import java.security.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyPair;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.topon.configuration.Configuration;

/**
 * User: topon
 * Date: May 7, 2007
 * Time: 10:56:01 AM
 */
public class IdentityJKS implements IdentityInterface {

    private KeyStore keystore;
    private String keyStorePath;
    private String keyStoreType;

	
    private  String alias;
    private X509Certificate cetificate;
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    
    
    private String keyStorePassword;
    private char[] keyStorePasswordChar;
    
    /** document to sign like byte array */
    private byte[] document;
    
    private Configuration configuration;

 
 

    public IdentityJKS(Configuration config) {
    	this.configuration = config;
    	init();
    }
    

    
    /* (non-Javadoc)
	 * @see org.topon.configuration.identity.IdentityInterface#init()
	 */
    public void init() {
    	this.keyStorePath = configuration.getKeystore();
    	this.alias = configuration.getAlias();
    	this.keyStorePassword = configuration.getPassword();

    	this.keystore = loadKeyStore();
    	this.keyStorePasswordChar = keyStorePassword.toCharArray();
    	this.keyPair = loadAllKeysFromKeystore();

    
    	
    }
    
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Get java.security.Kestore instance from JKS
     * @return
     */
    private KeyStore loadKeyStore() {
    	File keySF = new File(keyStorePath);
    	KeyStore keystore = null;
        try {
            FileInputStream is = new FileInputStream(keySF);
            keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(is, keyStorePassword.toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return keystore;

    }
    
    /**
     * Sign document which was previously set up.
     */
    public byte[] sign() throws IdentityException {
    	if(document == null ) {
    		throw new IdentityException("Document to sign must be set");
    	}
    	return doSign();
    }
    
    
    /**
     * Sign given document 
     */
    public byte[] sign(byte[] document) throws IdentityException {
    	this.document = document;
    	return doSign();
    }


    private byte[] doSign() {
        KeyPair keys = loadAllKeysFromKeystore();
        Signature sig = null;
        byte[] sigByte = null;
        PrivateKey privKey = keys.getPrivate();
        try {
            sig = Signature.getInstance("SHA1WithRSA");
            sig.initSign(privKey);
            sig.update(this.document, 0, this.document.length);
            sigByte = sig.sign();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sigByte;
    }
    
    
    
    /**
     * Verify that signature is ok or not.
     * @param key
     * @param buffer
     * @param signature
     * @return
     */
    public  boolean verifySignature( byte[] buffer, byte[] signature) {
        try {
            Signature sig = Signature.getInstance("SHA1WithRSA");
            sig.initVerify(keyPair.getPublic());
            sig.update(buffer, 0, buffer.length);
            return sig.verify(signature);
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static byte[] getSHA1(byte[] input ) {

        byte[] hash = new byte[0];
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            sha.update(input);
            hash = sha.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return hash;
    }

    
    
    
    /**
    * Return KeyPair from the keystore stored under alias and secured by the 
    * password
    * @return KeyPair - private a public key
    */ 
   private KeyPair loadAllKeysFromKeystore() {
        try {
            // Get private key
            Key key = keystore.getKey(alias, keyStorePasswordChar);
            if (key instanceof PrivateKey) {
                // Get certificate of public key
                Certificate cert = keystore.getCertificate(alias);
                this.cetificate = (X509Certificate)cert;
                // Get public key
                this.publicKey = cert.getPublicKey();
                this.privateKey = (PrivateKey)key;
                // Return a key pair
                return new KeyPair(publicKey, (PrivateKey)key);
            }
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }
	/* (non-Javadoc)
	 * @see org.topon.configuration.identity.IdentityInterface#getAlias()
	 */
	public String getAlias() {
		return alias;
	}
	/* (non-Javadoc)
	 * @see org.topon.configuration.identity.IdentityInterface#getX509Cetificate()
	 */
	public X509Certificate getX509Cetificate() {
		return cetificate;
	}

	/* (non-Javadoc)
	 * @see org.topon.configuration.identity.IdentityInterface#getPrivateKey()
	 */
	public PrivateKey getPrivateKey() {
		return keyPair.getPrivate();
	}
	
	/* (non-Javadoc)
	 * @see org.topon.configuration.identity.IdentityInterface#getKeyPair()
	 */
	public KeyPair getKeyPair() {
		return keyPair;
	}
	/* (non-Javadoc)
	 * @see org.topon.configuration.identity.IdentityInterface#getKeyStore()
	 */
	public KeyStore getKeyStore() {
		return keystore;
	}
	/**
	 * Set document to be signed
	 * @param document
	 */
	public void setDocument(byte[] document) {
		this.document = document;
	}



	public Configuration getConfiguration() {
		return this.configuration;
	}



	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
	
	

}
