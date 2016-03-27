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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

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

    BorderPane entirePane;
    ScrollPane leftPane;
    VBox rightPane;
    HBox classPane;
    HBox packagePane;
    HBox parentPane;
    HBox variablePane;
    StackPane variableTablePane;
    HBox methodPane;
    StackPane methodTablePane;
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
    Shape currentShape = null;
    
    static final int BUTTON_TAG_WIDTH = 40;
    double startingx, startingy,endingx, endingy;
    Rectangle r;
    Color color1 = Color.WHITE;
    Color previousStroke;
    Shape pr = null;
    
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
	leftPane = new ScrollPane();  
        rightPane = new VBox();
        leftPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        //expo.setBackgroundColor(Color.WHITE);
        
  
        FlowPane fileToolbarPane = gui.getFileToolbarPane();
        
        photoButton = initChildButton(fileToolbarPane, PropertyType.PHOTO_ICON.toString(), PropertyType.PHOTO_TOOLTIP.toString(), false);
        codeButton = initChildButton(fileToolbarPane, PropertyType.CODE_ICON.toString(), PropertyType.CODE_TOOLTIP.toString(), false);
        selectionButton = initChildButton(fileToolbarPane, PropertyType.SELECTION_ICON.toString(), PropertyType.SELECTION_TOOLTIP.toString(), false);
        resizeButton = initChildButton(fileToolbarPane, PropertyType.RESIZE_ICON.toString(), PropertyType.RESIZE_TOOLTIP.toString(), false);
        addClassButton = initChildButton(fileToolbarPane, PropertyType.ADDCLASS_ICON.toString(), PropertyType.ADDCLASS_TOOLTIP.toString(), false);
        addInterfaceButton = initChildButton(fileToolbarPane, PropertyType.ADDINTERFACE_ICON.toString(), PropertyType.ADDINTERFACE_TOOLTIP.toString(), false);
        removeButton = initChildButton(fileToolbarPane, PropertyType.REMOVE_ICON.toString(), PropertyType.REMOVE_TOOLTIP.toString(), false);
        undoButton = initChildButton(fileToolbarPane, PropertyType.UNDO_ICON.toString(), PropertyType.UNDO_TOOLTIP.toString(), false);
        redoButton = initChildButton(fileToolbarPane, PropertyType.REDO_ICON.toString(), PropertyType.REDO_TOOLTIP.toString(), false);
        zoominButton = initChildButton(fileToolbarPane, PropertyType.ZOOMIN_ICON.toString(), PropertyType.ZOOMIN_TOOLTIP.toString(), false);
        zoomoutButton = initChildButton(fileToolbarPane, PropertyType.ZOOMOUT_ICON.toString(), PropertyType.ZOOMOUT_TOOLTIP.toString(), false);
      
         
        classPane = new HBox();
        
        Label className = new Label("Class Name:");
        className.setFont(Font.font("Arial", 10));
        classPane.getChildren().add(className);
        
        classPane.getChildren().add(dummy);
        
        rightPane.getChildren().add(classPane);
        
        
        packagePane = new HBox();
     
        Label packageName = new Label("Package Name:");
        packageName.setFont(Font.font("Arial", 10));
        packagePane.getChildren().add(packageName);
        
        packagePane.getChildren().add(dummyData);
        
        rightPane.getChildren().add(packagePane);
        
    
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
        
       
        variablePane = new HBox();
        
        Label variableName = new Label("Variables: ");
        variableName.setFont(Font.font("Arial", 10));
        variablePane.getChildren().add(variableName);
        
        addButton = initChildButton(variablePane, PropertyType.ADD_ICON.toString(), PropertyType.ADD_TOOLTIP.toString(), false);
        minusButton = initChildButton(variablePane, PropertyType.MINUS_ICON.toString(), PropertyType.MINUS_TOOLTIP.toString(), false);
        
        rightPane.getChildren().add(variablePane);
        
      
        variableTablePane = new StackPane();
        table1.setEditable(true);
        
        TableColumn NameCol1 = new TableColumn("Name");
        TableColumn TypeCol1 = new TableColumn("Type");
        TableColumn StaticCol1 = new TableColumn("Static");
        TableColumn AccessCol1 = new TableColumn("Access");
        
        table1.getColumns().addAll(NameCol1, TypeCol1, StaticCol1, AccessCol1);
        variableTablePane.getChildren().add(table1);
        rightPane.getChildren().add(variableTablePane);
        
        methodPane = new HBox();
        
        Label methodName = new Label("Methods: ");
        methodName.setFont(Font.font("Arial", 10));
        methodPane.getChildren().add(methodName);
        
        addMButton = initChildButton(methodPane, PropertyType.ADD_ICON.toString(), PropertyType.ADD_TOOLTIP.toString(), false);
        minusMButton = initChildButton(methodPane, PropertyType.MINUS_ICON.toString(), PropertyType.MINUS_TOOLTIP.toString(), false);
        
        rightPane.getChildren().add(methodPane);
        
      
        methodTablePane = new StackPane();
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
        
        methodTablePane.getChildren().add(table2);
        rightPane.getChildren().add(methodTablePane);
        
    /*
    //ADD CAMERA BUTTON
    cameraButton.setOnAction(e -> {
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
                WritableImage snapshot = (rightPane.snapshot(new SnapshotParameters(), wi));
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", selectedFile);
		}
           
            } catch (IOException ex) {
                Logger.getLogger(TakeSnapShoot.class.getName()).log(Level.SEVERE, null, ex);
            }
	});
    
     leftPane.getChildren().add(camera);
        */
    
    ((BorderPane) workspace).setCenter(leftPane); //set the leftPane to the left of the BorderPane
    ((BorderPane) workspace).setRight(rightPane); 
    }
    
    /*
    private Rectangle getNewRectangle() {
        Rectangle r = new Rectangle();
        r.setFill(color1);
        r.setStroke(color2);
        r.strokeWidthProperty().set(slider.getValue());
        return r;
    }
    public Rectangle makingRectangle(double startingx, double startingy, double endingx, double endingy,
            Rectangle r){
        r.setX(startingx);
        r.setY(startingy);
        r.setWidth(endingx - startingx);
        r.setHeight(endingy - startingy);
        r.setFill(color1);
        r.setStroke(color2);
        return r;
    }
    
    public void createListener(Shape s){
        
        s.setOnMousePressed((MouseEvent e) -> {
           //THIS IS WHAT I TO HAPPEN
           DataManager expo = (DataManager) app.getDataComponent();

           
           if(selectedButton == selection){
           if (pr != null){
               pr.setStroke(previousStroke);
               
           }
           up.setDisable(false);
           down.setDisable(false);
           remove.setDisable(false);
           pr = s;
           previousStroke = (Color) s.getStroke();
           color_3.setValue((Color) s.getStroke());
           s.setStroke(Color.YELLOW);
           currentShape = s; 
           
           color_2.setValue((Color) s.getFill());
           slider.setValue(s.getStrokeWidth());
           System.out.println((Color) s.getStroke());
        }
           
        });
        s.setOnMouseDragged(e -> {
            if(s instanceof Rectangle){
            Rectangle r = (Rectangle) s;
            r.setX(e.getX());
            r.setY(e.getY());
            }else if(s instanceof Ellipse){
            Ellipse some = (Ellipse) s;
            some.setCenterX(e.getX());
            some.setCenterY(e.getY());    
            }
        });
    
    }
    */
    public void initStyle(){
       rightPane.getStyleClass().add("max_pane");
       classPane.getStyleClass().add("bordered_pane");
       packagePane.getStyleClass().add("bordered_pane");
       //parentPane.getStyleClass().add("bordered_pane");
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
      //  rightPane.getChildren().clear(); //not correct
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