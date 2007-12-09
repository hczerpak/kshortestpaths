package pl.czerpak.commons.heap.fibonacci;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.czerpak.commons.heap.Heap;

public class FibonacciHeapTestCase extends TestCase {

	private Heap<String> heap = null;

	@Before
	public void setUp() throws Exception {
		heap = new FibonacciHeap<String>();
		heap.put("aaa", 10.9);
		heap.put("bbb", 20.0);
		heap.put("ccc", 30.0);
		heap.put("ddd", 40.0);
		heap.put("eee", 50.0);
		heap.put("fff", 60.0);
	}

	@After
	public void tearDown() throws Exception {
		heap = null;
	}

	@Test
	public void testExtractMinimumEntry() {
		heap.put("minimum", 2.0);

		assertEquals("minimum", heap.getMinimum());
		heap.put("aaa", 1.);
		assertEquals("aaa", heap.getMinimum());

		heap = new FibonacciHeap<String>();
		heap.put("111", 111.);
		heap.extractMinimumEntry();
		assertTrue(heap.isEmpty());

		heap.put("111", 111.);
		assertFalse(heap.isEmpty());
		heap.put("222", 222.);
		assertFalse(heap.isEmpty());
		heap.extractMinimumEntry();
		heap.extractMinimumEntry();
		assertTrue(heap.isEmpty());
	}

	@Test
	public void testGetMinimum() {
		assertEquals("aaa", heap.getMinimum());
	}

	@Test
	public void testPut() {
		heap.put("put", 1.);
		assertEquals("put", heap.getMinimum());
	}

	@Test
	public void testIsEmpty() {
		for (int i = 0; i < 6; i++) {
			assertFalse(heap.isEmpty());
			heap.extractMinimumEntry();
		}

		assertTrue(heap.isEmpty());
		heap.put("empty", 5.);

		assertFalse(heap.isEmpty());

		heap.extractMinimumEntry();
		assertTrue(heap.isEmpty());
	}

	@Test
	public void testUpdate() {
		heap.update("ccc", 1.);
		assertEquals("ccc", heap.getMinimum());// )historyjka dla hefrona byl
												// sobie hefron i kochal kaje :)
	}
}
