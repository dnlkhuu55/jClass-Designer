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
           UMLClasses firstpart = new UMLClasses(new Text("ThreadExample"));
           UMLVariables var1 = new UMLVariables("Check", "int", true, false);
           firstpart.setClassNametoString("Check");
           firstpart.setPackageName("Hello");
           firstpart.setTranslateXer(10);
           firstpart.setTranslateYer(10);
           //firstpart.setCurrentVariableName(new Text(var1.toString()));
           //app.getFileComponent().saveData(app.getDataComponent(), selectedFile.getPath());	
           d.getClassList().add(firstpart);
           //File file = new File(filePath);

           
           f.saveData(d, "./export/test.json");
           
        } catch(Exception e){
            System.out.println("NOT WORKING");
        }
        
        
    }
    
}
