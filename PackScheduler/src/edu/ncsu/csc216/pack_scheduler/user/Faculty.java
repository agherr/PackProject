package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Faculty is a POJO which represents a faculty member. Faculty has a first
 * name, last name, id, email, hash password and a max course value. The max
 * course value must be between one and three.
 * 
 * @author Ashten Herr, Aditya Konidena
 *
 */
public class Faculty extends User {

	/** maxCourses is the max courses a faculty member can have */
	private int maxCourses;

	/** Max course value a faculty member can have */
	public static final int MAX_COURSES = 3;

	/** Minimum courses a faculty member can have */
	public static final int MIN_COURSES = 1;

	/** Schedule that belongs to a faculty member */
	private FacultySchedule schedule;

	/**
	 * Constructor of faculty object sets all of the fields using the abstract
	 * constructor other than max courses which is unique to a faculty object. Sets
	 * max courses which must be between 1 and 3 or IAE is thrown.
	 * 
	 * @param firstName  - faculty first name
	 * @param lastName   - faculty last name
	 * @param id         - faculty id
	 * @param email      - email of the faculty member
	 * @param hashPW     - hashed password
	 * @param maxCourses - max courses a faculty member can have.
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPW, int maxCourses) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCourses(maxCourses);
		schedule = new FacultySchedule(id);
	}

	/**
	 * Returns the faculty max courses field.
	 * 
	 * @return maxCourses
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * Sets the maxCourses field for faculty, throws IAE if maxCourses is less than
	 * 1 or greater than 3.
	 * 
	 * @param maxCourses - max courses of the faculty object
	 * @throws IllegalArgumentException if maxCourses is less than 1 or greater than
	 *                                  3
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}

		this.maxCourses = maxCourses;
	}

	/**
	 * Returns the faculty schedule.
	 * 
	 * @return the faculty schedule.
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}

	/**
	 * Returns whether the faculty member has too many courses (greater than the max
	 * course count of 3)
	 * 
	 * @return whether the schedule has too many courses.
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > maxCourses;
	}

	/**
	 * Generates the String representation of a faculty object, a comma seperated
	 * list of the fields.
	 * 
	 * @return String representation of faculty object.
	 */
	@Override
	public String toString() {
		return super.getFirstName() + "," + super.getLastName() + "," + super.getId() + "," + super.getEmail() + ","
				+ super.getPassword() + "," + getMaxCourses();
	}

	/**
	 * Returns the hash code of the object.
	 * 
	 * @return the hash code of the faculty object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Determines if two faculty are equal, faculty are equal if are of the same
	 * type and all of the user and faculty fields are equal to each other.
	 * 
	 * @param obj - object being compared to
	 * @return whether the two objects are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Faculty))
			return false;
		Faculty other = (Faculty) obj;
		return maxCourses == other.maxCourses;

	}

}
