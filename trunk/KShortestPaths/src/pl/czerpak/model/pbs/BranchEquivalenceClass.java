package pl.czerpak.model.pbs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pl.czerpak.algorithm.replacement.CzerpakReplacement;
import pl.czerpak.algorithm.replacement.Replacement;
import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Path;
import pl.czerpak.model.graph.Vertex;

public class BranchEquivalenceClass extends EquivalenceClass {

	private Branch parentBranch;
	private Path shortestPath = null;

	private Path replacementBasePath;

	public BranchEquivalenceClass(AlgorithmType algorithmType, DirectedGraph subgraph, Path replacementBasePath, Branch parentBranch) {
		super(algorithmType, subgraph);
		this.replacementBasePath = replacementBasePath;
		this.parentBranch = parentBranch;
	}

	@Override
	public void modifyPathBranchingStructure(PathBranchingStructure pbs) {
		if (shortestPath == null) getShortestPath();
		/**
		 * (a) Let w be the vertex where P branches off from branchPath(u, v)*
		 */
		Vertex w = null;

		Path pathUW = new Path();
		Path pathWV = new Path();
		Path pathWTp = new Path();
		Path branchPath = parentBranch.getBranchPath();

		// przygl�damy si� kolejnym elementom P i branchPath do momentu
		// kiedy P p�jdzie
		// w inn� stron�, od razu tworz�c �cie�ki potrzebne w nast�pnym
		// kroku
		for (int j = 0; j < branchPath.getEdgesSequence().size() - 1; j++) {
			Edge edgeFromP = shortestPath.getEdgesSequence().get(j);
			Edge edgeFromBranchPath = branchPath.getEdgesSequence().get(j + 1);

			if (edgeFromP != edgeFromBranchPath)
				w = edgeFromP.getSource();

			if (w == null)
				pathUW.getEdgesSequence().add(edgeFromBranchPath);
			else {
				pathWV.getEdgesSequence().add(edgeFromBranchPath);
				pathWTp.getEdgesSequence().add(edgeFromP);
			}
		}
		if (w == null) {
			// TODO: Out.instance.debug("Error: Path P should branch off from
			// branchPath(u, v)");
			return;
		}

		/***********************************************************************
		 * (b) Insert a new node labeled w, and split the branch (u, v) into two
		 * new branches (u, w) and (w, v). Add second branch (w, tp) that
		 * represents the suffix of P after w.
		 **********************************************************************/
		Node wNode = new Node();
		wNode.setVertex(w);
		wNode.setParentBranch(parentBranch);

		Node tpNode = new Node();
		tpNode.setVertex(graph.getTarget());

		Branch branchWTp = new Branch();
		branchWTp.setSource(wNode);
		branchWTp.setTarget(tpNode);
		branchWTp.setBranchPath(pathWTp);

		tpNode.setParentBranch(branchWTp);

		Branch branchWV = new Branch();
		branchWV.setSource(wNode);
		branchWV.setTarget(parentBranch.getTarget());
		branchWV.setBranchPath(pathWV);

		wNode.getOutgoingBranches().add(branchWTp);
		wNode.getOutgoingBranches().add(branchWV);

		Branch branchUW = parentBranch;
		branchUW.setTarget(wNode);
		branchUW.setBranchPath(pathUW);

		/***********************************************************************
		 * (c) Redistribute the paths of C(u, v) \ P among the four new
		 * equivalence classes C(u, w), C(w, v), C(w, tp), and C(w), depending
		 * on where they branch from branchPath(u, v) and/or P.
		 **********************************************************************/
		// i znowu redystrybucja jest umowna (nie ma) ponieważ nie mamy
		// ścieżek jawnie określonych
		BranchEquivalenceClass equivalenceClassUW = new BranchEquivalenceClass(
				AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, graph.clone(), pathUW, branchUW);
		branchUW.setEquivalenceClass(equivalenceClassUW);

		BranchEquivalenceClass equivalenceClassWV = new BranchEquivalenceClass(
				AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, graph.clone(), pathWV, branchWV);
		branchWV.setEquivalenceClass(equivalenceClassWV);

		BranchEquivalenceClass equivalenceClassWTp = new BranchEquivalenceClass(
				AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, graph.clone(), pathWTp, branchWTp);
		branchWTp.setEquivalenceClass(equivalenceClassWTp);

		NodeEquivalenceClass equivalenceClassW = new NodeEquivalenceClass(
				AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, graph.clone(), wNode);

		newClasses = new ArrayList<EquivalenceClass>();
		newClasses.add(equivalenceClassUW);
		newClasses.add(equivalenceClassWV);
		newClasses.add(equivalenceClassWTp);
		newClasses.add(equivalenceClassW);

	}

	@Override
	public Path getShortestPath() {
		if (shortestPath != null) return shortestPath;
		
		/***********************************************************************
		 * (...) subgraph H of G, defined by deleting from G all the vertices on
		 * prefixPath(a), including a.
		 * 
		 * 
		 * Note: remove all edges containing any of vertex being removed
		 **********************************************************************/
		Set<Vertex> removedVerticles = new HashSet<Vertex>();
		Edge edge;
		Path prefixA = parentBranch.getSource().prefixPath();
		for (int i = 0; i < prefixA.getEdgesSequence().size(); i++) {
			edge = prefixA.getEdgesSequence().get(i);
			//graph.getEdges().remove(edge);
			graph.remove(edge.getSource());
			removedVerticles.add(edge.getSource());
		}
		/** ...including a * */
		graph.remove(parentBranch.getSource().getVertex());
		removedVerticles.add(parentBranch.getSource().getVertex());
		
		//remove not needed edges from graph and all refferences to them from verticles' outgoingEdges
		List<Edge> edgesToRemove = new ArrayList<Edge>();
		for (Edge e : graph.getEdges()) 
			if (removedVerticles.contains(e.getSource()) || removedVerticles.contains(e.getTarget())) {
				//mark edge to be removed from graph
				edgesToRemove.add(e);
				//remove edge from source vertex
				e.getSource().getOutgoingEdges().remove(e);
			}
		//remove all marked edges from graph
		graph.getEdges().removeAll(edgesToRemove);
		
		graph.setSource(parentBranch.getBranchPath().getLeadEdge().getTarget());
		replacementBasePath = replacementBasePath.subPath(graph.getSource());
		
		Path replacement = null;
		switch (algorithmType) {
			case ALGORITHM_TYPE_REPLACEMENT :
				replacement = new Replacement(graph, replacementBasePath).getReplacement();
				break;
			case ALGORITHM_TYPE_CZERPAK :
				replacement = new CzerpakReplacement(graph, replacementBasePath).getReplacement();
				break;
		}

		if (replacement != null)
			replacement.setParentEquivalenceClass(this);

		shortestPath = replacement;
		
		return replacement;
	}
}
