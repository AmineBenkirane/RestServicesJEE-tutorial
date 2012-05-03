package dao.person;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tutorials.persons.dao.PersonDao;
import org.tutorials.persons.dao.model.Person;


import dao.technical.DaoTestCase;


/**
 * 
 */

/**
 * @author a.benkirane
 *
 */
public class PersonDaoTest extends DaoTestCase {

	Logger logger = LoggerFactory.getLogger(PersonDaoTest.class);
	
	
	@Test
	public void testFindAllPerson() {		
		PersonDao personDao = (PersonDao) context.getBean("personDao");

		List<Person> allPersons = personDao.findAll();
		
		assertTrue(allPersons.size() > 0);
	}
	
	@Test
	public void testCreatePerson() {
		
		
	}
	
	
}
