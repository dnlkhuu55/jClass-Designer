/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import jcd.data.ClassLines;
import jcd.data.DataManager;
import jcd.data.UMLClasses;
import jcd.file.FileManager;

/**
 *
 * @author dnlkhuu77
 */
public class TestLoad {
    public static void main(String[] args){
        String filePath = "./work/DesignSaveTest.json";
        FileManager f = new FileManager();
        try{
            DataManager d = new DataManager(null);
            f.loadData(d, filePath);
            
            for(VBox sc: d.getClassList()){
            
            if(sc instanceof UMLClasses){
                System.out.println(((UMLClasses) sc).getClassNametoString());
                System.out.println("Abstract?: " + ((UMLClasses) sc).isAbstract());
                System.out.println(((UMLClasses) sc).getPackageName());
                if(!((UMLClasses) sc).getParentName().equals(""))
                    System.out.println(((UMLClasses) sc).getParentName());
                System.out.println(((UMLClasses) sc).getVariableNames().toString());
                System.out.println(((UMLClasses) sc).getMethodNames().toString());
                
                for(String s: ((UMLClasses) sc).getParentInterfaces()){
                    System.out.println(s);
                }
                
                
            }
            System.out.println();
        }
            
            for(Line l: d.getLineList()){
                if(l instanceof ClassLines){
                    System.out.println(l.getStartX());
                    System.out.println(l.getEndX());
                    System.out.println(l.getStartY());
                    System.out.println(l.getEndY());
                    System.out.println(((ClassLines) l).getMid_x());
                    System.out.println(((ClassLines) l).getMid_y());
                }
            }
        }
        catch(Exception e){
            System.out.println("NOT LOADING!");
        }
    }
}
