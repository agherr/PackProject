package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Stack using array type to store data.
 * 
 * @author Ashten Herr
 *
 * @param <E> - type of stack being created.
 */
public class ArrayStack<E> implements Stack<E> {

	/** list represents the ArrayList to hold stack contents. */
	private ArrayList<E> list;

	/** capacity of the stack */
	private int capacity;

	/**
	 * Constructor for the array stack.
	 * 
	 * @param capacity - capacity of the stack
	 */
	public ArrayStack(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * push adds an element to the stack
	 * 
	 * @param element - element being added
	 * @throws IllegalArgumentException - if the capacity has been reached.
	 */
	@Override
	public void push(E element) {
		if (list.size() >= capacity) {
			throw new IllegalArgumentException();
		}
		list.add(list.size(), element);
	}

	/**
	 * Pops the top of the stack, removing the element.
	 * 
	 * @return element being removed from the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	@Override
	public E pop() {
		if (list.size() == 0) {
			throw new EmptyStackException();
		}
		E element = list.get(list.size() - 1);
		list.remove(list.size() - 1);
		return element;
	}

	/**
	 * returns whether the stack is empty.
	 * 
	 * @return whether the stack is empty
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * returns the size of the stack.
	 * 
	 * @return size of the stack
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * sets the capacity of the stack, capacity can not be less than 0 or less than size.
	 * 
	 * @param capacity - capacity to set the stack to
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
