package pl.czerpak.model.pbs;

import pl.czerpak.model.graph.Path;

public class Branch {

	private EquivalenceClass equivalenceClass;

	private Node source;

	private Node target;

	private Path branchPath;

	public Branch() {
	}

	public Branch(EquivalenceClass equivalenceClass, Node source, Node target, Path branchPath) {
		super();
		this.equivalenceClass = equivalenceClass;
		this.source = source;
		this.target = target;
		this.branchPath = branchPath;
	}

	public EquivalenceClass getEquivalenceClass() {
		return equivalenceClass;
	}

	public void setEquivalenceClass(EquivalenceClass equivalenceClass) {
		this.equivalenceClass = equivalenceClass;
	}

	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
	}

	public Node getTarget() {
		return target;
	}

	public void setTarget(Node target) {
		this.target = target;
	}

	public Path getBranchPath() {
		return branchPath;
	}

	public void setBranchPath(Path branchPath) {
		this.branchPath = branchPath;
	}

}
