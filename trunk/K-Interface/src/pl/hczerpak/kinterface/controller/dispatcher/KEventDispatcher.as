package pl.hczerpak.kinterface.controller.dispatcher
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.utils.Dictionary;
	
	[Event(name=GraphChangeEvent.TYPE_GRAPH_CHANGE_EVENT, type="pl.hczerpak.kinterface.controller.dispatcher.GraphChangeEvent")]

    /** Event dispatcher with ability to run certain commands */
	public class KEventDispatcher extends EventDispatcher
	{
		private var _commands : Dictionary;
		
		public function KEventDispatcher() : void {
			_commands = new Dictionary();
		}
		
		public function addCommand(eventType : String, command : Command) : void {
			_commands[eventType] = command;
		}
		
		override public function dispatchEvent(event : Event) : Boolean {
			var command : Command = _commands[event.type] as Command;
			
			if (command != null)
				command.clone().execute(event);
			else
				super.dispatchEvent(event);
				
			return true;
		}

	}
}