package pl.czerpak.model.pbs;

import java.util.ArrayList;
import java.util.List;

import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Path;
import pl.czerpak.model.graph.Vertex;

public class Node {
	private static int counter = 0;

	private String name;

	private Vertex vertex;

	private List<Branch> outgoingBranches;

	private Branch parentBranch;

	public Node() {
		this.outgoingBranches = new ArrayList<Branch>();
		this.name = "Node" + counter++;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	public List<Branch> getOutgoingBranches() {
		return outgoingBranches;
	}

	public void setOutgoingBranches(List<Branch> outgoingBranches) {
		this.outgoingBranches = outgoingBranches;
	}

	public Branch getParentBranch() {
		return parentBranch;
	}

	public void setParentBranch(Branch parentBranch) {
		this.parentBranch = parentBranch;
	}

	public String getName() {
		return name;
	}

	/**
	 * Zwraca kolekcję wszyskich "lead edges" wychodzących z wierzchołka
	 * _vertex. Lead edge do krawędź, która wychodzi z wierzchołka i rozpoczyna
	 * jakąś "najkrótszż ścieżkę". Krawędzi może wychodzić bardzo dużo, ale nie
	 * wszystkie muszą rozpoczynać najkrótsze ścieżki.
	 * 
	 * @return
	 * 
	 */
	public List<Edge> leadEdges() {
		List<Edge> resultCollection = new ArrayList<Edge>();

		for (int i = 0; i < outgoingBranches.size(); i++)
			resultCollection.add(outgoingBranches.get(i).getBranchPath().getEdgesSequence().get(0));

		return resultCollection;
	}

	/**
	 * Zwraca prefixPath powiązaną z węzłem u.
	 * 
	 * @return
	 * 
	 */
	public Path prefixPath() {
		if (parentBranch == null) {
			Path emptyPath = new Path();
			emptyPath.setSource(this.vertex);
			emptyPath.setTarget(this.vertex);

			return emptyPath;
		}

		return parentBranch.getSource().prefixPath().concat(parentBranch.getBranchPath());
	}
}
