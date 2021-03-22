/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * A queue using array type to store data
 * 
 * @author adityakonidena
 *
 * @param <E> - type of object
 */
public class ArrayQueue<E> implements Queue<E> {

	/**
	 * ArrayList to hold the queue elements
	 */
	private ArrayList<E> list;

	/**
	 * Capacity of queue
	 */
	private int capacity;

	/**
	 * Constructs the Queue
	 * 
	 * @param capacity the capacity to set the queue to
	 */
	public ArrayQueue(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * enqueue adds an element to the queue
	 * 
	 * @param element - element being added to the queue
	 * @throws IllegalArgumentException if the capacity has been reached.
	 */
	@Override
	public void enqueue(E element) {
		if (list.size() >= capacity) {
			throw new IllegalArgumentException();
		}
		list.add(list.size(), element);

	}

	/**
	 * dequeue removes the first item in the queue and returns that element
	 * 
	 * @return element being removed
	 * @throws NoSuchElementException if the queue is empty
	 */
	@Override
	public E dequeue() {
		if (list.size() == 0) {
			throw new NoSuchElementException();
		}
		E element = list.get(0);
		list.remove(0);
		return element;
	}

	/**
	 * Returns whether the queue is empty
	 * 
	 * @return whether the queue is empty
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();

	}

	/**
	 * Returns the size of the queue
	 * 
	 * @return size of queue
	 */
	@Override
	public int size() {
		return list.size();

	}

	/**
	 * sets the capacity of the queue
	 * 
	 * @param capacity - capacity to set to
	 * @throws IllegalArgumentException if capacity is less than 0 or size.
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;

	}

}
