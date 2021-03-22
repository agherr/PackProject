package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests StudentDirectory.
 * 
 * @author Sarah Heckman
 * @author Ashten Herr
 * @author Mark Shanahan
 */
public class StudentDirectoryTest {

	/** Valid course records */
	private final String validTestFile = "test-files/student_records.txt";

	/** File that does not exist */
	private final String nonExistingFile = "test-files/i_dont_exist.txt";

	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_CREDITS = 15;

	/**
	 * Resets course_records.txt for use in other tests.
	 * 
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {
		// Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_student_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "student_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			String f = e.getMessage();
			System.out.print(f);
			e.printStackTrace();
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests StudentDirectory().
	 */
	@Test
	public void testStudentDirectory() {
		// Test that the StudentDirectory is initialized to an empty list
		StudentDirectory sd = new StudentDirectory();
		assertFalse(sd.removeStudent("sesmith5"));
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.testNewStudentDirectory().
	 */
	@Test
	public void testNewStudentDirectory() {
		// Test that if there are students in the directory, they
		// are removed after calling newStudentDirectory().
		StudentDirectory sd = new StudentDirectory();

		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);

		sd.newStudentDirectory();
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.loadStudentsFromFile().
	 */
	@Test
	public void testLoadStudentsFromFile() {
		StudentDirectory sd = new StudentDirectory();

		// Test valid file
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);

		// test a non existent file
		StudentDirectory sd1 = new StudentDirectory();
		try {
			sd1.loadStudentsFromFile(nonExistingFile);
			fail("File should not exist and catch FileNotFoundException");
		} catch (IllegalArgumentException e) {
			assertEquals(0, sd1.getStudentDirectory().length);
		}
	}

	/**
	 * Tests StudentDirectory.addStudent().
	 */
	@Test
	public void testAddStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Test valid Student
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String[][] studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);
		assertEquals(LAST_NAME, studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[0][2]);
		// Add a second student with lower credits than the minimum
		sd.addStudent("Ashten", "Herr", "ID", "agherr@ncsu.edu", "pw", "pw", 2);
		studentDirectory = sd.getStudentDirectory();
		assertEquals(2, studentDirectory.length);
		assertEquals("Ashten", studentDirectory[1][0]);
		assertEquals("Herr", studentDirectory[1][1]);
		assertEquals("ID", studentDirectory[1][2]);
		// Try to add a repeat student and check that the student is not added.
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		studentDirectory = sd.getStudentDirectory();
		assertEquals(2, studentDirectory.length);
		try {
			sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(2, studentDirectory.length);
		}
		try {
			sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, "WrongPassword", PASSWORD, MAX_CREDITS);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(2, studentDirectory.length);
		}

	}

	/**
	 * Tests StudentDirectory.removeStudent().
	 */
	@Test
	public void testRemoveStudent() {
		StudentDirectory sd = new StudentDirectory();

		// Add students and remove
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		assertTrue(sd.removeStudent("efrost"));
		String[][] studentDirectory = sd.getStudentDirectory();
		assertEquals(9, studentDirectory.length);
		assertEquals("Lane", studentDirectory[1][0]);
		assertEquals("Berg", studentDirectory[1][1]);
		assertEquals("lberg", studentDirectory[1][2]);
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory().
	 */
	@Test
	public void testSaveStudentDirectory() {
		StudentDirectory sd = new StudentDirectory();

		// Add a student, and check to make sure that it is correct
		sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		assertEquals(1, sd.getStudentDirectory().length);
		sd.saveStudentDirectory("test-files/actual_student_records.txt");
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}

	/**
	 * Helper method to compare two files for the same contents
	 * 
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

	/**
	 * Tests the getStudentById() method
	 */
	@Test
	public void testGetStudentById() {

		StudentDirectory sd = new StudentDirectory();
		
		sd.loadStudentsFromFile(validTestFile);
		//create a student and add identical student
		Student s = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		
		//check that the two students you get have the same ID after getting the student by id
		assertEquals(s.getId(), sd.getStudentById(ID).getId());
		assertEquals(s.getFirstName(), sd.getStudentById(ID).getFirstName());
		assertEquals(s.getLastName(), sd.getStudentById(ID).getLastName());
		assertNull(sd.getStudentById("jumpingjack"));
	}
}
