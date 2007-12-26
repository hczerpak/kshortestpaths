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

	// private Map<Vertex, Integer> low;

	public ShortestPathTree(Dijkstra dijkstra) {
		this.dijkstra = dijkstra;
		// low = new HashMap<Vertex, Integer>();

		createTree();
	}

	private void createTree() {
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

	/**
	 * Mój sposób na implementację preorder.
	 * 
	 * Zaczynając od korzenia oznaczamy wszystkie elementy na drzewie. Obok jest
	 * przygotowany stos wierzchołków grafu. Przy każdym przejściu niżej wzdłuż
	 * najkrótszej ścieżki path(x, y) zdejmowany jest ze stosu jeden wierzchołek
	 * by wiedzieć, gdzie spośród potomków węzła mamy zejść aby podążać właściwą
	 * drogą. Kiedy schodzimy ścieżką za każdym razem zmniejszamy wartość low i
	 * ustawiamy ją dla wszystkich potomków (z wyjątkiem tego, do którego
	 * podążymy w dół). w ten sposób nie trzeba sprawdzać warunku minblock(u) =
	 * min(block(u), minblock(v))
	 * 
	 * @param shortestPath
	 */
	// public void createMinblocks(Path shortestPath) {
	// //punkt kontrolny, ostatni vertex w sciezce powinien byc takze w roocie
	// drzewa
	// if (shortestPath.getTarget() != root.getVertex())
	// throw new DijkstraException("Shortest path target <" +
	// shortestPath.getTarget().getName() + "> and shortest path tree root <" +
	// root.getVertex().getName() + "> differes.");
	//
	// //przygotowanie stosu bez ostatniego elementu (root.getVertex)
	// int pathSize = shortestPath.getEdgesSequence().size();
	// Stack<Vertex> vstack = new Stack<Vertex>();
	// vstack.add(shortestPath.getSource());
	// for (int i = 0; i < pathSize; i++)
	// vstack.add(shortestPath.getEdgesSequence().get(i).getSource());
	//		
	// low.put(root.getVertex(), vstack.size() + 1);// + 1 bo ostatniego nie
	// wrzucono na stos
	// preorder(vstack, root, vstack.size() + 1);
	//		
	// }
	//	
	//
	// private void preorder(Stack<Vertex> vstack, DijkstraTreeElement r, int
	// lowValue) {
	// DijkstraTreeElement next = null;
	// Vertex vertex = vstack.pop();
	// for (DijkstraTreeElement child : r.getChildren()) {
	// //if child represents previous vertex in path
	// if (child.getVertex() == vertex) {
	// next = child;
	// continue;
	// }
	//			
	// //if map contains certain key, update its lowValue
	// if (low.containsKey(child.getVertex()))
	// low.remove(child.getVertex());
	// low.put(child.getVertex(), lowValue);
	//			
	// preorder(vstack, child, lowValue);
	// }
	//		
	// //process next item with lower value
	// if (next != null)
	// preorder(vstack, next, lowValue - 1);
	// }
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
