package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Tests for FacultyRecordIOTest class
 * 
 * @author Ashten Herr, Aditya Konidena
 *
 */
public class FacultyRecordIOTest {

	/**
	 * Tests the reading file function of FacultyRecordIO
	 */
	@Test
	public void testReadValidFile() {
		try {
			LinkedList<Faculty> l = FacultyRecordIO.readFacultyRecords("test-files/faculty_records.txt");

			// Check size and contents
			assertEquals(8, l.size());

			assertEquals("awitt", l.get(0).getId());
			assertEquals("fmeadow", l.get(1).getId());
			assertEquals("bbrewer", l.get(2).getId());
			assertEquals("haguirr", l.get(3).getId());
			assertEquals("kpatel", l.get(4).getId());
			assertEquals("ebriggs", l.get(5).getId());
			assertEquals("nbrady", l.get(6).getId());
			assertEquals("lwalls", l.get(7).getId());

		} catch (FileNotFoundException e) {
			fail();
		}

	}

	/**
	 * Tests reading an invalid file
	 */
	@Test
	public void testInvalidFile() {
		try {
			// Try to read an invalid file
			LinkedList<Faculty> l = FacultyRecordIO.readFacultyRecords("test-files/invalid_faculty_records.txt");

			// Check size and contents
			assertEquals(0, l.size());
		} catch (FileNotFoundException e) {
			fail();
		}
	}

	/**
	 * Tests writing to a file.
	 */
	@Test
	public void testWriteFile() {
		Faculty f = new Faculty("Ashten", "Herr", "agherr", "agherr@gmail.com", "pw", 2);

		Faculty f1 = new Faculty("Bruh", "Moment", "bruh", "agherr@gmail.com", "pw", 2);

		Faculty f2 = new Faculty("Hello", "Random", "cschurt", "agherr@gmail.com", "pw", 2);

		LinkedList<Faculty> l = new LinkedList<Faculty>();

		assertTrue(l.add(f));
		assertTrue(l.add(f1));
		assertTrue(l.add(f2));

		try {
			FacultyRecordIO.writeFacultyRecords("test-files/actual_faculty_record1.txt", l);
			checkFiles("test-files/expected_faculty_records1.txt", "test-files/actual_faculty_record1.txt");
		} catch (FileNotFoundException e) {
			fail();
		}
	}

	/**
	 * Compares if two files are equal to each other.
	 * 
	 * @param expFile - expected file given
	 * @param actFile - actual file output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals("Expected: " + exp + " Actual: " + act, exp, act);
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}

			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
