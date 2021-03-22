/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for CourseNameValidator FSM 
 * 
 * @author dtkatowi, agherr, akonide
 *
 */
public class CourseNameValidatorFSMTest {
	
	/** FSM object used to check a courses name validity.*/
	private static final CourseNameValidatorFSM FSM = new CourseNameValidatorFSM();
	
	/**
	 * Test method for isValid()s
	 */
	@Test
	public void testIsValid() {
		try {
			FSM.isValid("###");
			fail("Invalid course name");
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
	}
	
	/**
	 * Test for initial state.
	 */
	@Test
	public void testStateInitial() {
		try {
			FSM.isValid("1111");
			fail();
		} catch (InvalidTransitionException e) {
			//Do nothing
		}
	}
	
	/**
	 * Test for single letter state.
	 */
	@Test
	public void testStateL() {
		try {
			assertTrue(FSM.isValid("A111"));
			assertTrue(FSM.isValid("B331H"));
			assertFalse(FSM.isValid("Q2"));
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Test for double letter state.
	 */
	@Test
	public void testStateLL() {
		try {
			assertTrue(FSM.isValid("AB121"));
			assertTrue(FSM.isValid("BC331H"));
			assertFalse(FSM.isValid("QQ2"));
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Test for triple letter state.
	 */
	@Test
	public void testStateLLL() {
		try {
			assertTrue(FSM.isValid("ABC111"));
			assertTrue(FSM.isValid("BCQ311F"));
			assertFalse(FSM.isValid("QQW2"));
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Test for quadruple letter state.
	 */
	@Test
	public void testStateLLLL() {
		try {
			assertTrue(FSM.isValid("ABCD133"));
			assertTrue(FSM.isValid("MARS421Q"));
			assertFalse(FSM.isValid("IQIQ23"));
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Test for single digit state.
	 */
	@Test
	public void testStateD() {
		try {
			assertTrue(FSM.isValid("ABC121"));
			assertTrue(FSM.isValid("BC333F"));
		} catch (InvalidTransitionException e) {
			fail();
		}
	}

	/**
	 * Test for double digit state
	 */
	@Test
	public void testStateDD() {
		try {
			assertTrue(FSM.isValid("AC141"));
			assertTrue(FSM.isValid("BCQ531F"));
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Test for triple digit state
	 */
	@Test
	public void testStateDDD() {
		try {
			assertTrue(FSM.isValid("A113"));
			assertTrue(FSM.isValid("Q311F"));
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
	
	/**
	 * Test for suffix state
	 */
	@Test
	public void testStateSuffix() {
		try {
			assertTrue(FSM.isValid("A143F"));
			assertTrue(FSM.isValid("Q311F"));
		} catch (InvalidTransitionException e) {
			fail();
		}
	}
}
