package pl.czerpak.commons.list.cdll;

import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CircularDoublyLinkedListTestCase extends TestCase {

	private List<String> list;

	@Before
	public void setUp() throws Exception {
		list = new CircularDoublyLinkedList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		list.add("eee");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddE() {
		assertEquals("aaa", list.get(0));

		assertEquals(5, list.size());
	}

	@Test
	public void testAddIntE() {
		assertEquals(5, list.size());

		list.add(2, "222");
		assertEquals(6, list.size());
		assertEquals(list.get(2), "222");

		list.add(2, "333");
		assertEquals(7, list.size());

		IndexOutOfBoundsException rangeError = null;
		try {
			list.add(1000, "1000");
		} catch (IndexOutOfBoundsException e) {
			rangeError = e;
		}
		assertNotNull(rangeError);
	}

	@Test
	public void testClear() {
		assertEquals(5, list.size());
		list.clear();
		assertEquals(0, list.size());
		list.add("aaA");
		assertEquals(1, list.size());
		list.clear();
		assertEquals(0, list.size());
	}

	@Test
	public void testIsEmpty() {
		assertFalse(list.isEmpty());
		list.clear();

		assertTrue(list.isEmpty());
	}

	@Test
	public void testRemoveInt() {
		list.remove(0);
		assertEquals(list.size(), 4);
		assertEquals(-1, list.indexOf("aaa"));
		assertEquals(0, list.indexOf("bbb"));
		assertEquals("eee", list.get(3));
	}

	@Test
	public void testSet() {
		list.add(0, "000");
		assertEquals(0, list.indexOf("000"));
		assertEquals("000", list.get(0));
		assertEquals(6, list.size());
		assertEquals(1, list.indexOf("aaa"));
		assertEquals(2, list.indexOf("bbb"));
		assertEquals("ccc", list.get(3));
	}

	@Test
	public void testGetIndexOf() {
		list.add(2, "222");
		assertEquals(2, list.indexOf("222"));
		assertEquals(0, list.indexOf("aaa"));
		assertEquals(6, list.size());

		assertEquals(-1, list.indexOf("not in list"));
	}

}
