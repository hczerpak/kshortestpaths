package pl.czerpak.model.graph;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class PathTestCase extends TestCase {
	
	private Path p;
	
	
	
	protected void setUp() throws Exception {
		DirectedGraph g = new DirectedGraph();
		List<Vertex> vs = g.getVerticles();

		// 10 wierzchołków
		for (int i = 0; i < 11; i++)
			vs.add(new Vertex());
		
		List<Edge> edgesList = new ArrayList<Edge>();
		Edge e;
		vs.get(0).getOutgoingEdges().add(e = new Edge(vs.get(0), vs.get(1), 1.));
		vs.get(0).getOutgoingEdges().add(e = new Edge(vs.get(0), vs.get(3), 2.)); edgesList.add(e);
		vs.get(1).getOutgoingEdges().add(e = new Edge(vs.get(1), vs.get(9), 3.));
		vs.get(1).getOutgoingEdges().add(e = new Edge(vs.get(1), vs.get(2), 4.));
		vs.get(3).getOutgoingEdges().add(e = new Edge(vs.get(3), vs.get(4), 5.)); edgesList.add(e);
		vs.get(2).getOutgoingEdges().add(e = new Edge(vs.get(2), vs.get(4), 6.));
		vs.get(4).getOutgoingEdges().add(e = new Edge(vs.get(4), vs.get(5), 7.)); edgesList.add(e);
		vs.get(5).getOutgoingEdges().add(e = new Edge(vs.get(5), vs.get(3), 8.));
		vs.get(5).getOutgoingEdges().add(e = new Edge(vs.get(5), vs.get(7), 9.)); edgesList.add(e);
		vs.get(2).getOutgoingEdges().add(e = new Edge(vs.get(2), vs.get(6), 10.));
		vs.get(2).getOutgoingEdges().add(e = new Edge(vs.get(2), vs.get(8), 11.));
		vs.get(6).getOutgoingEdges().add(e = new Edge(vs.get(6), vs.get(7), 12.));
		vs.get(7).getOutgoingEdges().add(e = new Edge(vs.get(7), vs.get(8), 13.)); edgesList.add(e);
		vs.get(8).getOutgoingEdges().add(e = new Edge(vs.get(8), vs.get(9), 14.)); edgesList.add(e);
		vs.get(9).getOutgoingEdges().add(e = new Edge(vs.get(9), vs.get(10), 15.)); edgesList.add(e);
	}

	protected void tearDown() throws Exception {
		p = null;
	}

	public void testGetLeadEdge() {

		assertEquals(p.getEdgesSequence().get(0).getSource().getName(), p.getLeadEdge().getSource().getName());
		assertEquals(p.getEdgesSequence().get(0).getTarget().getName(), p.getLeadEdge().getTarget().getName());
		assertEquals(p.getEdgesSequence().get(0).getWeight(), p.getLeadEdge().getWeight());
	}

	public void testSubPathEdgeEdge() {

		Path subp = p.subPath(p.getLeadEdge(), null);
		assertEquals(p.getEdgesSequence().size(), subp.getEdgesSequence().size());
		
		subp = p.subPath(p.getEdgesSequence().get(0), p.getEdgesSequence().get(p.getEdgesSequence().size() - 1));
		assertEquals(p.getEdgesSequence().size(), subp.getEdgesSequence().size());
		
		subp = p.subPath(p.getEdgesSequence().get(1), null);
		assertEquals(p.getEdgesSequence().size() - 1, subp.getEdgesSequence().size());
		
		subp = p.subPath(p.getEdgesSequence().get(1), p.getEdgesSequence().get(p.getEdgesSequence().size() - 1));
		assertEquals(p.getEdgesSequence().size() - 1, subp.getEdgesSequence().size());
		
		subp = p.subPath(p.getEdgesSequence().get(1), p.getEdgesSequence().get(p.getEdgesSequence().size() - 2));
		assertEquals(p.getEdgesSequence().size() - 2, subp.getEdgesSequence().size());
	}

	public void testSubPathByVertex() {
		
		
		fail("Not yet implemented"); // TODO
	}

	public void testSubPathByIndexIntInt() {
		Path subp = p.subPath(0, p.getEdgesSequence().size() - 1);
		assertEquals(subp.getEdgesSequence().size(), p.getEdgesSequence().size());
		
		fail("Not yet implemented"); // TODO
	}

	public void testSubPathByIndexInt() {
		fail("Not yet implemented"); // TODO
	}

	public void testConcat() {
		fail("Not yet implemented"); // TODO
	}

}
