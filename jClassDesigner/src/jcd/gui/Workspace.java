package jcd.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import jcd.JClassDesigner;
import jcd.PropertyType;
import jcd.data.DataManager;
import properties_manager.PropertiesManager;
import af.ui.AppGUI;
import af.AppTemplate;
import af.components.AppWorkspaceComponent;
import static af.settings.AppPropertyType.SAVE_WORK_TITLE;
import static af.settings.AppPropertyType.WORK_FILE_EXT;
import static af.settings.AppPropertyType.WORK_FILE_EXT_DESC;
import static af.settings.AppStartupConstants.FILE_PROTOCOL;
import static af.settings.AppStartupConstants.PATH_IMAGES;
import static af.settings.AppStartupConstants.PATH_WORK;
import af.ui.AppMessageDialogSingleton;
import af.ui.AppYesNoCancelDialogSingleton;
import java.util.HashMap;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jcd.data.ClassLines;
import jcd.data.UMLClasses;
import jcd.data.UMLInterfaces;
import jcd.data.UMLMethods;
import jcd.data.UMLVariables;

/**
 * This class serves as the workspace component for this application, providing
 * the user interface controls for editing work.
 *
 * @author Richard McKenna
 * @author Daniel Khuu
 * @version 1.0
 */
public class Workspace extends AppWorkspaceComponent {

    // HERE'S THE APP
    AppTemplate app;

    // IT KNOWS THE GUI IT IS PLACED INSIDE
    AppGUI gui;

    ScrollPane tempo;
    BorderPane lPane;
    Pane leftPane;
    Pane varPane;
    Pane metPane;
    VBox rightPane;
    HBox classPane;
    HBox packagePane;
    HBox parentPane;
    HBox variablePane;
    ScrollPane variableTablePane;
    HBox methodPane;
    ScrollPane methodTablePane;
    Button photoButton = new Button();
    Button codeButton = new Button();
    Button selectionButton = new Button();
    Button resizeButton = new Button();
    Button addClassButton = new Button();
    Button addInterfaceButton = new Button();
    Button removeButton = new Button();
    Button undoButton = new Button();
    Button redoButton = new Button();
    Button zoominButton = new Button();
    Button zoomoutButton = new Button();
    Button addButton = new Button();
    Button addMButton = new Button();
    Button minusButton = new Button();
    Button minusMButton = new Button();
    TextField dummy = new TextField();
    TextField dummyData = new TextField();
    TableView table1;
    TableColumn NameCol1;
    TableColumn TypeCol1;
    TableColumn StaticCol1;
    TableColumn AccessCol1;
    TableView table2;
    Button selectedButton;
    Text prevText = new Text();
    Text currentText = new Text();
    ComboBox parentComboBox = new ComboBox();
    
    CheckBox gridBox, snapBox;
    VBox checkBox = new VBox();
    
    static final int BUTTON_TAG_WIDTH = 40;
    UMLClasses sc;
    UMLInterfaces ie;
    UMLVariables var;
    UMLMethods methods;
    String temp00;
    String temp01;
    
    VBox prevPane = null;
    VBox currentPane = null;
    UMLVariables currentVariable;
    UMLMethods currentMethod;
    FlowPane fileToolbarPane;
    FlowPane editToolbarPane;
    FlowPane viewToolbarPane;
    HBox wholePane;
    Line s;
    Line l;
    
    HashMap<String,Text> textTemp = new HashMap<>(); 
    
    boolean snapping = false;
    
    // HERE ARE OUR DIALOGS
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;
    VariablesDialog vy;
    MethodsDialog my;

