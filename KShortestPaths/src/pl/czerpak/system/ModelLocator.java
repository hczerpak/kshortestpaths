package pl.czerpak.system;

import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.util.GraphFactory;

public class ModelLocator {
	private static DirectedGraph directedGraphInstance;

	public static DirectedGraph getDirectedGraph() {
		if (directedGraphInstance == null)
			directedGraphInstance = GraphFactory.graph1();

		return directedGraphInstance;
	}
}
