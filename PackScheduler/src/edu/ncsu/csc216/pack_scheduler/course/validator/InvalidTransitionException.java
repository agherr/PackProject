/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Exception for the CourseNameValidator FSM. Exception represents an invalid
 * transition between states.
 * 
 * @author dtkatowi, agherr, akonide
 *
 */
public class InvalidTransitionException extends Exception {

	/** The value 1 stored into a long primitive type */
	private static final long serialVersionUID = 1L;
	/** The default message of the exception when thrown */
	private static final String DEFAULT_MESSAGE = "Invalid FSM Transition.";

	/**
	 * Constructor for the InvalidTransitionException
	 * 
	 * @param message of exception
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}

	/**
	 * Constructor for the InvalidTransitionException
	 */
	public InvalidTransitionException() {
		super(DEFAULT_MESSAGE);
	}

}