    /**
     * Constructor for initializing the workspace, note that this constructor
     * will fully setup the workspace user interface for use.
     *
     * @param initApp The application this workspace is part of.
     *
     * @throws IOException Thrown should there be an error loading application
     * data for setting up the user interface.
     */
    public Workspace(AppTemplate initApp) throws IOException {
       app = initApp;
       gui = app.getGUI();
       DataManager expo = (DataManager) app.getDataComponent();
       vy = new VariablesDialog(null);
       my = new MethodsDialog(null);

       workspace = new BorderPane();
       leftPane = new Pane(); 
       varPane = new Pane();
       metPane = new Pane();
       leftPane.setPrefSize(1500, 1500);
       tempo = new ScrollPane();
       tempo.setContent(leftPane);
       
       fileToolbarPane = gui.getFileToolbarPane();
       editToolbarPane = gui.getEditToolbarPane();
       viewToolbarPane = gui.getViewToolbarPane();
       
       rightPane = new VBox();
       tempo.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
       leftPane.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
               
        selectionButton = initChildButton(editToolbarPane, PropertyType.SELECTION_ICON.toString(), PropertyType.SELECTION_TOOLTIP.toString(), true);
        resizeButton = initChildButton(editToolbarPane, PropertyType.RESIZE_ICON.toString(), PropertyType.RESIZE_TOOLTIP.toString(), true);
        addClassButton = initChildButton(editToolbarPane, PropertyType.ADDCLASS_ICON.toString(), PropertyType.ADDCLASS_TOOLTIP.toString(), false);
        addInterfaceButton = initChildButton(editToolbarPane, PropertyType.ADDINTERFACE_ICON.toString(), PropertyType.ADDINTERFACE_TOOLTIP.toString(), false);
        removeButton = initChildButton(editToolbarPane, PropertyType.REMOVE_ICON.toString(), PropertyType.REMOVE_TOOLTIP.toString(), true);
        undoButton = initChildButton(editToolbarPane, PropertyType.UNDO_ICON.toString(), PropertyType.UNDO_TOOLTIP.toString(), true);
        redoButton = initChildButton(editToolbarPane, PropertyType.REDO_ICON.toString(), PropertyType.REDO_TOOLTIP.toString(), true);
        zoominButton = initChildButton(viewToolbarPane, PropertyType.ZOOMIN_ICON.toString(), PropertyType.ZOOMIN_TOOLTIP.toString(), true);
        zoomoutButton = initChildButton(viewToolbarPane, PropertyType.ZOOMOUT_ICON.toString(), PropertyType.ZOOMOUT_TOOLTIP.toString(), true);
        
        checkBox.setSpacing(5);
        checkBox.setPadding(new Insets(5));
        gridBox = new CheckBox("Grid");
        snapBox = new CheckBox("Snap");
        checkBox.getChildren().addAll(gridBox, snapBox);
        viewToolbarPane.getChildren().add(checkBox);
        
        fileToolbarPane.setHgap(10);
        editToolbarPane.setHgap(10);
        viewToolbarPane.setHgap(10);
        
        selectionButton.setOnAction(e -> {
	    leftPane.setCursor(Cursor.DEFAULT);
            selectedButton = selectionButton;
            selectionButton.setDisable(true);
            currentPane.setCursor(Cursor.DEFAULT);
        });
       
        leftPane.setOnMousePressed(e -> {
            if (e.getTarget() instanceof Pane && !(e.getTarget() instanceof VBox)){
            if(currentPane != null && selectedButton == selectionButton){
                currentPane.setStyle("-fx-border-width: 1px");
                currentPane.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff");
                currentPane = null;
                table1.getItems().clear();
                prevPane = currentPane;
                selectedButton = null;
                selectionButton.setDisable(false);
                addButton.setDisable(true);
                minusButton.setDisable(true);
                //resizeButton.setDisable(true); //always need on despite being selected or not
                if (currentPane != null)
                    currentPane.setCursor(Cursor.DEFAULT);
            }}
            
        }); 
               
        addClassButton.setOnAction(e -> {
            selectedButton = addClassButton; //LOAD INTO TOOLBAR BY DEFAULT
            resizeButton.setDisable(false);
            removeButton.setDisable(false);
            undoButton.setDisable(false);
            redoButton.setDisable(false);
            zoominButton.setDisable(false);
            zoomoutButton.setDisable(false);
            addButton.setDisable(false);
            minusButton.setDisable(false);
            gui.updateToolbarControls(false);
            gui.updatePhotoCodeButton();
            if (currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);

            sc = new UMLClasses("DEFAULT");
            sc.setStyle("-fx-border-color: #ffff00; -fx-background-color: #ffffff");
            //sc.setIsAbstract(true);
            sc.setClassName("DEFAULT");
            sc.setClassNametoString("DEFAULT"); //currentText
            sc.setPackageName(""); //currentText
            
            createListener(sc);
            selectedButton = selectionButton;
            selectionButton.setDisable(true);

            sc.setTranslateX(10);
            sc.setTranslateY(10);
            sc.setTranslateXer(10);
            sc.setTranslateYer(10);
            
        if (currentPane != null){
            currentPane.setStyle("-fx-border-color: #000000");
            prevPane = currentPane;
        }
        currentPane = sc;
        leftPane.getChildren().add(sc);
        expo.getClassList().add(sc);
            
        if(dummy != null)
            dummy.setText(sc.getClassNametoString());
        if(dummyData != null)
            dummyData.setText(sc.getPackageName());
        table1.getItems().clear();
        table2.getItems().clear();
        });
        
        addInterfaceButton.setOnAction(eh -> {
            selectedButton = addClassButton; //LOAD INTO TOOLBAR BY DEFAULT
            resizeButton.setDisable(false);
            removeButton.setDisable(false);
            undoButton.setDisable(false);
            redoButton.setDisable(false);
            zoominButton.setDisable(false);
            zoomoutButton.setDisable(false);
            addButton.setDisable(false);
            minusButton.setDisable(false);
            gui.updateToolbarControls(false);
            gui.updatePhotoCodeButton();
            if (currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);

            ie = new UMLInterfaces("<Interface> DEFAULT");
            ie.setStyle("-fx-border-color: #ffff00; -fx-background-color: #ffffff");
            ie.setPackageName(""); //currentText
            

            createListener(ie);
            selectedButton = selectionButton;
            selectionButton.setDisable(true);

            ie.setTranslateX(20);
            ie.setTranslateY(20);
            ie.setTranslateXer(30);
            ie.setTranslateYer(30);
            
        if (currentPane != null){
            currentPane.setStyle("-fx-border-color: #000000");
            prevPane = currentPane;
        }
        currentPane = ie;
        leftPane.getChildren().add(ie);
        expo.getClassList().add(ie);
            
        if(dummy != null)
            dummy.setText(ie.getInterNametoString());
        if(dummyData != null)
            dummyData.setText(ie.getPackageName());
        table1.getItems().clear();
        table2.getItems().clear();
        });
        

        removeButton.setOnAction(eh -> {
            leftPane.setCursor(Cursor.DEFAULT);
            if(currentPane instanceof VBox){
                if(currentPane != null)
                    currentPane.setCursor(Cursor.DEFAULT);
                leftPane.getChildren().remove(currentPane);
                expo.getClassList().remove(currentPane);
                currentPane = null;
                
                selectionButton.setDisable(false);
                resizeButton.setDisable(true);
                removeButton.setDisable(true);
            }
        });
        
        resizeButton.setOnAction(eh -> {
            selectedButton = resizeButton;
            
            currentPane.setCursor(Cursor.CROSSHAIR);
            //Change this
            selectionButton.setDisable(false);
        });
        
        zoominButton.setOnAction(eh -> {
            if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
            /*
            for(VBox s: expo.getClassList()){
                s.setScaleX(s.getScaleX() * 2);
                s.setScaleY(s.getScaleY() * 2);
            }
            */
            /*
            for(Node s: leftPane.getChildren()){
                s.setScaleX(s.getScaleX() * 2);
                s.setScaleY(s.getScaleY() * 2);
            }
            */
            leftPane.setScaleX(leftPane.getScaleX() * 2);
            leftPane.setScaleY(leftPane.getScaleY() * 2);
        });
        
        zoomoutButton.setOnAction(eh -> {
            if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
            leftPane.setScaleX(leftPane.getScaleX() / 2);
            leftPane.setScaleY(leftPane.getScaleY() / 2);
            /*
            for(VBox s: expo.getClassList()){
                s.setScaleX(s.getScaleX() /2);
                s.setScaleY(s.getScaleY() / 2);
            }
            */
            /*
            for(Node s: leftPane.getChildren()){
                s.setScaleX(s.getScaleX() / 2);
                s.setScaleY(s.getScaleY() / 2);
            }
            */
        });
        
        gridBox.setOnAction(eh -> {
            gridding(eh);
        });
        
        snapBox.setOnAction(eh -> {
            if(snapBox.isSelected() == true)
                snapping = true;
            else
                snapping = false;
        });
        
        /////////////////////////////////////////////////////////////////////////////
        classPane = new HBox();
        
        Label className = new Label("Class Name:");
        className.setFont(Font.font("Arial", 10));
        classPane.getChildren().add(className);
        
        classPane.getChildren().add(dummy);  
        
        dummy.setOnKeyReleased((KeyEvent k) -> {
            if(currentPane != null && currentPane instanceof UMLClasses){
                UMLClasses lol = (UMLClasses) currentPane; //TRYING
                lol.setClassName(dummy.getText());
                lol.setClassNametoString(dummy.getText());
            }
            if(currentPane != null && currentPane instanceof UMLInterfaces){
                UMLInterfaces lol = (UMLInterfaces) currentPane; //TRYING
                lol.setInterName(dummy.getText());
                lol.setInterNametoString(dummy.getText());
            }
        });
        
        rightPane.getChildren().add(classPane);
        
        ////////////////////////////////////////////////////////////////////////
        packagePane = new HBox();
     
        Label packageName = new Label("Package Name:");
        packageName.setFont(Font.font("Arial", 10));
        packagePane.getChildren().add(packageName);
        
        packagePane.getChildren().add(dummyData);
        
        dummyData.setOnKeyReleased((KeyEvent k) -> {
            if(currentPane != null && currentPane instanceof UMLClasses){
                UMLClasses lol = (UMLClasses) currentPane; //!!!!!!!!!!
                lol.setPackageName(dummyData.getText());
            }
            if(currentPane != null && currentPane instanceof UMLInterfaces){
                UMLInterfaces lol = (UMLInterfaces) currentPane; //!!!!!!!!!!
                lol.setPackageName(dummyData.getText());
            }
        });
        
        rightPane.getChildren().add(packagePane);
        
        ////////////////////////////////////////////////////////////////////////
        parentPane = new HBox();
        
        Label parentName = new Label("Parent: ");
        parentName.setFont(Font.font("Arial", 10));
        parentPane.getChildren().add(parentName);
        
        //ComboBox parentComboBox = new ComboBox();
        parentComboBox.getItems().addAll(
            "NOTHING",
            "NULL" 
        );
        parentPane.getChildren().add(parentComboBox);
        
        rightPane.getChildren().add(parentPane);
        
       //////////////////////////////////////////////////////////////////////////
        variablePane = new HBox();
        
        Label variableName = new Label("Variables: ");
        variableName.setFont(Font.font("Arial", 10));
        variablePane.getChildren().add(variableName);
        
        addButton = initChildButton(variablePane, PropertyType.ADD_ICON.toString(), PropertyType.ADD_TOOLTIP.toString(), true);
        minusButton = initChildButton(variablePane, PropertyType.MINUS_ICON.toString(), PropertyType.MINUS_TOOLTIP.toString(), true);
        
        addButton.setOnAction(eh -> {
            if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
            
            vy.showAddVariableDialog();
            
            if (vy.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            UMLVariables si = vy.getVariable();
            currentVariable = si;
            table1.getItems().add(currentVariable);
            
            if(currentPane instanceof UMLClasses){
                UMLClasses j = (UMLClasses) currentPane;
                
                j.getVariableNames().add(currentVariable);
                
                String tmp1 = currentVariable.toString();
                Text temp = new Text(tmp1);
                
                textTemp.put(tmp1, temp);
                
                j.setCurrentVariableName(textTemp.get(tmp1));
            }
            if(currentPane instanceof UMLInterfaces){
                UMLInterfaces k = (UMLInterfaces) currentPane;
                
                k.getVariableNames().add(currentVariable);
                
                String tmp1 = currentVariable.toString();
                Text temp = new Text(tmp1);
                
                textTemp.put(tmp1, temp);
                
                k.setCurrentVariableName(textTemp.get(tmp1));
            }
        }
        else {
        }    
        });
        
        minusButton.setOnAction( e -> {
            if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
            
            if(currentVariable != null){
                if(currentPane instanceof UMLClasses){
                    UMLClasses j = (UMLClasses) currentPane;
                    j.getVariableNames().remove(currentVariable);
                    
                    j.removeCurrentVariableName(textTemp.get(currentVariable.toString()));
                    table1.getItems().remove(currentVariable);
                    
                }
                if(currentPane instanceof UMLInterfaces){
                    UMLInterfaces j = (UMLInterfaces) currentPane;
                    j.getVariableNames().remove(currentVariable);
                    
                    j.removeCurrentVariableName(textTemp.get(currentVariable.toString()));
                    table1.getItems().remove(currentVariable);
                }
                gui.updateToolbarControls(false);
                gui.updatePhotoCodeButton();
            }
        });
        
        
        rightPane.getChildren().add(variablePane);
        
      //////////////////////////////////////////////////////////////////////////
        variableTablePane = new ScrollPane();
        variableTablePane.setMaxSize(350, 200);
        
        table1 = new TableView();
        table1.setEditable(true);
        
        NameCol1 = new TableColumn("Name");
        TypeCol1 = new TableColumn("Type");
        StaticCol1 = new TableColumn("Static");
        AccessCol1 = new TableColumn("Access");
        
        NameCol1.setCellValueFactory(new PropertyValueFactory<UMLVariables, String>("name"));
        TypeCol1.setCellValueFactory(new PropertyValueFactory<UMLVariables, String>("type"));
        StaticCol1.setCellValueFactory(new PropertyValueFactory<UMLVariables, Boolean>("statictype"));
        AccessCol1.setCellValueFactory(new PropertyValueFactory<UMLVariables, String>("accesstype"));
        
        table1.getColumns().addAll(NameCol1, TypeCol1, StaticCol1, AccessCol1);
        table1.setPrefWidth(350);
        table1.setPrefHeight(200);

        varPane.getChildren().add(table1);
        variableTablePane.setContent(varPane);
        rightPane.getChildren().add(variableTablePane);
        
         table1.setOnMouseClicked(e -> {
             if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
             
            if(e.getClickCount() == 1 ){ //need for remove rows
                currentVariable = (UMLVariables) table1.getSelectionModel().getSelectedItem();
                temp00 = currentVariable.toString();
             }
             
             
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                UMLVariables temps = currentVariable;
                UMLVariables k = (UMLVariables) table1.getSelectionModel().getSelectedItem();
                vy.showEditVariableDialog(k);
                
                if (vy.wasCompleteSelected()) {
                    // UPDATE THE SCHEDULE ITEM
                    UMLVariables si = vy.getVariable();
                    k.setName(si.getName()); //this will update the variable in the array automatically
                    k.setType(si.getType());
                    k.setAccesstype(si.getAccesstype());
                    
                    k.setStatictype(si.isStatictype());
                    
                    if(currentPane instanceof UMLClasses){
                        currentVariable = temps;
                        
                        UMLClasses l = (UMLClasses) currentPane;
                        
                        l.removeCurrentVariableName(textTemp.get(temp00));
                        currentVariable = k;
                
                        String tmp1 = currentVariable.toString();
                        Text temp = new Text(tmp1);
                
                        textTemp.put(tmp1, temp);
                
                        l.setCurrentVariableName(textTemp.get(tmp1));
                        
                    }
                    
                    if(currentPane instanceof UMLInterfaces){
                        //Do something for interfaces
                        currentVariable = temps;
                        
                        UMLInterfaces i = (UMLInterfaces) currentPane;
                        
                        i.removeCurrentVariableName(textTemp.get(temp00));
                        currentVariable = k;
                        
                        String tmp1 = currentVariable.toString();
                        Text temp = new Text(tmp1);
                        
                        textTemp.put(tmp1, temp);
                        
                        i.setCurrentVariableName(textTemp.get(tmp1));
                        
                    }
                    gui.updateToolbarControls(false);
                    gui.updatePhotoCodeButton();
                }
        else {
        }   
        }
        });
        
