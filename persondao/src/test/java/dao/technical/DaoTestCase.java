/**
 * 
 */
package dao.technical;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

/**
 * @author a.benkirane
 *
 */
public abstract class DaoTestCase extends TestCase {

	protected ApplicationContext context;
	
	
	@Override
	public void setUp() {
		context = new ClassPathXmlApplicationContext(new String[] {
				"classpath*:spring/person-dao-applicationContext.xml" });
	}
	
}
