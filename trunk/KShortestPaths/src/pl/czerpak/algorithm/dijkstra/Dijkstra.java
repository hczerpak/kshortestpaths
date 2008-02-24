package pl.czerpak.algorithm.dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.czerpak.commons.heap.Heap;
import pl.czerpak.commons.heap.fibonacci.fast.FastUpdateHeap;
import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Path;
import pl.czerpak.model.graph.Vertex;

/**
 * Dijkstra's algorithm
 * 
 * based on description fount at
 * http://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
 * 
 * @author Hubert Czerpak
 */
public class Dijkstra {

	// TODO: wywali� dist??
	private Map<String, Double> distances = new HashMap<String, Double>();

	private Map<String, Vertex> previous = new HashMap<String, Vertex>();

	private DirectedGraph graph;
	
	private Path shortestPath;

	public Dijkstra(DirectedGraph graph) {
		this.graph = graph;
		
		Heap<Vertex> q = new FastUpdateHeap<Vertex>();
		Vertex v;
		for (int i = 0; i < graph.getVerticles().size(); i++) {
			v = graph.getVerticles().get(i);
			q.put(v, Double.POSITIVE_INFINITY);
			distances.put(v.getName(), Double.POSITIVE_INFINITY);
			//previous.remove(v.getName());
		}

		distances.put(graph.getSource().getName(), 0.0);
		q.update(graph.getSource(), 0.0);

		Vertex u;
		Edge edge;
		Double alt;
		List<Vertex> s = new ArrayList<Vertex>();
		// główna pętla tworząca mapę poprzedników węzłów
		while (!q.isEmpty()) {
			u = q.extractMinimumEntry();

			for (int i = 0; i < u.getOutgoingEdges().size(); i++) {
				edge = u.getOutgoingEdges().get(i);
				v = edge.getTarget();
				alt = distances.get(u.getName()) + edge.getWeight();
				if (alt < distances.get(v.getName())) {
					distances.put(v.getName(), alt);
					q.update(v, alt);

					previous.put(v.getName(), u);
				}
			}

			s.add(u);
		}
	}

	public List<Edge> getEdgesSequenceFromRootToVertex(Vertex target) {
		Vertex parent = previous.get(target.getName());
		
		if (parent == null) return new ArrayList<Edge>();

		Edge fromPreviousToTarget = null;
		List<Edge> outgoingEdges = parent.getOutgoingEdges();

		// przeszukiwanie zbioru krawędzi w parencie w poszukiwaniu krawędzi
		// łączącej parenta z targetem
		for (int i = 0; i < outgoingEdges.size(); i++) {
			if (outgoingEdges.get(i).getTarget() == target) {
				fromPreviousToTarget = outgoingEdges.get(i);
				break;
			}
		}

		List<Edge> edgesSequence;

		edgesSequence = getEdgesSequenceFromRootToVertex(parent);

		if (fromPreviousToTarget == null)
			throw new NullPointerException("There should be an edge connecting " + graph.getSource().getName() + " and " + target.getName());

		// dodaj znalezioną krawędź do listy
		edgesSequence.add(fromPreviousToTarget);

		return edgesSequence;
	}
	
	public Path getShortestPath() {
		if (shortestPath == null) {
			shortestPath = new Path();
			shortestPath.setEdgesSequence(getEdgesSequenceFromRootToVertex(graph.getTarget()));
			shortestPath.setWeight(distances.get(graph.getTarget().getName()));
			shortestPath.setSource(graph.getSource());
			shortestPath.setTarget(graph.getTarget());
		}
		return shortestPath;
	}
	
	public ShortestPathTree createShortestPathTree() {
		return new ShortestPathTree(this);
	}
	
	public Sink createSink() {
		graph.reverseEdges();
		Sink s = new Sink(this);
		graph.reverseEdges();
		
		return s;
	}

	public Map<String, Double> getDistances() {
		return distances;
	}

	public Map<String, Vertex> getPrevious() {
		return previous;
	}

	public DirectedGraph getGraph() {
		return graph;
	}
}