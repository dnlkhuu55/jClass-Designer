/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jcd.data.UMLMArgs;
import properties_manager.PropertiesManager;

/**
 *
 * @author dnlkhuu77
 */
public class ArgsDialog extends Stage {
    UMLMArgs args;
    
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;

    Label typeLabel;
    TextField typeTextField;
    Button completeButton;
    Button cancelButton;
    
    String selection;
    
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String ARG_PROMPT = "Argument: ";
    public static final String ARG_HEADING = "Argument Details";
    public static final String ADD_ARG_TITLE = "Add New Argument";
    public static final String EDIT_ARG_TITLE = "Edit Argument";
    
    public ArgsDialog(Stage m) {

        initModality(Modality.WINDOW_MODAL);
        initOwner(m);
        
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        headingLabel = new Label(ARG_HEADING);
        headingLabel.getStyleClass().add("heading_label");
    
        // NOW THE NAME
        typeLabel = new Label(ARG_PROMPT);
        typeLabel.getStyleClass().add("subheading_label");
        typeTextField = new TextField();
        typeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            args.setArgstype(newValue);
        });

        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent e) -> {
            Button sourceButton = (Button)e.getSource();
            ArgsDialog.this.selection = sourceButton.getText();
            ArgsDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(typeLabel, 0, 1, 1, 1);
        gridPane.add(typeTextField, 1, 1, 1, 1);
        gridPane.add(completeButton, 0, 2, 1, 1);
        gridPane.add(cancelButton, 1, 2, 1, 1);

        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(gridPane);
        //dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
    }
    
    /**
     * Accessor method for getting the selection the user made.
     * 
     * @return Either YES, NO, or CANCEL, depending on which
     * button the user selected when this dialog was presented.
     */
    public String getSelection() {
        return selection;
    }
    
    public UMLMArgs getArgs() { 
        return args;
    }
    
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @param message Message to appear inside the dialog.
     */
    public UMLMArgs showAddArgsDialog() {
        // SET THE DIALOG TITLE
        setTitle(ADD_ARG_TITLE);
        
        // RESET THE SCHEDULE ITEM OBJECT WITH DEFAULT VALUES
        args = new UMLMArgs();
        
        // LOAD THE UI STUFF
        typeTextField.setText(args.getArgstype());
        
        // AND OPEN IT UP
        this.showAndWait();
        
        return args;
    }
    
    public void loadGUIData() {
        // LOAD THE UI STUFF
        typeTextField.setText(args.getArgstype());
    }
    
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
    
    public void showEditArgsDialog(UMLMArgs argsToEdit) {
        // SET THE DIALOG TITLE
        setTitle(EDIT_ARG_TITLE);
        
        // LOAD THE LECTURE INTO OUR LOCAL OBJECT
        args = new UMLMArgs();
        args.setArgstype(argsToEdit.getArgstype());
        loadGUIData();
               
        // AND OPEN IT UP
        this.showAndWait();
    }
}
