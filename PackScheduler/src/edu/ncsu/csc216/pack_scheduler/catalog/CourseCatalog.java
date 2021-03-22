package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * CourseCatalog is a class that acts as a container for courses. A course
 * catalog is made of a sorted list of courses. Courses can be added/removed or
 * loaded/saved from files.
 * 
 * 
 * @author Ashten Herr
 * @author Torin Cuany
 *
 */
public class CourseCatalog {
	/**
	 * ARRAY_SIZE represents the array size to return from getCourseCatalog().
	 */
	public static final int ARRAY_SIZE = 5;

	/** Catalog of courses in sorted order */
	SortedList<Course> catalog;

	/**
	 * Constructor for CourseCatalog. Uses Default Constructor.
	 */
	public CourseCatalog() {
		newCourseCatalog();
	}

	/**
	 * Clears course catalog for a newSortedList
	 */
	public void newCourseCatalog() {
		this.catalog = new SortedList<Course>();
	}

	/**
	 * Loads courses from a file with a specified name throws IAE if the file is
	 * unable to be read.
	 * 
	 * @param fileName - name of the file to load
	 * @throws IllegalArgumentException if the file can not be read.
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			this.catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file");
		}
	}

	/**
	 * Adds a course to the catalog. Returns whether the course was successfully
	 * added or not. Course fields must meet parameter guidelines.
	 * 
	 * @param name         - name of course.
	 * @param title        - title of course.
	 * @param section      - course section string of numbers
	 * @param credits      - credit hour value of the course.
	 * @param instructorId - String value for the instructor id.
	 * @param enrollmentCap - Enrollment capacity of the course
	 * @param meetingDays  - meeting days that the course meets on (MTWHF).
	 * @param startTime    - start time of the course in military time.
	 * @param endTime      - endTime of course in military time.
	 * @return whether the course was added or not.
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId,
			int enrollmentCap, String meetingDays, int startTime, int endTime) {
		// get the course being added based on the name and section
		Course course = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime,
				endTime);
		// check for duplicates
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).isDuplicate(course)) {
				return false;
			}
		}
		// add course and return true as all requirements have been met
		this.catalog.add(course);
		return true;
	}

	/**
	 * removeCourseCatalog removes a course from the catalog with a specified name
	 * and section. Returns whether the course was successfully removed or not.
	 * 
	 * @param name    - name of the course
	 * @param section - section of the course
	 * @return whether course is removed successfully
	 */
	public boolean removeCourseFromCatalog(String name, String section) {

		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				this.catalog.remove(i);
				return true;
			}

		}
		// course was not found return false
		return false;
	}

	/**
	 * Returns a course from the course catalog with a specified name and section.
	 * Returns null if the course does not exist within the catalog.
	 * 
	 * @param name    - name of the course
	 * @param section - name of the section.
	 * @return Course in catalog with the matching name and section.
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				return catalog.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns an array of courses with the name, section, title, and Meeting
	 * String.
	 * 
	 * @return catalogArray of courses
	 */
	public String[][] getCourseCatalog() {
		// create an empty catalog array
		String[][] catalogArray = new String[catalog.size()][ARRAY_SIZE];
		// for each course put in the name, section, and title that matches.
		for (int i = 0; i < catalogArray.length; i++) {
			catalogArray[i][0] = catalog.get(i).getName();
			catalogArray[i][1] = catalog.get(i).getSection();
			catalogArray[i][2] = catalog.get(i).getTitle();
			catalogArray[i][3] = catalog.get(i).getMeetingString();
			catalogArray[i][4] = catalog.get(i).getCourseRoll().getEnrollmentCap() + "";
		}
		// return the fully filled array
		return catalogArray;
	}

	/**
	 * Saves Courses to a file with a specified file name, throws exception if the
	 * file can not be saved to.
	 * 
	 * @param fileName - Name of the file to save to.
	 * @throws IllegalArgumentException if the file can not be written to.
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write file");
		}
	}
}
