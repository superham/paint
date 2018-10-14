/*
 *@author: Alex Kaariainen
 *FXMLDocumentController.java
 *Version 0.3
 *Description: Logic behind FXML elements
 */
package main;
import imgMnp.*;
import javafx.scene.canvas.GraphicsContext;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

public class FXMLDocumentController implements Initializable {
    //------------------------------ Start of Private Class Members ------------------------------
    private static boolean hasBeenAlt=false;        //tracks if the current file has been altered since last save
    @FXML
    ColorPicker lineColorPicker;                    //color picker objects for line color and fill color
    @FXML
    Canvas currCanvas,previewCanvas;                //reference to the current canvas
    @FXML
    MenuItem menuBarSave,menuBarSaveAs,menuBarCopy,menuBarCut,menuBarTriangle,
            menuBarPaste,menuBarTranslate,menuBarRotate,menuBarScale,menuBarTriangleSolid
            ,menuBarCrop,menuBarResizeCanvas,menuBarLineSetWidth,menuBarCustomShape,
            menuBarSolidRectangle,menuBarOutlineRectangle,menuBarSolidOval,
            menuBarEmptyOval,menuBarStraightLine,menuBarContLine,menuBarUndo,
            menuBarRedo,menuBarColorDropper,menuBarEraser,menuBarText;
    @FXML
    private File currentFile = null;
    @FXML
    private Image currImage;
    private String fileExt;                         //keeps track of the file extension for internal use and compatilbity stuff
    private int lineWidth=5;                        //default line width set to 5, this is what is called to check width
    boolean hasUndoOccured=false;

    private Stack<Image> undoStack = new Stack<>();
    private Stack<Image> redoStack = new Stack<>();

    //------------------------------ End of Private Class Members ------------------------------



    //------------------------------ Start of Private Class Methods ------------------------------
    /**
     * Prompts user for new line width
     * */
    @FXML
    private void handleLineSetWidth(final ActionEvent event){ lineWidth=Popup.popupLineWidth(); }

    @FXML
    private void handleUndo(final ActionEvent event){
        try {
            GraphicsContext gc = previewCanvas.getGraphicsContext2D();
            gc.clearRect(0,0,640,480);
            currCanvas.getGraphicsContext2D().drawImage(undoStack.peek(), 0, 5, 640.0, 480.0); //set up image Canvas layer
            redoStack.push(undoStack.peek());
            undoStack.pop();
            hasUndoOccured = true;
            System.out.println("Pop Successful.");
            Paint.mainStage.setTitle(currentFile.getName()+"*");
        } catch(Exception e){

        }
    }

