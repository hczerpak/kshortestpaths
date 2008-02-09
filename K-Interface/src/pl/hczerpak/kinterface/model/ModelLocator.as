package pl.hczerpak.kinterface.model {
    
    import com.adobe.flex.extras.controls.springgraph.Graph;
    
    public class ModelLocator {
        
        private static var _instance : ModelLocator = null;
        
        public static function get instance() : ModelLocator { 
            if (!_instance) _instance = new ModelLocator(new SingletonEnforcer());
            return _instance; 
        }
        
        public function ModelLocator(enforcer : SingletonEnforcer) : void { }
         
        [Bindable] public var graph : Graph = new Graph();
       
        private var nodeNames : Object = new Object();
        
        public function exists(nodeName : String) : Boolean {
            return nodeNames.hasOwnProperty(nodeName);
        }
        
        public function addNode(nodeName : String) : void {
            nodeNames[nodeName] = nodeName;
        }
    }
}

class SingletonEnforcer {}