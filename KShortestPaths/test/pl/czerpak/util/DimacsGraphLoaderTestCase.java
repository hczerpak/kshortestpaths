package pl.czerpak.util;

import java.io.EOFException;
import java.io.File;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DimacsGraphLoaderTestCase extends TestCase {

	DimacsGraphLoader graphLoader;

	@Before
	public void setUp() throws Exception {
		graphLoader = new DimacsGraphLoader("D:" + File.separator + "workspace mgr" + File.separator + "KShortestPaths" + File.separator + "src" + File.separator + "pl"
				+ File.separator + "czerpak" + File.separator + "resources" + File.separator + "USA-road-d.NY.gr");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testLoadGraph() {
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
