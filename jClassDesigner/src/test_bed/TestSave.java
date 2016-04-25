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
import jcd.data.ClassLines;
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
           UMLClasses firstpart = new UMLClasses("ThreadExample", " ", " ", "", "AAA", 20, 100);

           //static ($), access (+/-)
           UMLVariables var1 = new UMLVariables("START_TEXT", "String", true, "public");
           UMLVariables var2 = new UMLVariables("PAUSE_TEXT", "String", true, "public");
           UMLVariables var3 = new UMLVariables("window", "Stage", false, "private");
           UMLVariables var4 = new UMLVariables("appPane", "BorderPane", false, "private");
           UMLVariables var5 = new UMLVariables("topPane", "FlowPane", false, "private");
           UMLVariables var6 = new UMLVariables("startButton", "Button", false, "private");
           UMLVariables var7 = new UMLVariables("pauseButton", "Button", false, "private");
           UMLVariables var8 = new UMLVariables("scrollPane", "ScrollPane", false, "private");
           UMLVariables var9 = new UMLVariables("textArea", "Text", false, "private");
           UMLVariables var10 = new UMLVariables("dateThread", "Thread", false, "private");
           UMLVariables var11 = new UMLVariables("dateTask", "Task", false, "private");
           UMLVariables var12 = new UMLVariables("counterThread", "Thread", false, "private");
           UMLVariables var13 = new UMLVariables("counterTask", "Task", false, "private");
           UMLVariables var14 = new UMLVariables("work", "boolean", false, "private");
           
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
           UMLMethods met1 = new UMLMethods("start", "void", false, false, "public");
           met1.getArgs().add("Stage");
           UMLMethods met2 = new UMLMethods("startWork", "void", false, false, "public");
           UMLMethods met3 = new UMLMethods("pauseWork", "void", false, false, "public"); 
           UMLMethods met4 = new UMLMethods("doWork", "boolean", false, false, "public");
           UMLMethods met5 = new UMLMethods("appendText", "void", false, false, "public");
           met5.getArgs().add("String");
           UMLMethods met6 = new UMLMethods("sleep", "void", false, false, "public"); 
           met6.getArgs().add("int");
           UMLMethods met7 = new UMLMethods("initLayout", "void", false, false, "private");
           UMLMethods met8 = new UMLMethods("initHandlers", "void", false, false, "private");
           UMLMethods met9 = new UMLMethods("initWindow", "void", false, false, "private");
           met9.getArgs().add("Stage");
           UMLMethods met10 = new UMLMethods("initThreads", "void", false, false, "private"); 
           UMLMethods met11 = new UMLMethods("main", "void", true, false, "public"); 
           met11.getArgs().add("String[] ");
           
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
           
           /////
           UMLClasses secondpart = new UMLClasses("CounterTask", " ", " ", "", "BBB", 50, 100);
           
           UMLVariables vegas1 = new UMLVariables("app", "ThreadExample", false, "public");
           UMLVariables vegas2 = new UMLVariables("counter", "int", false, "public");
           secondpart.getVariableNames().add(vegas1);
           secondpart.getVariableNames().add(vegas2);
           
           UMLMethods methane1 = new UMLMethods("CounterTask", " ", false, false, "private");
           methane1.getArgs().add("ThreadExample");
           UMLMethods methane2 = new UMLMethods("call", "void", false, false, "private");
           
           secondpart.getMethodNames().add(methane1);
           secondpart.getMethodNames().add(methane2);
           
           secondpart.setParentName("ThreadExample");
           
           d.getClassList().add(secondpart);
           
           ///  
           UMLClasses thirdpart = new UMLClasses("DateTask", " ", " ", "", "CCC", 75, 19);
           
           UMLVariables nachos1 = new UMLVariables("app", "ThreadExample", false, "private");
           UMLVariables nachos2 = new UMLVariables("now", "Date", false, "private");
           thirdpart.getVariableNames().add(nachos1);
           thirdpart.getVariableNames().add(nachos2);
           
           UMLMethods pizza1 = new UMLMethods("DateTask", " ", false, false, "private");
           pizza1.getArgs().add("ThreadExample");
           UMLMethods pizza2 = new UMLMethods("call", "void", false, false, "private");
           
           thirdpart.getMethodNames().add(pizza1);
           thirdpart.getMethodNames().add(pizza2);
           
           thirdpart.setParentName("ThreadExample");
           
           d.getClassList().add(thirdpart);
           ////
           
           UMLClasses fourthpart = new UMLClasses("PauseHandler", " ", " ", "", "PackTest", 100, 200);
           
           UMLVariables phil1 = new UMLVariables("PauseHandler", "ThreadExample", false, "private");
           UMLVariables phil2 = new UMLVariables("app", "ThreadExample", false, "private");
           fourthpart.getVariableNames().add(phil1);
           fourthpart.getVariableNames().add(phil2);
           
           UMLMethods tacos1 = new UMLMethods("handle", "void", false, false, "public");
           tacos1.getArgs().add("Event");
           
           fourthpart.getMethodNames().add(tacos1);
           
           fourthpart.setParentName("ThreadExample");
           
           d.getClassList().add(fourthpart);
           ////
           UMLClasses fifthpart = new UMLClasses("StartHandler", " ", " ", "", "PackTest", 5, 1);
           
           UMLVariables dark1 = new UMLVariables("StartHandler", "ThreadExample", false, "public");
           UMLVariables dark2 = new UMLVariables("app", "ThreadExample", false, "public");
           fifthpart.getVariableNames().add(dark1);
           fifthpart.getVariableNames().add(dark2);
           
           UMLMethods nice1 = new UMLMethods("handle", "void", false, false, "public");
           nice1.getArgs().add("Event");
           
           fifthpart.setParentName("ThreadExample");
           
           fifthpart.getMethodNames().add(nice1);
           
           d.getClassList().add(fifthpart);
           ////
           
           ClassLines l = new ClassLines("Test1", "Test2");
           l.setStartX(3);
           l.setStartY(10);
           l.setEndX(15);
           l.setEndY(13);
           l.setMid_x();
           l.setMid_y();
           d.getLineList().add(l);
           
           f.saveData(d, "./work/DesignSaveTest.json");
           
        } catch(Exception e){
            System.out.println("NOT WORKING");
        }
        
    }
    
}
