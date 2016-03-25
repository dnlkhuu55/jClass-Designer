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
    HBox buttonPane_1;
    HBox buttonPane_2;
    VBox background_in;
    VBox filler_in;
    VBox outline_in;
    VBox thickness_in;
    HBox camera;
    Button selection = new Button();
    Button remove = new Button();
    Button rectangle = new Button();
    Button oval = new Button();
    Button down = new Button();
    Button up = new Button();
    Button camera_button = new Button();
    Button selectedButton;
    Shape currentShape = null;
    
    static final int BUTTON_TAG_WIDTH = 40;
    double startingx, startingy,endingx, endingy;
    Rectangle r;
    Ellipse ell;
    Color color1 = Color.WHITE;
    Color color2 = Color.WHITE;
    ColorPicker color_2;
    ColorPicker color_3;
    Color previousStroke;
    Shape pr = null;
    Slider slider; 
    double slider_number;
    
    
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
        
        buttonPane_1 = new HBox();

	// MAKE THE BUTTONS
	selection = initChildButton(buttonPane_1, PropertyType.SELECTION_TOOL_ICON.toString(), PropertyType.SELECTION_TOOL_TOOLTIP.toString(), false);
        remove = initChildButton(buttonPane_1, PropertyType.REMOVE_ICON.toString(), PropertyType.REMOVE_TOOLTIP.toString(), true);
        rectangle = initChildButton(buttonPane_1, PropertyType.RECTANGLE_ICON.toString(), PropertyType.RECTANGLE_TOOLTIP.toString(), false);
        oval = initChildButton(buttonPane_1, PropertyType.ELLIPSE_ICON.toString(), PropertyType.ELLIPSE_TOOLTIP.toString(), false);
	
        
        selection.setOnAction(e -> {
	    rightPane.setCursor(Cursor.DEFAULT);
            selectedButton = selection;
            });
        
        remove.setOnAction(e -> {
           // DataManager expo = (DataManager) app.getDataComponent();
	   ((BorderPane) workspace).setCursor(Cursor.DEFAULT); //REMOVE
           
           if(currentShape != null){
               up.setDisable(true);
               down.setDisable(true);
               remove.setDisable(true);
               
               rightPane.getChildren().remove(currentShape);
               expo.getShapeList().remove(currentShape);
               gui.updateToolbarControls(false);
           }
        });
        
        rectangle.setOnAction(e -> {
        rightPane.setCursor(Cursor.CROSSHAIR);
        if(currentShape != null){
            currentShape.setStroke(previousStroke);
        }
        currentShape = null;
        selectedButton = rectangle;//Change cursor to crosshair
        });  
        
        oval.setOnAction(e -> {
        rightPane.setCursor(Cursor.CROSSHAIR);
        if(currentShape != null){
        currentShape.setStroke(previousStroke);
        }
        currentShape = null;
        selectedButton = oval;//Change cursor to crosshair
        });
        
        rightPane.setOnMousePressed((MouseEvent event) -> {
       //     DataManager expo = (DataManager) app.getDataComponent();
         if(rightPane.getCursor().equals(Cursor.CROSSHAIR) && selectedButton == rectangle){
            startingx = event.getX();
            startingy = event.getY();
            r = getNewRectangle();
            rightPane.getChildren().add(r);
            expo.getShapeList().add(r);
        }
         if(rightPane.getCursor().equals(Cursor.CROSSHAIR) && selectedButton == oval){
             startingx = event.getX();
             startingy = event.getY();
             ell = getNewEllipse();
             rightPane.getChildren().add(ell);
            expo.getShapeList().add(ell);
         }
        });
        rightPane.setOnMouseDragged((MouseEvent evo)->{
         if (rightPane.getCursor().equals(Cursor.CROSSHAIR) && selectedButton == rectangle){
            endingx = evo.getX() ;
            endingy = evo.getY();
            r = makingRectangle(startingx, startingy, endingx, endingy, r); 
        }
         if(rightPane.getCursor().equals(Cursor.CROSSHAIR) && selectedButton == oval){
             endingx = evo.getX();
             endingy = evo.getY();
             ell = makingNewEllipse(startingx, startingy, endingx, endingy, ell); 
         }
        }); 
        rightPane.setOnMouseReleased((MouseEvent ei)->{
         if (rightPane.getCursor().equals(Cursor.CROSSHAIR) && selectedButton == rectangle){
            //Rectangle rect = makingRectangle(startingx, startingy, endingx, endingy, r); 
            createListener(r);
            up.setDisable(false);
            down.setDisable(false);
            remove.setDisable(false);
            //rightPane.getChildren().add(r);
            gui.updateToolbarControls(false);
        }
         if(rightPane.getCursor().equals(Cursor.CROSSHAIR) && selectedButton == oval){
            // Ellipse ova = makingNewEllipse(startingx, startingy, endingx, endingy, ell);
             createListener(ell);
             up.setDisable(false);
             down.setDisable(false);
             remove.setDisable(false);
             //rightPane.getChildren().add(ova);
             gui.updateToolbarControls(false);
         }
        });
    leftPane.getChildren().add(buttonPane_1);
        
    buttonPane_2 = new HBox();
     
    up = initChildButton(buttonPane_2, PropertyType.UP_ICON.toString(), PropertyType.UP_TOOLTIP.toString(), true);
    down = initChildButton(buttonPane_2, PropertyType.DOWN_ICON.toString(), PropertyType.DOWN_TOOLTIP.toString(), true);
     
    up.setOnAction(e -> {
        
    //    DataManager expo = (DataManager) app.getDataComponent();
        rightPane.setCursor(Cursor.DEFAULT);
        expo.getShapeList().remove(currentShape);
        expo.getShapeList().add(currentShape);
        reloadWorkspace();
        });
    down.setOnAction(e -> {
        
    //    DataManager expo = (DataManager) app.getDataComponent();
        rightPane.setCursor(Cursor.DEFAULT);
        expo.getShapeList().remove(currentShape);
        expo.getShapeList().add(0, currentShape);
        reloadWorkspace();
        });
    
    leftPane.getChildren().add(buttonPane_2);
     
    background_in = new VBox();
     
    Label backc = new Label("Background Color");
    backc.setFont(Font.font("Verdana", 20));
    background_in.getChildren().add(backc);
    
    
    
    ColorPicker color_1 = new ColorPicker();      
    color_1.setOnAction(new EventHandler(){
            public void handle(Event event) {
                DataManager expo = (DataManager) app.getDataComponent();
                Color fill = color_1.getValue();
                BackgroundFill backgroundFill = 
                    new BackgroundFill(fill, 
                            CornerRadii.EMPTY, 
                            Insets.EMPTY);
                Background background = new Background(backgroundFill);
                rightPane.setBackground(background);
                gui.updateToolbarControls(false);
                
                expo.setBackgroundColor(fill);
            }
        });
 
    background_in.getChildren().add(color_1);
    
    leftPane.getChildren().add(background_in);
     
    filler_in = new VBox();
    
    Label fillerc = new Label("Filler Color");
    fillerc.setFont(Font.font("Arial", 20));
    filler_in.getChildren().add(fillerc);
    
    color_2 = new ColorPicker();     
    color_2.setOnAction(new EventHandler(){
            public void handle(Event event) {
                if(currentShape != null){
                    System.out.println(color_2.getValue());
                    Color fill = color_2.getValue();
                    System.out.println(currentShape);
                    System.out.println(fill);
                    currentShape.setFill(fill);
                    color1 = fill;
                }
                Color fill = color_2.getValue();
                color1 = fill;
                gui.updateToolbarControls(false);
            }
        });
    filler_in.getChildren().add(color_2);
    leftPane.getChildren().add(filler_in);
    
    outline_in = new VBox();
    
    Label outlinec = new Label("Outline Color");
    outlinec.setFont(Font.font("Arial", 20));
    outline_in.getChildren().add(outlinec);
    
    color_3 = new ColorPicker();      
    color_3.setOnAction(new EventHandler(){
            public void handle(Event event) {
                if(currentShape != null){
                    System.out.println(color_3.getValue());
                    Color fill = color_3.getValue();
                    System.out.println(currentShape);
                    System.out.println(fill);
                    previousStroke = fill;

                    currentShape.setStroke(fill);
                   // currentShape.strokeWidthProperty().bind(slider.valueProperty());
                    color2 = fill;
                    gui.updateToolbarControls(false);
                }
                Color fill = color_3.getValue();
                color2 = fill;
            }
        });
    outline_in.getChildren().add(color_3);
    leftPane.getChildren().add(outline_in);
    
    //ADD A SLIDER
    thickness_in = new VBox();
    
    Label thicknessc = new Label("Thickness Color");
    thicknessc.setFont(Font.font("Arial", 20));
    thickness_in.getChildren().add(thicknessc);
    
    slider = new Slider(1, 100, 1);
    
    slider_number = slider.getValue();
    
    slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                    if(currentShape != null){
                        currentShape.setStrokeWidth(slider.getValue());
                        gui.updateToolbarControls(false);
                    }
            }
        });
    

    thickness_in.getChildren().add(slider);
    
    leftPane.getChildren().add(thickness_in);
    
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
     ((BorderPane) workspace).setLeft(leftPane); //set the leftPane to the left of the BorderPane
     ((BorderPane) workspace).setCenter(rightPane);
    }
    
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
    
    private Ellipse getNewEllipse(){
        Ellipse some = new Ellipse();
        some.setFill(color1);
        some.setStroke(color2);
        some.strokeWidthProperty().set(slider.getValue());
        //some.strokeWidthProperty().bind(slider.valueProperty());
        return some;
    }
    public Ellipse makingNewEllipse(double centerx, double centery, double endingx, double endingy,
            Ellipse some){
        some.setCenterX(centerx);
        some.setCenterY(centery);
        some.setRadiusX(endingx - centerx);
        some.setRadiusY(endingy - centery);
        some.setFill(color1);
        some.setStroke(color2);
        return some;
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
    
    public void initStyle(){
       leftPane.getStyleClass().add("max_pane");
       buttonPane_1.getStyleClass().add("bordered_pane");
       buttonPane_2.getStyleClass().add("bordered_pane");
       background_in.getStyleClass().add("bordered_pane");
       filler_in.getStyleClass().add("bordered_pane");
       outline_in.getStyleClass().add("bordered_pane");
       camera.getStyleClass().add("bordered_pane");
       thickness_in.getStyleClass().add("bordered_pane");
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
        

    /**
     * This function reloads all the controls for editing tag attributes into
     * the workspace.
     */
    @Override
    public void reloadWorkspace() {
        DataManager expo = (DataManager) app.getDataComponent();
        rightPane.getChildren().clear();
       for(Shape s : expo.getShapeList()){
           createListener(s);
           //currentShape = s;
            rightPane.getChildren().add(s);
        }
       BackgroundFill backgroundFill = 
                    new BackgroundFill(expo.getBackgroundColor(), 
                            CornerRadii.EMPTY, 
                            Insets.EMPTY);
                Background background = new Background(backgroundFill);
                rightPane.setBackground(background);
                
        rightPane.setCursor(Cursor.DEFAULT);
        selectedButton = selection;
    }
}