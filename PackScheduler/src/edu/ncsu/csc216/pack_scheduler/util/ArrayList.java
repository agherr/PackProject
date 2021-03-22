/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Custom array list class for packScheduler.
 * 
 * @author Ashten
 * @param <E> - type of the list.
 *
 */
public class ArrayList<E> extends AbstractList<E> {

	/**
	 * Initial size of the list.
	 */
	private static final int INIT_SIZE = 10;

	/**
	 * List of type e to store elements.
	 */
	private E[] list;

	/**
	 * Current size of array list.
	 */
	private int size;

	/**
	 * Constructor for ArrayList
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[]) new Object[INIT_SIZE];
		this.size = 0;
	}

	@Override
	public E get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}

	/**
	 * Returns the size of the array list.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * add() method adds a variable at the specified index, throws null exception if
	 * the element is null, throws IAE if element is a duplicate.
	 * 
	 * @param index   - index to add element at
	 * @param element - element to add at index
	 * 
	 * @throws IllegalArgumentException if element is a duplicate in the arrayList
	 * @throws NullPointerException     if element is null
	 */
	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}

		if (element == null) {
			throw new NullPointerException();
		}
		if (!ensureCapacity()) {
			growArray();
		}
		for (int i = 0; i < size; i++) {
			if (element.equals(list[i])) {
				throw new IllegalArgumentException("Duplicate element.");
			}
		}

		size++;
		for (int i = list.length - 2; i >= index; i--) {
			list[i + 1] = list[i];
		}
		list[index] = element;
	}

	/**
	 * Removes an element from the ArrayList based on index provided
	 * 
	 * @param index index of element to be removed
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than
	 *                                   list size
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		E element = list[index];
		for (int i = index; i <= size - 1; i++) {
			list[i] = list[i + 1];
		}
		list[size - 1] = null;
		size--;
		return element;
	}

	/**
	 * Ensures the list has capacity to add element, if the list capacity is full
	 * returns false.
	 * 
	 * @return whether the list has capacity or not.
	 */
	private boolean ensureCapacity() {
		return !(size == list.length);
	}

	private void growArray() {
		@SuppressWarnings("unchecked")
		E[] listTemp = (E[]) new Object[list.length * 2];
		for (int i = 0; i < list.length; i++) {
			listTemp[i] = list[i];
		}
		list = listTemp;

	}

	/**
	 * To set the given element at the given array;
	 */
	@Override
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}

		E current = null;
		for (int j = 0; j < size; j++) {
			if (j == index) {
				current = list[j];
				list[j] = element;
			}
		}
		return current;
	}

}
