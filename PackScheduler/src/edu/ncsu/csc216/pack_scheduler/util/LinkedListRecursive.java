package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Class for a linked recursive list using recursion for its methods.
 * 
 * @author Ashten Herr, Aditya Konidena
 *
 * @param <E> - Type of data being stored.
 */
public class LinkedListRecursive<E> {

	/** Front of the linked list */
	private ListNode front;

	/** Size of the linked list */
	private int size;

	/**
	 * Constructs the linked list, sets front to null and size to 0,
	 */
	public LinkedListRecursive() {
		front = null;
		size = 0;
	}

	/**
	 * Returns whether the linked list is empty (size is equal to 0).
	 * 
	 * @return whether the list is empty.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the size of the linked list.
	 * 
	 * @return size of the linked list.
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds an element to the end of the list.
	 * 
	 * @param e - element being added
	 * @return - whether the element was successfully added
	 * @throws NullPointerException     if added element is null
	 * @throws IllegalArgumentException if the element already exists in the list.
	 */
	public boolean add(E e) {
		if (e == null) {
			throw new NullPointerException();
		}

		if (contains(e)) {
			throw new IllegalArgumentException();
		}

		if (size == 0) {
			front = new ListNode(e, null);
			size++;
			return true;
		} else {
			return front.add(e);
		}
	}

	/**
	 * Adds the provided element to the given index.
	 * 
	 * @param index the index to add element at
	 * @param e     element to be added
	 * @return true if element added, false in all other cases
	 * @throws IndexOutOfBoundsException if the index is less than 0 or greater than
	 *                                   size
	 * @throws NullPointerException      if e is null
	 * @throws IllegalArgumentException  if the element already exists in the list.
	 */
	public boolean add(int index, E e) {
		ListNode current = front;
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (e == null) {
			throw new NullPointerException();
		}

		if (contains(e)) {
			throw new IllegalArgumentException();
		}
		if (index == 0) {
			ListNode newNode = new ListNode(e, current);
			front = newNode;
			size++;
			return true;
		} else if (index > 0 && index < size) {
			return front.add(index - 1, e);
		} else if (index == size) {
			return front.add(e);
		}
		return false;
	}

	/**
	 * Gets the element at the given index
	 * 
	 * @param index index of requested element
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than
	 *                                   size - 1
	 * @return the element at the given index
	 */
	public E get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		} else if (index == 0) {
			return front.data;
		}
		return front.get(index);
	}

	/**
	 * Removes the element at the given index
	 * 
	 * @param index index at which element should be removed
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than
	 *                                   size - 1
	 * @return The element that was removed
	 */
	public E remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		E value = null;
		if (index == 0) {
			value = front.data;
			front = front.next;
			size--;
			return value;
		}
		return front.remove(index - 1);

	}

	/**
	 * Removes element based on the element provided as parameter
	 * 
	 * @param e element to be removed
	 * @return true if element is removed, false in all other cases
	 */
	public boolean remove(E e) {
		if (e == null) {
			return false;
		} else if (size == 0) {
			return false;
		}

		if (e.equals(front.data)) {
			front = front.next;
			size--;
			return true;
		}
		return front.remove(e);

	}

	/**
	 * Sets the provided data as the data for the element at the given index
	 * 
	 * @param index index at which data should be set
	 * @param e     element to set
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than
	 *                                   size - 1
	 * @throws NullPointerException      if e is null
	 * @throws IllegalArgumentException  if e already exists in the list
	 * @return previous data from index
	 */
	public E set(int index, E e) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		} else if (e == null) {
			throw new NullPointerException();
		} else if (this.contains(e)) {
			throw new IllegalArgumentException();
		}

		return front.set(index, e);
	}

	/**
	 * Returns whether the element exists in the list or not.
	 * 
	 * @param e - element being checked
	 * @return whether the list contains the element.
	 */
	public boolean contains(E e) {
		if (size == 0) {
			return false;
		}
		return front.contains(e);
	}

	private class ListNode {
		/** Field for the unknown object */
		private E data;
		/** Field for the ListNode object */
		private ListNode next;

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

		/**
		 * Adds element at a given index and returns whether the element was
		 * successfully added.
		 * 
		 * @param index - index of element
		 * @param e     - element being added
		 * @return whether it was added successfully
		 */
		public boolean add(int index, E e) {
			if (index == 0) {
				ListNode temp = this.next;
				this.next = new ListNode(e, temp);
				size++;
				return true;
			} else {
				return add(index - 1, e);
			}
		}

		/**
		 * Adds an element to the end of the list. Returns whether the element was
		 * successfully added.
		 * 
		 * @param e - element being added
		 * @return - whether the element was successfully added.
		 */
		public boolean add(E e) {
			if (next == null) {
				next = new ListNode(e, null);
				size++;
				return true;
			} else {
				return next.add(e);
			}
		}

		/**
		 * Removes element at given index. Returns the data of removed list node
		 * 
		 * @param index - index being removed
		 * @return data of the index being removed
		 */
		public E remove(int index) {
			if (index == 0) {
				E value = this.next.data;
				this.next = this.next.next;
				size--;
				return value;
			} else {
				return next.remove(index - 1);
			}
		}

		/**
		 * Removes a specific element based on the data. Returns true if the element is successfully removed.
		 * 
		 * @param e - element being removed
		 * @return whether the element is successfully removed
		 */
		public boolean remove(E e) {
			if (this.next.data.equals(e)) {
				this.next = this.next.next;
				size--;
				return true;
			} else {
				return this.next.remove(e);
			}
		}

		/**
		 * Returns element at given index
		 * 
		 * @param index - index of element
		 * @return the data of list node at the index
		 */
		public E get(int index) {
			if (index == 0) {
				return this.data;
			} else {
				return this.next.get(index - 1);
			}
		}

		/**
		 * Sets element at given index to e.
		 * 
		 * @param index - index being set
		 * @param e - data of element
		 * @return the previous data the index held
		 */
		public E set(int index, E e) {
			if (index == 0) {
				E removed = this.data;
				this.data = e;
				return removed;
			} else {
				return this.next.set(index - 1, e);
			}
		}

		/**
		 * Finds if the linked node contains the element specified, returns false if the
		 * next element is the end of the list and true if the element is the current
		 * data. If the next node is not null it moves to the next list node to search.
		 * 
		 * @param e - element being searched for
		 * @return - whether the element exists at the current node.
		 */
		public boolean contains(E e) {
			if (data.equals(e)) {
				return true;
			} else if (next == null) {
				return false;
			}
			return next.contains(e);
		}
	}
}
