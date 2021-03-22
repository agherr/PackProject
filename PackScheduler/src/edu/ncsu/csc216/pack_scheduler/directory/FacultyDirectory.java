package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Faculty Directory manages a group of faculty members, Faculty Members are
 * stored in a linked list and can be added, removed and loaded/saved from
 * files.
 * 
 * @author Ashten Herr, Aditya Konidena
 *
 */
public class FacultyDirectory {

	/** facultyDirectory holds faculty in a linked list */
	private LinkedList<Faculty> facultyDirectory;

	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Constructor creates a new empty facultyDirectory to hold faculty members.
	 */
	public FacultyDirectory() {
		newFacultyDirectory();
	}

	/**
	 * Sets the current facultyDirectory to a new empty linked list.
	 */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}

	/**
	 * Loads faculty into the faculty list from a file.
	 * 
	 * @param filename - file being loaded from
	 * @throws IllegalArgumentException if not able to read file
	 */
	public void loadFacultyFromFile(String filename) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(filename);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + filename);
		}
	}

	/**
	 * Creates a new faculty member and attempts to add it to the faculty list.
	 * Returns whether the faculty member is successfully added, duplicates are not
	 * allowed.
	 * 
	 * @param firstName      - first name of the faculty member
	 * @param lastName       - last name of the faculty member
	 * @param id             - id of the faculty member
	 * @param email          - email of the faculty member
	 * @param password       - password given
	 * @param repeatPassword - repeated password
	 * @param maxCourses     - max course value a faculty member can have
	 * @throws IllegalArgumentException for any invalid case
	 * @return - whether the faculty member was successfully added or not
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String password,
			String repeatPassword, int maxCourses) {
		String hashPW = "";
		String repeatHashPW = "";
		if (password == null || repeatPassword == null || "".equals(password) || "".equals(repeatPassword)) {
			throw new IllegalArgumentException("Invalid password");
		}
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(password.getBytes());
			hashPW = new String(digest1.digest());

			MessageDigest digest2 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest2.update(repeatPassword.getBytes());
			repeatHashPW = new String(digest2.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}

		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}

		// If an IllegalArgumentException is thrown, it's passed up from Student
		// to the GUI
		Faculty faculty = null;
		faculty = new Faculty(firstName, lastName, id, email, hashPW, maxCourses);

		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty f = facultyDirectory.get(i);
			if (faculty.getId().equals(f.getId())) {
				return false;
			}
		}
		return facultyDirectory.add(faculty);
	}

	/**
	 * Removes a faculty member with a specific id. Returns true if the faculty
	 * member exists in the directory and is removed, false if the faculty member
	 * was not found in the directory.
	 * 
	 * @param id - id of faculty member being removed
	 * @return whether the faculty was successfully removed
	 */
	public boolean removeFaculty(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			if (facultyDirectory.get(i).getId().equals(id)) {
				facultyDirectory.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a faculty directory with the first name, last name, and id.
	 * 
	 * @return String array of faculty informations
	 */
	public String[][] getFacultyDirectory() {
		String[][] displayArray = new String[facultyDirectory.size()][3];

		for (int i = 0; i < facultyDirectory.size(); i++) {
			displayArray[i][0] = facultyDirectory.get(i).getFirstName();
			displayArray[i][1] = facultyDirectory.get(i).getLastName();
			displayArray[i][2] = facultyDirectory.get(i).getId();
		}
		return displayArray;
	}

	/**
	 * Saves the current facultyDirectory to a file by referencing the IO class.
	 * 
	 * @param filename - filename being saved to
	 * @throws IllegalArgumentException if data cannot be written to file
	 */
	public void saveFacultyDirectory(String filename) {
		try {
			FacultyRecordIO.writeFacultyRecords(filename, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + filename);
		}
	}

	/**
	 * Returns faculty member in linked list with a specific id.
	 * 
	 * @param id - id of the faculty member
	 * @return faculty member with specific id
	 */
	public Faculty getFacultyById(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			if (facultyDirectory.get(i).getId().equals(id)) {
				return facultyDirectory.get(i);
			}
		}
		return null;
	}
}
