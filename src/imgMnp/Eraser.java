package imgMnp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.ErrorHandle;

public class Eraser {
    public static boolean line(Canvas canvas, int lineWidth, Canvas previewCanvas){ //TODO fix
        try{
            GraphicsContext gc = canvas.getGraphicsContext2D();
            GraphicsContext gcPreview = previewCanvas.getGraphicsContext2D();

            gc.setLineWidth(lineWidth);
            gc.setStroke(Color.GAINSBORO);

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
