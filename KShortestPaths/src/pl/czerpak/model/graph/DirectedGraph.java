package pl.czerpak.model.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public void remove(Vertex vertex) {
		verticles.remove(vertex);
		
		for (Edge e : vertex.getOutgoingEdges())
			remove(e);

		vertex.getOutgoingEdges().clear();
		
		for (Edge e : edges)
			if (e.getTarget().getId() == vertex.getId())
				remove(e);
	}
	
	public void remove(Edge edge) {
		edges.remove(edge);
	}
	
	/** quantity of verticles in graph * */
	public int n() {
		return verticles.size();
	}

	/** edges quantity * */
	public int m() {
		return edges.size();
	}

	public DirectedGraph clone() {
		DirectedGraph cloned = new DirectedGraph();
		Map<Vertex, Vertex> originalToClonedVertex = new HashMap<Vertex, Vertex>();
		Map<Edge, Edge> originalToClonedEdge = new HashMap<Edge, Edge>();
		
		//clone verticles
		for (Vertex original : verticles)
			originalToClonedVertex.put(original, (Vertex)original.clone());
		
		//clone edges
		for (Edge original : edges)
			originalToClonedEdge.put(original, (Edge)original.clone());

		//correct refferences to cloned edges
		for (Vertex clonedV : originalToClonedVertex.values()) {
			List<Edge> clonedOutgoingEdges = new ArrayList<Edge>();
			for (Edge e : clonedV.getOutgoingEdges())
				clonedOutgoingEdges.add(originalToClonedEdge.get(e));
			clonedV.setOutgoingEdges(clonedOutgoingEdges);
			
			cloned.verticles.add(clonedV);
		}

		//correct refferences to cloned verticles
		for (Edge clonedE : originalToClonedEdge.values()) {
			clonedE.setSource(originalToClonedVertex.get(clonedE.getSource()));
			clonedE.setTarget(originalToClonedVertex.get(clonedE.getTarget()));
			
			cloned.edges.add(clonedE);
		}
		
		cloned.source = originalToClonedVertex.get(source);
		cloned.target = originalToClonedVertex.get(target);

		return cloned;
	}

	public DirectedGraph reverseEdges() {
		Edge edge;
		Vertex temp, source, target;
		for (int i = 0; i < edges.size(); i++) {
			edge = edges.get(i);
			source = edge.getSource();
			target = edge.getTarget();
			
			temp = edge.getSource();
			edge.setSource(target);
			edge.setTarget(source);
			
			if (source != null)
				source.getOutgoingEdges().remove(edge);
			if (target != null)
				target.getOutgoingEdges().add(edge);
		}
		temp = this.source;
		this.source = this.target;
		this.target = temp;
		
		return this;
	}

	public String toString() {
		return "Verticles: " + n() + " edges: " + m();
	}
}
