package netdb.courses.softwarestudio.recommender.util;

import static org.junit.Assert.*;

import netdb.courses.softwarestudio.recommender.util.CharArraySet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple {@ink CharArrayMapSet}.
 */
public class CharArraySetTest {
	@Before
	public void startup() {
		
	}
	
	@After
	public void teardown() {
		
	}
	
	@Test
	//@Test(expected= IndexOutOfBoundsException.class)
	public void testAddAndContainForCharArrayOfSameLength() {
		char[] buf = new char[32];
		buf[0] = 'a';
		buf[1] = 'b';
		buf[2] = 'c';
		int len = 3;		
		
		CharArraySet set = new CharArraySet();
		set.add(buf, len);
		assertTrue(set.contains(buf, len));
		
		// buf will have high chance be hashed to another bucket
		buf[0] = 'x';
		buf[1] = 'y';
		buf[2] = 'z';
		assertFalse(set.contains(buf, len));
		
		buf[0] = 'a';
		buf[1] = 'b';
		buf[2] = 'c';
		assertTrue(set.contains(buf, len));
	}
}
