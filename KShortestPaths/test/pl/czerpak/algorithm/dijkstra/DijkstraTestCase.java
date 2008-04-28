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

		graph = GraphFactory.graph2();
		algorithm = new Dijkstra(graph);
		
		assertEquals(2, algorithm.getShortestPath().getLength());
		
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

		assertEquals(3., dijkstra.getDistances().get(vs.get(7).getId()));
		assertEquals(6., dijkstra.getDistances().get(vs.get(6).getId()));
		assertEquals(3., dijkstra.getDistances().get(vs.get(4).getId()));
		assertEquals(3., dijkstra.getDistances().get(vs.get(5).getId()));
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
		
		assertTrue(vs.get(0).getId()==edges.get(0).getSource().getId());
		assertTrue(vs.get(1).getId()==edges.get(0).getTarget().getId());
		assertEquals(3, edges.size());
		assertEquals(1., edges.get(0).getWeight());
		assertEquals(2., edges.get(1).getWeight());
		assertEquals(3., edges.get(2).getWeight());
		
		Path path = dijkstra.getShortestPath();
		edges = path.getEdgesSequence();
		
		assertTrue(vs.get(0).getId()==edges.get(0).getSource().getId());
		assertTrue(vs.get(2).getId()==edges.get(0).getTarget().getId());
		assertEquals(2, edges.size());
		assertEquals(1., edges.get(0).getWeight());
		assertEquals(2., edges.get(1).getWeight());
		
		assertEquals(3., path.getWeight());
	}

	public void testCreateShortestPathTree() {
		graph = GraphFactory.graph1();
		
		SPT_Base spiderX = new SPT_Base(new Dijkstra(graph));

		assertEquals(graph.getSource().getId(), spiderX.getRoot().getVertex().getId());
		
		Sink sink = new Sink(new Dijkstra(graph.clone().reverseEdges()));
		
		assertEquals(graph.getTarget().getId(), sink.getRoot().getVertex().getId());
		assertEquals(2, sink.getPathFrom(graph.getVerticles().get(8)).getLength());
		assertEquals(1, sink.getPathFrom(graph.getVerticles().get(9)).getLength());
	}
}
