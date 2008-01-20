package pl.czerpak.algorithm.dijkstra;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Path;
import pl.czerpak.model.graph.Vertex;

public class ShortestPathTree {

	private DijkstraTreeElement root;
	private Dijkstra dijkstra;

	public ShortestPathTree(Dijkstra dijkstra) {
		this.dijkstra = dijkstra;

		Vertex v, u;
		DijkstraTreeElement element, previousElement;
		Map<String, DijkstraTreeElement> elements = new HashMap<String, DijkstraTreeElement>();
		Vertex source = dijkstra.getGraph().getSource();

		root = new DijkstraTreeElement(source);
		elements.put(source.getName(), root);
		for (int i = 0; i < dijkstra.getGraph().getVerticles().size(); i++) {
			v = dijkstra.getGraph().getVerticles().get(i);
			element = elements.get(v.getName());
			if (element == null) {
				element = new DijkstraTreeElement(v);
				elements.put(v.getName(), element);
			}

			u = dijkstra.getPrevious().get(v.getName());
			if (u != null) {
				previousElement = elements.get(u.getName());
				if (previousElement == null) {
					previousElement = new DijkstraTreeElement(u);
					elements.put(u.getName(), previousElement);
				}

				previousElement.getChildren().add(element);
				element.setParent(previousElement);
			}
		}
	}

	public Path getPathToRoot(Vertex vertex) {
		Path path = new Path(dijkstra.getEdgesSequenceFromRootToVertex(vertex), vertex, root.getVertex());

		Collections.reverse(path.getEdgesSequence());

		return path;
	}

	public Path getPathFromRoot(Vertex vertex) {
		return new Path(dijkstra.getEdgesSequenceFromRootToVertex(vertex), root.getVertex(), vertex);
	}

	public DijkstraTreeElement getRoot() {
		return root;
	}

	public Double getDistance(Vertex vertex) {
		return dijkstra.getDistances().get(vertex.getName());
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
		 * tablica,w której zapamiętane będą wysokości wszystkich poddrzew węzła r
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
		if (e.getTarget() != null && low(e.getTarget()) > i)
			return true;

		return false;
	}
}
