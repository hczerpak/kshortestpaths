package pl.czerpak.model.graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	private static int counter = 0;

	private String name;

	private List<Edge> outgoingEdges;

	public Vertex() {
		this("Vertex" + counter++);
	}

	public Vertex(String name) {
		this.name = name;
		this.outgoingEdges = new ArrayList<Edge>();
	}

	public List<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}

	public void setOutgoingEdges(List<Edge> outgoingEdges) {
		this.outgoingEdges = outgoingEdges;
	}

	public String getName() {
		return name;
	}
}
