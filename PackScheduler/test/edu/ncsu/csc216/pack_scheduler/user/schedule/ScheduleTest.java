/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * ScheduleTest tests the schedule class.
 * 
 * @author adityakonidena
 * @author Ashten Herr
 *
 */
public class ScheduleTest {

	/**
	 * Tests schedule constructor.
	 */
	@Test
	public void testSchedule() {
		// Create new schedule
		Schedule schedule = new Schedule();

		// Check that the name is the default name and that it has no courses.
		assertEquals("My Schedule", schedule.getTitle());
		assertEquals(0, schedule.getScheduledCourses().length);
	}

	/**
	 * Tests the addCourseToSchedule method
	 */
	@Test
	public void testAddCourseToSchedule() {
		Schedule schedule = new Schedule();

		// Try to add null course
		try {
			schedule.addCourseToSchedule(null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(0, schedule.getScheduledCourses().length);
		}

		// Add course to schedule
		Course c = new Course("CSC217", "Java2", "004", 4, "agherr", 10, "MTW");

		schedule.addCourseToSchedule(c);

		// Check fields
		assertEquals(1, schedule.getScheduledCourses().length);

		assertEquals("CSC217", schedule.getScheduledCourses()[0][0]);
		assertEquals("004", schedule.getScheduledCourses()[0][1]);
		assertEquals("Java2", schedule.getScheduledCourses()[0][2]);
		assertEquals("MTW 12:00AM-12:00AM", schedule.getScheduledCourses()[0][3]);

		// Try adding duplicate course and catch exception.

		try {
			schedule.addCourseToSchedule(c);
		} catch (IllegalArgumentException e) {
			// Check fields
			assertEquals(1, schedule.getScheduledCourses().length);

			assertEquals("CSC217", schedule.getScheduledCourses()[0][0]);
			assertEquals("004", schedule.getScheduledCourses()[0][1]);
			assertEquals("Java2", schedule.getScheduledCourses()[0][2]);
			assertEquals("MTW 12:00AM-12:00AM", schedule.getScheduledCourses()[0][3]);
		}

		// Create a course with overlapping times
		Course c2 = new Course("CSC216", "Java2", "002", 4, "agherr", 10, "MTW");

		try {
			schedule.addCourseToSchedule(c2);
		} catch (IllegalArgumentException e) {
			// Check fields
			assertEquals(1, schedule.getScheduledCourses().length);

			assertEquals("CSC217", schedule.getScheduledCourses()[0][0]);
			assertEquals("004", schedule.getScheduledCourses()[0][1]);
			assertEquals("Java2", schedule.getScheduledCourses()[0][2]);
			assertEquals("MTW 12:00AM-12:00AM", schedule.getScheduledCourses()[0][3]);
		}

	}

	/**
	 * Tests the removeCourseFromSchedule method of Schedule.java
	 */
	@Test
	public void testRemoveCourseFromSchedule() {
		Schedule schedule = new Schedule();

		// 2 unique courses with no conflicts
		Course c = new Course("CSC217", "Java2Lab", "004", 1, "dtkatowi", 10, "MTW");
		Course c2 = new Course("CSC216", "Java2", "002", 3, "dtkatowi", 10, "HF");

		// Try to remove course from an empty schedule
		try {
			assertFalse(schedule.removeCourseFromSchedule(c));
		} catch (NullPointerException e) {
			assertEquals(0, schedule.getScheduledCourses().length);
		}

		// Remove added courses from schedule
		try {
			schedule.addCourseToSchedule(c);
			schedule.addCourseToSchedule(c2);

			assertEquals(2, schedule.getScheduledCourses().length);
			assertTrue(schedule.removeCourseFromSchedule(c));

			assertEquals(1, schedule.getScheduledCourses().length);
			assertTrue(schedule.removeCourseFromSchedule(c2));

			assertEquals(0, schedule.getScheduledCourses().length);
		} catch (NullPointerException e) {
			fail();
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

	/**
	 * Tests the reset schedule method in Schedule.java
	 */
	@Test
	public void testResetSchedule() {
		Schedule schedule = new Schedule();

		assertEquals("My Schedule", schedule.getTitle());

		// 2 unique courses with no conflicts
		Course c = new Course("CSC217", "Java2Lab", "004", 1, "dtkatowi", 10, "MTW");
		Course c2 = new Course("CSC216", "Java2", "002", 3, "dtkatowi", 10, "HF");

		schedule.setTitle("My Better Schedule");
		schedule.addCourseToSchedule(c);
		schedule.addCourseToSchedule(c2);

		assertEquals("My Better Schedule", schedule.getTitle());
		assertEquals(2, schedule.getScheduledCourses().length);

		schedule.resetSchedule();

		assertEquals("My Schedule", schedule.getTitle());
		assertEquals(0, schedule.getScheduledCourses().length);
	}

	/**
	 * Tests the getScheduledCourses method in Schedule.java
	 */
	@Test
	public void testGetScheduledCourses() {
		Schedule schedule = new Schedule();

		assertEquals(0, schedule.getScheduledCourses().length);

		Course c = new Course("MA405", "Linear Algebra", "001", 4, "dtkatowi", 10, "MTW");
		try {
			schedule.addCourseToSchedule(c);
		} catch (IllegalArgumentException e) {
			// Check fields
			assertEquals(1, schedule.getScheduledCourses().length);

			assertEquals("MA405", schedule.getScheduledCourses()[0][0]);
			assertEquals("001", schedule.getScheduledCourses()[0][1]);
			assertEquals("Linear Algebra", schedule.getScheduledCourses()[0][2]);
			assertEquals("MTW 12:00AM-12:00AM", schedule.getScheduledCourses()[0][3]);
		}
	}

	/**
	 * Tests the setTitle method in Schedule.java
	 */
	@Test
	public void testSetTitle() {
		Schedule schedule = new Schedule();
		try {
			schedule.setTitle(null);
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "Title cannot be null.");
			assertEquals("My Schedule", schedule.getTitle());
		}
	}
	
	/**
	 * Tests the can add method.
	 */
	@Test
	public void testCanAdd() {
		Schedule schedule = new Schedule();
		Course c = new Course("CSC217", "Java2Lab", "004", 1, "dtkatowi", 10, "MTW");
		Course c2 = new Course("CSC216", "Java2", "002", 3, "dtkatowi", 10, "HF");
		Course c3 = new Course("CSC216", "Java2", "002", 3, "dtkatowi", 10, "MTW");
		
		assertTrue(schedule.canAdd(c));
		assertTrue(schedule.canAdd(c2));
		assertTrue(schedule.canAdd(c3));
		assertFalse(schedule.canAdd(null));
		
		schedule.addCourseToSchedule(c);
		schedule.addCourseToSchedule(c2);
		
		assertFalse(schedule.canAdd(c3));
		
		
	}

}
