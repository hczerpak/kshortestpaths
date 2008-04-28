package pl.czerpak.model.pbs;

import java.util.List;

import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Path;

public abstract class EquivalenceClass {

	public enum AlgorithmType {
		ALGORITHM_TYPE_REPLACEMENT, ALGORITHM_TYPE_CZERPAK,
	};

	protected AlgorithmType algorithmType;

	protected DirectedGraph graph;
	
	protected List<EquivalenceClass> newClasses;
	
	protected DirectedGraph originalGraph;

	public EquivalenceClass(AlgorithmType algorithmType, DirectedGraph graph) {
		this.algorithmType = algorithmType;
		this.originalGraph = graph;
		this.graph = graph.clone();
	}
	
	public List<EquivalenceClass> getNewEquivalenceClasses() {
		return newClasses;
	}
	
	public abstract Path getShortestPath();
	
	public abstract void modifyPathBranchingStructure(PathBranchingStructure pbs);
	
	public abstract boolean hasNextPath();
	
	public DirectedGraph getGraph() {
		return graph;
	}
}