        ////////////////////////////////////////////////////////////////////////
        methodPane = new HBox();
        
        Label methodName = new Label("Methods: ");
        methodName.setFont(Font.font("Arial", 10));
        methodPane.getChildren().add(methodName);
        
        addMButton = initChildButton(methodPane, PropertyType.ADD_ICON.toString(), PropertyType.ADD_TOOLTIP.toString(), false);
        minusMButton = initChildButton(methodPane, PropertyType.MINUS_ICON.toString(), PropertyType.MINUS_TOOLTIP.toString(), false);
        
        rightPane.getChildren().add(methodPane);
        
        //ADDING AND REMOVING METHODS!!!!!!!
        addMButton.setOnAction(eh -> {
            if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
            
            my.showAddMethodDialog();
            
            if (my.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            UMLMethods me = my.getMethods();
            currentMethod = me;
            table2.getItems().add(currentMethod);
            
            if(currentPane instanceof UMLClasses){
                UMLClasses j = (UMLClasses) currentPane;
                
                j.getMethodNames().add(currentMethod);
                
                String tmp2 = currentMethod.toString();
                Text temp2 = new Text(tmp2);
                
                textTemp.put(tmp2, temp2);
                
                j.setCurrentMethodName(textTemp.get(tmp2));
            }
            if(currentPane instanceof UMLInterfaces){
                UMLInterfaces k = (UMLInterfaces) currentPane;
                
                k.getMethodNames().add(currentMethod);
                
                String tmp2 = currentMethod.toString();
                Text temp2 = new Text(tmp2);
                
                textTemp.put(tmp2, temp2);
                
                k.setCurrentVariableName(textTemp.get(tmp2));
            }
        }
        else {
        }    
        });
        
