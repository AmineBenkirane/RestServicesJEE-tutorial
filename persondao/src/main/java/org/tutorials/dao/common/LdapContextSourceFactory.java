/**
 * 
 */
package org.tutorials.dao.common;

import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * @author a.benkirane
 *
 */
public class LdapContextSourceFactory extends LdapContextSource {
	
	public static ContextSource getLdapContextSource() throws Exception {
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl("ldap://localhost:389");
		ldapContextSource.setBase("dc=intent,dc=com");
		ldapContextSource.setUserDn("cn=Directory Manager");
		ldapContextSource.setPassword("admin");
		ldapContextSource.afterPropertiesSet();
		return ldapContextSource;
	}

}