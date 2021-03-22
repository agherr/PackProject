package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ListIterator;

import org.junit.Test;

/**
 * Tests the LinkedList class.
 * 
 * @author Ashten Herr
 *
 */
public class LinkedListTest {

	/**
	 * Tests the constructor for LinkedList
	 */
	@Test
	public void testConstructor() {
		// Create new linked list
		LinkedList<String> l = new LinkedList<String>();
		// test the size
		assertEquals(0, l.size());

	}

	/**
	 * Test add method
	 */
	@Test
	public void testAdd() {
		// Create new linked list
		LinkedList<String> l = new LinkedList<String>();
		// test the size
		assertEquals(0, l.size());

		// Add some elements'
		assertTrue(l.add("Apple"));

		assertEquals(l.get(0), "Apple");

		assertTrue(l.add("Bannana"));
		assertTrue(l.add("Grape"));

		// Check order/size
		assertEquals(3, l.size());
		assertEquals(l.get(0), "Apple");
		assertEquals(l.get(1), "Bannana");
		assertEquals(l.get(2), "Grape");

		l.add(0, "First");
		assertEquals(4, l.size());
		assertEquals(l.get(0), "First");
		assertEquals(l.get(1), "Apple");
		assertEquals(l.get(2), "Bannana");
		assertEquals(l.get(3), "Grape");

		l.add(1, "Second");
		assertEquals(5, l.size());
		assertEquals(l.get(0), "First");
		assertEquals(l.get(1), "Second");
		assertEquals(l.get(2), "Apple");
		assertEquals(l.get(3), "Bannana");
		assertEquals(l.get(4), "Grape");

		l.add(5, "Last");
		assertEquals(6, l.size());
		assertEquals(l.get(0), "First");
		assertEquals(l.get(1), "Second");
		assertEquals(l.get(2), "Apple");
		assertEquals(l.get(3), "Bannana");
		assertEquals(l.get(4), "Grape");
		assertEquals(l.get(5), "Last");

	}

	/**
	 * Tests the remove method
	 */
	@Test
	public void testRemove() {
		LinkedList<String> l = new LinkedList<String>();
		// test the size
		assertEquals(0, l.size());

		// Add some elements'
		assertTrue(l.add("Apple"));

		assertEquals(l.get(0), "Apple");

		assertTrue(l.add("Bannana"));
		assertTrue(l.add("Grape"));

		// Check order/size
		assertEquals(3, l.size());
		assertEquals(l.get(0), "Apple");
		assertEquals(l.get(1), "Bannana");
		assertEquals(l.get(2), "Grape");

		l.remove(1);

		assertEquals(2, l.size());
		assertEquals("Apple", l.get(0));
		assertEquals("Grape", l.get(1));

		l.remove(1);

		assertEquals(1, l.size());
		assertEquals(l.get(0), "Apple");

		l.remove(0);

		assertEquals(0, l.size());

	}

	/**
	 * Tests the set method
	 */
	@Test
	public void testSet() {
		LinkedList<String> l = new LinkedList<String>();
		// test the size
		assertEquals(0, l.size());

		// Add some elements'
		assertTrue(l.add("Apple"));

		assertEquals(l.get(0), "Apple");

		assertTrue(l.add("Bannana"));
		assertTrue(l.add("Grape"));

		// Check order/size
		assertEquals(3, l.size());
		assertEquals(l.get(0), "Apple");
		assertEquals(l.get(1), "Bannana");
		assertEquals(l.get(2), "Grape");

		l.set(0, "Hello");
		l.set(1, "Fruit");
		l.set(2, "Bruh");

		assertEquals(3, l.size());
		assertEquals(l.get(0), "Hello");
		assertEquals(l.get(1), "Fruit");
		assertEquals(l.get(2), "Bruh");

	}

	/**
	 * Tests the iterator and its methods
	 */
	@Test
	public void testIteratorMethods() {
		LinkedList<String> l = new LinkedList<String>();
		// test the size
		assertEquals(0, l.size());

		// Add some elements'
		assertTrue(l.add("Apple"));

		assertEquals(l.get(0), "Apple");

		assertTrue(l.add("Bannana"));
		assertTrue(l.add("Grape"));

		// Check order/size
		assertEquals(3, l.size());
		assertEquals(l.get(0), "Apple");
		assertEquals(l.get(1), "Bannana");
		assertEquals(l.get(2), "Grape");
		
		ListIterator<String> i = l.listIterator(0);
		assertEquals(0, i.nextIndex());
		assertEquals(-1, i.previousIndex());
		
		assertEquals("Apple", i.next());
		
		assertEquals("Apple", i.previous());
		
	}
}
