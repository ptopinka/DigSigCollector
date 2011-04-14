package org.topon.configuraton.tests;

import junit.framework.TestCase;

import org.topon.configuration.Configuration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;

public class ConfigurationTest extends TestCase{
	
	public void testConfiguration() {
		Configuration config = Configuration.getInstance();
		String jmenoKey = config.getKeystore();
		assertEquals("jmeno keystoru neni stejne", jmenoKey, "PavelTopinka.p12");
		assertEquals("Spatny typkeystoru",config.getKeystoreType(),"p12");
		assertEquals("Spatne nactene heslo" ,config.getPassword(),"changeit");
		assertEquals("Nespravne URL Sig Store", config.getSignatureStoreURL(),"https://localhost:8443/dsigcollector");

		
		
	}

}
