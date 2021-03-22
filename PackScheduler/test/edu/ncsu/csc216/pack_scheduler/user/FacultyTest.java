package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Tests the faculty class
 * 
 * @author Ashten Herr, Aditya Koindena
 *
 */
public class FacultyTest {

	/**
	 * Tests the constructor
	 */
	@Test
	public void testFaculty() {
		Faculty f = new Faculty("Ashten", "Herr", "agherr", "agherr@gmail.com", "pw", 2);

		assertEquals("Ashten", f.getFirstName());
		assertEquals("Herr", f.getLastName());
		assertEquals("agherr", f.getId());
		assertEquals("agherr@gmail.com", f.getEmail());
		assertEquals("pw", f.getPassword());
		assertEquals(2, f.getMaxCourses());

		Faculty f1 = null;

		try {
			f1 = new Faculty("Ashten", "Herr", "agherr", "agherr@gmail.com", "pw", 5);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(f1);
		}

		try {
			f1 = new Faculty("Ashten", "Herr", "agherr", "agherr@gmail.com", "pw", -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(f1);
		}

		try {
			f1 = new Faculty("", "Herr", "agherr", "agherr@gmail.com", "pw", -1);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(f1);
		}

	}

	/**
	 * Tests the toString method
	 */
	@Test
	public void testToString() {
		Faculty f = new Faculty("Ashten", "Herr", "agherr", "agherr@gmail.com", "pw", 2);
		assertEquals("Ashten,Herr,agherr,agherr@gmail.com,pw,2", f.toString());
	}

	/**
	 * Tests the equals method
	 */
	@Test
	public void testEquals() {
		Faculty f = new Faculty("Ashten", "Herr", "agherr", "agherr@gmail.com", "pw", 2);
		Faculty f1 = new Faculty("Ashten", "Herr", "agherr", "agherr@gmail.com", "pw", 2);
		Faculty f2 = new Faculty("Ashtenasdf", "Herr", "agherr", "agherr@gmail.com", "pw", 2);

		assertTrue(f.equals(f1));
		assertTrue(f1.equals(f));

		assertFalse(f2.equals(f1));
	}

	/**
	 * Tests hashcode method
	 */
	@Test
	public void testHashCode() {
		Faculty f = new Faculty("Ashten", "Herr", "agherr", "agherr@gmail.com", "pw", 2);
		Faculty f1 = new Faculty("Ashten", "Herr", "agherr", "agherr@gmail.com", "pw", 2);
		Faculty f2 = new Faculty("Ashtenasdf", "Herr", "agherr", "agherr@gmail.com", "pw", 2);

		assertEquals(f1.hashCode(), f.hashCode());
		assertNotEquals(f1.hashCode(), f2.hashCode());
	}

}
