/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.file;

import af.components.AppDataComponent;
import javafx.geometry.Point2D;
import jcd.data.ClassLines;
import jcd.data.DataManager;
import jcd.data.UMLClasses;
import jcd.data.UMLMethods;
import jcd.data.UMLVariables;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import properties_manager.PropertiesManager;

/**
 *
 * @author dnlkhuu77
 */
public class FileManagerTest {
    static DataManager d;
    static FileManager f;
    static UMLClasses s;
    static UMLVariables v;
    static UMLMethods m;
    static ClassLines l;
    public FileManagerTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        //FileManager f = new FileManager();
        f = new FileManager();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try{
           //DataManager d = new DataManager(null);
           d = new DataManager(null); //TESTING
           UMLClasses firstpart = new UMLClasses("ThreadExample", " ", " ", "", "AAA", 20, 100);

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
           
           UMLVariables vegas1 = new UMLVariables("app", "ThreadExample", false, false);
           UMLVariables vegas2 = new UMLVariables("counter", "int", false, false);
           secondpart.getVariableNames().add(vegas1);
           secondpart.getVariableNames().add(vegas2);
           
           UMLMethods methane1 = new UMLMethods("CounterTask", " ", false, false, true);
           methane1.getArgs().add("ThreadExample");
           UMLMethods methane2 = new UMLMethods("call", "void", false, false, false);
           
           secondpart.getMethodNames().add(methane1);
           secondpart.getMethodNames().add(methane2);
           
           d.getClassList().add(secondpart);
           
           ///  
           UMLClasses thirdpart = new UMLClasses("DateTask", " ", " ", "", "CCC", 75, 19);
           
           UMLVariables nachos1 = new UMLVariables("app", "ThreadExample", false, false);
           UMLVariables nachos2 = new UMLVariables("now", "Date", false, false);
           thirdpart.getVariableNames().add(nachos1);
           thirdpart.getVariableNames().add(nachos2);
           
           UMLMethods pizza1 = new UMLMethods("DateTask", " ", false, false, true);
           pizza1.getArgs().add("ThreadExample");
           UMLMethods pizza2 = new UMLMethods("call", "void", false, false, false);
           
           thirdpart.getMethodNames().add(pizza1);
           thirdpart.getMethodNames().add(pizza2);
           
           d.getClassList().add(thirdpart);
           ////
           
           UMLClasses fourthpart = new UMLClasses("PauseHandler", " ", " ", "", "PackTest", 100, 200);
           
           UMLVariables phil1 = new UMLVariables("PauseHandler", "ThreadExample", false, true);
           UMLVariables phil2 = new UMLVariables("app", "ThreadExample", false, false);
           fourthpart.getVariableNames().add(phil1);
           fourthpart.getVariableNames().add(phil2);
           
           UMLMethods tacos1 = new UMLMethods("handle", "void", false, false, true);
           tacos1.getArgs().add("Event");
           
           fourthpart.getMethodNames().add(tacos1);
           
           d.getClassList().add(fourthpart);
           ////
           UMLClasses fifthpart = new UMLClasses("StartHandler", " ", " ", "", "PackTest", 5, 1);
           
           UMLVariables dark1 = new UMLVariables("StartHandler", "ThreadExample", false, true);
           UMLVariables dark2 = new UMLVariables("app", "ThreadExample", false, false);
           fifthpart.getVariableNames().add(dark1);
           fifthpart.getVariableNames().add(dark2);
           
           UMLMethods nice1 = new UMLMethods("handle", "void", false, false, true);
           nice1.getArgs().add("Event");
           
           fifthpart.getMethodNames().add(nice1);
          
           d.getClassList().add(fifthpart);
          // f.saveData(d, "./work/JUnitTest1.json");
           ////
           ClassLines first_line = new ClassLines();
           first_line.setStartX(0);
           first_line.setStartY(0);
           first_line.setEndX(10);
           first_line.setEndY(10);
           first_line.setMid_x();
           first_line.setMid_y();
           first_line.setStart_node("Test1");
           first_line.setEnd_node("Test2");
           
           //d.getClassList().add(firstpart);
           d.getLineList().add(first_line);
           f.saveData(d, "./work/JUnitTest1.json");
           
        } catch(Exception e){
            System.out.println("NOT WORKING");
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of loadData method, of class FileManager.
     */
    
    
    @Test
    public void testLoadData() throws Exception {
        System.out.println("loadData");
        String filePath = "./work/JUnitTest1.json";
        //FileManager instance = new FileManager();
        f.loadData(d, filePath);
        // TODO review the generated test code and remove the default call to fail.
        s = (UMLClasses) d.getClassList().get(0);
        l = (ClassLines) d.getLineList().get(0);
        
        assertEquals("ThreadExample", s.getClassNametoString());
        System.out.println("ClassName Result: " + s.getClassNametoString());
        //fail("The test case is a prototype.");
        
        assertEquals("AAA", s.getPackageName());
        System.out.println("PackageName Result: " + s.getPackageName());
        
        assertEquals(false, s.isAbstract());
        System.out.println("Is is the class abstract? " + s.isAbstract());
        
        assertEquals("20.0", Double.toString(s.getTranslateXer()));
        System.out.println("X location Result: " + s.getTranslateXer());
        
        assertEquals("100.0", Double.toString(s.getTranslateYer()));
        System.out.println("Y location Result: " + s.getTranslateYer());
        
        v = s.getVariableNames().get(0);
        
        assertEquals("START_TEXT", v.getName());
        System.out.println("Variable #1 Name: " + v.getName());
        
        assertEquals("String", v.getType());
        System.out.println("Variable #1 Type: " + v.getType());
        
        assertEquals(true, v.isStatictype());
        System.out.println("Is it static? " + v.isStatictype());
        
        assertEquals(true, v.isAccesstype());
        System.out.println("Is it public? " + v.isAccesstype());
        
        assertEquals("5.0", Double.toString(l.getMid_x()));
        System.out.println("Midpoint of Line: " + l.getMid_x());
        
        assertEquals("10.0", Double.toString(l.getEndX()));
        System.out.println("Ending X: " + l.getEndX());
        
        assertEquals("0.0", Double.toString(l.getStartX()));
        System.out.println("Starting X: " + l.getStartX());
    }
    
}
