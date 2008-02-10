package pl.hczerpak.kinterface.controller.dispatcher
{
	import com.adobe.flex.extras.controls.springgraph.Item;
	
	import flash.events.Event;

	public class GraphChangeEvent extends KEvent
	{		
		public static const NODE_ADDED : String = "GraphChangeEvent.NODE_ADDED";
		public static const NODE_REMOVED : String = "GraphChangeEvent.NODE_ADDED";
		public static const EDGE_ADDED : String = "GraphChangeEvent.EDGE_ADDED";
		public static const EDGE_REMOVED : String = "GraphChangeEvent.EDGE_REMOVED";
		
		public var node : Item = null;
				
		public function GraphChangeEvent(type : String) : void {
			super(type);
		}
		
		override public function clone() : Event {
			return new GraphChangeEvent(type);
		}
	}
}