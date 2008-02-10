package pl.hczerpak.kinterface.controller.util
{
    import com.adobe.flex.extras.controls.springgraph.Graph;
    import com.adobe.flex.extras.controls.springgraph.Item;
    
    import pl.hczerpak.kinterface.controller.Controller;
    import pl.hczerpak.kinterface.controller.dispatcher.GraphChangeEvent;
    import pl.hczerpak.kinterface.model.ModelLocator;
    
    public class GraphReader
    {
        public static function parseDimacs(dimacsFileContent : String) : Graph {
            return null;
        }
        
        public static function readGXML(xmlContent : String) : void {
            var xml : XML = new XML(xmlContent);
            
            var i : int = 0;
            while (xml.Node[i]) {
                var nodeId : String = xml.Node[i].@id;
                ModelLocator.instance.graph.add(new Item(nodeId));
                ModelLocator.instance.registerNodeID(nodeId);
                i++;
            }
            Controller.instance.dispatchEvent(new GraphChangeEvent(GraphChangeEvent.NODE_ADDED));
            
            i = 0;
            while (xml.Edge[i]) {   
                var fromID : String = xml.Edge[i].@fromID;
                var toID : String = xml.Edge[i].@toID;
                var weight : Number = Number(xml.Edge[i].@weight);
                       
                ModelLocator.instance.graph.link(
                    ModelLocator.instance.graph.find(fromID), 
                    ModelLocator.instance.graph.find(toID)); 
                ModelLocator.instance.addEdgeWeight(fromID, toID, weight);
                i++;
            }
            Controller.instance.dispatchEvent(new GraphChangeEvent(GraphChangeEvent.EDGE_ADDED));
        }
    }
}