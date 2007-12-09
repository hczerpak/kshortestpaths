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
	 * Zwraca kolekcjê wszyskich "lead edges" wychodz¹cych z wierzcho³ka
	 * _vertex. Lead edge do krawêdŸ, która wychodzi z wierzcho³ka i rozpoczyna
	 * jak¹œ "najkrótsz¹ œcie¿kê". Krawêdzi mo¿e wychodziæ bardzo du¿o, ale nie
	 * wszystkie musz¹ rozpoczynaæ najkrótsze œcie¿ki.
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
	 * Zwraca prefixPath powi¹zan¹ z wêz³em u.
	 * 
	 * @return
	 * 
	 */
	public Path prefixPath() {
		if (parentBranch == null) {
			Path emptyPath = new Path();
			emptyPath.setSource(this.vertex);
			emptyPath.setTarget(this.vertex);
			emptyPath.setWeight(new Double(0.0));

			return emptyPath;
		}

		return parentBranch.getSource().prefixPath().concat(parentBranch.getBranchPath());
	}
}
