package pl.hczerpak.kinterface.renderer
{
    import com.adobe.flex.extras.controls.springgraph.Graph;
    import com.adobe.flex.extras.controls.springgraph.IEdgeRenderer;
    import com.adobe.flex.extras.controls.springgraph.Item;
    
    import flash.display.Graphics;
    
    import mx.core.IDataRenderer;
    import mx.core.UIComponent;

    public class DirectedEdgeRenderer implements IEdgeRenderer {
        
        public function draw(graphics:Graphics, fromView:UIComponent, toView:UIComponent, fromX:int, fromY:int, toX:int, toY:int, graph:Graph):Boolean {
            if (!(graphics && fromView && toView && graph))
                return false;
            
            var fromItem: Item = (fromView as IDataRenderer).data as Item;
            var toItem: Item = (toView as IDataRenderer).data as Item;
            var linkData: Object = graph.getLinkData(fromItem, toItem);
            var alpha: Number = 1.0;
            var thickness: int = 1;
            var color: uint = 0x000000;
            
            if((linkData != null) && (linkData.hasOwnProperty("settings"))) {
                var settings: Object = linkData.settings;
                alpha = settings.alpha;
                thickness = settings.thickness;
                color = settings.color;
            }

            graphics.beginFill(0);
            graphics.lineStyle(thickness, 0xFF0000, alpha);
            graphics.moveTo(fromX, fromY);
            graphics.lineTo(fromX + (toX - fromX)/10, fromY + (toY - fromY)/10);
            
            graphics.lineStyle(thickness, color);
            graphics.moveTo(fromX + (toX - fromX)/10, fromY + (toY - fromY)/10);
            graphics.lineTo(toX - (toX - fromX)/10, toX - (toY - fromY)/10);
            
            graphics.lineStyle(thickness, 0x00FF00);
            graphics.moveTo(toX - (toX - fromX)/10, toX - (toY - fromY)/10);
            graphics.lineTo(toX, toY);
            graphics.endFill();
            
            return true;
        }
    }
}