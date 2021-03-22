package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * The student class represents a student at a university. Every student has a
 * first name, last name, ID, email, hashed password, and a credit value. The
 * student class allows for construction of the student object with all of the
 * proper fields and parameters met.
 * 
 * @author Ashten Herr
 * @author Mark Shanahan
 * @author Torin Cuany
 */
public class Student extends User implements Comparable<Student> {
	/** maxCredits represents the maximum credits the student can take */
	private int maxCredits;
	/** MAX_CREDITS is the maximum credit value a student can have */
	public static final int MAX_CREDITS = 18;
	/** MIN_CREDITS is the minimum credit value a student can have */
	public static final int MIN_CREDITS = 3;
	/** Represents the students schedule */
	public Schedule schedule;

	/**
	 * Student is the constructor method for the student object and sets all of the
	 * fields to the correct variables that the student class contains. A student is
	 * made up of a first name, last, name, ID, email, hashed password, and a credit
	 * value.
	 * 
	 * @param firstName  - the first name of the student
	 * @param lastName   - the last name of the student
	 * @param id         - the student ID
	 * @param email      - the students email
	 * @param hashPW     - the hashed password of the student
	 * @param maxCredits - the maximum credits that the student has
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCredits(maxCredits);
		schedule = new Schedule();
	}

	/**
	 * Student is a constructor for the student object when a credit value is not
	 * given it is set to the default which is 18.
	 * 
	 * @param firstName - the first name of the student
	 * @param lastName  - the last name of the student
	 * @param id        - the student ID
	 * @param email     - the students email
	 * @param hashPW    - the hashed password for the student
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		// call main constructor and set credit hours to the default max value
		this(firstName, lastName, id, email, hashPW, MAX_CREDITS);

	}

	/**
	 * Returns the maximum value of credits the student can take.
	 * 
	 * @return maxCredit value of the student.
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the max credit value of the student. maxCredits is invalid if less than
	 * 3 or greater than 18.
	 * 
	 * @param maxCredits - the maxCredits to set
	 * @throws IllegalArgumentException - if the credit value is less than 3 or
	 *                                  greater than 18.
	 */
	public void setMaxCredits(int maxCredits) {
		// check that maxCredits is not less than three or greater than 18
		if (maxCredits < MIN_CREDITS || maxCredits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid max credits");
		} else {
			this.maxCredits = maxCredits;
		}
	}

	/**
	 * Returns the string value of a student which is a comma separated list of all
	 * the fields.
	 * 
	 * @return the comma separated list of the student fields.
	 */
	@Override
	public String toString() {
		String s = super.getFirstName() + "," + super.getLastName() + "," + super.getId() + "," + super.getEmail() + ","
				+ super.getPassword() + "," + maxCredits;
		return s;
	}

	/**
	 * Compares two Student objects for ordering lexicographically in order of last
	 * name, first name, then id. If this instance of student occurs before the
	 * parameter, a -1 is returned. If after, a 1 is returned. If they have the same
	 * last name, first name, and id, a 0 is returned.
	 * 
	 * @return 1 if the instance Student occurs after the parameter in a list -1 if
	 *         the instance Student occurs before the parameter in a list 0 if the
	 *         instance Student and the parameter have equivalent indices in a list
	 * @throws NullPointerException - if the parameter is null
	 */
	@Override
	public int compareTo(Student s) {
		if (null == s) {
			throw new IllegalArgumentException("Student cannot be null");
		}

		// Checks if this last name occurs before or after the parameter's, or if they
		// match check first names
		int lastNameComp = super.getLastName().compareToIgnoreCase(s.getLastName());
		if (lastNameComp > 0) {
			return 1;
		} else if (lastNameComp < 0) {
			return -1;
		} else {

			// Checks if this first name occurs before or after the parameter's, or if they
			// match check ids
			int firstNameComp = super.getFirstName().compareToIgnoreCase(s.getFirstName());
			if (firstNameComp > 0) {
				return 1;
			} else if (firstNameComp < 0) {
				return -1;
			} else {

				// If first and last are equal, check ids
				int idComp = super.getId().compareToIgnoreCase(s.getId());
				if (idComp > 0) {
					return 1;
				} else if (idComp < 0) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	/**
	 * Returns the hash code value of a student
	 * 
	 * @return hash code value.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Test for equality 2 students are considered equal if they are of the same
	 * type and there credit values are equal.
	 * 
	 * @return whether two students are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Student))
			return false;
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
	}

	/**
	 * Method that returns the students schedule in which they can use the schedule
	 * functionality.
	 * 
	 * @return schedule for the Student
	 */
	public Schedule getSchedule() {
		return schedule;
	}

	/**
	 * Return whether a student can add a course, null, duplicate and schedules with
	 * conflict will return false, If the credits go over the remaining credits
	 * available for a student then false is also returned. Otherwise a course can
	 * be added and true is returned.
	 * 
	 * @param c - course being tested if it can be added.
	 * @return - whether the course can be added.
	 */
	public boolean canAdd(Course c) {
		return schedule.canAdd(c) && c.getCredits() <= getMaxCredits() - schedule.getScheduleCredits();
	}
}
