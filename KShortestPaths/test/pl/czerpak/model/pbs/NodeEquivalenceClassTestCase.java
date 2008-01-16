package pl.czerpak.model.pbs;

import java.util.List;

import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Path;
import pl.czerpak.model.pbs.EquivalenceClass.AlgorithmType;
import pl.czerpak.system.ModelLocator;
import pl.czerpak.util.GraphFactory;
import junit.framework.TestCase;

public class NodeEquivalenceClassTestCase extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetShortestPath() {
		Node s = new Node();
		DirectedGraph g = GraphFactory.graph2();
		s.setVertex(g.getSource());
	
		EquivalenceClass eq = new NodeEquivalenceClass(AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, g, s);

		Path path = eq.getShortestPath();
		List<Edge> edges = path.getEdgesSequence();
		
		assertEquals(g.getVerticles().get(0).getName(), edges.get(0).getSource().getName());
		assertEquals(g.getVerticles().get(2).getName(), edges.get(0).getTarget().getName());
		assertEquals(2, edges.size());
		assertEquals(1., edges.get(0).getWeight());
		assertEquals(2., edges.get(1).getWeight());
		
		assertEquals(3., path.getWeight());
	}

	public void testModifyPathBranchingStructure() {
		Node s = new Node();
		DirectedGraph g = GraphFactory.graph2();
		s.setVertex(g.getSource());
	
		EquivalenceClass eq = new NodeEquivalenceClass(AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, g, s);

		PathBranchingStructure ti = new PathBranchingStructure();
		ti.setRoot(s);
		eq.modifyPathBranchingStructure(ti);
		
		assertEquals(1, ti.getBranches().size());
		assertEquals(2, ti.getNodes().size());
		assertEquals(s.getName(), ti.getBranches().iterator().next().getSource().getName());
		assertEquals(g.getTarget().getName(), ti.getBranches().iterator().next().getTarget().getVertex().getName());
		
		Path branchPath = ti.getBranches().iterator().next().getBranchPath();

		assertEquals(2, branchPath.getLength());
		assertEquals(3., branchPath.getWeight());
	}

	public void testNodeEquivalenceClass() {
		Node s = new Node();
		s.setVertex(ModelLocator.getDirectedGraph().getSource());
	
		EquivalenceClass eq = new NodeEquivalenceClass(AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, ModelLocator.getDirectedGraph(), s);
		
		assertEquals(AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, eq.algorithmType);
		assertEquals(ModelLocator.getDirectedGraph().getEdges().size(), eq.graph.getEdges().size());
		assertEquals(ModelLocator.getDirectedGraph().getSource().getName(), eq.graph.getSource().getName());
		assertEquals(ModelLocator.getDirectedGraph().getTarget().getName(), eq.graph.getTarget().getName());
	}

}
