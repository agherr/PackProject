/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the Activity.java class. ActivityTest tests the functionality of
 * conflicts between different activities.
 * 
 * @author Ashten
 */
public class ActivityTest {

	/**
	 * Test method for checking course conflicts
	 */
	@Test
	public void testCheckConflict() {
		Course a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 20, "MW", 1330,
				1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 20, "TH", 1330,
				1445);
		try {
			a1.checkConflict(a2);
			assertEquals("Incorrect meeting string for this Activity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("Incorrect meeting string for possibleConflictingActivity.", "TH 1:30PM-2:45PM",
					a2.getMeetingString());
		} catch (ConflictException e) {
			fail("A ConflictException was thrown when two Activities at the same time on completely distinct days were compared.");
		}
	}

	/**
	 * Tests two courses with identical times and a similar meeting day throw a
	 * ConflictException.
	 */
	@Test
	public void testCheckConflictWithConflict() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 20, "MW", 1330,
				1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 20, "M", 1330,
				1445);
		try {
			a1.checkConflict(a2);
			fail("A ConflictException was NOT thrown when two Activities had a day/time conflict.");
		} catch (ConflictException e) {
			assertEquals("Incorrect meeting string for this Activity.", "MW 1:30PM-2:45PM", a1.getMeetingString());
			assertEquals("Incorrect meeting string for possibleConflictingActivity.", "M 1:30PM-2:45PM",
					a2.getMeetingString());
		}
	}

	/**
	 * Test checks a conflict exception is thrown if an activity's meeting time
	 * exists inside of another meeting time.
	 */
	@Test
	public void testCheckActivityInsideActivity() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 20, "MTWHF", 1100,
				2000);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 20, "MTWHF", 1500,
				1700);
		try {
			a1.checkConflict(a2);
			fail();
		} catch (ConflictException e) {
			assertEquals("Check conflict message", "Schedule conflict.", e.getMessage());
		}
		try {
			a2.checkConflict(a1);
			fail();
		} catch (ConflictException e) {
			assertEquals("Check conflict message", "Schedule conflict.", e.getMessage());
		}
		assertEquals("Check meeting strings", a1.getMeetingString(), "MTWHF 11:00AM-8:00PM");
		assertEquals("Check meeting strings", a2.getMeetingString(), "MTWHF 3:00PM-5:00PM");
	}

	/**
	 * Tests checkConflict method with arranged courses. Overlapping arranged
	 * courses should not throw any exceptions
	 */
	@Test
	public void testCheckArrangedCourseConflict() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 20, "A");
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 20, "A");
		Activity a3 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 20, "MTWHF", 0,
				100);
		// check conflict between two arranged courses
		try {
			a1.checkConflict(a2);
			a2.checkConflict(a1);
		} catch (ConflictException e) {
			fail();
		}
		assertEquals("Arranged courses should have the same meeting string", a1.getMeetingString(),
				a2.getMeetingString());
		// check conflict between an arranged course and non arranged course.
		try {
			a1.checkConflict(a3);
			a3.checkConflict(a1);
		} catch (ConflictException e) {
			fail();
		}
		assertEquals("Checking meeting string arranged course", "Arranged", a1.getMeetingString());
		assertEquals("Check course meeting string", "MTWHF 12:00AM-1:00AM", a3.getMeetingString());
	}

	/**
	 * Tests overlapping courses on different days.
	 */
	@Test
	public void testOverlappingCoursesDifferentDays() {
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 20, "MTH", 1200,
				1400);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 20, "WF", 1100,
				1300);
		try {
			a1.checkConflict(a2);
		} catch (ConflictException e) {
			fail();
		}
		try {
			a2.checkConflict(a1);
		} catch (ConflictException e) {
			fail();
		}
		assertEquals("Check course meeting string", "MTH 12:00PM-2:00PM", a1.getMeetingString());
		assertEquals("Check course meeting string", "WF 11:00AM-1:00PM", a2.getMeetingString());
	}

}
