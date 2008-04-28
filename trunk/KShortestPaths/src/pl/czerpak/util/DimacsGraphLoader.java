package pl.czerpak.util;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Edge;
import pl.czerpak.model.graph.Vertex;

public class DimacsGraphLoader {

	private BufferedReader reader;

	private boolean problemLineLoaded = false;

	private Map<String, Vertex> verticles;

	private List<Edge> edges;

	private int verticlesCounter = 0;

	private int edgesCounter = 0;

	private ShortestPathProblemDescription problemDescription;

	private DirectedGraph result = new DirectedGraph();

	/**
	 * Quote from http://www.dis.uniroma1.it/~challenge9/download.shtml :
	 * 
	 * Graph (.gr files) View sample.gr A graph contains n nodes and m arcs
	 * Nodes are identified by integers 1...n Graphs can be interpreted as
	 * directed or undirected, depending on the problem being studied Graphs can
	 * have parallel arcs and self-loops Arc weights are signed integers By
	 * convention, shortest path graph file names should have the suffix .gr.
	 * Line types are as follows. In the format descriptions below, bold
	 * characters should appear exactly as typed: Comment lines Comment lines
	 * can appear anywhere and are ignored by programs.
	 * 
	 * c This is a comment
	 * 
	 * Problem line The problem line is unique and must appear as the first
	 * non-comment line. This line has the format on the right, where n and m
	 * are the number of nodes and the number of arcs, respectively.
	 * 
	 * p sp n m
	 * 
	 * Arc descriptor lines Arc descriptors are of the form on the right, where
	 * U and V are the tail and the head node ids, respectively, and W is the
	 * arc weight.
	 * 
	 * a U V W
	 * 
	 * 
	 * 
	 * @param filename
	 */
	public DimacsGraphLoader(ShortestPathProblemDescription problemDescription) {
		this.problemDescription = problemDescription;
		reader = new BufferedReader(new InputStreamReader(this.getClass()
				.getResourceAsStream(problemDescription.inputFileName)));
	}

	public DirectedGraph loadGraph() throws MultipleProblemLinesException,
			IllegalLineFormatException, EOFException {
		String line = "";
		try {
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("c"))
					continue; // igonore comments
				if (line.startsWith("p sp")) {
					if (problemLineLoaded)
						throw new MultipleProblemLinesException();

					parseProblemLine(line);
					continue;
				}
				if (line.startsWith("a")) {
					if (!problemLineLoaded)
						throw new IllegalLineFormatException(
								"no problem line found");

					parseArcDescriptiorLine(line);
					continue;
				}
				throw new IllegalLineFormatException(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (verticles.size() != verticlesCounter)
			throw new EOFException("Not enough verticles (nodes) found");

		if (edges.size() != edgesCounter)
			throw new EOFException("Not enough edges (arcs) found");

		result.setEdges(edges);
		List<Vertex> verticlesList = new ArrayList<Vertex>(verticlesCounter);
		for (Vertex v : verticles.values())
			verticlesList.add(v);

		result.setVerticles(verticlesList);

		if (result.getSource() == null)
			throw new IllegalArgumentException("Source node " + problemDescription.sourceName + " not found in " + problemDescription.inputFileName);
		if (result.getTarget() == null)
			throw new IllegalArgumentException("Target node " + problemDescription.targetName + " not found in " + problemDescription.inputFileName);

		return result;
	}

	/**
	 * a U V W
	 * 
	 * @throws IllegalLineFormatException
	 * 
	 */
	private void parseArcDescriptiorLine(String line)
			throws IllegalLineFormatException {
		String tailName, headName;
		Vertex tail, head;
		double weight;
		StringTokenizer tokenizer = new StringTokenizer(line, " ");
		if (tokenizer.countTokens() != 4)
			throw new IllegalLineFormatException(line);

		tokenizer.nextToken();
		tailName = tokenizer.nextToken();
		headName = tokenizer.nextToken();
		weight = Double.parseDouble(tokenizer.nextToken());

		if (!verticles.containsKey(tailName)) {
			verticles.put(tailName, tail = new Vertex(tailName));
			verticlesCounter++;
		} else
			tail = verticles.get(tailName);

		if (!verticles.containsKey(headName)) {
			verticles.put(headName, head = new Vertex(headName));
			verticlesCounter++;
		} else
			head = verticles.get(headName);

		Edge edge;
		edges.add(edge = new Edge(head, tail, weight));
		head.add(edge);

		if (problemDescription != null) {
			if (result.getSource() == null) {
				if (headName.equals(problemDescription.sourceName))
					result.setSource(head);
				else if (tailName.equals(problemDescription.sourceName))
					result.setSource(tail);
			}
			if (result.getTarget() == null) {
				if (tailName.equals(problemDescription.targetName))
					result.setTarget(tail);
				else if (headName.equals(problemDescription.targetName))
					result.setTarget(head);
			}
		}
		edgesCounter++;
	}

	/**
	 * p sp n m
	 * 
	 * @throws IllegalLineFormatException
	 */
	private void parseProblemLine(String line)
			throws IllegalLineFormatException {
		int n, m;

		StringTokenizer tokenizer = new StringTokenizer(line, " ");

		if (tokenizer.countTokens() != 4)
			throw new IllegalLineFormatException(line);

		tokenizer.nextToken();
		tokenizer.nextToken();
		n = Integer.parseInt(tokenizer.nextToken());
		m = Integer.parseInt(tokenizer.nextToken());

		verticles = new HashMap<String, Vertex>(n);

		// TODO: sprawdzić szybkość przy implementacji LinkedList
		edges = new ArrayList<Edge>(m);

		problemLineLoaded = true;
	}

}
