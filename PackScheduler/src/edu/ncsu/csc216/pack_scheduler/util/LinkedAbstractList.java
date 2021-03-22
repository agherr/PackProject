/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Class of a custom LinkedList that extends to the AbstractList class.
 * 
 * @author Daniel Katowitz
 * @author Aditya Konidena
 * @author Ashten Herr
 * @param <E> object
 *
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	/** The first node that points to the rest of the LinkedList */
	ListNode front;
	/** The size of the LinkedList */
	private int size;
	/** The capacity of the LinkedList */
	private int capacity;

	/**
	 * Node points to the list in the reverse direction
	 */
	ListNode back;

	/**
	 * Constructs the LinkedAbstractList
	 * 
	 * @param cap value of the capacity of the LinkedList
	 * @throws IllegalArgumentException - if capacity is negative or if capacity is less than size
	 */
	public LinkedAbstractList(int cap) {
		this.front = null;
		this.back = null;
		this.size = 0;
		if (cap >= 0) {
			this.capacity = cap;
		} else {
			throw new IllegalArgumentException("Capacity can not be negative");
		}
		if (capacity < size) {
			throw new IllegalArgumentException("Capacity can not be less than size");
		}
	}

	/**
	 * Sets the capacity of the linked list. Throws IAE if the capacity is less than
	 * current size or 0.
	 * 
	 * @param capacity capacity to set the list too
	 * @throws IllegalArgumentException if capacity is less than 0 or less than
	 *                                  size.
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size) {
			throw new IllegalArgumentException();
		} else {
			this.capacity = capacity;
		}
	}

	/**
	 * Gets the element at the specified
	 * 
	 * @param idx - index of element
	 * @return element at the specified index
	 * @throws IndexOutOfBoundsException if the index is not valid
	 */
	@Override
	public E get(int idx) {
		ListNode current = front;
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i < idx; i++) {
			current = current.next;
		}
		return current.data;
	}

	/**
	 * Method that adds a new E object at the index idx of the LinkedList
	 * 
	 * @param idx     index to add the new element
	 * @param element to add to the LinkedList
	 * @throws IllegalArgumentException   if the capacity has been reached, or if the
	 *                                   element exists in the list
	 * @throws NullPointerException      if element is null
	 * @throws IndexOutOfBoundsException if the index is out of range.
	 */
	@Override
	public void add(int idx, E element) {
		if (capacity == size) {
			throw new IllegalArgumentException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		if (front != null) {
			ListNode equals = front;
			while (equals.next != null) {
				if (element.equals(equals.data)) {
					throw new IllegalArgumentException();
				}
				equals = equals.next;
			}
		}
		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}

		ListNode newNode = new ListNode(element);
		ListNode current = front;

		if (idx == 0 && size == 0) {
			newNode.next = current;
			front = newNode;
			back = newNode;
		} else if (idx == 0) {
			newNode.next = current;
			front = newNode;
		} else if (idx == size) {

			back.next = newNode;
			back = newNode;
		} else {
			// Get to index to add element at.
			for (int i = 0; i < (idx - 1); i++) {
				current = current.next;
			}

			newNode.next = current.next;
			current.next = newNode;
		}

		size++;
	}

	/**
	 * Method to remove the element at a given index in the LinkedList
	 * 
	 * @param idx of the element to be removed
	 * @return E element removed from the LinkedList
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than
	 *                                   size
	 */
	@Override
	public E remove(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		E value = null;
		if (idx == 0) {
			value = front.data;
			front = front.next;

		} else {
			ListNode current = front;
			for (int i = 0; i < idx - 1; i++) {
				current = current.next;
			}
			value = current.next.data;
			current.next = current.next.next;
			back = current;
		}
		size--;
		return value;
	}

	/**
	 * Method to set a given element E at a given index
	 * 
	 * @param idx     of element to be set
	 * @param element to set at given index
	 * @return E object
	 * @throws NullPointerException      if the element is null
	 * @throws IllegalArgumentException  if the element exists already in the list
	 * @throws IndexOutOfBoundsException - if the index is not valid
	 */
	@Override
	public E set(int idx, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		ListNode equals = front;
		if (size > 0) {
			while (equals.next != null) {
				if (element.equals(equals.data)) {
					throw new IllegalArgumentException();
				}
				equals = equals.next;
			}
		}

		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		if (size == 0 && idx == 0) {
			throw new IndexOutOfBoundsException();
		}

		if (size > 0) {
			ListNode current = front;

			for (int i = 0; i < idx; i++) {
				current = current.next;
			}
			E temp = current.data;
			current.data = element;
			return temp;
		}

		return null;

	}

	/**
	 * Inner class of LinkedAbstractList
	 * 
	 * @author Daniel Katowitz
	 * @author Aditya Konidena
	 * @author Ashten Herr
	 */
	private class ListNode {

		/** Field for the unknown object */
		private E data;
		/** Field for the ListNode object */
		private ListNode next;

		/**
		 * Constructor of a ListNode object
		 * 
		 * @param data for ListNode
		 */
		public ListNode(E data) {
			this(data, null);
		}

		/**
		 * Constructor of a ListNode object
		 * 
		 * @param data for ListNode
		 * @param next points to the next ListNode
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}

	/**
	 * Gets the size of the list
	 * 
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Checks if the list is empty
	 * 
	 * @return true is size is 0, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

}
