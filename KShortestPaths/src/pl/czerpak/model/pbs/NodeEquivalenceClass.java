package pl.czerpak.model.pbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.czerpak.algorithm.dijkstra.Dijkstra;
import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Path;

public class NodeEquivalenceClass extends EquivalenceClass {

	private Node parentNode;
	private Path shortestPath = null;

	public NodeEquivalenceClass(AlgorithmType algorithmType,
			DirectedGraph subgraph, Node parentNode) {
		super(algorithmType, subgraph);
		this.parentNode = parentNode;
	}

	@Override
	public void modifyPathBranchingStructure(PathBranchingStructure ti) {
		if (shortestPath == null)
			throw new RuntimeException(
					"Create shortest path first. This way shortest path will be lost");

		/**
		 * (a) Add a new branch (u, tp) to T that represents the suffix of P
		 * after u *
		 */
		Branch newBranch = new Branch();

		Node tpNode = new Node();
		tpNode.setVertex(graph.getTarget());
		tpNode.setParentBranch(newBranch);

		newBranch.setSource(parentNode);
		newBranch.setTarget(tpNode);
		// represents the suffix of P after u
		newBranch.setBranchPath(shortestPath.subPath(parentNode.getVertex()));

		// Add a new branch (u, tp) to T
		parentNode.getOutgoingBranches().add(newBranch);
		ti.getBranches().add(newBranch);
		ti.getNodes().add(parentNode);
		ti.getNodes().add(tpNode);

		/***********************************************************************
		 * (b) Remove from C(u) the paths that share at least one edge with P
		 * after u and put all of them except P into the newly created
		 * equivalence class C(u, tp)
		 **********************************************************************/
		BranchEquivalenceClass newEqClass = new BranchEquivalenceClass(
				AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, graph, shortestPath,
				newBranch);
		newBranch.setEquivalenceClass(newEqClass);

		newClasses = new ArrayList<EquivalenceClass>();
		newClasses.add(newEqClass);

		shortestPath = null;
	}

	@Override
	public Path getShortestPath() {
		if (shortestPath == null)
			computeShortestPath();

		return shortestPath;
	}

	private void computeShortestPath() {
		if (shortestPath != null)
			return;

		Edge edge;
		/***********************************************************************
		 * We obtain graph H by deleting from G all the vertices in
		 * prefixPath(w) except w ...
		 **********************************************************************/
		Path prefixW = parentNode.prefixPath();
		for (int i = 0; i < prefixW.getEdgesSequence().size(); i++) {
			edge = prefixW.getEdgesSequence().get(i);
			graph.remove(edge);
			graph.remove(edge.getSource());
		}

		Map<Long, Edge> edgeMap = new HashMap<Long, Edge>();
		for (int i = 0; i < graph.getEdges().size(); i++)
			edgeMap.put(graph.getEdges().get(i).getId(), graph.getEdges()
					.get(i));

		/** ..., plus all the lead edges that leave from w. * */
		List<Edge> leadEdges = parentNode.leadEdges();
		for (int i = 0; i < leadEdges.size(); i++) {
			edge = leadEdges.get(i);
			// translating...
			edge = edgeMap.get(edge.getId());

			/**
			 * czyli że co, że już usunięto tą krawędź?
			 */
			if (edge != null) {
				graph.remove(edge);
				edge.disjoin();
			}
		}
		graph.setSource(parentNode.getVertex());
		/***********************************************************************
		 * We compute the shortest path from w to t in H, then append it to
		 * prefixPath(w)
		 **********************************************************************/
		Dijkstra d = new Dijkstra(graph);
		shortestPath = d.getShortestPath();

		if (shortestPath != null)
			shortestPath.setParentEquivalenceClass(this);
	}

	@Override
	public boolean hasNextPath() {

		computeShortestPath();

		if (shortestPath != null)
			return true;
		else
			return false;
	}
}
