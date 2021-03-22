/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Test class for CourseNameValidator
 * 
 * @author dtkatowi, agherr, akonide
 *
 */
public class CourseNameValidatorTest {

	/** FSM object used to validate course name strings during testing. */
	private static final CourseNameValidator FSM = new CourseNameValidator();

	/**
	 * Test method for isValid(). Test that certain cases of onOther will throw
	 * InvalidTransitionException.
	 */
	@Test
	public void testIsValid() {
		try {
			FSM.isValid("###");
			fail("Invalid course name");
		} catch (InvalidTransitionException e) {
			// Do nothing
		}

		try {
			FSM.isValid("CSC22D");
			fail("Invalid course name");
		} catch (InvalidTransitionException e) {
			// Do nothing
		}

		try {
			FSM.isValid("CSC2222");
			fail("Invalid course name");
		} catch (InvalidTransitionException e) {
			// Do nothing
		}

		try {
			FSM.isValid("CSC222F2");
			fail("Invalid course name");
		} catch (InvalidTransitionException e) {
			// Do nothing
		}

		try {
			FSM.isValid("CSC222FF");
			fail("Invalid course name");
		} catch (InvalidTransitionException e) {
			// Do nothing
		}

		try {
			FSM.isValid("CSC2-");
			fail("Invalid course name");
		} catch (InvalidTransitionException e) {
			// Do nothing
		}
	}

	/**
	 * Test method for valid transitions of stateInitial
	 */
	@Test
	public void testStateInitial() {
		try {
			FSM.isValid("1111");
			fail();
		} catch (InvalidTransitionException e) {
			// Do nothing
		}
	}

	/**
	 * Test method for valid transitions of stateL
	 */
	@Test
	public void testStateL() {
		try {
			assertTrue(FSM.isValid("A111"));
			assertTrue(FSM.isValid("B331H"));
		} catch (InvalidTransitionException e) {
			fail();
		}
		
		try {
			FSM.isValid("Q2");
			fail();
		} catch (InvalidTransitionException e) {
			// Do nothing
		}
	}

	/**
	 * Test method for valid transitions of stateLL
	 */
	@Test
	public void testStateLL() {
		try {
			assertTrue(FSM.isValid("AB121"));
			assertTrue(FSM.isValid("BC331H"));
		} catch (InvalidTransitionException e) {
			fail();
		}
		
		try {
			FSM.isValid("QQ2");
			fail();
		} catch (InvalidTransitionException e) {
			// Do nothing
		}
	}

	/**
	 * Test method for valid transitions of stateLLL
	 */
	@Test
	public void testStateLLL() {
		try {
			assertTrue(FSM.isValid("ABC111"));
			assertTrue(FSM.isValid("BCQ311F"));
		} catch (InvalidTransitionException e) {
			fail();
		}
		
		try {
			FSM.isValid("QQW2");
			fail();
		} catch (InvalidTransitionException e) {
			// Do nothing
		}
	}

	/**
	 * Test method for valid transitions of stateLLLL
	 */
	@Test
	public void testStateLLLL() {
		try {
			assertTrue(FSM.isValid("ABCD133"));
			assertTrue(FSM.isValid("MARS421Q"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		try {
			FSM.isValid("CSCCC");
			fail("Invalid course name");
		} catch (InvalidTransitionException e) {
			// Do nothing
		}
		
		try {
			FSM.isValid("IQIQ23");
			fail("Invalid course name");
		} catch (InvalidTransitionException e) {
			// Do nothing
		}
	}

	/**
	 * Test method for valid transitions of stateD
	 */
	@Test
	public void testStateD() {
		try {
			assertTrue(FSM.isValid("ABC121"));
			assertTrue(FSM.isValid("BC333F"));
		} catch (InvalidTransitionException e) {
			fail();
		}

		try {
			FSM.isValid("CSC2C");
			fail("Invalid course name");
		} catch (InvalidTransitionException e) {
			// Do nothing
		}
	}

	/**
	 * Test method for valid transitions of stateDD
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
	 * Test method for valid transitions of stateDDD
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
	 * Test method for valid transitions of stateSuffix
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
