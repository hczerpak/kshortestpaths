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
		Map<Long, DijkstraTreeElement> elements = new HashMap<Long, DijkstraTreeElement>();
		Vertex source = dijkstra.getGraph().getSource();

		root = new DijkstraTreeElement(source);
		elements.put(source.getId(), root);
		for (int i = 0; i < dijkstra.getGraph().getVerticles().size(); i++) {
			v = dijkstra.getGraph().getVerticles().get(i);
			element = elements.get(v.getId());
			if (element == null) {
				element = new DijkstraTreeElement(v);
				elements.put(v.getId(), element);
			}

			u = dijkstra.getPrevious().get(v.getId());
			if (u != null) {
				previousElement = elements.get(u.getId());
				if (previousElement == null) {
					previousElement = new DijkstraTreeElement(u);
					elements.put(u.getId(), previousElement);
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
		return dijkstra.getDistances().get(vertex.getId());
	}
}
