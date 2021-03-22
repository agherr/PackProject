package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * StudentRecordIO handles file input and output for PackScheduler.
 * StudentRecordIO reads in files to create student objects and writes to files
 * the current student objects loaded in the PackScheduler.
 * 
 * @author Ashten Herr
 * @author Torin Cuany
 * @author Mark Shanahan
 */
public class StudentRecordIO {

	/**
	 * Reads a text file of student information and adds students to a SortedList if
	 * they are not invalid/in the wrong format.
	 * 
	 * @param fileName    - file of text file which contains student information.
	 * @return SortedList - SortedList that makes up the added students.
	 * @throws FileNotFoundException - if the specified file name can not be found
	 *                               on the system.
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		// create scanner for scanning the file
		Scanner fileReader = new Scanner(new FileInputStream(fileName));
		// create an array list of students to return
		SortedList<Student> students = new SortedList<Student>();
		// while the text file has a next line check it to see if it is a valid student
		while (fileReader.hasNextLine()) {
			try {
				// read the student info to check validity
				Student student = readStudent(fileReader.nextLine());
				boolean flag = false;
				// check for duplicates
				for (int i = 0; i < students.size(); i++) {
					Student check = students.get(i);
					// if they are duplicates set the flag to true to not add them to the list
					if (check.getId().equals(student.getId())) {
						flag = true;
						break;
					}
				}
				// if the flag is not true then add the student, otherwise skip the student
				if (!flag) {
					students.add(student);
				}
				// catch any possible exceptions in the student class that aren't valid and skip
				// that line.
			} catch (IllegalArgumentException e) {
				e.getMessage();
			}

		}
		fileReader.close();
		// return filled ArrayList of student
		return students;
	}

	/**
	 * Reads a line of text in order to create a student object. Each line has comma
	 * separated fields to construct one. Throws IllegalArgumentException if the
	 * line is not formatted properly.
	 * 
	 * @param nextLine - the line being processed into a student object
	 * @return Student object created from the information of the line.
	 * @throws IllegalArgumentException - if the file is in an improper format (not
	 *                                  comma separated list)
	 */
	private static Student readStudent(String nextLine) {
		// Create scanner for the line
		Scanner lineScanner = new Scanner(nextLine);
		// use comma to separate the tokens
		lineScanner.useDelimiter(",");
		try {
			// try getting all of the variable fields of student object with lineScanner
			String firstName = lineScanner.next();
			String lastName = lineScanner.next();
			String id = lineScanner.next();
			String email = lineScanner.next();
			String hashedPW = lineScanner.next();
			int credits = lineScanner.nextInt();
			// return new student object
			return new Student(firstName, lastName, id, email, hashedPW, credits);
		} catch (InputMismatchException e) {
			throw new IllegalArgumentException("Improper file format.");
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Improper file format.");
		} finally {
			lineScanner.close();
		}
	}

	/**
	 * Writes a text file of the current student student directory to a specified
	 * directory. Throws IO Exception if the file name can not be written to.
	 * 
	 * @param fileName         - name of file that the user wants to output to
	 * @param studentDirectory - SortedList of students to be printed on the file
	 * @throws IOException     - when the specified output file can not be written
	 *                     on/created
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		// Prints to the specified file
		PrintStream fileWriter = new PrintStream(new File(fileName));
		// print each student from list
		for (int i = 0; i < studentDirectory.size(); i++) {
			fileWriter.println(studentDirectory.get(i).toString());
		}
		fileWriter.close();

	}

}
