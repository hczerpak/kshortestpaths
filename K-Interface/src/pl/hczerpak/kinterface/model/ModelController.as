package pl.hczerpak.kinterface.model
{
    import pl.hczerpak.kinterface.dispatcher.KEventDispatcher;
    
    public class ModelController extends KEventDispatcher
    {
        private static var _instance : ModelController = null;
        
        public static function get instance() : ModelController { 
            if (!_instance) _instance = new ModelController(new SingletonEnforcer());
            return _instance; 
        }
        
        public function ModelController(enforcer : SingletonEnforcer) : void { 
        }
    }
}

class SingletonEnforcer {}