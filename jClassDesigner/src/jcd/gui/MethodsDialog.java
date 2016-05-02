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
import jcd.data.UMLMethods;
import jcd.data.UMLVariables;
import properties_manager.PropertiesManager;

/**
 *
 * @author dnlkhuu77
 */
public class MethodsDialog extends Stage {
    UMLMethods meths;
    
    GridPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    
    Label nameLabel;
    TextField nameTextField;
    Label returnLabel;
    TextField returnTextField;
    Label accessLabel;
    ComboBox accessComboBox;
    Label staticLabel;
    CheckBox staticCheckBox;
    Label abstractLabel;
    CheckBox abstractCheckBox;
    Button completeButton;
    Button cancelButton;
    
    String selection;
    
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String NAME_PROMPT = "Name: ";
    public static final String RETURN_PROMPT = "Return: ";
    public static final String ACCESS_PROMPT = "Access: ";
    public static final String STATIC_PROMPT = "Static: ";
    public static final String ABSTRACT_PROMPT = "Abstract: ";
    public static final String METHOD_ARGS0_PROMPT = "Argument: ";
    public static final String METHOD_ARGS1_PROMPT = "Argument: ";
    public static final String METHOD_ARGS2_PROMPT = "Argument: ";
    public static final String METHOD_ARGS3_PROMPT = "Argument: ";
    public static final String METHODS_HEADING = "Method Details";
    public static final String ADD_METHOD_TITLE = "Add New Method";
    public static final String EDIT_METHOD_TITLE = "Edit Method";
    
    public MethodsDialog(Stage m) {

        initModality(Modality.WINDOW_MODAL);
        initOwner(m);
        
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        
        headingLabel = new Label(METHODS_HEADING);
        headingLabel.getStyleClass().add("heading_label");
    
        // NOW THE NAME
        nameLabel = new Label(NAME_PROMPT);
        nameLabel.getStyleClass().add("subheading_label");
        nameTextField = new TextField();
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            meths.setName(newValue);
        });
        
        returnLabel = new Label(RETURN_PROMPT);
        returnLabel.getStyleClass().add("subheading_label");
        returnTextField = new TextField();
        returnTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            meths.setReturntype(newValue);
        });

        accessLabel = new Label(ACCESS_PROMPT);
        accessLabel.getStyleClass().add("subheading_label");
        accessComboBox = new ComboBox();
        accessComboBox.getItems().addAll("public", "private", "protected");
        accessComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String acessy = newValue.toString();
                meths.setAccesstype(acessy);
            }
        });
        
        staticLabel = new Label(STATIC_PROMPT);
        staticLabel.getStyleClass().add("subheading_label");
        staticCheckBox = new CheckBox();
        staticCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            meths.setStatictype(newValue);
        });
        
        abstractLabel = new Label(ABSTRACT_PROMPT);
        abstractLabel.getStyleClass().add("subheading_label");
        abstractCheckBox = new CheckBox();
        abstractCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            meths.setAbstractype(newValue);
        });

        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent e) -> {
            Button sourceButton = (Button)e.getSource();
            MethodsDialog.this.selection = sourceButton.getText();
            MethodsDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);

        gridPane.add(headingLabel, 0, 0, 2, 1);
        gridPane.add(nameLabel, 0, 1, 1, 1);
        gridPane.add(nameTextField, 1, 1, 1, 1);
        gridPane.add(returnLabel, 0, 2, 1, 1);
        gridPane.add(returnTextField, 1, 2, 1, 1);
        gridPane.add(accessLabel, 0, 3, 1, 1);
        gridPane.add(accessComboBox, 1, 3, 1, 1);
        gridPane.add(staticLabel, 0, 4, 1, 1);
        gridPane.add(staticCheckBox, 1, 4, 1, 1);
        gridPane.add(abstractLabel, 0, 5, 1, 1);
        gridPane.add(abstractCheckBox, 1, 5, 1, 1);
        gridPane.add(completeButton, 0, 6, 1, 1);
        gridPane.add(cancelButton, 1, 6, 1, 1);

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
    
    public UMLMethods getMethods() { 
        return meths;
    }
    
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @param message Message to appear inside the dialog.
     */
    public UMLMethods showAddMethodDialog() {
        // SET THE DIALOG TITLE
        setTitle(ADD_METHOD_TITLE);
        
        // RESET THE SCHEDULE ITEM OBJECT WITH DEFAULT VALUES
        meths = new UMLMethods();
        
        // LOAD THE UI STUFF
        nameTextField.setText(meths.getName());
        returnTextField.setText(meths.getReturntype());
        accessComboBox.getSelectionModel().select(0);
        staticCheckBox.setSelected(meths.isStatictype());
        abstractCheckBox.setSelected(meths.isAbstractype());
        
        // AND OPEN IT UP
        this.showAndWait();
        
        return meths;
    }
    
    public void loadGUIData() {
        // LOAD THE UI STUFF
        nameTextField.setText(meths.getName());
        returnTextField.setText(meths.getReturntype());
        accessComboBox.getSelectionModel().select(meths.getAccesstype());
        staticCheckBox.setSelected(meths.isStatictype());
        abstractCheckBox.setSelected(meths.isAbstractype());
    }
    
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
    }
    
    public void showEditVariableDialog(UMLMethods methodsToEdit) {
        // SET THE DIALOG TITLE
        setTitle(EDIT_METHOD_TITLE);
        
        // LOAD THE LECTURE INTO OUR LOCAL OBJECT
        meths = new UMLMethods();
        meths.setName(methodsToEdit.getName());
        meths.setReturntype(methodsToEdit.getReturntype());
        meths.setAccesstype(methodsToEdit.getAccesstype());
        meths.setStatictype(methodsToEdit.isStatictype());
        meths.setAbstractype(methodsToEdit.isAbstractype());
        loadGUIData();
               
        // AND OPEN IT UP
        this.showAndWait();
    }
}
