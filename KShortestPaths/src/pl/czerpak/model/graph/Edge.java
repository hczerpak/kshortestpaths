package pl.czerpak.model.graph;

public class Edge implements Cloneable {

	private static long counter = 0;
	
	private Vertex source;

	private Vertex target;

	private Double weight;
	
	private long id;

	public Edge(Vertex source, Vertex target, Double weight) {
		if (weight < 0)
			throw new IllegalArgumentException("Negative edge weight detected: " + weight);

		this.source = source;
		this.target = target;
		this.weight = weight;
		
		this.id = counter++;
	}
	
	private Edge(long id, Vertex source, Vertex target, Double weight) {
		this(source, target, weight);
		this.id = id;
	}
	
	/**
	 * Usuwa połącznie z listy krawędzi wychodzących z węzła źródłowego source.
	 */
	public void disjoin() {
		source.remove(this);
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
	
	public long getId() {
		return id;
	}
	
	public Object clone() {
		return new Edge(this.id, source, target, new Double(weight.doubleValue()));
	}

	@Override
	public String toString() {
		return "Edge (id:" + id + ") | weight: " + weight + 
		" \nSource: " + source.toString() +
		" \nTarget: " + target.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Edge other = (Edge) obj;
		if (id != other.id)
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}
	
	
}
