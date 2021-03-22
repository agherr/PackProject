package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * FacultyRecordIO manages the file input/output of faculty objects, reads and
 * writes files for faculty members.
 * 
 * @author Ashten Herr, Aditya Konidena
 *
 */
public class FacultyRecordIO {

	/**
	 * Reads a faculty file and returns a linked list of the faculty members.
	 * 
	 * @param filename - file name of the file being read
	 * @return Linked list of faculty members from file
	 * @throws FileNotFoundException - if the file can not be found.
	 */
	public static LinkedList<Faculty> readFacultyRecords(String filename) throws FileNotFoundException {
		// create scanner for scanning the file
		Scanner fileReader = new Scanner(new FileInputStream(filename));
		// create an array list of students to return
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		// while the text file has a next line check it to see if it is a valid student
		while (fileReader.hasNextLine()) {
			String line = fileReader.nextLine();
			try {
				Faculty newFaculty = processFaculty(line);
				boolean flag = false;
				for (Faculty f : faculty) {
					if (f.getId().equals(newFaculty.getId())) {
						flag = true;
					}
				}

				if (!flag) {
					faculty.add(newFaculty);
				}

			} catch (IllegalArgumentException e) {
				// Skip the line the faculty object was invalid.
			}
		}
		fileReader.close();
		return faculty;
	}

	/**
	 * Processes a single line of Faculty information into a faculty member and
	 * returns the member. Throws IAE if there is a problem reading the line, or
	 * creating the faculty member.
	 * 
	 * @param line - line being processed
	 * @return Faculty member to be added
	 * @throws IllegalArgumentException - if line is improperly formatted or an
	 *                                  exception is thrown creating the faculty
	 *                                  member.
	 */
	private static Faculty processFaculty(String line) {
		// Create scanner for the line
		Scanner lineScanner = new Scanner(line);
		// use comma to separate the tokens
		lineScanner.useDelimiter(",");
		try {
			// try getting all of the variable fields of student object with lineScanner
			String firstName = lineScanner.next();
			String lastName = lineScanner.next();
			String id = lineScanner.next();
			String email = lineScanner.next();
			String hashedPW = lineScanner.next();
			int maxCourses = lineScanner.nextInt();
			// return new student object
			return new Faculty(firstName, lastName, id, email, hashedPW, maxCourses);
		} catch (InputMismatchException e) {
			throw new IllegalArgumentException("Improper file format.");
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Improper file format.");
		} finally {
			lineScanner.close();
		}
	}

	/**
	 * Writes faculty records to a file, exception is thrown if the file name can
	 * not be written to. Utilizes the faculty toString method and writes each out.
	 * 
	 * @param filename - filename being written to
	 * @param faculty  - faculty LinkedList of faculty members being written
	 * @throws FileNotFoundException - if the file can not be written to
	 */
	public static void writeFacultyRecords(String filename, LinkedList<Faculty> faculty) throws FileNotFoundException {
		// Prints to the specified file
		PrintStream fileWriter = new PrintStream(new File(filename));
		// print each student from list
		for (Faculty f : faculty) {
			fileWriter.println(f.toString());
		}
		fileWriter.close();
	}

}
