/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcd.file;

import af.components.AppDataComponent;
import jcd.data.ClassLines;
import jcd.data.DataManager;
import jcd.data.UMLClasses;
import jcd.data.UMLMethods;
import jcd.data.UMLVariables;
import static jcd.file.FileManagerTest.d;
import static jcd.file.FileManagerTest.f;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import properties_manager.PropertiesManager;

/**
 *
 * @author dnlkhuu77
 */
public class FileManagerSecondTest {
    static DataManager d;
    static FileManager f;
    static UMLClasses s;
    static UMLClasses parentTest;
    static UMLVariables v;
    static UMLMethods m;
    static UMLMethods m2;
    public FileManagerSecondTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        //FileManager f = new FileManager();
        f = new FileManager();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try{
           d = new DataManager(null); //TESTING
           UMLClasses firstpart = new UMLClasses("Example1", " ", " ", "", "javasquare", 0, 98);
           firstpart.setIsAbstract(true);
           
           //static ($), access (+/-)
           UMLVariables var1 = new UMLVariables("LOOKING", "String", false, false);
           firstpart.getVariableNames().add(var1);
           
           //name, returntype, statictype($), abstractype(#), accesstype(+/-)
           UMLMethods met1 = new UMLMethods("TestMethod1", "void", false, true, true);
           UMLMethods met2 = new UMLMethods("TestMethod2", "int", false, true, true);
           met1.getArgs().add("BorderPane");
           met1.getArgs().add("double");
           met2.getArgs().add("int");
           
           firstpart.getMethodNames().add(met1);
           firstpart.getMethodNames().add(met2);
           d.getClassList().add(firstpart);
           
           UMLClasses secondpart = new UMLClasses("CounterTask", " ", " ", "", "BBB", 50, 100);
           
           UMLVariables vegas1 = new UMLVariables("app", "Example1", false, false);
           UMLVariables vegas2 = new UMLVariables("counter", "int", false, false);
           secondpart.getVariableNames().add(vegas1);
           secondpart.getVariableNames().add(vegas2);
           
           UMLMethods methane1 = new UMLMethods("CounterTask", " ", false, false, true);
           methane1.getArgs().add("Example1");
           UMLMethods methane2 = new UMLMethods("call", "void", false, false, false);
           
           secondpart.getMethodNames().add(methane1);
           secondpart.getMethodNames().add(methane2);
           
           d.getClassList().add(secondpart);
           
           ///  
           UMLClasses thirdpart = new UMLClasses("DateTask", " ", " ", "", "CCC", 75, 19);
           
           UMLVariables nachos1 = new UMLVariables("app", "Example1", false, false);
           UMLVariables nachos2 = new UMLVariables("now", "Date", false, false);
           thirdpart.getVariableNames().add(nachos1);
           thirdpart.getVariableNames().add(nachos2);
           
           UMLMethods pizza1 = new UMLMethods("DateTask", " ", false, false, true);
           pizza1.getArgs().add("Example1");
           UMLMethods pizza2 = new UMLMethods("call", "void", false, false, false);
           
           thirdpart.getMethodNames().add(pizza1);
           thirdpart.getMethodNames().add(pizza2);
           
           d.getClassList().add(thirdpart);
           ////
           
           UMLClasses fourthpart = new UMLClasses("PauseHandler", " ", " ", "", "PackTest", 100, 200);
           
           UMLVariables phil1 = new UMLVariables("PauseHandler", "Example1", false, true);
           UMLVariables phil2 = new UMLVariables("app", "Example1", false, false);
           fourthpart.getVariableNames().add(phil1);
           fourthpart.getVariableNames().add(phil2);
           
           UMLMethods tacos1 = new UMLMethods("handle", "void", false, false, true);
           tacos1.getArgs().add("Event");
           
           fourthpart.getMethodNames().add(tacos1);
           
           d.getClassList().add(fourthpart);
           ////
           UMLClasses fifthpart = new UMLClasses("StartHandler", " ", " ", "", "PackTest", 5, 1);
           
           UMLVariables dark1 = new UMLVariables("StartHandler", "Example1", false, true);
           UMLVariables dark2 = new UMLVariables("app", "Example1", false, false);
           fifthpart.getVariableNames().add(dark1);
           fifthpart.getVariableNames().add(dark2);
           
           UMLMethods nice1 = new UMLMethods("handle", "void", false, false, true);
           UMLMethods met11 = new UMLMethods("main", "void", true, false, true); 
           met11.getArgs().add("String[] ");
           nice1.getArgs().add("Event");
           
           fifthpart.getMethodNames().add(nice1);
           fifthpart.getMethodNames().add(met11);
          
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
           d.getLineList().add(first_line);
           
           f.saveData(d, "./work/JUnitTest2.json");
           ////
           
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
        String filePath = "./work/JUnitTest2.json";
        //FileManager instance = new FileManager();
        f.loadData(d, filePath);
        // TODO review the generated test code and remove the default call to fail.
        s = (UMLClasses) d.getClassList().get(0);
        
        assertEquals("Example1", s.getClassNametoString());
        System.out.println("ClassName Result: " + s.getClassNametoString());
        //fail("The test case is a prototype.");
        
        assertEquals("javasquare", s.getPackageName());
        System.out.println("PackageName Result: " + s.getPackageName());
        
        assertEquals("", s.getParentName());
        System.out.println("Is is the class abstract? " + s.getParentName());
        
        assertEquals("0.0", Double.toString(s.getTranslateXer()));
        System.out.println("X location Result: " + s.getTranslateXer());
        
        assertEquals("98.0", Double.toString(s.getTranslateYer()));
        System.out.println("Y location Result: " + s.getTranslateYer());
        
        m = s.getMethodNames().get(0);
        
        assertEquals("TestMethod1", m.getName());
        System.out.println("Method Name #1: " + m.getName());
        
        assertEquals("void", m.getReturntype());
        System.out.println("Return Type #1: " + m.getReturntype());
        
        m2 = s.getMethodNames().get(1);
        
        assertEquals("TestMethod2", m2.getName());
        System.out.println("Method Name #2: " + m2.getName());
        
        assertEquals("int", m2.getReturntype());
        System.out.println("Return Type #2: " + m2.getReturntype());
        
        String s = m2.getArgs().get(0);
        
        assertEquals("int", s);
        System.out.println("Argument Type of Method #2: " + s);
    }
    
}
