package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Tests StudentRecordIO class.
 * 
 * @author Ashten
 * @author Torin Cuany
 *
 */
public class StudentRecordIOTest {

	/** Valid student records */
	private final String validTestFile = "test-files/student_records.txt";

	/** Invalid student records */
	private final String invalidTestFile = "test-files/invalid_student_records.txt";
	
	/** Duplicate student ID file */
	private final String duplicateIdFile = "test-files/duplicate_student_id.txt";
	
	/** File with improper format */
	private final String improperFile = "test-files/improper_file_format.txt";
	
	/** Valid student for testing file output with student array. */
	private String validStudent0 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	/** Valid student for testing file output with student array. */
	private String validStudent1 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	/** Valid student for testing file output with student array. */
	private String validStudent2 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	/** Valid student for testing file output with student array. */
	private String validStudent3 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	/** Valid student for testing file output with student array. */
	private String validStudent4 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	/** Valid student for testing file output with student array. */
	private String validStudent5 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/** Valid student for testing file output with student array. */
	private String validStudent6 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	/** Valid student for testing file output with student array. */
	private String validStudent7 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	/** Valid student for testing file output with student array. */
	private String validStudent8 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	/** Valid student for testing file output with student array. */
	private String validStudent9 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";

	/** Array made up of valid students created */
	private String[] validStudents = { validStudent3, validStudent6, validStudent4, validStudent5, validStudent2,
			validStudent8, validStudent0, validStudent9, validStudent1, validStudent7 };

	/** Field for hash password */
	private String hashPW;
	/** Hashed password version of the given password for valid students "pw" */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Set up for file IO for StudentRecordIO
	 * 
	 * @throws Exception - catch any unwarranted exceptions from set up.
	 */
	@Before
	public void setUp() throws Exception {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = new String(digest.digest());

			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
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

	/**
	 * Tests readStudentRecords method.
	 */
	@Test
	public void testReadStudentRecords() {
		// test reading a valid file first
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(validTestFile);
			assertEquals(10, students.size());
			for (int i = 0; i < validStudents.length; i++) {
				assertEquals(validStudents[i], students.get(i).toString());
			}
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + validTestFile);
		}

		// test reading an invalid file
		try {
			// check that no student is added to the array
			SortedList<Student> invalidStudents = StudentRecordIO.readStudentRecords(invalidTestFile);
			assertEquals(0, invalidStudents.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + invalidTestFile);
		}
		//read file with a duplicate student ID
		try {
			// check that duplicate student ID's will not be added twice to an array
			SortedList<Student> duplicateStudents = StudentRecordIO.readStudentRecords(duplicateIdFile);
			assertEquals(1, duplicateStudents.size());
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + duplicateIdFile);
		}
		//read a file with improper file format
		SortedList<Student> improperStudents = null;
		try {
			improperStudents = StudentRecordIO.readStudentRecords(improperFile);
		} catch (FileNotFoundException e) {
			fail("Unexpected error reading " + improperFile);
		}
		//check that the student was not added due to an InputMismatchException
		assertEquals(improperStudents.size(), 0);
	}

	/**
	 * Tests writeStudentRecords method.
	 * writes two students to a text file and checks the file is matching the expected results
	 */
	@Test
	public void testWriteStudentRecords() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		try {
			StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students);
		} catch (IOException e) {
			fail("Cannot write to student records file");
		}
		//check that the files match each other
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}

	/**
	 * Tests attempting to write to a file outside of the permissions.
	 */
	@Test
	public void testWriteStudentRecordsNoPermissions() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		// Assumption that you are using a hash of "pw" stored in hashPW

		try {
			StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students);
			fail("Attempted to write to a directory location that doesn't exist or without the appropriate permissions and the write happened.");
		} catch (IOException e) {
			assertEquals("/home/sesmith5/actual_student_records.txt (Permission denied)", e.getMessage());
			// The actual error message on Jenkins!
		}

	}

}
