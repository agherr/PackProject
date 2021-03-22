package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Activity Class is the abstract class that encompasses the Course and Event
 * Classes An activity must have a title, start time, end time, meeting days,
 * and the fields the concrete class requires (course or event).
 * 
 * Activities have two versions of display arrays, a short and long one to
 * display in the schedule tabs.
 * 
 * @author Ashten Herr
 */
public abstract class Activity implements Conflict {

	/** UPPER_HOUR is the total hours in a day */
	private static final int UPPER_HOUR = 24;
	/** UPPER_MINUTE is the total minutes in an hour */
	private static final int UPPER_MINUTE = 60;
	/** HALF_DAY represents half the hours in a day */
	private static final int HALF_DAY = 12;
	/**
	 * MOD_VALUE represents the mod value to get the hours/minute value of the
	 * military time
	 */
	private static final int MOD_VALUE = 100;
	/**
	 * DIVISOR represents the divisor value for 100, used to separate the minutes
	 * and hour values when given military time.
	 */
	private static final int DIVISOR = 100;
	/**
	 * MINUTE_CONVERT represents the calculation used to see if a the minute value
	 * is less than 10. Used in converting military time to standard time.
	 */
	private static final int MINUTE_CONVERT = 10;
	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time in military time. */
	private int startTime;
	/** Course's ending time in military time. */
	private int endTime;

	/**
	 * Constructor for Activity which is the abstract class for an event or a
	 * course. Sets title and meeting days and time and refers to the concrete class
	 * to complete construction.
	 * 
	 * @param title       - title of the activity
	 * @param meetingDays - meeting days of the activity as a string (MTWHFA)
	 * @param startTime   - start time as a military time value
	 * @param endTime     - end time as a military time value
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Checks for duplicate activities
	 * 
	 * @param activity being tested as a duplicate
	 * @return whether the activities are duplicates or not
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * getShortDisplayArray returns a shortened version of an array to be displayed
	 * in the GUI. Array contents differ on whether the activity is a course or an
	 * event.
	 * 
	 * @return short array of activity information
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * getShortDisplayArray returns a long version of an array to be displayed in
	 * the GUI. Array contents differ on whether the activity is a course or an
	 * event.
	 * 
	 * @return long array of activity information
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Returns the title of course.
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the course title A course title can not be null or an empty string An
	 * invalid course title will throw an exception
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException if the title is null or an empty string.
	 */
	public void setTitle(String title) {
		// check if title is null or an empty string
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}

	/**
	 * Returns the meetingDays of the course.
	 * 
	 * @return meetingDays - String of days the course meets (MTWHFA).
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the start time of the course.
	 * 
	 * @return startTime - Start time of course in military time.
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the end time of the course
	 * 
	 * @return endTime - The end time of the course in military time.
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * setMeetingDaysAndTime checks the startTime and endTime parameters, which can
	 * not be greater than 2399 or less than 0000. setMeetingDaysAndTime also checks
	 * that end time is greater than start time otherwise IAE is thrown. meetingDays
	 * Parameter is checked in child classes.
	 * 
	 * @param meetingDays - meeting days as a string
	 * @param startTime   - start time as military time
	 * @param endTime     - end time as military time
	 * 
	 * @throws IllegalArgumentException if startTime is greater than endTime
	 * @throws IllegalArgumentException if an invalid military time is given. Hours
	 *                                  can not be less than 0 or above 23 and
	 *                                  minutes can not be less than 0 or above 59.
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		// find each hour and minute value
		int startHour = (startTime / DIVISOR) % MOD_VALUE;
		int startMin = startTime % MOD_VALUE;
		int endHour = (endTime / DIVISOR) % MOD_VALUE;
		int endMin = endTime % MOD_VALUE;
		// make sure each value fits the constraints
		// hour can't be negative or above 23 and minute can not be above 59
		if (startHour < 0 || startHour >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid start time.");
		}
		if (startMin < 0 || startMin >= UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid start time.");
		}
		if (endHour < 0 || endHour >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid end time.");
		}

		if (endMin < 0 || endMin >= UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid end time.");
		}
		// check to see that the start time is not later than the end time
		if (endTime < startTime) {
			throw new IllegalArgumentException("End time cannot be before start time.");
		}
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Returns a string with the meeting days and the start and end times formatted
	 * in regular time. Format is meetingDays formatted start time - formatted end
	 * time ex - MW 1:35PM-2:35PM. If the class is Arranged then "Arranged" is
	 * returned as the meeting string.
	 * 
	 * @return the meeting string as a string with meeting days start time and end
	 *         time
	 */
	public String getMeetingString() {
		// check that meetingDays is arranged
		if ("A".equals(meetingDays)) {
			return "Arranged";
		}
		return meetingDays + " " + getTimeString(startTime) + "-" + getTimeString(endTime);

	}

