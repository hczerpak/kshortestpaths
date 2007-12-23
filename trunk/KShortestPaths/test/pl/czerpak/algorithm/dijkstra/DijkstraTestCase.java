package pl.czerpak.algorithm.dijkstra;

import java.util.List;

import junit.framework.TestCase;
import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Path;
import pl.czerpak.model.graph.Vertex;
import pl.czerpak.util.GraphFactory;

public class DijkstraTestCase extends TestCase {

	private DirectedGraph graph;
	private Dijkstra algorithm;

	public void testShortestPath() {

		graph = GraphFactory.graph1();
		algorithm = new Dijkstra(graph);
		
		Path path = algorithm.getShortestPath();
		
		assertEquals(19., path.getWeight());
		
		assertEquals(1., path.getLeadEdge().getWeight());
		assertEquals(1., path.getEdgesSequence().get(0).getWeight());
		
		assertEquals(3., path.getEdgesSequence().get(1).getWeight());
		assertEquals(15., path.getEdgesSequence().get(2).getWeight());
		
		assertEquals(3, path.getEdgesSequence().size());
		
		
	}
	
	public void testDistances() {
		graph = new DirectedGraph();
		List<Vertex> vs = graph.getVerticles();

		// 8 wierzchołków
		for (int i = 0; i < 8; i++)
			vs.add(new Vertex());

		graph.setSource(vs.get(0));

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
		

		graph.setTarget(vs.get(7));
		GraphFactory.create(graph, graph.getSource());
		
		Dijkstra dijkstra = new Dijkstra(graph);

		assertEquals(3., dijkstra.getDistances().get(vs.get(7).getName()));
		assertEquals(6., dijkstra.getDistances().get(vs.get(6).getName()));
		assertEquals(3., dijkstra.getDistances().get(vs.get(4).getName()));
		assertEquals(3., dijkstra.getDistances().get(vs.get(5).getName()));
	}
	
	public void testGetEdgesSequenceFromRootToVertex() {
		graph = new DirectedGraph();
		List<Vertex> vs = graph.getVerticles();

		// 8 wierzchołków
		for (int i = 0; i < 8; i++)
			vs.add(new Vertex());

		graph.setSource(vs.get(0));

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
		

		graph.setTarget(vs.get(7));
		GraphFactory.create(graph, graph.getSource());
		
		Dijkstra dijkstra = new Dijkstra(graph);
		
		List<Edge> edges = dijkstra.getEdgesSequenceFromRootToVertex(vs.get(6));
		
		assertTrue(vs.get(0).getName().equals(edges.get(0).getSource().getName()));
		assertTrue(vs.get(1).getName().equals(edges.get(0).getTarget().getName()));
		assertEquals(3, edges.size());
		assertEquals(1., edges.get(0).getWeight());
		assertEquals(2., edges.get(1).getWeight());
		assertEquals(3., edges.get(2).getWeight());
		
		Path path = dijkstra.getShortestPath();
		edges = path.getEdgesSequence();
		
		assertTrue(vs.get(0).getName().equals(edges.get(0).getSource().getName()));
		assertTrue(vs.get(2).getName().equals(edges.get(0).getTarget().getName()));
		assertEquals(2, edges.size());
		assertEquals(1., edges.get(0).getWeight());
		assertEquals(2., edges.get(1).getWeight());
		
		assertEquals(3., path.getWeight());
	}
}
