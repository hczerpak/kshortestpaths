package pl.czerpak.algorithm.dijkstra;

import pl.czerpak.model.graph.Path;
import pl.czerpak.model.graph.Vertex;

public class ShortestPathTree extends SPT_Base {

	public ShortestPathTree(Dijkstra dijkstra) {
		super(dijkstra);
	}

	/** Returns path from root to vertex v **/
	public Path getPathTo(Vertex vertex) {
		return new Path(dijkstra.getEdgesSequenceFromRootToVertex(vertex), root.getVertex(), vertex);
	}

}
