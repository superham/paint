package imgMnp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.ErrorHandle;

public class TwoPointLine{


    /**
     *
     * Draws a two point line onto a canvas of varying width and color. First point is selected when the mouse is
     * pressed, line then previews as mouse is dragged on a preivew Canvas, line is finally drawn onto the canvas when
     * the mouse is pressed for a second time.
     *
     * @return              true if line successfully drawn, false otherwise
     * @param canvas        Canvas used to draw the line upon
     * @param lineWidth     Line width in pixels
     * @param lineColor     Color of the line to be drawn
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     0.7
     * @since       0.4
     */
    public static boolean line(Canvas canvas, int lineWidth, Color lineColor, Canvas previewCanvas){
        try{ // try to draw the line
            GraphicsContext gc = canvas.getGraphicsContext2D();
            GraphicsContext gcPreview = previewCanvas.getGraphicsContext2D();

            previewCanvas.setOnMousePressed( pressEvent -> { // first point of the 2d line

                previewCanvas.setOnMouseMoved( dragEvent -> { // line is previewed as the mouse is dragged
                    gcPreview.clearRect(0,0,640.0,480.0);
                    gcPreview.setLineWidth(lineWidth);
                    gcPreview.setStroke(lineColor);
                    gcPreview.strokeLine(pressEvent.getX(), pressEvent.getY(), dragEvent.getX(), dragEvent.getY());

                    previewCanvas.setOnMousePressed( release2Event -> { // second point of line. used to complete line
                        gc.setLineWidth(lineWidth);
                        gc.setStroke(lineColor);
                        gc.strokeLine(pressEvent.getX(), pressEvent.getY(), release2Event.getX(), release2Event.getY());
                        release2Event.consume();
                        previewCanvas.setOnMouseMoved(null);
                        previewCanvas.setOnMousePressed(null);
                    });
                });

                pressEvent.consume();
                previewCanvas.setOnMousePressed(null);
            });
            return true;
        } catch (Exception e){
            ErrorHandle.error("DRAWING A LINE");
            return false;
        }
    }
}
