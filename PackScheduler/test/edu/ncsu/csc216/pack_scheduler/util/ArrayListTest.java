/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests ArrayList class
 * 
 * @author Aditya
 *
 */
public class ArrayListTest {

	/**
	 * Test the ArrayList constructor
	 */
	@Test
	public void testArrayList() {
		ArrayList<String> test = new ArrayList<String>();
		assertEquals(0, test.size());

		// fail("Not yet implemented");
	}

	/**
	 * Tests if get() works
	 */
	@Test
	public void testGet() {
		ArrayList<String> test = new ArrayList<String>();
		test.add("trial");
		assertEquals("trial", test.get(0));
		test.add("trial1");
		test.add("trial2");
		test.add("trial3");
		test.add("trial4");
		test.add("trial5");
		assertEquals("trial3", test.get(3));

		// fail("Not yet implemented");
	}

	/**
	 * Tests if add(index, element) works as desired.
	 */
	@Test
	public void testAdd() {
		ArrayList<String> test = new ArrayList<String>();
		test.add("trial");
		test.add("trial1");
		test.add("trial2");
		test.add("trial3");
		test.add("trial4");
		test.add("trial5");
		test.add("trial6");
		test.add("trial7");
		test.add("trial8");
		test.add("trial9");
		test.add("trial10");

		test.add(4, "testing");
		assertEquals(12, test.size());
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
		ArrayList<String> test = new ArrayList<String>();
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
		ArrayList<String> test = new ArrayList<String>();
		test.add("trial0");
		test.add("trial1");
		test.add("trial2");
		test.add("trial3");

		test.set(0, "dummyValue");
		
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
