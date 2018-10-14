package imgMnp;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.ErrorHandle;

public class TwoPointLine{


    /**
     *
     * Draws a two point line onto the canvas passed to it.
     * Handles mouse events and consuming event.
     * Returns a boolean to determine if the action was successful.
     *
     * @return boolean,     TRUE if successfully drew a line
     * @param canvas        Canvas used to draw line upon
     * @param lineWidth     Line width as pixels
     * @param lineColor     Color of the line to be drawn
     */
    public static void line(Canvas canvas, int lineWidth, Color lineColor, Canvas previewCanvas){
        try{ // try to draw the line
            GraphicsContext gc = canvas.getGraphicsContext2D();
            GraphicsContext gcPreview = previewCanvas.getGraphicsContext2D();
            double[] numbers;

            previewCanvas.setOnMousePressed( pressEvent -> { //mouse press event
                System.out.println("Pressing line.");
                previewCanvas.setOnMouseDragged( dragEvent -> {
                    gcPreview.clearRect(0,0,640.0,480.0);
                    System.out.println("Dragging line.");
                    gcPreview.setLineWidth(lineWidth);
                    gcPreview.setStroke(lineColor);
                    gcPreview.strokeLine(pressEvent.getX(), pressEvent.getY(), dragEvent.getX(), dragEvent.getY());
                    previewCanvas.setOnMouseReleased( release2Event -> {
                        System.out.println("Final here.");
                        gc.setLineWidth(lineWidth);
                        gc.setStroke(lineColor);
                        gc.strokeLine(pressEvent.getX(), pressEvent.getY(), release2Event.getX(), release2Event.getY());
                        //numbers = { pressEvent.getX(), pressEvent.getY(), release2Event.getX(),release2Event.getY() };
                        release2Event.consume();
                        previewCanvas.setOnMouseDragged(null);
                        previewCanvas.setOnMouseReleased(null);
                    });
                });
                pressEvent.consume();
                previewCanvas.setOnMousePressed(null);
            });
           // return numbers;
        } catch (Exception e){
            ErrorHandle.error("DRAWING A LINE");
        }
    }
}
