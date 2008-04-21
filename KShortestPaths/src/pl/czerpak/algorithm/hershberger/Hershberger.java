package pl.czerpak.algorithm.hershberger;

import java.util.ArrayList;
import java.util.List;

import pl.czerpak.commons.heap.Heap;
import pl.czerpak.commons.heap.fibonacci.FibonacciHeap;
import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Path;
import pl.czerpak.model.pbs.EquivalenceClass;
import pl.czerpak.model.pbs.Node;
import pl.czerpak.model.pbs.NodeEquivalenceClass;
import pl.czerpak.model.pbs.PathBranchingStructure;

public class Hershberger {

	private Heap<Path> heap; // stos

	private PathBranchingStructure ti; // path branching structure Ti

	private DirectedGraph graph; // graf G

	private int k;

	private List<Path> resultsContainer;

	public Hershberger(DirectedGraph graph, int k, List<Path> resultsContainer) {
		heap = new FibonacciHeap<Path>();
		this.graph = graph;
		this.resultsContainer = resultsContainer;
		this.k = k;
	}

	public void run() {
		/***********************************************************************
		 * 1. Initialize path branching structure T to contain a single node s,
		 * and put path(s, t) in the heap. There is one equivalence class C(s)
		 * initially, which corresponds to all s-t paths.
		 **********************************************************************/
		Node s = new Node();
		s.setVertex(graph.getSource());
		// There is one equivalence class C(s) initially, which corresponds to
		// all s-t paths.
		EquivalenceClass initialEquivalenceClass = new NodeEquivalenceClass(EquivalenceClass.AlgorithmType.ALGORITHM_TYPE_REPLACEMENT, graph, s);

		// Initialize path branching structure T...
		ti = new PathBranchingStructure();
		// ...to contain a single node s
		ti.setRoot(s);

		// and put path(s, t)...
		Path shortest = initialEquivalenceClass.getShortestPath();
		// ...in the heap
		heap.put(shortest, shortest.getWeight());

		// zmienna potrzebna w kroku czwartym
		List<EquivalenceClass> changedOrCreatedClasses = new ArrayList<EquivalenceClass>();

		/** . Repeat the following steps k times. * */
		for (int i = 0; i < k; i++) {

			/**
			 * 1. Extract the minimum key from the heap. The key corresponds to
			 * some path P. *
			 */
			Path p = heap.extractMinimumEntry();

			// jeśli zabrakło ścieżek
			if (p == null) {
				System.out.println("Not enough paths in graph.\n");
				break;
			}

			resultsContainer.add(p);
			changedOrCreatedClasses.add(p.getParentEquivalenceClass());

			EquivalenceClass pClass = p.getParentEquivalenceClass();

			/*******************************************************************
			 * 2. If P belongs to an equivalence class C(u) for some node u then 
			 * 3. Else (P belongs to the equivalence class C(u, v) for some
			 * branch (u, v))
			 ******************************************************************/

			pClass.modifyPathBranchingStructure(ti);
			changedOrCreatedClasses.addAll(pClass.getNewEquivalenceClasses());

			/*******************************************************************
			 * 4. For each new or changed equivalence class (at most four),
			 * compute the shortest path from s to t that belongs to the class.
			 * Insert these paths into the heap.
			 ******************************************************************/
			for (int j = 0; j < changedOrCreatedClasses.size(); j++) {
				if (changedOrCreatedClasses.get(j).hasNextPath()) {
					shortest = changedOrCreatedClasses.get(j).getShortestPath();
					heap.put(shortest, shortest.getWeight());
				}
			}
			changedOrCreatedClasses.clear();
		}

		System.out.println("PathBranchingStructure ok: \n" + ti.toString() + "\n\n");
	}
}
