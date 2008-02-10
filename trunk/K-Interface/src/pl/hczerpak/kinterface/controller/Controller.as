package pl.hczerpak.kinterface.controller
{
    import pl.hczerpak.kinterface.controller.dispatcher.GraphChangeCommand;
    import pl.hczerpak.kinterface.controller.dispatcher.GraphChangeEvent;
    import pl.hczerpak.kinterface.controller.dispatcher.KEventDispatcher;
    
    public class Controller extends KEventDispatcher {
        
        private static var _instance : Controller = null;
        
        public static function get instance() : Controller { 
            if (!_instance) _instance = new Controller(new SingletonEnforcer());
            return _instance; 
        }
        
        public function Controller(enforcer : SingletonEnforcer) : void { 
            addCommand(GraphChangeEvent.EDGE_ADDED, new GraphChangeCommand());
            addCommand(GraphChangeEvent.EDGE_REMOVED, new GraphChangeCommand());
            addCommand(GraphChangeEvent.NODE_ADDED, new GraphChangeCommand());
            addCommand(GraphChangeEvent.NODE_REMOVED, new GraphChangeCommand());
        }
    }
}

class SingletonEnforcer {}