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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jcd.data.ClassLines;
import jcd.data.UMLClasses;
import jcd.data.UMLInterfaces;
import jcd.data.UMLMArgs;
import jcd.data.UMLMethods;
import jcd.data.UMLVariables;
import jcd.data.URManager;
import jcd.file.UndoRedoManager;

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

    ScrollPane tempo, variableTablePane, methodTablePane;
    Pane leftPane, varPane, metPane, argPane;
    VBox rightPane;
    HBox classPane, packagePane, parentPane, variablePane, methodPane, wholePane;
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
    Button addAButton = new Button();
    Button minusAButton = new Button();
    Button removeParentButton = new Button();
    TextField dummy = new TextField();
    TextField dummyData = new TextField();
    TableView table1, table2, table3;
    TableColumn NameCol1, TypeCol1, StaticCol1, AccessCol1;
    Button selectedButton;
    Text prevText = new Text();
    Text currentText = new Text();
    ComboBox parentComboBox;
    
    CheckBox gridBox, snapBox;
    VBox checkBox = new VBox();
    
    static final int BUTTON_TAG_WIDTH = 40;
    UMLClasses sc, toLine;
    UMLInterfaces ie, toInterface;
    UMLVariables var;
    UMLMethods methods;
    String temp00, temp01;
    
    VBox prevPane = null;
    VBox currentPane = null;
    UMLVariables currentVariable;
    UMLMethods currentMethod;
    UMLMArgs currentArg;
    FlowPane fileToolbarPane, editToolbarPane, viewToolbarPane;
    Line s, l;
    
    HashMap<String,Text> textTemp = new HashMap<>(); 
    
    boolean snapping = false;
    
    // HERE ARE OUR DIALOGS
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;
    VariablesDialog vy;
    MethodsDialog my;
    ArgsDialog ay;
    ClassLines ba;
    
    //UndoRedoManager doing = new UndoRedoManager();
    URManager urtest = new URManager();

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
       ay = new ArgsDialog(null);

       workspace = new BorderPane();
       leftPane = new Pane(); 
       varPane = new Pane();
       metPane = new Pane();
       argPane = new Pane();
       leftPane.setPrefSize(1500, 1500);
       tempo = new ScrollPane();
       tempo.setContent(leftPane);
       expo.setLeftPane(leftPane);
       urtest.undoing(app.getDataComponent());
       
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
            if (currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
        });
       
        leftPane.setOnMousePressed(e -> {
            if (e.getTarget() instanceof Pane && !(e.getTarget() instanceof VBox)){
            if(currentPane != null && selectedButton == selectionButton){
                currentPane.setStyle("-fx-border-width: 1px");
                currentPane.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff");
                currentPane = null;
                table1.getItems().clear();
                table2.getItems().clear();
                table3.getItems().clear();
                prevPane = currentPane;
                selectedButton = null;
                selectionButton.setDisable(false);
                addButton.setDisable(true);
                minusButton.setDisable(true);
                addMButton.setDisable(true);
                minusMButton.setDisable(true);
                addAButton.setDisable(true); //arguments button!!!!!
                minusAButton.setDisable(true);
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
            addMButton.setDisable(false);
            minusMButton.setDisable(false);
            addAButton.setDisable(false);
            minusAButton.setDisable(false);
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
        table3.getItems().clear();
        
        urtest.undoing(app.getDataComponent());
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
            addMButton.setDisable(false);
            minusMButton.setDisable(false);
            addAButton.setDisable(true);
            minusAButton.setDisable(true);
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
        table3.getItems().clear();
        
        urtest.undoing(app.getDataComponent());
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
            urtest.undoing(app.getDataComponent());
        });
        
        resizeButton.setOnAction(eh -> {
            selectedButton = resizeButton;
            
            if (currentPane != null)
                currentPane.setCursor(Cursor.CROSSHAIR);
            //Change this
            selectionButton.setDisable(false);
        });
        
        //undo
        undoButton.setOnAction(e -> {
           try {
               urtest.loadUndo(app.getDataComponent());
           } catch (CloneNotSupportedException ex) {
               Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
           }
            reloadWorkspace();
        });
        
        redoButton.setOnAction(e -> {
           try {
               urtest.loadRedo(app.getDataComponent());
           } catch (CloneNotSupportedException ex) {
               Logger.getLogger(Workspace.class.getName()).log(Level.SEVERE, null, ex);
           }
            reloadWorkspace();
        });
        
        
        zoominButton.setOnAction(eh -> {
            if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
            leftPane.setScaleX(leftPane.getScaleX() * 2);
            leftPane.setScaleY(leftPane.getScaleY() * 2);
            expo.setLeftPane(leftPane);
            urtest.undoing(app.getDataComponent());
        });
        
        zoomoutButton.setOnAction(eh -> {
            if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
            leftPane.setScaleX(leftPane.getScaleX() / 2);
            leftPane.setScaleY(leftPane.getScaleY() / 2);
            expo.setLeftPane(leftPane);
            urtest.undoing(app.getDataComponent());
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
            urtest.undoing(app.getDataComponent());
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
            urtest.undoing(app.getDataComponent());
        });
        
        rightPane.getChildren().add(packagePane);
        
        ////////////////////////////////////////////////////////////////////////
        parentPane = new HBox();
        parentComboBox = new ComboBox();
        Label parentName = new Label("Parent: ");
        parentName.setFont(Font.font("Arial", 10));
        parentPane.getChildren().add(parentName);
        
        parentComboBox.setEditable(true);
        parentPane.getChildren().add(parentComboBox);
        removeParentButton = initChildButton(parentPane, PropertyType.REMOVE_ICON.toString(), PropertyType.REMOVE_TOOLTIP.toString(), false);
        rightPane.getChildren().add(parentPane);

        parentComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try{
                String acessy = newValue.toString();
                int flag_0 = 0; //custom external class 
                for(VBox h: expo.getClassList()){ //we need to check if the string is a class or interface
                    if(h instanceof UMLClasses){
                        if(acessy.equals(((UMLClasses) h).getClassNametoString())){
                            flag_0 = 1;
                            toLine = (UMLClasses) h;
                        }
                    }
                    if (h instanceof UMLInterfaces){
                        if(acessy.equals(((UMLInterfaces) h).getInterNametoString())){
                            flag_0 = 2;
                            toInterface = (UMLInterfaces) h;
                        }
                    }
                }
                if (currentPane instanceof UMLClasses){
                    int flag = 0; //0 means you must add the interface parent
                    UMLClasses now = (UMLClasses) currentPane; //interfaces doesn't have parents
                    if(flag_0 == 2){
                        for(String h : now.getParentInterfaces()){
                            if(acessy.equals(h))
                            flag = 1; //1 means you already added it
                        }
                        if(flag == 0){
                            now.getParentInterfaces().add(acessy);
                            ba = new ClassLines();
                            ba.setStartX(currentPane.getTranslateX());
                            ba.setStartY(currentPane.getTranslateY());
                            ba.setEndX(toInterface.getTranslateX());
                            ba.setEndY(toInterface.getTranslateY());
                            ba.startXProperty().bind(currentPane.translateXProperty());
                            ba.startYProperty().bind(currentPane.translateYProperty());
                            ba.endXProperty().bind(toInterface.translateXProperty());
                            ba.endYProperty().bind(toInterface.translateYProperty());
                            ba.setStart_node(now.getClassNametoString());
                            ba.setEnd_node(toInterface.getInterNametoString());
                            leftPane.getChildren().add(ba);
                            expo.getLineList().add(ba);
                        }
                    }
                    if(flag_0 == 1){
                        now.setParentName(acessy);
                        ba = new ClassLines();
                        //add the line here
                        //Line s = new Line();
                        ba.setStartX(currentPane.getTranslateX());
                        ba.setStartY(currentPane.getTranslateY());
                        ba.setEndX(toLine.getTranslateX());
                        ba.setEndY(toLine.getTranslateY());
                        //s.setEndY(100);
                        ba.startXProperty().bind(currentPane.translateXProperty());
                        ba.startYProperty().bind(currentPane.translateYProperty());
                        ba.endXProperty().bind(toLine.translateXProperty());
                        ba.endYProperty().bind(toLine.translateYProperty());
                        ba.setStart_node(now.getClassNametoString());
                        ba.setEnd_node(toLine.getClassNametoString());
                        leftPane.getChildren().add(ba); //test
                        expo.getLineList().add(ba);
                    }
                    if(flag_0 == 0){
                        now.setParentName(acessy);
                        
                        UMLClasses ex = new UMLClasses(acessy, "", "", "", "", 150, 150);
                        createListener(ex);
                        leftPane.getChildren().add(ex);
                        
                        ba = new ClassLines();
                        ba.setStartX(currentPane.getTranslateX());
                        ba.setStartY(currentPane.getTranslateY());
                        ba.setEndX(ex.getTranslateX());
                        ba.setEndY(ex.getTranslateY());
                        ba.startXProperty().bind(currentPane.translateXProperty());
                        ba.startYProperty().bind(currentPane.translateYProperty());
                        ba.endXProperty().bind(ex.translateXProperty());
                        ba.endYProperty().bind(ex.translateYProperty());
                        ba.setStart_node(now.getClassNametoString());
                        ba.setEnd_node(ex.getClassNametoString());
                        leftPane.getChildren().add(ba);
                        expo.getLineList().add(ba);
                    }
                } 
                
                if(currentPane instanceof UMLInterfaces){
                    int flag = 0; //0 means you must add the interface parent
                    UMLInterfaces now = (UMLInterfaces) currentPane; //interfaces doesn't have parents
                    if(flag_0 == 2){
                        for(String h : now.getParentInterfaces()){
                            if(acessy.equals(h))
                            flag = 1; //1 means you already added it
                        }
                        if(flag == 0){
                            now.getParentInterfaces().add(acessy);
                            ba = new ClassLines();
                            ba.setStartX(currentPane.getTranslateX());
                            ba.setStartY(currentPane.getTranslateY());
                            ba.setEndX(toInterface.getTranslateX());
                            ba.setEndY(toInterface.getTranslateY());
                            ba.startXProperty().bind(currentPane.translateXProperty());
                            ba.startYProperty().bind(currentPane.translateYProperty());
                            ba.endXProperty().bind(toInterface.translateXProperty());
                            ba.endYProperty().bind(toInterface.translateYProperty());
                            ba.setStart_node(now.getInterNametoString());
                            ba.setEnd_node(toInterface.getInterNametoString());
                            leftPane.getChildren().add(ba);
                            expo.getLineList().add(ba);
                        }}
                }
                
                urtest.undoing(app.getDataComponent());
            }catch(Exception e){
                        System.out.println("Clicking error");
                    }
            }
        }
        );
        
        removeParentButton.setOnAction(e -> {
            String lol = (String) parentComboBox.getSelectionModel().getSelectedItem();
            
            if(currentPane != null){
                if(currentPane instanceof UMLClasses){
                    UMLClasses no = (UMLClasses) currentPane;
                    if(lol.equals(no.getParentName())){
                        no.setParentName("");
                    }
                    else{
                        no.getParentInterfaces().remove(lol);
                    }
                }
                
                
                parentComboBox.getSelectionModel().clearSelection();
                urtest.undoing(app.getDataComponent());
            }
        });
        
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
                int flaggy = 0;
                
                for(VBox s: expo.getClassList()){
                    if(s instanceof UMLClasses){
                        if(currentVariable.getType().equals(((UMLClasses) s).getClassNametoString())){
                            if(!j.getParentName().equals(((UMLClasses) s).getClassNametoString())){
                                flaggy = 1;
                                ba = new ClassLines();
                                ba.setStartX(currentPane.getTranslateX());
                                ba.setStartY(currentPane.getTranslateY());
                                ba.setEndX(s.getTranslateX());
                                ba.setEndY(s.getTranslateY());
                                ba.startXProperty().bind(currentPane.translateXProperty());
                                ba.startYProperty().bind(currentPane.translateYProperty());
                                ba.endXProperty().bind(s.translateXProperty());
                                ba.endYProperty().bind(s.translateYProperty());
                                ba.setStart_node(j.getClassNametoString());
                                ba.setEnd_node(((UMLClasses) s).getClassNametoString());
                                leftPane.getChildren().add(ba);
                                expo.getLineList().add(ba);
                                j.setParentName(((UMLClasses) s).getClassNametoString());
                            }
                        }
                    }
                }
                
                if(flaggy == 0){
                    if(!currentVariable.getType().contains("int")){
                        if(!currentVariable.getType().contains("double")){
                            if(!currentVariable.getType().contains("boolean")){
                                if(!currentVariable.getType().contains("float")){
                                    if(!currentVariable.getType().contains("char")){
                                        if(!currentVariable.getType().contains("byte")){
                                            if(!currentVariable.getType().contains("short")){
                                                if(!currentVariable.getType().contains("long")){
                        UMLClasses ex = new UMLClasses(currentVariable.getType());
                        createListener(ex);
                        leftPane.getChildren().add(ex);
                        expo.getClassList().add(ex);
                        j.setParentName(ex.getClassNametoString());
                        
                        ba = new ClassLines();
                        ba.setStartX(currentPane.getTranslateX());
                        ba.setStartY(currentPane.getTranslateY());
                        ba.setEndX(ex.getTranslateX());
                        ba.setEndY(ex.getTranslateY());
                        ba.startXProperty().bind(currentPane.translateXProperty());
                        ba.startYProperty().bind(currentPane.translateYProperty());
                        ba.endXProperty().bind(ex.translateXProperty());
                        ba.endYProperty().bind(ex.translateYProperty());
                        ba.setStart_node(j.getClassNametoString());
                        ba.setEnd_node(ex.getClassNametoString());
                        leftPane.getChildren().add(ba);
                        expo.getLineList().add(ba);
                        
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                }
                }
                urtest.undoing(app.getDataComponent());
            }
            if(currentPane instanceof UMLInterfaces){
                UMLInterfaces k = (UMLInterfaces) currentPane;
                
                k.getVariableNames().add(currentVariable);
                
                String tmp1 = currentVariable.toString();
                Text temp = new Text(tmp1);
                
                textTemp.put(tmp1, temp);
                
                k.setCurrentVariableName(textTemp.get(tmp1));
                urtest.undoing(app.getDataComponent());
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
                urtest.undoing(app.getDataComponent());
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
                        
                        int flaggy = 0;
                
                for(VBox s: expo.getClassList()){
                    if(s instanceof UMLClasses){
                        if(currentVariable.getType().equals(((UMLClasses) s).getClassNametoString())){
                            if(!l.getParentName().equals(((UMLClasses) s).getClassNametoString())){
                                flaggy = 1;
                                ba = new ClassLines();
                                ba.setStartX(currentPane.getTranslateX());
                                ba.setStartY(currentPane.getTranslateY());
                                ba.setEndX(s.getTranslateX());
                                ba.setEndY(s.getTranslateY());
                                ba.startXProperty().bind(currentPane.translateXProperty());
                                ba.startYProperty().bind(currentPane.translateYProperty());
                                ba.endXProperty().bind(s.translateXProperty());
                                ba.endYProperty().bind(s.translateYProperty());
                                ba.setStart_node(l.getClassNametoString());
                                ba.setEnd_node(((UMLClasses) s).getClassNametoString());
                                leftPane.getChildren().add(ba);
                                expo.getLineList().add(ba);
                                l.setParentName(((UMLClasses) s).getClassNametoString());
                            }
                        }
                    }
                }
                
                if(flaggy == 0){
                    if(!currentVariable.getType().contains("int")){
                        if(!currentVariable.getType().contains("double")){
                            if(!currentVariable.getType().contains("boolean")){
                                if(!currentVariable.getType().contains("float")){
                                    if(!currentVariable.getType().contains("char")){
                                        if(!currentVariable.getType().contains("byte")){
                                            if(!currentVariable.getType().contains("short")){
                                                if(!currentVariable.getType().contains("long")){
                        UMLClasses ex = new UMLClasses(currentVariable.getType());
                        createListener(ex);
                        leftPane.getChildren().add(ex);
                        expo.getClassList().add(ex);
                        l.setParentName(ex.getClassNametoString());
                        
                        ba = new ClassLines();
                        ba.setStartX(currentPane.getTranslateX());
                        ba.setStartY(currentPane.getTranslateY());
                        ba.setEndX(ex.getTranslateX());
                        ba.setEndY(ex.getTranslateY());
                        ba.startXProperty().bind(currentPane.translateXProperty());
                        ba.startYProperty().bind(currentPane.translateYProperty());
                        ba.endXProperty().bind(ex.translateXProperty());
                        ba.endYProperty().bind(ex.translateYProperty());
                        ba.setStart_node(l.getClassNametoString());
                        ba.setEnd_node(ex.getClassNametoString());
                        leftPane.getChildren().add(ba);
                        expo.getLineList().add(ba);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                }
                        
                    }}
                    
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
                    urtest.undoing(app.getDataComponent());
                }
        else {
        }   }
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
            UMLMethods me = my.getMethods();
            currentMethod = me;
            table2.getItems().add(currentMethod);
            table3.getItems().clear();
            addAButton.setDisable(false);
            minusAButton.setDisable(false);
            
            if(currentPane instanceof UMLClasses){
                UMLClasses j = (UMLClasses) currentPane;
                
                j.getMethodNames().add(currentMethod);
                
                String tmp2 = currentMethod.toString();
                Text temp2 = new Text(tmp2);
                
                textTemp.put(tmp2, temp2);
                
                j.setCurrentMethodName(textTemp.get(tmp2));
                
                
                          int flaggy = 0;
                
                for(VBox s: expo.getClassList()){
                    if(s instanceof UMLClasses){
                        if(currentMethod.getReturntype().equals(((UMLClasses) s).getClassNametoString())){
                            if(!currentMethod.getReturntype().equals(((UMLClasses) s).getClassNametoString())){
                                flaggy = 1;
                                ba = new ClassLines();
                                ba.setStartX(currentPane.getTranslateX());
                                ba.setStartY(currentPane.getTranslateY());
                                ba.setEndX(s.getTranslateX());
                                ba.setEndY(s.getTranslateY());
                                ba.startXProperty().bind(currentPane.translateXProperty());
                                ba.startYProperty().bind(currentPane.translateYProperty());
                                ba.endXProperty().bind(s.translateXProperty());
                                ba.endYProperty().bind(s.translateYProperty());
                                ba.setStart_node(j.getClassNametoString());
                                ba.setEnd_node(((UMLClasses) s).getClassNametoString());
                                leftPane.getChildren().add(ba);
                                expo.getLineList().add(ba);
                                j.setParentName(((UMLClasses) s).getClassNametoString());
                            }
                        }
                    }
                }
                
                if(flaggy == 0){
                    if(!currentMethod.getReturntype().contains("int")){
                        if(!currentMethod.getReturntype().contains("double")){
                            if(!currentMethod.getReturntype().contains("boolean")){
                                if(!currentMethod.getReturntype().contains("float")){
                                    if(!currentMethod.getReturntype().contains("char")){
                                        if(!currentMethod.getReturntype().contains("byte")){
                                            if(!currentMethod.getReturntype().contains("short")){
                                                if(!currentMethod.getReturntype().contains("long")){
                        UMLClasses ex = new UMLClasses(currentMethod.getReturntype());
                        createListener(ex);
                        leftPane.getChildren().add(ex);
                        expo.getClassList().add(ex);
                        j.setParentName(ex.getClassNametoString());
                        
                        ba = new ClassLines();
                        ba.setStartX(currentPane.getTranslateX());
                        ba.setStartY(currentPane.getTranslateY());
                        ba.setEndX(ex.getTranslateX());
                        ba.setEndY(ex.getTranslateY());
                        ba.startXProperty().bind(currentPane.translateXProperty());
                        ba.startYProperty().bind(currentPane.translateYProperty());
                        ba.endXProperty().bind(ex.translateXProperty());
                        ba.endYProperty().bind(ex.translateYProperty());
                        ba.setStart_node(j.getClassNametoString());
                        ba.setEnd_node(ex.getClassNametoString());
                        leftPane.getChildren().add(ba);
                        expo.getLineList().add(ba);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                }
                        
                    }
                
                
            }
            if(currentPane instanceof UMLInterfaces){
                UMLInterfaces k = (UMLInterfaces) currentPane;
                
                k.getMethodNames().add(currentMethod);
                
                String tmp2 = currentMethod.toString();
                Text temp2 = new Text(tmp2);
                
                textTemp.put(tmp2, temp2);
                
                k.setCurrentMethodName(textTemp.get(tmp2));
            }
            urtest.undoing(app.getDataComponent());
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
                currentMethod = null;
                addAButton.setDisable(false);
                minusAButton.setDisable(false);
                urtest.undoing(app.getDataComponent());
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

    //METHOD DIALOG AND EDITING!!!!!!!!!!!!
        table2.setOnMouseClicked(e -> {
             if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
             
            if(e.getClickCount() == 1 ){ //need for remove rows
                currentMethod = (UMLMethods) table2.getSelectionModel().getSelectedItem();
                temp01 = currentMethod.toString();
                table3.getItems().clear();
                table3.getItems().addAll(currentMethod.getArgs()); 
                addAButton.setDisable(false);
                minusAButton.setDisable(false);
             }

            if (e.getClickCount() == 2) {
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
                        addAButton.setDisable(false);
                        minusAButton.setDisable(false);
                        
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
                        addAButton.setDisable(false);
                        minusAButton.setDisable(false);
                        
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
                    urtest.undoing(app.getDataComponent());
                }
        else {
        }}
        }    
    );
    /////////////////////////////////////////////////////////////////////////////
    HBox ArgumentsPane = new HBox();
        
        Label TestName = new Label("Arguments: ");
        TestName.setFont(Font.font("Arial", 10));
        ArgumentsPane.getChildren().add(TestName);
        
        addAButton = initChildButton(ArgumentsPane, PropertyType.ADD_ICON.toString(), PropertyType.ADD_TOOLTIP.toString(), false);
        minusAButton = initChildButton(ArgumentsPane, PropertyType.MINUS_ICON.toString(), PropertyType.MINUS_TOOLTIP.toString(), false);
        rightPane.getChildren().add(ArgumentsPane);
        
        addAButton.setOnAction(eh -> {
            if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
            
            ay.showAddArgsDialog();
            
            if (ay.wasCompleteSelected()) {
            UMLMArgs ae = ay.getArgs();
            currentArg = ae;
            table3.getItems().add(currentArg);
           
            if(currentMethod != null){
                if(currentPane instanceof UMLClasses){
                    UMLClasses j = (UMLClasses) currentPane;
                    j.getMethodNames().remove(currentMethod);
                    
                    j.removeCurrentMethodName(textTemp.get(currentMethod.toString()));

                    currentMethod.addUMLMArgs(currentArg);
                    j.getMethodNames().add(currentMethod);
                
                String tmp2 = currentMethod.toString();
                Text temp2 = new Text(tmp2);
                
                textTemp.put(tmp2, temp2);
                
                j.setCurrentMethodName(textTemp.get(tmp2));
                
                int flaggy = 0;
                
                for(VBox s: expo.getClassList()){
                    if(s instanceof UMLClasses){
                        if(currentArg.getArgstype().equals(((UMLClasses) s).getClassNametoString())){
                            if(!j.getParentName().equals(((UMLClasses) s).getClassNametoString())){
                                flaggy = 1;
                                ba = new ClassLines();
                                ba.setStartX(currentPane.getTranslateX());
                                ba.setStartY(currentPane.getTranslateY());
                                ba.setEndX(s.getTranslateX());
                                ba.setEndY(s.getTranslateY());
                                ba.startXProperty().bind(currentPane.translateXProperty());
                                ba.startYProperty().bind(currentPane.translateYProperty());
                                ba.endXProperty().bind(s.translateXProperty());
                                ba.endYProperty().bind(s.translateYProperty());
                                ba.setStart_node(j.getClassNametoString());
                                ba.setEnd_node(((UMLClasses) s).getClassNametoString());
                                leftPane.getChildren().add(ba);
                                expo.getLineList().add(ba);
                                j.setParentName(((UMLClasses) s).getClassNametoString());
                            }
                        }
                    }
                }
                
                if(flaggy == 0){
                    if(!currentArg.getArgstype().contains("int")){
                        if(!currentArg.getArgstype().contains("double")){
                            if(!currentArg.getArgstype().contains("boolean")){
                                if(!currentArg.getArgstype().contains("float")){
                                    if(!currentArg.getArgstype().contains("char")){
                                        if(!currentArg.getArgstype().contains("byte")){
                                            if(!currentArg.getArgstype().contains("short")){
                                                if(!currentArg.getArgstype().contains("long")){
                        UMLClasses ex = new UMLClasses(currentArg.getArgstype());
                        createListener(ex);
                        leftPane.getChildren().add(ex);
                        expo.getClassList().add(ex);
                        j.setParentName(ex.getClassNametoString());
                        
                        ba = new ClassLines();
                        ba.setStartX(currentPane.getTranslateX());
                        ba.setStartY(currentPane.getTranslateY());
                        ba.setEndX(ex.getTranslateX());
                        ba.setEndY(ex.getTranslateY());
                        ba.startXProperty().bind(currentPane.translateXProperty());
                        ba.startYProperty().bind(currentPane.translateYProperty());
                        ba.endXProperty().bind(ex.translateXProperty());
                        ba.endYProperty().bind(ex.translateYProperty());
                        ba.setStart_node(j.getClassNametoString());
                        ba.setEnd_node(ex.getClassNametoString());
                        leftPane.getChildren().add(ba);
                        expo.getLineList().add(ba);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                }}
                    
                }
                if(currentPane instanceof UMLInterfaces){
                    UMLInterfaces j = (UMLInterfaces) currentPane;
                    j.getMethodNames().remove(currentMethod);
                    
                    j.removeCurrentMethodName(textTemp.get(currentMethod.toString()));
                    currentMethod.addUMLMArgs(currentArg);
                    j.getMethodNames().add(currentMethod);
                    
                    String tmp2 = currentMethod.toString();
                Text temp2 = new Text(tmp2);
                
                textTemp.put(tmp2, temp2);
                
                j.setCurrentMethodName(textTemp.get(tmp2));
                }
                gui.updateToolbarControls(false);
                gui.updatePhotoCodeButton();
                urtest.undoing(app.getDataComponent());
            }          
        } 
        else {
        }    
        });
        
        minusAButton.setOnAction( e -> {
            if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
            
            if(currentMethod != null){
                if(currentPane instanceof UMLClasses){
                    table3.getItems().remove(currentArg);
                    UMLClasses j = (UMLClasses) currentPane;
                    j.getMethodNames().remove(currentMethod);
                    
                    j.removeCurrentMethodName(textTemp.get(currentMethod.toString()));
                    
                    currentMethod.removeUMLArgs(currentArg);
                    j.getMethodNames().add(currentMethod);
                
                String tmp2 = currentMethod.toString();
                Text temp2 = new Text(tmp2);
                
                textTemp.put(tmp2, temp2);
                
                j.setCurrentMethodName(textTemp.get(tmp2));
                    
                }
                if(currentPane instanceof UMLInterfaces){
                    table3.getItems().remove(currentArg);
                    UMLInterfaces j = (UMLInterfaces) currentPane;
                    j.getMethodNames().remove(currentMethod);
                    
                    j.removeCurrentMethodName(textTemp.get(currentMethod.toString()));
                    
                    currentMethod.removeUMLArgs(currentArg);
                    
                    j.getMethodNames().add(currentMethod);
                
                String tmp2 = currentMethod.toString();
                Text temp2 = new Text(tmp2);
                
                textTemp.put(tmp2, temp2);
                
                j.setCurrentMethodName(textTemp.get(tmp2));
                
                }
                gui.updateToolbarControls(false);
                gui.updatePhotoCodeButton();
                urtest.undoing(app.getDataComponent());
            }
        });
        
      //////////////////////////////////////////////////////////////////////////
        ScrollPane argScrollPane = new ScrollPane();
        argScrollPane.setMaxSize(350, 175);
        
        table3 = new TableView();
        table3.setEditable(true);
        
        TableColumn NameCol3 = new TableColumn("Arguments");
        NameCol3.setPrefWidth(350);
        NameCol3.setCellValueFactory(new PropertyValueFactory<UMLMArgs, String>("argstype"));
        
        table3.getColumns().addAll(NameCol3);
        table3.setPrefWidth(350);
        table3.setPrefHeight(175);

        argPane.getChildren().add(table3);
        argScrollPane.setContent(argPane);
        rightPane.getChildren().add(argScrollPane);
        
        
        table3.setOnMouseClicked(e -> {
             if(currentPane != null)
                currentPane.setCursor(Cursor.DEFAULT);
             
            if(e.getClickCount() == 1 ){ //need for remove rows
                currentArg = (UMLMArgs) table3.getSelectionModel().getSelectedItem();
                temp01 = currentMethod.toString();
             }

            if (e.getClickCount() == 2) {
                UMLMethods temps = currentMethod;
                UMLMArgs al = (UMLMArgs) table3.getSelectionModel().getSelectedItem();
                ay.showEditArgsDialog(al);
                
                if (ay.wasCompleteSelected()) {
                    // UPDATE THE SCHEDULE ITEM
                    UMLMArgs jul = ay.getArgs();
                    al.setArgstype(jul.getArgstype()); //this will update the variable in the array automatically
                    
                    if(currentPane instanceof UMLClasses){
                        currentMethod = temps;
                        
                        UMLClasses l = (UMLClasses) currentPane;
                        
                        l.removeCurrentMethodName(textTemp.get(temp01));
                        currentMethod.removeUMLArgs(currentArg);
                        currentMethod.addUMLMArgs(al);
                        currentArg = al;
                
                        String tmp2 = currentMethod.toString();
                        Text temp2 = new Text(tmp2);
                
                        textTemp.put(tmp2, temp2);
                
                        l.setCurrentMethodName(textTemp.get(tmp2));
                        
                        int flaggy = 0;
                
                for(VBox s: expo.getClassList()){
                    if(s instanceof UMLClasses){
                        if(currentArg.getArgstype().equals(((UMLClasses) s).getClassNametoString())){
                            if(!l.getParentName().equals(((UMLClasses) s).getClassNametoString())){
                                flaggy = 1;
                                ba = new ClassLines();
                                ba.setStartX(currentPane.getTranslateX());
                                ba.setStartY(currentPane.getTranslateY());
                                ba.setEndX(s.getTranslateX());
                                ba.setEndY(s.getTranslateY());
                                ba.startXProperty().bind(currentPane.translateXProperty());
                                ba.startYProperty().bind(currentPane.translateYProperty());
                                ba.endXProperty().bind(s.translateXProperty());
                                ba.endYProperty().bind(s.translateYProperty());
                                ba.setStart_node(l.getClassNametoString());
                                ba.setEnd_node(((UMLClasses) s).getClassNametoString());
                                leftPane.getChildren().add(ba);
                                expo.getLineList().add(ba);
                                l.setParentName(((UMLClasses) s).getClassNametoString());
                            }
                        }
                    }
                }
                
                if(flaggy == 0){
                    if(!currentArg.getArgstype().contains("int")){
                        if(!currentArg.getArgstype().contains("double")){
                            if(!currentArg.getArgstype().contains("boolean")){
                                if(!currentArg.getArgstype().contains("float")){
                                    if(!currentArg.getArgstype().contains("char")){
                                        if(!currentArg.getArgstype().contains("byte")){
                                            if(!currentArg.getArgstype().contains("short")){
                                                if(!currentArg.getArgstype().contains("long")){
                        UMLClasses ex = new UMLClasses(currentArg.getArgstype());
                        createListener(ex);
                        leftPane.getChildren().add(ex);
                        expo.getClassList().add(ex);
                        l.setParentName(ex.getClassNametoString());
                        
                        ba = new ClassLines();
                        ba.setStartX(currentPane.getTranslateX());
                        ba.setStartY(currentPane.getTranslateY());
                        ba.setEndX(ex.getTranslateX());
                        ba.setEndY(ex.getTranslateY());
                        ba.startXProperty().bind(currentPane.translateXProperty());
                        ba.startYProperty().bind(currentPane.translateYProperty());
                        ba.endXProperty().bind(ex.translateXProperty());
                        ba.endYProperty().bind(ex.translateYProperty());
                        ba.setStart_node(l.getClassNametoString());
                        ba.setEnd_node(ex.getClassNametoString());
                        leftPane.getChildren().add(ba);
                        expo.getLineList().add(ba);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                }}
                        
                    }
                    
                    if(currentPane instanceof UMLInterfaces){
                        //Do something for interfaces
                        currentMethod = temps;
                        
                        UMLInterfaces i = (UMLInterfaces) currentPane;
                        
                        i.removeCurrentMethodName(textTemp.get(temp01));
                        currentMethod.removeUMLArgs(currentArg);
                        currentMethod.addUMLMArgs(al);
                        currentArg = al;
                        
                        String tmp2 = currentMethod.toString();
                        Text temp2 = new Text(tmp2);
                        
                        textTemp.put(tmp2, temp2);
                        
                        i.setCurrentMethodName(textTemp.get(tmp2));
                        
                    }
                    gui.updateToolbarControls(false);
                    gui.updatePhotoCodeButton();
                    urtest.undoing(app.getDataComponent());
                }
        else {
        }}
        }    
    );
      
        ///////////////////////////////////////////////////////////////////////////    
    ((BorderPane) workspace).setCenter(tempo); //set the leftPane to the left of the BorderPane
    ((BorderPane) workspace).setRight(rightPane); 
    
    }
    /////////////////////////////////////////////////////////////////////////////
          
    public void createListener(VBox s){
        s.setOnMousePressed((MouseEvent e) -> {
           
        DataManager expo = (DataManager) app.getDataComponent();
        removeButton.setDisable(false);
        resizeButton.setDisable(false);
        addButton.setDisable(false);
        minusButton.setDisable(false);
        addMButton.setDisable(false);
        minusMButton.setDisable(false);
        addAButton.setDisable(true);
        minusAButton.setDisable(true);
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
                table3.getItems().clear();
                if(!lol.getMethodNames().isEmpty()){
                    if(!lol.getMethodNames().get(0).getArgs().isEmpty())
                        table3.getItems().addAll(lol.getMethodNames().get(0).getArgs());
                }
                
                parentComboBox.getItems().clear();
                for(VBox sh: expo.getClassList()){
                    if(sh instanceof UMLClasses)
                        parentComboBox.getItems().add(((UMLClasses) sh).getClassNametoString());
                    if(sh instanceof UMLInterfaces)
                        parentComboBox.getItems().add(((UMLInterfaces) sh).getInterNametoString());
                }  
                if(!lol.getParentName().equals(""))
                    parentComboBox.getSelectionModel().select(lol.getParentName());
                else if (!lol.getParentInterfaces().isEmpty()){
                    parentComboBox.setValue(lol.getParentInterfaces().get(0));
                }
                else{
                    //DO NOTHING
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
                table3.getItems().clear();
                if(!st.getMethodNames().isEmpty()){
                    if(!st.getMethodNames().get(0).getArgs().isEmpty())
                        table3.getItems().addAll(st.getMethodNames().get(0).getArgs());
                }
               
               parentComboBox.getItems().clear();
                for(VBox sh: expo.getClassList()){
                    if(sh instanceof UMLInterfaces)
                        parentComboBox.getItems().add(((UMLInterfaces) sh).getInterNametoString());
                }  
                if (!st.getParentInterfaces().isEmpty()){
                    parentComboBox.setValue(st.getParentInterfaces().get(0));
                }
                else{
                    //DO NOTHING
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
                urtest.undoing(app.getDataComponent());
            }
            if(s instanceof UMLInterfaces){
                UMLInterfaces st = (UMLInterfaces) s;
                st.setTranslateX(e.getX() + s.getTranslateX());
                st.setTranslateY(e.getY() + s.getTranslateY());
                st.setTranslateXer(e.getX() + s.getTranslateX());
                st.setTranslateYer(e.getY() + s.getTranslateY());
                urtest.undoing(app.getDataComponent());
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
                    urtest.undoing(app.getDataComponent());
                }
                if(s instanceof UMLInterfaces){
                    UMLInterfaces st = (UMLInterfaces) s;
                    st.setMinWidth(e.getSceneX() - st.getTranslateX());
                    st.setMaxWidth(e.getSceneX() - st.getTranslateX());
                    st.setWidthy(st.getWidth());
                    st.setMinHeight(e.getSceneY() - st.getTranslateY());
                    st.setMaxHeight(e.getSceneY() - st.getTranslateY());
                    st.setHeighty(st.getHeight());
                    urtest.undoing(app.getDataComponent());
                }
                
            }
        });
        
        s.setOnMouseReleased(e -> {
            DataManager expo = (DataManager) app.getDataComponent();
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
                urtest.undoing(app.getDataComponent());
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
        }
        if(!gridBox.isSelected()){
            for(int i = 0; i < leftPane.getChildren().size(); i++){
                Node n = leftPane.getChildren().get(i);
                if (n instanceof Line){
                    if(!(n instanceof ClassLines)){
                    leftPane.getChildren().remove(n);
                    i--;
                    }
                }
            }
        }
    }
    public void trying(String lol){
        DataManager expo = (DataManager) app.getDataComponent();
        for(ClassLines sc: expo.getLineList()){
                    if(sc.getEnd_node().equals(lol)){
                        leftPane.getChildren().remove(sc);
                        expo.getLineList().remove(sc);
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
        DataManager expo = (DataManager) app.getDataComponent();
        expo.getClassList().clear();
        expo.getLineList().clear();
        leftPane.getChildren().clear();
        leftPane.setScaleX(1);
        leftPane.setScaleY(1);
        expo.setLeftPane(leftPane);
        dummy.clear();
        dummyData.clear();     
        table1.getItems().clear();
        table2.getItems().clear();
        table3.getItems().clear();
        selectionButton.setDisable(true);
        resizeButton.setDisable(true);
        removeButton.setDisable(true);
        undoButton.setDisable(true);
        redoButton.setDisable(true);
        zoominButton.setDisable(true);
        zoomoutButton.setDisable(true);
        addButton.setDisable(true);
        minusButton.setDisable(true);
        addMButton.setDisable(true);
        minusMButton.setDisable(true);
        addAButton.setDisable(true);
        minusAButton.setDisable(true);
        snapping = false;
        gridBox.setSelected(false);
        snapBox.setSelected(false);
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
        leftPane.setScaleX(expo.getLeftPane().getScaleX());
        leftPane.setScaleY(expo.getLeftPane().getScaleY());
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
            addMButton.setDisable(false);
            minusMButton.setDisable(false);
        }     
        
                   
        for(ClassLines cl: expo.getLineList()){
               leftPane.getChildren().add(cl);
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