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

	// TODO: wywalić dist??
	private Map<Long, Double> distances = new HashMap<Long, Double>();
	private Map<Long, Vertex> previous = new HashMap<Long, Vertex>();
	private Map<Long, Vertex> longToVertex = new HashMap<Long, Vertex>();

	private DirectedGraph graph;
	
	private Path shortestPath;

	public Dijkstra(DirectedGraph graph) {
		this.graph = graph;
		
		if (graph.getVerticles().size() == 0)
			throw new RuntimeException("Graph is empty");
		
		Heap<Long> q = new FastUpdateHeap<Long>();
		Vertex v;
		for (int i = 0; i < graph.getVerticles().size(); i++) {
			v = graph.getVerticles().get(i);
			longToVertex.put(v.getId(), v); //trick tlumaczacy idiki na instancje węzłów bo poslugujemy sie idkami wezlow a nie instancjami, moze byc kilka instancji jednego wezla w trakcie obliczen i one wszystkie wskazuja ten sam wezel w oryginalnym grafie
			q.put(v.getId(), Double.POSITIVE_INFINITY);
			distances.put(v.getId(), Double.POSITIVE_INFINITY);
		}

		distances.put(graph.getSource().getId(), 0.0);
		q.update(graph.getSource().getId(), 0.0);

		Vertex u;
		Edge edge;
		Double alt;
		List<Vertex> s = new ArrayList<Vertex>();
		// główna pętla tworząca mapę poprzedników węzłów
		while (!q.isEmpty()) {
			u = longToVertex.get(q.extractMinimumEntry());

			for (int i = 0; i < u.getOutgoingEdges().size(); i++) {
				edge = u.getOutgoingEdges().get(i);
				v = edge.getTarget();
				alt = distances.get(u.getId()) + edge.getWeight();
				if (alt < distances.get(v.getId())) {
					distances.put(v.getId(), alt);
					q.update(v.getId(), alt);

					previous.put(v.getId(), u);
				}
			}
			s.add(u);
		}
	}

	public List<Edge> getEdgesSequenceFromRootToVertex(Vertex target) {
		Vertex parent = previous.get(target.getId());
		
		if (parent == null) return new ArrayList<Edge>();
		else
			parent = longToVertex.get(parent.getId());
		
		Edge fromPreviousToTarget = null;
		List<Edge> outgoingEdges = parent.getOutgoingEdges();

		// przeszukiwanie zbioru krawędzi w parencie w poszukiwaniu krawędzi
		// łączącej parenta z targetem
		for (int i = 0; i < outgoingEdges.size(); i++) {
			if (outgoingEdges.get(i).getTarget().getId() == target.getId()) {
				fromPreviousToTarget = outgoingEdges.get(i);
				break;
			}
		}

		List<Edge> edgesSequence = getEdgesSequenceFromRootToVertex(parent);

		if (fromPreviousToTarget == null)
			throw new NullPointerException("There should be pathe connecting " + graph.getSource() + " and " + target);

		// dodaj znalezioną krawędź do listy
		edgesSequence.add(fromPreviousToTarget);

		return edgesSequence;
	}
	
	public Path getShortestPath() {
		if (shortestPath == null) {
			Path computed = new Path();
			computed.setEdgesSequence(getEdgesSequenceFromRootToVertex(graph.getTarget()));
			computed.setSource(graph.getSource());
			computed.setTarget(graph.getTarget());
			computed.recalculateWeight();
			
			if (computed.getEdgesSequence().size() > 0)
				shortestPath = computed;
		}
		return shortestPath;
	}

	public Map<Long, Double> getDistances() {
		return distances;
	}

	public Map<Long, Vertex> getPrevious() {
		return previous;
	}

	public DirectedGraph getGraph() {
		return graph;
	}
}
