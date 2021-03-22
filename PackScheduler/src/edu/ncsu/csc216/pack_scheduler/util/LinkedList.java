package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * LinkedList class represents a doubly connected LinkedList to store objects.
 * LinkedList can store any object data type and is linked doubly (previous and
 * next)
 * 
 * @author Ashten Herr
 * @param <E> = type of object in the LinkedList
 *
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	/** Size of the list */
	private int size;

	/** Back most list node */
	private ListNode back;

	/** Front most list node, start of the list. */
	private ListNode front;

	/**
	 * Constructor of the LinkedList, initiates the front and back nodes to point
	 * eachother and size to 0.
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.previous = front;
		size = 0;

	}

	/**
	 * Returns a new LinkedListIterator at the specified index.
	 * 
	 * @return ListIterator for the Linked list.
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new LinkedListIterator(index);
	}

	/**
	 * Returns the size.
	 * 
	 * @return Size of the Linked list.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds an element at the specified index. If the list already contains the
	 * element an IAE is thrown.
	 * 
	 * @param index   - index of where to add element at.
	 * @param element - element being added
	 * @throws IllegalArgumentException if the element is a duplicate.
	 */
	@Override
	public void add(int index, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		super.add(index, element);

	}

	/**
	 * Sets the element at a specified index to the new element. Throws IAE if the
	 * element is a duplicate.
	 * 
	 * @param index   - index being set to.
	 * @param element - element being set to
	 * @return the element that was just set (before being set)
	 * @throws IllegalArgumentException if the element is a duplicate.
	 */
	@Override
	public E set(int index, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		return super.set(index, element);
	}

	/**
	 * Represents a double linked list node. Contains data, previous node and the
	 * next node.
	 * 
	 * @author Ashten Herr
	 *
	 */
	private class ListNode {
		/** data being held by the ListNode */
		public E data;

		/** next represents the next ListNode */
		public ListNode next;

		/** previous ListNode in list */
		public ListNode previous;

		/**
		 * ListNode constructor with no pointer references, sets next and prev to null
		 * with a call to the other constructor.
		 * 
		 * @param data - data of the ListNode
		 */
		public ListNode(E data) {
			this(data, null, null);
		}

		/**
		 * Constructor for a List Node, sets the data, previous and next list node to
		 * specified parameters.
		 * 
		 * @param data - data of the list node.
		 * @param prev - previous list node the node points to.
		 * @param next - next list node the node points to.
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.previous = prev;
			this.next = next;
		}

	}

	/**
	 * LinkedListIterator helps traverse the Linked list to add elements. Iterator
	 * goes back and forth and can add, remove, and set elements. List is doubly
	 * linked.
	 * 
	 * 
	 * @author Ashten Herr
	 *
	 */
	private class LinkedListIterator implements ListIterator<E> {
		/** previous list node from current iterator position */
		public ListNode previous;
		/** next list node from current iterator position */
		public ListNode next;
		/** index of previous listNode */
		public int previousIndex;
		/** index of next listNode */
		public int nextIndex;
		/** Last retrieved listNode from list */
		public ListNode lastRetrieved;

		/**
		 * Constructor for LinkedListIterator if the index is out of bounds a Index out
		 * of bounds exception is thrown. sets the previous/next fields so a call to
		 * next will return ListNode next at the specified index.
		 * 
		 * @param index - index starting position of the iterator
		 * @throws IndexOutOfBoundsException if the index is out of bounds.
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}

			if (size == 0) {
				next = back;
				previous = front;
			} else {
				next = front;
				previous = front;

				for (int i = 0; i <= index; i++) {
					next = next.next;
				}
				previous = next.previous;
			}

			nextIndex = index;

			previousIndex = index - 1;

			lastRetrieved = null;

		}

		/**
		 * Returns whether the Iterators next element is not null (not an end piece)
		 * 
		 * @return whether the iterator has a next element
		 */
		@Override
		public boolean hasNext() {
			return next.data != null;
		}

		/**
		 * Moves the iterator to the right with a next call, returning the element of
		 * next.
		 * 
		 * @return element being iterated over (in front of the iterator cursor)
		 * @throws NoSuchElementException if next returns null (end of linked list)
		 */
		@Override
		public E next() {
			if (hasNext()) {
				lastRetrieved = next;
				E rtn = next.data;
				next = next.next;
				previous = previous.next;
				previousIndex++;
				nextIndex++;
				return rtn;
			}

			throw new NoSuchElementException();
		}

		/**
		 * Returns whether the iterator has a valid on the previous element. (Behind the
		 * cursor)
		 */
		@Override
		public boolean hasPrevious() {
			return previous.data != null;
		}

		/**
		 * Iterates over the previous element and returns the previous ListNode, throws
		 * NSEE if the list is at the front.
		 * 
		 * @return data of the previous list node
		 * @throws NoSuchElementException if the list is at the front (no previous
		 *                                element exists)
		 */
		@Override
		public E previous() {
			if (hasPrevious()) {
				lastRetrieved = previous;
				E rtn = previous.data;
				next = next.previous;
				previous = previous.previous;
				previousIndex--;
				nextIndex--;
				return rtn;
			}

			throw new NoSuchElementException();
		}

		/**
		 * Returns the next index value.
		 * 
		 * @return next index of the iterator
		 */
		@Override
		public int nextIndex() {
			return nextIndex;
		}

		/**
		 * Returns the previous index
		 * 
		 * @return the previous index of the iterator
		 */
		@Override
		public int previousIndex() {
			return previousIndex;
		}

		/**
		 * Removes the last retrieved index, if it is null an IllegalStateException is
		 * thrown. Sets lastRetrieved to null after removing.
		 * 
		 * @throws IllegalStateException if lastRetrieved is null
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}

			ListNode previousNode = lastRetrieved.previous;
			ListNode nextNode = lastRetrieved.next;

			previousNode.next = nextNode;
			nextNode.previous = previousNode;

			lastRetrieved = null;
			size--;

		}

		/**
		 * Sets the last retrieved element to the element throws IllegalStateException
		 * if lastRetrieved is null.
		 * 
		 * @param e - element replacing lastRetrieved data
		 * @throws IllegalStateException if lastRetrieved is null.
		 * @throws NullPointerException  if the element is null.
		 */
		@Override
		public void set(E e) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}

			if (e == null) {
				throw new NullPointerException();
			}

			lastRetrieved.data = e;

		}

		/**
		 * Adds an element at the index before a call to next would return. (between
		 * next and previous). If the data is null a NPE is thrown, increments size and
		 * sets lastRetrieved to null.
		 * 
		 * @param e - element being added
		 * @throws NullPointerException if e is null.
		 */
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}

			ListNode newNode = new ListNode(e);

			previous.next = newNode;
			next.previous = newNode;

			newNode.next = next;
			newNode.previous = previous;

			previous = newNode;

			lastRetrieved = null;
			size++;

		}
	}

}
