package pl.hczerpak.kinterface.controller.dispatcher
{
	import flash.events.Event;

	public class GraphChangeEvent extends KEvent
	{		
		public static const TYPE_GRAPH_CHANGE_EVENT : String = "GraphChangeEvent";
		
		public function GraphChangeEvent() : void {
			super(TYPE_GRAPH_CHANGE_EVENT);
		}
		
		override public function clone() : Event {
			return new GraphChangeEvent();
		}
	}
}