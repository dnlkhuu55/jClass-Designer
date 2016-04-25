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
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
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
    VBox table1;
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
    
    VBox prevPane = null;
    VBox currentPane = null;
    FlowPane fileToolbarPane;
    FlowPane editToolbarPane;
    FlowPane viewToolbarPane;
    HBox wholePane;
    Line s;
    Line l;
    
    boolean snapping = false;
    
    // HERE ARE OUR DIALOGS
    AppMessageDialogSingleton messageDialog;
    AppYesNoCancelDialogSingleton yesNoCancelDialog;

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
        });
       
        leftPane.setOnMousePressed(e -> {
            if (e.getTarget() instanceof Pane && !(e.getTarget() instanceof VBox)){
            if(currentPane != null && selectedButton == selectionButton){
                currentPane.setStyle("-fx-border-width: 1px");
                currentPane.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff");
                currentPane = null;
                prevPane = currentPane;
                selectedButton = null;
                selectionButton.setDisable(false);
            }}
        }); 
         
        resizeButton.setOnAction(eh -> {
            leftPane.setCursor(Cursor.CROSSHAIR);
            selectionButton = resizeButton;
        });
               
        addClassButton.setOnAction(e -> {
            selectedButton = addClassButton; //LOAD INTO TOOLBAR BY DEFAULT
            resizeButton.setDisable(false);
            removeButton.setDisable(false);
            undoButton.setDisable(false);
            redoButton.setDisable(false);
            zoominButton.setDisable(false);
            zoomoutButton.setDisable(false);
            gui.updateToolbarControls(false);
            gui.updatePhotoCodeButton();
            leftPane.setCursor(Cursor.DEFAULT);

            sc = new UMLClasses("DEFAULT");
            sc.setStyle("-fx-border-color: #ffff00; -fx-background-color: #ffffff");
            //sc.setIsAbstract(true);
            sc.setClassName("DEFAULT");
            sc.setClassNametoString("DEFAULT"); //currentText
            sc.setPackageName(""); //currentText
            

            /*
            var = new UMLVariables();
            var.setName("Hello");
            var.setParent(sc.getVariableNames.get(0));
            var.setType("int");
            var.setStatictype(true);
            var.setAccesstype(false);
            sc.setCurrentVariableName(new Text(var.toString()));
            sc.getVariableNames.add(var);
            */
            /*
            methods = new UMLMethods("Name1", "void", true, true, true);
            methods.getArgs().add("Dummy");
            //to fix the comma, loop until you hit n-2 elements in array. Do the last args manually.
            sc.setCurrentMethodName(new Text(methods.toString()));
            sc.getMethodNames().add(methods);
            */
            
            
            
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
        });
        
        addInterfaceButton.setOnAction(eh -> {
            selectedButton = addClassButton; //LOAD INTO TOOLBAR BY DEFAULT
            resizeButton.setDisable(false);
            removeButton.setDisable(false);
            undoButton.setDisable(false);
            redoButton.setDisable(false);
            zoominButton.setDisable(false);
            zoomoutButton.setDisable(false);
            gui.updateToolbarControls(false);
            gui.updatePhotoCodeButton();
            leftPane.setCursor(Cursor.DEFAULT);

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
        });
        

        removeButton.setOnAction(eh -> {
            leftPane.setCursor(Cursor.DEFAULT);
            if(currentPane instanceof VBox){
                leftPane.getChildren().remove(currentPane);
                currentPane = null;
                
                selectionButton.setDisable(true);
                resizeButton.setDisable(true);
                removeButton.setDisable(true);
                //update the text fields
                
            }
        });
        
        resizeButton.setOnAction(eh -> {
            selectedButton = resizeButton;
            tempo.setCursor(Cursor.CROSSHAIR);
            //Change this
            selectionButton.setDisable(false);
        });
        
        zoominButton.setOnAction(eh -> {
            for(Node s: expo.getClassList()){
                s.setScaleX(s.getScaleX() * 2);
                s.setScaleY(s.getScaleY() * 2);
            }
        });
        
        zoomoutButton.setOnAction(eh -> {
            for(Node s: expo.getClassList()){
                s.setScaleX(s.getScaleX() /2);
                s.setScaleY(s.getScaleY() / 2);
            }
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
            "NOTHING",
            "NULL",
            "HI",
            "COME ON"  
        );
        parentPane.getChildren().add(parentComboBox);
        
        rightPane.getChildren().add(parentPane);
        
       //////////////////////////////////////////////////////////////////////////
        variablePane = new HBox();
        
        Label variableName = new Label("Variables: ");
        variableName.setFont(Font.font("Arial", 10));
        variablePane.getChildren().add(variableName);
        
        addButton = initChildButton(variablePane, PropertyType.ADD_ICON.toString(), PropertyType.ADD_TOOLTIP.toString(), false);
        minusButton = initChildButton(variablePane, PropertyType.MINUS_ICON.toString(), PropertyType.MINUS_TOOLTIP.toString(), false);
        
        addButton.setOnAction(eh -> {
            UMLVariables var = new UMLVariables();
            table1.getChildren().add(var.settingStuff());
        });
        
        
        rightPane.getChildren().add(variablePane);
        
      //////////////////////////////////////////////////////////////////////////
        variableTablePane = new ScrollPane();
        variableTablePane.setMaxSize(350, 200);
        /*
        table1 = new TableView();
        table1.setEditable(true);
        
        TableColumn NameCol1 = new TableColumn("Name");
        TableColumn TypeCol1 = new TableColumn("Type");
        TableColumn StaticCol1 = new TableColumn("Static");
        TableColumn AccessCol1 = new TableColumn("Access");
        
        table1.getColumns().addAll(NameCol1, TypeCol1, StaticCol1, AccessCol1);
        table1.setPrefWidth(350);
        table1.setPrefHeight(200);

        varPane.getChildren().add(table1);
        */
        table1 = new VBox(); //try gridpane later
        table1.setMinHeight(400);
        
        //Header
        HBox header = new HBox();
        
        //Column Headers
        HBox namecol1 = new HBox();
        Text n = new Text("Name");
        namecol1.getChildren().add(n);
        namecol1.setPrefWidth(100);
        
        HBox typecol1 = new HBox();
        Text t = new Text("Type");
        typecol1.getChildren().add(t);
        typecol1.setPrefWidth(100);
        
        HBox staticcol1 = new HBox();
        Text s = new Text("Static");
        staticcol1.getChildren().add(s);
        staticcol1.setPrefWidth(100);
        
        HBox accesscol1 = new HBox();
        Text a = new Text("Access");
        accesscol1.getChildren().add(a);
        accesscol1.setPrefWidth(100);
        
        header.getChildren().addAll(namecol1, typecol1, staticcol1, accesscol1);
        table1.getChildren().add(header);
        varPane.getChildren().add(table1);
        variableTablePane.setContent(varPane);
        rightPane.getChildren().add(variableTablePane);
        
        ////////////////////////////////////////////////////////////////////////
        methodPane = new HBox();
        
        Label methodName = new Label("Methods: ");
        methodName.setFont(Font.font("Arial", 10));
        methodPane.getChildren().add(methodName);
        
        addMButton = initChildButton(methodPane, PropertyType.ADD_ICON.toString(), PropertyType.ADD_TOOLTIP.toString(), false);
        minusMButton = initChildButton(methodPane, PropertyType.MINUS_ICON.toString(), PropertyType.MINUS_TOOLTIP.toString(), false);
        
        rightPane.getChildren().add(methodPane);
        
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
        TableColumn Arg1 = new TableColumn("Arg1");
        
        table2.getColumns().addAll(NameCol2, ReturnCol2, StaticCol2, AbstractCol2, AccessCol2,
                Arg1);
        
        metPane.getChildren().add(table2);
        methodTablePane.setContent(metPane);
        rightPane.getChildren().add(methodTablePane);
    
    ((BorderPane) workspace).setCenter(tempo); //set the leftPane to the left of the BorderPane
    ((BorderPane) workspace).setRight(rightPane); 
    }
    /////////////////////////////////////////////////////////////////////////////
          
    public void createListener(VBox s){
        s.setOnMousePressed((MouseEvent e) -> {
           
        DataManager expo = (DataManager) app.getDataComponent();
        removeButton.setDisable(false);
        resizeButton.setDisable(false);
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
            if(selectedButton == resizeButton && tempo.getCursor() == Cursor.CROSSHAIR){ //resizing
                if(s instanceof UMLClasses){
                    UMLClasses lol = (UMLClasses) s;
                    lol.setMinWidth(e.getSceneX() - lol.getTranslateX());
                    lol.setMaxWidth(e.getSceneX() - lol.getTranslateX());
                    //set the setHeight(e.getY() - lol.getTranslateX()) in the UMLClasses
                    lol.setMinHeight(e.getSceneY() - lol.getTranslateY());
                    lol.setMaxHeight(e.getSceneY() - lol.getTranslateY());
                }
                if(s instanceof UMLInterfaces){
                    UMLInterfaces st = (UMLInterfaces) s;
                    st.setMinWidth(e.getSceneX() - st.getTranslateX());
                    st.setMaxWidth(e.getSceneX() - st.getTranslateX());
                    st.setMinHeight(e.getSceneY() - st.getTranslateY());
                    st.setMaxHeight(e.getSceneY() - st.getTranslateY());
                }
                
            }
        });
        
        s.setOnMouseReleased(e -> {
            if(snapping == true){
                double currentpositionX = s.getTranslateX();
                double currentpositionY = s.getTranslateY();
                
                if(currentpositionX % 10 != 0){
                    while(currentpositionX % 10 != 0)
                        currentpositionX++;
                }
                if(currentpositionY % 10 != 0){
                    while(currentpositionY % 10 != 0)
                        currentpositionY++;
                }
                s.setTranslateX(currentpositionX);
                s.setTranslateY(currentpositionY);
                //set the translatex into the specific pane.
            }
            //check x value
            //if not 20, snap to next lowest number divisible by 20 (for loop to check)
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
              s.setFill(Color.BLACK);
              s.setStroke(Color.BLACK);
              s.setStyle("line");
              
              leftPane.getChildren().add(l);
              l.toBack();
          }
        }
        if(!gridBox.isSelected()){
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
        selectionButton.setDisable(true);
        resizeButton.setDisable(true);
        removeButton.setDisable(true);
        undoButton.setDisable(true);
        redoButton.setDisable(true);
        zoominButton.setDisable(true);
        zoomoutButton.setDisable(true);
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
               leftPane.getChildren().add(lol);
           }
           if(s instanceof UMLInterfaces){
               UMLInterfaces st = (UMLInterfaces) s;
               
               st.setTranslateX(st.getTranslateXer());
               st.setTranslateY(st.getTranslateYer());
               leftPane.getChildren().add(st);
           }
           
            currentPane = s;
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