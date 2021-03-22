/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * CourseNameValidator is a FSM to verify if a course name is valid.
 * 
 * @author dtkatowi, agherr, akonide
 *
 */
public class CourseNameValidator {

	/**
	 * Current State of CourseNameValidator
	 */
	private State state;

	/**
	 * Represents the initial state of a Course name
	 */
	private final State stateInitial = new StateInitial();

	/**
	 * Represents the first letter state of a Course name
	 */
	private final State stateL = new StateL();

	/**
	 * Represents the second letter state of a Course name
	 */
	private final State stateLL = new StateLL();

	/**
	 * Represents the third letter state of a Course name
	 */
	private final State stateLLL = new StateLLL();

	/**
	 * Represents the fourth letter state of a Course name
	 */
	private final State stateLLLL = new StateLLLL();

	/**
	 * Represents the first digit state of a course name.
	 */
	private final State stateD = new StateD();

	/**
	 * Represents the second digit state of a course name.
	 */
	private final State stateDD = new StateDD();

	/**
	 * Represents the third digit state of a course name.
	 */
	private final State stateDDD = new StateDDD();

	/**
	 * Represents the suffix state of a course name which is optional for a course.
	 */
	private final State stateSuffix = new StateSuffix();

	/**
	 * isValid implements the FSM state pattern to test a courses requirements. A
	 * course is made of 1-4 letters followed by exactly 3 numbers and an optional
	 * suffix. isValid returns whether a course is valid or not based on those
	 * requirements.
	 * 
	 * 
	 * @param courseName - course name being tested for validity
	 * @return whether the course name is valid.
	 * @throws InvalidTransitionException - if the specified transition is invalid.
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {
		// Start off in initial state
		state = stateInitial;

		// Loop for every character and depending on the character type update the state
		for (int i = 0; i < courseName.length(); i++) {
			char c = courseName.charAt(i);
			if (Character.isLetter(c)) {
				state.onLetter();
			} else if (Character.isDigit(c)) {
				state.onDigit();
			} else {
				state.onOther();
			}

		}

		// If the ending state is at 3 digits or a suffix return true because they are
		// valid end states
		if (state.equals(stateDDD) || state.equals(stateSuffix)) {
			return true;
		}
		throw new InvalidTransitionException();
	}

	/**
	 * State class to implement isValid method. Course name has a state for every
	 * possible character a course can have. A course state has three options a
	 * letter digit or other character. An other character will always throw an
	 * exception while letter and digits may throw an exception based on the current
	 * state.
	 * 
	 * 
	 * @author dtkatowi, agherr, akonide
	 *
	 */
	public abstract class State {

		/**
		 * Abstract method represents behavior if the current character being processed
		 * is a letter.
		 * 
		 * @throws InvalidTransitionException if the current state does not allow for a
		 *                                    letter character to be processed.
		 */
		public abstract void onLetter() throws InvalidTransitionException;

		/**
		 * Abstract method represents behavior if the current character being processed
		 * is a digit.
		 * 
		 * @throws InvalidTransitionException - if current state does not allow for a
		 *                                    digit character to be processed.
		 * 
		 */
		public abstract void onDigit() throws InvalidTransitionException;

		/**
		 * Method which represents processing a character other than a digit or letter.
		 * Exception is thrown as a course can only be made of letters and digits.
		 * 
		 * @throws InvalidTransitionException - If the character being processed is not
		 *                                    a letter or digit.
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}

	}

	/**
	 * Initial state (start state) allows only for a character to be a letter
	 * 
	 * @author dtkatowi, agherr, akonide
	 */
	public class StateInitial extends State {

		/**
		 * Moves state to the first letter state.
		 */
		@Override
		public void onLetter() {
			state = stateL;

		}

		/**
		 * Invalid transition throws exception.
		 * 
		 * @throws InvalidTransitionException - transition is invalid for current state.
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");

		}
	}

	/**
	 * Represents the first letter state for a course name. Both letters and digits
	 * are valid transitions.
	 * 
	 * @author dtkatowi, agherr, akonide
	 */
	public class StateL extends State {

