/**
 * 
 */
package org.tutorials.persons.dao.impl;

import java.util.List;

import javax.naming.Name;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.NameAlreadyBoundException;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AbstractContextMapper;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;
import org.tutorials.persons.dao.PersonDao;
import org.tutorials.persons.dao.exceptions.TechnicalException;
import org.tutorials.persons.dao.model.Person;


/**
 * @author a.benkirane
 *
 */
public class PersonDaoImpl implements PersonDao {

	Logger logger = LoggerFactory.getLogger(PersonDaoImpl.class);

	public static String OU_USERS = "users";
	private LdapTemplate ldapTemplate;

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}


	public void create(Person person) {
		DirContextAdapter context = new DirContextAdapter(person.getDistinguishedName());
		mapToContext(person, context);
		try {
			ldapTemplate.bind(context);
		}
		catch(NameAlreadyBoundException nabe) {
			throw new TechnicalException(nabe.getMessage());
		}
	}

	public void update(Person person) {
		DirContextOperations context = ldapTemplate.lookupContext(person.getDistinguishedName());
		mapToContext(person, context);
		ldapTemplate.modifyAttributes(context);
	}

	public void delete(Person person) {
		ldapTemplate.unbind(person.getDistinguishedName());
	}

	public Person findByPrimaryKey(String dn) throws TechnicalException {
		Person personFound = null;
		try {
			personFound = (Person) ldapTemplate.lookup(dn, getContextMapper());
		} catch (Exception e) {
			throw new TechnicalException("Impossible de trouver l'entrée dans le LDAP"); 
		}

		return personFound;
	}

	private String getDnForUser(String uid) {
		Filter f = new EqualsFilter("uid", uid);
		List result = ldapTemplate.search(DistinguishedName.EMPTY_PATH, f.toString(),
				new AbstractContextMapper() {
			protected Object doMapFromContext(DirContextOperations ctx) {
				return ctx.getNameInNamespace();
			}
		});

		if(result.size() != 1) {
			throw new RuntimeException("User not found or not unique");
		}

		return (String)result.get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Person> findByName(String name) {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass", "person")).and(new WhitespaceWildcardsFilter("cn",name));
		return ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Person> findAll() {
		EqualsFilter filter = new EqualsFilter("objectclass", "person");
		return ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
	}

	protected ContextMapper getContextMapper() {
		return new PersonContextMapper();
	}

	protected Name buildDn(String uid, String client) {
		DistinguishedName dn = new DistinguishedName();
		dn.add("ou", OU_USERS);
		dn.add("ou", client);
		dn.add("uid", uid);

		return dn;
	}

	protected void mapToContext(Person person, DirContextOperations context) {
		context.setAttributeValues("objectclass", new String[] {"top", "person", "inetOrgPerson"});
		context.setAttributeValue("uid", person.getUid());
		context.setAttributeValue("cn", person.getFullName());
		context.setAttributeValue("sn", person.getLastName());
		context.setAttributeValue("givenName", person.getGivenName());
		context.setAttributeValue("mail", person.getEmail());

		context.setDn(buildDn(person.getUid(), person.getClient()));
	}

	private static class PersonContextMapper extends AbstractContextMapper {
		public Object doMapFromContext(DirContextOperations context) {
			Person person = new Person();
			person.setUid(context.getStringAttribute("uid"));
			person.setFullName(context.getStringAttribute("cn"));
			person.setLastName(context.getStringAttribute("sn"));
			person.setGivenName(context.getStringAttribute("givenName"));
			person.setEmail(context.getStringAttribute("mail"));
			person.setDistinguishedName(context.getNameInNamespace());
			return person;
		}
	}
}
