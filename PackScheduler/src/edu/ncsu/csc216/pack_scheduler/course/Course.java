package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * A course on a scheduling system. Course.java maintains information about a
 * course such as the name, section, title, instructor ID, credits and meeting
 * time. Allows the user to create a course with specified fields. User can
 * return all field methods.
 * 
 * @author Ashten Herr
 */
public class Course extends Activity implements Comparable<Course> {
	/** SECTION_LENGTH is the character length of a course section */
	private static final int SECTION_LENGTH = 3;
	/** MAX_CREDITS is the maximum credits a course can have */
	private static final int MAX_CREDITS = 5;
	/** MIN_CREDITS is the minimum credits a course can have */
	private static final int MIN_CREDITS = 1;
	/**
	 * SHORT_ARRAY_SIZE represents the short array size to store activity
	 * information.
	 */
	private static final int SHORT_ARRAY_SIZE = 5;
	/**
	 * LONG_ARRAY_SIZE represents the long array size to store activity information.
	 */
	private static final int LONG_ARRAY_SIZE = 7;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/**
	 * Course validator
	 */
	private CourseNameValidator validator;

	/**
	 * Course roll for the course.
	 */
	private CourseRoll roll;

	/**
	 * Creates course object with all possible fields a name, title, section,
	 * credits, instructor ID, meeting days, start time, end time.
	 * 
	 * @param name          represents the name of a course
	 * @param title         represents the title of a course
	 * @param section       represents the section number of the course as a string
	 * @param credits       represents the total credit hours of a course
	 * @param instructorId  represents the instructor ID
	 * @param enrollmentCap - capacity of course to hold students.
	 * @param meetingDays   represents which days the class meets on as a string
	 *                      (MTWHFA)
	 * @param startTime     the start time as military time
	 * @param endTime       the end time as military time
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays, int startTime, int endTime) {
		// call super constructor to set common fields
		super(title, meetingDays, startTime, endTime);
		// set course specific fields
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		roll = new CourseRoll(this, enrollmentCap);

	}

	/**
	 * Creates a course object without a start time and end time Course requires a
	 * name, title, section, credits, instructor ID, meeting days Start time for a
	 * course is set to 0, 0 when the course is not provided startTime and endTime
	 * 
	 * @param name          represents the name of a course
	 * @param title         represents the title of a course
	 * @param section       represents the section number of the course as a string
	 * @param credits       represents the total credit hours of a course
	 * @param instructorId  represents the instructor ID
	 * @param enrollmentCap capacity of course to hold students.
	 * @param meetingDays   represents which days the class meets on as a string
	 *                      (MTWHFA)
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap,
			String meetingDays) {
		// calls main constructor
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Returns course name.
	 * 
	 * @return the name of the course.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. If the name is null, has a length less than 5 or more
	 * than 8, does not contain a space between letter characters and number
	 * characters, has less than 1 or more than 4 letter characters, and not exactly
	 * three trailing digit characters, an IllegalArgumentException is thrown.
	 * 
	 * @param name - The name to set as the courses name, must be 5 - 8 characters
	 *             1-4 letter characters followed by a space and 3 trailing digits.
	 * 
	 * @throws IllegalArgumentException - if the name is null
	 * @throws IllegalArgumentException - if the name parameter less than 5 or more
	 *                                  than 8 characters or is not organized by 1
	 *                                  to 4 characters followed by a space and 3
	 *                                  trailing digits.
	 */
	private void setName(String name) {
		boolean valid;
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null.");
		}
		if ("".equals(name)) {
			throw new IllegalArgumentException("Invalid name");
		}
		validator = new CourseNameValidator();
		try {
			valid = validator.isValid(name);
			if (valid) {
				this.name = name;
			}

		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid name");
		}

//		// Throw exception if the name is null
//		if (name == null) {
//			throw new IllegalArgumentException("Name cannot be null.");
//		}
//
//		// Throw exception if the name is an empty string
//		// Throw exception if the name contains less than 5 character or greater than 8
//		// characters
//		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
//			throw new IllegalArgumentException("Name's length should be between 5 and 8, inclusive.");
//		}
//		// Check for pattern of L[LLL] NNN
//		int numberCount = 0;
//		int letterCount = 0;
//		boolean spaceFlag = false;
//		// check each index of name
//		for (int i = 0; i < name.length(); i++) {
//			// if the space has not been found yet
//			if (!spaceFlag) {
//				// check for a letter or space if neither are true throw exception
//				if (Character.isLetter(name.charAt(i))) {
//					letterCount++;
//				} else if (name.charAt(i) == ' ') {
//					spaceFlag = true;
//				} else {
//					throw new IllegalArgumentException("Names should have 1-4 letters, a space, and 3 digits.");
//				}
//				// if spaceFlag is true check for digits
//			} else if (spaceFlag) {
//				if (Character.isDigit(name.charAt(i))) {
//					numberCount++;
//				} else {
//					throw new IllegalArgumentException("Names should have 1-4 letters, a space, and 3 digits.");
//				}
//				// if after the space the character isn't a digit throw exception
//			} else {
//				throw new IllegalArgumentException("Names should have 1-4 letters, a space, and 3 digits.");
//			}
//		}
//		// Check that the number of letters is correct
//		if (letterCount < MIN_LETTER_COUNT || letterCount > MAX_LETTER_COUNT) {
//			throw new IllegalArgumentException("Names should have 1-4 letters, a space, and 3 digits.");
//		}
//		// Check that the number of digits is correct
//		if (numberCount != DIGIT_COUNT) {
//			throw new IllegalArgumentException("Names should have 1-4 letters, a space, and 3 digits.");
//		}
//		// set field
//		this.name = name;
	}

	/**
	 * Returns the section of the course
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the section of the course. A section must be 3 digits long and only
	 * contain digits.
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException - when section length is not 3 or is null
	 * @throws IllegalArgumentException - when section is not made up of digits.
	 */
	public void setSection(String section) {
		// check if the section is null or not a length of 3
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		// make sure each character is a digit
		for (int i = 0; i < section.length(); i++) {
			if (!Character.isDigit(section.charAt(i))) {
				throw new IllegalArgumentException("Section should be three digits.");
			}
		}
		this.section = section;
	}

	/**
	 * Returns the credit value of the course.
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the credit value of the course Credits must be between 1 and 5
	 * inclusive.
	 * 
	 * @param credits - The credit value of the course.
	 * @throws IllegalArgumentException when the credits is less than 1 or greater
	 *                                  than 5.
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Credits should be between 1 and 5, inclusive.");
		}
		this.credits = credits;
	}

	/**
	 * Returns the instructor ID.
	 * 
	 * @return instructorId - instructor ID of the course
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the intructorID. InstructorID can not be null or an empty string.
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException when instructorId is null or an empty
	 *                                  string.
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId != null && instructorId.length() == 0) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		this.instructorId = instructorId;
	}

	/**
	 * Returns a shortened array of course information to be used as a display in
	 * WolfScheduler includes the course name, section, title, and meeting string.
	 * 
	 * @return short version of display array
	 */
	@Override
	public String[] getShortDisplayArray() {
		// create array length 4 and fill fields and return
		String[] shortDisplay = new String[SHORT_ARRAY_SIZE];
		shortDisplay[0] = getName();
		shortDisplay[1] = getSection();
		shortDisplay[2] = getTitle();
		shortDisplay[3] = getMeetingString();
		shortDisplay[4] = roll.getOpenSeats() + "";
		return shortDisplay;
	}

	/**
	 * Returns a long version of an array of course information used to display
	 * course information. Long display array contains the course name, section,
	 * title, credits, Instructor ID, meeting string and and empty string for an
	 * event field.
	 * 
	 * @return long array version of the display array
	 */
	@Override
	public String[] getLongDisplayArray() {
		// create array length 7 and fill fields and return
		String[] longDisplay = new String[LONG_ARRAY_SIZE];
		longDisplay[0] = getName();
		longDisplay[1] = getSection();
		longDisplay[2] = getTitle();
		longDisplay[3] = "" + getCredits();
		longDisplay[4] = getInstructorId();
		longDisplay[5] = getMeetingString();
		longDisplay[6] = "";
		return longDisplay;
	}

	/**
	 * setMeetingDaysAndTime sets the meeting days and times for a course Meeting
	 * days is processed as a string and the times are in military format. The
	 * meeting day string must be one of the allowed characters (M T W H F A) and no
	 * repeats, otherwise invalid.
	 * 
	 * @param meetingDays - string representing the days the course takes place
	 * @param startTime   - start time of the course in military time
	 * @param endTime     - end time of the course in military time.
	 * 
	 * @throws IllegalArgumentException if meetingDays is null or an empty string
	 * @throws IllegalArgumentException if meetingDays contains anything other than
	 *                                  a (M T W H F A) character
	 * @throws IllegalArgumentException if any meetingDays characters repeat
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		// check for null or an empty string
		if (meetingDays == null || meetingDays.length() == 0) {
			throw new IllegalArgumentException("Invalid meeting days.");
		}
		// if a course is Arranged then default to Activity to set the times
		if ("A".equals(meetingDays)) {
			super.setMeetingDaysAndTime(meetingDays, 0, 0);
		} else {
			// start counter for each day
			int mon = 0;
			int tue = 0;
			int wed = 0;
			int thu = 0;
			int fri = 0;
			// check each index for all of the days
			for (int i = 0; i < meetingDays.length(); i++) {
				if (meetingDays.charAt(i) == 'M') {
					mon++;
				} else if (meetingDays.charAt(i) == 'T') {
					tue++;
				} else if (meetingDays.charAt(i) == 'W') {
					wed++;
				} else if (meetingDays.charAt(i) == 'H') {
					thu++;
				} else if (meetingDays.charAt(i) == 'F') {
					fri++;
				} else {
					// throw IAE when a day is not valid
					throw new IllegalArgumentException("Invalid meeting days.");
				}
			}
			// make sure there are no repeat days
			if (mon > 1 || tue > 1 || wed > 1 || thu > 1 || fri > 1) {
				throw new IllegalArgumentException("Invalid meeting days.");
			}
			// check common time fields in parent class
			super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		}

	}

	/**
	 * Returns the course roll.
	 * 
	 * @return course roll of the course.
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}

	/**
	 * Returns comma separated value of all course fields.
	 * 
	 * @return String representation of the course which is a comma seperated list
	 *         of each field.
	 */
	@Override
	public String toString() {
		// if meeting days equals A there is no specified class time
		if ("A".equals(getMeetingDays())) {
			return name + "," + getTitle() + "," + getSection() + "," + getCredits() + "," + getInstructorId() + ","
					+ roll.getEnrollmentCap() + "," + getMeetingDays();
		}
		return name + "," + getTitle() + "," + getSection() + "," + getCredits() + "," + getInstructorId() + ","
				+ roll.getEnrollmentCap() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime();
	}

	/**
	 * Returns the hash code value of a course.
	 * 
	 * @return the hash code value of a course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Tests equality between two courses courses are considered equal if they are
	 * of the same type and if all of the course fields are equal to each other.
	 * 
	 * @return whether the two courses are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof Course))
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * tests if two events are duplicates Events are duplicates if they are both of
	 * the Event type and if they have the same name
	 * 
	 * @param activity - the activity being tested as an event duplicate
	 * @return whether the events are duplicate or not
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		// check for instance of course then cast
		if (activity instanceof Course) {
			Course activity2 = (Course) activity;
			// return whether they have the same name
			return this.name.equals(activity2.getName()) && this.section.equals(activity2.getSection());
		}
		return false;

	}

	/**
	 * Compares two courses to find the lexicographic order by name, then section.
	 * 
	 * @param c - course to compare to.
	 * @return 1 if the current instance occurs after the parameter. 0 if the
	 *         current instance matches the parameter. -1 if the current instance
	 *         occurs before the parameter.
	 * @throws IllegalArgumentException if the course is null
	 */
	@Override
	public int compareTo(Course c) {
		if (null == c) {
			throw new IllegalArgumentException("Course cannot be null");
		}
		// check course names first
		int courseName = name.compareToIgnoreCase(c.getName());
		if (courseName > 0) {
			return 1;
		} else if (courseName < 0) {
			return -1;
		} else {

			// checks section numbers
			int sectionValue = section.compareToIgnoreCase(c.getSection());
			if (sectionValue > 0) {
				return 1;
			} else if (sectionValue < 0) {
				return -1;
			}
		}
		return 0;
	}

}