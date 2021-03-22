/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Queue based on LinkedList
 * 
 * @author adityakonidena
 *
 * @param <E> - type of object
 */
public class LinkedQueue<E> implements Queue<E> {

	/**
	 * Capacity of the LinkedQueue
	 */
	private int capacity;

	/**
	 * LinkedAbstractList stores the contents of the Queue
	 */
	private LinkedAbstractList<E> list;

	/**
	 * Constructor for capacity
	 * 
	 * @param capacity capacity of queue
	 */
	public LinkedQueue(int capacity) {

		list = new LinkedAbstractList<E>(capacity);
		this.capacity = capacity;

	}

	/**
	 * enqueue adds a element to the queue
	 * 
	 * @param element - element being added
	 * @throws IllegalArgumentException if the queue has reached capacity
	 */
	@Override
	public void enqueue(E element) {
		if (list.size() >= capacity) {
			throw new IllegalArgumentException();
		}

		list.add(list.size(), element);

	}

	/**
	 * dequeue removes an element from the queue and returns the removed element.
	 * 
	 * @return element being removed
	 * @throws NoSuchElementException - if the queue is empty.
	 */
	@Override
	public E dequeue() {
		if (list.isEmpty()) {
			throw new NoSuchElementException();
		}

		E element = list.get(0);
		list.remove(0);
		return element;
	}

	/**
	 * returns whether the queue is empty
	 * 
	 * @return whether the queue is empty
	 */
	@Override
	public boolean isEmpty() {

		return list.isEmpty();
	}

	/**
	 * returns the size
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
	 * @param capacity - capacity to set the queue to
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);

	}

}
