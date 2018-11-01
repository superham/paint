/*
 *@author: Alex Kaariainen
 *FXMLDocumentController.java
 *Version 0.3
 *Description: Logic behind FXML elements
 */

package main;
import imgMnp.*;
import imgMnp.Rectangle;
import javafx.scene.control.CheckBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import popup.Popup;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;

public class FXMLDocumentController implements Initializable {
    //------------------------------ Start of Private Class Members ------------------------------
    private static boolean hasBeenAlt=false;        //tracks if the current file has been altered since last save
    @FXML
    ColorPicker lineColorPicker;                    //color picker objects for line color and fill color
    @FXML
    Canvas currCanvas,previewCanvas;                //reference to the current canvas
    @FXML
    MenuItem menuBarSave,menuBarSaveAs,menuBarCopy,menuBarCut,menuBarTriangle
            ,menuBarTriangleSolid,menuBarCustomShape,
            menuBarSolidRectangle,menuBarOutlineRectangle,menuBarSolidOval,
            menuBarEmptyOval,menuBarStraightLine,menuBarContLine,menuBarUndo,
            menuBarRedo,menuBarColorDropper,menuBarEraser,menuBarText;
    @FXML
    Slider fontSlider,lineSlider;
    @FXML
    private File currentFile = null;
    @FXML
    private Image currImage;

    @FXML
    CheckBox boldBox;

    private String fileExt;                         //keeps track of the file extension for internal use and compatilbity stuff
    boolean hasUndoOccured=false;
    boolean firstRedo=false;
    private int fontSize=12;

    private Stack<Image> undoStack = new Stack<>();
    private Stack<Image> redoStack = new Stack<>();

    @FXML
    Label layerCount;
    int layercount=0;

    //------------------------------ End of Private Class Members ------------------------------



    //------------------------------ Start of Private Class Methods ------------------------------

    @FXML
    private void handleUndo(final ActionEvent event){
        try {
            if(layercount>1) {
                hasUndoOccured = false;
                GraphicsContext gc = previewCanvas.getGraphicsContext2D();
                gc.clearRect(0, 0, 640, 480);
                Image undo = undoStack.pop();
                currCanvas.getGraphicsContext2D().drawImage(undo, 3, 11, 640.0, 480.0); //set up image Canvas layer
                redoStack.push(undo);
                hasUndoOccured = true;
                Paint.mainStage.setTitle(currentFile.getName() + "*");
                layercount--;
                layerCount.setText("Current Layer: " + layercount);
                System.out.println("Undo occured.");
            }
        } catch(Exception e){
            Popup.popupError("Cannot Undo");

        }
    }


    @FXML
    private void handleFont(final ActionEvent event){ fontSize=Popup.popupFontSize();}

    @FXML
    private void handleRedo(final ActionEvent event){
        try {
            GraphicsContext gc = previewCanvas.getGraphicsContext2D();
            gc.clearRect(0,0,640,480);
            Image redo = redoStack.pop();
            undoStack.push(redo); //move top of undo to redo
            currCanvas.getGraphicsContext2D().drawImage(redo, 3, 11, 640.0, 480.0); //set up image Canvas layer
            Paint.mainStage.setTitle(currentFile.getName() + "*");
            layercount++;
            layerCount.setText("Current Layer: " + layercount);
            System.out.println("Redo occured.");
        } catch (Exception e){
           Popup.popupError("Cannot Redo");
        }
    }

