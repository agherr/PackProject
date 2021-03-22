package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;

/**
 * Creates a Cousre Roll object
 * 
 * @author Daniel Katowitz
 * @author Aditya Konidena
 * @author Ashten Herr
 *
 */
public class CourseRoll {

	/**
	 * A linked abstract list called roll
	 */
	private LinkedAbstractList<Student> roll;
	/**
	 * Enrollment capacity for the list
	 */
	private int enrollmentCap;
	/**
	 * Minimum enrollment capacity
	 */
	public static final int MIN_ENROLLMENT = 10;
	/**
	 * Maximum enrollment capacity
	 */
	public static final int MAX_ENROLLMENT = 250;

	/**
	 * Using a queue for the waitlist
	 */
	private LinkedQueue<Student> waitlist;

	/** course the roll carries*/
	private Course course;

	/**
	 * Constructor for CourseRoll object; instantiates an empty linked abstract list
	 * with the capacity provided
	 * 
	 * @param capacity the capacity of the linked abstract list
	 * @param c - course of the course roll
	 * 
	 * @throws IllegalArgumentException if the course is null
	 */
	public CourseRoll(Course c, int capacity) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		course = c;
		setEnrollmentCap(capacity);
		roll = new LinkedAbstractList<Student>(enrollmentCap);
		waitlist = new LinkedQueue<Student>(10);
	}

	/**
	 * Sets the enrollment capacity of the list based on input capacity
	 * 
	 * @param capacity the maximum capacity of the list
	 * @throws IllegalArgumentException if the input capacity is lower than the
	 *                                  Minimum enrollment or greater than the
	 *                                  Maximum enrollment
	 */
	public void setEnrollmentCap(int capacity) {

		if (capacity < MIN_ENROLLMENT || capacity > MAX_ENROLLMENT) {
			throw new IllegalArgumentException();
		}

		if (roll != null && roll.size() > capacity) {
			throw new IllegalArgumentException();
		} else {
			enrollmentCap = capacity;
			if (roll != null) {
				LinkedAbstractList<Student> newRoll = new LinkedAbstractList<Student>(capacity);

				for (int i = 0; i < roll.size(); i++) {
					newRoll.add(i, roll.get(i));
				}
				
				roll = newRoll;
			}

		}
	}

	/**
	 * Returns the open seats in a course roll, equal to capacity minus taken spots.
	 * 
	 * @return Open seats in a course.
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}

	/**
	 * Returns the enrollment capacity of a course.
	 * 
	 * @return enrollment capacity of a course.
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Enrolls a student into a course. Throws exception if the student is null,
	 * there is no more open seats, or if the student is already enrolled.
	 * 
	 * @param s - student being added to a course.
	 * @throws IllegalArgumentException if the student is null, the course is full,
	 *                                  or if the student is already enrolled.
	 */
	public void enroll(Student s) {
		// Check for null
		if (s == null) {
			throw new IllegalArgumentException();
		}

		// If there are no open seats check the waitlist
		if (getOpenSeats() <= 0) {
			if (waitlist.size() == 10) {
				// no room to add to waitlist
				throw new IllegalArgumentException();
			} else {

				boolean isDuplicate = false;

				for (int i = 0; i < waitlist.size(); i++) {
					Student check = waitlist.dequeue();
					if (check.equals(s)) {
						isDuplicate = true;
					}
					waitlist.enqueue(check);
				}

				// If its a duplicate throw IAE otherwise add to waitlist
				if (isDuplicate) {
					throw new IllegalArgumentException();
				} else {
					waitlist.enqueue(s);
				}
			}

		} else {

			// Check for duplicates
			for (int i = 0; i < roll.size(); i++) {
				if (roll.get(i).equals(s)) {
					throw new IllegalArgumentException();
				}
			}

			// Add at the rolls size
			roll.add(roll.size(), s);
		}

	}

	/**
	 * Drops a student from a course if the student is null or an exception is
	 * thrown from searching for student an IAE is thrown.
	 * 
	 * @param s - student being dropped.
	 * @throws IllegalArgumentException if the student is null or another exception
	 *                                  is thrown while removing the student.
	 */
	public void drop(Student s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}

		boolean flag = false;

		// Check roll for student
		try {
			for (int i = 0; i < roll.size(); i++) {
				if (roll.get(i).equals(s)) {
					roll.remove(i);

					// If there is someone on the waitlist enroll after removing the student
					if (getNumberOnWaitlist() >= 1) {
						Student addedStudent = waitlist.dequeue();
						roll.add(addedStudent);
						addedStudent.getSchedule().addCourseToSchedule(course);

					}
					flag = true;
					break;

				}
			}
		} catch (NullPointerException e) {
			//Do nothing
		}
		// check on the waitlist, student was not found in course roll
		if (!flag) {
			for (int j = 0; j < waitlist.size(); j++) {
				Student check = waitlist.dequeue();
				// if the student dequeued is not the specified student then re-enroll them on
				// the waitlist, maintains sorted order as all students are dequeued than
				// enqueued (except the removed student)
				if (!check.equals(s)) {
					waitlist.enqueue(check);
				}
			}
		}

	}

	/**
	 * Returns whether a student can enroll in a course. Student can enroll if there
	 * is a open seat and they are not currently enrolled in the same course.
	 * 
	 * @param s - student being checked for enrollment eligibility.
	 * @return whether the student can be enrolled.
	 */
	public boolean canEnroll(Student s) {
		if (getNumberOnWaitlist() == 10) {
			return false;
		} else if (getNumberOnWaitlist() < 10 || getOpenSeats() >= 1) {
			// Search for duplicates
			for (int i = 0; i < roll.size(); i++) {
				if (roll.get(i).equals(s)) {
					return false;
				}
			}
			boolean flag = false;
			for (int i = 0; i < waitlist.size(); i++) {
				Student check = waitlist.dequeue();
				if (check.equals(s)) {
					flag = true;
				}
				waitlist.enqueue(check);

			}

			if (flag) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns the amount of students on the waitlist.
	 * 
	 * @return amount of students on waitlist.
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}
}
