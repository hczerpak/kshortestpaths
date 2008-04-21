package pl.czerpak.model.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.czerpak.model.pbs.EquivalenceClass;

/**
 * Klasa reprezentuje ścieżkę w grafie. Najważniejszą częścią jest sekwencja (lista)
 * krawędzi, z których jest złożona ścieżka. Dodatkowa zawiera takie parametry jak
 * wierzchołek początkowy, końcowy, waga ścieżki (suma wag krawędzi).
 * 
 * Zawiera też referencję do nadrzędnej klasy przynależności (EquivalenceClass). Normalnie
 * parametr ten jest nie używany.
 * 
 * @author HCzerpak
 *
 */
public class Path {

	/**
	 * licznik do tworzenia identyfikatorów
	 */
	private static long counter = 0L;

	private Vertex source;
	private Vertex target;
	private Double weight = Double.POSITIVE_INFINITY;
	private long id;
	private List<Edge> edgesSequence;
	private EquivalenceClass parentEquivalenceClass;

	public Path() {
		edgesSequence = new ArrayList<Edge>();
		id = counter++;
	}

	public Path(List<Edge> edgesSequence, Vertex source, Vertex target) {
		this.source = source;
		this.target = target;
		this.edgesSequence = edgesSequence;
		recalculateWeight();
	}
	
	public void add(Edge e) {
		if (e != null) {
			if (source == null) source = e.getSource();
			target = e.getTarget();
			
			edgesSequence.add(e);
		}
	}
	
	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	public Vertex getTarget() {
		return target;
	}

	public void setTarget(Vertex target) {
		this.target = target;
	}

	public Double getWeight() {
		return weight;
	}
	
	/**
	 * @return length of edgeSequence list
	 */
	public int getLength() {
		return edgesSequence.size();
	}

	public List<Edge> getEdgesSequence() {
		return edgesSequence;
	}

	public void setEdgesSequence(List<Edge> edgesSequence) {
		this.edgesSequence = edgesSequence;
	}

	public EquivalenceClass getParentEquivalenceClass() {
		return parentEquivalenceClass;
	}

	public void setParentEquivalenceClass(EquivalenceClass parentEquivalenceClass) {
		this.parentEquivalenceClass = parentEquivalenceClass;
	}

	public Edge getLeadEdge() {
		if (edgesSequence.size() > 0)
			return edgesSequence.get(0);

		return null;
	}

	public String toString() {
		return "Path" + id + " | source: " + source.getName() + " target: " + target.getName();
	}

	public Path clone() {
		Path cloned = new Path();
		cloned.source = source;
		cloned.target = target;
		cloned.parentEquivalenceClass = parentEquivalenceClass;
		for (int i = 0; i < edgesSequence.size(); i++)
			cloned.edgesSequence.add(edgesSequence.get(i));

		cloned.recalculateWeight();
		
		return cloned;
	}

	// UTIL BLOCK

	/**
	 * Zwraca podścieżkę o podanym początkowej i końcowej krawędzi włączając
	 * obydwie krawędzie do wynikowej ścieżki < ., .>
	 * 
	 * @param startEdge
	 * @param endEdge
	 * @return podścieżka
	 * 
	 */
	public Path subPath(Edge startEdge, Edge endEdge) {
		int startIndex = 0;
		int endIndex = 0;

		// if endEdge == null last edge is taken as end
		if (endEdge == null)
			endIndex = -1;
		else
			while (edgesSequence.get(endIndex) != endEdge && endIndex < edgesSequence.size() - 1)
				endIndex++;

		// searching for starting edge
		while (edgesSequence.get(startIndex) != startEdge && startIndex < edgesSequence.size() - 1)
			startIndex++;

		// jeśli się skończyła ścieżka i nie ma końcowej to błędne parametry i
		// zwraca błąd
		if (startIndex == edgesSequence.size())
			throw new IllegalArgumentException("No starting edge");

		return subPath(startIndex, endIndex);
	}

	/**
	 * Buduje podścieżkę zaczynając od podanego wierzchołka
	 * 
	 * @param startingVertex
	 * @return
	 * 
	 */
	public Path subPath(Vertex startingVertex) {
		
		for (int i = 0; i < edgesSequence.size(); i++)
			if (edgesSequence.get(i).getSource().getId() == startingVertex.getId())
				return subPath(i);

		return subPath(0);
	}

	/**
	 * Buduje podścieżkę zaczynając od podanego indeksu krawędzi do końca lub do
	 * krawędzi podanej jako koniec.
	 * 
	 * Threshold is sharp <startIndex, endIndex>
	 * 
	 * @param startIndex Indeks startowej KRAWĘDZI
	 * @param endIndex Indeks końcowej KRAWĘDZI
	 * @return
	 * 
	 */
	public Path subPath(int startIndex, int endIndex) {
		Path subp = new Path();

		if (endIndex == -1)
			endIndex = edgesSequence.size() - 1;

		// copy references to new edge sequence
		for (int i = startIndex; i < endIndex + 1; i++)
			subp.add(edgesSequence.get(i));
		
		//set source and target
		if (subp.edgesSequence.size() > 0) {
			subp.source = subp.edgesSequence.get(0).getSource();
			subp.target = subp.edgesSequence.get(subp.edgesSequence.size() - 1).getTarget();
		}
		
		subp.recalculateWeight();
		
		return subp;
	}

	public Path subPath(int startIndex) {
		return subPath(startIndex, -1);
	}
	
	public void recalculateWeight() {
		if (edgesSequence != null) {
			weight = 0.;
			for (Edge e : edgesSequence)
				weight += e.getWeight();
		}
	}

	/**
	 * Concats two paths
	 * 
	 * @param path
	 * @return
	 * 
	 */
	public Path concat(Path path) {
		if (path == null) return this;
		
		Path concatenation = new Path();

		concatenation.source = this.source;
		concatenation.target = path.target;
		concatenation.parentEquivalenceClass = this.parentEquivalenceClass;
		concatenation.weight = this.weight + path.weight;

		concatenation.edgesSequence.addAll(edgesSequence);
		concatenation.edgesSequence.addAll(path.edgesSequence);
		
		return concatenation;
	}

	/**
	 * Funkcja przeznaczona do tłumaczenia ścieżek zbudowanych z krawędzi
	 * i wierzchołków grafu o poodwracanych kierunkach krawędzi. Klonowanie
	 * takiej ścieżki nigdy nie da prawidłowych rezultatów więc zostaje ona
	 * przetlumaczona na obiekty z grafu użytego do konstrukcji roboczego
	 * grafu z przerobionymi krawędziami.
	 * @return
	 */
	public Path translate(DirectedGraph dictionary) {
		Path translated = new Path();
		
		translated.parentEquivalenceClass = parentEquivalenceClass;
		translated.weight = weight;
		
		Map<Long, Edge> edgeMap = new HashMap<Long, Edge>();
		for (Edge e : dictionary.getEdges())
			edgeMap.put(e.getId(), e);
		
		Map<Long, Vertex> vertexMap = new HashMap<Long, Vertex>();
		for (Vertex v : dictionary.getVerticles())
			vertexMap.put(v.getId(), v);

		for (Edge e : edgesSequence)
			translated.add(edgeMap.get(e.getId()));
		
		translated.source = vertexMap.get(source.getId());
		translated.target = vertexMap.get(target.getId());
		
		return translated;
	}
}
