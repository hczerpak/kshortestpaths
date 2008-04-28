package pl.czerpak.model.graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Cloneable {
	
	private static int counter = 0;

	private long id;

	private List<Edge> outgoingEdges;
	
	private String name;

	public Vertex() {
		outgoingEdges = new ArrayList<Edge>();
		id = counter++;
	}
	
	public Vertex(String name) {
		this.outgoingEdges = new ArrayList<Edge>();
		id = counter++;
		this.name = name;
	}
	
	private Vertex(long id) {
		outgoingEdges = new ArrayList<Edge>();
		this.id = id;
	}

	public boolean add(Edge edge) {
		return outgoingEdges.add(edge);
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

	public long getId() {
		return id;
	}
	
	public Object clone() {
		Vertex cloned = new Vertex(this.id);
		
		for (Edge e : outgoingEdges)
			cloned.outgoingEdges.add(e);
		
		return cloned;
	}

	@Override
	public boolean equals(Object obj) {
		try {
			Vertex objV = (Vertex)obj;
			if (objV.id == id)
				return true;
		} catch (ClassCastException e) {
			return false;
		}
		
		return false;
	}

	@Override
	public String toString() {
		return name;
	}
}
