package pl.czerpak.model.pbs;

import java.util.HashSet;
import java.util.Set;

public class PathBranchingStructure {
	private Node root; // Korzen drzewa pbs

	private Set<Node> nodes; // zbior wszystkich wezlow drzewa pbs

	private Set<Branch> branches; // zbior wszystkich galezi drzewa

	public PathBranchingStructure() {
		nodes = new HashSet<Node>();
		branches = new HashSet<Branch>();
	}
	
	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
		nodes.add(root);
	}

	public Set<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Set<Node> nodes) {
		this.nodes = nodes;
	}

	public Set<Branch> getBranches() {
		return branches;
	}

	public void setBranches(Set<Branch> branches) {
		this.branches = branches;
	}

	/**
	 * 
	 * @return
	 * 
	 */
	public String toString() {
		String result = goDeeper(root, 0);
		result += "Nodes: " + nodes.size() + ", branches: " + branches.size();
		return result;
	}

	/**
	 * toString helper function
	 * 
	 * @param node
	 * @param indent
	 * @return
	 * 
	 */
	private String goDeeper(Node node, int indent) {
		String result = "";
		for (int i = 0; i < indent; i++)
			result += "    ";

		result += node.getName() + "\n";

		++indent;
		for (int i = 0; i < node.getOutgoingBranches().size(); i++)
			result += goDeeper(node.getOutgoingBranches().get(i).getTarget(), indent);

		return result;
	}
}
