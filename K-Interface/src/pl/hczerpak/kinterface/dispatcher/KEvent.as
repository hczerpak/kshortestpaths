package pl.hczerpak.kinterface.dispatcher {
	
	import flash.events.Event;
	
	/**
	 * Represents all messages traveling in application
	 */
	public class KEvent extends Event {
		
		public function KEvent(type : String) {
			super(type);
		}
	}

}