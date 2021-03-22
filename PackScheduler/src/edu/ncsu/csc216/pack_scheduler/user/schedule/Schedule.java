/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Class which creates a schedule, handles adding/removing courses. Also
 * controls the title of the schedule with naming the title. Can return a 2D
 * array of schedule information which contains courses.
 * 
 * @author Aditya Konidena
 * @author Ashten Herr
 *
 */
public class Schedule {

	/**
	 * Represents the default schedule title, title starts as this default value.
	 */
	private static final String DEFAULT_TITLE = "My Schedule";

	/**
	 * Represents the array size to hold a courses information to return
	 */
	private static final int ARRAY_SIZE = 4;
	/**
	 * An ArrayList of courses
	 */
	private ArrayList<Course> schedule;
	/**
	 * Schedule title
	 */
	private String title;

	/**
	 * Constructor for Schedule
	 */
	public Schedule() {
		schedule = new ArrayList<Course>();
		title = "My Schedule";
	}

	/**
	 * Method to add course to schedule
	 * 
	 * @param course Course to be added to schedule
	 * @throws NullPointerException     if input parameter is null
	 * @throws IllegalArgumentException if course being added is a duplicate, if
	 *                                  there is a conflict
	 * @return true if course is added, false otherwise
	 */
	public boolean addCourseToSchedule(Course course) {
		if (course == null) {
			throw new NullPointerException();
		}
		for (int i = 0; i < schedule.size(); i++) {
			if (course.getName().equals(schedule.get(i).getName())) {
				throw new IllegalArgumentException("You are already enrolled in " + course.getName());
			}
			try {
				course.checkConflict(schedule.get(i));
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}

		schedule.add(course);
		return true;
	}

	/**
	 * Removes a course from a schedule, if the schedule does not have the course
	 * false is returned, otherwise the course is removed and true is returned.
	 * 
	 * @param course - course object being removed from the schedule
	 * @return - whether the course was successfully removed
	 */
	public boolean removeCourseFromSchedule(Course course) {
		if (course == null) {
			return false;
		}
		for (int i = 0; i < schedule.size(); i++) {
			if (course.isDuplicate(schedule.get(i))) {
				schedule.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Resets the schedule by creating a new array list of courses. Sets title to
	 * the default title.
	 */
	public void resetSchedule() {
		schedule = new ArrayList<Course>();
		setTitle(DEFAULT_TITLE);
	}

	/**
	 * Returns a 2D array of course information from the schedule. Contains a Name,
	 * section, title, and meeting string.
	 * 
	 * @return the 2D array of course information.
	 */
	public String[][] getScheduledCourses() {
		String[][] scheduleArray = new String[schedule.size()][ARRAY_SIZE];
		for (int i = 0; i < schedule.size(); i++) {
			scheduleArray[i] = schedule.get(i).getShortDisplayArray();
		}
		return scheduleArray;
	}

	/**
	 * Sets the title of a schedule, title can not be null.
	 * 
	 * @param title - title to set the schedule to
	 * @throws IllegalArgumentException if the title is null
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}

		this.title = title;
	}

	/**
	 * Returns the schedule title.
	 * 
	 * @return title of the schedule.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Returns the scheduled credits of a schedule.
	 * 
	 * @return how many credits a schedule currently holds.
	 */
	public int getScheduleCredits() {
		int sum = 0;
		for (int i = 0; i < schedule.size(); i++) {
			sum = sum + schedule.get(i).getCredits();
		}
		return sum;
	}
	
	/**
	 * canAdd() returns whether a course can be added or not.
	 * 
	 * @param c - course being tested for the possibility to be added.
	 * @return - whether the course can be added or not.
	 */
	public boolean canAdd(Course c) {
		if (c == null) {
			return false;
		}
		
		for (int i = 0; i < schedule.size(); i++) {
			if (c.getName().equals(schedule.get(i).getName())) {
				return false;
			}
			try {
				c.checkConflict(schedule.get(i));
			} catch (ConflictException e) {
				return false;
			}
		}
		return true;
	}

}
