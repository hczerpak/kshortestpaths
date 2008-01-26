package pl.hczerpak.kinterface.renderer
{
    import com.adobe.flex.extras.controls.springgraph.Graph;
    import com.adobe.flex.extras.controls.springgraph.IEdgeRenderer;
    import com.adobe.flex.extras.controls.springgraph.Item;
    
    import flash.display.Graphics;
    
    import mx.core.IDataRenderer;
    import mx.core.UIComponent;

    public class ArrowEdgeRenderer implements IEdgeRenderer {
        public function draw(g:Graphics, fromView:UIComponent, toView:UIComponent, fromX:int, fromY:int, toX:int, toY:int, graph:Graph):Boolean {
            if (!(g && fromView && toView && graph))
                return false;
            
            var fromItem: Item = (f as IDataRenderer).data as Item;
            var toItem: Item = (t as IDataRenderer).data as Item;
            var linkData: Object = graph.getLinkData(fromItem, toItem);
            var alpha: Number = 1.0;
            var thickness: int = 1;
            if((linkData != null) && (linkData.hasOwnProperty("settings"))) {
                var settings: Object = linkData.settings;
                alpha = settings.alpha;
                thickness = settings.thickness;
                color = settings.color;
            }

            graphics.lineStyle(thickness,color,alpha);
            graphics.beginFill(0);
            graphics.moveTo(fromX, fromY);
            graphics.lineTo(toX, toY);
            graphics.endFill();
            
            var triangleHeight:uint = 100;
            graphics.beginFill(0xFF0000);
            graphics.moveTo(triangleHeight/2, 0);
            graphics.lineTo(triangleHeight, triangleHeight);
            graphics.lineTo(0, triangleHeight);
            graphics.lineTo(triangleHeight/2, 0);
        }
    }
}