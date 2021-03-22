package edu.ncsu.csc216.pack_scheduler.manager;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Tests the methods in RegistrationManager
 * 
 * @author Ashten Herr, Daniel Katowitz, Aditya Konidena
 *
 */
public class RegistrationManagerTest {

	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Software Development Fundamentals";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 3;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course meeting days */
	private static final String MEETING_DAYS = "MW";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;

	/** FIRST_NAME is a valid first name for a student object. */
	public static final String FIRST_NAME = "Daniel";
	/** LAST_NAME is a valid last name for a student object */
	public static final String LAST_NAME = "Katowitz";
	/** ID is a valid id for a student object */
	public static final String ID = "ID";
	/** EMAIL is a valid email string for a student object */
	public static final String EMAIL = "dtkatowi@ncsu.edu";
	/** HASH_PW represents a valid hashed password string for a student object */
	public static final String HASH_PW = "hashedpassword";
	/**
	 * CREDITS represents a valid credit value for a student which is also the
	 * default value.
	 */
	public static final int STUDENT_CREDITS = 18;

	/** Instance for RegistrationManager */
	private RegistrationManager manager;
	/** Registrar user name */
	private String registrarUsername;
	/** Registrar password */
	private String registrarPassword;
	/** Properties file */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Sets up the CourseManager and clears the data.
	 * 
	 * @throws Exception if error
	 */
	@Before
	public void setUp() throws Exception {
		manager = RegistrationManager.getInstance();
		manager.logout();
		manager.clearData();

		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
			registrarUsername = prop.getProperty("id");
			registrarPassword = prop.getProperty("pw");
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot process properties file.");
		}
	}

	/**
	 * Tests the getCourseCatalog method in RegistrationManager
	 */
	@Test
	public void testGetCourseCatalog() {
		CourseCatalog cc = new CourseCatalog();
		assertEquals(0, cc.getCourseCatalog().length);
		cc.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, 10, MEETING_DAYS, START_TIME, END_TIME);
		cc.addCourseToCatalog("CSC226", "Discrete Mathematics for Computer Scientists", SECTION, CREDITS, "ixdomnig",
				10, "TF", START_TIME, END_TIME);
		assertEquals(2, cc.getCourseCatalog().length);
		assertEquals("Discrete Mathematics for Computer Scientists",
				cc.getCourseFromCatalog("CSC226", "001").getTitle());
		assertEquals("001", cc.getCourseFromCatalog("CSC226", "001").getSection());
		assertEquals("CSC216", cc.getCourseFromCatalog("CSC216", "001").getName());
		assertEquals(TITLE, cc.getCourseFromCatalog("CSC216", "001").getTitle());

	}

	/**
	 * Tests the getStudentDirectory method in RegistrationManager
	 */
	@Test
	public void testGetStudentDirectory() {
		StudentDirectory sd = new StudentDirectory();
		assertEquals(0, sd.getStudentDirectory().length);
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, HASH_PW, HASH_PW, STUDENT_CREDITS);
		sd.addStudent("John", "Doe", "jdoe", "jdoe@ncsu.edu", "JKHSF^$SNCI(", "JKHSF^$SNCI(", 18);
		assertEquals(2, sd.getStudentDirectory().length);
		assertEquals(FIRST_NAME, sd.getStudentById(ID).getFirstName());
		assertEquals("John", sd.getStudentById("jdoe").getFirstName());
		assertEquals("Doe", sd.getStudentById("jdoe").getLastName());
		assertEquals("jdoe@ncsu.edu", sd.getStudentById("jdoe").getEmail());
		assertEquals(EMAIL, sd.getStudentById(ID).getEmail());

	}

	/**
	 * Tests the login method in RegistrationManager
	 */
	@Test
	public void testLogin() {
		String pass = "pw";
		manager.clearData();
		manager.logout();
		manager.getFacultyDirectory().addFaculty("Aditya", "Konidena", "akonide", "akonide@ncsu.edu", pass, pass, 2);
		assertTrue(manager.login("akonide", pass));
		assertEquals(manager.getFacultyDirectory().getFacultyById("akonide"), manager.getCurrentUser());

		manager.logout();
		manager.clearData();
		manager.logout();

		manager.getStudentDirectory().addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, pass, pass, STUDENT_CREDITS);
		assertTrue(manager.login(ID, pass));
		assertEquals(manager.getStudentDirectory().getStudentById(ID), manager.getCurrentUser());

		manager.logout();

		Properties prop = new Properties();

		try {
			InputStream input = new FileInputStream(PROP_FILE);
			prop.load(input);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}

		assertTrue(manager.login(prop.getProperty("id"), prop.getProperty("pw")));
		manager.logout();
		try {
			assertFalse(manager.login("fakeid", "fakepassword"));
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), "User doesn't exist.");
		}

	}

	/**
	 * Tests the logout method in RegistrationManager
	 */
	@Test
	public void testLogout() {
		manager.clearData();
		manager.logout();
		String pass = "pw";
		manager.getStudentDirectory().addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, pass, pass, STUDENT_CREDITS);
		assertTrue(manager.login(ID, pass));
		manager.logout();
		assertNull(manager.getCurrentUser());

	}

	/**
	 * Tests the getCurrentUser method in RegistrationManager
	 */
	@Test
	public void testGetCurrentUser() {
		manager.clearData();
		String pass = "pw";
		manager.getStudentDirectory().addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, pass, pass, STUDENT_CREDITS);
		assertTrue(manager.login(ID, pass));
		assertEquals(manager.getStudentDirectory().getStudentById(ID), manager.getCurrentUser());
		manager.logout();
		assertNull(manager.getCurrentUser());
	}

	/**
	 * Tests RegistrationManager.enrollStudentInCourse()
	 */
	@Test
	public void testEnrollStudentInCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.enrollStudentInCourse() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login(registrarUsername, registrarPassword);
		try {
			manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.enrollStudentInCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"RegistrationManager.enrollStudentInCourse() - currentUser is registrar, so cannot enroll in course.",
					registrarUsername, manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");

		assertTrue(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: True - Student max credits are 3 and course has 3.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001 then CSC 217-211\nResults: False - Student max credits are 3 and additional credit of CSC217-211 cannot be added, will exceed max credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC217", "211")));

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals("User: efrost\nCourse: CSC216-001\n", 3, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals("User: efrost\nCourse: CSC216-001\n", 1, scheduleFrostArray.length);
		assertEquals("User: efrost\nCourse: CSC216-001\n", "CSC216", scheduleFrostArray[0][0]);
		assertEquals("User: efrost\nCourse: CSC216-001\n", "001", scheduleFrostArray[0][1]);
		assertEquals("User: efrost\nCourse: CSC216-001\n", "Software Development Fundamentals",
				scheduleFrostArray[0][2]);
		assertEquals("User: efrost\nCourse: CSC216-001\n", "TH 1:30PM-2:45PM", scheduleFrostArray[0][3]);
		assertEquals("User: efrost\nCourse: CSC216-001\n", "9", scheduleFrostArray[0][4]);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		// Check Student Schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", 9,
				scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", 3, scheduleHicksArray.length);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "CSC216", scheduleHicksArray[0][0]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "001", scheduleHicksArray[0][1]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "Software Development Fundamentals",
				scheduleHicksArray[0][2]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "TH 1:30PM-2:45PM",
				scheduleHicksArray[0][3]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "8", scheduleHicksArray[0][4]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "CSC226", scheduleHicksArray[1][0]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "001", scheduleHicksArray[1][1]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n",
				"Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "MWF 9:35AM-10:25AM",
				scheduleHicksArray[1][3]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "9", scheduleHicksArray[1][4]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "CSC116", scheduleHicksArray[2][0]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "003", scheduleHicksArray[2][1]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "Intro to Programming - Java",
				scheduleHicksArray[2][2]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "TH 11:20AM-1:10PM",
				scheduleHicksArray[2][3]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "9", scheduleHicksArray[2][4]);

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.dropStudentFromCourse()
	 */
	@Test
	public void testDropStudentFromCourse() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// test if not logged in
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.dropStudentFromCourse() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login(registrarUsername, registrarPassword);
		try {
			manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001"));
			fail("RegistrationManager.dropStudentFromCourse() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals(
					"RegistrationManager.dropStudentFromCourse() - currentUser is registrar, so cannot enroll in course.",
					registrarUsername, manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");

		assertTrue(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: True - Student max credits are 3 and course has 3.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001 then CSC 217-211\nResults: False - Student max credits are 3 and additional credit of CSC217-211 cannot be added, will exceed max credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC217", "211")));

		assertFalse(
				"Action: drop\nUser: efrost\nCourse: CSC217-211\nResults: False - student not enrolled in the course",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC217", "211")));
		assertTrue("Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));

		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals("User: efrost\nCourse: CSC226-001, then removed\n", 0, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals("User: efrost\nCourse: CSC226-001, then removed\n", 0, scheduleFrostArray.length);

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", 9,
				scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", 3, scheduleHicksArray.length);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "CSC216", scheduleHicksArray[0][0]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "001", scheduleHicksArray[0][1]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "Software Development Fundamentals",
				scheduleHicksArray[0][2]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "TH 1:30PM-2:45PM",
				scheduleHicksArray[0][3]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "9", scheduleHicksArray[0][4]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "CSC226", scheduleHicksArray[1][0]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "001", scheduleHicksArray[1][1]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n",
				"Discrete Mathematics for Computer Scientists", scheduleHicksArray[1][2]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "MWF 9:35AM-10:25AM",
				scheduleHicksArray[1][3]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "9", scheduleHicksArray[1][4]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "CSC116", scheduleHicksArray[2][0]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "003", scheduleHicksArray[2][1]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "Intro to Programming - Java",
				scheduleHicksArray[2][2]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "TH 11:20AM-1:10PM",
				scheduleHicksArray[2][3]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\n", "9", scheduleHicksArray[2][4]);

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n", 6,
				scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n", 2,
				scheduleHicksArray.length);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n", "CSC216",
				scheduleHicksArray[0][0]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n", "001",
				scheduleHicksArray[0][1]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n",
				"Software Development Fundamentals", scheduleHicksArray[0][2]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n",
				"TH 1:30PM-2:45PM", scheduleHicksArray[0][3]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n", "9",
				scheduleHicksArray[0][4]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n", "CSC116",
				scheduleHicksArray[1][0]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n", "003",
				scheduleHicksArray[1][1]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n",
				"Intro to Programming - Java", scheduleHicksArray[1][2]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n",
				"TH 11:20AM-1:10PM", scheduleHicksArray[1][3]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001\n", "9",
				scheduleHicksArray[1][4]);

		assertFalse("Action: drop\nUser: efrost\nCourse: CSC226-001\nResults: False - already dropped",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC226", "001")));

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC216-001\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC216", "001")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n", 3,
				scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n", 1,
				scheduleHicksArray.length);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n",
				"CSC116", scheduleHicksArray[0][0]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n",
				"003", scheduleHicksArray[0][1]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n",
				"Intro to Programming - Java", scheduleHicksArray[0][2]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n",
				"TH 11:20AM-1:10PM", scheduleHicksArray[0][3]);
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001\n", "9",
				scheduleHicksArray[0][4]);

		assertTrue("Action: drop\nUser: efrost\nCourse: CSC116-003\nResults: True",
				manager.dropStudentFromCourse(catalog.getCourseFromCatalog("CSC116", "003")));

		// Check schedule
		ahicks = directory.getStudentById("ahicks");
		scheduleHicks = ahicks.getSchedule();
		assertEquals(
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001, CSC116-003\n",
				0, scheduleHicks.getScheduleCredits());
		scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals(
				"User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, removed CSC226-001, CSC216-001, CSC116-003\n",
				0, scheduleHicksArray.length);

		manager.logout();
	}

	/**
	 * Tests RegistrationManager.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		StudentDirectory directory = manager.getStudentDirectory();
		directory.loadStudentsFromFile("test-files/student_records.txt");

		CourseCatalog catalog = manager.getCourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");

		manager.logout(); // In case not handled elsewhere

		// Test if not logged in
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.resetSchedule() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login(registrarUsername, registrarPassword);
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is registrar, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertEquals("RegistrationManager.resetSchedule() - currentUser is registrar, so cannot enroll in course.",
					registrarUsername, manager.getCurrentUser().getId());
		}
		manager.logout();

		manager.login("efrost", "pw");
		assertTrue(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001\nResults: True - Student max credits are 3 and course has 3.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertFalse(
				"Action: enroll\nUser: efrost\nCourse: CSC216-001 then CSC 217-211\nResults: False - Student max credits are 3 and additional credit of CSC217-211 cannot be added, will exceed max credits.",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC217", "211")));

		manager.resetSchedule();
		// Check Student Schedule
		Student efrost = directory.getStudentById("efrost");
		Schedule scheduleFrost = efrost.getSchedule();
		assertEquals("User: efrost\nCourse: CSC226-001, then schedule reset\n", 0, scheduleFrost.getScheduleCredits());
		String[][] scheduleFrostArray = scheduleFrost.getScheduledCourses();
		assertEquals("User: efrost\nCourse: CSC226-001, then schedule reset\n", 0, scheduleFrostArray.length);
		assertEquals("Course should have all seats available after reset.", 10,
				catalog.getCourseFromCatalog("CSC226", "001").getCourseRoll().getOpenSeats());

		manager.logout();

		manager.login("ahicks", "pw");
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC226-001\nResults: False - duplicate",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC226", "001")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-001\nResults: False - time conflict",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "001")));
		assertTrue("Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003\nResults: True",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC116", "003")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC116-002\nResults: False - already in section of 116",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC216", "601")));
		assertFalse(
				"Action: enroll\nUser: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, CSC230-001\nResults: False - exceeded max credits",
				manager.enrollStudentInCourse(catalog.getCourseFromCatalog("CSC230", "001")));

		manager.resetSchedule();
		// Check Student schedule
		Student ahicks = directory.getStudentById("ahicks");
		Schedule scheduleHicks = ahicks.getSchedule();
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, then schedule reset\n", 0,
				scheduleHicks.getScheduleCredits());
		String[][] scheduleHicksArray = scheduleHicks.getScheduledCourses();
		assertEquals("User: ahicks\nCourse: CSC216-001, CSC226-001, CSC116-003, then schedule reset\n", 0,
				scheduleHicksArray.length);
		assertEquals("Course should have all seats available after reset.", 10,
				catalog.getCourseFromCatalog("CSC226", "001").getCourseRoll().getOpenSeats());
		assertEquals("Course should have all seats available after reset.", 10,
				catalog.getCourseFromCatalog("CSC216", "001").getCourseRoll().getOpenSeats());
		assertEquals("Course should have all seats available after reset.", 10,
				catalog.getCourseFromCatalog("CSC116", "003").getCourseRoll().getOpenSeats());

		manager.logout();
	}

	/**
	 * Tests the faculty schedule methods in registration manager.
	 * Assumes all IO classes are properly working
	 */
	@Test
	public void testFacultyScheduleMethods() {
		
		manager.logout(); // In case not handled elsewhere

		// Test if not logged in
		try {
			manager.resetSchedule();
			fail("RegistrationManager.resetSchedule() - If the current user is null, an IllegalArgumentException should be thrown, but was not.");
		} catch (IllegalArgumentException e) {
			assertNull("RegistrationManager.resetSchedule() - currentUser is null, so cannot enroll in course.",
					manager.getCurrentUser());
		}

		// test if registrar is logged in
		manager.login(registrarUsername, registrarPassword);
		manager.getCourseCatalog().newCourseCatalog();
		manager.getCourseCatalog().addCourseToCatalog("CSC216", "Software Development Fundamentals", "001", 3, null, 20, "MW", 1330, 1445);
		manager.getFacultyDirectory().addFaculty("Ashten", "Herr", "agherr", "agherr@ncsu.edu", "pw", "pw", 2);
		manager.addFacultyToCourse(manager.getCourseCatalog().getCourseFromCatalog("CSC216", "001"), manager.getFacultyDirectory().getFacultyById("agherr"));
		
		assertEquals(1, manager.getFacultyDirectory().getFacultyById("agherr").getSchedule().getNumScheduledCourses());
		
		manager.removeFacultyFromCourse(manager.getCourseCatalog().getCourseFromCatalog("CSC216", "001"), manager.getFacultyDirectory().getFacultyById("agherr"));
		
		assertEquals(0, manager.getFacultyDirectory().getFacultyById("agherr").getSchedule().getNumScheduledCourses());
		
		manager.addFacultyToCourse(manager.getCourseCatalog().getCourseFromCatalog("CSC216", "001"), manager.getFacultyDirectory().getFacultyById("agherr"));
		
		assertEquals(1, manager.getFacultyDirectory().getFacultyById("agherr").getSchedule().getNumScheduledCourses());
		
		manager.resetFacultySchedule(manager.getFacultyDirectory().getFacultyById("agherr"));
		
		assertEquals(0, manager.getFacultyDirectory().getFacultyById("agherr").getSchedule().getNumScheduledCourses());
	}
}