package pl.czerpak.algorithm.dijkstra;

import java.util.List;

import junit.framework.TestCase;
import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Vertex;
import pl.czerpak.util.GraphFactory;

public class ShortestPathTreeTestCase extends TestCase {
	
	public void testConstructor() {
		
		DirectedGraph g = new DirectedGraph();
		List<Vertex> vs = g.getVerticles();

		// 8 wierzchołków
		for (int i = 0; i < 8; i++)
			vs.add(new Vertex());

		g.setSource(vs.get(0));

		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(0), vs.get(1), 1.));
		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(0), vs.get(2), 1.));
		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(0), vs.get(3), 1.));
		vs.get(1).getOutgoingEdges().add(new Edge(vs.get(1), vs.get(4), 2.));
		vs.get(1).getOutgoingEdges().add(new Edge(vs.get(1), vs.get(5), 2.));
		vs.get(4).getOutgoingEdges().add(new Edge(vs.get(4), vs.get(6), 3.));
		vs.get(5).getOutgoingEdges().add(new Edge(vs.get(5), vs.get(6), 3.));
		vs.get(2).getOutgoingEdges().add(new Edge(vs.get(2), vs.get(7), 2.));
		vs.get(3).getOutgoingEdges().add(new Edge(vs.get(3), vs.get(7), 3.));
		vs.get(6).getOutgoingEdges().add(new Edge(vs.get(6), vs.get(7), 4.));
		

		g.setTarget(vs.get(7));
		GraphFactory.create(g, g.getSource());
		
		Dijkstra dijkstra = new Dijkstra(g);
		
		ShortestPathTree tree = new ShortestPathTree(dijkstra);
		
		assertEquals(3., tree.getDistance(vs.get(7)));
		assertEquals(6., tree.getDistance(vs.get(6)));
		assertEquals(3., tree.getDistance(vs.get(4)));
		assertEquals(3., tree.getDistance(vs.get(5)));
		
		assertEquals(3., tree.getPathTo(vs.get(7)).getWeight());
		
	}
	
	public void testCreateShortestPathTree() {
		DirectedGraph g = GraphFactory.graph1();
		Dijkstra dijkstra = new Dijkstra(g);
		ShortestPathTree tree = new ShortestPathTree(dijkstra);

		assertEquals(g.getSource().getId(), tree.getRoot().getVertex().getId());

		assertEquals(0, tree.getPathTo(g.getVerticles().get(0)).getLength());
		assertEquals(1, tree.getPathTo(g.getVerticles().get(1)).getLength());
		assertEquals(2, tree.getPathTo(g.getVerticles().get(2)).getLength());
		assertEquals(1, tree.getPathTo(g.getVerticles().get(3)).getLength());
		assertEquals(2, tree.getPathTo(g.getVerticles().get(4)).getLength());
		assertEquals(3, tree.getPathTo(g.getVerticles().get(5)).getLength());
		assertEquals(3, tree.getPathTo(g.getVerticles().get(6)).getLength());
		assertEquals(4, tree.getPathTo(g.getVerticles().get(7)).getLength());
		assertEquals(3, tree.getPathTo(g.getVerticles().get(8)).getLength());
		assertEquals(2, tree.getPathTo(g.getVerticles().get(9)).getLength());
		assertEquals(3, tree.getPathTo(g.getVerticles().get(10)).getLength());
	}
	
	/** 
	 * bezsensowny test bo nie wiem co testowac
	 */
	public void testIsValid() {
		//DirectedGraph g = GraphFactory.graph1();
		//Dijkstra dijkstra = new Dijkstra(g);
		//ShortestPathTree tree = dijkstra.createShortestPathTree();
		//tree.createMinblocks(tree.getPathTo(g.getTarget()));
		
//		for (int i = 0; i < g.getEdges().size(); i++)
//			assertTrue(tree.isValid(g.getEdges().get(i), i));
		
	}
}
