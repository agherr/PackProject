
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Interface handles the possibility of two activities times overlapping. This
 * occurs if two activities time and day slots overlap. Interface handles
 * 
 * @author Ashten Herr
 */
public interface Conflict {
	/**
	 * checkConflict interface helps check for conflict between Activities. Method
	 * is implemented in Activity.java to handle conflict issues between activities.
	 * 
	 * 
	 * @param possibleConflictingActivity - activity that is being checked for
	 *                                    overlapping other activities
	 * @throws ConflictException - Exception is thrown if the two activities have
	 *                           conflicting time slots.
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
