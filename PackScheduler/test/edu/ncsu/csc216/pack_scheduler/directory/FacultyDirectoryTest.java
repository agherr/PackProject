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

/**
 * Tests for FacultyDirectory
 * 
 * @author Aditya Konidena, Ashten Herr
 *
 */
public class FacultyDirectoryTest {

	/** Valid course records */
	private final String validTestFile = "test-files/faculty_records.txt";

	/** File that does not exist */
	private final String nonExistingFile = "test-files/i_dont_exist.txt";

	/** Test first name */
	private static final String FIRST_NAME = "John";
	/** Test last name */
	private static final String LAST_NAME = "Doe";
	/** Test id */
	private static final String ID = "jdoe";
	/** Test email */
	private static final String EMAIL = "jdoe@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_COURSES = 3;

	/**
	 * Resets faculty_records.txt for use in other tests
	 */
	@Before
	public void setUp() {
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_faculty_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "faculty_records.txt");
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
	 * Tests FacultyDirectory()
	 */
	@Test
	public void testFacultyDirectory() {
		FacultyDirectory fd = new FacultyDirectory();
		assertFalse(fd.removeFaculty("sesmith5"));
		assertEquals(0, fd.getFacultyDirectory().length);
	}

	/**
	 * Tests loadFacultyFromFile()
	 */
	@Test
	public void testLoadFacultyFromFile() {
		FacultyDirectory fd = new FacultyDirectory();

		// Test valid file
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);

		// test a non existent file
		FacultyDirectory fd1 = new FacultyDirectory();
		try {
			fd1.loadFacultyFromFile(nonExistingFile);
			fail("File should not exist and catch FileNotFoundException");
		} catch (IllegalArgumentException e) {
			assertEquals(0, fd1.getFacultyDirectory().length);
		}
	}

	/**
	 * Tests addFaculty()
	 */
	@Test
	public void testAddFaculty() {
		FacultyDirectory fd = new FacultyDirectory();

		// Test valid Student
		fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		String[][] facultyDirectory = fd.getFacultyDirectory();
		assertEquals(1, facultyDirectory.length);
		assertEquals(FIRST_NAME, facultyDirectory[0][0]);
		assertEquals(LAST_NAME, facultyDirectory[0][1]);
		assertEquals(ID, facultyDirectory[0][2]);
		// Add a second student with lower credits than the minimum
		fd.addFaculty("Ashten", "Herr", "ID", "agherr@ncsu.edu", "pw", "pw", 2);
		facultyDirectory = fd.getFacultyDirectory();
		assertEquals(2, facultyDirectory.length);
		assertEquals("Ashten", facultyDirectory[1][0]);
		assertEquals("Herr", facultyDirectory[1][1]);
		assertEquals("ID", facultyDirectory[1][2]);
		// Try to add a repeat student and check that the student is not added.
		fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_COURSES);
		facultyDirectory = fd.getFacultyDirectory();
		assertEquals(2, facultyDirectory.length);
		try {
			fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, null, PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(2, facultyDirectory.length);
		}
		try {
			fd.addFaculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "WrongPassword", PASSWORD, MAX_COURSES);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(2, facultyDirectory.length);
		}
	}

	/**
	 * Tests removeFaculty()
	 */
	@Test
	public void testRemoveFaculty() {
		FacultyDirectory fd = new FacultyDirectory();

		// Add students and remove
		fd.loadFacultyFromFile(validTestFile);
		assertEquals(8, fd.getFacultyDirectory().length);
		assertTrue(fd.removeFaculty("bbrewer"));
		String[][] facultyDirectory = fd.getFacultyDirectory();
		assertEquals(7, facultyDirectory.length);
		assertEquals("Fiona", facultyDirectory[1][0]);
		assertEquals("Meadows", facultyDirectory[1][1]);
		assertEquals("fmeadow", facultyDirectory[1][2]);
	}

	/**
	 * Test for saveFacultyDirectory()
	 */
	@Test
	public void testSaveFacultyDirectory() {
		FacultyDirectory fd = new FacultyDirectory();

		// Add a student, and check to make sure that it is correct
		fd.addFaculty("John", "Doe", "jdoe", "jdoe@ncsu.edu", "pw", "pw", 2);
		assertEquals(1, fd.getFacultyDirectory().length);
		fd.saveFacultyDirectory("test-files/actual_faculty_records.txt");
		checkFiles("test-files/expected_faculty_records2.txt", "test-files/actual_faculty_records.txt");
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

}
