package org.topon.client.tests;

import java.util.Vector;

import org.topon.client.URLClient;
import org.topon.configuration.Configuration;
import org.topon.configuration.identity.IdentityFactory;
import org.topon.configuration.identity.IdentityInterface;
import org.topon.database.DocumentModel;

import junit.framework.TestCase;

public class ClientGuiTests extends TestCase{
	
	public void testURLClient() {
		Configuration config = Configuration.getInstance();
		IdentityInterface ident = IdentityFactory.createIdentity(config);
		URLClient client = new URLClient(ident);

		Vector vector = client.getDocumentsToSignFromServer();
	    System.out.println(((DocumentModel)vector.get(2)).getDescription());
		assertNotNull(vector);
	}

}
