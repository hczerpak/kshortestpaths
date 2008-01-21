package pl.czerpak.algorithm.replacement;

import java.util.HashSet;
import java.util.Set;

import pl.czerpak.algorithm.dijkstra.Dijkstra;
import pl.czerpak.algorithm.dijkstra.ShortestPathTree;
import pl.czerpak.commons.heap.Heap;
import pl.czerpak.commons.heap.fibonacci.FibonacciHeap;
import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Path;

public class Replacement {

	private DirectedGraph graph;

	private Path basePath = null;

	private Path bestReplacement = null;

	public Replacement(DirectedGraph graph, Path basePath) {
		this.graph = graph;
		this.basePath = basePath;

		execute();
	}

	private void execute() {
		/***********************************************************************
		 * let x be a shortest path tree from the source to all the remaining
		 * nodes, and let y be a shortest path tree from all the nodes to the
		 * target
		 **********************************************************************/
		ShortestPathTree spiderX = new Dijkstra(graph).createShortestPathTree();

		graph.reverseEdges();
		
		ShortestPathTree spiderY = new Dijkstra(graph).createShortestPathTree();

		graph.reverseEdges();

		Heap<Path> heap = new FibonacciHeap<Path>();

		/**
		 * For every edge ei = (vi, vi+1) contained in P
		 */
		Set<Edge> setEi = new HashSet<Edge>();

		for (int i = 0; i < basePath.getEdgesSequence().size(); i++) {
			Edge cutEdge = basePath.getEdgesSequence().get(i);
			/**
			 * (a) Let Xi = X \ ei. Let Ei be the set of all edges (a, b) in
			 * E\ei such that a and b are in different components of Xi, with a
			 * in the same component as x.
			 */
			// add to Ei edges with source set to a
			for (Edge edge : cutEdge.getSource().getOutgoingEdges()) 
				setEi.add(edge);
			setEi.remove(cutEdge);
			
			// remove from Ei edges with target set to a
			for (Edge edge : setEi) {
				if (edge.getTarget() == cutEdge.getSource())
					setEi.remove(edge);
			}

			/**
			 * For every edge (a, b) contained in E
			 */
			double pathWeight;
			Path path;
			double dxa, cab, dby;
			int j = 0;
			for (Edge edge : setEi) {

				if (spiderY.isValid(edge, j)) {
					dxa = spiderX.getDistance(edge.getSource());
					cab = edge.getWeight();
					dby = spiderY.getDistance(edge.getTarget());
					/**
					 * Let pathWeight(a, b) = dxa + cab + dby. Observe that dxa
					 * and dby can be computed in constant time from X and Y
					 */
					pathWeight = dxa + cab + dby;

					path = new Path(spiderX.getPathFromRoot(edge.getSource()).getEdgesSequence(), graph.getSource(), graph.getTarget());
					path.getEdgesSequence().add(edge);
					path.getEdgesSequence().addAll(spiderY.getPathToRoot(edge.getTarget()).getEdgesSequence());
				} else {
					DirectedGraph g = graph.clone();
					g.remove(edge);
					edge.disjoin();
					path = new Dijkstra(g).getShortestPath();
					pathWeight = path.getWeight();
				}
				heap.put(path, pathWeight);
				j++;
			}
		}
		bestReplacement = heap.getMinimum();
	}

	public Path getReplacement() {
		return bestReplacement;
	}
}
