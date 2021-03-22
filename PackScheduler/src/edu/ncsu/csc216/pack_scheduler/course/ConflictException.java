/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * ConflictException.java is the checked exception for WolfScheduler.
 * ConlfictException.java occurs when a Activity is created that conflicts with
 * an already existing Activity.
 * 
 * @author Ashten Herr
 *
 */
public class ConflictException extends Exception {
	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for ConflictException, allows for a custom message to be inputted
	 * when constructing a ConflictException.
	 * 
	 * @param message - custom message used when constructing ConflictException
	 */
	public ConflictException(String message) {
		super(message);
	}

	/**
	 * Constructor for ConflictException, uses a default message of "Schedule
	 * conflict." when thrown without a string parameter.s
	 */
	public ConflictException() {
		super("Schedule conflict.");
	}
}
