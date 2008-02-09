package pl.hczerpak.kinterface.controller.util
{
    import com.adobe.flex.extras.controls.springgraph.Graph;
    import com.adobe.flex.extras.controls.springgraph.Item;
    
    public class GraphFilesParser
    {
        public static function parseDimacs(dimacsFileContent : String) : Graph {
            return null;
        }
        
        public static function parseGXML(xmlContent : String) : Graph {
            var graph : Graph = new Graph(); 
            var xml : XML = new XML(xmlContent);
            
            var i : int = 0;
            while (xml.Node[i]) {
                graph.add(new Item(xml.Node[i].@id));
                i++;
            }
            
            i = 0;
            while (xml.Edge[i]) {   
                var source : String = xml.Edge[i].@fromID;
                var target : String = xml.Edge[i].@toID;
                       
                graph.link(graph.find(source), graph.find(target)); 
                i++;
            }
            
            return graph;
        }
    }
}