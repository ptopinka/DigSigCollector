package org.topon.configuration.identity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.topon.configuration.Configuration;

public class IdentityP12 implements IdentityInterface {

	private Configuration configuration;

	private KeyStore keystore;
	private String keyStorePath;
	private String keyStoreType;


	private  String alias;
	private X509Certificate certificate;
	private KeyPair keyPair;
	private PublicKey publicKey;
	private PrivateKey privateKey;


	private String keyStorePassword;
	private char[] keyStorePasswordChar;




        
        
        
	public IdentityP12(Configuration config) {
		this.configuration = config;
		init();
	}


	private KeyStore loadKeyStore() {
        KeyStore ks = null;
        try {
			ks = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(keyStorePath);
			
			ks.load(fis, keyStorePasswordChar);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ks;
	}

	private KeyPair loadAllFromKeystore() {
        //return cert = (X509Certificate)ks.getCertificate(alias);
        //return privKey =(PrivateKey) ks.getKey(alias, passwd);
		try {
			Key key = keystore.getKey(alias, keyStorePasswordChar);
			if(key  instanceof PrivateKey) {
                Certificate cert = keystore.getCertificate(alias);
                this.certificate = (X509Certificate)cert;
                // Get public key
                this.publicKey = cert.getPublicKey();
                this.privateKey = (PrivateKey)key;
                // Return a key pair
                return new KeyPair(publicKey, (PrivateKey)key);

			}
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	public void init() {
		keyStorePath = configuration.getKeystore();
		alias = configuration.getAlias();
		keyStoreType = configuration.getKeystoreType();
		keyStorePassword = configuration.getPassword();
		keyStorePasswordChar = keyStorePassword.toCharArray();
		
		this.keystore = loadKeyStore();
		this.keyPair = loadAllFromKeystore();
	}


	public String getAlias() {
		return this.alias;
	}

	public KeyPair getKeyPair() {
		return this.keyPair;
	}

	public KeyStore getKeyStore() {
		return this.keystore;
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public X509Certificate getX509Cetificate() {
		return this.certificate;
	}

	public Configuration getConfiguration() {
		return this.configuration;
	}
	
	public static void main(String[] args) {
		IdentityInterface identity = IdentityFactory.createIdentity(Configuration.getInstance());
		System.out.println(identity.getAlias());
		System.out.println(identity.getX509Cetificate().getIssuerDN().getName());
                System.out.println(identity.getX509Cetificate().getSubjectDN().getName());
	}



}
