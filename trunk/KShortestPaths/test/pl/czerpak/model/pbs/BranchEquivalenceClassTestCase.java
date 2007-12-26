package pl.czerpak.model.pbs;

import java.util.List;

import junit.framework.TestCase;
import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Path;
import pl.czerpak.model.pbs.EquivalenceClass.AlgorithmType;
import pl.czerpak.system.ModelLocator;
import pl.czerpak.util.GraphFactory;

public class BranchEquivalenceClassTestCase extends TestCase {

	public void testGetShortestPath() {
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
		
		Path shortestPath = eq2.getShortestPath();
		
		List<Edge> edges = shortestPath.getEdgesSequence();
		
		assertEquals(g.getVerticles().get(0).getName(), edges.get(0).getSource().getName());
		assertEquals(g.getVerticles().get(2).getName(), edges.get(0).getTarget().getName());
		assertEquals(5, edges.size());
		assertEquals(1., edges.get(0).getWeight());
		assertEquals(4., edges.get(1).getWeight());
		
		assertEquals(30., shortestPath.getWeight());
	}

	public void testModifyPathBranchingStructure() {
		Node s = new Node();
		DirectedGraph g = GraphFactory.graph1();
		s.setVertex(g.getSource());
	
		EquivalenceClass eq = new NodeEquivalenceClass(AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, g, s);

		PathBranchingStructure ti = new PathBranchingStructure();
		ti.setRoot(s);
		eq.modifyPathBranchingStructure(ti);
		
		EquivalenceClass eq2 = new BranchEquivalenceClass(
				AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, 
				eq.getGraphCopy(), 
				eq.getShortestPath(), 
				ti.getBranches().iterator().next());
		
		eq2.modifyPathBranchingStructure(ti);
		
		fail("Not yet implemented");
		
	}

	public void testBranchEquivalenceClass() {
		Node s = new Node();
		s.setVertex(ModelLocator.getDirectedGraph().getSource());
	
		EquivalenceClass eq = new BranchEquivalenceClass(AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, ModelLocator.getDirectedGraph(), null, null);
		
		assertEquals(AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, eq.algorithmType);
		assertEquals(ModelLocator.getDirectedGraph().getEdges().size(), eq.graph.getEdges().size());
		assertEquals(ModelLocator.getDirectedGraph().getSource().getName(), eq.graph.getSource().getName());
		assertEquals(ModelLocator.getDirectedGraph().getTarget().getName(), eq.graph.getTarget().getName());
	}

}
