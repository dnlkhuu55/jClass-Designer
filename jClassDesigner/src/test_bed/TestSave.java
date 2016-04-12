/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import af.AppTemplate;
import static af.settings.AppPropertyType.SAVE_WORK_TITLE;
import static af.settings.AppPropertyType.WORK_FILE_EXT;
import static af.settings.AppPropertyType.WORK_FILE_EXT_DESC;
import static af.settings.AppStartupConstants.PATH_WORK;
import java.io.File;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import jcd.data.DataManager;
import jcd.data.UMLClasses;
import jcd.data.UMLMethods;
import jcd.data.UMLVariables;
import jcd.file.FileManager;
import properties_manager.PropertiesManager;

/**
 *
 * @author dnlkhuu77
 */
public class TestSave {
    public static void main(String[] args){

        FileManager f = new FileManager();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try{
           DataManager d = new DataManager(null);
           UMLClasses firstpart = new UMLClasses("ThreadExample", " ", " ", null, "PackTest", 20, 100);
           
           //static ($), access (+/-)
           UMLVariables var1 = new UMLVariables("START_TEXT", "String", true, true);
           UMLVariables var2 = new UMLVariables("PAUSE_TEXT", "String", true, true);
           UMLVariables var3 = new UMLVariables("window", "Stage", false, false);
           UMLVariables var4 = new UMLVariables("appPane", "BorderPane", false, false);
           UMLVariables var5 = new UMLVariables("topPane", "FlowPane", false, false);
           UMLVariables var6 = new UMLVariables("startButton", "Button", false, false);
           UMLVariables var7 = new UMLVariables("pauseButton", "Button", false, false);
           UMLVariables var8 = new UMLVariables("scrollPane", "ScrollPane", false, false);
           UMLVariables var9 = new UMLVariables("textArea", "Text", false, false);
           UMLVariables var10 = new UMLVariables("dateThread", "Thread", false, false);
           UMLVariables var11 = new UMLVariables("dateTask", "Task", false, false);
           UMLVariables var12 = new UMLVariables("counterThread", "Thread", false, false);
           UMLVariables var13 = new UMLVariables("counterTask", "Task", false, false);
           UMLVariables var14 = new UMLVariables("work", "boolean", false, false);
           
           firstpart.getVariableNames().add(var1);
           firstpart.getVariableNames().add(var2);
           firstpart.getVariableNames().add(var3);
           firstpart.getVariableNames().add(var4);
           firstpart.getVariableNames().add(var5);
           firstpart.getVariableNames().add(var6);
           firstpart.getVariableNames().add(var7);
           firstpart.getVariableNames().add(var8);
           firstpart.getVariableNames().add(var9);
           firstpart.getVariableNames().add(var10);
           firstpart.getVariableNames().add(var11);
           firstpart.getVariableNames().add(var12);
           firstpart.getVariableNames().add(var13);
           firstpart.getVariableNames().add(var14);

           d.getClassList().add(firstpart);
           
           //name, returntype, statictype($), abstractype(#), accesstype(+/-)
           UMLMethods met1 = new UMLMethods("start", "void", false, false, true);
           met1.getArgs().add("Stage");
           UMLMethods met2 = new UMLMethods("startWork", "void", false, false, true);
           UMLMethods met3 = new UMLMethods("pauseWork", "void", false, false, true); 
           UMLMethods met4 = new UMLMethods("doWork", "boolean", false, false, true);
           UMLMethods met5 = new UMLMethods("appendText", "void", false, false, true);
           met5.getArgs().add("String");
           UMLMethods met6 = new UMLMethods("sleep", "void", false, false, true); 
           met6.getArgs().add("int");
           UMLMethods met7 = new UMLMethods("initLayout", "void", false, false, false);
           UMLMethods met8 = new UMLMethods("initHandlers", "void", false, false, false);
           UMLMethods met9 = new UMLMethods("initWindow", "void", false, false, false);
           met9.getArgs().add("Stage");
           UMLMethods met10 = new UMLMethods("initThreads", "void", false, false, false); 
           UMLMethods met11 = new UMLMethods("main", "void", true, false, true); 
           
           firstpart.getMethodNames().add(met1);
           firstpart.getMethodNames().add(met2);
           firstpart.getMethodNames().add(met3);
           firstpart.getMethodNames().add(met4);
           firstpart.getMethodNames().add(met5);
           firstpart.getMethodNames().add(met6);
           firstpart.getMethodNames().add(met7);
           firstpart.getMethodNames().add(met8);
           firstpart.getMethodNames().add(met9);
           firstpart.getMethodNames().add(met10);
           firstpart.getMethodNames().add(met11);
           
           f.saveData(d, "./export/test.json");
           
        } catch(Exception e){
            System.out.println("NOT WORKING");
        }
        
        
    }
    
}
