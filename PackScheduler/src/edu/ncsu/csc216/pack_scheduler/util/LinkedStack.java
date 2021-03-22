package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Stack using linked list version.
 * 
 * @author Ashten Herr
 *
 * @param <E> - type of stack
 */
public class LinkedStack<E> implements Stack<E> {

	/**
	 * capacity of the stack
	 */
	private int capacity;

	/**
	 * Stack as a linked list
	 */
	private LinkedAbstractList<E> linkedList;

	/**
	 * Constructor for LinkedStack
	 * 
	 * @param capacity - capacity of the LinkedStack
	 */
	public LinkedStack(int capacity) {
		linkedList = new LinkedAbstractList<E>(capacity);
		this.capacity = capacity;
	}

	/**
	 * push method adds to element to the top of the stack
	 * 
	 * @param element - element being added
	 * @throws IllegalArgumentException if the size of the list has reached
	 *                                  capacity.
	 */
	@Override
	public void push(E element) {
		if (linkedList.size() >= capacity) {
			throw new IllegalArgumentException();
		}
		linkedList.add(linkedList.size(), element);

	}

	/**
	 * pop removes the top element from the stack and returns that element.
	 * 
	 * @return element being removed from stack.
	 * @throws EmptyStackException - if the stack is empty.
	 */
	@Override
	public E pop() {
		if (linkedList.isEmpty()) {
			throw new EmptyStackException();
		}
		E element = linkedList.get(linkedList.size() - 1);
		linkedList.remove(linkedList.size() - 1);
		return element;
	}

	/**
	 * Returns true if the stack is empty.
	 * 
	 * @return whether the stack is empty
	 */
	@Override
	public boolean isEmpty() {
		return linkedList.isEmpty();
	}

	/**
	 * returns size of the stack
	 * 
	 * @return size of the stack.
	 */
	@Override
	public int size() {
		return linkedList.size();
	}

	/**
	 * Sets the capacity of the stack.
	 * 
	 * @param capacity - capacity to set the stack to.
	 */
	@Override
	public void setCapacity(int capacity) {
		linkedList.setCapacity(capacity);

	}

}
