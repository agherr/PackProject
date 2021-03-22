package edu.ncsu.csc216.pack_scheduler.user;

/**
 * 
 * The user class represents any user of the PackScheduler system. A user is
 * either a student or a registrar to use the system. A user is made up of a
 * first name, last name, id, email and hashed password.
 * 
 * @author Ashten
 *
 */
public abstract class User {

	/** firstName represents the students first name */
	private String firstName;
	/** lastName represents the students last name */
	private String lastName;
	/** id represents the students id */
	private String id;
	/** email represents the email */
	private String email;
	/** hashPW represents the hashed password */
	private String hashPW;

	/**
	 * Constructor for a user which requires a firstName, lastName, id, email, and
	 * hash password.
	 * 
	 * @param firstName - first name of the user
	 * @param lastName  - last name of the user
	 * @param id        - id of the user
	 * @param email     - email of the user
	 * @param hashPW    - users hashed password
	 */
	public User(String firstName, String lastName, String id, String email, String hashPW) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(hashPW);
	}

	/**
	 * Returns the first name of a student.
	 * 
	 * @return the students first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of a student. A first name is invalid if the name is null
	 * or an empty string.
	 * 
	 * @param firstName - the firstName to set
	 * @throws IllegalArgumentException - when the first name is null or an empty
	 *                                  string.
	 */
	public void setFirstName(String firstName) {
		// check that the name is not null or an empty string
		if (firstName == null || "".equals(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		} else {
			this.firstName = firstName;
		}

	}

	/**
	 * Returns the students last name.
	 * 
	 * @return Last name of the student.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the students last name, a last name is invalid if the students name is
	 * null or an empty string.
	 * 
	 * @param lastName - the lastName to set
	 * @throws IllegalArgumentException - if the last name is null or an empty
	 *                                  string.
	 */
	public void setLastName(String lastName) {
		// check for null or empty string
		if (lastName == null || "".equals(lastName)) {
			throw new IllegalArgumentException("Invalid last name");
		} else {
			this.lastName = lastName;
		}
	}

	/**
	 * Returns the id of a user
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of a user throws IAE if a id is null or an empty string
	 * 
	 * @param id the id to set
	 * @throws IllegalArgumentException if the id is null or an empty string.
	 */
	protected void setId(String id) {
		// check for null or empty string
		if (id == null || "".equals(id)) {
			throw new IllegalArgumentException("Invalid id");
		} else {
			this.id = id;
		}
	}

	/**
	 * Returns the email of a student.
	 * 
	 * @return email of the student.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email of a student. An IllegalArgumentException is thrown if the
	 * email is an empty string or null value. An IllegalArgumentException is also
	 * thrown if the email does not contain or has multiple '@' characters or '.'
	 * characters . If the email's '.' character comes before the '@' character an
	 * IAE is thrown.
	 * 
	 * @param email - the email to set
	 * @throws IllegalArgumentException - if the email is null or an empty string.
	 * @throws IllegalArgumentException - if the email does not contain a '.' or '@'
	 *                                  character or if the '.' comes before the '@'
	 *                                  character
	 */
	public void setEmail(String email) {
		// check if the email is null or an empty string
		if (email == null || "".equals(email)) {
			throw new IllegalArgumentException("Invalid email");
		} else {
			// look for the @ symbol and . and check for there locations
			int at = -1;
			int period = -1;
			// check each character
			for (int i = 0; i < email.length(); i++) {
				if (email.charAt(i) == '@') {
					at = i;
				} else if (email.charAt(i) == '.') {
					period = i;

				}
			}
			// check if the symbols came up and if the at came before the period throw IAE
			// if this is not the case
			if (at == -1 || period == -1 || period < at) {
				throw new IllegalArgumentException("Invalid email");

			} else {
				// set the email, all requirements are met
				this.email = email;
			}
		}
	}

	/**
	 * Returns the hashed value of the students password.
	 * 
	 * @return the hashPW
	 */
	public String getPassword() {
		return hashPW;
	}

	/**
	 * Sets the hash password of the student, a hashed password can not be null or
	 * an empty string. Throws IAE if this is the case.
	 * 
	 * @param hashPW - the hash password of the student
	 * @throws IllegalArgumentException - when the hashed password is null or an
	 *                                  empty string.
	 */
	public void setPassword(String hashPW) {
		if (hashPW == null || "".equals(hashPW)) {
			throw new IllegalArgumentException("Invalid password");
		} else {
			this.hashPW = hashPW;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((hashPW == null) ? 0 : hashPW.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (hashPW == null) {
			if (other.hashPW != null)
				return false;
		} else if (!hashPW.equals(other.hashPW))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}
}