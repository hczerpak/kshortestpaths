package pl.czerpak.system;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import pl.czerpak.model.graph.DirectedGraph;
import pl.czerpak.util.DimacsGraphLoader;
import pl.czerpak.util.IllegalLineFormatException;
import pl.czerpak.util.MultipleProblemLinesException;

public class Luncher {
	
	
	
	private static void printHelp() {
		System.out.println("Specyfy input file, output file and algorith this way:");
		System.out.println("java -jar K-Core.jar pl.czerpak.system.Luncher input.file output.file REPLACEMENT|CZERPAK|DIJKSTRA");
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		if (args.length >= 3) {
			
			String inputFileName = args[0];
			String outputFileName = args[1];
			String algorighm = args[2];
			

			try {
				DirectedGraph dg = new DimacsGraphLoader(inputFileName).loadGraph();
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFileName));
				
				
			} catch (EOFException e) {
				e.printStackTrace();
			} catch (MultipleProblemLinesException e) {
				e.printStackTrace();
			} catch (IllegalLineFormatException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		} else
			printHelp();
		
	}
}
