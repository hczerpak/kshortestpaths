package pl.hczerpak.kinterface.controller.dispatcher
{
	import flash.events.Event;
	
	public class Command
	{
		public function clone() : Command { throw new Error("clone() not implemented"); }
		public function execute(event: Event): void { }
		public function result(event: Object): void { }
	}
}