	/**
	 * getTimeString takes a military time and returns a regular time formatted
	 * version. example - 1200 - 12:00PM . Helper method for getMeetingString().
	 * 
	 * @param time - military time passed in
	 * @return string value of time in regular form (ex 12:30PM)
	 */
	private String getTimeString(int time) {
		// set flag to tell if time is pm or am
		boolean pm = false;
		// find the hour value
		int hour = (time / DIVISOR) % MOD_VALUE;
		// if the hour is past 12 pm then set pm to true
		if (hour >= HALF_DAY) {
			pm = true;
			// configure hour if it is pm
			if (hour > 12) {
				hour = hour - HALF_DAY;
			}
		}
		// if hour = 0 that represents 12 AM
		if (hour == 0) {
			hour = HALF_DAY;
		}
		// find the minute value
		int min = time % MOD_VALUE;
		String minuteValue;
		// format minute string
		if (min == 0) {
			minuteValue = "00";
		} else if (min < MINUTE_CONVERT) {
			minuteValue = "0" + min;
		} else {
			minuteValue = "" + min;
		}
		// return full string whether its am or pm
		if (pm) {
			return hour + ":" + minuteValue + "PM";
		} else {
			return hour + ":" + minuteValue + "AM";
		}

	}

	/**
	 * checkConflict checks the current instance of activity to another activity to
	 * see if the activities have a conflicting time slots. if the two activities do
	 * have a conflicting time slot which includes an overlapping day and time value
	 * that is inclusive a ConflictException is thrown. The only exception to this
	 * rule are Arranged courses which are allowed to have an overlapping time slot
	 * with other arranged courses.
	 * 
	 * 
	 * @param possibleConflictingActivity - Activity that could have a possible time
	 *                                    conflict with the current instance.
	 * @throws ConflictException - if the current instance of Activity and the
	 *                           possibleConflictingActivity have an overlapping
	 *                           time on the same day. (Excludes Arranged Times)
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		// represents if two Activities have a similar meeting day
		boolean dayFlag = false;
		// Check if any of the days match for each of the Activities meeting days
		for (int i = 0; i < this.getMeetingDays().length(); i++) {
			// check each character with all other possible characters
			for (int j = 0; j < possibleConflictingActivity.getMeetingDays().length(); j++) {
				// check if the two characters match and are not an Arranged ('A') Activity
				// Arranged days can have overlapping times.
				if (this.getMeetingDays().charAt(i) == possibleConflictingActivity.getMeetingDays().charAt(j)
						&& this.getMeetingDays().charAt(i) != 'A') {
					// The Activities have a day in common other than an Arranged time.
					// set dayFlag to true to represent that the Activities share a common day.
					dayFlag = true;
					break;

				}

			}
		}
		// if the Activities have a common day then check the times at which they occur.
		if (dayFlag) {
			// check if the start time is between the time interval of the
			// possibleConflictingActivity or equal to a time value in the conflicting
			// activity.
			if (this.getStartTime() <= possibleConflictingActivity.getEndTime()
					&& this.getStartTime() >= possibleConflictingActivity.getStartTime()) {
				throw new ConflictException();
			}
			// check if the end time is between the time interval of the
			// possibleConflictingActivity or equal to a time value in the conflicting
			// activity.
			if (this.getEndTime() <= possibleConflictingActivity.getEndTime()
					&& this.getEndTime() >= possibleConflictingActivity.getStartTime()) {
				throw new ConflictException();
			}
			// check that the possibleConflictingActivity times are not inside of the
			// original activities time slots.
			if (this.getStartTime() < possibleConflictingActivity.getStartTime()
					&& this.getEndTime() > possibleConflictingActivity.getEndTime()) {
				throw new ConflictException();
			}
		}

	}

	/**
	 * returns the hash code value of an activity
	 * 
	 * @return Hash code value of an activity.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * tests equality between two Activities returns true if each activity is of the
	 * same type and all of the activity fields equal each other
	 * 
	 * @return whether the activities are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Activity))
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}