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

	public EquivalenceClass(AlgorithmType algorithmType, DirectedGraph graph) {
		this.algorithmType = algorithmType;
		this.graph = graph;
	}
	
	public List<EquivalenceClass> getNewEquivalenceClasses() {
		return newClasses;
	}
	
	public DirectedGraph getGraphCopy() {
		return graph.clone();
	}
	
	public abstract Path getShortestPath();
	
	public abstract void modifyPathBranchingStructure(PathBranchingStructure pbs, Path shortestPathP);
}
