package imgMnp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Text {

    /**
     *
     * Displays text passed to it onto a canvas. Can alter color and font size.
     *
     * @param currCanvas        Canvas used to draw the final text upon
     * @param previewCanvas     Canvas used to preview text upon
     * @param fontSize          Font size of the text
     * @param textField         Text to be displayed
     * @param color             Color of the text
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     1.0
     * @since       0.9
     */
    public static void displayText(Canvas currCanvas, Canvas previewCanvas, int fontSize, String textField, Color color, boolean boldState){
       if(boldState){
           previewCanvas.setOnMouseMoved( moveEvent -> {
               GraphicsContext gcPreivew = previewCanvas.getGraphicsContext2D();
              // gcPreivew.setFill(color);
               gcPreivew.setFont(new Font(fontSize));
               gcPreivew.clearRect(0,0,640.0,480.0);
               gcPreivew.setStroke(color);
               gcPreivew.strokeText(textField,moveEvent.getX(),moveEvent.getY(),200);
               previewCanvas.setOnMousePressed( pressEvent -> {
                   GraphicsContext gc = currCanvas.getGraphicsContext2D();
                   //gc.setFill(color);
                   gc.setFont(new Font(fontSize));
                   gcPreivew.clearRect(0,0,640.0,480.0);
                   gc.setStroke(color);
                   gc.strokeText(textField,moveEvent.getX(),moveEvent.getY(),200);
                   previewCanvas.setOnMousePressed(null);
                   previewCanvas.setOnMouseMoved(null);
               });
           });
       }else{
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
}
