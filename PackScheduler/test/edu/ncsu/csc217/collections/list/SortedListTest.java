package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testing SortedList Class before implementing in PackScheduler.
 * 
 * @author Ashten
 * @author Torin Cuany
 */
public class SortedListTest {

	/**
	 * Tests creation of a sortedList and function of adding elements in the list
	 * size.
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		// assert that no elements exist in the list
		assertFalse(list.contains("apple"));
		// Check list is empty
		// Add 10 items to the list and check that they are successfully added
		assertTrue(list.isEmpty());
		assertTrue(list.add("apple"));
		assertTrue(list.add("bannana"));
		assertTrue(list.add("orange"));
		assertTrue(list.add("strawberry"));
		assertTrue(list.add("pineapple"));
		assertTrue(list.add("kiwi"));
		assertTrue(list.add("canalope"));
		assertTrue(list.add("watermelon"));
		assertTrue(list.add("blueberry"));
		assertTrue(list.add("raspberry"));
		// check the size of the list
		assertEquals(10, list.size());
		// Add 11th item
		assertTrue(list.add("blackberry"));
		// recheck the size
		assertEquals(11, list.size());

	}

	/**
	 * Tests adding elements to a sorted list. Tests adding various elements. (null,
	 * duplicates, different places)
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();

		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		// Add string that is before banana alphabetically
		list.add("apple");
		assertEquals(2, list.size());
		// assert "apple" was indexed before "banana"
		assertEquals("apple", list.get(0));
		list.add("strawberry");
		list.add("cranberry");
		list.add("kiwi");

		// Check each element is sorted
		assertEquals(5, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("banana", list.get(1));
		// Check middle element is in middle index
		assertEquals("cranberry", list.get(2));
		assertEquals("kiwi", list.get(3));
		// Check last element is in last index
		assertEquals("strawberry", list.get(4));

		// Check adding a null item
		try {
			list.add(null);
			fail("Null item was added.");
		} catch (NullPointerException e) {
			assertEquals(5, list.size());
			assertEquals("apple", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("cranberry", list.get(2));
			assertEquals("kiwi", list.get(3));
			assertEquals("strawberry", list.get(4));
		}

		// Check adding a duplicate
		try {
			list.add("kiwi");
			fail("Duplicate item was added.");
		} catch (IllegalArgumentException m) {
			assertEquals(5, list.size());
			assertEquals("apple", list.get(0));
			assertEquals("banana", list.get(1));
			assertEquals("cranberry", list.get(2));
			assertEquals("kiwi", list.get(3));
			assertEquals("strawberry", list.get(4));
		}
	}

	/**
	 * Tests the get() method for a list element.
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		// test getting from empty list throws exception
		try {
			list.get(0);
			fail("Cant get item from empty list");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		// add some elements
		list.add("apple");
		list.add("strawberry");
		list.add("cranberry");
		list.add("kiwi");
		// check negative index throws exception
		try {
			list.get(-1);
			fail("Cant get item at negative index");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
		}

		try {
			list.get(4);
			fail("Cant get item at the size value.");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
		}
		// check the list contents
		assertEquals("apple", list.get(0));
		assertEquals("cranberry", list.get(1));
		assertEquals("kiwi", list.get(2));
		assertEquals("strawberry", list.get(3));
	}

	/**
	 * Tests the remove() method. Tests removing from the beginning middle and end
	 * of a list. Also checks removing from non-existing indexes that throw
	 * exceptions.
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		// check removing from an empty list throws exception
		try {
			list.remove(0);
			fail("Can't remove from empty list");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}

		// add some elements
		list.add("apple");
		list.add("strawberry");
		list.add("cranberry");
		list.add("kiwi");

		try {
			list.remove(-1);
			fail("Can't remove at negative index");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
		}

		try {
			list.remove(4);
			fail("Can't remove at a lists size");
		} catch (IndexOutOfBoundsException e) {
			assertEquals(4, list.size());
		}

		// check the list contents
		assertEquals("apple", list.get(0));
		assertEquals("cranberry", list.get(1));
		assertEquals("kiwi", list.get(2));
		assertEquals("strawberry", list.get(3));

		// remove middle element
		assertEquals(list.remove(1), "cranberry");
		// check size
		assertEquals(list.size(), 3);

		// check contents
		assertEquals("apple", list.get(0));
		assertEquals("kiwi", list.get(1));
		assertEquals("strawberry", list.get(2));

		// test removing last element
		assertEquals(list.remove(2), "strawberry");
		// check size
		assertEquals(list.size(), 2);

		// check contents
		assertEquals("apple", list.get(0));
		assertEquals("kiwi", list.get(1));

		// test removing last element
		assertEquals(list.remove(0), "apple");
		// check size
		assertEquals(list.size(), 1);

		// check contents
		assertEquals("kiwi", list.get(0));

		// test removing last element
		assertEquals(list.remove(0), "kiwi");
		// check size
		assertEquals(list.size(), 0);

	}

	/**
	 * Tests indexOf() method.
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();

		// test checking an empty list
		assertEquals(-1, list.indexOf("berry"));

		// Add some elements
		list.add("apple");
		list.add("strawberry");
		list.add("cranberry");
		list.add("kiwi");

		// check size
		assertEquals(list.size(), 4);
		
		// test indexOf returns true index of elements in list
		assertEquals(0, list.indexOf("apple"));
		assertEquals(1, list.indexOf("cranberry"));
		assertEquals(2, list.indexOf("kiwi"));
		assertEquals(3, list.indexOf("strawberry"));
		
		// test for elements not in the list
		assertEquals(-1, list.indexOf("lettuce"));
		assertEquals(-1, list.indexOf("watermelon"));

		// test index of null
		try {
			list.indexOf(null);
			fail("Cannot find index of null.");
		} catch (NullPointerException e) {
			assertEquals(0, list.indexOf("apple"));
			assertEquals(1, list.indexOf("cranberry"));
			assertEquals(2, list.indexOf("kiwi"));
			assertEquals(3, list.indexOf("strawberry"));
		}

	}

	/**
	 * Tests the clear() method.
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements
		list.add("apple");
		list.add("strawberry");
		list.add("cranberry");
		list.add("kiwi");
		
		// check size
		assertEquals(list.size(), 4);
		
		// clear list
		list.clear();

		// check size is zero
		assertEquals(list.size(), 0);
	}

	/**
	 * Tests isEmpty() method for SortedList.
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();

		// check initial size is zero
		assertEquals(list.size(), 0);
		
		// Add some elements
		list.add("apple");
		list.add("strawberry");
		list.add("cranberry");
		list.add("kiwi");

		// check size is no longer empty
		assertEquals(list.size(), 4);
	}

	/**
	 * Tests the contains() method for the SortedList class
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();

		// check initial size is zero
		assertEquals(list.size(), 0);
		assertFalse(list.contains("apple"));
		
		// Add some elements
		list.add("apple");
		list.add("strawberry");
		list.add("cranberry");
		list.add("kiwi");
		
		// Test true and false statements
		assertEquals(list.size(), 4);
		assertTrue(list.contains("strawberry"));
		assertTrue(list.contains("cranberry"));
		assertTrue(list.contains("kiwi"));
		assertTrue(list.contains("apple"));
		assertFalse(list.contains("blueberry"));
		assertFalse(list.contains("watermelon"));
		assertFalse(list.contains("candy"));
	}

	/**
	 * Tests the equals() method for the SortedList class.
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// add some elements
		list1.add("apple");
		list1.add("strawberry");
		list1.add("cranberry");
		list1.add("kiwi");
		
		list2.add("apple");
		list2.add("strawberry");
		list2.add("cranberry");
		list2.add("kiwi");
		
		list3.add("apple");
		list3.add("strawberry");
		list3.add("cranberry");
		
		// Test for equality and in-equality
		assertEquals(list1.size(), 4);
		assertEquals(list2.size(), 4);
		assertEquals(list3.size(), 3);
		assertTrue(list1.equals(list2));
		assertTrue(list2.equals(list1));
		assertFalse(list1.equals(list3));
		assertFalse(list3.equals(list1));
	}

	/**
	 * Tests the hashCode() method for the SortedList Class.
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();

		// add some elements
		list1.add("apple");
		list1.add("strawberry");
		list1.add("cranberry");
		list1.add("kiwi");
		
		list2.add("apple");
		list2.add("strawberry");
		list2.add("cranberry");
		list2.add("kiwi");
		
		list3.add("apple");
		list3.add("strawberry");
		list3.add("cranberry");
		
		// Test for same and different hashCode
		assertEquals(list1.size(), 4);
		assertEquals(list2.size(), 4);
		assertEquals(list3.size(), 3);
		assertEquals(list1.hashCode(), list2.hashCode());
		assertNotEquals(list1.hashCode(), list3.hashCode());
	}

}