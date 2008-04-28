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
public class Sink extends SPT_Base {

	private Map<Long, Integer> minblocks;
	private Map<Long, Integer> blocks;

	public Sink(Dijkstra dijkstra) {
		super(dijkstra);

		this.blocks = new HashMap<Long, Integer>();
		this.minblocks = new HashMap<Long, Integer>();
		// blocks have to be computed along shortest paths
		calculateBlocks();
		// minblocks are computed down thru shortest path tree
		calculateMinBlocks();
	}

	private void calculateBlocks() {
		Path pathXY = dijkstra.getShortestPath();
		
		//sink -> vertex
		List<Edge> edges = pathXY.getEdgesSequence();

		DijkstraTreeElement currentElement = root;
		
		blocks.put(root.getVertex().getId(), edges.size());

		// przejść po ścieżce i po kolei dodawać blocki do poddrzew
		for (int i = 0; i < edges.size(); i++) {
			Vertex applyBlocksHere = edges.get(i).getTarget();
			blocks.put(applyBlocksHere.getId(), edges.size() - i);
			//blocks.put(edges.get(i).getTarget(), edges.size());

			for (int j = 0; j < currentElement.getChildren().size(); j++) {
				DijkstraTreeElement childElement = currentElement.getChildren().get(j);
				applyBlockNumber(childElement, edges.size() - i);
				currentElement = childElement;
			}
		}

	}

	private void applyBlockNumber(DijkstraTreeElement parent, int blockNumber) {
		for (DijkstraTreeElement child : parent.getChildren()) {
			if (blocks.containsKey(child.getVertex())) {
				if (blocks.get(child.getVertex().getId()) > blockNumber)
					blocks.put(child.getVertex().getId(), blockNumber);
			} else
				blocks.put(child.getVertex().getId(), blockNumber);
			applyBlockNumber(child, blockNumber);
		}
	}

	private void calculateMinBlocks() {

		minblocks.put(root.getVertex().getId(), blocks.get(root.getVertex().getId()));

		applyMinblock(root);
	}

	private void applyMinblock(DijkstraTreeElement parent) {
		for (DijkstraTreeElement treeElement : parent.getChildren()) {
			minblocks.put(
					treeElement.getVertex().getId(), 
					Math.min(
							blocks.get(treeElement.getVertex().getId()), 
							minblocks.get(parent.getVertex().getId())));
			applyMinblock(treeElement);
		}
	}

	/**
	 * <blockquote>We say that <i>(a, b)</i> is <i>valid</i> if <i>low(b) > i</i>.
	 * 
	 * @param edge
	 * @return
	 */
	public boolean isValid(Edge e, int i) {
		int lowb = minblocks.get(e.getTarget().getId());

		if (lowb > i)
			return true;

		return false;
	}

	/** Returns path from vertex v to sink */
	public Path getPathFrom(Vertex vertex) {
		Path path = new Path(
				dijkstra.getEdgesSequenceFromRootToVertex(vertex),
				vertex, 
				root.getVertex());

		Collections.reverse(path.getEdgesSequence());

		return path;
	}
	
	/** Returns path from root to vertex v **/
	public Path getPathTo(Vertex vertex) {
		throw new RuntimeException("Invalid call in sink structure");
	}
}
