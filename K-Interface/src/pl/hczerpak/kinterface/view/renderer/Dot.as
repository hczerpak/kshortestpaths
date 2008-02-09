package pl.hczerpak.kinterface.view.renderer
{
    import flash.display.GradientType;
    import flash.display.InterpolationMethod;
    import flash.display.SpreadMethod;
    
    import mx.core.UIComponent;

    public class Dot extends UIComponent
    {
        public function set color(i: int): void {
            _color = i;
            invalidateDisplayList(); 
        }
        
        private var _color: int;
        
        override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
            graphics.clear();
            graphics.drawCircle(unscaledWidth / 2, unscaledHeight / 2, unscaledHeight / 2);
            graphics.endFill();
        }
    }
}

