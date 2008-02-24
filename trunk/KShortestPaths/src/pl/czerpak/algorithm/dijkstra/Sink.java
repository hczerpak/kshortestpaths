package pl.czerpak.algorithm.dijkstra;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Path;
import pl.czerpak.model.graph.Vertex;

/**
 * Opossition to shortest paths tree - shortest paths sink. Shortest paths from
 * every verticle to sink s.
 * 
 * 
 * @author HCzerpak
 * 
 */
public class Sink extends ShortestPathTree {

	private Map<Vertex, Integer> minblocks;
	private Map<Vertex, Integer> blocks;

	public Sink(Dijkstra dijkstra) {
		super(dijkstra);

		this.blocks = new HashMap<Vertex, Integer>();
		this.minblocks = new HashMap<Vertex, Integer>();
		// blocks have to be computed along shortest paths
		calculateBlocks();
		// minblocks are computed down thru shortest path tree
		calculateMinBlocks();
	}

	private int temp, min;

	public int low(Vertex b) {
		min = dijkstra.getGraph().n(); // n-liczba wierzcholkow grafu

		preorder(root, b);

		return min;
	}

	/**
	 * @param root
	 *            korzeń drzewa Y, root->node - wierzhołek grafu, root->next -
	 *            tablica wskaźników (referencji) na węzły potomne w stosunku do
	 *            root
	 * @param b
	 * @return
	 */
	private int preorder(DijkstraTreeElement root, Vertex b) {
		/**
		 * tablica,w której zapamiętane będą wysokości wszystkich poddrzew węzła
		 * r
		 */
		int[] h = new int[root.getChildren().size()];

		if (root != null) {
			if (root.getVertex().getId() == b.getId()) {
				temp = minimum(h) + 1;
				if (min > temp)
					min = temp;
				return temp;
			}
			for (int i = 0; i < root.getChildren().size(); i++)
				h[i] = preorder(root.getChildren().get(i), b) + 1;
		}
		return minimum(h);
	}

	// private Set<Vertex>traceVerticles = new HashSet<Vertex>();
	private void calculateBlocks() {
		Path pathXY = dijkstra.getShortestPath();
		// czy lista jest w kolejności root->vertex? powinna być vertex->root
		List<Edge> edges = pathXY.getEdgesSequence();

		DijkstraTreeElement currentElement = root;
		// traceVerticles.add(root.getVertex());

		// przejść po ścieżce i po kolei dodawać blocki do poddrzew
		for (int i = edges.size() - 1; i >= 0; i--) {
			Vertex applyBlocksHere = edges.get(i).getSource();
			blocks.put(applyBlocksHere, i);
			// traceVerticles.add(applyBlocksHere);

			for (int j = 0; j < currentElement.getChildren().size(); j++) {
				Vertex temp = currentElement.getChildren().get(j).getVertex();
				if (temp.getId() == applyBlocksHere.getId()) {
					applyBlockNumber(currentElement, i);
					continue;
				}
			}
		}

	}

	private void applyBlockNumber(DijkstraTreeElement parent, int blockNumber) {
		for (DijkstraTreeElement child : parent.getChildren()) {
			if (blocks.containsKey(child.getVertex())) {
				if (blocks.get(child.getVertex()) > blockNumber)
					blocks.put(child.getVertex(), blockNumber);
			}
			applyBlockNumber(child, blockNumber);
		}
	}

	private void calculateMinBlocks() {

		minblocks.put(root.getVertex(), blocks.get(root.getVertex()));

		applyMinblock(root);
	}

	private void applyMinblock(DijkstraTreeElement parent) {
		for (DijkstraTreeElement treeElement : parent.getChildren()) {
			minblocks.put(treeElement.getVertex(), Math.min(blocks
					.get(treeElement.getVertex()), minblocks.get(parent
					.getVertex())));
			applyMinblock(treeElement);
		}
	}

	private int minimum(int[] h) {
		int min = Integer.MIN_VALUE;

		if (h.length > 0)
			min = h[0];
		for (int i = 0; i < h.length; i++)
			if (h[i] < min)
				min = h[i];

		return min;
	}

	/**
	 * <blockquote>We say that <i>(a, b)</i> is <i>valid</i> if <i>low(b) > i</i>.
	 * 
	 * @param edge
	 * @return
	 */
	public boolean isValid(Edge e, int i) {
		int lowb = low(e.getTarget());

		if (e.getTarget() != null && lowb > i)
			return true;

		return false;
	}

	/** Returns path from vertex v to sink */
	public Path getPathFrom(Vertex vertex) {
		Path path = new Path(dijkstra.getEdgesSequenceFromRootToVertex(vertex),
				vertex, root.getVertex());

		Collections.reverse(path.getEdgesSequence());

		return path;
	}
}
