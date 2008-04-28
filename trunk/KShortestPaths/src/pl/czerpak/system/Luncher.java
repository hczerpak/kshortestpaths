package pl.czerpak.system;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pl.czerpak.algorithm.hershberger.Hershberger;
import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.model.graph.Path;
import pl.czerpak.util.DimacsGraphLoader;
import pl.czerpak.util.IllegalLineFormatException;
import pl.czerpak.util.MultipleProblemLinesException;
import pl.czerpak.util.ShortestPathProblemDescription;

public class Luncher {

	private static void printHelp() {
		System.out
				.println("Podaj następujące parametry: "
						+ "plik z grafem, plik z wynikiem, algorytm, wierzołek początkowy, wierzchołek końcowy. "
						+ "Przykład: \n");
		System.out
				.println("java -jar K-Core.jar pl.czerpak.system.Luncher input.file output.file REPLACEMENT|CZERPAK|DIJKSTRA startnode endnode 5");
	}

	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		if (args.length == 6) {

			ShortestPathProblemDescription problemDescription = new ShortestPathProblemDescription(
					args[0], args[1], args[2], args[3], args[4], new Integer(args[5]));

			if (problemDescription.k < 1) {
				System.out.println("Podaj k większe od zera.");
				return;
			}

			DirectedGraph dg = null;
			BufferedOutputStream bos = null;
			try {
				dg = new DimacsGraphLoader(problemDescription).loadGraph();
				bos = new BufferedOutputStream(new FileOutputStream(
						problemDescription.outputFileName));
			} catch (EOFException e) {
				e.printStackTrace();
			} catch (MultipleProblemLinesException e) {
				e.printStackTrace();
			} catch (IllegalLineFormatException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			List<Path> results = new ArrayList<Path>();
			Hershberger h = new Hershberger(dg, 3, results);
			h.run();

			StringBuffer sb = new StringBuffer().append("<shortestPaths>\n");
			
			for (int i = 0; i < results.size(); i++) {
				Path p = results.get(i);
				
				sb.append(p.toXMLString());

				try {
					bos.write(sb.toString().getBytes());
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
			sb.append("</shortestPaths>\n");
			
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}

		} else
			printHelp();

	}
}
