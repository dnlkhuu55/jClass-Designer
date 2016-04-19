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
import java.math.BigDecimal;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import jcd.data.ClassLines;
import jcd.data.UMLClasses;
import jcd.data.UMLInterfaces;
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
        JsonArrayBuilder interfacesBuilder = Json.createArrayBuilder();
        JsonArrayBuilder innerBuilder = Json.createArrayBuilder();
        JsonArrayBuilder methodBuilder = Json.createArrayBuilder();
        JsonArrayBuilder argsBuilder = Json.createArrayBuilder();
        ArrayList<VBox> test = expo.getClassList();
        ArrayList<Line> test_2 = expo.getLineList();
        ArrayList<UMLVariables> vars = new ArrayList<>();
        ArrayList<UMLMethods> mets = new ArrayList<>();
        ArrayList<String> args = new ArrayList<>();
        int counter = 0;
        int counter2 = 0;
        
        for(VBox sh: test){ //arrayBuilder is the array while testing is the object
            counter = 0;
            counter2 = 0;
            JsonObjectBuilder testing = Json.createObjectBuilder();
            if(sh instanceof UMLClasses){
            testing.add("Name", ((UMLClasses) sh).getClassNametoString()).add("Abstract", ((UMLClasses) sh).isAbstract())
		.add("Package", ((UMLClasses) sh).getPackageName())
                 .add("Flag", 0).add("T_X", ((UMLClasses) sh).getTranslateXer())
                    .add("T_Y", ((UMLClasses) sh).getTranslateYer());
            if(((UMLClasses) sh).getParentName() != null)
                testing.add("Parent", ((UMLClasses) sh).getParentName());
            
            for(String inters: ((UMLClasses) sh).getParentInterfaces()){
                JsonObjectBuilder parentInters = Json.createObjectBuilder();
                parentInters.add("Parent_Interface" + Integer.toString(counter2), inters);
                
                interfacesBuilder.add(parentInters.build());
                counter2++;
            }
            testing.add("parentInterfaces", interfacesBuilder);
            
            
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
                counter = 0;
                JsonObjectBuilder innerMeth = Json.createObjectBuilder();
                
                innerMeth.add("MethName", method.getName()).add("ReturnType", method.getReturntype())
                .add("StaticType", method.isStatictype()).add("AbstractType", method.isAbstractype())
                        .add("AccessType", method.isAccesstype());
                
                args = method.getArgs();
                for(String arguments: args){ //argsBuilder is the array while the matrix is the object
                    JsonObjectBuilder matrix = Json.createObjectBuilder();
                    matrix.add("Arg" + Integer.toString(counter), arguments);
                    
                    argsBuilder.add(matrix.build());
                    counter++;
                }
                innerMeth.add("arguments", argsBuilder);
                methodBuilder.add(innerMeth.build());
            }
            
            ///////
            testing.add("variables", innerBuilder);
            testing.add("methods", methodBuilder);
            }
            
            
            if(sh instanceof UMLInterfaces){
                testing.add("Name", ((UMLInterfaces) sh).getInterNametoString())
                 .add("Package", ((UMLInterfaces) sh).getPackageName())
                 .add("Flag", 1).add("T_X", ((UMLInterfaces) sh).getTranslateXer())
                    .add("T_Y", ((UMLInterfaces) sh).getTranslateYer());
            
            vars = ((UMLInterfaces) sh).getVariableNames(); //the inner variable class
            mets = ((UMLInterfaces) sh).getMethodNames();
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
        
        for(Line s: test_2){
            JsonObjectBuilder testing = Json.createObjectBuilder();
            if(s instanceof ClassLines){
            testing.add("Flag", 2).add("Starting_X", ((ClassLines) s).getStartX())
		.add("Starting_Y", ((ClassLines) s).getStartY())
                .add("Ending_X", ((ClassLines) s).getEndX()).add("Ending_Y", ((ClassLines) s).getEndY())
                    .add("Mid_X", ((ClassLines) s).getMid_x()).add("Mid_Y", ((ClassLines) s).getMid_y())
                    .add("Starting_Class", ((ClassLines) s).getStart_node())
                    .add("Ending_Class", ((ClassLines) s).getEnd_node());
            arrayBuilder.add(testing.build());
            }
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
        expo.getLineList().clear();
	
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonArray json = loadJSONFile(filePath);
        int flag;
        
        ArrayList<UMLVariables> vars = new ArrayList<>();
        ArrayList<UMLMethods> mets = new ArrayList<>();
        ArrayList<String> args = new ArrayList<>();
        ArrayList<Line> lines = new ArrayList<>();
        int counter = 0;
        int counter2 = 0;

        
        for(JsonValue sh: json){
            JsonObject shaping = (JsonObject) sh;
            flag = shaping.getInt("Flag");
            
            if(flag == 0){
                UMLClasses r = new UMLClasses(shaping.getString("Name"));
                r.setClassName(shaping.getString("Name"));
                
                if(shaping.getBoolean("Abstract") == true){
                    r.setIsAbstract(true);
                }
                else{
                    r.setIsAbstract(false);
                }
                
                r.setClassNametoString(shaping.getString("Name"));
                r.setPackageName(shaping.getString("Package"));
                r.setParentName(shaping.getString("Parent"));
                r.setTranslateXer(shaping.getJsonNumber("T_X").doubleValue());
                r.setTranslateYer(shaping.getJsonNumber("T_Y").doubleValue());
                
                counter2 = 0;
                JsonArray parentInterfaces = shaping.getJsonArray("parentInterfaces");
                for(JsonValue load_int: parentInterfaces){
                    JsonObject into_parent = (JsonObject) load_int;
                    r.getParentInterfaces().add(into_parent.getString("Parent_Interface" + Integer.toString(counter2)));
                    counter2++;
                }
                
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
                        counter++;
                    }
                    mets.add(m);
                    r.setCurrentMethodName(new Text(m.toString()));
                }
                
                /////
                expo.getClassList().add(r);
            }
            
            if(flag ==1){ //Interfaces
                
                UMLInterfaces s = new UMLInterfaces(shaping.getString("Name"));
                s.setInterName(shaping.getString("Name"));
                
                s.setInterNametoString(shaping.getString("Name"));
                s.setPackageName(shaping.getString("Package"));

                s.setTranslateXer(shaping.getJsonNumber("T_X").doubleValue());
                s.setTranslateYer(shaping.getJsonNumber("T_Y").doubleValue());
                ////variables
                vars = s.getVariableNames();
                
                JsonArray varLoading = shaping.getJsonArray("variables");
                for(JsonValue load_var: varLoading){
                    JsonObject into_var = (JsonObject) load_var;
                    UMLVariables v = new UMLVariables();
                    
                    v.setName(into_var.getString("VarName"));
                    v.setType(into_var.getString("Type"));
                    v.setStatictype(into_var.getBoolean("Static"));
                    v.setAccesstype(into_var.getBoolean("Access"));
                    vars.add(v);
                    s.setCurrentVariableName(new Text(v.toString()));
                }
                
                mets = s.getMethodNames();
                
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
                    s.setCurrentMethodName(new Text(m.toString()));
                }
                
                /////
                expo.getClassList().add(s);
            
            }
        if(flag == 2){
            ClassLines l = new ClassLines();
            l.setStartX(shaping.getJsonNumber("Starting_X").doubleValue());
            l.setStartY(shaping.getJsonNumber("Starting_Y").doubleValue());
            l.setEndX(shaping.getJsonNumber("Ending_X").doubleValue());
            l.setEndY(shaping.getJsonNumber("Ending_Y").doubleValue());
            l.setStart_node(shaping.getString("Starting_Class"));
            l.setEnd_node(shaping.getString("Ending_Class"));
            l.setMid_x();
            l.setMid_y();
            expo.getLineList().add(l);
        }
        
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
            int last_slash = filePath.lastIndexOf('/');
            String getting_package = filePath.substring(last_slash + 1, filePath.length());
        
        filePath = filePath + "/";
            
        File file = new File(filePath);
        String updateString = new String();
        String finalString = new String();
        
        String innerString = new String();
        String finalInnerString = new String();
        
        DataManager expo = (DataManager) data;
        
        for(VBox sh: expo.getClassList()){
            if(sh instanceof UMLClasses){
                
                updateString = ((UMLClasses) sh).getPackageName().replace('.', '/');
                finalString = filePath + updateString;
                file = new File(finalString);
                file.mkdir();
                //ADD THE JAVA SOURCE CODE INTO THESE FOLDERS
                //double for loop to check all the VBoxes classes' names and create folders first
                //then we will use printwriter to write the java source code.
                
                
                for(VBox ll: expo.getClassList()){
                    String java_source = new String();
                    
                    if(ll instanceof UMLClasses){
                        
                        innerString = ((UMLClasses) ll).getPackageName().replace('.', '/');
                        
                        if(innerString.equals(updateString)){ 
                            //make the java source file
                            finalInnerString = filePath + innerString + "/" +
                                    ((UMLClasses) ll).getClassNametoString() + ".java";
                            OutputStream os = new FileOutputStream(finalInnerString);

                            java_source = java_source + "package " + 
                                    getting_package + "." + ((UMLClasses) ll).getPackageName() + "; \n";
                            
                            java_source = java_source + "import javafx.concurrent.Task; \n";
                            java_source = java_source + "import javafx.scene.text.Text; \n";
                            java_source = java_source + "import javafx.event.Event; \n";
                           
                            for(VBox h: expo.getClassList()){ 
                                if(h instanceof UMLClasses){            
                                    try{
                                        String s = getting_package + "." + ((UMLClasses) h).getPackageName() +
                                                "." + ((UMLClasses) h).getClassNametoString();
                                        java_source = java_source + "import " + s + "; \n";
                                    }catch (Exception e){
                                        System.out.println("NOT IMPORTING PACKAGES AS IMPORT");
                                    }
                                                
                                }
                            }
                            
                            for(UMLVariables impo: ((UMLClasses) ll).getVariableNames()){ //importing
                                Package[] pack = Package.getPackages();
                                
                                for(int i = 0; i < pack.length; i++){
                                    try{
                                        if(!impo.getType().equals("Text")){
                                            if(!impo.getType().equals("Event")){
                                                String s = pack[i].getName() + "." + impo.getType();
                                                Class.forName(s);
                                                java_source = java_source + "import " + pack[i].getName() + "." + impo.getType() + "; \n";
                                            }
                                        }
                                    }catch (Exception e){
                                        System.out.println("The api doesn't match");
                                    }
                                }      
                            }
                            
                            ArrayList<UMLMethods> ml = ((UMLClasses) ll).getMethodNames(); 
                            for(int i = 0; i < ml.size(); i++){ //for every method in ArrayList<Methods>, look at one method
                                UMLMethods sz = ml.get(i);
                                Package[] hello = Package.getPackages();
                                
                                for(int j = 0; j < sz.getArgs().size(); j++){ //for every argument in that method, look at the arguments
                                    
                                    String l = sz.getArgs().get(j);
                                    for(int k = 0; k < hello.length; k++){
                                        String mt = hello[k].getName() + "." + l;
                                        try{
                                            if(!l.equals("Event")){
                                                if(!l.equals("Text")){
                                                Class.forName(mt);
                                                java_source = java_source + "import " + hello[k].getName() + "." + l + "; \n";
                                                }
                                            }
                                        }catch (Exception e){
                                        System.out.println("Arguments API not matched");
                                        }
                                    }
                                }
                            }
                            
                            //method to printwriter
                            if(((UMLClasses) ll).isAbstract() == false)
                                java_source = java_source + "public class " + ((UMLClasses) ll).getClassNametoString() + " ";
                            else
                                java_source = java_source + "public abstract class " + ((UMLClasses) ll).getClassNametoString() + " ";
                             
                             //is there a parent???
                            if(((UMLClasses) ll).getParentName().equals("") == false){
                                 java_source = java_source + "extends " + ((UMLClasses) ll).getParentName();
                            }
                             
                            if(!((UMLClasses) ll).getParentInterfaces().isEmpty()){
                                java_source = java_source + "implements ";
                                for(int i = 0; i < ((UMLClasses) ll).getParentInterfaces().size() - 1; i++){
                                    java_source = java_source + ((UMLClasses) ll).getParentInterfaces().get(i) + ", ";
                                }
                                java_source = java_source + ((UMLClasses) ll).getParentInterfaces().get(((UMLClasses) ll).getParentInterfaces().size() -1);
                            }
                         
                            java_source = java_source + "{ \n";
                            
                            for(UMLVariables k: ((UMLClasses) ll).getVariableNames()){
                                if(k.isAccesstype() == true)
                                   java_source = java_source + "public ";
                                else
                                    java_source = java_source + "private ";
                                
                                if(k.isStatictype() == true)
                                    java_source = java_source + "static ";
                                
                                java_source = java_source + k.getType() + " " + k.getName() + "; \n";
                                
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
                                    java_source = java_source + m.getArgs().get(i) + " arg" + Integer.toString(i) + ", ";     
                                }
                                
                                //for the last argument
                                    java_source = java_source + m.getArgs().get(m.getArgs().size() - 1) + " arg" +
                                        Integer.toString(m.getArgs().size() - 1);                             
                                }
                                java_source = java_source + ")"; //opening a method
                                
                                if(((UMLClasses) ll).isAbstract() == false){
                                java_source = java_source + "{ \n";
                                
                                if(!m.getReturntype().equalsIgnoreCase("void")){
                                    
                                    if(m.getReturntype().equals("int"))
                                        java_source = java_source + "return 0; \n";
                                    else if(m.getReturntype().equals("double"))
                                        java_source = java_source + "return 1.0; \n";
                                    else if(m.getReturntype().equals("byte"))
                                        java_source = java_source + "return 1; \n";
                                    else if(m.getReturntype().equals("short"))
                                        java_source = java_source + "return 1; \n";
                                    else if(m.getReturntype().equals("long"))
                                        java_source = java_source + "return 1; \n";
                                    else if(m.getReturntype().equals("float"))
                                        java_source = java_source + "return 1; \n";
                                    else if(m.getReturntype().equals("char"))
                                        java_source = java_source + "return 'c'; \n";
                                    else if(m.getReturntype().equals("String"))
                                        java_source = java_source + "return 'Example String '; \n";
                                    else if(m.getReturntype().equals("boolean"))
                                        java_source = java_source + "return false; \n";
                                    else if (m.getReturntype().equals(" ") || m.getReturntype().equals(""))
                                        java_source = java_source; //constructor
                                    else
                                        java_source = java_source + "return null; \n";
                                }
                                java_source = java_source + "} \n"; //closing a method
                            }
                            else{
                                   java_source = java_source + "; \n"; 
                                }
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
                if(sh instanceof UMLInterfaces) {
                    updateString = ((UMLInterfaces) sh).getPackageName().replace('.', '/');
                    finalString = filePath + updateString;
                    file = new File(finalString);
                    //file = new File(filePath + ((UMLInterfaces) sh).getPackageName());
                    file.mkdir();
                        
                    for(VBox ll: expo.getClassList()){
                        String java_source = new String();
                    
                    if(ll instanceof UMLInterfaces){
                        innerString = ((UMLInterfaces) sh).getPackageName().replace('.', '/');
                        
                         if(innerString.equals(updateString)){ 
                            //make the java source file
                            finalInnerString = filePath + innerString + "/" +
                                    ((UMLInterfaces) sh).getInterNametoString() + ".java";
                            OutputStream os = new FileOutputStream(finalInnerString);

                            java_source = java_source + "package " + 
                                    getting_package + "." + ((UMLInterfaces) ll).getPackageName() + "; \n";
                            //INTO ANY PACKAGE
                            
                            java_source = java_source + "import javafx.concurrent.Task; \n";
                            java_source = java_source + "import javafx.scene.text.Text; \n";
                            java_source = java_source + "import javafx.event.Event; \n";
                           
                            for(VBox h: expo.getClassList()){ 
                                if(h instanceof UMLClasses){            
                                    try{
                                        String s = getting_package + "." + ((UMLClasses) h).getPackageName() +
                                                "." + ((UMLClasses) h).getClassNametoString();
                                        System.out.println(s);
                                        
                                        //Class.forName(((UMLClasses) h).getClassNametoString());
                                        java_source = java_source + "import " + s + "; \n";
                                    }catch (Exception e){
                                        System.out.println("NOT IMPORTING PACKAGES AS IMPORT");
                                    }           
                                }
                            }
                            
                            for(UMLVariables impo: ((UMLInterfaces) ll).getVariableNames()){ //importing
                                Package[] pack = Package.getPackages();
                                
                                for(int i = 0; i < pack.length; i++){
                                    try{
                                        if(!impo.getType().equals("Text")){
                                            if(!impo.getType().equals("Event")){
                                                String s = pack[i].getName() + "." + impo.getType();
                                                Class.forName(s);
                                                java_source = java_source + "import " + pack[i].getName() + "." + impo.getType() + "; \n";
                                            }
                                        }
                                        
                                    }catch (Exception e){
                                        System.out.println("The api doesn't match");
                                    }
                                }      
                            }
                            
                            ArrayList<UMLMethods> ml = ((UMLInterfaces) ll).getMethodNames(); 
                            for(int i = 0; i < ml.size(); i++){ //for every method in ArrayList<Methods>, look at one method
                                UMLMethods sz = ml.get(i);
                                Package[] hello = Package.getPackages();
                                
                                for(int j = 0; j < sz.getArgs().size(); j++){ //for every argument in that method, look at the arguments
                                    
                                    String l = sz.getArgs().get(j);
                                    for(int k = 0; k < hello.length; k++){
                                    String mt = hello[k].getName() + "." + l;
                                    try{
                                        if(!l.equals("Event")){
                                            if(!l.equals("Text")){
                                                Class.forName(mt);
                                                java_source = java_source + "import " + hello[k].getName() + "." + l + "; \n";
                                            }
                                        }
                                    }catch (Exception e){
                                        System.out.println("Arguments API not matched");
                                    }
                                    }
                                }
                            }
                            
                            
                            
                            //method to printwriter
                            java_source = java_source + "public interface " + ((UMLInterfaces) ll).getInterNametoString() + " { \n";
                             
                            for(UMLVariables k: ((UMLInterfaces) ll).getVariableNames()){
                                //sc = ((UMLClasses) ll).getVariableNames();
                                //k has name, type, static?, access?
                                java_source = java_source + "public final ";
                                java_source = java_source + k.getType() + " " + k.getName() + " ";
                                
                                //expand on this
                                if(k.getType().equals("int") || k.getType().equals("double") ||
                                        k.getType().equals("short") || k.getType().equals("float")
                                        || k.getType().equals("byte") || k.getType().equals("long"))
                                    java_source = java_source + "= 0";
                                else if(k.getType().equals("boolean"))
                                    java_source = java_source + "= false";
                                else if(k.getType().equals("char"))
                                    java_source = java_source + "'c'";
                                else
                                    java_source = java_source + "= null";
                                
                                java_source = java_source + "; \n";
                            }
                            
                            for(UMLMethods m: ((UMLInterfaces) ll).getMethodNames()){
                                //name, returntype, statictype, abstracttype, accesstype, args

                                java_source = java_source + "public void ";
                                java_source = java_source + m.getName() + "( ";
                                
                                if(!m.getArgs().isEmpty()){
                                    for(int i = 0; i < m.getArgs().size()-1; i++){ //except the last method
                                        java_source = java_source + m.getArgs().get(i) + " arg" + Integer.toString(i) + ", ";     
                                    }
                                
                                //for the last argument
                                    java_source = java_source + m.getArgs().get(m.getArgs().size() - 1) + " arg" +
                                        Integer.toString(m.getArgs().size() - 1);
                                }
                                java_source = java_source + "); \n"; 
                            }
                            java_source = java_source + "}";
                          PrintWriter out = new PrintWriter(filePath + innerString + "/" + 
                          ((UMLInterfaces) ll).getInterNametoString() + ".java");
                            
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
