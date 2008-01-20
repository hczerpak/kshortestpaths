package pl.czerpak.algorithm.replacement;

import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.pbs.BranchEquivalenceClass;
import pl.czerpak.model.pbs.Node;
import pl.czerpak.model.pbs.NodeEquivalenceClass;
import pl.czerpak.model.pbs.PathBranchingStructure;
import pl.czerpak.model.pbs.EquivalenceClass.AlgorithmType;
import pl.czerpak.util.GraphFactory;
import junit.framework.TestCase;

public class ReplacementTestCase extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testReplacement() {
		fail("Not yet implemented"); // TODO
	}

	public void testGetReplacement() {
		Node s = new Node();
		DirectedGraph g = GraphFactory.graph1();
		s.setVertex(g.getSource());
	
		NodeEquivalenceClass eq = new NodeEquivalenceClass(AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, g, s);

		PathBranchingStructure ti = new PathBranchingStructure();
		ti.setRoot(s);
		eq.modifyPathBranchingStructure(ti);
		
		BranchEquivalenceClass eq2 = new BranchEquivalenceClass(
				AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, 
				eq.getGraphCopy(), 
				eq.getShortestPath(), 
				ti.getBranches().iterator().next());
		
		
		fail("Not yet implemented"); // TODO
	}

}
