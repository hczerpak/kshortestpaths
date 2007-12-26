package pl.czerpak.model.graph;

import java.util.ArrayList;
import java.util.List;

public class DirectedGraph {

	private List<Edge> edges;

	private List<Vertex> verticles;

	private Vertex target;

	private Vertex source;

	public DirectedGraph() {
		edges = new ArrayList<Edge>();
		verticles = new ArrayList<Vertex>();
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public List<Vertex> getVerticles() {
		return verticles;
	}

	public void setVerticles(List<Vertex> verticles) {
		this.verticles = verticles;
	}

	public Vertex getTarget() {
		return target;
	}

	public void setTarget(Vertex target) {
		this.target = target;
	}

	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	/** Ilo�� wierzcho�k�w w grafie * */
	public int n() {
		return verticles.size();
	}

	/** Ilo�� kraw�dzi w grafie * */
	public int m() {
		return edges.size();
	}

	public DirectedGraph clone() {
		DirectedGraph cloned = new DirectedGraph();
		cloned.target = target;
		cloned.source = source;
		cloned.verticles = verticles;
		for (int i = 0; i < edges.size(); i++)
			cloned.edges.add(edges.get(i));

		return cloned;
	}

	public void reverseEdges() {
		Edge edge;
		Vertex temp;
		for (int i = 0; i < edges.size(); i++) {
			edge = edges.get(i);
			temp = edge.getSource();
			edge.setSource(edge.getTarget());
			edge.setTarget(temp);
		}
		temp = source;
		source = target;
		target = temp;
	}

	public String toString() {
		return "Verticles: " + n() + " edges: " + m();
	}
}