    @FXML
    private void handleCopy(final ActionEvent event){
        hasBeenAlt=true;
        Effects.copy(currCanvas,previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    /**
     *    Handles 'About' menu action. Displays information about the program.
     */
    @FXML
    private void handleAboutAction(final ActionEvent event)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Alex's Pain(t) Version 0.2");
        alert.setContentText("Alex's Pain(t) program is inspired by GIMP and Microsoft Paint. ");
        alert.showAndWait();
    }

    @FXML
    private void handleEraser(final ActionEvent event){
        Eraser.line(currCanvas, (int) fontSlider.getValue(),previewCanvas);
        hasBeenAlt=true;
        pushToStack(undoStack);
        redoStack.clear();
        //undoStack.push(currCanvas);
        //redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleText(final ActionEvent event){
        String textField = Popup.popupText();
        String correctText = textField.substring(9,textField.length()-1);
        Text.displayText(currCanvas,previewCanvas,(int) fontSlider.getValue(),correctText,lineColorPicker.getValue(),boldBox.isSelected());
        hasBeenAlt=true;
       pushToStack(undoStack);
        redoStack.clear();
        //undoStack.push(currCanvas);
        //redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleCut(final ActionEvent event){
        hasBeenAlt=true;
        Effects.cut(currCanvas,previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        //undoStack.push(currCanvas);
        //redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleTriangle(final ActionEvent event){
        hasBeenAlt=true;
        CustomShape.triangle(currCanvas,(int) lineSlider.getValue(),lineColorPicker.getValue(),previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleFillRectangle(final ActionEvent event){
        hasBeenAlt=true;
        Rectangle.solidRect(currCanvas,lineColorPicker.getValue(),(int) lineSlider.getValue(),previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleOutlineRectangle(final ActionEvent event){
        hasBeenAlt=true;
        Rectangle.emptyRect(currCanvas,lineColorPicker.getValue(),(int) lineSlider.getValue(),previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }


    @FXML
    private void handleFillCircle(final ActionEvent event){
        hasBeenAlt=true;
        Oval.solidCircle(currCanvas,(int) lineSlider.getValue(),lineColorPicker.getValue(),previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleOutlineCircle(final ActionEvent event){
        hasBeenAlt=true;
        Oval.emptyCircle(currCanvas,(int) lineSlider.getValue(),lineColorPicker.getValue(),previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleCustomShape(final ActionEvent event){
        hasBeenAlt=true;
        CustomShape.line(currCanvas,(int) lineSlider.getValue(),lineColorPicker.getValue(),previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleColorDropper(final ActionEvent event){
        Effects.colorPicker(previewCanvas,currCanvas,lineColorPicker);
    }

    /**
     *   Handles the 'Exit' menu action
     */
    @FXML
    private void handleExitAction(final ActionEvent event){ closeProgram(); }

    /**
     * Handles drawing a continous line when called from menubar
     *
     * @return void
     * @param event        ActionEvent passed when button is pressed
     */
    @FXML
    private void handleCTLine(final ActionEvent event) {
        ContLine.line(currCanvas, (int) lineSlider.getValue(), lineColorPicker.getValue(),previewCanvas); // if can successfully draw a line
        hasBeenAlt=true;
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    /**
     * Handles drawing a two point line when called from menubar
     *
     * @return void
     * @param event        ActionEvent passed when button is pressed
     */
    @FXML
    private void handleTwoPointLine(final ActionEvent event){
        TwoPointLine.line(currCanvas, (int) lineSlider.getValue(), lineColorPicker.getValue(),previewCanvas);
        hasBeenAlt=true;
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleTriangleSolid(final ActionEvent event){
        CustomShape.triangleSolid(currCanvas, (int) lineSlider.getValue(), lineColorPicker.getValue(),previewCanvas);
        hasBeenAlt=true;
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    /**
     *
     * Handles the action for the "Open file" button. Passes selected file(.png, .jpeg, .bmp)
     * to openSelectedFile(File) method.
     *
     * @param event        ActionEvent created by "Open File" button
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     1.0
     * @since       0.1
     */
    @FXML
    private void handleOpenFileAction(final ActionEvent event) {
        boolean cond=true;

        if(hasBeenAlt){
            cond = Popup.popupConfirmation("Warning: You are about to open a new file without " +
                    "saving prior changes, you will lose progress.");
        }

        if(cond){
            resetAppState();
            //File open
            try {
                FileChooser fileChooser = new FileChooser();//gets file from user
                fileChooser.setTitle("Open File");
                fileChooser.getExtensionFilters().addAll( //forces user to choose 'acceptable' file types
                        new FileChooser.ExtensionFilter("PNG", "*.png"),
                        new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                        new FileChooser.ExtensionFilter("BMP", "*.bmp"));
                currentFile = fileChooser.showOpenDialog(Paint.mainStage);
                fileExt = fileChooser.getSelectedExtensionFilter().getDescription();
                if(!(openSelectedFile(currentFile))){Popup.popupError("Failed to open selected file");}

            }
            catch(Exception e){
                System.out.println("ERROR:FILE SELECTION WRAPPER FAILURE.");
            }
        }
    }

    /**
     *    Handles TBA features.
     */
    @FXML
    private void handleTBA(final ActionEvent event){
        Popup.popupAlert("This feature is planned to be implemented soon. :)");
    }

    /**
     *    Handles 'save' menu action.
     *    Saves the selected file to it's original location.
     */
    @FXML
    private void handleSaveAction(final ActionEvent event){
        try{
            if(currentFile != null){//verify that the image is selected and not null
                BufferedImage buffImage = SwingFXUtils.fromFXImage(currImage, null);
                ImageIO.write(buffImage,fileExt, currentFile);
                Popup.popupAlert("File saved");
                Paint.mainStage.setTitle(currentFile.getName());
                //currCanvas.getGraphicsContext2D().drawImage(undoStack.peek(),0,0,640.0,480.0);
                undoStack.clear();
                redoStack.clear();
            }else{
                handleAccessingNullFile();
            }
        }catch(Exception e){
            System.out.println("ERROR: FILE SAVE FALIURE.");
        }
        hasBeenAlt=false;
    }

    /**
     *    Handles 'Save As' menu action.
     *    Saves the selected file to a user specified location.
     */
    @FXML
    private void handleSaveAsAction(final ActionEvent event){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                    new FileChooser.ExtensionFilter("BMP", "*.bmp"));
            File dest = fileChooser.showSaveDialog(Paint.mainStage);//gets the file path from the user
            //Files.copy(currentFile.toPath(),dest.toPath());
            currentFile = dest;//broken
            Popup.popupAlert("File saved in: " + currentFile.getAbsolutePath());
            System.out.println(currentFile.getAbsolutePath());
            Paint.mainStage.setTitle(currentFile.getName());
            undoStack.clear();
            redoStack.clear();
            hasBeenAlt = false;
        } catch(Exception e){
            Popup.popupError("Error during 'Save As' action.");
        }
    }

    /**
     *    Handles accessing an unknown file type
     */
    private void handleAccessingNullFile(){
        Popup.popupAlert("No file selected or file has not been modified.");
    }

    /**
     *   Function to set user permission
     *   True = User can maniuplate the image. False = User cannot manipulate the image
     */
    private void canManipulatePainting(boolean canManPainting){//this is used to enable/diable the menu items at once
        if(canManPainting){
            menuBarSave.setDisable(false);
            menuBarSaveAs.setDisable(false);
            menuBarCopy.setDisable(false);
            menuBarCut.setDisable(false);
            menuBarContLine.setDisable(false);
            menuBarStraightLine.setDisable(false);
            menuBarSolidRectangle.setDisable(false);
            menuBarOutlineRectangle.setDisable(false);
            menuBarSolidOval.setDisable(false);
            menuBarEmptyOval.setDisable(false);
            menuBarUndo.setDisable(false);
            menuBarRedo.setDisable(false);
            menuBarColorDropper.setDisable(false);
            menuBarEraser.setDisable(false);
            menuBarText.setDisable(false);
            menuBarCustomShape.setDisable(false);
            menuBarTriangle.setDisable(false);
            menuBarTriangleSolid.setDisable(false);
        }
    }


    /**
     * TODO correct the area it saves.
     * Handles capturing/saving an editied/created custom canva
     * This is called by the save as method
     */
    private void takeSnapShot(Scene scene){
        WritableImage writableImage = new WritableImage( (int) currCanvas.getWidth(), (int) currCanvas.getHeight() ); //first param in snapshot method call
        SnapshotParameters snapParam = new SnapshotParameters();//TODO FIX THIS
        // snapParam.setViewport(new Rectangle2D(imageViewFXML.getX(),imageViewFXML.getY(),currCanvas.getWidth(),currCanvas.getHeight()));
        snapParam.setViewport(new Rectangle2D(4,46,currCanvas.getWidth(),currCanvas.getHeight()));//sets the viewport area to save from
        currCanvas.snapshot(snapParam,writableImage);
        // currCanvas.snapshot(writableImage);
        File file = new File("snapshot.png");
        FileChooser fileChooser = new FileChooser();
        File dest = fileChooser.showSaveDialog(Paint.mainStage);//gets the save as locaiton from user
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", dest);
            Popup.popupAlert("File saved in: " + dest.getAbsolutePath());
            // Paint.mainStage.setTitle("");
        } catch (IOException ex) {
            System.out.println("ERROR TAKING SCREENSHOT");
        }
    }
    //------------------------------ End of Private Class Methods ------------------------------



    //------------------------------ Start of Public Class Methods ------------------------------

    /**
     *    Prompts the user to safely close the application.
     */
    private static void closeProgram()
    {
        if(hasBeenAlt){
            boolean warning = Popup.popupConfirmation("Warning: You are about to exit and have not saved your modified file.");
            if(warning){
                Platform.exit();
            }
        }else{
            boolean confirm = Popup.popupConfirmation("Pain(t) is about to exit. Are you sure?");

            if (confirm) {
                Platform.exit();
            }
        }
    }


    /**
     *
     * Opens a file that is passed to it. Must be of type, .png, .jpeg, .bmp
     * @return            True if successfully opened file, False otherwise.
     * @param file        File to be opened
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     1.0
     * @since       0.1
     */
    private boolean openSelectedFile(File file) {
            //File selection
            try {
                System.out.println("Current file is: " + file.toURI().toString());
                Paint.mainStage.setTitle(file.getName());
                System.out.println("fail0");
                currImage = new Image(file.toURI().toString());
                canManipulatePainting(true); //TODO if you're debugging THIS IS YOUR ISSUE. Screw this function
                System.out.println("fail00");
                currCanvas.setWidth(640.0);
                currCanvas.setHeight(480.0);
                previewCanvas.setWidth(640.0);
                previewCanvas.setHeight(480.0);
                System.out.println("fail1");
                currCanvas.getGraphicsContext2D().drawImage(currImage, 0, 0, 640.0, 480.0);//set up image Canvas layer
                System.out.println("fail2");
                pushToStack(undoStack);
                redoStack.clear();
                layerCount.setText("Current Layer: " + layercount);
                return true;
            } catch (Exception e) {
                System.out.println("ERROR: FAILED TO OPEN SELECTED FILE.");
                return false;
            }
    }

    //
    private void pushToStack(Stack<Image> stack){

        WritableImage writableImage = new WritableImage( (int) currCanvas.getWidth(), (int) currCanvas.getHeight() ); //first param in snapshot method call
        SnapshotParameters snapParam = new SnapshotParameters();//TODO FIX THIS
        snapParam.setViewport(new Rectangle2D(4,46,currCanvas.getWidth(),currCanvas.getHeight()));//sets the viewport area to save from
        Image image = currCanvas.snapshot(snapParam,writableImage);
        stack.push(image);
        layercount++;
        layerCount.setText("Current Layer: " + layercount);

    }

    /*used when additional images are loaded.
    * It sets the app to a default state.*/
    private void resetAppState(){
        cleanCanvas();

        layercount=0;

        /*clear currcanvas*/
        GraphicsContext gc = currCanvas.getGraphicsContext2D();
        gc.setStroke(Color.GAINSBORO);
        gc.setFill(Color.GAINSBORO);
        gc.fillRect(0, 0, 800, 600);

        /*clear stacks*/
        redoStack.clear();
        undoStack.clear();
    }

    /*makes an unnoticabvle change to the canvas
    * to fix the last change not being applied bug*/
    private void cleanCanvas(){
        GraphicsContext gc = currCanvas.getGraphicsContext2D();
        gc.setLineWidth(1);
        gc.setStroke(Color.TRANSPARENT);
        gc.strokeLine(100, 100, 100, 100);

        GraphicsContext gcP = previewCanvas.getGraphicsContext2D();
        gcP.setLineWidth(1);
        gcP.setStroke(Color.TRANSPARENT);
        gcP.strokeRect(0, 0, 800, 600);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { }
}
