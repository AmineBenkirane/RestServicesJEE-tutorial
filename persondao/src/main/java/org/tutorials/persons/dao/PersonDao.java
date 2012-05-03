/**
 * 
 */
package org.tutorials.persons.dao;

import java.util.List;

import org.tutorials.persons.dao.exceptions.TechnicalException;
import org.tutorials.persons.dao.model.Person;


/**
 * @author a.benkirane
 *
 */
public interface PersonDao {

	public void create(Person person);
	public void update(Person person);
	public void delete(Person person);
	public Person findByPrimaryKey(String dn) throws TechnicalException;
	public List<Person> findByName(String name);
	public List<Person> findAll();
	
}
