package imgMnp;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;

import main.ErrorHandle;

public class Rectangle{

    public static boolean solidRect(Canvas canvas, Color color, int lineWidth, Canvas previewCanvas){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gcPreview = previewCanvas.getGraphicsContext2D();

        try{
            previewCanvas.setOnMousePressed( mousePressEvent -> {
            double x1 = mousePressEvent.getX();
            double y1 = mousePressEvent.getY();
            previewCanvas.setOnMouseDragged( mouseDragEvent -> {
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
                    previewCanvas.setOnMouseDragged(null);
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

    public static boolean emptyRect(Canvas canvas, Color color, int lineWidth,Canvas previewCanvas) {

        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gcPreview = previewCanvas.getGraphicsContext2D();

        try{
            previewCanvas.setOnMousePressed( mousePressEvent -> {
                double x1 = mousePressEvent.getX();
                double y1 = mousePressEvent.getY();
                previewCanvas.setOnMouseDragged( mouseDragEvent -> {
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
                        previewCanvas.setOnMouseDragged(null);
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
}
