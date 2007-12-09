package pl.czerpak.algorithm.dijkstra;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Path;
import pl.czerpak.model.graph.Vertex;

public class ShortestPathTree {

	private DijkstraTreeElement root;
	private Dijkstra dijkstra;
	private Map<Vertex, Integer> low;

	public ShortestPathTree(Dijkstra dijkstra) {
		this.dijkstra = dijkstra;
		low = new HashMap<Vertex, Integer>();
		
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
	 * Mój sposób na implementacjê preorder.
	 * 
	 * Zaczynaj¹c od korzenia oznaczamy wszystkie elementy na drzewie. Obok jest przygotowany stos
	 * wierzcho³ków grafu. Przy ka¿dym przejœciu ni¿ej wzd³u¿ najkrótszej œcie¿ki path(x, y) zdejmowany jest
	 * ze stosu jeden wierzcho³ek by wiedzieæ, gdzie spoœród potomków wêz³a mamy zejœæ aby pod¹¿aæ
	 * w³aœciw¹ drog¹. Kiedy schodzimy œcie¿k¹ za ka¿dym razem zmniejszamy wartoœæ low i ustawiamy j¹ dla
	 * wszystkich potomków (z wyj¹tkiem tego, do którego pod¹¿ymy w dó³). w ten sposób nie trzeba sprawdzaæ
	 * warunku minblock(u) = min(block(u), minblock(v))
	 * 
	 * @param shortestPath
	 */
	public void createMinblocks(Path shortestPath) {
		//punkt kontrolny, ostatni vertex w sciezce powinien byc takze w roocie drzewa
		if (shortestPath.getTarget() != root.getVertex())
			throw new DijkstraException("Shortest path target <" + shortestPath.getTarget().getName() + "> and shortest path tree root <" + root.getVertex().getName() + "> differes.");

		//przygotowanie stosu bez ostatniego elementu (root.getVertex)
		int pathSize = shortestPath.getEdgesSequence().size();
		Stack<Vertex> vstack = new Stack<Vertex>();
		vstack.add(shortestPath.getSource());
		for (int i = 0; i < pathSize; i++) 
			vstack.add(shortestPath.getEdgesSequence().get(i).getSource());
		
		low.put(root.getVertex(), vstack.size() + 1);// + 1 bo ostatniego nie wrzucono na stos
		preorder(vstack, root, vstack.size() + 1);
		
	}
	

	private void preorder(Stack<Vertex> vstack, DijkstraTreeElement r, int lowValue) {
		DijkstraTreeElement next = null;
		Vertex vertex = vstack.pop();
		for (DijkstraTreeElement child : r.getChildren()) {
			if (child.getVertex() == vertex) {
				next = child;
				continue;
			}
			low.put(child.getVertex(), lowValue);
			preorder(vstack, child, lowValue);
		}
		preorder(vstack, next, lowValue - 1);
	}

	/**
	 * <blockquote>We say that <i>(a, b)</i> is <i>valid</i> if <i>low(b) > i</i>.
	 * 
	 * @param edge
	 * @return
	 */
	public boolean isValid(Edge e, int i) {
		if (e.getTarget() != null && low.containsKey(e.getTarget()) && low.get(e.getTarget()) > i)
			return true;
		
		return false;
	}
}
