
package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * StudentTest tests the student class. Includes testing the creation of a
 * valid/invalid student and makes sure all student fields handle
 * proper/improper parameters correctly.
 * 
 * @author Ashten Herr
 * @author Torin Cuany
 */
public class StudentTest {

	/** FIRST_NAME is a valid first name for a student object. */
	public static final String FIRST_NAME = "Ashten";
	/** LAST_NAME is a valid last name for a student object */
	public static final String LAST_NAME = "Herr";
	/** ID is a valid id for a student object */
	public static final String ID = "ID";
	/** EMAIL is a valid email string for a student object */
	public static final String EMAIL = "agherr@ncsu.edu";
	/** HASH_PW represents a valid hashed password string for a student object */
	public static final String HASH_PW = "hashedpassword";
	/**
	 * CREDITS represents a valid credit value for a student which is also the
	 * default value.
	 */
	public static final int CREDITS = 18;

	/**
	 * Tests the creation of a valid student.
	 */
	@Test
	public void testStudent() {
		// test valid student
		User student = null; // Initialize a student reference to null
		try {
			student = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		} catch (IllegalArgumentException e) {
			fail(e.getMessage());
		}

		// test valid student with no maxCredits
		User studentTwo = null; // Initialize a student reference to null
		try {
			studentTwo = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW);
		} catch (IllegalArgumentException e) {
			e.getMessage();
			fail();
		}
		// Check each student is equal even when using different constructor path.
		assertEquals("Check each both students are equal to eachother when all fields are the same.", student,
				studentTwo);

	}

	/**
	 * Tests the setFirstName method checks that a name can not be set to an empty
	 * string or null and checks valid parameters for the setFirstName method.
	 */
	@Test
	public void testSetFirstName() {
		// Create valid student
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		// Try an empty string and catch exception
		try {
			s.setFirstName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Test that the name did not change after being invalid.", s.getFirstName(), FIRST_NAME);
		}
		// Try setting a null name and catch an exception
		try {
			s.setFirstName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Test that the name did not change after being invalid.", s.getFirstName(), FIRST_NAME);
		}
		try {
			s.setFirstName("Nolan");
			assertEquals("Test that the name was changed to the new parameter", s.getFirstName(), "Nolan");
		} catch (IllegalArgumentException e) {
			// Exception should NOT be thrown if the name parameter is valid.
			e.getStackTrace();
			fail();
		}

	}

	/**
	 * Tests the setLastName method, test that setting the name to null or an empty
	 * string throws an IllegalArgumentException. Also tests the setLastName method
	 * of a valid parameter.
	 */
	@Test
	public void testLastName() {
		// Create a valid student
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		// Test setting the last name to a last name will throw an exception.
		try {
			s.setLastName("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Test that the last name did not change after being invalid.", s.getLastName(), LAST_NAME);
		}
		// Test that setting last name to null will throw an exception
		try {
			s.setLastName(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Test that the last name did not change after being invalid.", s.getLastName(), LAST_NAME);
		}

		try {
			s.setLastName("Last");
			assertEquals("Test that the name was changed to the new parameter", s.getLastName(), "Last");
		} catch (IllegalArgumentException e) {
			// Exception should NOT be thrown if the name parameter is valid.
			e.getStackTrace();
			fail();
		}

	}

	/**
	 * Tests the setCredits method, tests that an exception is thrown if the credits
	 * are not set between 3 and 18 (inclusive). Also tests the creation of a
	 * student without a credit value gives a student a value of 18 credits.
	 */
	@Test
	public void testSetCredits() {
		// Create valid Student
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		// Attempt setting max credits to a value above the max and catch exception
		try {
			s.setMaxCredits(19);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Test that maxCredits did not change after setting to invalid value", s.getMaxCredits(), 18);
		}
		// Attempt setting max credits to a value below the min and catch exception
		try {
			s.setMaxCredits(2);
		} catch (IllegalArgumentException e) {
			assertEquals("Test that maxCredits did not change after setting to invalid value", s.getMaxCredits(), 18);
		}
		// test the default parameter of creating a student with no credit value is
		// equal to 18 (the max credit value)
		Student student = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW);
		assertEquals("Test default setCredits value is 18 without a parameter.", 18, student.getMaxCredits());

	}

	/**
	 * Tests the setEmail method checks both invalid and valid email choices. Checks
	 * that a null/empty string will throw an exception. The lack of a '.' or '@'
	 * character will throw an IAE. Checks that an exception is thrown if the '.'
	 * character comes before the '@' character.
	 */
	@Test
	public void testSetEmail() {
		// create a valid student
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		// try setting to an empty string and catch exception
		try {
			s.setEmail("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Check that the email did not change.", s.getEmail(), EMAIL);
		}
		// try setting email to null
		try {
			s.setEmail(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Check that the email did not change.", s.getEmail(), EMAIL);
		}
		// try creating an email without a '.' character
		try {
			s.setEmail("agherr@ncsuedu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Check that the email did not change.", s.getEmail(), EMAIL);
		}
		// try creating an email without a '@' character
		try {
			s.setEmail("agherrncsu.edu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Check that the email did not change.", s.getEmail(), EMAIL);
		}
		// try creating an email where the '@' character is after the '.' character
		try {
			s.setEmail("agherr.ncsu@edu");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Check that the email did not change.", s.getEmail(), EMAIL);
		}
		// try setting to a valid email
		try {
			s.setEmail("email@ncsu.edu");
		} catch (IllegalArgumentException e) {
			fail();
		}
		assertEquals("Check that the email did change", s.getEmail(), "email@ncsu.edu");
	}

	/**
	 * tests the setPassword method, checks an exception is thrown if the
	 * setPassword method is given a null or empty string value.
	 */
	@Test
	public void testSetPassword() {
		// create a valid student
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		// attempt to set password to an empty string
		try {
			s.setPassword("");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Check that the hash password did not change", s.getPassword(), HASH_PW);
		}
		// attempt to set password to null
		try {
			s.setPassword(null);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Check that the hash password did not change", s.getPassword(), HASH_PW);
		}
	}
	
	/**
	 * Tests the setId method, tests setting an ID to null or an empty string throws an IAE
	 */
	@Test
	public void testSetId() {
		Student s = null;
		//try creating a student with an empty string for the id
		try {
			s = new Student(FIRST_NAME, LAST_NAME, "", EMAIL, HASH_PW, CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		}
		//try creating a student with a null string for the id
		try {
			s = new Student(FIRST_NAME, LAST_NAME, null, EMAIL, HASH_PW, CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertNull(s);
		} 
		//create a student with a valid id
		try {
			s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		} catch (IllegalArgumentException e) {
			fail();
		} 
		//make sure the student is no longer null
		assertNotNull(s);
		assertEquals("Check that the ID is the correct value", s.getId(), ID);
		
		
	}
	
	/**
	 * tests the toString method. toString() should generate a comma seperated list
	 */
	@Test
	public void testToString() {
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		String expected = FIRST_NAME + "," + LAST_NAME + "," + ID + "," + EMAIL + "," + HASH_PW + "," + CREDITS;
		assertEquals(s.toString(), expected);
	}

	/**
	 * tests the equals method which should return true if two students fields are
	 * all equal to each other.
	 */
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals() {
		// create a lot of valid students with different fields
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		User s2 = new Student("Outlier", LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		User s3 = new Student(FIRST_NAME, "Outlier", ID, EMAIL, HASH_PW, CREDITS);
		User s4 = new Student(FIRST_NAME, LAST_NAME, "Outlier", EMAIL, HASH_PW, CREDITS);
		User s5 = new Student(FIRST_NAME, LAST_NAME, ID, "outlier@ncsu.edu", HASH_PW, CREDITS);
		User s6 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "Outlier", CREDITS);
		User s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, 17);
		User s8 = null;
		String s9 = "Student";
		// test each different student to be not equal (false) to the first student (s)

		assertFalse(s.equals(s2));
		assertFalse(s.equals(s3));
		assertFalse(s.equals(s4));
		assertFalse(s.equals(s5));
		assertFalse(s.equals(s6));
		assertFalse(s.equals(s7));
		assertFalse(s.equals(s8));
		assertFalse(s.equals(s9));

		// try to test equality for two equal student objects
		assertTrue(s.equals(s));
		assertTrue(s.equals(s1));
		assertTrue(s1.equals(s));

	}

	/**
	 * tests the hashCode method.
	 */
	@Test
	public void testHashCode() {
		// create a lot of valid students with different fields
		User s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		User s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		User s2 = new Student("Outlier", LAST_NAME, ID, EMAIL, HASH_PW, CREDITS);
		User s3 = new Student(FIRST_NAME, "Outlier", ID, EMAIL, HASH_PW, CREDITS);
		User s4 = new Student(FIRST_NAME, LAST_NAME, "Outlier", EMAIL, HASH_PW, CREDITS);
		User s5 = new Student(FIRST_NAME, LAST_NAME, ID, "outlier@ncsu.edu", HASH_PW, CREDITS);
		User s6 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "Outlier", CREDITS);
		User s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, 17);
		
		//check two equal student objects have the same hash code
		assertEquals(s.hashCode(), s1.hashCode());
		
		//check that different fields result in different hash codes
		assertNotEquals(s.hashCode(), s2.hashCode());
		assertNotEquals(s.hashCode(), s3.hashCode());
		assertNotEquals(s.hashCode(), s4.hashCode());
		assertNotEquals(s.hashCode(), s5.hashCode());
		assertNotEquals(s.hashCode(), s6.hashCode());
		assertNotEquals(s.hashCode(), s7.hashCode());

	}
	
	/**
	 * Tests the compareTo method
	 */
	@Test
	public void testCompareTo() {
		// Creates students with differing first names, last names, and ids
		Student s = new Student("Andrew", "Aaron", "AJAaron", EMAIL, HASH_PW, CREDITS);
		Student s1 = new Student("Becca", "Aaron", "BJAaron", EMAIL, HASH_PW, CREDITS);
		Student s2 = new Student("Aden", "Aaron", "ACAaron", EMAIL, HASH_PW, CREDITS);
		Student s3 = new Student("Cam", "Benton", "CJBenton", EMAIL, HASH_PW, CREDITS);
		Student s4 = new Student("Ben", "Benton", "BZBenton", EMAIL, HASH_PW, CREDITS);
		Student s5 = new Student("Ben", "Benton", "BZBenton", EMAIL, HASH_PW, CREDITS);
		
		// Check compare true and false returns for order
		assertEquals(s.compareTo(s2), 1);
		assertEquals(s3.compareTo(s4), 1);
		assertEquals(s2.compareTo(s5), -1);
		assertEquals(s4.compareTo(s5), 0);
		assertEquals(s5.compareTo(s4), 0);
		assertEquals(s1.compareTo(s3), -1);
	}
	
	/**
	 * Tests the canAdd method 
	 */
	@Test
	public void testCanAdd() {
		Student student = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, 3);
		
		Course c = new Course("CSC217", "Java2Lab", "004", 1, "dtkatowi", 10, "MTW");
		Course c2 = new Course("CSC216", "Java2", "002", 3, "dtkatowi", 10, "HF");
		Course c3 = new Course("CSC216", "Java2", "002", 3, "dtkatowi", 10, "MTW");
		
		assertTrue(student.canAdd(c));
		assertTrue(student.canAdd(c2));
		assertTrue(student.canAdd(c3));
		
		student.getSchedule().addCourseToSchedule(c);
		
		assertFalse(student.canAdd(c2));
		
	}
}
