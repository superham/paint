package imgMnp;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.ErrorHandle;

public class CustomShape {

    public static boolean line(Canvas canvas, int lineWidth, Color lineColor, Canvas previewCanvas){ //TODO fix
        try{
            GraphicsContext gc = canvas.getGraphicsContext2D();
            GraphicsContext gcPreview = previewCanvas.getGraphicsContext2D();

            gc.setLineWidth(lineWidth);
            gc.setStroke(lineColor);
            gc.setFill(lineColor);

            previewCanvas.setOnMousePressed( pressEvent -> { // mouse pressed event
                gc.moveTo(pressEvent.getX(), pressEvent.getY());
                gc.stroke();
                gc.beginPath();
                pressEvent.consume();
                previewCanvas.setOnMousePressed(null);
            });

            previewCanvas.setOnMouseDragged( dragEvent -> { // mouse drag event
                gc.lineTo(dragEvent.getX(), dragEvent.getY());
                gc.stroke();
                dragEvent.consume();
            });

            previewCanvas.setOnMouseReleased( releaseEvent -> { // mouse release event
                gc.closePath();
                gc.fill();
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

    public static boolean triangle(Canvas canvas, int lineWidth, Color lineColor, Canvas previewCanvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gcPreview = previewCanvas.getGraphicsContext2D();

        gc.setLineWidth(lineWidth);
        gc.setStroke(lineColor);
        gc.setFill(lineColor);
        gcPreview.setLineWidth(lineWidth);
        gcPreview.setStroke(lineColor);
        gcPreview.setFill(lineColor);

        previewCanvas.setOnMousePressed( pressEvent -> {
            double x1 = pressEvent.getX();
            double y1 = pressEvent.getY();
            previewCanvas.setOnMouseMoved( moveEvent -> {
                gcPreview.clearRect(0,0,640,480);
                double x2 = moveEvent.getX();
                double y2 = moveEvent.getY();

                double leftX,rightX,leftY,rightY,middleOfBaseX,heightY;//sets orentation for x,y
                if(x1<x2){ leftX = x1; leftY = y1; rightX = x2; rightY = y2;
                }else {leftX = x2; leftY=y2; rightX = x1; rightY=y1;}

                heightY = (rightX-leftX);

                double x3 = rightX;
                double y3 = rightY+heightY;

                gcPreview.strokeLine(leftX,leftY,rightX,rightY);//bottom left to bottom right
                gcPreview.strokeLine(rightX,rightY,x3,y3);//bottom right to top right
                gcPreview.strokeLine(x3,y3,leftX,leftY);
                previewCanvas.setOnMousePressed( finalPressEvent -> {
                    gcPreview.clearRect(0,0,640,480);
                    double x21 = finalPressEvent.getX();
                    double y21 = finalPressEvent.getY();

                    double leftX1,rightX1,leftY1,rightY1,middleOfBaseX1,heightY1;//sets orentation for x,y
                    if(x1<x21){ leftX1 = x1; leftY1 = y1; rightX1 = x21; rightY1 = y21;
                    }else {leftX1 = x21; leftY1=y21; rightX1 = x1; rightY1=y1;}

                    heightY1 = (rightX1-leftX1);

                    double x31 = rightX1;
                    double y31 = rightY1+heightY1;

                    gc.strokeLine(leftX1,leftY1,rightX1,rightY1);//bottom left to bottom right
                    gc.strokeLine(rightX1,rightY1,x31,y31);//bottom right to top right
                    gc.strokeLine(x31,y31,leftX1,leftY1);
                    previewCanvas.setOnMousePressed(null);
                    previewCanvas.setOnMouseMoved(null);
                });

            });
        });
        return true;
    }

    public static boolean triangleSolid(Canvas canvas, int lineWidth, Color lineColor, Canvas previewCanvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gcPreview = previewCanvas.getGraphicsContext2D();

        gc.setLineWidth(lineWidth);
        gc.setStroke(lineColor);
        gc.setFill(lineColor);
        gcPreview.setLineWidth(lineWidth);
        gcPreview.setStroke(lineColor);
        gcPreview.setFill(lineColor);

        previewCanvas.setOnMousePressed( pressEvent -> {
            gcPreview.beginPath();
            gc.beginPath();
            double x1 = pressEvent.getX();
            double y1 = pressEvent.getY();
            previewCanvas.setOnMouseMoved( moveEvent -> {
                gcPreview.clearRect(0,0,640,480);
                double x2 = moveEvent.getX();
                double y2 = moveEvent.getY();

                double leftX,rightX,leftY,rightY,middleOfBaseX,heightY;//sets orentation for x,y
                if(x1<x2){ leftX = x1; leftY = y1; rightX = x2; rightY = y2;
                }else {leftX = x2; leftY=y2; rightX = x1; rightY=y1;}

                heightY = (rightX-leftX);

                double x3 = rightX;
                double y3 = rightY+heightY;

                gcPreview.strokeLine(leftX,leftY,rightX,rightY);//bottom left to bottom right
                gcPreview.strokeLine(rightX,rightY,x3,y3);//bottom right to top right
                gcPreview.strokeLine(x3,y3,leftX,leftY);
                gcPreview.closePath();
                previewCanvas.setOnMousePressed( finalPressEvent -> {
                    gcPreview.clearRect(0,0,640,480);
                    double x21 = finalPressEvent.getX();
                    double y21 = finalPressEvent.getY();

                    double leftX1,rightX1,leftY1,rightY1,middleOfBaseX1,heightY1;//sets orentation for x,y
                    if(x1<x21){ leftX1 = x1; leftY1 = y1; rightX1 = x21; rightY1 = y21;
                    }else {leftX1 = x21; leftY1=y21; rightX1 = x1; rightY1=y1;}

                    heightY1 = (rightX1-leftX1);

                    double x31 = rightX1;
                    double y31 = rightY1+heightY1;

                    gc.strokeLine(leftX1,leftY1,rightX1,rightY1);//bottom left to bottom right
                    gc.strokeLine(rightX1,rightY1,x31,y31);//bottom right to top right
                    gc.strokeLine(x31,y31,leftX1,leftY1);
                    gc.closePath();
                    previewCanvas.setOnMousePressed(null);
                    previewCanvas.setOnMouseMoved(null);
                });

            });
        });
        return true;
    }
}
