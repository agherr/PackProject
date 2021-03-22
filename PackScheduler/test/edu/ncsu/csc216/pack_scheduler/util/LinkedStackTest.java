package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Test;

/**
 * Tests the ArrayStack class
 * @author Ashten Herr
 *
 */
public class LinkedStackTest {

	/**
	 * Tests push and pop methods.
	 */
	@Test
	public void testPushAndPop() {
		LinkedStack<String> s = new LinkedStack<String>(5);

		s.push("1");
		assertEquals(1, s.size());

		s.push("2");
		assertEquals(2, s.size());

		s.push("3");
		assertEquals(3, s.size());

		s.push("4");
		assertEquals(4, s.size());

		s.push("5");
		assertEquals(5, s.size());

		try {
			s.push("6");
		} catch (IllegalArgumentException e) {
			assertEquals(5, s.size());
		}

		assertEquals("5", s.pop());
		assertEquals("4", s.pop());
		assertEquals("3", s.pop());
		assertEquals("2", s.pop());
		assertEquals("1", s.pop());

		try {
			s.pop();
		} catch (EmptyStackException e) {
			assertEquals(0, s.size());
		}

	}

	/**
	 * Tests isEmpty()
	 */
	@Test
	public void testIsEmpty() {
		LinkedStack<String> s = new LinkedStack<String>(5);
		assertEquals(0, s.size());

		assertTrue(s.isEmpty());

		s.push("1");

		assertFalse(s.isEmpty());
	}

	/**
	 * Tests size method.
	 */
	@Test
	public void testSize() {
		LinkedStack<String> s = new LinkedStack<String>(5);
		assertEquals(0, s.size());

		s.push("1");
		assertEquals(1, s.size());

		s.push("2");
		assertEquals(2, s.size());

		s.push("3");
		assertEquals(3, s.size());

		s.push("4");
		assertEquals(4, s.size());

		s.push("5");
		assertEquals(5, s.size());
	}

	/**
	 * Tests the set capacity method
	 */
	@Test
	public void testSetCapacity() {
		LinkedStack<String> s = new LinkedStack<String>(5);

		s.setCapacity(4);

		s.push("1");
		assertEquals(1, s.size());

		s.push("2");
		assertEquals(2, s.size());

		s.push("3");
		assertEquals(3, s.size());

		s.push("4");
		assertEquals(4, s.size());

		try {
			s.push("6");
		} catch (IllegalArgumentException e) {
			assertEquals(4, s.size());
		}
		
		try {
			s.setCapacity(1);
		} catch (IllegalArgumentException e) {
			assertEquals(4, s.size());
		}
	}

}
