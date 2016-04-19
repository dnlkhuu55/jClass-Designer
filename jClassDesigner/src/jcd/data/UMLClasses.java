package jcd.data;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author dnlkhuu77
 */
public class UMLClasses extends VBox{
    private boolean abstracttype;
    private Text className;
    private String classNametoString;
    private ArrayList<UMLVariables> variableNames = new ArrayList<>();
    private ArrayList<UMLMethods> methodNames = new ArrayList<>();
    
    private Text currentVariableName;
    private Text currentMethodName;
    private String packageName;
    private String parentName;
    private ArrayList<String> parentInterfaces = new ArrayList<>();
    
    private double translateXer;
    private double translateYer;
    
    private double widthy;
    private double heighty;
    
    VBox classes;
    VBox variables;
    VBox method;

    public UMLClasses(String a){
        classNametoString = a;
        className = new Text(a);
        currentVariableName = new Text("");
        currentMethodName = new Text("");
        packageName = "";
        parentName = "";
        translateXer = 10;
        translateYer = 10;
        
        classes = new VBox();
        variables = new VBox();
        method = new VBox();
        
        classes.getChildren().add(className);
        variables.getChildren().add(currentVariableName);
        method.getChildren().add(currentMethodName);
        classes.setMargin(className, new Insets(5));
        variables.setMargin(currentVariableName, new Insets(5));
        method.setMargin(currentMethodName, new Insets(5));
        
        classes.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff");
        variables.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff");
        method.setStyle("-fx-background-color: #ffffff");
        
        this.getChildren().add(classes);
        this.getChildren().add(variables);
        this.getChildren().add(method);
        abstracttype = false;
    }
    
    public UMLClasses(String namey, String variabley, String methody, String par, 
            String packagey, double X, double Y){
        //TEST
        classNametoString = namey;
        className = new Text(namey);
        currentVariableName = new Text(variabley);
        currentMethodName = new Text(methody);
        packageName = packagey; //TEST
        parentName = par;
        translateXer = X;
        translateYer = Y;
        
        classes = new VBox();
        variables = new VBox();
        method = new VBox();
        
        classes.getChildren().add(className);
        variables.getChildren().add(currentVariableName);
        method.getChildren().add(currentMethodName);
        classes.setMargin(className, new Insets(5));
        variables.setMargin(currentVariableName, new Insets(5));
        method.setMargin(currentMethodName, new Insets(5));
        //method.maxHeightProperty().bind(this.maxHeightProperty());
        
        classes.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff");
        variables.setStyle("-fx-border-color: #000000; -fx-background-color: #ffffff");
        method.setStyle("-fx-background-color: #ffffff");
        
        this.getChildren().add(classes);
        this.getChildren().add(variables);
        this.getChildren().add(method);
        
        abstracttype = false;
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
        if(abstracttype == true)
            name = name + " {abstract}"; 
        
        className.setText(name);
        classes.getChildren().clear();
        classes.getChildren().add(className);
        
        
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
        if(abstracttype == true)
            classNametoString = classNametoString + " {abstract}";
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
        variables.getChildren().add(currentVariableName);
        //variableNames.add(currentVariableName);
    }
    
    /**
     * @return the variableNames
     */
    public ArrayList<UMLVariables> getVariableNames() {
        return variableNames;
    }

    /**
     * @param variableNames the variableNames to set
     */
    public void setVariableNames(ArrayList<UMLVariables> variableNames) {
        this.variableNames = variableNames;
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
        method.getChildren().add(currentMethodName);
        //methodNames.add(currentMethodName);
    }

    /**
     * @return the methodNames
     */
    public ArrayList<UMLMethods> getMethodNames() {
        return methodNames;
    }

    /**
     * @param methodNames the methodNames to set
     */
    public void setMethodNames(ArrayList<UMLMethods> methodNames) {
        this.methodNames = methodNames;
    }

    /**
     * @return the parentInterfaces
     */
    public ArrayList<String> getParentInterfaces() {
        return parentInterfaces;
    }

    /**
     * @param parentInterfaces the parentInterfaces to set
     */
    public void setParentInterfaces(ArrayList<String> parentInterfaces) {
        this.parentInterfaces = parentInterfaces;
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
     * @return the packageName
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * @param parentName
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
    
    /**
     * @return the widthy
     */
    public double getWidthy() {
        return widthy;
    }

    /**
     * @param widthy the widthy to set
     */
    public void setWidthy(double widthy) {
        this.widthy = widthy;
    }

    /**
     * @return the heighty
     */
    public double getHeighty() {
        return heighty;
    }

    /**
     * @param heighty the heighty to set
     */
    public void setHeighty(double heighty) {
        this.heighty = heighty;
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
        this.translateXer = translateX;
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

    /**
     * @return the isAbstract
     */
    public boolean isAbstract() {
        return abstracttype;
    }

    /**
     * @param isAbstract the isAbstract to set
     */
    public void setIsAbstract(boolean isAbstract) {
        this.abstracttype = isAbstract;
    }

}
