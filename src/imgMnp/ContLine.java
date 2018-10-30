package imgMnp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.ErrorHandle;

public class ContLine{

    /**
     *
     * Draws a continuous line onto a canvas. As the mouse is dragged the line is drawn, line is complete
     * after mouse release.
     *
     * @return              true if line successfully drawn, false otherwise
     * @param canvas        Canvas used to draw line upon
     * @param lineWidth     Line width as pixels
     * @param lineColor     Color of the line to be drawn
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     1.0
     * @since       0.4
     */
   public static boolean line(Canvas canvas, int lineWidth, Color lineColor, Canvas previewCanvas){
       try{
           GraphicsContext gc = canvas.getGraphicsContext2D();

           gc.setLineWidth(lineWidth);
           gc.setStroke(lineColor);

           previewCanvas.setOnMousePressed( pressEvent -> { // first point of new line
               gc.moveTo(pressEvent.getX(), pressEvent.getY());
               gc.stroke();
               pressEvent.consume();
               previewCanvas.setOnMousePressed(null);
           });

           previewCanvas.setOnMouseDragged( dragEvent -> { // line is drawn as mouse is dragged
               gc.lineTo(dragEvent.getX(), dragEvent.getY());
               gc.stroke();
               dragEvent.consume();
           });

           previewCanvas.setOnMouseReleased( releaseEvent -> { // line is complete after mouse release
               previewCanvas.setOnMouseDragged(null);
               previewCanvas.setOnMouseReleased(null);
               releaseEvent.consume();
           });

           return true;
       } catch(Exception e ){
           ErrorHandle.error("DRAWING A CONTINUOUS LINE");
           return false;
       }
   }
}
