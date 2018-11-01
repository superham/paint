package imgMnp;
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
import javafx.stage.FileChooser;
import javafx.util.Pair;
import javafx.scene.paint.Color;
import javafx.scene.image.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Stack;

/**
 * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
 * @version     1.0
 * @since       0.7
 */

public class Effects{

    public static void cut(Canvas canvas, Canvas previewCanvas){ //TODO fix cutting up and to the right

        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gcP = previewCanvas.getGraphicsContext2D();

        previewCanvas.setOnMousePressed( pressEvent -> { //first position to cut
            double x1 = pressEvent.getX();
            double y1 = pressEvent.getY();
            previewCanvas.setOnMouseMoved( moveEvent -> {
                gcP.clearRect(0,0,640.0,480.0);
                double topLeftXp, topLeftYp, widthp, heightp;
                double x2preview = moveEvent.getX();
                double y2preview = moveEvent.getY();

                if (x1 < x2preview) {
                    topLeftXp = x1;
                    widthp = x2preview - x1;
                } else {
                    topLeftXp = x2preview;
                    widthp = x1 - x2preview;
                }
                if (y1 < y2preview) {
                    topLeftYp = y1;
                    heightp = y2preview - y1;
                } else {
                    topLeftYp = y2preview;
                    heightp = y1 - y2preview;
                }
                gcP.setStroke(Color.BLACK);
                gcP.setFill(Color.BLACK);
                gcP.setLineWidth(2);
                gcP.strokeRect(topLeftXp, topLeftYp, widthp, heightp);

                previewCanvas.setOnMousePressed( releaseEvent -> {
                    previewCanvas.setOnMouseMoved(null);
                    double topLeftX, topLeftY, width, height;
                    double x2 = releaseEvent.getX();
                    double y2 = releaseEvent.getY();
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

                    WritableImage writableImage = new WritableImage( (int) width, (int) height ); //first param in snapshot method call
                    SnapshotParameters snapParam = new SnapshotParameters();//TODO FIX THIS
                    snapParam.setViewport(new Rectangle2D(4+topLeftX,46+topLeftY,width,height));//TODO FIX 4 AND 46
                    canvas.snapshot(snapParam,writableImage);

                    gc.setFill(Color.GAINSBORO);
                    gc.fillRect(topLeftX,topLeftY,width,height);


                    releaseEvent.consume();
                    previewCanvas.setOnMouseReleased(null);
                    previewCanvas.setOnMousePressed(null);
                    previewCanvas.setOnMouseMoved(null);
                    gcP.clearRect(0,0,640.0,480.0);

                    previewCanvas.setOnMouseMoved( movePreviewEvent -> {
                        gcP.clearRect(0,0,640.0,480.0);
                        System.out.println("Pasteing Cut.");
                        double dx = movePreviewEvent.getX();
                        double dy = movePreviewEvent.getY();

                        gcP.drawImage(writableImage,dx,dy,width,height);

                        previewCanvas.setOnMousePressed( finalPressEvent -> {
                            double dx1=finalPressEvent.getX();
                            double dy1=finalPressEvent.getY();
                            gcP.clearRect(0,0,640.0,480.0);
                            gc.drawImage(writableImage,dx1,dy1,width,height);
                            previewCanvas.setOnMousePressed(null);
                            previewCanvas.setOnMouseMoved(null);
                        });

                    });
                });
            });
        });
    }

    public static void copy(Canvas canvas, Canvas previewCanvas){ //TODO fix cutting up and to the right

        GraphicsContext gc = canvas.getGraphicsContext2D();
        GraphicsContext gcP = previewCanvas.getGraphicsContext2D();

        previewCanvas.setOnMousePressed( pressEvent -> { //first position to cut
            double x1 = pressEvent.getX();
            double y1 = pressEvent.getY();
            previewCanvas.setOnMouseMoved( moveEvent -> {
                gcP.clearRect(0,0,640.0,480.0);
                double topLeftXp, topLeftYp, widthp, heightp;
                double x2preview = moveEvent.getX();
                double y2preview = moveEvent.getY();

                if (x1 < x2preview) {
                    topLeftXp = x1;
                    widthp = x2preview - x1;
                } else {
                    topLeftXp = x2preview;
                    widthp = x1 - x2preview;
                }
                if (y1 < y2preview) {
                    topLeftYp = y1;
                    heightp = y2preview - y1;
                } else {
                    topLeftYp = y2preview;
                    heightp = y1 - y2preview;
                }
                gcP.setStroke(Color.BLACK);
                gcP.setFill(Color.BLACK);
                gcP.setLineWidth(2);
                gcP.strokeRect(topLeftXp, topLeftYp, widthp, heightp);

                previewCanvas.setOnMousePressed( releaseEvent -> {
                    previewCanvas.setOnMouseMoved(null);
                    double topLeftX, topLeftY, width, height;
                    double x2 = releaseEvent.getX();
                    double y2 = releaseEvent.getY();
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

                    WritableImage writableImage = new WritableImage( (int) width, (int) height ); //first param in snapshot method call
                    SnapshotParameters snapParam = new SnapshotParameters();//TODO FIX THIS
                    snapParam.setViewport(new Rectangle2D(4+topLeftX,46+topLeftY,width,height));//TODO FIX 4 AND 46
                    canvas.snapshot(snapParam,writableImage);


                    releaseEvent.consume();
                    previewCanvas.setOnMouseReleased(null);
                    previewCanvas.setOnMousePressed(null);
                    previewCanvas.setOnMouseMoved(null);
                    gcP.clearRect(0,0,640.0,480.0);

                    previewCanvas.setOnMouseMoved( movePreviewEvent -> {
                        gcP.clearRect(0,0,640.0,480.0);
                        System.out.println("Pasteing Cut.");
                        double dx = movePreviewEvent.getX();
                        double dy = movePreviewEvent.getY();

                        gcP.drawImage(writableImage,dx,dy,width,height);

                        previewCanvas.setOnMousePressed( finalPressEvent -> {
                            double dx1=finalPressEvent.getX();
                            double dy1=finalPressEvent.getY();
                            gcP.clearRect(0,0,640.0,480.0);
                            gc.drawImage(writableImage,dx1,dy1,width,height);
                            previewCanvas.setOnMousePressed(null);
                            previewCanvas.setOnMouseMoved(null);
                        });

                    });
                });
            });
        });
    }

    public static void colorPicker(Canvas previewCanvas, Canvas currCanvas, ColorPicker colorpickerthing){

        WritableImage writableImage = new WritableImage( (int) 640, (int) 480 ); //first param in snapshot method call
        SnapshotParameters snapParam = new SnapshotParameters();//TODO FIX THIS
        snapParam.setViewport(new Rectangle2D(4,46,640,480));//TODO FIX 4 AND 46
        Image image = currCanvas.snapshot(snapParam,writableImage);

        previewCanvas.setOnMousePressed( pressEvent -> {
           GraphicsContext gcP = previewCanvas.getGraphicsContext2D();
            PixelReader pixelReader = image.getPixelReader();
            int x = (int) pressEvent.getX();
            int y = (int) pressEvent.getY();
            colorpickerthing.setValue(pixelReader.getColor(x,y));
        });
    }
}
