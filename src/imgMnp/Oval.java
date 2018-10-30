package imgMnp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.ErrorHandle;

/**
 * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
 * @version     1.0
 * @since       0.5
 */

public class Oval{

    /**
     *
     * Draws a solid circle on a canvas.
     *
     * @return boolean,     TRUE if successfully drew a line
     * @param canvas        Canvas used to draw oval upon
     * @param lineWidth     Line width as pixels. O
     * @param color         Color of the line to be drawn
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     0.8
     * @since       0.5
     */
    public static boolean solidCircle(Canvas canvas, int lineWidth, Color color, Canvas previewCanvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gcPreview = previewCanvas.getGraphicsContext2D();
        try{
            previewCanvas.setOnMousePressed( pressEvent -> {
                double x1 = pressEvent.getX();
                double y1 = pressEvent.getY();
                previewCanvas.setOnMouseDragged( dragEvent -> {
                    gcPreview.clearRect(0,0,640.0,480.0);
                    double x2 = dragEvent.getX();
                    double y2 = dragEvent.getY();
                    drawOval(x1,y1,x2,y2,gcPreview,true,color,lineWidth);
                    previewCanvas.setOnMouseReleased(releaseEvent -> {
                        drawOval(x1,y1,x2,y2,gc,true,color,lineWidth);
                        previewCanvas.setOnMouseDragged(null);
                        previewCanvas.setOnMouseReleased(null);
                        releaseEvent.consume();
                    });
                    dragEvent.consume();
                });

                pressEvent.consume();
                previewCanvas.setOnMousePressed(null);
            });
            return true;
        } catch(Exception e){
            ErrorHandle.error("SOLID CIRCLE");
            return false;
        }
    }

    /**
     *
     * Draws a empty circle on the canvas passed to it
     * Handles mouse evetns and consuming event.
     * Returns a boolean to determine if the action was successful.
     *
     * @return boolean,     TRUE if successfully drew a line
     * @param canvas        Canvas used to draw line upon
     * @param lineWidth     Line width of the border as pixels
     * @param color         Color of the line to be drawn
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     0.8
     * @since       0.5
     */
    public static boolean emptyCircle(Canvas canvas, int lineWidth, Color color, Canvas previewCanvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gcPreview = previewCanvas.getGraphicsContext2D();
        try{
            previewCanvas.setOnMousePressed( pressEvent -> {
                double x1 = pressEvent.getX();
                double y1 = pressEvent.getY();
                previewCanvas.setOnMouseDragged( dragEvent -> {
                    gcPreview.clearRect(0,0,640.0,480.0);
                    double x2 = dragEvent.getX();
                    double y2 = dragEvent.getY();
                    drawOval(x1,y1,x2,y2,gcPreview,false,color,lineWidth);
                    previewCanvas.setOnMouseReleased(releaseEvent -> {
                        gcPreview.clearRect(0,0,640.0,480.0);
                        System.out.println("Drawing oval on real canvas now.");
                        drawOval(x1,y1,x2,y2,gc,false,color,lineWidth);
                        previewCanvas.setOnMouseDragged(null);
                        previewCanvas.setOnMouseReleased(null);
                        releaseEvent.consume();
                    });
                    dragEvent.consume();
                });

                pressEvent.consume();
                previewCanvas.setOnMousePressed(null);
            });
            return true;
        } catch(Exception e){
            ErrorHandle.error("SOLID CIRCLE");
            return false;
        }
    }


    public static boolean drawOval(double x1, double y1, double x2, double y2, GraphicsContext gc, boolean fill, Color color, int lineWidth) {


        try {
            //Four cases.
            //down & right
            //down & left
            //up & right
            //up & left
            //double topRight,topLeft,bottomRight,bottomLeft;
            System.out.println(x1 + "," + y1 + "," + x2 + "," + y2);
            double topLeftX, topLeftY, width, height;
            if (x1 < x2) {
                topLeftX = x1;
                width = x2 - x1;
            } else {
                topLeftX = x2;
                width = x1 - x2;
            }
            if (y1 < y2) {
                topLeftY = y1;
                height = y2 - y1;
            } else {
                topLeftY = y2;
                height = y1 - y2;
            }
            System.out.println("Top Left X:" + topLeftX);
            System.out.println("Top Left Y:" + topLeftY);
            System.out.println("Width:" + width);
            System.out.println("Height:" + height);
            gc.setStroke(color);
            gc.setFill(color);
            gc.setLineWidth(lineWidth);
            if (fill) {
                gc.fillOval(topLeftX, topLeftY, width, height);
            } else {
                gc.strokeOval(topLeftX, topLeftY, width, height);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
