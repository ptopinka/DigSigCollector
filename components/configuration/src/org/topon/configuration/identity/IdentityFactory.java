package org.topon.configuration.identity;

import org.topon.configuration.Configuration;

public class IdentityFactory {

	
	private IdentityFactory() {
		
	}
	public static IdentityInterface createIdentity(Configuration config) 
//TODO dopsat Identity factory a IdentityP12 zkotrolovat kod aby to fungovalo
	{
		if(config.getKeystoreType().equals(IdentityInterface.JKS_KEYSTORE)) {
			return new IdentityJKS(config);
		} else if(config.getKeystoreType().equalsIgnoreCase(IdentityInterface.P12_KEYSTORE)) {
			return new IdentityP12(config);
		} else {
	//		throw new IdentityException("Invalid KeyStore type in your configuation akceptable is JKS or P12");
			throw new IllegalArgumentException("Invalid KeyStore type in your configuation akceptable is JKS or P12");
		}
	}
}