        minusMButton.setOnAction( e -> {
            if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
            
            if(currentMethod != null){
                if(currentPane instanceof UMLClasses){
                    UMLClasses j = (UMLClasses) currentPane;
                    j.getMethodNames().remove(currentMethod);
                    
                    j.removeCurrentMethodName(textTemp.get(currentMethod.toString()));
                    table2.getItems().remove(currentMethod);
                    
                }
                if(currentPane instanceof UMLInterfaces){
                    UMLInterfaces j = (UMLInterfaces) currentPane;
                    j.getMethodNames().remove(currentMethod);
                    
                    j.removeCurrentMethodName(textTemp.get(currentMethod.toString()));
                    table2.getItems().remove(currentMethod);
                }
                gui.updateToolbarControls(false);
                gui.updatePhotoCodeButton();
            }
        });
        
        ////////////////////////////////////////////////////////////////////////
        methodTablePane = new ScrollPane();
        methodTablePane.setMaxSize(350, 200);
        table2 = new TableView();
        table2.setEditable(true);
        
        TableColumn NameCol2 = new TableColumn("Name");
        TableColumn ReturnCol2 = new TableColumn("Return");
        TableColumn StaticCol2 = new TableColumn("Static");
        TableColumn AbstractCol2 = new TableColumn("Abstract");
        TableColumn AccessCol2 = new TableColumn("Access");
        TableColumn Arg0 = new TableColumn("Arg0");
        
