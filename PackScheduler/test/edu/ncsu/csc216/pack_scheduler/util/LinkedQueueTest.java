
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * Tests for LinkedQueue
 * 
 * @author adityakonidena
 *
 */
public class LinkedQueueTest {

	/**
	 * Test method for Enqueue and Dequeue
	 * {@link edu.ncsu.csc216.pack_scheduler.util.LinkedQueue#enqueue(java.lang.Object)}.
	 */
	@Test
	public void testEnqueueAndDequeue() {
		LinkedQueue<String> queue = new LinkedQueue<String>(6);
		queue.enqueue("a");
		assertEquals(1, queue.size());

		queue.enqueue("1");
		assertEquals(2, queue.size());

		queue.enqueue("hello");
		assertEquals(3, queue.size());

		queue.enqueue("&");
		assertEquals(4, queue.size());

		queue.enqueue("cat");
		assertEquals(5, queue.size());

		queue.enqueue("dog");
		assertEquals(6, queue.size());

		try {
			queue.enqueue("invalid");
			fail();
		} catch (IllegalArgumentException e) {

			assertEquals(6, queue.size());
		}

		String element = queue.dequeue();
		assertEquals(5, queue.size());
		assertEquals("a", element);

		element = queue.dequeue();
		assertEquals(4, queue.size());
		assertEquals("1", element);

		element = queue.dequeue();
		assertEquals(3, queue.size());
		assertEquals("hello", element);

		element = queue.dequeue();
		assertEquals(2, queue.size());
		assertEquals("&", element);
		element = queue.dequeue();

		assertEquals(1, queue.size());
		assertEquals("cat", element);

		element = queue.dequeue();
		assertEquals(0, queue.size());
		assertEquals("dog", element);

		try {
			element = queue.dequeue();
			fail();
		} catch (NoSuchElementException e) {
			assertEquals(0, queue.size());
		}

	}

	/**
	 * Test method for isEmpty()
	 * {@link edu.ncsu.csc216.pack_scheduler.util.LinkedQueue#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		LinkedQueue<String> queue = new LinkedQueue<String>(6);
		assertEquals(0, queue.size());
		assertTrue(queue.isEmpty());

		queue.enqueue("testing");
		assertFalse(queue.isEmpty());
	}

	/**
	 * Test method for size
	 * {@link edu.ncsu.csc216.pack_scheduler.util.LinkedQueue#size()}.
	 */
	@Test
	public void testSize() {
		LinkedQueue<String> queue = new LinkedQueue<String>(6);

		queue.enqueue("a");
		assertEquals(1, queue.size());

		queue.enqueue("1");
		assertEquals(2, queue.size());

		queue.enqueue("hello");
		assertEquals(3, queue.size());

		queue.enqueue("&");
		assertEquals(4, queue.size());

		queue.enqueue("cat");
		assertEquals(5, queue.size());

		queue.enqueue("dog");
		assertEquals(6, queue.size());
	}

	/**
	 * Test method for setCapacity()
	 * {@link edu.ncsu.csc216.pack_scheduler.util.LinkedQueue#setCapacity(int)}.
	 */
	@Test
	public void testSetCapacity() {
		LinkedQueue<String> queue = new LinkedQueue<String>(5);

		queue.setCapacity(4);

		queue.enqueue("1");
		assertEquals(1, queue.size());

		queue.enqueue("2");
		assertEquals(2, queue.size());

		queue.enqueue("3");
		assertEquals(3, queue.size());

		queue.enqueue("4");
		assertEquals(4, queue.size());

		try {
			queue.enqueue("6");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(4, queue.size());
		}

		try {
			queue.setCapacity(1);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(4, queue.size());
		}
	}

}
