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
		edges.removeAll(vertex.getOutgoingEdges());
		
		vertex.getOutgoingEdges().clear();
		
		
		List<Edge> toRemove = new ArrayList<Edge>();
		for (Edge e : edges)
			if (e.getTarget().getId() == vertex.getId()) {
				e.getSource().getOutgoingEdges().remove(e);
				toRemove.add(e);
			}
		edges.remove(toRemove);
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
		Map<Long, Vertex> originalToClonedVertex = new HashMap<Long, Vertex>();
		Map<Long, Edge> originalToClonedEdge = new HashMap<Long, Edge>();
		
		//clone verticles
		for (Vertex original : verticles)
			originalToClonedVertex.put(original.getId(), (Vertex)original.clone());
		
		//clone edges
		for (Edge original : edges)
			originalToClonedEdge.put(original.getId(), (Edge)original.clone());

		//correct refferences to cloned edges
		for (Vertex clonedV : originalToClonedVertex.values()) {
			List<Edge> clonedOutgoingEdges = new ArrayList<Edge>();
			for (Edge e : clonedV.getOutgoingEdges())
				clonedOutgoingEdges.add(originalToClonedEdge.get(e.getId()));
			clonedV.setOutgoingEdges(clonedOutgoingEdges);
			
			cloned.verticles.add(clonedV);
		}

		//correct refferences to cloned verticles
		for (Edge clonedE : originalToClonedEdge.values()) {
			clonedE.setSource(originalToClonedVertex.get(clonedE.getSource().getId()));
			clonedE.setTarget(originalToClonedVertex.get(clonedE.getTarget().getId()));
			
			cloned.edges.add(clonedE);
		}
		
		cloned.source = originalToClonedVertex.get(source.getId());
		cloned.target = originalToClonedVertex.get(target.getId());

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
