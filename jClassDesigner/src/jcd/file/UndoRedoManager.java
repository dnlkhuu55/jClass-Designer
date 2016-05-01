/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.file;

import af.components.AppDataComponent;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Stack;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import jcd.data.ClassLines;
import jcd.data.DataManager;
import jcd.data.UMLClasses;
import jcd.data.UMLInterfaces;
import jcd.data.UMLMArgs;
import jcd.data.UMLMethods;
import jcd.data.UMLVariables;

/**
 *
 * @author dnlkhuu77
 */
public class UndoRedoManager {
    Stack undoStack = new Stack();
    Stack redoStack = new Stack();
    
    public void clearUndo(){
        undoStack.clear();
    }
    public void clearRedo(){
        redoStack.empty();
    }
    
    public void undoData(AppDataComponent data) throws IOException {
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
        ArrayList<UMLMArgs> args = new ArrayList<>();
        Pane leftPane = expo.getLeftPane();
        int counter = 0;
        int counter2 = 0;
        
        JsonObjectBuilder ll = Json.createObjectBuilder();
        ll.add("LeftPaneX", leftPane.getScaleX()).add("LeftPaneY", leftPane.getScaleY()).add("Flag", -1);
        arrayBuilder.add(ll.build());
        
        for(VBox sh: test){ //arrayBuilder is the array while testing is the object
            counter = 0;
            counter2 = 0;
            JsonObjectBuilder testing = Json.createObjectBuilder();
            if(sh instanceof UMLClasses){
            testing.add("Name", ((UMLClasses) sh).getClassNametoString()).add("Abstract", ((UMLClasses) sh).isAbstract())
		.add("Package", ((UMLClasses) sh).getPackageName())
                 .add("Flag", 0).add("T_X", ((UMLClasses) sh).getTranslateXer())
                    .add("T_Y", ((UMLClasses) sh).getTranslateYer()).add("Width", sh.getWidth())
                    .add("Height", sh.getHeight()).add("Scale_X", sh.getScaleX()).add("Scale_Y", sh.getScaleY());
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

               innerVar.add("VarName", variable.getName()).add("Type", variable.getType()).add("Static", variable.isStatictype()).add("Access", variable.getAccesstype());
               innerBuilder.add(innerVar.build());
            }
            ///////
            for(UMLMethods method: mets){ //methodBuilder is the array while the innerMEthod is the object
                counter = 0;
                JsonObjectBuilder innerMeth = Json.createObjectBuilder();
                
                innerMeth.add("MethName", method.getName()).add("ReturnType", method.getReturntype())
                .add("StaticType", method.isStatictype()).add("AbstractType", method.isAbstractype())
                        .add("AccessType", method.getAccesstype());
                
                args = method.getArgs();
                for(UMLMArgs arguments: args){ //argsBuilder is the array while the matrix is the object
                    JsonObjectBuilder matrix = Json.createObjectBuilder();
                    matrix.add("Arg" + Integer.toString(counter), arguments.getArgstype());
                    
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
                    .add("T_Y", ((UMLInterfaces) sh).getTranslateYer()).add("Width", sh.getWidth())
                    .add("Height", sh.getHeight()).add("Scale_X", sh.getScaleX()).add("Scale_Y", sh.getScaleY());
            
            vars = ((UMLInterfaces) sh).getVariableNames(); //the inner variable class
            mets = ((UMLInterfaces) sh).getMethodNames();
            ////////
            for(UMLVariables variable: vars){ //innerBuilder is the array while innerVar is the object
                JsonObjectBuilder innerVar = Json.createObjectBuilder();

               innerVar.add("VarName", variable.getName()).add("Type", variable.getType()).add("Static", variable.isStatictype()).add("Access", variable.getAccesstype());
               innerBuilder.add(innerVar.build());
            }
            ///////
            for(UMLMethods method: mets){ //methodBuilder is the array while the innerMEthod is the object
                JsonObjectBuilder innerMeth = Json.createObjectBuilder();
                
                innerMeth.add("MethName", method.getName()).add("ReturnType", method.getReturntype())
                .add("StaticType", method.isStatictype()).add("AbstractType", method.isAbstractype())
                        .add("AccessType", method.getAccesstype());
                
                args = method.getArgs();
                for(UMLMArgs arguments: args){ //argsBuilder is the array while the matrix is the object
                    JsonObjectBuilder matrix = Json.createObjectBuilder();
                    matrix.add("Arg" + Integer.toString(counter), arguments.getArgstype());
                    
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
        
       undoStack.push(nodesArray);
    }
    
     public void loadundoData(AppDataComponent data) throws IOException {
        try{
            JsonArray json = (JsonArray) undoStack.pop();
            redoStack.push(json); //if error, put this at the end
            
        DataManager expo = (DataManager)data;
	//expo.reset();
        expo.getClassList().clear();
        expo.getLineList().clear();
	
        int flag;
        
        ArrayList<UMLVariables> vars = new ArrayList<>();
        ArrayList<UMLMethods> mets = new ArrayList<>();
        ArrayList<UMLMArgs> args = new ArrayList<>();
        ArrayList<Line> lines = new ArrayList<>();
        Pane ll = new Pane();
        int counter = 0;
        int counter2 = 0;

        for(JsonValue sh: json){
            JsonObject shaping = (JsonObject) sh;
            flag = shaping.getInt("Flag");
            
            if(flag == -1){
                ll.setScaleX(shaping.getJsonNumber("LeftPaneX").doubleValue());
                ll.setScaleY(shaping.getJsonNumber("LeftPaneY").doubleValue());
                expo.setLeftPane(ll);
            }
            
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
                r.setWidthy(shaping.getJsonNumber("Width").doubleValue());
                r.setHeighty(shaping.getJsonNumber("Height").doubleValue());
                r.setScaleX(shaping.getJsonNumber("Scale_X").doubleValue());
                r.setScaleY(shaping.getJsonNumber("Scale_Y").doubleValue());
                
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
                    v.setAccesstype(into_var.getString("Access"));
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
                    m.setAccesstype(into_met.getString("AccessType"));
                    
                    args = m.getArgs();
                    //args
                    JsonArray argsLoading = into_met.getJsonArray("arguments");
                    for(JsonValue load_arguments: argsLoading){
                        JsonObject into_args = (JsonObject) load_arguments;
                        //args.add(into_args.getString("Arg" + Integer.toString(counter)));
                        UMLMArgs a = new UMLMArgs(into_args.getString("Arg" + Integer.toString(counter)));
                        args.add(a);
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
                s.setWidthy(shaping.getJsonNumber("Width").doubleValue());
                s.setHeighty(shaping.getJsonNumber("Height").doubleValue());
                s.setInterName(shaping.getString("Name"));
                
                s.setInterNametoString(shaping.getString("Name"));
                s.setPackageName(shaping.getString("Package"));

                s.setTranslateXer(shaping.getJsonNumber("T_X").doubleValue());
                s.setTranslateYer(shaping.getJsonNumber("T_Y").doubleValue());
                s.setScaleX(shaping.getJsonNumber("Scale_X").doubleValue());
                s.setScaleY(shaping.getJsonNumber("Scale_Y").doubleValue());
                ////variables
                vars = s.getVariableNames();
                
                JsonArray varLoading = shaping.getJsonArray("variables");
                for(JsonValue load_var: varLoading){
                    JsonObject into_var = (JsonObject) load_var;
                    UMLVariables v = new UMLVariables();
                    
                    v.setName(into_var.getString("VarName"));
                    v.setType(into_var.getString("Type"));
                    v.setStatictype(into_var.getBoolean("Static"));
                    v.setAccesstype(into_var.getString("Access"));
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
                    m.setAccesstype(into_met.getString("AccessType"));
                    
                    args = m.getArgs();
                    //args
                    JsonArray argsLoading = into_met.getJsonArray("arguments");
                    for(JsonValue load_arguments: argsLoading){
                        JsonObject into_args = (JsonObject) load_arguments;
                        //args.add(into_args.getString("Arg" + Integer.toString(counter)));
                        UMLMArgs a = new UMLMArgs(into_args.getString("Arg" + Integer.toString(counter)));
                        args.add(a);
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
        }catch(Exception ex){
            System.out.println("This undo cannot be loaded. Try again!");
        }
    }
     
      public void loadredoData(AppDataComponent data) throws IOException {
        try{
        DataManager expo = (DataManager)data;
	//expo.reset();
        expo.getClassList().clear();
        expo.getLineList().clear();
	
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonArray json = (JsonArray) redoStack.pop();
        undoStack.push(json);
        
        int flag;
        
        ArrayList<UMLVariables> vars = new ArrayList<>();
        ArrayList<UMLMethods> mets = new ArrayList<>();
        ArrayList<UMLMArgs> args = new ArrayList<>();
        Pane ll = new Pane();
        ArrayList<Line> lines = new ArrayList<>();
        int counter = 0;
        int counter2 = 0;

        
        for(JsonValue sh: json){
            JsonObject shaping = (JsonObject) sh;
            flag = shaping.getInt("Flag");
            
            if(flag == -1){
                ll.setScaleX(shaping.getJsonNumber("LeftPaneX").doubleValue());
                ll.setScaleY(shaping.getJsonNumber("LeftPaneY").doubleValue());
                expo.setLeftPane(ll);
            }
            
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
                r.setWidthy(shaping.getJsonNumber("Width").doubleValue());
                r.setHeighty(shaping.getJsonNumber("Height").doubleValue());
                r.setScaleX(shaping.getJsonNumber("Scale_X").doubleValue());
                r.setScaleY(shaping.getJsonNumber("Scale_Y").doubleValue());
                
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
                    v.setAccesstype(into_var.getString("Access"));
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
                    m.setAccesstype(into_met.getString("AccessType"));
                    
                    args = m.getArgs();
                    //args
                    JsonArray argsLoading = into_met.getJsonArray("arguments");
                    for(JsonValue load_arguments: argsLoading){
                        JsonObject into_args = (JsonObject) load_arguments;
                        //args.add(into_args.getString("Arg" + Integer.toString(counter)));
                        UMLMArgs a = new UMLMArgs(into_args.getString("Arg" + Integer.toString(counter)));
                        args.add(a);
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
                s.setWidthy(shaping.getJsonNumber("Width").doubleValue());
                s.setHeighty(shaping.getJsonNumber("Height").doubleValue());
                s.setInterName(shaping.getString("Name"));
                
                s.setInterNametoString(shaping.getString("Name"));
                s.setPackageName(shaping.getString("Package"));

                s.setTranslateXer(shaping.getJsonNumber("T_X").doubleValue());
                s.setTranslateYer(shaping.getJsonNumber("T_Y").doubleValue());
                s.setScaleX(shaping.getJsonNumber("Scale_X").doubleValue());
                s.setScaleY(shaping.getJsonNumber("Scale_Y").doubleValue());
                ////variables
                vars = s.getVariableNames();
                
                JsonArray varLoading = shaping.getJsonArray("variables");
                for(JsonValue load_var: varLoading){
                    JsonObject into_var = (JsonObject) load_var;
                    UMLVariables v = new UMLVariables();
                    
                    v.setName(into_var.getString("VarName"));
                    v.setType(into_var.getString("Type"));
                    v.setStatictype(into_var.getBoolean("Static"));
                    v.setAccesstype(into_var.getString("Access"));
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
                    m.setAccesstype(into_met.getString("AccessType"));
                    
                    args = m.getArgs();
                    //args
                    JsonArray argsLoading = into_met.getJsonArray("arguments");
                    for(JsonValue load_arguments: argsLoading){
                        JsonObject into_args = (JsonObject) load_arguments;
                        //args.add(into_args.getString("Arg" + Integer.toString(counter)));
                        UMLMArgs a = new UMLMArgs(into_args.getString("Arg" + Integer.toString(counter)));
                        args.add(a);
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
        }catch(Exception ex){
            System.out.println("This file cannot be loaded. Try again!");
        }
    }
     
}
