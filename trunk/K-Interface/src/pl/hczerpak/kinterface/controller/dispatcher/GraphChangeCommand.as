package pl.hczerpak.kinterface.controller.dispatcher
{
    import flash.events.Event;
    
	public class GraphChangeCommand extends Command
	{
		
		override public function execute(event : Event) : void {
			trace("[GraphChangeCommand] execute");
		}
		
		override public function clone() : Command {
		    return new GraphChangeCommand();
		}
	}
}