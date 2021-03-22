package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Stack interface.
 * 
 * @author Ashten, Aditya
 * @param <E> - type of the stack
 *
 */
public interface Stack<E> {

	/**
	 * Method to add element to the stack
	 * 
	 * @param element element to be added to stack
	 */
	void push(E element);

	/**
	 * Method to remove first element from stack
	 * 
	 * @return object that has been removed
	 */
	E pop();

	/**
	 * Checks if stack is empty
	 * 
	 * @return true if empty, false if not
	 */
	boolean isEmpty();

	/**
	 * Gets the size of the stack
	 * 
	 * @return the size of the stack
	 */
	int size();

	/**
	 * Sets the initial capacity of the stack
	 * 
	 * @param capacity capactiry to be set to
	 */
	void setCapacity(int capacity);

}
