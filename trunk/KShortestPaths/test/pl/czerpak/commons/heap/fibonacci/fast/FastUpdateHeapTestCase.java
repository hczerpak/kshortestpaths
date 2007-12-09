package pl.czerpak.commons.heap.fibonacci.fast;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.czerpak.commons.heap.Heap;

public class FastUpdateHeapTestCase extends TestCase {

	private Heap<String> heap = null;

	@Before
	public void setUp() throws Exception {
		heap = new FastUpdateHeap<String>();
		heap.put("aaa", 10.);
		heap.put("bbb", 20.);
		heap.put("ccc", 30.);
		heap.put("ddd", 40.);
		heap.put("eee", 50.);
		heap.put("fff", 60.);
	}

	@After
	public void tearDown() throws Exception {
		heap = null;
	}

	@Test
	public void testExtractMinimumEntry() {
		heap.put("minimum", 2.);
		assertEquals("minimum", heap.getMinimum());
		heap.extractMinimumEntry();
		assertNotSame("", "minimum", heap.getMinimum());
		assertEquals("aaa", heap.getMinimum());

		heap = new FastUpdateHeap<String>();
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
	public void testPut() {
		heap.put("put", 1.);
		assertEquals("put", heap.getMinimum());
	}
}
