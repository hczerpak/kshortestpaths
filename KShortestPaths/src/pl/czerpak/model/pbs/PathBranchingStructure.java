package pl.czerpak.model.pbs;

import java.util.List;

public class PathBranchingStructure {
	private Node root; // Korzen drzewa pbs

	private List<Node> nodes; // zbior wszystkich wezlow drzewa pbs

	private List<Branch> branches; // zbior wszystkich galezi drzewa

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Branch> getBranches() {
		return branches;
	}

	public void setBranches(List<Branch> branches) {
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
