
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Class to test methods in LinkedAbstractListTest
 * 
 * @author Daniel Katowitz
 * @author Aditya Konidena
 * @author Ashten Herr
 *
 */
public class LinkedAbstractListTest {

	/**
	 * Test the ArrayList constructor
	 */
	@Test
	public void testArrayList() {
		LinkedAbstractList<String> test = new LinkedAbstractList<String>(10);
		assertEquals(0, test.size());

		LinkedAbstractList<String> test2 = null;
		try {
			test2 = new LinkedAbstractList<String>(-5);
		} catch (IllegalArgumentException e) {
			assertNull(test2);
		}
	}

	/**
	 * Tests if get() works
	 */
	@Test
	public void testGet() {
		LinkedAbstractList<String> test = new LinkedAbstractList<String>(10);
		test.add(0, "trial");
		assertEquals("trial", test.get(0));
		test.add(1, "trial1");
		test.add(2, "trial2");
		test.add(3, "trial3");
		test.add(4, "trial4");
		test.add(5, "trial5");
		assertEquals("trial3", test.get(3));

		// fail("Not yet implemented");
	}

	/**
	 * Tests if add(index, element) works as desired.
	 */
	@Test
	public void testAdd() {
		LinkedAbstractList<String> test = new LinkedAbstractList<String>(50);
		test.add(0, "trial");
		test.add(1, "trial1");
		test.add(2, "trial2");
		test.add(3, "trial3");
		test.add(4, "trial4");
		test.add(5, "trial5");
		test.add(6, "trial6");
		test.add(7, "trial7");
		test.add(8, "trial8");
		test.add(9, "trial9");
		test.add(10, "trial10");

		test.add(4, "testing");
		assertEquals(12, test.size());
		assertEquals("trial3", test.get(3));
		assertEquals("testing", test.get(4));
		assertEquals("trial4", test.get(5));
		assertEquals("trial5", test.get(6));

		try {
			test.add(19, "invalid");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			test.add(5, null);
		} catch (NullPointerException e) {
			assertEquals(null, e.getMessage());
		}
	}

	/**
	 * Tests if add(index, element) works as desired.
	 */
	@Test
	public void testRemove() {
		LinkedAbstractList<String> test = new LinkedAbstractList<String>(10);
		test.add("trial0");
		test.add("trial1");
		test.add("trial2");
		test.add("trial3");
		test.add("trial4");
		test.add("trial5");

		test.remove(4);
		assertEquals(5, test.size());
		assertEquals("trial0", test.get(0));
		assertEquals("trial1", test.get(1));
		assertEquals("trial2", test.get(2));
		assertEquals("trial3", test.get(3));
		assertEquals("trial5", test.get(4));

		test.remove(1);

		assertEquals(4, test.size());
		assertEquals("trial0", test.get(0));
		assertEquals("trial2", test.get(1));
		assertEquals("trial3", test.get(2));
		assertEquals("trial5", test.get(3));

		try {
			test.remove(12);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}
	}

	/**
	 * Tests the set() method
	 */
	@Test
	public void testSet() {
		LinkedAbstractList<String> test = new LinkedAbstractList<String>(10);
		test.add("trial0");
		test.add("trial1");
		test.add("trial2");
		test.add("trial3");

		assertEquals("trial0", test.set(0, "dummyValue"));
		assertEquals("dummyValue", test.get(0));
		assertEquals("trial1", test.get(1));
		assertEquals("trial2", test.get(2));
		assertEquals("trial3", test.get(3));
		assertEquals(4, test.size());

		try {
			test.set(3, null);
			fail();
		} catch (NullPointerException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			test.set(12, "invalid");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			test.set(-2, "invalid");
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertEquals(null, e.getMessage());
		}
		try {
			test.set(0, "trial1");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(null, e.getMessage());
		}

	}

}