    @FXML
    private void handleRedo(final ActionEvent event){
        try {
            GraphicsContext gc = previewCanvas.getGraphicsContext2D();
            gc.clearRect(0,0,640,480);
            undoStack.push(redoStack.peek()); //move top of redoStack to undoStack
            redoStack.pop();                  //pop redo stack
            currCanvas.getGraphicsContext2D().drawImage(undoStack.peek(), 0, 5, 640.0, 480.0); //set up image Canvas layer
            Paint.mainStage.setTitle(currentFile.getName() + "*");
        } catch (Exception e){

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
        //ContLine.line(currCanvas, lineWidth, lineColorPicker.getValue(),previewCanvas); // if can successfully draw a line
        Eraser.line(currCanvas, lineWidth,previewCanvas);
        hasBeenAlt=true;
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleText(final ActionEvent event){
        // int fontSize = Popup.popupFontSize();
        String textField = Popup.popupText();
        String correctText = textField.substring(9,textField.length()-1);
        Text.displayText(currCanvas,previewCanvas,10,correctText,lineColorPicker.getValue());
        hasBeenAlt=true;
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleCut(final ActionEvent event){
        hasBeenAlt=true;
        Effects.cut(currCanvas,previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleTriangle(final ActionEvent event){
        hasBeenAlt=true;
        CustomShape.triangle(currCanvas,lineWidth,lineColorPicker.getValue(),previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleFillRectangle(final ActionEvent event){
        hasBeenAlt=true;
        Rectangle.solidRect(currCanvas,lineColorPicker.getValue(),lineWidth,previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleOutlineRectangle(final ActionEvent event){
        hasBeenAlt=true;
        Rectangle.emptyRect(currCanvas,lineColorPicker.getValue(),lineWidth,previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }


    @FXML
    private void handleFillCircle(final ActionEvent event){
        hasBeenAlt=true;
        Oval.solidCircle(currCanvas,lineWidth,lineColorPicker.getValue(),previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleOutlineCircle(final ActionEvent event){
        hasBeenAlt=true;
        Oval.emptyCircle(currCanvas,lineWidth,lineColorPicker.getValue(),previewCanvas);
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleCustomShape(final ActionEvent event){
        hasBeenAlt=true;
        CustomShape.line(currCanvas,lineWidth,lineColorPicker.getValue(),previewCanvas);
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
        ContLine.line(currCanvas, lineWidth, lineColorPicker.getValue(),previewCanvas); // if can successfully draw a line
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
        TwoPointLine.line(currCanvas, lineWidth, lineColorPicker.getValue(),previewCanvas);
        hasBeenAlt=true;
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    @FXML
    private void handleTriangleSolid(final ActionEvent event){
        CustomShape.triangleSolid(currCanvas, lineWidth, lineColorPicker.getValue(),previewCanvas);
        hasBeenAlt=true;
        pushToStack(undoStack);
        redoStack.clear();
        Paint.mainStage.setTitle(currentFile.getName()+"*");
    }

    /**
     * Handles opening a file.
     * This is called by the FXML document when the correct button has been pressed
     * This relys upon the openSelectedFile method
     */
    @FXML
    private void handleOpenFileAction(final ActionEvent event) {
        //File open
        try {
            //TODO alt ways to accept additional file types
            FileChooser fileChooser = new FileChooser();//gets file from user
            fileChooser.setTitle("Open File");
            //forces user to choose 'acceptable' file types
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                    new FileChooser.ExtensionFilter("BMP", "*.bmp"));
            currentFile = fileChooser.showOpenDialog(Paint.mainStage);
            fileExt = fileChooser.getSelectedExtensionFilter().getDescription();
            openSelectedFile(currentFile);//calls method to display file
        }
        catch(Exception e){
            System.out.println("ERROR:FILE SELECTION WRAPPER FAILURE.");
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
                currCanvas.getGraphicsContext2D().drawImage(undoStack.peek(),0,0,640.0,480.0);
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
        if((currentFile != null)){
            if(hasBeenAlt){//checks to see if the user modifiyed the image.
                takeSnapShot(Paint.mainScene);//if so take a snapshot
            } else{//if they did not edit the image just save it noramlly
                try{
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("PNG", "*.png"),
                            new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                            new FileChooser.ExtensionFilter("BMP", "*.bmp"));
                    File dest=fileChooser.showSaveDialog(Paint.mainStage);//gets the file path from the user
                    //takeSnapShot(Paint.mainScene);
                    Files.copy(currentFile.toPath(),dest.toPath());
                    currentFile=dest;
                    Paint.mainStage.setTitle(dest.getName());
                    currCanvas.getGraphicsContext2D().drawImage(undoStack.peek(),0,0,640.0,480.0);
                    undoStack.clear();
                    redoStack.clear();
                    Popup.popupAlert("File saved");
                }catch(Exception e){
                    Popup.popupAlert("File save error");
                    System.out.println("ERROR: SAVE AS FAILURE.");
                }
            }

        }else{//checks for image underneath
            if(hasBeenAlt){
                try {
                    // FileChooser fileChooser = new FileChooser();
                    // File dest = fileChooser.showSaveDialog(Paint.mainStage);
                    takeSnapShot(Paint.mainScene);
                    //Files.copy(currentFile.toPath(), dest.toPath());

                }catch(Exception e){
                    System.out.println("ERROR SAVING CANVAS IMAGE");
                }
            }else{
                handleAccessingNullFile();
            }
        }
        hasBeenAlt=false;
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
            menuBarLineSetWidth.setDisable(false);
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
            System.out.println("ERROR...");
        }
    }
    //------------------------------ End of Private Class Methods ------------------------------



    //------------------------------ Start of Public Class Methods ------------------------------

    /**
     *    Prompts the user to safely close the application.
     */
    @FXML
    public static void closeProgram()
    {
        if(hasBeenAlt){
            Popup.popupConfirmation("Warning: You have not saved your modified file.");
        }
        boolean confirm = Popup.popupConfirmation("Pain(t) is about to exit. Are you sure?");

        if (confirm) {
            Platform.exit();
        }
    }


    /**
     *    Opens and dispalys the selceted file.
     *    @param file, image file to display
     */
    private void openSelectedFile(File file) {
        if (file != null) {
            //File selection
            try{
                System.out.println("Current file is: " + file.toURI().toString());
                Paint.mainStage.setTitle(file.getName());
                currImage = new Image(file.toURI().toString());
                canManipulatePainting(true); //TODO if you're debugging this is your issue. Screw this funcion
                currCanvas.setWidth(640.0);
                currCanvas.setHeight(480.0);
                previewCanvas.setWidth(640.0);
                previewCanvas.setHeight(480.0);
                currCanvas.getGraphicsContext2D().drawImage(currImage,0,0,640.0,480.0);//set up image Canvas layer
            } catch(Exception e) {
                System.out.println("ERROR: FAILED TO OPEN SELECTED FILE.");
                Popup.popupError("Failed to open selected file");
            }
        } else {
            System.out.println("WARNING:FILE SELECTED IS SEEN AS NULL.");
        }
    }

    private void pushToStack(Stack<Image> stack){
        WritableImage writableImage = new WritableImage( (int) currCanvas.getWidth(), (int) currCanvas.getHeight() ); //first param in snapshot method call
        SnapshotParameters snapParam = new SnapshotParameters();//TODO FIX THIS
        // snapParam.setViewport(new Rectangle2D(imageViewFXML.getX(),imageViewFXML.getY(),currCanvas.getWidth(),currCanvas.getHeight()));
        snapParam.setViewport(new Rectangle2D(0,46,currCanvas.getWidth(),currCanvas.getHeight()));//sets the viewport area to save from
        Image image = currCanvas.snapshot(snapParam,writableImage);
        stack.push(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { }
}
//Pre cut down      :802
//Post cut          :551
//double post cut   :391