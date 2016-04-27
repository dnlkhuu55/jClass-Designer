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
import jcd.data.UMLVariables;
import properties_manager.PropertiesManager;

/**
 *
 * @author dnlkhuu77
 */
public class VariablesDialog extends Stage{
    UMLVariables vars;
    
    // GUI CONTROLS FOR OUR DIALOG
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    Label nameLabel;
    TextField nameTextField;
    Label typeLabel;
    TextField typeTextField;
    Label accessLabel;
    ComboBox accessComboBox;
    Label staticLabel;
    CheckBox staticCheckBox;
    Button completeButton;
    Button cancelButton;
    
    String selection;
    
    // CONSTANTS FOR OUR UI
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String NAME_PROMPT = "Name: ";
    public static final String TYPE_PROMPT = "Type: ";
    public static final String ACCESS_PROMPT = "Access: ";
    public static final String STATIC_PROMPT = "Static: ";
    public static final String VARIABLES_HEADING = "Variable Details";
    public static final String ADD_VARIABLE_TITLE = "Add New Variable";
    public static final String EDIT_VARIABLE_TITLE = "Edit Variable";
    
    public VariablesDialog(Stage initStage) {
        
        //vars = new UMLVariables();

        initModality(Modality.WINDOW_MODAL);
        initOwner(initStage);
        
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        headingLabel = new Label(VARIABLES_HEADING);
        headingLabel.getStyleClass().add("heading_label");
    
        // NOW THE NAME
        nameLabel = new Label(NAME_PROMPT);
        nameLabel.getStyleClass().add("subheading_label");
        nameTextField = new TextField();
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            vars.setName(newValue);
        });
        
        typeLabel = new Label(TYPE_PROMPT);
        typeLabel.getStyleClass().add("subheading_label");
        typeTextField = new TextField();
        typeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            vars.setType(newValue);
        });

        accessLabel = new Label(ACCESS_PROMPT);
        accessLabel.getStyleClass().add("subheading_label");
        accessComboBox = new ComboBox();
        accessComboBox.getItems().addAll("public", "private", "protected");
        accessComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String acessy = newValue.toString();
                vars.setAccesstype(acessy);
            }
        });
        
        staticLabel = new Label(STATIC_PROMPT);
        staticLabel.getStyleClass().add("subheading_label");
        staticCheckBox = new CheckBox();
        staticCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            vars.setStatictype(newValue);
        });

        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent e) -> {
            Button sourceButton = (Button)e.getSource();
            VariablesDialog.this.selection = sourceButton.getText();
            VariablesDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(nameLabel, 0, 1, 1, 1);
        gridPane.add(nameTextField, 1, 1, 1, 1);
        gridPane.add(typeLabel, 0, 2, 1, 1);
        gridPane.add(typeTextField, 1, 2, 1, 1);
        gridPane.add(accessLabel, 0, 3, 1, 1);
        gridPane.add(accessComboBox, 1, 3, 1, 1);
        gridPane.add(staticLabel, 0, 4, 1, 1);
        gridPane.add(staticCheckBox, 1, 4, 1, 1);
        gridPane.add(completeButton, 0, 5, 1, 1);
        gridPane.add(cancelButton, 1, 5, 1, 1);

        dialogScene = new Scene(gridPane);
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
    
    public UMLVariables getVariable() { 
        return vars;
    }
    
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @param message Message to appear inside the dialog.
     */
    public UMLVariables showAddVariableDialog() {
        // SET THE DIALOG TITLE
        setTitle(ADD_VARIABLE_TITLE);
        
        // RESET THE SCHEDULE ITEM OBJECT WITH DEFAULT VALUES
        vars = new UMLVariables();
        
        // LOAD THE UI STUFF
        nameTextField.setText(vars.getName());
        typeTextField.setText(vars.getType());
        accessComboBox.getSelectionModel().select(0);
        staticCheckBox.setSelected(vars.isStatictype());
        
        // AND OPEN IT UP
        this.showAndWait();
        
        return vars;
    }
    
    public void loadGUIData() {
        // LOAD THE UI STUFF
        nameTextField.setText(vars.getName());
        typeTextField.setText(vars.getType());
        accessComboBox.getSelectionModel().select(vars.getAccesstype());
        staticCheckBox.setSelected(vars.isStatictype());
    }
    
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
    
    public void showEditVariableDialog(UMLVariables variablesToEdit) {
        setTitle(EDIT_VARIABLE_TITLE);
        
        vars = new UMLVariables();
        vars.setName(variablesToEdit.getName());
        vars.setType(variablesToEdit.getType());
        vars.setAccesstype(variablesToEdit.getAccesstype());
        vars.setStatictype(variablesToEdit.isStatictype());
        loadGUIData();
               
        // AND OPEN IT UP
        this.showAndWait();
    }
}
