package pl.czerpak.algorithm.dijkstra;

import java.util.HashMap;
import java.util.Map;

import pl.czerpak.model.graph.Vertex;

/**
 * 
 * Base class for shortestPathTreeStructures
 * 
 * @author HCzerpak
 *
 */
public class SPT_Base {

	protected DijkstraTreeElement root;
	protected Dijkstra dijkstra;
	
	
	
	public SPT_Base(Dijkstra dijkstra) {
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

	public DijkstraTreeElement getRoot() {
		return root;
	}

	public Double getDistance(Vertex vertex) {
		return dijkstra.getDistances().get(vertex.getName());
	}
}
