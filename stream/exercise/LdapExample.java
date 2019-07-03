import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;


/**
 * Hello world!
 *
 */
public class LdapExample 
{
public static String LDAP_TLS_CONNECTION_STRING = "ldaps://ldaphost:636";
	public static String SECURITY_CREDENTIALS_PASSWORD = "password";
	public static String SECURITY_PRINCIPAL_USER_DETAIL = "uid=username,ou=internal,ou=people,dc=dcvaluelike_company,dc=com";
	
    public static void main( String[] args )
    {
    	String keystorePath = "C:/Program Files (x86)/Java/jdk1.7.0_80/jre/lib/security/cacerts";
    	System.setProperty("javax.net.ssl.keyStore", keystorePath);
    	System.setProperty("com.sun.jndi.ldap.object.disableEndpointIdentification", "true");//FOR JDK 8
    	System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
    	
    	Hashtable<String, String> env = new Hashtable<String, String>();
    	env.put(Context.PROVIDER_URL, LDAP_TLS_CONNECTION_STRING);
    	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    	env.put(Context.SECURITY_AUTHENTICATION, "simple");
    	env.put(Context.SECURITY_PRINCIPAL, SECURITY_PRINCIPAL_USER_DETAIL);
    	env.put(Context.SECURITY_CREDENTIALS, SECURITY_CREDENTIALS_PASSWORD);
    	env.put(Context.REFERRAL, "follow");
    	//env.put(Context.SECURITY_PROTOCOL, "ssl");
    	env.put("java.naming.ldap.version", "3");
    	 
    	DirContext ctx = null;
    	 
    	try {
    		ctx = new InitialDirContext(env);
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(2);
            constraints.setCountLimit(2);  
            
            NamingEnumeration<SearchResult> results = ctx.search("ou=external,ou=people,dc=dcvaluelike_company,dc=com", "(uid=region-userou)", constraints);
            
            while (results.hasMoreElements())
            {
                SearchResult result = results.nextElement();
                String dn = result.getNameInNamespace();
                Attributes attributes = result.getAttributes(); 
                String uid = (String) attributes.get("uid").get();
                
                System.out.println("dn:" + dn + " uid:" + uid);
                 
                 
            }            
    				
    		// Use your context here...
    	} catch (NamingException e) {
    		System.out.println("Problem occurs during context initialization !");
    		e.printStackTrace();
    	}    	
    	
    }
    }
