package pl.czerpak.algorithm.replacement;


public class Component //extends ShortestPathTree 
{

//	private Set<Vertex> verticles;
//	private Edge cutEdge;
//	private boolean source;
//
//	/**
//	 * 
//	 * @param cutEdge
//	 * @param dijkstra
//	 * @param dijkstraTreeElement
//	 * @param source if true determines component to be with source S, otherwise with sink S
//	 */
//	public Component(Edge cutEdge, Dijkstra dijkstra, DijkstraTreeElement dijkstraTreeElement, boolean source) {
//		super(dijkstra, dijkstraTreeElement);
//		this.cutEdge = cutEdge;
//		this.source = source;
//
//		indexVerticles();
//	}
//	
//	private void indexVerticles() {
//		verticles = new HashSet<Vertex>();
//		Queue<DijkstraTreeElement> queue = new LinkedBlockingQueue<DijkstraTreeElement>();
//		DijkstraTreeElement element;
//		
//		if (source) {
//			//indeksuje wszystkie wêz³y oprócz œcie¿ki przechodz¹cej przez cutEdge
//			queue.add(this.getRoot());
//			while (!queue.isEmpty()) {
//				element = queue.poll();
//				verticles.add(element.getVertex());
//				for (DijkstraTreeElement child : element.getChildren()) {
//					if (element.getVertex() == cutEdge.getSource() && child.getVertex() == cutEdge.getTarget())
//						continue;
//					
//					queue.add(child);
//				}
//			}
//		} else {
//			//indeksuje wszystkie wêz³y za cutEdge
//			boolean indexing = false;
//			queue.add(this.getRoot());
//			while (!queue.isEmpty()) {
//				element = queue.poll();
//				if (indexing)
//					verticles.add(element.getVertex());
//				for (DijkstraTreeElement child : element.getChildren()) {
//					if (element.getVertex() == cutEdge.getSource() && child.getVertex() == cutEdge.getTarget()) {
//						queue.clear();
//						queue.add(child);
//						indexing = true;
//					}	
//				}
//			}
//		}
//	}
//
//	public boolean contains(Vertex vertex) {
//		return verticles.contains(vertex);
//	}
//	
//	public double distance(Vertex vertex) {
//		return dijkstra.getDistances().get(vertex.getName());
//	}
}
