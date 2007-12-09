package pl.czerpak.model.graph;

import java.util.ArrayList;
import java.util.List;

import pl.czerpak.model.pbs.EquivalenceClass;

public class Path {

	private static int counter = 0;

	private Vertex source;
	private Vertex target;
	private Double weight = Double.POSITIVE_INFINITY;
	private String name;
	private List<Edge> edgesSequence;
	private EquivalenceClass parentEquivalenceClass;

	public Path() {
		edgesSequence = new ArrayList<Edge>();
		name = "Path" + counter++;
	}

	public Path(List<Edge> edgesSequence, Vertex source, Vertex target) {
		this.source = source;
		this.target = target;
		this.edgesSequence = edgesSequence;
		if (edgesSequence != null) {
			weight = 0.;
			for (Edge e : edgesSequence)
				weight += e.getWeight();
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

	public void setWeight(Double weight) {
		this.weight = weight;
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

	public String getName() {
		return name;
	}

	public Edge getLeadEdge() {
		if (edgesSequence.size() > 0)
			return edgesSequence.get(0);

		return null;
	}

	public String toString() {
		return name + " | source: " + source.getName() + " target: " + target.getName();
	}

	public Path clone() {
		Path cloned = new Path();
		cloned.source = source;
		cloned.target = target;
		cloned.weight = weight;
		cloned.parentEquivalenceClass = parentEquivalenceClass;
		for (int i = 0; i < edgesSequence.size(); i++)
			cloned.edgesSequence.add(edgesSequence.get(i));

		return cloned;
	}

	// UTIL BLOCK

	/**
	 * Zwraca podœcie¿kê o podanym pocz¹tkowej i koñcowej krawêdzi w³¹czaj¹c
	 * obydwie krawêdzie do wynikowej œcie¿ki < ., .>
	 * 
	 * @param startEdge
	 * @param endEdge
	 * @return podœcie¿ka
	 * 
	 */
	public Path subPath(Edge startEdge, Edge endEdge) {
		int startIndex = 0;
		int endIndex = 0;

		// koniec œcie¿ki to ostatnia krawêdŸ jeœli nie zdefiniowano koñca
		// bezpoœrednio
		if (endEdge == null)
			endIndex = -1;
		else
			while (edgesSequence.get(endIndex) != endEdge && endIndex < edgesSequence.size() - 1)
				endIndex++;

		// wyszukanie krawêdzi pocz¹tkowej
		while (edgesSequence.get(startIndex) != startEdge && startIndex < edgesSequence.size() - 1)
			startIndex++;

		// jeœli siê skoñczy³a œcie¿ka i nie ma koñcowej to b³êdne parametry i
		// zwraca b³¹d
		if (startIndex == edgesSequence.size())
			throw new IllegalArgumentException("Nie znaleziono ostatniej krawêdzi");

		return subPathByIndex(startIndex, endIndex);
	}

	/**
	 * Buduje podœcie¿kê zaczynaj¹c od podanego wierzcho³ka
	 * 
	 * @param startingVertex
	 * @return
	 * 
	 */
	public Path subPathByVertex(Vertex startingVertex) {
		int startIndex = 0;

		for (int i = 0; i < edgesSequence.size(); i++)
			if (edgesSequence.get(i).getSource() == startingVertex)
				break;

		return subPathByIndex(startIndex);
	}

	/**
	 * Buduje podœcie¿kê zaczynaj¹c od podanego indeksu krawêdzi do koñca lub do
	 * krawêdzi podanej jako koniec.
	 * 
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * 
	 */
	public Path subPathByIndex(int startIndex, int endIndex) {
		Path subp = new Path();

		if (endIndex == -1)
			endIndex = edgesSequence.size() - 1;

		// przepisywanie sekwencji krawêdzi do obiektu podœcie¿ki
		for (int i = startIndex; i < endIndex + 1; i++)
			subp.edgesSequence.add(edgesSequence.get(i));

		return subp;
	}

	public Path subPathByIndex(int startIndex) {
		return subPathByIndex(startIndex, -1);
	}

	/**
	 * Konkatenuje dwie œcie¿ki
	 * 
	 * @param path
	 * @return
	 * 
	 */
	public Path concat(Path path) {
		Path concatenation = new Path();

		concatenation.source = this.source;
		concatenation.target = path.target;
		concatenation.parentEquivalenceClass = this.parentEquivalenceClass;
		concatenation.weight = this.weight + path.weight;

		for (int i = 0; i < edgesSequence.size(); i++)
			concatenation.edgesSequence.add(edgesSequence.get(i));

		if (path != null)
			for (int i = 0; i < path.edgesSequence.size(); i++)
				concatenation.edgesSequence.add(path.edgesSequence.get(i));

		return concatenation;
	}
}
