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
import java.io.File;
import static java.io.File.createTempFile;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import jcd.data.UMLClasses;
import jcd.data.UMLMethods;
import jcd.data.UMLVariables;

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
    public static final String PATH_EXPORT = "./export/";
    
    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        StringWriter sw = new StringWriter();
        
        DataManager expo = (DataManager) data;
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder innerBuilder = Json.createArrayBuilder();
        JsonArrayBuilder methodBuilder = Json.createArrayBuilder();
        JsonArrayBuilder argsBuilder = Json.createArrayBuilder();
        ArrayList<VBox> test = expo.getClassList();
        ArrayList<UMLVariables> vars = new ArrayList<>();
        ArrayList<UMLMethods> mets = new ArrayList<>();
        ArrayList<String> args = new ArrayList<>();
        int counter = 0;
        
        for(VBox sh: test){ //arrayBuilder is the array while testing is the object
            JsonObjectBuilder testing = Json.createObjectBuilder();
            if(sh instanceof UMLClasses){
            testing.add("Name", ((UMLClasses) sh).getClassNametoString()).add("Abstract", ((UMLClasses) sh).isAbstract())
		.add("Package", ((UMLClasses) sh).getPackageName())
                 .add("Flag", 0).add("T_X", ((UMLClasses) sh).getTranslateXer())
                    .add("T_Y", ((UMLClasses) sh).getTranslateYer());
            if(((UMLClasses) sh).getParentName() != null)
                testing.add("Parent", ((UMLClasses) sh).getParentName().getClassNametoString());
            //use a try-catch block to get the parent out when loading
            
            vars = ((UMLClasses) sh).getVariableNames(); //the inner variable class
            mets = ((UMLClasses) sh).getMethodNames();
            ////////
            for(UMLVariables variable: vars){ //innerBuilder is the array while innerVar is the object
                JsonObjectBuilder innerVar = Json.createObjectBuilder();

               innerVar.add("VarName", variable.getName()).add("Type", variable.getType()).add("Static", variable.isStatictype()).add("Access", variable.isAccesstype());
               innerBuilder.add(innerVar.build());
            }
            ///////
            for(UMLMethods method: mets){ //methodBuilder is the array while the innerMEthod is the object
                JsonObjectBuilder innerMeth = Json.createObjectBuilder();
                
                innerMeth.add("MethName", method.getName()).add("ReturnType", method.getReturntype())
                .add("StaticType", method.isStatictype()).add("AbstractType", method.isAbstractype())
                        .add("AccessType", method.isAccesstype());
                
                args = method.getArgs();
                for(String arguments: args){ //argsBuilder is the array while the matrix is the object
                    JsonObjectBuilder matrix = Json.createObjectBuilder();
                    matrix.add("Arg" + Integer.toString(counter), arguments);
                    
                    argsBuilder.add(matrix.build());
                    
                }
                innerMeth.add("arguments", argsBuilder);
                methodBuilder.add(innerMeth.build());
            }
            
            ///////
            testing.add("variables", innerBuilder);
            testing.add("methods", methodBuilder);
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
	//expo.reset();
        expo.getClassList().clear();
	
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonArray json = loadJSONFile(filePath);
        int flag;
        
        ArrayList<UMLVariables> vars = new ArrayList<>();
        ArrayList<UMLMethods> mets = new ArrayList<>();
        ArrayList<String> args = new ArrayList<>();
        int counter = 0;
        
        
        
        for(JsonValue sh: json){
            JsonObject shaping = (JsonObject) sh;
            flag = shaping.getInt("Flag");
            
            if(flag == 0){
                UMLClasses r = new UMLClasses(shaping.getString("Name"));
                r.setClassName(shaping.getString("Name"));
                
                if(shaping.getBoolean("Abstract") == true)
                    r.setIsAbstract(true);
                else
                    r.setIsAbstract(false);
                
                r.setClassNametoString(shaping.getString("Name"));
                r.setPackageName(shaping.getString("Package"));
                try{
                    r.setPackageName(shaping.getString("Parent"));
                } catch(Exception e){
                    System.out.println("There is no parent");
                }
                r.setTranslateXer(shaping.getJsonNumber("T_X").doubleValue());
                r.setTranslateYer(shaping.getJsonNumber("T_Y").doubleValue());
                ////variables
                vars = r.getVariableNames();
                
                JsonArray varLoading = shaping.getJsonArray("variables");
                for(JsonValue load_var: varLoading){
                    JsonObject into_var = (JsonObject) load_var;
                    UMLVariables v = new UMLVariables();
                    
                    v.setName(into_var.getString("VarName"));
                    v.setType(into_var.getString("Type"));
                    v.setStatictype(into_var.getBoolean("Static"));
                    v.setAccesstype(into_var.getBoolean("Access"));
                    vars.add(v);
                    r.setCurrentVariableName(new Text(v.toString()));
                }
                
                mets = r.getMethodNames();
                
                JsonArray metLoading = shaping.getJsonArray("methods");
                for(JsonValue load_methods: metLoading){
                    counter = 0;
                    JsonObject into_met = (JsonObject) load_methods;
                    UMLMethods m = new UMLMethods();
                    
                    m.setName(into_met.getString("MethName"));
                    m.setReturntype(into_met.getString("ReturnType"));
                    m.setStatictype(into_met.getBoolean("StaticType"));
                    m.setAbstractype(into_met.getBoolean("AbstractType"));
                    m.setAccesstype(into_met.getBoolean("AccessType"));
                    
                    args = m.getArgs();
                    //args
                    JsonArray argsLoading = into_met.getJsonArray("arguments");
                    for(JsonValue load_arguments: argsLoading){
                        JsonObject into_args = (JsonObject) load_arguments;
                        args.add(into_args.getString("Arg" + Integer.toString(counter)));
                    }
                    mets.add(m);
                    r.setCurrentMethodName(new Text(m.toString()));
                }
                
                /////
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
        try{
        File file = new File(filePath);
        String updateString = new String();
        String finalString = new String();
        
        String innerString = new String();
        String finalInnerString = new String();
        
        DataManager expo = (DataManager) data;
        //String java_source = "public class ";
        
        for(VBox sh: expo.getClassList()){
            if(sh instanceof UMLClasses){
                
                updateString = ((UMLClasses) sh).getPackageName().replace('.', '/');
                finalString = filePath + updateString;
                file = new File(finalString);
                //file = new File(filePath + ((UMLClasses) sh).getPackageName());
                file.mkdir();
                //ADD THE JAVA SOURCE CODE INTO THESE FOLDERS
                //double for loop to check all the VBoxes classes' names and create folders first
                //then we will use printwriter to write the java source code.
                
                
                for(VBox ll: expo.getClassList()){
                    String java_source = new String();
                    if(ll instanceof UMLClasses){
                        
                        innerString = ((UMLClasses) ll).getPackageName().replace('.', '/');
                        
                        //if(((UMLClasses) ll).getPackageName().equals((((UMLClasses) sh).getPackageName()))){
                         if(innerString.equals(updateString)){ 
                            //make the java source file
                            //OutputStream os = new FileOutputStream(filePath + ((UMLClasses) ll).getPackageName() + "/" + 
                            //        ((UMLClasses) ll).getClassNametoString() + ".java");
                            finalInnerString = filePath + innerString + "/" +
                                    ((UMLClasses) ll).getClassNametoString() + ".java";
                            OutputStream os = new FileOutputStream(finalInnerString);
                            
                            
                            try{
                                Class l = Class.forName( ((UMLClasses) ll).getPackageName() );
                                //Class l = Class.forName("java.lang.Thread");
                                java_source = "import " + ((UMLClasses) ll).getPackageName() + "; \n";
                            }catch (ClassNotFoundException e){
                                System.out.println("API NOT WORKING");
                            }

                            //method to printwriter
                             java_source = java_source + "public class ";
                             
                             //is there a parent???
                             
                            java_source = java_source + ((UMLClasses) ll).getClassNametoString() + "{ \n";
                            
                            for(UMLVariables k: ((UMLClasses) ll).getVariableNames()){
                                //sc = ((UMLClasses) ll).getVariableNames();
                                //k has name, type, static?, access?
                                
                                if(k.isAccesstype() == true)
                                   java_source = java_source + "public ";
                                else
                                    java_source = java_source + "private ";
                                
                                if(k.isStatictype() == true)
                                    java_source = java_source + "static ";
                                
                                java_source = java_source + k.getType() + " " + k.getName() + "\n";
                                
                            }
                            
                            for(UMLMethods m: ((UMLClasses) ll).getMethodNames()){
                                //name, returntype, statictype, abstracttype, accesstype, args
                                
                                if(m.isAccesstype() == true)
                                    java_source = java_source + "public ";
                                else
                                    java_source = java_source + "private ";
                                
                                if(m.isStatictype() == true)
                                    java_source = java_source + "static ";
                                
                                if(m.isAbstractype() == true)
                                    java_source = java_source + "abstract ";
                                
                                java_source = java_source + " " + m.getReturntype()+ " " + m.getName() + "( ";
                                
                                if(!m.getArgs().isEmpty()){
                                    
                                for(int i = 0; i < m.getArgs().size()-1; i++){ //except the last method
                                    java_source = java_source + m.getArgs().get(i) + " arg" + Integer.toString(i + 1) + ", ";     
                                }
                                
                                //for the last argument
                                    java_source = java_source + m.getArgs().get(m.getArgs().size() - 1) + " arg" +
                                        Integer.toString(m.getArgs().size() - 1);
                                    
                                }
                                java_source = java_source + "){ \n"; //opening a method
                                
                                if(m.getReturntype().equalsIgnoreCase("void")){
                                    
                                    if(m.getReturntype().equalsIgnoreCase("int"))
                                        java_source = java_source + "return 0; \n";
                                    else if(m.getReturntype().equals("double"))
                                        java_source = java_source + "return 1.0; \n";
                                    else if(m.getReturntype().equals("String"))
                                        java_source = java_source + "return 'Example String '; \n";
                                    else if(m.getReturntype().equals("boolean"))
                                        java_source = java_source + "return false; \n";
                                    //else if(m.getReturntype().equals("byte"))
                                        
                                
                                
                                
                                }
                                
                                java_source = java_source + "} \n"; //closing a method
                            }
                            
                            
                            
                            java_source = java_source + "}";
                            
                            
                            
                            
                            PrintWriter out = new PrintWriter(filePath + innerString + "/" + 
                                    ((UMLClasses) ll).getClassNametoString() + ".java");
                            
                            out.print(java_source);
                            out.close();
                        }
                    }
                }
                
                
                
            }
        }
        }catch(Exception e){
            System.out.println("FILE EXPORT ERROR");
        }
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
