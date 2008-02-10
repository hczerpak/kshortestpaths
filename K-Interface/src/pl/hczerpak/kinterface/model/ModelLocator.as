package pl.hczerpak.kinterface.model {
    
    import com.adobe.flex.extras.controls.springgraph.Graph;
    
    import mx.collections.ArrayCollection;
    
    [Bindable]
    public class ModelLocator {
        
        private static var _instance : ModelLocator = null;
        
        public static function get instance() : ModelLocator { 
            if (!_instance) _instance = new ModelLocator(new SingletonEnforcer());
            return _instance; 
        }
        
        public function ModelLocator(enforcer : SingletonEnforcer) : void { }
        
        public var graph : Graph = new Graph();
       
        private var _nodeNames : Object = new Object();
        private var _nodeNamesCollection : ArrayCollection = new ArrayCollection();
        
        /** Map containing nodes edge weights indexed by node names 
        * 
        * Example:
        * 
        *     _edgeWeight[nodeName] returns Object which has properties named by target nodeNames and value of edge weight
        * 
        *  _edgeWeight[nodeName]  ---> Object o
        * o[targetNodeName] ---> value eg. 5,4
        * 
        * */
        private var _edgeWeights : Object = new Object();
        
        public function exists(nodeName : String) : Boolean {
            return _nodeNames.hasOwnProperty(nodeName);
        }
        
        public function registerNodeID(nodeID : String) : void {
            _nodeNames[nodeID] = nodeID;
            _nodeNamesCollection.addItem(nodeID);
            
            trace("[ModelLocator] Node added (id = " + nodeID + ")");
        }
        
        public function get nodeIds() : ArrayCollection {
            return _nodeNamesCollection;
        }
        
        public function addEdgeWeight(fromID : String, toID : String, weight : Number) : void {
            var edgesMap : Object = null;
            
            if (_edgeWeights[fromID])
                edgesMap = _edgeWeights[fromID];
            else {
                edgesMap = new Object();   
                _edgeWeights[fromID] = edgesMap; 
            }
            
            edgesMap[toID] = weight;
            
            trace("[ModelLocator] Edge weight added.\n    fromID: " + fromID + "\n    toID: " + toID + "\n    weight: " + weight);
        }
        
        public function edgeWeight(fromID : String, toID : String) : Number {
            var edgesMap : Object = null;
            
            if (_edgeWeights[fromID]) {
                edgesMap = _edgeWeights[fromID];
                if (edgesMap[toID])
                    return edgesMap[toID];
            }
            
            return -1;
        }
    }
}

class SingletonEnforcer {}