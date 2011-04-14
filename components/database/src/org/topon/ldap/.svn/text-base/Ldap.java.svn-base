package org.topon.ldap;

import javax.naming.directory.InitialDirContext;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.directory.Attributes;
import javax.naming.directory.Attribute;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import java.util.Hashtable;
import java.io.FileOutputStream;

public class Ldap {
    public static void main(String[] args) throws Exception {
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://topon:64441");
        DirContext ctx = new InitialDirContext(env);
        SearchControls constraints;
        constraints = new SearchControls();
        constraints.setCountLimit(0);
//        constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

	        //NamingEnumeration ne = ctx.search("ou=rocnik_1,ou=CVUT,dc=in,dc=systinet,dc=com", "ObjectClass=Person",constraints);
	        NamingEnumeration ne = ctx.search("ou=CVUT,dc=in,dc=systinet,dc=com", "ObjectClass=Person",constraints);

	        //        NamingEnumeration ne = ctx.search("ou=CVUT,dc=in,dc=systinet,dc=com",null );
 
        while (ne.hasMore()) {
            SearchResult se = (SearchResult) ne.next();
            Attributes attrs = se.getAttributes();
            System.out.println("============" +se.getName());
            NamingEnumeration ine = attrs.getAll();
            while (ine.hasMore()) {
                Attribute attr = (Attribute) ine.next();
                System.out.println(attr);
                if (attr.getID().equals("userCertificate")) {
                    byte[] value = (byte[]) attr.get();
                    FileOutputStream fos = new FileOutputStream("c:\\work\\topon.crt");
                    fos.write(value);
                    fos.close();
                }
            }
        }
    }
}
