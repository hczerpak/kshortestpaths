package pl.czerpak.algorithm.hershberger;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import pl.czerpak.model.graph.Path;
import pl.czerpak.util.GraphFactory;

public class HershbergerTestCase extends TestCase {

	private Hershberger h;
	private List<Path> results;
	
	protected void setUp() throws Exception {
		results = new ArrayList<Path>();
		h = new Hershberger(GraphFactory.graph1(), 3, results);
	}
	
	public void testRun() {
		h.run();
		assertNotNull(results);
		assertEquals(3, results.size());
		
		Path path = results.get(0);
		
		assertEquals(19., path.getWeight());
		
		assertEquals(1., path.getLeadEdge().getWeight());
		assertEquals(1., path.getEdgesSequence().get(0).getWeight());
		
		assertEquals(3., path.getEdgesSequence().get(1).getWeight());
		assertEquals(15., path.getEdgesSequence().get(2).getWeight());
		
		assertEquals(3, path.getEdgesSequence().size());
		
		path = results.get(1);
		
		assertEquals(45., path.getWeight());
		
		assertEquals(5, path.getEdgesSequence().size());
		
		assertEquals(1., path.getLeadEdge().getWeight());
		assertEquals(1., path.getEdgesSequence().get(0).getWeight());
		
		assertEquals(4., path.getEdgesSequence().get(1).getWeight());
		assertEquals(11., path.getEdgesSequence().get(2).getWeight());
		assertEquals(14., path.getEdgesSequence().get(3).getWeight());
		assertEquals(15., path.getEdgesSequence().get(4).getWeight());
		
		assertEquals(5, path.getEdgesSequence().size());

		path = results.get(2);
		
		assertEquals(65., path.getWeight());
		
		assertEquals(7, path.getEdgesSequence().size());
		
		System.out.println("All ok!");
	}

}
