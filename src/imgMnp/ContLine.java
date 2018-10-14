package imgMnp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.ErrorHandle;

public class ContLine{

    /**
     *
     * Draws a continous line onto the canvas passed to it.
     * Handles mouse evetns and consuming event.
     * Returns a boolean to determine if the action was successful.
     *
     * @return boolean,     TRUE if successfully drew a line
     * @param canvas        Canvas used to draw line upon
     * @param lineWidth     Line width as pixels
     * @param lineColor     Color of the line to be drawn
     */
   public static boolean line(Canvas canvas, int lineWidth, Color lineColor, Canvas previewCanvas){ //TODO fix
       try{
           GraphicsContext gc = canvas.getGraphicsContext2D();
           GraphicsContext gcPreview = previewCanvas.getGraphicsContext2D();

           gc.setLineWidth(lineWidth);
           gc.setStroke(lineColor);

           previewCanvas.setOnMousePressed( pressEvent -> { // mouse pressed event
               gc.moveTo(pressEvent.getX(), pressEvent.getY());
               gc.stroke();
               pressEvent.consume();
               previewCanvas.setOnMousePressed(null);
           });

           previewCanvas.setOnMouseDragged( dragEvent -> { // mouse drag event
               gc.lineTo(dragEvent.getX(), dragEvent.getY());
               gc.stroke();
               dragEvent.consume();
           });

           previewCanvas.setOnMouseReleased( releaseEvent -> { // mouse release event
               previewCanvas.setOnMouseDragged(null);
               previewCanvas.setOnMouseReleased(null);
               releaseEvent.consume();
           });

           return true;
       } catch(Exception e ){
           ErrorHandle.error("DRAWING A CONTINOUS LINE");
           return false;
       }
   }
}
