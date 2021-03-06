package pl.czerpak.algorithm.replacement;

import java.util.List;

import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Path;
import pl.czerpak.model.pbs.BranchEquivalenceClass;
import pl.czerpak.model.pbs.EquivalenceClass;
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

	public void testGetReplacement() {
		Node s = new Node();
		DirectedGraph g = GraphFactory.graph1();
		s.setVertex(g.getSource());
	
		EquivalenceClass eq = new NodeEquivalenceClass(AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, g, s);

		PathBranchingStructure ti = new PathBranchingStructure();
		ti.setRoot(s);
		eq.modifyPathBranchingStructure(ti);
		
		BranchEquivalenceClass eq2 = new BranchEquivalenceClass(
				AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, 
				eq.getGraph(), 
				eq.getShortestPath(), 
				ti.getBranches().iterator().next());
		
		Path shortestPath = eq2.getShortestPath();
		shortestPath.recalculateWeight();
		eq2.modifyPathBranchingStructure(ti);
		
		List<Edge> edges = shortestPath.getEdgesSequence();
		
		assertEquals(g.getVerticles().get(0).getId(), edges.get(0).getSource().getId());
		assertEquals(g.getVerticles().get(2).getId(), edges.get(1).getTarget().getId());
		assertEquals(5, edges.size());
		assertEquals(1., edges.get(0).getWeight());
		assertEquals(4., edges.get(1).getWeight());
		assertEquals(11., edges.get(2).getWeight());
		
		assertEquals(45., shortestPath.getWeight());
	}

}
