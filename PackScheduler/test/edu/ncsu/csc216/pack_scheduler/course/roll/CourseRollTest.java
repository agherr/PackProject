package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Program that tests the methods in CourseRoll.java
 * 
 * @author Daniel Katowitz
 * @author Aditya Konidena
 * @author Ashten Herr
 *
 */
public class CourseRollTest {

	// Test invalid construction
	/**
	 * Tests the CourseRoll constructor with invalid arguments
	 */
	@Test
	public void testCourseRollInvalid() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		// Invalid capacity
		CourseRoll roll = null;
		assertNull(roll);
		try {
			roll = new CourseRoll(c, 3);
			fail();
		} catch (IllegalArgumentException e) {
			// Do nothing
		}

		// Invalid capacity
		try {
			roll = new CourseRoll(c, 254);
			fail();
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
	}

	// Construct a new CourseRoll with capacity 20
	/**
	 * Tests the CourseRoll constructor
	 */
	@Test
	public void testCourseRoll() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		try {
			CourseRoll roll = c.getCourseRoll();
			assertEquals(10, roll.getOpenSeats());
			assertEquals(10, roll.getEnrollmentCap());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	// Enroll a new Student
	/**
	 * Tests the enroll student method
	 */
	@Test
	public void testEnroll() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		try {
			CourseRoll roll = c.getCourseRoll();
			Student s = new Student("Johnny", "Appleseed", "seeds", "growatree@ncsu.edu", "pw");
			Student s2 = new Student("John", "Apples", "seed", "growatree@ncsu.edu", "pw");
			assertTrue(roll.canEnroll(s));
			roll.enroll(s);
			assertEquals(9, roll.getOpenSeats());
			roll.enroll(s2);
			assertEquals(8, roll.getOpenSeats());
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	// Attempt to incorrectly enroll a Student
	/**
	 * Tests the enroll student method with invalid arguments
	 */
	@Test
	public void testEnrollInvalid() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll roll = c.getCourseRoll();
		// Attempt to enroll a null student
		try {
			roll.enroll(null);
		} catch (IllegalArgumentException e) {
			assertEquals(10, roll.getOpenSeats());
			assertEquals(10, roll.getEnrollmentCap());
		}

		// Attempt to enroll in a full roll
		try {
			Student s = new Student("Johnny", "Appleseed", "seeds", "growatree@ncsu.edu", "pw");
			Student s2 = new Student("John", "Apples", "seed", "growatree@ncsu.edu", "pw");
			Student s3 = new Student("Jane", "Sourapple", "sied", "growatree@ncsu.edu", "pw");
			Student s4 = new Student("Joe", "Seed", "seedz", "growatree@ncsu.edu", "pw");
			Student s5 = new Student("Jacod", "App", "zeedz", "growatree@ncsu.edu", "pw");
			Student s6 = new Student("Jamie", "Lanister", "grow", "growatree@ncsu.edu", "pw");
			Student s7 = new Student("Jorge", "Potato", "tree", "growatree@ncsu.edu", "pw");
			Student s8 = new Student("Jenny", "Redapple", "ground", "growatree@ncsu.edu", "pw");
			Student s9 = new Student("Jim", "Membership", "appleseed", "growatree@ncsu.edu", "pw");
			Student s10 = new Student("Jo", "Apple", "applesneeze", "growatree@ncsu.edu", "pw");

			assertTrue(roll.canEnroll(s));
			roll.enroll(s);

			assertTrue(roll.canEnroll(s2));
			roll.enroll(s2);

			assertTrue(roll.canEnroll(s3));
			roll.enroll(s3);

			assertTrue(roll.canEnroll(s4));
			roll.enroll(s4);

			assertTrue(roll.canEnroll(s5));
			roll.enroll(s5);

			assertTrue(roll.canEnroll(s6));
			roll.enroll(s6);

			assertTrue(roll.canEnroll(s7));
			roll.enroll(s7);

			assertTrue(roll.canEnroll(s8));
			roll.enroll(s8);

			assertTrue(roll.canEnroll(s9));
			roll.enroll(s9);

			assertTrue(roll.canEnroll(s10));
			roll.enroll(s10);

			Student s11 = new Student("Daniel", "Katowitz", "dtkatowi", "dtkatowi@ncsu.edu", "pw");
			assertTrue(roll.canEnroll(s11));
			roll.enroll(s11);

			assertEquals(1, roll.getNumberOnWaitlist());

			Student s0 = new Student("Full", "Student", "cantenroll", "noroom@ncsu.edu", "pw");
			assertTrue(roll.canEnroll(s0));
		} catch (IllegalArgumentException e) {
			fail();
		}

		// Attempt to double enroll a Student
		try {
			Student s = new Student("Johnny", "Appleseed", "seeds", "growatree@ncsu.edu", "pw");
			roll.enroll(s);
			roll.enroll(s);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}
	}

	/**
	 * Tests the drop method in CourseRoll with valid and invalid arguments
	 */
	@Test
	public void testDrop() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		Student s = new Student("Johnny", "Appleseed", "seeds", "growatree@ncsu.edu", "pw");
		Student s2 = new Student("John", "Apples", "seed", "growatree@ncsu.edu", "pw");
		Student s3 = new Student("Jane", "Sourapple", "sied", "growatree@ncsu.edu", "pw");
		Student s4 = new Student("Joe", "Seed", "seedz", "growatree@ncsu.edu", "pw");
		Student s5 = new Student("Jacod", "App", "zeedz", "growatree@ncsu.edu", "pw");
		Student s6 = new Student("Jamie", "Lanister", "grow", "growatree@ncsu.edu", "pw");
		Student s7 = new Student("Jorge", "Potato", "tree", "growatree@ncsu.edu", "pw");
		Student s8 = new Student("Jenny", "Redapple", "ground", "growatree@ncsu.edu", "pw");
		Student s9 = new Student("Jim", "Membership", "appleseed", "growatree@ncsu.edu", "pw");
		Student s10 = new Student("Jo", "Apple", "applesneeze", "growatree@ncsu.edu", "pw");
		Student s11 = new Student("Daniel", "Katowitz", "dtkatowi", "dtkatowi@ncsu.edu", "pw");
		Student s12 = new Student("Ashten", "Herr", "agherr", "agherr@ncsu.edu", "pw");

		try {
			CourseRoll roll = c.getCourseRoll();
			//Enroll all of the students
			roll.enroll(s);
			roll.enroll(s2);
			roll.enroll(s3);
			roll.enroll(s4);
			roll.enroll(s5);
			roll.enroll(s6);
			roll.enroll(s7);
			roll.enroll(s8);
			roll.enroll(s9);
			roll.enroll(s10);
			roll.enroll(s11);
			roll.enroll(s12);
			
			//Check that the sizes are correct
			assertEquals(0, roll.getOpenSeats());
			assertEquals(2, roll.getNumberOnWaitlist());
			
			//Drop a student enrolled in course
			roll.drop(s);
			
			assertEquals(0, roll.getOpenSeats());
			assertEquals(1, roll.getNumberOnWaitlist());
			
			//Drop a student on the waitlist
			roll.drop(s12);
			
			assertEquals(0, roll.getOpenSeats());
			assertEquals(0, roll.getNumberOnWaitlist());
			
			//Drop another student
			
			roll.drop(s3);
			assertEquals(1, roll.getOpenSeats());
			assertEquals(0, roll.getNumberOnWaitlist());
			

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			fail();
		}

//		//Attempt to drop a student not in roll
//		try {
//			CourseRoll roll = new CourseRoll(10);
//			Student s = new Student("Johnny", "Appleseed", "seeds", "growatree@ncsu.edu", "pw");
//			roll.drop(s);
//			fail();
//		} catch (IllegalArgumentException e) {
//			//Do nothing
//		}

		// Attempt to drop a null student
		try {
			CourseRoll roll = c.getCourseRoll();
			roll.drop(null);
			fail();
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
	}

}