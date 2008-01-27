package pl.hczerpak.kinterface.model {
    import com.adobe.flex.extras.controls.springgraph.Graph;
    
    
    public class ModelLocator {
        
        private static var _instance : ModelLocator = null;
        
        public static function get instance() : ModelLocator { 
            if (!_instance) _instance = new ModelLocator(new SingletonEnforcer());
            return _instance; 
        }
        
        public function ModelLocator(enforcer : SingletonEnforcer) : void { 
        }
        
        [Bindable] public var graph : Graph = new Graph();
    }
}

class SingletonEnforcer {}