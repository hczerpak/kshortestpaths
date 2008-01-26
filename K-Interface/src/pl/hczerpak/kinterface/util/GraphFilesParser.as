package pl.hczerpak.kinterface.util
{
    public class GraphFilesParser
    {
        public static function parseDimacs(dimacsFileContent : String) : XML {
            return null;
        }
        
        public static function parseGXML(xmlContent : String) : XML {
            return new XML(xmlContent);
        }
    }
}