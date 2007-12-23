package pl.czerpak.util;

import java.util.List;

import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Vertex;

public class GraphFactory {

	/**
	 * Tworzenie przyk�adowego grafu zorientowanego
	 */
	public static DirectedGraph graph1() {
		DirectedGraph g = new DirectedGraph();
		List<Vertex> vs = g.getVerticles();

		// 10 wierzchołków
		for (int i = 0; i < 11; i++)
			vs.add(new Vertex());

		g.setSource(vs.get(0));

		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(0), vs.get(1), 1.));
		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(0), vs.get(3), 2.));
		vs.get(1).getOutgoingEdges().add(new Edge(vs.get(1), vs.get(9), 3.));
		vs.get(1).getOutgoingEdges().add(new Edge(vs.get(1), vs.get(2), 4.));
		vs.get(3).getOutgoingEdges().add(new Edge(vs.get(3), vs.get(4), 5.));
		vs.get(2).getOutgoingEdges().add(new Edge(vs.get(2), vs.get(4), 6.));
		vs.get(4).getOutgoingEdges().add(new Edge(vs.get(4), vs.get(5), 7.));
		vs.get(5).getOutgoingEdges().add(new Edge(vs.get(5), vs.get(3), 8.));
		vs.get(5).getOutgoingEdges().add(new Edge(vs.get(5), vs.get(7), 9.));
		vs.get(2).getOutgoingEdges().add(new Edge(vs.get(2), vs.get(6), 10.));
		vs.get(2).getOutgoingEdges().add(new Edge(vs.get(2), vs.get(8), 11.));
		vs.get(6).getOutgoingEdges().add(new Edge(vs.get(6), vs.get(7), 12.));
		vs.get(7).getOutgoingEdges().add(new Edge(vs.get(7), vs.get(8), 13.));
		vs.get(8).getOutgoingEdges().add(new Edge(vs.get(8), vs.get(9), 14.));
		vs.get(9).getOutgoingEdges().add(new Edge(vs.get(9), vs.get(10), 15.));

		g.setTarget(vs.get(10));
		create(g, vs.get(0));

		return g;
	}
	
	public static DirectedGraph graph2() {
		DirectedGraph g = new DirectedGraph();
		List<Vertex> vs = g.getVerticles();

		// 8 wierzchołków
		for (int i = 0; i < 8; i++)
			vs.add(new Vertex());

		g.setSource(vs.get(0));

		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(0), vs.get(1), 1.));
		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(0), vs.get(2), 1.));
		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(0), vs.get(3), 1.));
		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(1), vs.get(4), 2.));
		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(1), vs.get(5), 2.));
		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(4), vs.get(6), 3.));
		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(5), vs.get(6), 3.));
		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(2), vs.get(7), 2.));
		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(3), vs.get(7), 3.));
		vs.get(0).getOutgoingEdges().add(new Edge(vs.get(6), vs.get(7), 4.));
		

		g.setTarget(vs.get(7));
		create(g, g.getSource());

		return g;
	}

	public static void create(DirectedGraph g, Vertex v) {
		for (int i = 0; i < v.getOutgoingEdges().size(); i++) {
			Edge edge = v.getOutgoingEdges().get(i);
			if (!g.getEdges().contains(edge)) {
				g.getEdges().add(edge);
				create(g, edge.getTarget());
			}
		}
	}
}
