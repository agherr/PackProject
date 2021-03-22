/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests InvalidTransitionException class
 * @author dtkatowi, agherr, akonide
 *
 */
public class InvalidTransitionExceptionTest {

	/**
	 * Test for InvalidTransitionException 
	 */
	@Test
	public void testInvalidTransitionExceptionString() {
		InvalidTransitionException ite = new InvalidTransitionException("Invalid FSM Transition");
		assertEquals("Invalid FSM Transition", ite.getMessage());
	}
	
	/**
	 * Test for InvalidTransitionException 
	 */
	@Test
	public void testInvalidTransitionExceptionDefault() {
		InvalidTransitionException ite = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", ite.getMessage());
	}

}
