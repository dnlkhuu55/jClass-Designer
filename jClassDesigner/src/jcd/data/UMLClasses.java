package jcd.data;

import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author dnlkhuu77
 */
public class UMLClasses extends GridPane{
    private Text className;
    private String classNametoString;
    private ArrayList<Text> variableNames = new ArrayList<Text>();
    private ArrayList<Text> methodNames = new ArrayList<Text>();
    
    private Text currentVariableName;
    private Text currentMethodName;
    private String packageName;
    
    private double sceneX;
    private double sceneY;
    private double translateXer;
    private double translateYer;
    
    Text classText = new Text();
    
    public UMLClasses(Text a, Text b, Text c){
        className = a;
        currentVariableName = b;
        currentMethodName = c;
        addColumn(3, a, b, c); 
    }

    /**
     * @return the className
     */
    public Text getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String name) {
        className.setText(name);
    }

    /**
     * @return the classNametoString
     */
    public String getClassNametoString() {
        return classNametoString;
    }

    /**
     * @param classNametoString the classNametoString to set
     */
    public void setClassNametoString(String classNametoString) {
        this.classNametoString = classNametoString;
    }

    /**
     * @return the variableNames
     */
    public ArrayList<Text> getVariableNames() {
        return variableNames;
    }

    /**
     * @param variableNames the variableNames to set
     */
    public void setVariableNames(ArrayList<Text> variableNames) {
        this.variableNames = variableNames;
    }

    /**
     * @return the methodNames
     */
    public ArrayList<Text> getMethodNames() {
        return methodNames;
    }

    /**
     * @param methodNames the methodNames to set
     */
    public void setMethodNames(ArrayList<Text> methodNames) {
        this.methodNames = methodNames;
    }

    /**
     * @return the currentVariableName
     */
    public Text getCurrentVariableName() {
        return currentVariableName;
    }

    /**
     * @param currentVariableName the currentVariableName to set
     */
    public void setCurrentVariableName(Text currentVariableName) {
        this.currentVariableName = currentVariableName;
    }

    /**
     * @return the currentMethodName
     */
    public Text getCurrentMethodName() {
        return currentMethodName;
    }

    /**
     * @param currentMethodName the currentMethodName to set
     */
    public void setCurrentMethodName(Text currentMethodName) {
        this.currentMethodName = currentMethodName;
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @return the sceneX
     */
    public double getSceneX() {
        return sceneX;
    }

    /**
     * @param sceneX the sceneX to set
     */
    public void setSceneX(double sceneX) {
        this.sceneX = sceneX;
    }

    /**
     * @return the sceneY
     */
    public double getSceneY() {
        return sceneY;
    }

    /**
     * @param sceneY the sceneY to set
     */
    public void setSceneY(double sceneY) {
        this.sceneY = sceneY;
    }

    /**
     * @return the translateX
     */
    public double getTranslateXer() {
        return translateXer;
    }

    /**
     * @param translateX the translateX to set
     */
    public void setTranslateXer(double translateX) {
        this.translateXer = translateXer;
    }

    /**
     * @return the translateY
     */
    public double getTranslateYer() {
        return translateYer;
    }

    /**
     * @param translateY the translateY to set
     */
    public void setTranslateYer(double translateY) {
        this.translateYer = translateY;
    }
    
    public void updateClass(){
        setRowIndex(className, 0);
    }

    public void getStyle(String fxbackgroundcolor_white) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
