package imgMnp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Text {
    public static void displayText(Canvas currCanvas, Canvas previewCanvas, int fontSize, String textField, Color color){
        previewCanvas.setOnMouseMoved( moveEvent -> {
            GraphicsContext gcPreivew = previewCanvas.getGraphicsContext2D();
            gcPreivew.setFill(color);
            gcPreivew.setFont(new Font(fontSize));
            gcPreivew.clearRect(0,0,640.0,480.0);
            gcPreivew.fillText(textField,moveEvent.getX(),moveEvent.getY(),200);
            previewCanvas.setOnMousePressed( pressEvent -> {
                GraphicsContext gc = currCanvas.getGraphicsContext2D();
                gc.setFill(color);
                gc.setFont(new Font(fontSize));
                gcPreivew.clearRect(0,0,640.0,480.0);
                gc.fillText(textField,moveEvent.getX(),moveEvent.getY(),200);
                previewCanvas.setOnMousePressed(null);
                previewCanvas.setOnMouseMoved(null);
            });
        });
    }
}