        NameCol2.setCellValueFactory(new PropertyValueFactory<UMLMethods, String>("name"));
        ReturnCol2.setCellValueFactory(new PropertyValueFactory<UMLMethods, String>("returntype"));
        StaticCol2.setCellValueFactory(new PropertyValueFactory<UMLMethods, Boolean>("statictype"));
        AbstractCol2.setCellValueFactory(new PropertyValueFactory<UMLMethods, Boolean>("abstractype"));
        AccessCol2.setCellValueFactory(new PropertyValueFactory<UMLMethods, String>("accesstype"));
        
        table2.getColumns().addAll(NameCol2, ReturnCol2, StaticCol2, AbstractCol2, AccessCol2,
                Arg0);
        
        metPane.getChildren().add(table2);
        methodTablePane.setContent(metPane);
        rightPane.getChildren().add(methodTablePane);
        
    
    ((BorderPane) workspace).setCenter(tempo); //set the leftPane to the left of the BorderPane
    ((BorderPane) workspace).setRight(rightPane); 
    

    //METHOD DIALOG AND EDITING!!!!!!!!!!!!
    table2.setOnMouseClicked(e -> {
             if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
             
            if(e.getClickCount() == 1 ){ //need for remove rows
                currentMethod = (UMLMethods) table2.getSelectionModel().getSelectedItem();
                temp01 = currentMethod.toString();
             }
             
             
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                UMLMethods temps = currentMethod;
                UMLMethods me = (UMLMethods) table2.getSelectionModel().getSelectedItem();
                my.showEditVariableDialog(me);
                
                if (my.wasCompleteSelected()) {
                    // UPDATE THE SCHEDULE ITEM
                    UMLMethods jul = my.getMethods();
                    me.setName(jul.getName()); //this will update the variable in the array automatically
                    me.setReturntype(jul.getReturntype());
                    me.setAccesstype(jul.getAccesstype());
                    me.setStatictype(jul.isStatictype());
                    me.setAbstractype(jul.isAbstractype());
                    
                    if(currentPane instanceof UMLClasses){
                        currentMethod = temps;
                        
                        UMLClasses l = (UMLClasses) currentPane;
                        
                        l.removeCurrentMethodName(textTemp.get(temp01));
                        currentMethod = me;
                
                        String tmp2 = currentMethod.toString();
                        Text temp2 = new Text(tmp2);
                
                        textTemp.put(tmp2, temp2);
                
                        l.setCurrentMethodName(textTemp.get(tmp2));
                        
                    }
                    
                    if(currentPane instanceof UMLInterfaces){
                        //Do something for interfaces
                        currentMethod = temps;
                        
                        UMLInterfaces i = (UMLInterfaces) currentPane;
                        
                        i.removeCurrentMethodName(textTemp.get(temp01));
                        currentMethod = me;
                        
                        String tmp2 = currentMethod.toString();
                        Text temp2 = new Text(tmp2);
                        
                        textTemp.put(tmp2, temp2);
                        
                        i.setCurrentMethodName(textTemp.get(tmp2));
                        
                    }
                    gui.updateToolbarControls(false);
                    gui.updatePhotoCodeButton();
                }
        else {
        }   
        }
        });
    
    }
    /////////////////////////////////////////////////////////////////////////////
          
    public void createListener(VBox s){
        s.setOnMousePressed((MouseEvent e) -> {
           
        DataManager expo = (DataManager) app.getDataComponent();
        removeButton.setDisable(false);
        resizeButton.setDisable(false);
        addButton.setDisable(false);
        minusButton.setDisable(false);
        if(selectedButton == selectionButton || selectedButton == resizeButton){
           if (currentPane != null){
            currentPane.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff");
            //prevPane = currentPane;
           }
           currentPane = s; 

           currentPane.setStyle("-fx-border-color: #ffff00; -fx-background-color: #ffffff");
           
           if (currentPane instanceof UMLClasses){
               UMLClasses lol = (UMLClasses) currentPane; //!!!!!!!!!!!!!!!
                if(dummy != null)
                    dummy.setText(lol.getClassNametoString());
                if(dummyData != null)
                    dummyData.setText(lol.getPackageName());
                table1.getItems().clear();
                table1.getItems().addAll(lol.getVariableNames());
                table2.getItems().clear();
                table2.getItems().addAll(lol.getMethodNames());
                
                parentComboBox.getItems().clear();
                for(VBox sh: expo.getClassList()){
                    if(sh instanceof UMLClasses)
                        parentComboBox.getItems().add(((UMLClasses) sh).getClassNametoString());
                    if(sh instanceof UMLInterfaces)
                        parentComboBox.getItems().add(((UMLInterfaces) sh).getInterNametoString());
                }  
            }
           if(currentPane instanceof UMLInterfaces){
               UMLInterfaces st = (UMLInterfaces) currentPane;
               if(dummy != null)
                   dummy.setText(st.getInterNametoString());
               if(dummy != null)
                   dummyData.setText(st.getPackageName());
                table1.getItems().clear();
                table1.getItems().addAll(st.getVariableNames());
                table2.getItems().clear();
                table2.getItems().addAll(st.getMethodNames());
               
               for(VBox sh: expo.getClassList()){
                    if(sh instanceof UMLClasses)
                        parentComboBox.getItems().add(((UMLClasses) sh).getClassNametoString());
                    if(sh instanceof UMLInterfaces)
                        parentComboBox.getItems().add(((UMLInterfaces) sh).getInterNametoString());
                }
           }
        }   
        
        });
        s.setOnMouseDragged(e -> {
            DataManager expo = (DataManager) app.getDataComponent();
            if(selectedButton == selectionButton){ //dragging
            gui.updateToolbarControls(false);
            gui.updatePhotoCodeButton();
            if(s instanceof UMLClasses){
                UMLClasses lol = (UMLClasses) s;
                lol.setTranslateX(e.getX() + s.getTranslateX());
                lol.setTranslateY(e.getY() + s.getTranslateY());
                lol.setTranslateXer(e.getX() + s.getTranslateX());
                lol.setTranslateYer(e.getY() + s.getTranslateY());
            }
            if(s instanceof UMLInterfaces){
                UMLInterfaces st = (UMLInterfaces) s;
                st.setTranslateX(e.getX() + s.getTranslateX());
                st.setTranslateY(e.getY() + s.getTranslateY());
                st.setTranslateXer(e.getX() + s.getTranslateX());
                st.setTranslateYer(e.getY() + s.getTranslateY());
            }
            }
            if(selectedButton == resizeButton && currentPane.getCursor() == Cursor.CROSSHAIR){ //resizing
                if(s instanceof UMLClasses){
                    UMLClasses lol = (UMLClasses) s;
                    lol.setMinWidth(e.getSceneX() - lol.getTranslateX());
                    lol.setMaxWidth(e.getSceneX() - lol.getTranslateX());
                    lol.setWidthy(lol.getWidth());
                    //set the setHeight(e.getY() - lol.getTranslateX()) in the UMLClasses
                    lol.setMinHeight(e.getSceneY() - lol.getTranslateY());
                    lol.setMaxHeight(e.getSceneY() - lol.getTranslateY());
                    lol.setHeighty(lol.getHeight());
                }
                if(s instanceof UMLInterfaces){
                    UMLInterfaces st = (UMLInterfaces) s;
                    st.setMinWidth(e.getSceneX() - st.getTranslateX());
                    st.setMaxWidth(e.getSceneX() - st.getTranslateX());
                    st.setWidthy(st.getWidth());
                    st.setMinHeight(e.getSceneY() - st.getTranslateY());
                    st.setMaxHeight(e.getSceneY() - st.getTranslateY());
                    st.setHeighty(st.getHeight());
                }
                
            }
        });
        
        s.setOnMouseReleased(e -> {
            if(snapping == true){
                double currentpositionX = s.getTranslateX();
                double currentpositionY = s.getTranslateY();
                
                if(currentpositionX % 10 != 0){
                    while(currentpositionX % 10 != 0){
                        currentpositionX++;
                        currentpositionX = (int) Math.round(currentpositionX);
                    }
                }
                if(currentpositionY % 10 != 0){
                    while(currentpositionY % 10 != 0){
                        currentpositionY++;
                        currentpositionY = (int) Math.round(currentpositionY);
                    }
                }
                
                s.setTranslateX(currentpositionX);
                s.setTranslateY(currentpositionY);
                //set the translatex into the specific pane.
            }
        });
    }
    
    public void gridding(ActionEvent e){
        if(gridBox.isSelected()){   
            
          
          for(int i = 0; i < 1500; i+=10){
            s = new Line();
            s.setStartY(0);
            s.setEndY(1500); //vertical
            s.setStartX(i);
            s.setEndX(i);
            s.setFill(Color.BLACK);
            s.setStroke(Color.BLACK);
            s.setStyle("line");
            
            leftPane.getChildren().add(s);
            s.toBack();
            }
          
          for(int i = 0; i < 1500; i+=10){
              l = new Line();
              l.setStartX(0);
              l.setEndX(1500);
              l.setStartY(i);
              l.setEndY(i);
              l.setFill(Color.BLACK);
              l.setStroke(Color.BLACK);
              l.setStyle("line");
              
              leftPane.getChildren().add(l);
              l.toBack();
          }
          
          //leftPane.getStyleClass().add("grid_lines");
          
        }
        if(!gridBox.isSelected()){
            //leftPane.getStyleClass().remove("grid_lines");
            
            for(int i = 0; i < leftPane.getChildren().size(); i++){
                Node n = leftPane.getChildren().get(i);
                if (n instanceof Line){
                    
                    leftPane.getChildren().remove(n);
                    i--;
                }
            }
            
        }
    }

    public void initStyle(){
       rightPane.getStyleClass().add("max_pane");
       classPane.getStyleClass().add("bordered_pane");
       packagePane.getStyleClass().add("bordered_pane");
       parentPane.getStyleClass().add("bordered_pane");
       variablePane.getStyleClass().add("bordered_pane");
       methodPane.getStyleClass().add("bordered_pane");
    }
    
    public Button initChildButton(Pane toolbar, String icon, String tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(icon);
        Image buttonImage = new Image(imagePath);
	
	// NOW MAKE THE BUTTON
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        button.setTooltip(buttonTooltip);
	
	// PUT THE BUTTON IN THE TOOLBAR
        toolbar.getChildren().add(button);
	
	// AND RETURN THE COMPLETED BUTTON
        return button;
    }
      
    public void resetWorkspace(){
        leftPane.getChildren().clear();
        dummy.clear();
        dummyData.clear();     
        table1.getItems().clear();
        table2.getItems().clear();
        selectionButton.setDisable(true);
        resizeButton.setDisable(true);
        removeButton.setDisable(true);
        undoButton.setDisable(true);
        redoButton.setDisable(true);
        zoominButton.setDisable(true);
        zoomoutButton.setDisable(true);
        addButton.setDisable(true);
        minusButton.setDisable(true);
        gui.updateToolbarControls(true);
        
    }
    
    public void photoGo(){
    	try {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File(PATH_WORK));
            fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
            fc.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter( "PNG Files", "*.png"));

            File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
                
            if (selectedFile != null) {
                WritableImage wi = new WritableImage(1500, 1500);
                WritableImage snapshot = (leftPane.snapshot(new SnapshotParameters(), wi));
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", selectedFile);
		}
            }catch (IOException ex) {
                Logger.getLogger(TakeSnapShoot.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {
        DataManager expo = (DataManager) app.getDataComponent();
        leftPane.getChildren().clear();
        for(VBox s : expo.getClassList()){
           createListener(s);
           
           if(s instanceof UMLClasses){
               UMLClasses lol = (UMLClasses) s;
               
               lol.setTranslateX(lol.getTranslateXer());
               lol.setTranslateY(lol.getTranslateYer());
               lol.setPrefWidth(lol.getWidthy());
               lol.setPrefHeight(lol.getHeighty());
               leftPane.getChildren().add(lol);
           }
           if(s instanceof UMLInterfaces){
               UMLInterfaces st = (UMLInterfaces) s;
               
               st.setTranslateX(st.getTranslateXer());
               st.setTranslateY(st.getTranslateYer());
               st.setPrefWidth(st.getWidthy());
               st.setPrefHeight(st.getHeighty());
               leftPane.getChildren().add(st);
           }
           
            currentPane = s;
            addButton.setDisable(false);
            minusButton.setDisable(false);
        }     
        currentPane.setStyle("-fx-border-color: #ffff00");
        
        leftPane.setCursor(Cursor.DEFAULT);
        selectedButton = selectionButton; //bugger
        selectionButton.setDisable(true);
        
        if(currentPane instanceof UMLClasses){
            UMLClasses f = (UMLClasses) currentPane;
            dummy.setText(f.getClassNametoString());
            dummyData.setText(f.getPackageName());
        }
        if(currentPane instanceof UMLInterfaces){
            UMLInterfaces f = (UMLInterfaces) currentPane;
            dummy.setText(f.getInterNametoString());
            dummyData.setText(f.getPackageName());
        }
    }
}