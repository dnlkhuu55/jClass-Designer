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
import jcd.data.UMLInterfaces;
import jcd.data.UMLMethods;
import jcd.data.UMLVariables;
import static jcd.file.FileManagerSecondTest.f;
import static jcd.file.FileManagerTest.d;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import properties_manager.PropertiesManager;

/**
 *
 * @author dnlkhuu77
 */
public class FileManagerFinalTest {
    static DataManager d;
    static FileManager f;
    static UMLInterfaces s;
    static UMLVariables v;
    static UMLMethods m;
    static UMLMethods m2;
    static ClassLines l;
    
    public FileManagerFinalTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        //FileManager f = new FileManager();
        f = new FileManager();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        try{
           d = new DataManager(null); //TESTING
           //(String a, String variabley, String methody, String packagey, double x_er, double y_er)
           UMLInterfaces firstpart = new UMLInterfaces("Interface_Test", " ", " ", "PackageA", 55, 110);
           
           //static ($), access (+/-)
           UMLVariables var1 = new UMLVariables("LOOKING", "int", false, "public");
           firstpart.getVariableNames().add(var1);
           
           //name, returntype, statictype($), abstractype(#), accesstype(+/-)
           // (ABSTRACT METHODS ARE NEVEN STATIC)
           UMLMethods met1 = new UMLMethods("TestMethod1", "void", false, true, "public");
           UMLMethods met2 = new UMLMethods("TestMethod2", "int", false, true, "public");
           met1.getArgs().add("String");
           met1.getArgs().add("double");
           met2.getArgs().add("int");
           
           firstpart.getMethodNames().add(met1);
           firstpart.getMethodNames().add(met2);
           d.getClassList().add(firstpart);
           
           UMLClasses secondpart = new UMLClasses("CounterTask", " ", " ", "", "BBB", 50, 100);
           
           secondpart.getParentInterfaces().add("Interface_Test");
           
           UMLVariables vegas2 = new UMLVariables("counter", "int", false, "private");
           secondpart.getVariableNames().add(vegas2);
           
           UMLMethods methane1 = new UMLMethods("CounterTask", " ", false, false, "private");
           UMLMethods methane2 = new UMLMethods("call", "void", false, false, "private");
           
           secondpart.getMethodNames().add(methane1);
           secondpart.getMethodNames().add(methane2);
           
           d.getClassList().add(secondpart);
           
           ///  
           UMLClasses thirdpart = new UMLClasses("DateTask", " ", " ", "", "CCC", 75, 19);
           
           UMLVariables nachos2 = new UMLVariables("now", "Date", false, "private");
           thirdpart.getVariableNames().add(nachos2);
           
           UMLMethods pizza1 = new UMLMethods("DateTask", " ", false, false, "private");
           UMLMethods pizza2 = new UMLMethods("call", "void", false, false, "private");
           
           thirdpart.getMethodNames().add(pizza1);
           thirdpart.getMethodNames().add(pizza2);
           
           thirdpart.getParentInterfaces().add("Interface_Test");
           
           d.getClassList().add(thirdpart);
           ////
           
           UMLClasses fourthpart = new UMLClasses("PauseHandler", " ", " ", "", "PackTest", 100, 200);
           
           UMLVariables phil2 = new UMLVariables("app", "float", false, "private");
           fourthpart.getVariableNames().add(phil2);
           
           UMLMethods tacos1 = new UMLMethods("handle", "void", false, false, "public");
           tacos1.getArgs().add("Event");
           
           fourthpart.getMethodNames().add(tacos1);
           
           fourthpart.getParentInterfaces().add("Interface_Test");
           
           d.getClassList().add(fourthpart);
           ////
           UMLClasses fifthpart = new UMLClasses("StartHandler", " ", " ", "", "PackTest", 5, 1);
           
           UMLVariables dark2 = new UMLVariables("app", "long", false, "private");
           fifthpart.getVariableNames().add(dark2);
           
           UMLMethods nice1 = new UMLMethods("handle", "void", false, false, "public");
           UMLMethods met11 = new UMLMethods("main", "void", true, false, "public"); 
           met11.getArgs().add("String[] ");
           nice1.getArgs().add("Event");
           
           fifthpart.getMethodNames().add(nice1);
           fifthpart.getMethodNames().add(met11);
           
           fifthpart.getParentInterfaces().add("Interface_Test");
          
           d.getClassList().add(fifthpart);
          // f.saveData(d, "./work/JUnitTest1.json");
           
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
           
           f.saveData(d, "./work/JUnitTest3.json");
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
        String filePath = "./work/JUnitTest3.json";
        //FileManager instance = new FileManager();
        f.loadData(d, filePath);
        // TODO review the generated test code and remove the default call to fail.
        s = (UMLInterfaces) d.getClassList().get(0);
        l = (ClassLines) d.getLineList().get(0);
        
        assertEquals("Interface_Test", s.getInterNametoString());
        System.out.println("ClassName Result: " + s.getInterNametoString());
        
        
        assertEquals("PackageA", s.getPackageName());
        System.out.println("PackageName Result: " + s.getPackageName());

        
        assertEquals("55.0", Double.toString(s.getTranslateXer()));
        System.out.println("X location Result: " + s.getTranslateXer());
        
        assertEquals("110.0", Double.toString(s.getTranslateYer()));
        System.out.println("Y location Result: " + s.getTranslateYer());
        
        v = s.getVariableNames().get(0);
        
        assertEquals(false, v.isStatictype());
        System.out.println("Is this variable static? " + v.isStatictype());
        
        
        m = s.getMethodNames().get(0);
        
        assertEquals("TestMethod1", m.getName());
        System.out.println("Method Name #1: " + m.getName());
        
        assertEquals("void", m.getReturntype());
        System.out.println("Return Type #1: " + m.getReturntype());
        
        assertEquals(true, m.isAbstractype());
        System.out.println("Is this method abstract? " + m.isAbstractype());
        
        m2 = s.getMethodNames().get(1);
        
        assertEquals("TestMethod2", m2.getName());
        System.out.println("Method Name #2: " + m2.getName());
        
        assertEquals(true, m2.isAbstractype());
        System.out.println("Return Type #2: " + m2.isAbstractype());
        
        String s = m2.getArgs().get(0);
        
        assertEquals("int", s);
        System.out.println("Argument Type of Method #2: " + s);
        
        assertEquals("5.0", Double.toString(l.getMid_x()));
        System.out.println("Midpoint of Line: " + l.getMid_x());
        
        assertEquals("10.0", Double.toString(l.getEndX()));
        System.out.println("Ending X: " + l.getEndX());
        
        assertEquals("0.0", Double.toString(l.getStartX()));
        System.out.println("Starting X: " + l.getStartX());
    }
}
