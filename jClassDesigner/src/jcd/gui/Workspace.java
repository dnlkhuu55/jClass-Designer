package jcd.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
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
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import static javafx.scene.layout.GridPane.setRowIndex;
import javafx.scene.text.Text;
import jcd.data.UMLClasses;

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
    TableView table1 = new TableView();
    TableView table2 = new TableView();
    Button selectedButton;
    Text prevText = new Text();
    Text currentText = new Text();
    
    CheckBox gridBox, snapBox;
    VBox checkBox = new VBox();
    
    static final int BUTTON_TAG_WIDTH = 40;
    UMLClasses sc;
    
    UMLClasses prevPane = null;
    UMLClasses currentPane = null;
    FlowPane fileToolbarPane;
    FlowPane editToolbarPane;
    FlowPane viewToolbarPane;
    HBox wholePane;
    
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
       leftPane.setPrefSize(2000, 2000);
       tempo = new ScrollPane();
       tempo.setContent(leftPane);
       
       fileToolbarPane = gui.getFileToolbarPane();
       editToolbarPane = gui.getEditToolbarPane();
       viewToolbarPane = gui.getViewToolbarPane();
        
        
       rightPane = new VBox();
       tempo.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
       leftPane.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
        

        photoButton = initChildButton(fileToolbarPane, PropertyType.PHOTO_ICON.toString(), PropertyType.PHOTO_TOOLTIP.toString(), false);
        codeButton = initChildButton(fileToolbarPane, PropertyType.CODE_ICON.toString(), PropertyType.CODE_TOOLTIP.toString(), false);
        
        selectionButton = initChildButton(editToolbarPane, PropertyType.SELECTION_ICON.toString(), PropertyType.SELECTION_TOOLTIP.toString(), false);
        resizeButton = initChildButton(editToolbarPane, PropertyType.RESIZE_ICON.toString(), PropertyType.RESIZE_TOOLTIP.toString(), false);
        addClassButton = initChildButton(editToolbarPane, PropertyType.ADDCLASS_ICON.toString(), PropertyType.ADDCLASS_TOOLTIP.toString(), false);
        addInterfaceButton = initChildButton(editToolbarPane, PropertyType.ADDINTERFACE_ICON.toString(), PropertyType.ADDINTERFACE_TOOLTIP.toString(), false);
        removeButton = initChildButton(editToolbarPane, PropertyType.REMOVE_ICON.toString(), PropertyType.REMOVE_TOOLTIP.toString(), false);
        undoButton = initChildButton(editToolbarPane, PropertyType.UNDO_ICON.toString(), PropertyType.UNDO_TOOLTIP.toString(), false);
        redoButton = initChildButton(editToolbarPane, PropertyType.REDO_ICON.toString(), PropertyType.REDO_TOOLTIP.toString(), false);
        zoominButton = initChildButton(viewToolbarPane, PropertyType.ZOOMIN_ICON.toString(), PropertyType.ZOOMIN_TOOLTIP.toString(), false);
        zoomoutButton = initChildButton(viewToolbarPane, PropertyType.ZOOMOUT_ICON.toString(), PropertyType.ZOOMOUT_TOOLTIP.toString(), false);
        
        checkBox.setSpacing(5);
        checkBox.setPadding(new Insets(5));
        gridBox = new CheckBox("Grid");
        snapBox = new CheckBox("Snap");
        checkBox.getChildren().addAll(gridBox, snapBox);
        viewToolbarPane.getChildren().add(checkBox);
        
        fileToolbarPane.setHgap(10);
        editToolbarPane.setHgap(10);
        viewToolbarPane.setHgap(10);
        
        
            //ADD CAMERA BUTTON
        photoButton.setOnAction(e -> {
	try {
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(PATH_WORK));
		fc.setTitle(props.getProperty(SAVE_WORK_TITLE));
		fc.getExtensionFilters().addAll(
		new FileChooser.ExtensionFilter( "PNG Files", "*.png"));

		File selectedFile = fc.showSaveDialog(app.getGUI().getWindow());
                
		if (selectedFile != null) {
		    WritableImage wi = new WritableImage(1000, 700);
                WritableImage snapshot = (leftPane.snapshot(new SnapshotParameters(), wi));
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", selectedFile);
		}
           
            } catch (IOException ex) {
                Logger.getLogger(TakeSnapShoot.class.getName()).log(Level.SEVERE, null, ex);
            }
	});
        
        
        selectionButton.setOnAction(e -> {
	    tempo.setCursor(Cursor.DEFAULT);
            selectedButton = selectionButton;
        });
        
        addClassButton.setOnAction(e -> {
            selectedButton = addClassButton; //LOAD INTO TOOLBAAR BY DEFAULT

            sc = new UMLClasses(new Text("DEFAULT"), new Text(" "), new Text(" "));
            sc.setStyle("-fx-background-color: #ffffff");
            sc.setStyle("-fx-border-width: 10000px");
            sc.setStyle("-fx-border-color: #ffff00");
            sc.setClassNametoString("DEFAULT"); //currentText
            sc.setPackageName(" "); //currentText
                 
            createListener(sc);
            
            sc.setLayoutX(10);
            sc.setLayoutY(10);
            
            sc.setSceneX(10);
            sc.setSceneY(10);
            sc.setTranslateXer(10);
            sc.setTranslateYer(10);
            sc.setGridLinesVisible(true);
            
        if (currentPane != null){
            currentPane.setStyle("-fx-border-width: 1px");
            currentPane.setStyle("-fx-border-color: #000000");
            prevPane = currentPane;
           }
        currentPane = sc;
        leftPane.getChildren().add(sc);
            
        if(dummy != null)
            dummy.setText(currentPane.getClassNametoString());
        if(dummyData != null) //ERROR HERE
            dummyData.setText(currentPane.getPackageName());

        });
        
        /////////////////////////////////////////////////////////////////////////////
        classPane = new HBox();
        
        Label className = new Label("Class Name:");
        className.setFont(Font.font("Arial", 10));
        classPane.getChildren().add(className);
        
        classPane.getChildren().add(dummy);  
        
        dummy.setOnKeyReleased((KeyEvent k) -> {
            if(currentPane != null){
                currentPane.setClassName(dummy.getText());
                currentPane.setClassNametoString(dummy.getText());
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
            if(currentPane != null){
                currentPane.setPackageName(dummyData.getText());
                
            }
        });
        
        rightPane.getChildren().add(packagePane);
        
        ////////////////////////////////////////////////////////////////////////
        parentPane = new HBox();
        
        Label parentName = new Label("Parent: ");
        parentName.setFont(Font.font("Arial", 10));
        parentPane.getChildren().add(parentName);
        
        final ComboBox emailComboBox = new ComboBox();
        emailComboBox.getItems().addAll(
            "jacob.smith@example.com",
            "isabella.johnson@example.com",
            "ethan.williams@example.com",
            "emma.jones@example.com",
            "michael.brown@example.com"  
        );
        parentPane.getChildren().add(emailComboBox);
        
        rightPane.getChildren().add(parentPane);
        
       //////////////////////////////////////////////////////////////////////////
        variablePane = new HBox();
        
        Label variableName = new Label("Variables: ");
        variableName.setFont(Font.font("Arial", 10));
        variablePane.getChildren().add(variableName);
        
        addButton = initChildButton(variablePane, PropertyType.ADD_ICON.toString(), PropertyType.ADD_TOOLTIP.toString(), false);
        minusButton = initChildButton(variablePane, PropertyType.MINUS_ICON.toString(), PropertyType.MINUS_TOOLTIP.toString(), false);
        
        rightPane.getChildren().add(variablePane);
        
      //////////////////////////////////////////////////////////////////////////
        variableTablePane = new ScrollPane();
        table1.setEditable(true);
        
        TableColumn NameCol1 = new TableColumn("Name");
        TableColumn TypeCol1 = new TableColumn("Type");
        TableColumn StaticCol1 = new TableColumn("Static");
        TableColumn AccessCol1 = new TableColumn("Access");
        
        table1.getColumns().addAll(NameCol1, TypeCol1, StaticCol1, AccessCol1);
        table1.setPrefWidth(350);
        table1.setPrefHeight(200);

        variableTablePane.setContent(table1);
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
        table2.setEditable(true);
        
        TableColumn NameCol2 = new TableColumn("Name");
        TableColumn ReturnCol2 = new TableColumn("Return");
        TableColumn StaticCol2 = new TableColumn("Static");
        TableColumn AbstractCol2 = new TableColumn("Abstract");
        TableColumn AccessCol2 = new TableColumn("Access");
        TableColumn Arg1 = new TableColumn("Arg1");
        TableColumn Arg2 = new TableColumn("Arg2");
        
        table2.getColumns().addAll(NameCol2, ReturnCol2, StaticCol2, AbstractCol2, AccessCol2,
                Arg1, Arg2);
        table2.setPrefWidth(350);
        table2.setPrefHeight(300);
        methodTablePane.setContent(table2);
        rightPane.getChildren().add(methodTablePane);
    
    ((BorderPane) workspace).setCenter(tempo); //set the leftPane to the left of the BorderPane
    ((BorderPane) workspace).setRight(rightPane); 
    }
    /////////////////////////////////////////////////////////////////////////////
    
                
    public void createListener(UMLClasses s){
        currentText.textProperty().unbind();
        s.setOnMousePressed((MouseEvent e) -> {
           
        DataManager expo = (DataManager) app.getDataComponent();
        
        if(selectedButton == selectionButton){
           if (currentPane != null){
            currentPane.setStyle("-fx-border-width: 1px");
            currentPane.setStyle("-fx-border-color: #000000");
            prevPane = currentPane;
           }
           
           currentPane = s; 

           currentPane.setStyle("-fx-border-width: 1000px");
           currentPane.setStyle("-fx-border-color: #ffff00");
           
           if(dummy != null)
                dummy.setText(currentPane.getClassNametoString());
           if(dummyData != null) //ERROR HERE
                dummyData.setText(currentPane.getPackageName());
        }   
    });
        s.setOnMouseDragged(e -> {
            if(selectedButton == selectionButton){
           s.setTranslateX(s.getTranslateXer() + e.getSceneX() - s.getSceneX() - 25);
           s.setTranslateY(s.getTranslateYer() + e.getSceneY() - s.getSceneY() - 105);
            }
        });
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
       // button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
	
	// AND RETURN THE COMPLETED BUTTON
        return button;
    }
    
    
      
    public void resetWorkspace(){
        
        leftPane.getChildren().clear();
        dummy.clear();
        dummyData.clear();
    }

    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {
        /*
        DataManager expo = (DataManager) app.getDataComponent();
        rightPane.getChildren().clear();
       for(Shape s : expo.getShapeList()){
           //createListener(s);
           //currentShape = s;
         //   rightPane.getChildren().add(s);
        }
                
        rightPane.setCursor(Cursor.DEFAULT);
        selectedButton = null; //bugger
*/
    }
}