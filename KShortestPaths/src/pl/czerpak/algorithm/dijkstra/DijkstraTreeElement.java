package pl.czerpak.algorithm.dijkstra;

import java.util.ArrayList;
import java.util.List;

import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Vertex;

public class DijkstraTreeElement {

	private static int counter = 0;

	private DijkstraTreeElement parent;

	private List<DijkstraTreeElement> children;

	private Vertex vertex;

	private String name;

	public DijkstraTreeElement(Vertex vertex) {
		this.children = new ArrayList<DijkstraTreeElement>();
		this.vertex = vertex;
		this.name = "DijkstraTreeElement" + counter++;
	}

	public DijkstraTreeElement getParent() {
		return parent;
	}

	public void setParent(DijkstraTreeElement parent) {
		this.parent = parent;
	}

	public List<DijkstraTreeElement> getChildren() {
		return children;
	}

	public void setChildren(List<DijkstraTreeElement> children) {
		this.children = children;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	public String getName() {
		return name;
	}
	
	public Edge getParentEdge() {
		Vertex parentVertex = parent.getVertex();
		Edge edge;
		for (int i = 0; i < parentVertex.getOutgoingEdges().size(); i++) {
			edge = parentVertex.getOutgoingEdges().get(i);
			if (edge.getTarget() == vertex)
				return edge;
		}
		
		throw new IllegalStateException("Parent Edge not found!");
	}
}
