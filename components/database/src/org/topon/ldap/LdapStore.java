package org.topon.ldap;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.topon.configuration.ServerConfiguration;
import org.topon.database.UserModel;

public class LdapStore {
	ServerConfiguration ldapConfig;
	DirContext ctx;
	SearchControls constraints = new SearchControls();

	public LdapStore(ServerConfiguration serverconfig)
			throws LdapStoreException {
		this.ldapConfig = serverconfig;
		try {
			ctx = connectToLdap();
		} catch (NamingException e) {
			throw new LdapStoreException(
					"Cannot create connection to the Server", e);
		}
	}

	private DirContext connectToLdap() throws NamingException {
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapConfig.getLdapProviderUrl());
		DirContext ctx = new InitialDirContext(env);
		return ctx;
	}

	/**
	 *  Odpojeni od LDAPu
	 */
	private void disconnectFromLdap() {
		//TODO implementovat jak se odpojit od LDAPu
	}

	/**
	 * Nacteni uzivatele z LDAPu podle jeho loginu. Jde o nacteni vsech jeho dostupnych
	 * informaci vcetne X509Certifikatu
	 * @param login
	 * @return
	 * @throws LdapStoreException 
	 */
	public UserModel getUser(String login) throws LdapStoreException  {
		UserModel userMod = new UserModel();

	
		String dirPath =  "ou=CVUT,dc=in,dc=systinet,dc=com";
		Attributes matchAttrs = new BasicAttributes(true); // ignore attribute name case
		matchAttrs.put(new BasicAttribute("uid", login));
		
		
		try {
			NamingEnumeration answer = ctx.search(dirPath, matchAttrs);
		
			while (answer.hasMore()) {

				SearchResult sr = (SearchResult)answer.next();
			    Attributes attrs = sr.getAttributes();
			    NamingEnumeration ine = attrs.getAll();
			    userMod = getUserModelFromNamingEnumeration(ine);
			    
			}
		
		} catch (NamingException e) {
			throw new LdapStoreException("Cannot Search given directory", e);
		} catch (CertificateException e) {
			throw new LdapStoreException(
					"Cannot instantiate user:certificate from LDAP ", e);
		}
		return userMod;
	}
	
	/**
	 * create UserModel from LdapSearch NamingEnumeration. It is helper function
	 * @param ine
	 * @return UserModel when ldap search was succesfull
	 * @throws NamingException
	 * @throws CertificateException
	 */
	private UserModel getUserModelFromNamingEnumeration(NamingEnumeration ine)
	throws NamingException, CertificateException{
		UserModel userMod = new UserModel();
		while (ine.hasMore()) {
			Attribute attr = (Attribute) ine.next();

			if(attr.getID().equals("cn")) {
				//System.out.println(attr.get());
				userMod.setCommonName((String) attr.get());
			}
			
			if (attr.getID().equals("uid")) {
				userMod.setUid((String) attr.get());
			}
			if (attr.getID().startsWith("userCertificate")) {
				byte[] value = (byte[]) attr.get();
				ByteArrayInputStream bais = new ByteArrayInputStream(
						value);
				CertificateFactory cf = CertificateFactory
						.getInstance("X.509");
				X509Certificate cert = (X509Certificate) cf
						.generateCertificate(bais);
				userMod.setCertificate(cert);	
			}
		}
		
		return userMod;
	}
	
	
	/**
	 * Vlozeni noveho uzivatele do LDAPu, 
	 * @param userModel
	 * @return true pokud byl uzivatel uspesne vlozen, jinak false
	 */
	public boolean insertUser(UserModel userModel) {
		boolean isInserted = false;
		//TODO implementovat funkci na vkladani uzivatele do LDAPu
		return isInserted;
	}
	

	/**
	 * smazani uzivatel z LDAPu, reprezentovaneho pomoci UserModel
	 * @param userModel  objekt reprezentujici uzivatele
	 * @return true pokud byl uzivatel uspesne smazan
	 */
	public boolean deleteUser(UserModel userModel) {
		boolean isDeleted = false;
		//TODO implementovat funkci na mazani uzivatele z LDAPu
		return isDeleted;
	}
	
	/**
	 * funkce na editaci uzivatele v LDAPu
	 * @param userModel
	 * @return true pokud byl uzivatel uspesne editovan v LDAPu jinak false
	 */
	public boolean editUser(UserModel userModel) {
		boolean isEdit = false;
		//TODO implementovat funkci na editaci uzivatel v LDAPu
		return isEdit;
	}
	
	
	public Vector getUsers() throws LdapStoreException {
		Vector users = new Vector();
		constraints.setCountLimit(0);
		//	    constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
		constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

		//String dirPath = ldapConfig.getLdapSearchBaseGroup();
		//"ou=rocnik_1,ou=CVUT,dc=in,dc=systinet,dc=com";
		String dirPath =  "ou=CVUT,dc=in,dc=systinet,dc=com";
		try {
			NamingEnumeration ne = ctx.search(dirPath, "ObjectClass=Person",
					constraints);
			while (ne.hasMore()) {
				SearchResult se = (SearchResult) ne.next();

				Attributes attrs = se.getAttributes();

				NamingEnumeration ine = attrs.getAll();
				UserModel userMod = new UserModel();
				userMod = getUserModelFromNamingEnumeration(ine);
				users.add(userMod);

			}

		} catch (NamingException e) {
			throw new LdapStoreException("Cannot Search given directory", e);
		} catch (CertificateException e) {
			throw new LdapStoreException(
					"Cannot instantiate user:certificate from LDAP ", e);
		}

		return users;
	}

	public HashMap getUsersHashMap() throws LdapStoreException {
		HashMap hm = new HashMap();
		Vector vector = getUsers();
		for(int i = 0; i < vector.size();i++) {
			UserModel um = (UserModel)vector.get(i);
			String key = um.getUid();
			hm.put(key, um);
		}
		return hm;
	}
	
	
	public static void main(String[] args) throws Exception {
		ServerConfiguration config = ServerConfiguration.getInstance();
		LdapStore ldap = new LdapStore(config);
		Vector users = ldap.getUsers();
		Iterator it = users.iterator();
		while (it.hasNext()) {
			UserModel userMod = (UserModel) it.next();
			System.out.println(userMod);
			System.out.println();
		}

		UserModel usMod = ldap.getUser("anicka@fel.cvut.cz");
		System.out.println(usMod);
		
		HashMap hm = ldap.getUsersHashMap();
		Set HostKeys = hm.keySet();
		Iterator It = HostKeys.iterator();
		while (It.hasNext()) {
		String HostNow = (String)(It.next());
		System.out.println("key...." + HostNow);
		System.out.println(HostNow + " - " + hm.get(HostNow));
		}	
	}
}