		/**
		 * Changes state to the second letter state.
		 */
		@Override
		public void onLetter() {
			state = stateLL;

		}

		/**
		 * Changes state to the first digit state.
		 */
		@Override
		public void onDigit() {
			state = stateD;

		}
	}

	/**
	 * Represents the current state of the second letter of a course name.
	 * 
	 * @author dtkatowi, agherr, akonide
	 *
	 */
	public class StateLL extends State {

		/**
		 * Letter is a valid transition for this state. Updates to third letter state.
		 */
		@Override
		public void onLetter() {
			state = stateLLL;

		}

		/**
		 * Digit is a valid transition for this state updates to first digit state.
		 */
		@Override
		public void onDigit() {
			state = stateD;
		}

	}

	/**
	 * State of the 3rd letter in a course name letter and digits are valid
	 * transitions.
	 * 
	 * @author dtkatowi, agherr, akonide
	 *
	 */
	public class StateLLL extends State {

		/**
		 * Transition to the 4th letter state stateLLLL
		 */
		@Override
		public void onLetter() {
			state = stateLLLL;

		}

		/**
		 * Transition to the first digit state stateD
		 */
		@Override
		public void onDigit() {
			state = stateD;

		}
	}

	/**
	 * State of the fourth letter of a course name. onDigit() is the only valid
	 * transition.
	 * 
	 * @author dtkatowi, agherr, akonide
	 *
	 */
	public class StateLLLL extends State {

		/**
		 * Invalid transition throw exception.
		 * 
		 * @throws InvalidTransitionException when a 5th letter is found
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");

		}

		/**
		 * Valid transition to first digit state stateD
		 */
		@Override
		public void onDigit() {
			state = stateD;

		}
	}

	/**
	 * State of the first digit.
	 * 
	 * @author dtkatowi, agherr, akonide
	 *
	 */
	public class StateD extends State {

		/**
		 * Invalid Transition throw exception.
		 * 
		 * @throws InvalidTransitionException if a second digit is not found
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must have 3 digits.");

		}

		/**
		 * Valid transition to the second digit state stateDD.
		 */
		@Override
		public void onDigit() {
			state = stateDD;

		}

	}

	/**
	 * Represents the second digit state of a course name, onDigit is the only valid
	 * transition.
	 * 
	 * @author dtkatowi, agherr, akonide
	 *
	 */
	public class StateDD extends State {

		/**
		 * Invalid Transition throw exception.
		 * 
		 * @throws InvalidTransitionException if a third digit is not found
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must have 3 digits.");

		}

		/**
		 * Valid transition to the third digit state stateDDD.
		 */
		@Override
		public void onDigit() {
			state = stateDDD;

		}

	}

	/**
	 * State for the third digit of a course. onLetter is the only valid transition.
	 * StateDDD is a valid end state for this FSM as well.
	 * 
	 * @author dtkatowi, agherr, akonide
	 *
	 */
	public class StateDDD extends State {

		/**
		 * Valid transition to the suffix state stateSuffix.
		 */
		@Override
		public void onLetter() {
			state = stateSuffix;

		}

		/**
		 * Invalid Transition throw exception.
		 * 
		 * @throws InvalidTransitionException if a fourth digit is not found
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have 3 digits.");

		}

	}

	/**
	 * Suffix state. This is a end state with no valid transitions. Represents the
	 * optional letter character at the end of a course name.
	 * 
	 * @author dtkatowi, agherr, akonide
	 *
	 */
	public class StateSuffix extends State {

		/**
		 * Invalid Transition throw exception.
		 * 
		 * @throws InvalidTransitionException if more than one suffix is found
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");

		}

		/**
		 * Invalid Transition throw exception.
		 * 
		 * @throws InvalidTransitionException if a digit is found after suffix
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");

		}

	}

}
