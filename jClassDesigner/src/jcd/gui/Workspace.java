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
    Pane leftPane;
    Pane rightPane;
    HBox classPane;
    HBox packagePane;
    VBox variables;
    VBox methods;
    Button select = new Button();
    Button variableadd = new Button();
    Button variableremove = new Button();
    Button methodAdd = new Button();
    Button methodRemove = new Button();
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
	PropertiesManager propsSingleton = PropertiesManager.getPropertiesManager();
        DataManager expo = (DataManager) app.getDataComponent();

	workspace = new BorderPane();
	leftPane = new VBox();  
        rightPane = new Pane();
        rightPane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        expo.setBackgroundColor(Color.WHITE);
        
        
        
        FlowPane fileToolbarPane = gui.getFileToolbarPane();
        
        select = initChildButton(fileToolbarPane, PropertyType.SELECTION_ICON.toString(), PropertyType.SELECTION_TOOLTIP.toString(), false);
        
        
        
        
        
        
        
        
        
        
        
        classPane = new HBox();
        
        
        //MAKE A LABEL
        

	
         leftPane.getChildren().add(classPane);
        
         packagePane = new HBox();
     
    
    
         leftPane.getChildren().add(packagePane);
    
          //ParentPane
    
    
     
        variables = new VBox();
     
        Label backc = new Label("Background Color");
        backc.setFont(Font.font("Verdana", 20));
        variables.getChildren().add(backc);
    
    
    
        leftPane.getChildren().add(variables);
     
        methods = new VBox();
    
        Label fillerc = new Label("Filler Color");
        fillerc.setFont(Font.font("Arial", 20));
        methods.getChildren().add(fillerc);
    
        leftPane.getChildren().add(methods);
    
    /*
    //ADD CAMERA BUTTON
    camera = new HBox();
    
    camera_button = initChildButton(camera, PropertyType.CAMERA.toString(), PropertyType.CAMERA.toString(), false);
       
    camera_button.setOnAction(e -> {
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
     ((BorderPane) workspace).setRight(leftPane); //set the leftPane to the left of the BorderPane
     ((BorderPane) workspace).setCenter(rightPane);
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
       leftPane.getStyleClass().add("max_pane");
       classPane.getStyleClass().add("bordered_pane");
       packagePane.getStyleClass().add("bordered_pane");
       //parentPane.getStyleClass().add("bordered_pane");
       variables.getStyleClass().add("bordered_pane");
       methods.getStyleClass().add("bordered_pane");
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
        rightPane.getChildren().clear();
    }

    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {
        DataManager expo = (DataManager) app.getDataComponent();
        rightPane.getChildren().clear();
       for(Shape s : expo.getShapeList()){
           //createListener(s);
           //currentShape = s;
            rightPane.getChildren().add(s);
        }
                
        rightPane.setCursor(Cursor.DEFAULT);
        selectedButton = null; //bugger
    }
}