package jcd.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import jcd.data.DataManager;
import af.components.AppDataComponent;
import af.components.AppFileComponent;
import af.ui.AppMessageDialogSingleton;
import javafx.scene.layout.GridPane;
import jcd.data.UMLClasses;

/**
 * This class serves as the file management component for this application,
 * providing all I/O services.
 *
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public class FileManager implements AppFileComponent {

    /**
     * This method is for saving user work, which in the case of this
     * application means the data that constitutes the page DOM.
     * 
     * @param data The data management component for this application.
     * 
     * @param filePath Path (including file name/extension) to where
     * to save the data to.
     * 
     * @throws IOException Thrown should there be an error writing 
     * out data to the file.
     */
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        StringWriter sw = new StringWriter();
        
        DataManager expo = (DataManager) data;
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        ArrayList<GridPane> test = expo.getClassList();
        
        for(GridPane sh: test){
            JsonObjectBuilder testing = Json.createObjectBuilder();
            if(sh instanceof UMLClasses){
            testing.add("Name", ((UMLClasses) sh).getClassNametoString())
		.add("Package", ((UMLClasses) sh).getPackageName())
                 .add("Flag", 0).add("X", ((UMLClasses) sh).getSceneX())
                    .add("Y", ((UMLClasses) sh).getSceneY()).add("T_X", ((UMLClasses) sh).getTranslateXer())
                    .add("T_Y", ((UMLClasses) sh).getTranslateYer());
            }
            arrayBuilder.add(testing.build());
        }
        
       JsonArray nodesArray = arrayBuilder.build();
        
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeArray(nodesArray);
	jsonWriter.close();
        
        OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeArray(nodesArray);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }
      
    /**
     * This method loads data from a JSON formatted file into the data 
     * management component and then forces the updating of the workspace
     * such that the user may edit the data.
     * 
     * @param data Data management component where we'll load the file into.
     * 
     * @param filePath Path (including file name/extension) to where
     * to load the data from.
     * 
     * @throws IOException Thrown should there be an error reading
     * in data from the file.
     */
    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        try{
        DataManager expo = (DataManager)data;
	expo.reset();
        expo.getClassList().clear();
	
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonArray json = loadJSONFile(filePath);
        int flag;
        Color backy;
        
        
        for(JsonValue sh: json){
            JsonObject shaping = (JsonObject) sh;
            flag = shaping.getInt("Flag");
            
            if(flag == 0){
                UMLClasses r = new UMLClasses();
                r.setClassName(shaping.getString("Name"));
                r.setPackageName(shaping.getString("Package"));
                r.setSceneX(shaping.getJsonNumber("X").doubleValue());
                r.setSceneY(shaping.getJsonNumber("Y").doubleValue());
                r.setTranslateXer(shaping.getJsonNumber("T_X").doubleValue());
                r.setTranslateYer(shaping.getJsonNumber("T_Y").doubleValue());
                expo.getClassList().add(r);   
            }
            /*
            else if(flag==1){ //ellipse
                Ellipse s = new Ellipse();
                s.setFill(Paint.valueOf(shaping.getString("Fill")));
                s.setStroke(Paint.valueOf(shaping.getString("Stroke")));
                s.strokeWidthProperty().set(shaping.getJsonNumber("StrokeWidth").doubleValue());
                s.setCenterX(shaping.getJsonNumber("CenterX").doubleValue());
                s.setCenterY(shaping.getJsonNumber("CenterY").doubleValue());
                s.setRadiusX(shaping.getJsonNumber("RadiusX").doubleValue());
                s.setRadiusY(shaping.getJsonNumber("RadiusY").doubleValue());
                expo.getShapeList().add(s);
            }
            
            else{
                backy = Color.valueOf(shaping.getString("Backy"));
                expo.setBackgroundColor(backy);
            }
            */
        }
        }catch(IOException ex){
            System.out.println("This file cannot be loaded. Try again!");
        }
    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonArray loadJSONFile(String jsonFilePath) throws IOException {
        try{
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonArray json = jsonReader.readArray();
	jsonReader.close();
	is.close();
	return json;
        }catch(Exception IO){       
           AppMessageDialogSingleton.getSingleton().show("JSON Error", "Unable to load from the JSON file.");
        }
        return null;
    }
    
    /**
     * This method exports the contents of the data manager to a 
     * Web page including the html page, needed directories, and
     * the CSS file.
     * 
     * @param data The data management component.
     * 
     * @param filePath Path (including file name/extension) to where
     * to export the page to.
     * 
     * @throws IOException Thrown should there be an error writing
     * out data to the file.
     */
    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {

    }
    
    /**
     * This method is provided to satisfy the compiler, but it
     * is not used by this application.
     */
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
	// NOTE THAT THE Web Page Maker APPLICATION MAKES
	// NO USE OF THIS METHOD SINCE IT NEVER IMPORTS
	// EXPORTED WEB PAGES
    }
}
