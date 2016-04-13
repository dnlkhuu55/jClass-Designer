/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_bed;

import javafx.scene.layout.VBox;
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
                System.out.println(((UMLClasses) sc).getPackageName());
                if(((UMLClasses) sc).getParentName() != null)
                    System.out.println(((UMLClasses) sc).getParentName().getClassNametoString());
                System.out.println(((UMLClasses) sc).getVariableNames().toString());
                System.out.println(((UMLClasses) sc).getMethodNames().toString());
            }
            System.out.println();
        }
        }
        catch(Exception e){
            System.out.println("NOT LOADING!");
        }
    }
}
