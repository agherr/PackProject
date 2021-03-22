package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Course records from text files. Writes a set of CourseRecords to a
 * file. 
 * 
 * @author Sarah Heckman
 * @author Mark Shanahan
 * @author Ashten Herr
 */
public class CourseRecordIO {

	/**
	 * Reads course records from a file and generates a list of valid Courses. Any
	 * invalid Courses are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a File NotFoundException is thrown.
	 * 
	 * @param fileName file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {

		Scanner fileReader = new Scanner(new FileInputStream(fileName)); // Create a file scanner to read the file
		SortedList<Course> courses = new SortedList<Course>(); // Create an empty array of Course objects
		while (fileReader.hasNextLine()) { // While we have more lines in the file
			try { // Attempt to do the following
					// Read the line, process it in readCourse, and get the object
					// If trying to construct a Course in readCourse() results in an exception, flow
					// of control will transfer to the catch block, below
				Course course = readCourse(fileReader.nextLine());

				// Create a flag to see if the newly created Course is a duplicate of something
				// already in the list
				boolean duplicate = false;
				// Look at all the courses in our list
				for (int i = 0; i < courses.size(); i++) {
					// Get the course at index i
					Course current = courses.get(i);
					// Check if the name and section are the same
					if (course.getName().equals(current.getName())
							&& course.getSection().equals(current.getSection())) {
						duplicate = true;
						break; // We can break out of the loop, no need to continue searching
					}
				}
				// If the course is NOT a duplicate
				if (!duplicate) {
					courses.add(course); // Add to the ArrayList!
				} // Otherwise ignore
			} catch (IllegalArgumentException e) {
				// The line is invalid b/c we couldn't create a course, skip it!
			}
		}
		// Close the Scanner b/c we're responsible with our file handles
		fileReader.close();
		// Return the ArrayList with all the courses we read!
		return courses;
	}

	/**
	 * Takes the input file and creates a course object out of it
	 * 
	 * @return A course object based off the input file
	 * @throws IllegalArgumentException if things are broken
	 * @param nextLine A string that represents all the info in a course object
	 */
	private static Course readCourse(String nextLine) {

		Scanner lineScanner = new Scanner(nextLine);

		lineScanner.useDelimiter(",");
		try {
			String cName = lineScanner.next();
			String cTitle = lineScanner.next();
			String cSection = lineScanner.next();
			int cCredits = lineScanner.nextInt();
			String cInstructor = lineScanner.next();
			int cEnrollmentCap = lineScanner.nextInt();
			String cDays = lineScanner.next();
			if ("A".equals(cDays)) {
				if (lineScanner.hasNext()) {
					lineScanner.close();
					throw new IllegalArgumentException("Arranged schedule can not have start/end time");
				} else {
					lineScanner.close();

					Course course = new Course(cName, cTitle, cSection, cCredits, null, cEnrollmentCap, cDays);
					if (RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(cInstructor) != null) {
						RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(cInstructor)
								.getSchedule().addCourseToSchedule(course);
					}
					return course;
				}
			}
			int startTime = lineScanner.nextInt();
			int endTime = lineScanner.nextInt();
			lineScanner.close();
			Course course = new Course(cName, cTitle, cSection, cCredits, null, cEnrollmentCap, cDays, startTime,
					endTime);

			if (RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(cInstructor) != null) {
				RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(cInstructor).getSchedule()
						.addCourseToSchedule(course);
			}
			return course;

		} catch (InputMismatchException e) {
			throw new IllegalArgumentException("Wrong file format.");
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Wrong file format.");
		}

	}

	/**
	 * This method takes an ArrayList of courses and writes to a file of specified
	 * name
	 * 
	 * @throws IOException if it is unable to write to file
	 * @param fileName A string with the name of the file that The courseRecord will
	 *                 be written to
	 * @param courses  This is the arraylist that stores all of the course objects
	 *                 that will be written to the file
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		// This goes through each index in the arraylist and prints it to the file
		if (courses.size() > 0) {
			fileWriter.print(courses.get(0).toString());
			for (int i = 1; i < courses.size(); i++) {
				fileWriter.print("\n" + courses.get(i).toString());
			}
			// Close the Scanner b/c we're responsible with our file handles
			fileWriter.close();
		}

	}

}