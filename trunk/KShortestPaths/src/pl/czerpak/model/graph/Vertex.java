package pl.czerpak.model.graph;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Vertex implements Cloneable {
	
	private static int counter = 0;

	private long id;

	private List<Edge> outgoingEdges;

	public Vertex() {
		outgoingEdges = new ArrayList<Edge>();
		id = counter++;
	}
	
	public Vertex(String name) {
		this.outgoingEdges = new ArrayList<Edge>();
		id = counter++;
	}
	
	private Vertex(long id) {
		this.id = id;
	}

	public void remove(Edge edge) {
		outgoingEdges.remove(edge);
	}
	
	public List<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}

	public void setOutgoingEdges(List<Edge> outgoingEdges) {
		this.outgoingEdges = outgoingEdges;
	}

	public String getName() {
		return "Vertex" + id;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public long getId() {
		return id;
	}
	
	public Object clone() {
		Vertex cloned = new Vertex(this.id);
		
		for (Edge e : outgoingEdges)
			cloned.outgoingEdges.add(e);
		
		return cloned;
	}
}
