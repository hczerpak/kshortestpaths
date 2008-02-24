package pl.czerpak.util;

import java.io.EOFException;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DimacsGraphLoaderTestCase extends TestCase {

	DimacsGraphLoader graphLoader;

	@Before
	public void setUp() throws Exception {
		graphLoader = new DimacsGraphLoader("USA-road-d.NY.gr");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadGraph() {
		assertNotNull(graphLoader);
		try {
			graphLoader.loadGraph();
		} catch (EOFException e) {
			e.printStackTrace();
		} catch (MultipleProblemLinesException e) {
			e.printStackTrace();
		} catch (IllegalLineFormatException e) {
			e.printStackTrace();
		}
	}

}
