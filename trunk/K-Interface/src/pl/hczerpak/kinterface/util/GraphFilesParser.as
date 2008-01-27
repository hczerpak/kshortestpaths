package pl.hczerpak.kinterface.util
{
    import com.adobe.flex.extras.controls.springgraph.Graph;
    import com.adobe.flex.extras.controls.springgraph.Item;
    
    public class GraphFilesParser
    {
        public static function parseDimacs(dimacsFileContent : String) : XML {
            return null;
        }
        
        public static function parseGXML(xmlContent : String) : XML {
            var graph : Graph = new Graph(); 
            var xml : XML = new XML(xmlContent);
            
            var i : int = 0;
            while (xml.Vertex[i]) {
                graph.add(new Item(xml.Vertex[i].@id));
                i++;
            }
            
            i = 0;
            while (xml.Edge[i]) {
                graph.link(graph.find(xml.Edge[i].@fromID), graph.find(xml.Edge[i].@toID)); 
                i++;
            }
            
            return new XML(xmlContent);
        }
    }
}