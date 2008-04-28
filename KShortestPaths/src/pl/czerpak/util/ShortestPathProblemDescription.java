package pl.czerpak.util;


public class ShortestPathProblemDescription {
	
	public static enum Algorithm {
		REPLACEMENT,
		CZERPAK,
		DIJKSTRA
	};
	
	public String inputFileName;
	public String outputFileName;
	public Algorithm algorithm;
	public String sourceName;
	public String targetName;
	public Integer k;

	public ShortestPathProblemDescription(String inputFileName,
			String outputFileName, String algorithm, String startNode,
			String endNode, Integer k) {
		super();
		this.inputFileName = inputFileName;
		this.outputFileName = outputFileName;
		this.algorithm = parseAlgorithm(algorithm);
		this.sourceName = startNode;
		this.targetName = endNode;
		this.k = k;
		
		if (algorithm == null)
			throw new IllegalArgumentException("Algorytm nie rozpoznany, podałeś: " + algorithm);
	}
	
	private static Algorithm parseAlgorithm(String a) {
		if (a.trim().toLowerCase().equals("replacement"))
			return Algorithm.REPLACEMENT;
		if (a.trim().toLowerCase().equals("czerpak"))
			return Algorithm.CZERPAK;
		if (a.trim().toLowerCase().equals("dijkstra"))
			return Algorithm.DIJKSTRA;
		
		return null;
	}
	
}
