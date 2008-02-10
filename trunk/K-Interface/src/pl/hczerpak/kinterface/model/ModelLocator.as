package pl.hczerpak.kinterface.model {
    
    import com.adobe.flex.extras.controls.springgraph.Graph;
    
    import mx.collections.ArrayCollection;
    
    public class ModelLocator {
        
        private static var _instance : ModelLocator = null;
        
        public static function get instance() : ModelLocator { 
            if (!_instance) _instance = new ModelLocator(new SingletonEnforcer());
            return _instance; 
        }
        
        public function ModelLocator(enforcer : SingletonEnforcer) : void { }
         
        [Bindable] public var graph : Graph = new Graph();
       
        private var _nodeNames : Object = new Object();
        private var _nodeNamesCollection : ArrayCollection = new ArrayCollection();
        
        public function exists(nodeName : String) : Boolean {
            return _nodeNames.hasOwnProperty(nodeName);
        }
        
        public function addNode(nodeName : String) : void {
            _nodeNames[nodeName] = nodeName;
            _nodeNamesCollection.addItem(nodeName);
        }
        
        public function get nodeNames() : ArrayCollection {
            return _nodeNamesCollection;
        }
    }
}

class SingletonEnforcer {}