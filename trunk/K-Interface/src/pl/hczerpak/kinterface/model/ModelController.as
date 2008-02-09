package pl.hczerpak.kinterface.model
{
    import flash.events.EventDispatcher;
    
	[Event(name=GraphChangeEvent.TYPE_GRAPH_CHANGE_EVENT, type="pl.hczerpak.kinterface.controller.dispatcher.GraphChangeEvent")]
	
    public class ModelController extends EventDispatcher {
        
        private static var _instance : ModelController = null;
        
        public static function get instance() : ModelController { 
            if (!_instance) _instance = new ModelController(new SingletonEnforcer());
            return _instance; 
        }
        
        public function ModelController(enforcer : SingletonEnforcer) : void { }
    }
}

class SingletonEnforcer {}