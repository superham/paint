package imgMnp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;

public class Rectangle{

    /**
     *
     * Draws a solid rectangle. Top left point in rectangle is selected, rectangle is the previewed as mouse is moved,
     * rectangle is drawn after second mouse press.
     *
     * @return              true if shape successfully drawn, false otherwise
     * @param canvas        Canvas used to draw the shape upon
     * @param lineWidth     Line width in pixels
     * @param color         Color of the shape
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     0.8
     * @since       0.5
     */
    public static boolean solidRect(Canvas canvas, Color color, int lineWidth, Canvas previewCanvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gcPreview = previewCanvas.getGraphicsContext2D();

        try{
            previewCanvas.setOnMousePressed( mousePressEvent -> {
            double x1 = mousePressEvent.getX();
            double y1 = mousePressEvent.getY();
            previewCanvas.setOnMouseMoved( mouseDragEvent -> {
                gcPreview.clearRect(0,0,640.0,480.0);
                double topLeftX, topLeftY, width, height;
                double x2 = mouseDragEvent.getX();
                double y2 = mouseDragEvent.getY();

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
                gcPreview.setStroke(color);
                gcPreview.setFill(color);
                gcPreview.setLineWidth(lineWidth);
                gcPreview.fillRect(topLeftX, topLeftY, width, height);
                previewCanvas.setOnMouseReleased( mouseReleaseEvent -> {
                    gc.setStroke(color);
                    gc.setFill(color);
                    gc.setLineWidth(lineWidth);
                    gc.fillRect(topLeftX, topLeftY, width, height);

                    mouseReleaseEvent.consume();
                    previewCanvas.setOnMouseReleased(null);
                    previewCanvas.setOnMouseMoved(null);
                    previewCanvas.setOnMousePressed(null);
                });
            });
           mousePressEvent.consume();
       });
            return true;//process happened
        } catch(Exception e){
            return false;//process failed
        }
    }

    /**
     *
     * Draws an empty rectangle. Top left point in rectangle is selected, rectangle is the previewed as mouse is moved,
     * rectangle is drawn after second mouse press.
     *
     * @return              true if shape successfully drawn, false otherwise
     * @param canvas        Canvas used to draw the shape upon
     * @param lineWidth     Line width in pixels
     * @param color         Color of the shape
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     0.8
     * @since       0.5
     */
    public static boolean emptyRect(Canvas canvas, Color color, int lineWidth,Canvas previewCanvas) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gcPreview = previewCanvas.getGraphicsContext2D();

        try{
            previewCanvas.setOnMousePressed( mousePressEvent -> {
                double x1 = mousePressEvent.getX();
                double y1 = mousePressEvent.getY();
                previewCanvas.setOnMouseMoved( mouseDragEvent -> {
                    gcPreview.clearRect(0,0,640.0,480.0);
                    double topLeftX, topLeftY, width, height;
                    double x2 = mouseDragEvent.getX();
                    double y2 = mouseDragEvent.getY();

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
                    gcPreview.setStroke(color);
                    gcPreview.setFill(color);
                    gcPreview.setLineWidth(lineWidth);
                    gcPreview.strokeRect(topLeftX, topLeftY, width, height);
                    previewCanvas.setOnMouseReleased( mouseReleaseEvent -> {
                        gc.setStroke(color);
                        gc.setFill(color);
                        gc.setLineWidth(lineWidth);
                        gc.strokeRect(topLeftX, topLeftY, width, height);

                        mouseReleaseEvent.consume();
                        previewCanvas.setOnMouseReleased(null);
                        previewCanvas.setOnMouseMoved(null);
                        previewCanvas.setOnMousePressed(null);
                    });
                });
                mousePressEvent.consume();
            });
            return true;
        } catch(Exception e){
            return false;
        }
    }
}
