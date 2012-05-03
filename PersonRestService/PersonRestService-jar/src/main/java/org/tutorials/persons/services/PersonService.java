/**
 * 
 */
package org.tutorials.persons.services;

import java.util.List;

import org.tutorials.persons.services.exceptions.PersonBusinessException;

import org.tutorials.persons.dto.PersonDTO;


/**
 * @author a.benkirane
 *
 */
public interface PersonService {

	public PersonDTO createPerson(PersonDTO person)  throws PersonBusinessException;

	public List<PersonDTO> searchPerson(String name);
	
	public PersonDTO viewPerson(String dn) throws PersonBusinessException;
}
