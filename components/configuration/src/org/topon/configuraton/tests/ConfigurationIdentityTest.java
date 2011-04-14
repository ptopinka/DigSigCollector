package org.topon.configuraton.tests;

import java.util.Vector;

import junit.framework.TestCase;

import org.topon.configuration.Configuration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;

public class ConfigurationIdentityTest extends TestCase{
	
	public void testURLClient() {
		Configuration config = Configuration.getInstance();
		IdentityInterface identity;
		identity = IdentityFactory.createIdentity(config);
		assertNotNull(identity);
		Configuration configi = identity.getConfiguration();
		
		assertNotNull(configi);
		assertEquals("Konfigurace nejsou stejne...", configi, config);
		
		String keystore = configi.getKeystore();
		assertEquals("ocekavane jmeno keystoru" ,keystore,"TestKeystore");
		
		
		
	}

}
