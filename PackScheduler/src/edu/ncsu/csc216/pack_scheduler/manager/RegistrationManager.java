package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Class that accesses all behaviors of the RegistrationManager to get all of
 * the courses in the CourseCatalog and Students in the StudentDirectory, as
 * well as hashes the passwords for logging in and out of the
 * RegistrationManager class.
 * 
 * @author Ashten Herr, Daniel Katowitz, Aditya Konidena
 *
 */
public class RegistrationManager {

	/**
	 * An instance of registration manager
	 */
	private static RegistrationManager instance;
	/**
	 * Faculty Directory
	 */
	private static FacultyDirectory facultyDirectory;
	/**
	 * Course catalog
	 */
	private static CourseCatalog courseCatalog;
	/**
	 * Student directory
	 */
	private static StudentDirectory studentDirectory;
	/**
	 * The user
	 */
	private User registrar;
	/**
	 * The current user
	 */
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/**
	 * Used to access the registrar.properties file when logging in/out of registrar
	 * user.
	 */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * creates a registrar and new faculty directory
	 */
	private RegistrationManager() {
		createRegistrar();
		facultyDirectory = new FacultyDirectory();
	}

	/**
	 * Creates a registrar from the properties file. Input comes from properties
	 * file with stored registrar information.
	 * 
	 * @throws IllegalArgumentException if the registrar can not be created due to a
	 *                                  file issue.
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
	 * Method used to hash the password to provide extra security uses hash
	 * algorithm to hash a password into its hash value.
	 * 
	 * @param pw to be hashed
	 * @return String of hashed password
	 * @throws IllegalArgumentException if the password can not be hashed.
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return new String(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * Method to get an instance of the RegistrationManager, if the instance is null
	 * creates a new registrationManager with new fields for the course catalog and
	 * student directory.
	 * 
	 * @return instance of the RegistrationManager
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
			courseCatalog = new CourseCatalog();
			studentDirectory = new StudentDirectory();
		}
		return instance;
	}

	/**
	 * Method to get the CourseCatalog that holds all of the Course information
	 * 
	 * @return CourseCatalog of Course objects
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * Method to get the StudentDirectory that contains the Student information
	 * 
	 * @return StudentDirectory of Student objects
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * Authenticates the login details provided, first its checked that a user is
	 * not already logged in then credentials are checked with the registrar then
	 * checked with the student directory to log in, if the user does not exist an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param id       - id of user
	 * @param password - password of user
	 * @return true if login is successful, false in all other cases
	 * @throws IllegalArgumentException - if the user id does not match with a
	 *                                  registrar or student directory.
	 * 
	 */
	public boolean login(String id, String password) {
		// check if the current user is null

		if (getCurrentUser() == null) {

			// check with the registrar first that password and id match
			if (registrar.getId().equals(id)) {
				String localHashPW = hashPW(password);
				if (registrar.getPassword().equals(localHashPW)) {
					currentUser = registrar;
					return true;
				}
				return false;
			} else {
				// check if there is an existing student otherwise throw exception
				Student s = studentDirectory.getStudentById(id);
				Faculty f = facultyDirectory.getFacultyById(id);

				if (s == null && f == null) {
					throw new IllegalArgumentException("User doesn't exist.");
				}

				// check the student id and password to match
				if (s != null && f == null) {
					String localHashPW = hashPW(password);
					if (s.getPassword().equals(localHashPW)) {
						currentUser = s;
						return true;
					}
				} else if (s == null && f != null) {
					String localHashPW = hashPW(password);
					if (f.getPassword().equals(localHashPW)) {
						currentUser = f;
						return true;
					}
				}
			}
		}
		// non matching passwords return false
		return false;

	}

	/**
	 * Logs the user out
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * Gets the current user of the system
	 * 
	 * @return the current user
	 */
	public User getCurrentUser() {

		return currentUser;
	}

	/**
	 * Clears data from both course catalog and student directory
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}

	private static class Registrar extends User {
		/**
		 * Creates a registrar user. Calls User constructor to create registrar.
		 * 
		 * @param firstName first name of registrar
		 * @param lastName  last name of registrar
		 * @param id        id of registrar
		 * @param email     email of registrar
		 * @param hashPW    hashPW of registrar
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}

	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * 
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			CourseRoll roll = c.getCourseRoll();

			if (s.canAdd(c) && roll.canEnroll(s)) {
				roll.enroll(s);
				schedule.addCourseToSchedule(c);
				return true;
			}

		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * 
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			c.getCourseRoll().drop(s);
			return s.getSchedule().removeCourseFromSchedule(c);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Resets the logged in student's schedule by dropping them from every course
	 * and then resetting the schedule.
	 */
	public void resetSchedule() {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			String[][] scheduleArray = schedule.getScheduledCourses();
			for (int i = 0; i < scheduleArray.length; i++) {
				Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
				c.getCourseRoll().drop(s);
			}
			schedule.resetSchedule();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
	}

	/**
	 * Gets the faculty directory
	 * 
	 * @return the faculty directory
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * Adds a course to a faculty schedule, exceptions propagate to GUI. Current
	 * user must be the registrar.
	 * 
	 * @param c - course being added
	 * @param f - faculty whos schedule is adding the course
	 * @return whether the faculty is successfully added
	 * @throws IllegalArgumentException if the current user is not the registrar
	 */
	public boolean addFacultyToCourse(Course c, Faculty f) {
		if (currentUser instanceof Registrar) {
			return f.getSchedule().addCourseToSchedule(c);
		} else if (currentUser != null && !(currentUser instanceof Registrar)) {
			throw new IllegalArgumentException();
		}
		return false;
	}

	/**
	 * Removes a course from a faculty schedule, exceptions propagate to GUI,
	 * Current user must be a registrar.
	 * 
	 * @param c - course being removed
	 * @param f - faculty whos schedule is removing the course.
	 * @return whether the faculty can be removed
	 * @throws IllegalArgumentException if the current user is not the registrar
	 */
	public boolean removeFacultyFromCourse(Course c, Faculty f) {
		if (currentUser instanceof Registrar) {
			return f.getSchedule().removeCourseFromSchedule(c);
		} else if (currentUser != null && !(currentUser instanceof Registrar)) {
			throw new IllegalArgumentException();
		}
		return false;
	}

	/**
	 * Resets a faculty schedule if the current user is the registrar.
	 * 
	 * @param f - faculty whos schedule is being reset.
	 * @throws IllegalArgumentException if the current user is not the registrar
	 */
	public void resetFacultySchedule(Faculty f) {
		if (currentUser instanceof Registrar) {
			f.getSchedule().resetSchedule();
		} else if (currentUser != null && !(currentUser instanceof Registrar)) {
			throw new IllegalArgumentException();
		}
	}
}