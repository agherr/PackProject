/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface for queue
 * 
 * @author adityakonidena
 *
 * @param <E> - type of queue
 */
public interface Queue<E> {
	/**
	 * Adds the provided element to the back of the queue
	 * 
	 * @param element element to be added
	 */
	void enqueue(E element);

	/**
	 * Removes the element at the back of the queue
	 * 
	 * @return element that is being removed
	 */
	E dequeue();

	/**
	 * CHecks if queue is empty
	 * 
	 * @return true if it is, false if not
	 */
	boolean isEmpty();

	/**
	 * Gets the size of the queue
	 * 
	 * @return the size
	 */
	int size();

	/**
	 * Sets the capacity to the provided capacity
	 * 
	 * @param capacity capacity to be set to
	 */
	void setCapacity(int capacity);

}
