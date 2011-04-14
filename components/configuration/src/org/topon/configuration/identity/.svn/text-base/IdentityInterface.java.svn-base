package org.topon.configuration.identity;

import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

import org.topon.configuration.Configuration;

public interface IdentityInterface {

	public static final String JKS_KEYSTORE = "JKS";
	public static final String P12_KEYSTORE = "P12";
	
	
	public abstract void init();

	public abstract String getAlias();

	public abstract X509Certificate getX509Cetificate();

	public abstract PrivateKey getPrivateKey();

	public abstract KeyPair getKeyPair();

	public abstract KeyStore getKeyStore();

	public abstract Configuration getConfiguration();

}