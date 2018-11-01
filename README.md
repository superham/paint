
# Paint
Author: Alex Kaariainen
_______________________________________________________________

## About
An image manipulation program written in JavaFX, inspired by GIMP and Microsoft Paint, developed for MacOS. 

Supports the following features: Open/Save/Save As images (.png, .jpeg, .bmp), copy and paste portions of the selected image, undo and redo changes, color dropper, color selector, eraser tool, text tool, varying width and color straight and free-hand line tools, solid and outlined rectangle/oval/triangle, custom solid shape, 'smart' saving logic to prevent loss of progress upon exiting/opening new files. 
_______________________________________________________________

## Build Instructions
This project can be compiled using any IDE that supports JavaFX and FXML, IntellJ for example.
_______________________________________________________________

## Executable
The .jar can be donwloaded from [here](https://github.com/superham/paint/releases/download/v1.0.0/current.paint.jar).
_______________________________________________________________

## Application Screenshot
![alt text](https://i.imgur.com/Db6xp3o.png)

_______________________________________________________________

## How to use Paint
  ### Opening Images
   * File > Open
   * Select any file of types .png, .jpeg, .bmp
  ### Editing Images
   #### Cut/Copy
   * Edit > Cut/Copy
   * Press and Release mouse to select first point of rectangluar area to copy/cut
   * Press and Release mouse at a second point to complete rectangle to copy/cut
   * Upon third mouse press and release the selected area will be pasted onto the image
   #### Undo/Redo
   ##### Option 1
   * Edit > Undo/Redo
   ##### Option 2
   * Click on the Undo/Redo button at the top right of the image window
   ### Eraser Tool
   * Select desired line widith using the slider on the right side of the window
   * Effects > Eraser
   * Click and drag areas of image to be erased.
   * Upon mouse release program will exit eraser mode
   ### Text Tool
   * Select desired font size using the slider on the right side of the window 
   * Effects > Text
   * Type out desired test and select 'ok'
   * Click and release mouse, the text will be pasted at this location.
   * Program exits text mode
   ### Line Tools
   #### Straight Line
   * Select desired line width using sliders
   * Select desired line color using color drop-down on right side of screen
   * Line > Straight Line
   * Press and Release to select first point
   * Move mouse to desired second point
   * Press and Release to complete line
   * Program exits line mode
   #### Free Hand Line
   * Select desired line width using sliders
   * Select desired line color using color drop-down on right side of screen
   * Line > Free Hand Line
   * Press and drag to to draw on the image
   * Program exits line mode upon mouse release
   #### Shape Tools
   * Select desired shape color using color drop-down on right side of screen
   * Selected desired line width using sliders
   * Shape > Rectangle/Oval/Triangle > Solid/Outline
   * Press and Release to select first point of shape
   * Press and Release a second time to selected second point
   * Upon release the shape will be pasted to the image. The program also now exits shape mode
  ### Saving Changes
   * A user can see at any time if their progress has been saved been looking at the file name at the top of the window. If the file name ends with '*' then the changes have not been saved.
   * File > Save/Save As
   * 'Save' will overwrite the selected file
   * 'Save As' will ask the user to specify where they would like to save their file and in what format.
