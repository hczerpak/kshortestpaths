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
	 * Zwraca pod�cie�k� o podanym pocz�tkowej i ko�cowej kraw�dzi w��czaj�c
	 * obydwie kraw�dzie do wynikowej �cie�ki < ., .>
	 * 
	 * @param startEdge
	 * @param endEdge
	 * @return pod�cie�ka
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

		// je�li si� sko�czy�a �cie�ka i nie ma ko�cowej to b��dne parametry i
		// zwraca b��d
		if (startIndex == edgesSequence.size())
			throw new IllegalArgumentException("No starting edge");

		return subPath(startIndex, endIndex);
	}

	/**
	 * Buduje pod�cie�k� zaczynaj�c od podanego wierzcho�ka
	 * 
	 * @param startingVertex
	 * @return
	 * 
	 */
	public Path subPath(Vertex startingVertex) {
		int i = 0;

		for (; i < edgesSequence.size(); i++)
			if (edgesSequence.get(i).getSource() == startingVertex)
				break;

		return subPath(i);
	}

	/**
	 * Buduje pod�cie�k� zaczynaj�c od podanego indeksu kraw�dzi do ko�ca lub do
	 * kraw�dzi podanej jako koniec.
	 * 
	 * @param startIndex
	 * @param endIndex
	 * @return
	 * 
	 */
	public Path subPath(int startIndex, int endIndex) {
		Path subp = new Path();

		if (endIndex == -1)
			endIndex = edgesSequence.size() - 1;

		// copy references to new edge sequence
		for (int i = startIndex; i < endIndex + 1; i++)
			subp.edgesSequence.add(edgesSequence.get(i));
		
		//set source and target
		if (subp.edgesSequence.size() > 0) {
			subp.source = subp.edgesSequence.get(0).getSource();
			subp.target = subp.edgesSequence.get(subp.edgesSequence.size() - 1).getTarget();
		}
		return subp;
	}

	public Path subPath(int startIndex) {
		return subPath(startIndex, -1);
	}

	/**
	 * Konkatenuje dwie �cie�ki
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
